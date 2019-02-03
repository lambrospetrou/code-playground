(ns hello-world.core
  (:require ;[cljs.nodejs :as nodejs]
            [clojure.string :as str]))

;(nodejs/enable-util-print!)
(enable-console-print!)

(println "Hello world!")

(defn average [& a]
  (do
    (println (str "Calculating average for: " a))
    (/ (reduce + a) (count a))))

(defn extract-nums [event]
  (into
   []
   (map
    #(js/parseInt %)
    (filter #((comp not str/blank?) %) (str/split (get (get event "queryStringParameters") "n" "") #",")))))

(defn ^:export handler [event context callback]
  (do
    (println event)
    (callback
     nil
     (clj->js {:statusCode 200
               :body (.stringify js/JSON (apply average (extract-nums (js->clj event))))
               :headers {}}))))

; The following only works with `simple` optimizations
; (set! (.-exports js/module) #js
      ; {:handler handler})
