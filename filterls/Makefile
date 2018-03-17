.PHONY: filter-go filter-ex

default: clean filter-go filter-ex

clean:
	rm -rf build/ && mkdir -p build/

filter-go: clean
	go build filter-go.go
	mv filter-go build/

filter-ex: clean
	cd filter_iex; MIX_ENV=prod mix escript.build
	mv filter_iex/filter_iex build/filter-ex

run:
	rm -rf output/ && mkdir -p output
	elixir run-all.exs
