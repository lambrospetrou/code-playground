#!/usr/bin/env plk

; http://planck-repl.org/scripts.html
; http://planck-repl.org/sdk.html

(ns example.echo
  (:require [planck.core :as core]
            [goog.string :as gstr]))

(defn filterls-lines
  ([lines] (filterls-lines lines *out*))
  ([lines out]
   (doseq [l lines]
     (let [parts (gstr/splitLimit l " " 3)
           second-col (get parts 1)
           num (if (gstr/isNumeric second-col) (gstr/parseInt second-col) nil)]
       (if (> num 10) (do (-write out l) (-write out "\n")))))
   (-flush out)))

(defn filterls [] (filterls-lines (core/line-seq core/*in*)))

(set! *main-cli-fn* filterls)

#(time (example.echo/filterls-lines (planck.core/line-seq (planck.io/reader "../../filterls/test-files/data.txt")) (planck.io/writer "./results.filterls.out")))
#(time (example.echo/filterls-lines (planck.core/line-seq (planck.io/reader "../../filterls/test-files/dataMM.txt")) (planck.io/writer "./results.filterls.out")))
