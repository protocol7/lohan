(ns lohan.pn-counter-test
  (:require [clojure.test :refer :all]
            [lohan.pn-counter :as c]))

(deftest pn-counter
  (testing "make"
    (is (= {} (c/pn-counter) ))
    )
  (testing "increment"
    (is (= {:P {"foo" 1}} (c/increment (c/pn-counter) 1 "foo") ))
    )
  (testing "decrement"
    (is (= {:N {"foo" 1}} (c/decrement (c/pn-counter) 1 "foo") ))
    )
  (testing "increment with delta"
    (let [c (c/increment (c/pn-counter) 1 "foo")]
        (is (= {:P {"foo" 3}} (c/increment c 2 "foo")))
        (is (= {:P {"foo" 1 "bar" 2}} (c/increment c 2 "bar")))
      )
    )
  (testing "decrement with delta"
    (let [c (c/decrement (c/pn-counter) 1 "foo")]
        (is (= {:N {"foo" 3}} (c/decrement c 2 "foo")))
        (is (= {:N {"foo" 1 "bar" 2}} (c/decrement c 2 "bar")))
      )
    )
  (testing "merge"
    (let [c1 (c/increment (c/pn-counter) 1 "foo")
          c2 (c/increment (c/pn-counter) 2 "bar")
          c3 (c/increment c1 1 "bar")
          c4 (c/decrement c1 1 "foo")]
        (is (= {:P {"foo" 1 "bar" 2}} (c/merge c1 c2)))
        (is (= {:P {"foo" 1 "bar" 2}} (c/merge c2 c3)))
        (is (= {:P {"foo" 1 "bar" 1}} (c/merge c1 c3)))
        (is (= {:P {"foo" 1} :N {"foo" 1}} (c/merge c1 c4)))
      )
    )
  (testing "value"
    (let [c (c/increment (c/pn-counter) 1 "foo")]
        (is (= 3 (c/value (c/increment c 2 "foo"))))
        (is (= 3 (c/value (c/increment c 2 "bar"))))
        (is (= 0 (c/value (c/decrement c 1 "foo"))))
        (is (= 2 (c/value (c/decrement (c/increment c 2 "bar") 1 "bar"))))
      )
    )
  )
