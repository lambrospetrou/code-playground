use rusqlite::{Connection, Result, params};
use rusqlite::NO_PARAMS;
use rand::Rng;
use std::env;
use std::time::Instant;

fn time_it<F: Fn() -> Result<()>>(prefix: String, f: F) -> Result<()> {
    let start = Instant::now();
    f()?;
    let duration = start.elapsed();
    println!("Time elapsed in {:?} is: {:?}", prefix, duration);
    return Ok(());
}

fn main() -> Result<()> {
    let mut conn = Connection::open("users.db")?;

    conn.execute(
        "create table if not exists users (name TEXT, age INTEGER)",
        NO_PARAMS,
    )?;

    let start = Instant::now();

    // Transaction inserts
    let num_inserts = match env::var("NUM_INSERTS") {
        Ok(val) => val.parse().unwrap(),
        Err(_) => 100
    };
    let tx = conn.transaction()?;
    time_it(String::from("transaction inserts"), || {
        let mut rng = rand::thread_rng();
        { // Scope is needed here to avoid `move_out` issues at `tx.commit()`
            let mut stmt = tx.prepare("INSERT INTO users (name, age) VALUES (?, ?)")?;
            for idx in 1..num_inserts {
                let name = if rng.gen::<f64>() < 0.6 {
                    format!("Lambros-{}", idx)
                } else {
                    String::from("Lambros")
                };
                stmt.execute(params![name, idx])?;
                // tx.execute("INSERT INTO users (name, age) VALUES (?, ?)", params![name, idx])?;
            }
        }
        print!("finished inserting {:?} users\n", num_inserts);
        return Ok(());
    })?;
    tx.commit()?;

    // Query all rows
    {
        time_it(String::from("query"), || {
            let mut stmt = conn.prepare("SELECT * FROM users WHERE name LIKE ?")?;
            let mut rows = stmt.query(params!["Lambros-%"])?;
            let mut n = 0;
            while let Some(_row) = rows.next()? {
                n += 1;
            }
            print!("finished fetching {} rows\n", n);
            let all_rows: i32 = conn.query_row("SELECT count(*) FROM users", NO_PARAMS, |row| row.get(0))?;
            print!("all rows: {}\n", all_rows);
            return Ok(());
        })?;
    }

    let duration = start.elapsed();
    println!("Time elapsed in main() is: {:?}", duration);

    Ok(())
}
