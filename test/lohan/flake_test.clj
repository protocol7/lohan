(ns lohan.flake-test
  (:require [clojure.test :refer :all]
            [lohan.flake :as f]
            [lohan.common :as cmn]
  )
  (:import [java.util Arrays])
)

(defn clock [time]
  (cmn/set-clock (fn [] time))
  )

(deftest flake
  (testing "gen"
    (clock 1)
    (binding [cmn/mac-address (.getBytes "hellob")]
      (is (= (seq (byte-array (map byte [0 0 0 0 0 0 0 1 104 101 108 108 111 98 0 0]))) (seq (f/gen-id))))
      (is (= (seq (byte-array (map byte [0 0 0 0 0 0 0 1 104 101 108 108 111 98 0 1]))) (seq (f/gen-id))))
      (clock 2)
      (is (= (seq (byte-array (map byte [0 0 0 0 0 0 0 2 104 101 108 108 111 98 0 0]))) (seq (f/gen-id))))
    )
  )
  (testing "gen-string"
    (clock 3)
    (binding [cmn/mac-address (.getBytes "hellob")]
      (is (= "0000000000000003-68656c6c6f62-0000" (f/gen-string-id)))
      (is (= "0000000000000003-68656c6c6f62-0001" (f/gen-string-id)))
      (clock 4)
      (is (= "0000000000000004-68656c6c6f62-0000" (f/gen-string-id)))
    )
  )
)
