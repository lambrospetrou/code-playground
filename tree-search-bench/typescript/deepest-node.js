#!/usr/bin/env node
"use strict";
// I use `node` above instead of `ts-node` because this will be compiled to JS before running.
Object.defineProperty(exports, "__esModule", { value: true });
var fs_1 = require("fs");
exports.deepestNode = function (root) {
    var do_deep_rec = function (n, depth) {
        if (!n) {
            return [undefined, 0];
        }
        if (!n.left && !n.right) {
            return [n, depth];
        }
        var _a = do_deep_rec(n.left, depth + 1), left = _a[0], dleft = _a[1];
        var _b = do_deep_rec(n.right, depth + 1), right = _b[0], dright = _b[1];
        if (dleft > dright) {
            return [left, dleft];
        }
        return [right, dright];
    };
    return do_deep_rec(root, 1)[0];
};
var inputPath = process.argv[2];
var tree = JSON.parse(String(fs_1.readFileSync(inputPath)));
console.time("deepestNode");
var result = exports.deepestNode(tree);
console.timeEnd("deepestNode");
console.error(JSON.stringify(result));
