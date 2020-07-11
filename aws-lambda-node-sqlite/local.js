const db = require('better-sqlite3')(process.env.SQLITE_PATH || 'users.db');

db.prepare("create table if not exists users (name TEXT, age INTEGER)").run();

const run = () => {
  console.log(`starting...`)

  const hrstart = process.hrtime();

  const NUM_INSERTS = process.env.NUM_INSERTS || 100;
  const insertStm = db.prepare('INSERT INTO users (name, age) VALUES (?, ?)');
  db.transaction(
    () => {
      for (let i=0; i<NUM_INSERTS; i++) {
        if (Math.random() < 0.6) {
          insertStm.run(`Lambros-${i}`, i);
        } else {
          insertStm.run(`Lambros`, i);
        }
      }
    }
  )();
  console.log(`finished ${NUM_INSERTS} inserts`);

  const row = db.prepare('SELECT * FROM users WHERE name LIKE ?').get(`Lambros`);
  console.log(row.name, row.age);
  console.log(`finished single where`);

  const rows = db.prepare('SELECT * FROM users WHERE name LIKE ?').all(`Lambros%`);
  console.log(`finished fetching ${rows.length} rows`);

  const hrend = process.hrtime(hrstart);
  console.log(`Execution time (hr): ${hrend[0]}s ${hrend[1]/1000000}ms`);
};

if (process.env.RUN_LOCAL === "1") {
  run();
}

module.exports = {
  handler:  async (_event) => {
    run();

    const response = {
        statusCode: 200,
        body: JSON.stringify('Hello from Lambda!'),
    };
    return response;
  }
};
