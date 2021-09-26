package main

import (
	"context"
	"fmt"
	"html"
	"log"
	"net/http"
	"strings"

	"crawshaw.io/sqlite/sqlitex"
)

func main() {
	var err error
	var dbpool *sqlitex.Pool
	// if dbpool, err = sqlitex.Open("file::memory:?mode=memory", 0, 10); err != nil {
	if dbpool, err = sqlitex.Open("./users.sqlite3", 0, 10); err != nil {
		log.Fatal(err)
	}
	ensureTableExists(dbpool)

	http.HandleFunc("/add", makeAddHandler(dbpool))
	http.HandleFunc("/", makeGetHandler(dbpool))
	fmt.Println("Listening port :8080 ")
	log.Fatal(http.ListenAndServe(":8080", nil))
}

func ensureTableExists(dbpool *sqlitex.Pool) {
	conn := dbpool.Get(context.TODO())
	if conn == nil {
		return
	}
	defer dbpool.Put(conn)
	sqlitex.ExecTransient(conn, "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, UNIQUE(name));", nil)
}

func makeAddHandler(dbpool *sqlitex.Pool) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		name := strings.TrimSpace(r.URL.Query().Get("name"))
		if len(name) == 0 {
			w.WriteHeader(http.StatusBadRequest)
			fmt.Fprintf(w, "Invalid 'name' given: %q", name)
			return
		}

		conn := dbpool.Get(r.Context())
		if conn == nil {
			return
		}
		defer dbpool.Put(conn)

		if err := sqlitex.Exec(conn, "INSERT INTO users (name) VALUES (?);", nil, name); err != nil {
			w.WriteHeader(http.StatusInternalServerError)
			fmt.Fprintf(w, "Sadly we failed to register you: %s", name)
			return
		}
		fmt.Fprintf(w, "Welcome to our community %s!", name)
	}
}

func makeGetHandler(dbpool *sqlitex.Pool) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		name := strings.TrimSpace(r.URL.Query().Get("name"))
		if len(name) == 0 {
			w.WriteHeader(http.StatusBadRequest)
			fmt.Fprintf(w, "Invalid 'name' given: %s", name)
			return
		}

		conn := dbpool.Get(r.Context())
		if conn == nil {
			return
		}
		defer dbpool.Put(conn)

		stmt := conn.Prep("SELECT * FROM users WHERE name = $name;")
		stmt.SetText("$name", name)
		exists := false
		for {
			if hasRow, err := stmt.Step(); err != nil {
				w.WriteHeader(http.StatusInternalServerError)
				fmt.Fprintf(w, "Could not query the 'name' given: %s", html.EscapeString(name))
				return
			} else if !hasRow {
				break
			}
			exists = true
		}
		if !exists {
			w.WriteHeader(http.StatusBadRequest)
			fmt.Fprintf(w, "Sadly we could not find you: %s", html.EscapeString(name))
		} else {
			fmt.Fprintf(w, "Boom! We found you: %s", html.EscapeString(name))
		}
	}
}
