# File/Stream comparison Go vs Elixir

This project compares Go and Elixir while parsing files line-by-line and doing some filtering.

The programs parse a text file that has the output of `ls ~/*` and in different sizes. The purpose is to find the lines that their second column is a `number > X`.

## Requirements

Tested with:

|Runtime|Version|
|---|---|
|Go|1.10|
|Elixir|1.6.3|
|Racket|6.12|
|Python|3.5.2|
|Ruby|2.3.1p112|
|Rust|1.24.1|
|Crystal|0.24.2|
|Clojure|1.10|
|ClojureScript|1.10 (Planck)|

## Commands

Compile the binaries to run.

```
make
```

Run all the parsing for all the files inside `test-files/`.

```
make run
```

The filtered output files are inside the `output/`

## Results

In my benchmarks on Thinkpad T430 with 16GB ram and i5-3320M (running Xubuntu 16.04), the memory was constant and never exceeded more than few megabytes since all solutions are doing streaming of the input and output.

```
1x = Go (compiled, and `go run filter.go`)
1-1.5x = Rust (compiled with `-O`)
3.5x = Crystal (compile with `--release`)
5.5x = Ruby
8x = Python
10-15x = Clojure/Elixir/Racket
25-30x = Elixir (`IO.binstream(:stdio)`)
```

So Go seems to be the fastest but with a bit more verbose code.

Take the above results with a grain of salt, since when I run them on a Macbook 2015, Python was faster than Ruby so other results might be different as well.

```
# Some of the results and the datasets I used:

lambros@thinkunix:~/dev/github/code-playground/filterls$ ll -h test-files/data.txt test-files/dataMM.txt test-files/data.txt100000.txt 
-rw-rw-r-- 1 lambros lambros  23M Mar 18 17:48 test-files/dataMM.txt
-rw-rw-r-- 1 lambros lambros 2.4K Mar 18 17:48 test-files/data.txt
-rw-rw-r-- 1 lambros lambros 227M Mar 21 22:11 test-files/data.txt100000.txt

lambros@thinkunix:~/dev/github/code-playground/filterls$ make run
rm -rf output/ && mkdir -p output
elixir run-all.exs
filter-go-data.txt	[2.876ms	2876ns]
filter-go-dataMM.txt	[290.17ms	290170ns]
filter-go-data.txt100000.txt	[2724.463ms	2724463ns]
filter-rs-data.txt	[2.894ms	2894ns]
filter-rs-dataMM.txt	[346.541ms	346541ns]
filter-rs-data.txt100000.txt	[3863.397ms	3863397ns]
filter-cr-data.txt	[3.332ms	3332ns]
filter-cr-dataMM.txt	[1034.42ms	1034420ns]
filter-cr-data.txt100000.txt	[9677.427ms	9677427ns]
elixir-pat-data.txt	[257.237ms	257237ns]
elixir-pat-dataMM.txt	[3377.384ms	3377384ns]
elixir-pat-data.txt100000.txt	[29846.069ms	29846069ns]
elixir-split-data.txt	[245.733ms	245733ns]
elixir-split-dataMM.txt	[4345.178ms	4345178ns]
elixir-split-data.txt100000.txt	[35989.025ms	35989025ns]
filter.rkt-data.txt	[412.014ms	412014ns]
filter.rkt-dataMM.txt	[5559.543ms	5559543ns]
filter.rkt-data.txt100000.txt	[49156.482ms	49156482ns]
filter.py-data.txt	[128.758ms	128758ns]
filter.py-dataMM.txt	[3024.823ms	3024823ns]
filter.py-data.txt100000.txt	[22230.096ms	22230096ns]
filter.rb-data.txt	[62.38ms	62380ns]
filter.rb-dataMM.txt	[1619.383ms	1619383ns]
filter.rb-data.txt100000.txt	[15073.681ms	15073681ns]
```
