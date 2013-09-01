(ns lohan.core
  (:gen-class)
  (:require [lohan.g-counter :as g])
  )

(defn -main
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (let [c (g/g-counter)]
    (let [c2 (g/increment c 2 "foo")]
      (println c2)
      (println (g/merge (g/increment c2 1) (g/increment c2 2)))
      (println (g/value (g/increment c2 2)))
      )
    )
  )
