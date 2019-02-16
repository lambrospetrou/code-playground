#!/usr/bin/env node

const readline = require("readline")

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
    terminal: false
});

const filterLine = line => {
    const parts = line.split(" ", 3);
    if (parts.length < 2) { return false; }
    const n = parseInt(parts[1]);
    if (isNaN(n) || n <= 10) { return false; }
    return true;
};

rl.on("line", line => {
    if (filterLine(line)) { console.log(line); }
});

