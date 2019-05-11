#!/usr/bin/env clj

(ns filter-clj.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn filterer [line]
  (let [cols (str/split line #" " 3)]
    (if 
      (< (count cols) 2)
      false  
      (try 
        (> (Integer/parseInt (get cols 1)) 10)
        (catch Exception e false)))))

(defn process [in out]
    (with-open [bufout (java.io.BufferedWriter. out) bufin (java.io.BufferedReader. in)]
          (doseq
                  [^String l (line-seq bufin)]
                  (if (filterer l) (do (.write bufout l) (.newLine bufout))))))

(defn process-file [fin fout]
    (process (java.io.FileReader. fin) (java.io.FileWriter. fout)))

(defn process-stdio [] (process *in* *out*))

(defn -main []
  (process-stdio))

