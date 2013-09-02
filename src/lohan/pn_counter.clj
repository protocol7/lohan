(ns lohan.pn-counter
  (:require [lohan.common :as cmn]))

(defn pn-counter
  "Make a PN-counter"
  []
  {}
  )

(defn- change [counter delta node registry]
   (let [reg (get counter registry {})
         existing (get reg node 0)]
     (assoc counter registry (assoc reg node (+ existing delta)))
     )
   )

(defn increment
  "Increment a PN-counter with the provided delta"
  ([counter delta] (change counter delta (cmn/default-node) :P))
  ([counter delta node] (change counter delta node :P))
  )

(defn decrement
  "Decrement a PN-counter with the provided delta"
  ([counter delta] (change counter delta (cmn/default-node) :N))
  ([counter delta node] (change counter delta node :N))
  )

(defn merge [counter other]
  "Merge two PN-counters"
  (let [
        p (merge-with max (get counter :P {}) (get other :P {}))
        n (merge-with max (get counter :N {}) (get other :N {}))]
    ; below is ugly, there must be a better way(TM)
    (cond
      (and (= p {}) (= n {})) {}
      (= p {}) {:N n}
      (= n {}) {:P p}
      :else {:P p :N n}
    )
   )
  )

(defn value [counter]
  "Get the value of a PN-counter"
  (-
    (reduce + (vals (get counter :P {})))
    (reduce + (vals (get counter :N {})))
   )
  )
