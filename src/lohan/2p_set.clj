(ns lohan.2p-set
  (:require [clojure.set :as s])
  (:refer-clojure :exclude [remove merge]))

(defn twop-set
  "Make a 2P-set"
  []
  [#{} #{}]
  )

(defn add
  "Add value to the 2P-set"
  [set value]
     [(conj (first set) value) (second set)]
   )

(defn remove
  "Remove value to the 2P-set, if already existing in set"
  [set value]
   (if (contains? (first set) value)
     [(first set) (conj (second set) value)]
     (throw (IllegalStateException. "Value not in set"))
     )
   )

(defn merge [set other]
  "Merge two 2P-sets"
  [(s/union (first set) (first other)) (s/union (second set) (second other))]
  )

(defn value [set]
  "Get a clojure set of a 2P-set"
  (s/difference (first set) (second set))
  )
