(ns browser.main)

; The output will be loadable through the `<script>` tag in any
; webpage, making it ideal for client-side code (e.g. React).
;
; More details at https://shadow-cljs.github.io/docs/UsersGuide.html#target-browser

(println "Hello, world! - this is from the module static code - Open your Console in DevTools")

(defn init []
  (println "This is from the init function")
  (.appendChild (.-body js/document) (.createTextNode js/document "It works!")))
