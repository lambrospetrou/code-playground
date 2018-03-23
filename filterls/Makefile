.PHONY: filter-go filter-ex filter-racket filter-py filter-rs clean run

default: clean filter-go filter-ex filter-racket filter-py filter-rb filter-rs

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

run:
	rm -rf output/ && mkdir -p output
	elixir run-all.exs
