(ns lohan.g-counter)

(defn g-counter
  "Make a G-counter"
  []
  {}
  )

(defn- default-node []
  (.getHostName (java.net.InetAddress/getLocalHost))
  )

(defn increment
  "Increment a G-counter with the provided delta"
  ([counter delta] (increment counter delta (default-node)))
  ([counter delta node]
   (let [existing (get counter node 0)]
     (assoc counter node (+ existing delta))
     )
   )
  )

(defn merge [counter other]
  "Merge two G-counters"
  (merge-with max counter other)
  )

(defn value [counter]
  "Get the value of a G-counter"
  (reduce + (vals counter))
  )
