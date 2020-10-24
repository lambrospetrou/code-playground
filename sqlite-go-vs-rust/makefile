SHELL := /bin/bash

run: clean-db
	for i in `seq 50`; do SQLITE_PATH=users-rust.db NUM_INSERTS=200 ./sqlite-rust 2>&1 | grep -ie "Time.*main" ; done;
	for i in `seq 50`; do SQLITE_PATH=users-go.db NUM_INSERTS=200 ./sqlite-golang 2>&1 | grep -ie "Time.*main" ; done;

clean: clean-db
	rm sqlite-golang sqlite-rust

clean-db:
	rm -f users-*.db

compile: compile-go compile-rust compile-rust-static
	echo "Binaries compiled."

compile-go:
	cd golang && go build && mv sqlite-golang ../sqlite-golang

compile-rust:
	cd rust && make release && mv target/release/rust ../sqlite-rust

compile-rust-static:
	cd rust && make release-static && mv target/x86_64-unknown-linux-musl/release/rust ../sqlite-rust-static
