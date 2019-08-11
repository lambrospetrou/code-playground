function genBinary(depth = 10, probHasChildren = 0.9, probTwoChildren = 0.8) {
  const root = {val: 1};
  extendSubTree(root, depth, probHasChildren, probTwoChildren);
  addNodeAtDepth(root, depth);
  return root;
}

function newNode() {
  return {val: Math.ceil((Math.random() * 1000))};
}

function extendSubTree(node, depth, probHasChildren, probTwoChildren) {
  if (depth == 0) return;

  const hasChildren = Math.random() < probHasChildren;
  if (!hasChildren) return;

  const firstChild = Math.random() > 0.5 ? "left" : "right";

  node[firstChild] = newNode();
  extendSubTree(node[firstChild], depth - 1, probHasChildren, probTwoChildren);

  const hasTwoChildren = Math.random() < probTwoChildren;
  const secondChild = firstChild == "left" ? "right" : "left";
  if (hasTwoChildren) {
    node[secondChild] = newNode();
    extendSubTree(node[secondChild], depth - 1, probHasChildren, probTwoChildren);
  }
}

function addNodeAtDepth(root, maxdepth) {
  let node = root;
  let depth = maxdepth;
  while (depth > 0) {
    const {left, right} = node;
    if (!left && !right) {
      node.right = newNode();
      node = node.right;
    } else if (left) {
      node = node.left;
    } else {
      node = node.right;
    }
    depth -= 1;
  }
}

module.exports = {
  genBinary
};
