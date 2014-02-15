(ns lohan.zipf
  (:require [lohan.common :as cmn])
  (:refer-clojure :exclude [merge]))

(defn- gen-dist-map
  "Ported from http://stackoverflow.com/a/8788662/24495"
  [n alpha]
  (let [
        tmp (map (fn [i] (/ 1.0 (Math/pow i alpha))) (range 1 (inc n)))
        zeta (reduce (fn [sums x] (conj sums (+ (last sums) x))) [0] tmp)
        ]
    (map (fn [x] (/ x (last zeta))) zeta)
    )
  )

(defn make-sample
  "Sample a Zipf distribution. Returns a lazy seq of sampled value"
  [n alpha]
  (let [
        dist-map (gen-dist-map n alpha)
        f (fn []
            (let [
              u (Math/random)
              p (java.util.Collections/binarySearch dist-map u compare)
              d (if (< p 0) (dec (- (inc p))) p)
              ]
              d
            )
          )
        ]
    (for [_ (range)] (f))
    )
  )
