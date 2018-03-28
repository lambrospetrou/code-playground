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
1x = Rust (compiled with `-O`)
2.5x = Crystal (compile with `--release`)
4.5x = Ruby
6x = Python
10x = Elixir pattern matching (`File.stream!`)
15x = Racket
30x = Elixir pattern matching (`IO.binstream(:stdio)`)
60x = Elixir splitting
```

So Go seems to be the fastest but with a bit more verbose code.

Take the above results with a grain of salt, since when I run them on a Macbook 2015, Python was faster than Ruby so other results might be different as well.

```
# Some of the results with the largest dataset I used (2.3GB)

lambros@thinkunix:~/dev/github/code-playground/filterls$ time ./filter.go < test-files/data.txt1000000.txt > output/data.txt1000000.txt.filter-go.txt

real	0m27.408s
user	0m24.504s
sys	0m2.477s

lambros@thinkunix:~/dev/github/code-playground/filterls$ time ./build/filter-rs < test-files/data.txt1000000.txt > output/data.txt1000000.txt.filter-rs.txt

real	0m24.518s
user	0m23.453s
sys	0m1.048s

lambros@thinkunix:~/dev/github/code-playground/filterls$ time ./build/filter-cr < test-files/data.txt1000000.txt > output/data.txt1000000.txt.filter-cr.txt

real	1m8.233s
user	0m41.949s
sys	0m31.815s

lambros@thinkunix:~/dev/github/code-playground/filterls$ time ./filter.rb < test-files/data.txt1000000.txt>  output/data.txt1000000.txt.filter-rb.txt

real	1m45.471s
user	1m43.303s
sys	0m2.064s

lambros@thinkunix:~/dev/github/code-playground/filterls$ time ./filter.py < test-files/data.txt1000000.txt > output/data.txt1000000.txt.filter-py.txt

real	2m21.371s
user	2m18.621s
sys	0m2.068s

lambros@thinkunix:~/dev/github/code-playground/filterls$ time ./build/filter-ex 0 test-files/data.txt1000000.txt output/data.txt1000000.txt.filter-ex.txt

real	4m51.423s
user	4m43.745s
sys	0m5.974s

lambros@thinkunix:~/dev/github/code-playground/filterls$ time ./filter.rkt < test-files/data.txt1000000.txt > output/data.txt1000000.txt.filter-rkt.txt

real	6m33.597s
user	6m28.500s
sys	0m4.807s

lambros@thinkunix:~/dev/github/code-playground/filterls$ time ./build/filter-ex 0 < test-files/data.txt1000000.txt > output/data.txt1000000.txt.filter-ex-stdin.txt

real	13m47.526s
user	14m33.955s
sys	2m30.357s
```
