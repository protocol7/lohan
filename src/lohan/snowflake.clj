(ns lohan.snowflake
  "Generates Snowdlake IDs https://github.com/boundary/flake"
  (:require [lohan.common :as cmn])
  )

(def twepoch 1288834974657)

(def sf_seq (atom [0 0]))

(defn- id-seq
  []
  (swap! sf_seq
    (fn [[ts s]]
      (let [now (cmn/tick)]
        (if (= ts now)
          ;; seq overflows at 4096
          ;; TODO should check for not overflowing within the same ms
          [ts (bit-and (inc s) 0xffff)]
          [now 0]
          )
        )
    ))
  )

(defn gen-id
  "Generate a Snowflake ID"
  [dc-id worker-id]
  ;; TODO verify dc-id and worker-id are within valid ranges
  (let [[ts id] (id-seq)]
    (bit-or
      (bit-shift-left (- ts twepoch) 22)
      (bit-shift-left dc-id 17)
      (bit-shift-left worker-id 12)
      id
      )
  )
)
