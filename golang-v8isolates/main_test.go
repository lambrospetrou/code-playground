package main

import (
	"testing"

	"rogchap.com/v8go"
)

func BenchmarkNode(b *testing.B) {
	script := generateScript(2) + "\nconsole.log(result);"
	for i := 0; i < b.N; i++ {
		node(script)
	}
}

func BenchmarkNodeParallel(b *testing.B) {
	script := generateScript(5) + "\nconsole.log(result);"
	b.ResetTimer()
	b.RunParallel(func(pb *testing.PB) {
		for pb.Next() {
			node(script)
		}
	})
}

func BenchmarkV8IsolatesReuse(b *testing.B) {
	script := generateScript(2)
	isolate, _ := v8go.NewIsolate()
	for i := 0; i < b.N; i++ {
		v8isolates(script, isolate)
	}
}

func BenchmarkV8IsolatesNoReuse(b *testing.B) {
	script := generateScript(2)
	for i := 0; i < b.N; i++ {
		v8isolates(script)
	}
}

func BenchmarkV8IsolatesReuseParallel(b *testing.B) {
	script := generateScript(5)
	b.ResetTimer()
	b.RunParallel(func(pb *testing.PB) {
		isolate, _ := v8go.NewIsolate()
		for pb.Next() {
			v8isolates(script, isolate)
		}
	})
}

func BenchmarkV8IsolatesNoReuseParallel(b *testing.B) {
	script := generateScript(5)
	b.ResetTimer()
	b.RunParallel(func(pb *testing.PB) {
		for pb.Next() {
			v8isolates(script)
		}
	})
}
