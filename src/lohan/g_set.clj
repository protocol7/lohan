(ns lohan.g-set
  (:require [clojure.set :as s])
  (:refer-clojure :exclude [merge]))

(defn g-set
  "Make a G-set"
  []
  #{}
  )

(defn add
  "Add value to the G-set"
  [set value]
     (conj set value)
   )

(defn merge [set other]
  "Merge two G-sets"
  (s/union set other)
  )

(defn value [set]
  "Get a clojure set of a G-set"
  set
  )
