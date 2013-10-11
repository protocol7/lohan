(ns lohan.or-set
  (:require [clojure.set :as s]
            )
  (:refer-clojure :exclude [remove merge]))

(defn or-set
  "Make a OR-set provided a tag generating function, e.g backed by Snowflake"
  [tag-gen-fn]
  [{} tag-gen-fn]
  )

(defn add
  "Add value to the OR-set"
  [[s tag-gen-fn] value]
  (let [
        [ins-set del-set] (get s value [#{} #{}])
        ]
      ;; add a generated tag to the insertion set
      [(assoc s value [(conj ins-set (tag-gen-fn)) del-set]) tag-gen-fn]
    )
   )

(defn remove
  "Remove value to the OR-element-set"
  [[s tag-gen-fn] value]
  (let [
        [ins-set del-set] (get s value [#{} #{}])
        ]
      ;; insert insertion set into the deletion set
      [(assoc s value [ins-set (s/union del-set ins-set)]) tag-gen-fn]
    )
  )

(defn merge [[s1 tag-gen-fn1] [s2 tag-gen-fn2]]
  "Merge two OR-sets"
  [(merge-with (fn [v1 v2]
    [
      (s/union (first v1) (first v2))
      (s/union (second v1) (second v2))
    ])
    s1 s2
    ) tag-gen-fn1]
  )

(defn value
  "Get a clojure set of a OR-set"
  [[s _]]
    (set (for [[k [a r]] s :when (seq (s/difference a r))] k))
  )
