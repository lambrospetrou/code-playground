# Use V8 Isolates through Go

A basic comparison between using V8 Isolates to execute some JavaScript code through Go (Golang), versus shelling to `Node.js` per execution.

Obviously, we can optimize the Node case as well by starting up a process per thread and communicating with it over standard input/output, but then the solution is much more complex.

I just wanted to confirm that using V8 Isolates is easy, and it turns out it is :)

I use the V8 bindings provided by [rogchap.com/v8go](https://github.com/rogchap/v8go) so special thanks to the author.

## Results

**Mac 16" 2020**

```
➜  golang-v8isolates go test -bench . -benchtime 1s -benchmem
goos: darwin
goarch: amd64
pkg: github.com/lambrospetrou/golang-v8isolates
BenchmarkNode-16                              19	  62746119 ns/op     43208 B/op     57 allocs/op
BenchmarkNodeParallel-16                     176	   6861996 ns/op     43142 B/op     55 allocs/op
BenchmarkV8IsolatesReuse-16                 4171	    290742 ns/op        75 B/op      6 allocs/op
BenchmarkV8IsolatesNoReuse-16                823	   1414748 ns/op        88 B/op      7 allocs/op
BenchmarkV8IsolatesReuseParallel-16        30026	     45703 ns/op        75 B/op      6 allocs/op
BenchmarkV8IsolatesNoReuseParallel-16       2079	    983084 ns/op        83 B/op      7 allocs/op
PASS
ok  	github.com/lambrospetrou/golang-v8isolates	10.218s
```

**Surface Pro 2017**

```
lambros@LPSurfacePro:/mnt/c/Users/lambros/dev/github/code-playground/golang-v8isolates$ go test -bench . -benchtime 1s -benchmem
goos: linux
goarch: amd64
pkg: github.com/lambrospetrou/code-playground/golang-v8isolates
BenchmarkNode-4                            27    47009289 ns/op      51359 B/op     92 allocs/op
BenchmarkNodeParallel-4                    76    15338893 ns/op      51548 B/op     92 allocs/op
BenchmarkV8IsolatesReuse-4               3342      385395 ns/op         74 B/op      6 allocs/op
BenchmarkV8IsolatesNoReuse-4              692     1956912 ns/op         84 B/op      7 allocs/op
BenchmarkV8IsolatesReuseParallel-4       5684      213542 ns/op         75 B/op      6 allocs/op
BenchmarkV8IsolatesNoReuseParallel-4      303     3631768 ns/op         83 B/op      7 allocs/op
PASS
ok    github.com/lambrospetrou/code-playground/golang-v8isolates      12.725s
```
