.PHONY: bundle

bundle: prepare_output_dir build-app
	cp fly.toml build/

prepare_output_dir:
	rm -rf build
	mkdir -p build/bin

build-app: prepare_output_dir
	cd app; GOARCH=amd64 GOOS=linux go build -o app
	mv app/app build/bin/
	cp app/Dockerfile build/Dockerfile

docker-compose: bundle
	docker-compose up

docker-compose-fresh: bundle
	docker-compose up --build --remove-orphans

deploy:
	flyctl deploy build/
