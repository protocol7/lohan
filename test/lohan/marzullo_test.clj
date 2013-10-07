(ns lohan.marzullo-test
  (:require [clojure.test :refer :all]
            [lohan.marzullo :as m]
  )
)

(deftest marzullo
  (testing "intersect"
    (is (= [0 0] (m/intersect [])))
    (is (= [11 12] (m/intersect [[11 12]])))
    (is (= [11 12] (m/intersect [[8 12] [11 13] [10 12]])))
    (is (= [11 12] (m/intersect [[8 12] [11 13] [14 15]])))
    (is (= [8 9] (m/intersect [[8 9] [8 12] [10 12]])))
  )
)
