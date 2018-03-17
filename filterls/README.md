# File/Stream comparison Go vs Elixir

This project compares Go and Elixir while parsing files line-by-line and doing some filtering.

The programs parse a text file that has the output of `ls ~/*` and in different sizes. The purpose is to find the lines that their second column is a `number > X`.

## Requirements

Tested with:

**Go: 1.10+**
**Elixir: 1.6.3**

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
1x = Go
100x = Elixir pattern matching
200x = Elixir splitting
```

So Go seems to be super fast compared to Elixir for raw IO, but pattern matching is really an elegant way to write functions.

