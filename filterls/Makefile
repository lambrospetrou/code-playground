.PHONY: filter-go filter-ex filter-racket filter-py clean run

default: clean filter-go filter-ex filter-racket filter-py

clean:
	rm -rf build/ && mkdir -p build/

filter-go: clean
	go build -o filter-go filter.go
	mv filter-go build/

filter-ex: clean
	cd filter_iex; MIX_ENV=prod mix escript.build
	mv filter_iex/filter_iex build/filter-ex

filter-racket: clean
	cp filter-racket.rkt build/
	#raco exe --orig-exe filter-racket.rkt; mv filter-racket build/

filter-py: clean
	cp filter.py build/

run:
	rm -rf output/ && mkdir -p output
	elixir run-all.exs
