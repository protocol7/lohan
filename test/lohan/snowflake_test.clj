(ns lohan.snowflake-test
  (:require [clojure.test :refer :all]
            [lohan.snowflake :as s]
            [lohan.common :as cmn]
  )
  (:import [java.util Arrays])
)

(defn clock [time]
  (cmn/set-clock (fn [] time))
  )

(deftest snowflake
  (testing "gen-id"
    (clock 1288834974658)
      (is (= 4472832 (s/gen-id 2 4)))
      (is (= 4472833 (s/gen-id 2 4)))
      (clock 2)
    )
  )
