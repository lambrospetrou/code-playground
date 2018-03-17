package main

import (
	"bufio"
	"io"
	"os"
	"strconv"
	"strings"
)

type filterer func(string) bool

func ce(err error) {
	if err != nil {
		panic(err)
	}
}

var splitter filterer = func(line string) bool {
	segs := strings.Fields(line)
	if len(segs) > 1 {
		n, err := strconv.ParseUint(segs[1], 10, 64)
		if err == nil && n > 0 {
			return true
		}
	}
	return false
}

func fromFile(filename string) {
	fin, err := os.Open(filename)
	ce(err)
	fout, err := os.Create(filename + ".go.txt")
	process(fin, fout, splitter)
}

func fromStdIo() {
	process(os.Stdin, os.Stdout, splitter)
}

func process(i io.ReadCloser, o io.WriteCloser, f filterer) {
	bufIn := bufio.NewReader(i)
	in := bufio.NewScanner(bufIn)
	out := bufio.NewWriter(o)
	for in.Scan() {
		l := in.Text()
		if f(l) {
			out.WriteString(l)
			out.WriteString("\n")
		}
	}
	out.Flush()
	o.Close()
	i.Close()
}

func main() {
	if len(os.Args) > 1 {
		fromFile(os.Args[1])
	} else {
		fromStdIo()
	}
}
