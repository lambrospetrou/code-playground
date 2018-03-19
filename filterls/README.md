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

In my benchmarks on T430 with 16GB ram and i5-3320M, the memory was constant and never exceeded more than few megabytes since all solutions are doing streaming of the input and output.

```
1x = Go (compiled, and `go run filter.go`)
6x = Python
20x = Racket
85x = Elixir pattern matching
180x = Elixir splitting
```

So Go seems to be the fastest but with a bit more verbose code.
