#!/usr/bin/env lumo

(ns example.echo
  (:require [readline :as readline]
            [goog.string :as gstr]
            [clojure.string :as str]))

(defn filter-line [l]
  (let [parts (str/split l #" " 3)
        second-col (get parts 1)
        num (if (gstr/isNumeric second-col) (gstr/parseInt second-col) nil)]
    (> num 10)))

(defn filterls []
  (let [rl (.createInterface readline (clj->js {:input js/process.stdin, :output js/process.stdout, :terminal false}))]
    (.on rl "line" #(if (filter-line %) (println %)))))

(set! *main-cli-fn* filterls)

