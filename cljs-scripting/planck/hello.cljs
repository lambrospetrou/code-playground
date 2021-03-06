#!/usr/bin/env plk

; http://planck-repl.org/scripts.html
; http://planck-repl.org/sdk.html

(ns example.echo
  (:require [planck.core :as core]
            [goog.string :as gstr]
            [clojure.string :as str]))

; (doseq [l (core/line-seq core/*in*)]
;   (println l))

; (doseq [arg *command-line-args*]
;   (println arg))

; (doseq [file (core/file-seq "/tmp")]
;   (println (:path file)))

(defn filterls-lines
  ([lines] (filterls-lines lines *out*))
  ([lines out]
   (doseq [l lines]
     (let [parts (str/split l #" " 3)
           second-col (get parts 1)
           num (if (gstr/isNumeric second-col) (gstr/parseInt second-col) nil)]
       (if (> num 10) (do (-write out l) (-write out "\n")))))))

(defn filterls [] (filterls-lines (core/line-seq core/*in*)))

(time (example.echo/filterls-lines (planck.core/line-seq (planck.io/reader "../../filterls/test-files/data.txt")) (planck.io/writer "./results.filterls.out")))
(time (example.echo/filterls-lines (planck.core/line-seq (planck.io/reader "../../filterls/test-files/dataMM.txt")) (planck.io/writer "./results.filterls.out")))
