(ns lohan.lww-element-set
  (:require [clojure.set :as s]
            [lohan.common :as cmn])
  (:refer-clojure :exclude [remove merge]))

(defn lww-element-set
  "Make a LWW-element-set"
  []
  {}
  )

(defn add
  "Add value to the LWW-element-set"
  [set value]
    (assoc set value
      (assoc (get set value [0 0]) 0 (cmn/tick))
      )
   )

(defn remove
  "Remove value to the LWW-element-set, if already existing in set"
  [set value]
    (if (contains? set value)
      (assoc set value
        (assoc (get set value) 1 (cmn/tick))
       )
      (throw (IllegalStateException. "Value not in set"))
     )
   )

(defn merge [set other]
  "Merge two LWW-element-sets"
  (merge-with (fn [v1 v2]
    [
      (max (first v1) (first v2))
      (max (get v1 1 0) (get v2 1 0))
    ])
    set other
    )
  )

(defn value
  "Get a clojure set of a LWW-element-set"
  ([setet] (value setet :a))
  ([setet bias]
    (set (for [[k [a r]] setet :when ((if (= bias :a) >= >) a r)] k))
  )
 )
