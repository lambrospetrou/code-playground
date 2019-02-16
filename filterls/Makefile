.PHONY: filter-go filter-ex filter-racket filter-py filter-rs filter-cr filter-cljs filter-clj filter-clj-uberjar clean run

default: clean filter-go filter-ex filter-racket filter-py filter-rb filter-rs filter-cr filter-cljs filter-clj filter-clj-uberjar filter-js

clean:
	rm -rf build/ && mkdir -p build/

filter-go: clean
	go build -o filter-go filter.go
	mv filter-go build/
	cp filter.go build/

filter-rs: clean
	rustc filter.rs -o build/filter-rs -O

filter-ex: clean
	cd filter_iex; MIX_ENV=prod mix escript.build
	mv filter_iex/filter_iex build/filter-ex

filter-racket: clean
	cp filter.rkt build/
	#raco exe --orig-exe filter-racket.rkt; mv filter-racket build/

filter-py: clean
	cp filter.py build/

filter-rb: clean
	cp filter.rb build/

filter-cr: clean
	crystal filter.cr -o build/filter-cr --release

filter-js: clean
	cp filter.js build/

filter-cljs: clean
	cp filter.cljs build/
	cp filter-lumo.cljs build/

filter-clj: clean
	cp filter.clj build/

filter-clj-uberjar: clean
	cd filter_clj; lein compile && lein uberjar
	mv filter_clj/target/filter_clj-0.1.0-SNAPSHOT-standalone.jar build/filter-clj-uberjar.jar
	docker run --rm -it -v `pwd`:`pwd` -w `pwd` oracle/graalvm-ce:1.0.0-rc12 bash -c "native-image -H:+ReportUnsupportedElementsAtRuntime --no-server -J-Xmx4G -J-Xms4G -jar ./build/filter-clj-uberjar.jar && chown `id -u`:`id -u` ./filter-clj-uberjar"
	mv filter-clj-uberjar build/filter-clj-uberjar-native

run:
	rm -rf output/ && mkdir -p output
	elixir run-all.exs
