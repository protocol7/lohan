(ns lohan.intersection-algo-test
  (:require [clojure.test :refer :all]
            [lohan.intersection-algo :as m]
  )
)

(deftest intersection
  (testing "intersect"
    (is (= [] (m/intersect [])))
    (is (= [11 12] (m/intersect [[11 12]])))
    (is (= [10 12] (m/intersect [[8 12] [11 13] [10 12]])))
    (is (= [11 12] (m/intersect [[8 12] [11 13] [14 15]])))
    (is (= [8 12] (m/intersect [[8 9] [8 12] [10 12]])))
    (is (= [5 8] (m/intersect [[3  7] [5 15] [4 8] [7 15]])))
    (is (= [5 7] (m/intersect [[3  7] [5 15] [4 8] [9 13]])))
    (is (= [] (m/intersect [[3  7] [8 12] [4 8] [9 13]])))
  )
)
