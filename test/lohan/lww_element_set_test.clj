(ns lohan.lww-element-set-test
  (:require [clojure.test :refer :all]
            [lohan.lww-element-set :as s]
            [lohan.common :as cmn]
            )
  )

(defn clock [time]
  (cmn/set-clock (fn [] time))
  )

(defn setup [f]
  (clock 1)
  (f)
  )

(use-fixtures :each setup)

(deftest lww-element-set
  (testing "make"
    (is (= {} (s/lww-element-set) ))
    )
  (testing "add"
    (is (= {"foo" [1 0]}  (s/add (s/lww-element-set) "foo")))
    )
  (testing "remove"
    (let [x (s/add (s/lww-element-set) "foo")]
      (clock 2)
        (is (= {"foo" [1 2]}  (s/remove x "foo")))
      )
    )
  (testing "remove-non-existing"
    (is (thrown? IllegalStateException (s/remove (s/lww-element-set) "dummy")))
    )
  (testing "merge"
    (clock 1)
    (let [s1 (s/add (s/lww-element-set) "foo")]
      (clock 2)
      (let [s2 (s/remove (s/add s1 "bar") "foo")]
          (is (= {"foo" [1 2] "bar" [2 0]} (s/merge s1 s2)))
        )
      )
    )
  (testing "value"
    (clock 1)
    ; bias default to adds
    (let [s1 (s/remove (s/add (s/lww-element-set) "foo") "foo")]
      (is (= #{"foo"} (s/value s1)))
    )
    ; bias on removes
    (let [s1 (s/remove (s/add (s/lww-element-set) "foo") "foo")]
      (is (= #{} (s/value s1 :r)))
    )
  )
)
