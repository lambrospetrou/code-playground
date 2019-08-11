#!/usr/bin/env node

// I use `node` above instead of `ts-node` because this will be compiled to JS before running.

import { readFileSync } from "fs";

/**
 * Given the root of a binary tree, return a deepest node. For example, in the following tree, return d.

      a
    / \
   b  c
  /
  d

*/

interface Node {
  left?: Node,
  right?: Node,
  val: any
}

export const deepestNode = (root: Node) : Node => {
  const do_deep_rec = (n: Node | undefined, depth: number) : [Node | undefined, number] => {
    if (!n) {
      return [undefined, 0]
    }
    if (!n.left && !n.right) {
      return [n, depth];
    }

    const [left, dleft] = do_deep_rec(n.left, depth + 1);
    const [right, dright] = do_deep_rec(n.right, depth + 1);

    if (dleft > dright) {
      return [left, dleft];
    }
    return [right, dright];
  }
  return do_deep_rec(root, 1)[0] as Node
}

const inputPath = process.argv[2];
const tree = JSON.parse(String(readFileSync(inputPath)));

console.time("deepestNode");
const result = deepestNode(tree);
console.timeEnd("deepestNode");

console.error(JSON.stringify(result));
