#!/usr/bin/env lumo

(ns example.echo
  (:require ["shelljs" :as sh]))

(defonce path (js/require "path"))

; (doseq [arg *command-line-args*]
;   (println arg))

(defn- -main [& args]
  ; (doseq [arg args] (println arg))
  (println (str (sh/cat (.join path (str (sh/pwd)) "package.json"))))
)

(set! *main-cli-fn* -main)
