.PHONY: release-docker release clean prepare

release: clean
	cargo build --release

release-static: clean
	cross build --target x86_64-unknown-linux-musl --release
#docker run --rm --user "$(id -u)":"$(id -g)" -v `pwd`:/usr/src/myapp -w /usr/src/myapp rust:1 cargo build --release

clean:
	rm -rf target/

prepare:
	cargo install cross
