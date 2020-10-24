package main

import (
	"database/sql"
	"fmt"
	"log"
	"math/rand"
	"os"
	"strconv"
	"time"

	_ "github.com/mattn/go-sqlite3"
)

func createTable(db *sql.DB) {
	sqlStmt := `
	create table if not exists users (name TEXT, age INTEGER);
	--delete from users;
	`
	_, err := db.Exec(sqlStmt)
	if err != nil {
		log.Fatalf("%q: %s\n", err, sqlStmt)
	}
}

func transactionInserts(db *sql.DB, num int) {
	tx, err := db.Begin()
	if err != nil {
		log.Fatal(err)
	}
	stmt, err := tx.Prepare("INSERT INTO users (name, age) VALUES (?, ?)")
	if err != nil {
		log.Fatal(err)
	}
	defer stmt.Close()
	for i := 0; i < num; i++ {
		var suffix string
		if rand.Float64() >= 0.6 {
			suffix = "Lambros"
		} else {
			suffix = fmt.Sprintf("Lambros-%03d", i)
		}
		_, err = stmt.Exec(suffix, i)
		if err != nil {
			log.Fatal(err)
		}
	}
	tx.Commit()
	log.Printf("finished inserting %d users", num)
}

func queryAll(db *sql.DB) {
	rows, err := db.Query("SELECT * FROM users WHERE name LIKE ?", "Lambros-%")
	if err != nil {
		log.Fatal(err)
	}
	n := 0
	for rows.Next() {
		n++
	}
	log.Printf("finished fetching %d rows", n)

	rows, err = db.Query("SELECT count(*) FROM users")
	if err != nil {
		log.Fatal(err)
	}
	rows.Next()
	cnt := 0
	if err = rows.Scan(&cnt); err != nil {
		log.Fatal(err)
	}
	log.Println("all rows:", cnt)
}

func timeIt(prefix string, f func()) {
	start := time.Now()
	f()
	log.Println("Time elapsed in", prefix, "is:", time.Since(start))
}

func main() {
	dbPath := os.Getenv("SQLITE_PATH")
	if dbPath == "" {
		dbPath = "./users.db"
	}

	// os.Remove(dbPath)
	db, err := sql.Open("sqlite3", dbPath)
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	var numInserts = 100
	numInsertsStr := os.Getenv("NUM_INSERTS")
	if numInsertsStr != "" {
		numInserts, err = strconv.Atoi(numInsertsStr)
		if err != nil {
			log.Fatal(err)
		}
	}

	timeIt("main()", func() {
		createTable(db)
		timeIt("inserts", func() {
			transactionInserts(db, numInserts)
		})
		timeIt("query", func() {
			queryAll(db)
		})
	})
}
