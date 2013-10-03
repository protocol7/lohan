(ns lohan.marzullo
  "Implementation of Marzullo's algorithm, as used in NTP"
  (:require [lohan.common :as cmn])
  )

(defn- compare-items
  [[oa ta] [ob tb]]
  (let [oc (compare oa ob)]
    (if (zero? oc) (compare tb ta) oc)
    )
  )

(defn intersect
  "Find the best intersect of the given sources. Sources should be vectors of
   start and end points, e.g. [[8 12] [10 12] [11 13]]"
  [sources]
    (let [
          table (
                 ;; Sort table based on offset and type
                 sort compare-items
                      ;; produce a table of [offset type]
                      (reduce
                        (fn [xs [start end]] (conj (conj xs [start -1]) [end 1]))
                        [] sources))
          ]
      ;; loop over table. finding the best intersect
      (loop [best 0
             cnt 0
             beststart 0
             bestend 0
             ts table
             ]
        (if (empty? ts)
          ;; we're done
          [beststart bestend]
          (let [[offset t] (first ts)
                cntprime (- cnt t)
                ]
            ;; if this is the best overlap so far (most intersects), use it
            (if (> cntprime best)
              (recur cntprime cntprime offset (first (second ts)) (rest ts))
              (recur best cntprime beststart bestend (rest ts))
              )
            )
          )
        )
      )
  )
