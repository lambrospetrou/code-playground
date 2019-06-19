(ns server.main
  ; Import NPM modules directly 
  ; https://shadow-cljs.github.io/docs/UsersGuide.html#npm
  (:require ["crypto" :as crypto]))

(def value-a 1)

(defonce value-b 2)

(defn do-work []
  (println (-> (crypto/randomBytes 16) (.toString "base64"))))

(defn reload! []
  (println "Code updated.")
  (println "Trying values:" value-a value-b)
  (do-work))

(defn main! []
  (println "App loaded!")
  (do-work))

(defn handler [event context callback]
  (do
    (println event)
    (callback
     nil
     (clj->js {:statusCode 200
               :body (.stringify js/JSON (-> (crypto/randomBytes 16) (.toString "base64")))
               :headers {}}))))
