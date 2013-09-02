(ns lohan.2p-set-test
  (:require [clojure.test :refer :all]
            [lohan.2p-set :as s]))

(deftest twop-set
  (testing "make"
    (is (= [#{} #{}] (s/twop-set) ))
    )
  (testing "add"
    (is (= [#{"foo"} #{}]  (s/add (s/twop-set) "foo")))
    )
  (testing "remove"
    (is (= [#{"foo"} #{"foo"}]  (s/remove (s/add (s/twop-set) "foo") "foo")))
    (is (thrown? IllegalStateException  (s/remove (s/twop-set) "foo")))
    )
  (testing "merge"
    (let [s1 (s/add (s/twop-set) "foo")
          s2 (s/remove (s/add (s/twop-set) "bar") "bar")]
        (is (= [#{"foo" "bar"} #{"bar"}] (s/merge s1 s2)))
      )
    )
  (testing "value"
    (is (= #{"foo"} (s/value (s/add (s/twop-set) "foo"))))
    (is (= #{} (s/value (s/remove (s/add (s/twop-set) "foo") "foo"))))
    )
  )
