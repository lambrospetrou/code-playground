package main

import (
	"context"
	"fmt"
	"log"
	"net/http"
	"os"

	"zombiezen.com/go/sqlite"
	"zombiezen.com/go/sqlite/sqlitex"
)

var dbpool *sqlitex.Pool

// Using a Pool to execute SQL in a concurrent HTTP handler.
func main() {
	port := os.Getenv("PORT")
	if len(port) == 0 {
		port = "5000"
	}
	dbPath := os.Getenv("SQLITE_PATH")
	if dbPath == "" {
		dbPath = "./users.db"
	}

	var err error
	dbpool, err = sqlitex.Open(dbPath, 0, 10)
	if err != nil {
		log.Fatal(err)
	}
	createTable()

	log.Printf("Started the server at " + port)

	http.HandleFunc("/", handle)
	log.Fatal(http.ListenAndServe(":"+port, nil))
}

func handle(w http.ResponseWriter, r *http.Request) {
	conn := dbpool.Get(r.Context())
	if conn == nil {
		fmt.Fprintln(w, "No connection available...")
		return
	}
	defer dbpool.Put(conn)

	log.Println("selecting from db...")

	err := sqlitex.Execute(conn, "SELECT * FROM users;", &sqlitex.ExecOptions{
		ResultFunc: func(stmt *sqlite.Stmt) error {
			fmt.Fprintln(w, stmt.ColumnText(0))
			return nil
		},
	})
	if err != nil {
		log.Println(err)
	}
}

func createTable() {
	conn := dbpool.Get(context.Background())
	if conn == nil {
		log.Fatalln("No connection available...")
		return
	}
	defer dbpool.Put(conn)

	sqlStmt := `create table if not exists users (name TEXT, age INTEGER);`
	if err := sqlitex.Execute(conn, sqlStmt, nil); err != nil {
		log.Fatalln("Could not create initial table!")
	}

	sqlInsertStmt := `INSERT INTO users (name, age) VALUES ('lambros', 30);`
	if err := sqlitex.Execute(conn, sqlInsertStmt, nil); err != nil {
		log.Fatalln("Could not insert initial rows!")
	}

	err := sqlitex.Execute(conn, "SELECT * FROM users;", &sqlitex.ExecOptions{
		ResultFunc: func(stmt *sqlite.Stmt) error {
			log.Println(stmt.ColumnText(0))
			return nil
		},
	})
	if err != nil {
		log.Println(err)
	}
}
