(ns lohan.flake
  "Generates Flake IDs https://github.com/boundary/flake

  Implementation based on https://github.com/mumrah/flake-java"
  (:require [lohan.common :as cmn])
  (:import [java.nio ByteBuffer])
  )

(def flake_seq (atom [0 0]))

(defn- id-seq
  []
  (swap! flake_seq
    (fn [[ts s]]
      (let [now (cmn/tick)]
        (if (= ts now)
          [ts (inc s)]
          [now 0]
          )
        )
    ))
  )

(defn- id
  []
  (let [[ts id] (id-seq)]
    (doto
      ; TODO cache byte buffer per thread
      (ByteBuffer/allocate 16)
      (.putLong ts)
      (.put cmn/mac-address)
      (.putShort id)
    )
  )
)

(defn gen-id
  "Generate a Flake ID"
  []
  (.array (id))
  )

(defn gen-string-id
  "Generate a Flask ID as in string format"
  []
  (let [bb (.flip (id))
        ts (.getLong bb)
        node0 (.getInt bb)
        node1 (.getShort bb)
        s (.getShort bb)
        ]
      (format "%016d-%s%s-%04d" ts (Integer/toHexString node0) (Integer/toHexString node1) s)
    )
  )
