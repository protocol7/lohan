(ns lohan.g-counter-test
  (:require [clojure.test :refer :all]
            [lohan.g-counter :as g]))



(deftest g-counter
  (testing "make"
    (is (= {} (g/g-counter) ))
    )
  (testing "increment"
    (is (= {"foo" 1} (g/increment (g/g-counter) 1 "foo") ))
    )
  (testing "increment with delta"
    (let [c (g/increment (g/g-counter) 1 "foo")]
        (is (= {"foo" 3} (g/increment c 2 "foo")))
        (is (= {"foo" 1 "bar" 2} (g/increment c 2 "bar")))
      )
    )
  (testing "merge"
    (let [c1 (g/increment (g/g-counter) 1 "foo")
          c2 (g/increment (g/g-counter) 2 "bar")
          c3 (g/increment c1 1 "bar")]
        (is (= {"foo" 1 "bar" 2} (g/merge c1 c2)))
        (is (= {"foo" 1 "bar" 2} (g/merge c2 c3)))
        (is (= {"foo" 1 "bar" 1} (g/merge c1 c3)))
      )
    )
  (testing "value"
    (let [c (g/increment (g/g-counter) 1 "foo")]
        (is (= 3 (g/value (g/increment c 2 "foo"))))
        (is (= 3 (g/value (g/increment c 2 "bar"))))
      )
    )
  )
