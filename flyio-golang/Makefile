.PHONY: bundle

bundle: prepare_output_dir web
	cp fly.toml build/

prepare_output_dir:
	rm -rf build
	mkdir -p build/bin

web: prepare_output_dir
	cd web; GOARCH=amd64 GOOS=linux go build -o web
	mv web/web build/bin/
	cp web/Dockerfile build/Dockerfile

docker-compose: bundle
	docker-compose up

docker-compose-fresh: bundle
	docker-compose up --build --remove-orphans

deploy:
	flyctl deploy build/
