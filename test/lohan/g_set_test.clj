(ns lohan.g-set-test
  (:require [clojure.test :refer :all]
            [lohan.g-set :as s]))

(deftest g-set
  (testing "make"
    (is (= #{} (s/g-set) ))
    )
  (testing "add"
    (is (= #{"foo"} (s/add (s/g-set) "foo")))
    )
  (testing "merge"
    (let [s1 (s/add (s/g-set) "foo")
          s2 (s/add (s/g-set) "bar")]
        (is (= #{"foo" "bar"} (s/merge s1 s2)))
      )
    )
  (testing "value"
    (is (= #{"foo"} (s/value (s/add (s/g-set) "foo"))))
    )
  )
