(ns library.main
  ; Import NPM modules directly https://shadow-cljs.github.io/docs/UsersGuide.html#npm
  (:require ["crypto" :as crypto]))

; Library code is helpful when you want to use standard commonjs
; module `require` to import this from AWS Lambda or just other
; Node applications, or other generic JavaScript modules.
;
; More details at https://shadow-cljs.github.io/docs/UsersGuide.html#target-node-library

; AWS Lambda handler example
(defn handler [event context callback]
  (do
    (println event)
    (callback
     nil
     (clj->js {:statusCode 200
               :body (.stringify js/JSON (-> (crypto/randomBytes 16) (.toString "base64")))
               :headers {}}))))
