import * as tf from "@tensorflow/tfjs";

const a = tf.tensor([[1, 2], [3, 4]]);
console.log("shape:", a.shape);
a.print();
