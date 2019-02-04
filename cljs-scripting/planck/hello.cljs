#!/usr/bin/env plk

; http://planck-repl.org/scripts.html

(ns example.echo
  (:require [planck.core :as core]))

(doseq [l (core/line-seq core/*in*)]
  (println l))

(doseq [arg *command-line-args*]
  (println arg))

(doseq [file (core/file-seq "/tmp")]
  (println (:path file)))
