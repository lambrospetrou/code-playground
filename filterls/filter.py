#!/usr/bin/env python3

import sys

def filter(line):
    segs = line.split(maxsplit=2)
    if len(segs) > 1 and segs[1].isnumeric():
        return int(segs[1]) > 10

def process():
    for line in sys.stdin:
        if filter(line):
            print(line[:-1])

if __name__ == "__main__":
    process()
