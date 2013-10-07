(ns lohan.intersection-algo
  "Implementation of the Intersection algorithm, as used in NTP"
  )

(defn- compare-items
  [[oa ta] [ob tb]]
  (let [oc (compare oa ob)]
    (if (zero? oc) (compare tb ta) oc)
    )
  )

(defn- build-table
  [sources]
  ;; Sort table based on offset and type
  (sort compare-items
    ;; produce a table of [offset type], including the mid-point
    (reduce
      (fn [xs [start end]]
        (conj xs [start -1] [(+ start (/ (- end start) 2)) 0] [end 1])
        )
      [] sources))
  )

(defn- find-boundary
  [min-ok num-bad table boundary intersect-fn]
  (loop [
         table table
         num-intersect 0
         num-bad num-bad
         boundary boundary
         ]
    (if (seq table)
      (let [
            [boundary typ] (first table)
            num-intersect (intersect-fn num-intersect typ)
           ]
        (if (>= num-intersect min-ok)
          [num-bad boundary]
          (recur (rest table) num-intersect (if (zero? typ) (inc num-bad) num-bad) boundary)
        )
      )
      ;; end of table
      [num-bad boundary]
    )
  )
)

(defn- intersect-for-f
  [min-ok lower upper table]
  (let [
        [num-bad lower] (find-boundary min-ok 0 table lower -)
        [num-bad upper] (find-boundary min-ok num-bad (reverse table) upper +)
        ]
      [num-bad lower upper]
    )
  )

(defn- check-return [lower upper] (if (<= lower upper) [lower upper] []) )

(defn intersect
  "Find the best intersect of the given sources. Sources should be vectors of
   start and end points, e.g. [[8 12] [10 12] [11 13]]"
  [sources]
  (if (seq sources)
    (let [
          table (build-table sources)
          M (count sources)
          ]
      ;; loop over the allowed false ticker range
      (loop [
             fs (range 0 (/ M 2))
             lower 0
             upper 0
             ]
        (if (seq fs)
          (let [
                max-bad (first fs)
                min-ok (- M max-bad)
                [num-bad lower upper] (intersect-for-f min-ok lower upper table)
              ]
              (if (<= num-bad max-bad)
                (check-return lower upper)
                (recur (rest fs) lower upper)
                )
            )
          ;; we've checked all allowed false tickers, check what to return
          (check-return lower upper)
          )
        )
      )
      ;; empty sources
      []
    )
  )
