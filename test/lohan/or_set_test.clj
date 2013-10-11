(ns lohan.or-set-test
  (:require [clojure.test :refer :all]
            [lohan.or-set :as s]
            )
  )

(def next-tag (atom 1))

(defn set-next-tag [t] (swap! next-tag (fn [_] t)))

(defn tag-gen [] @next-tag )

(defn each-fixture [f]
  (set-next-tag 1)
  (f)
)

(use-fixtures :each each-fixture)

(deftest or-set
  (testing "make"
    (is (= [{} tag-gen] (s/or-set tag-gen) ))
    )
  (testing "add"
    (is (= [{"foo" [#{1} #{}]} tag-gen] (s/add (s/or-set tag-gen) "foo")))
    )
  (testing "remove"
    (let [x (s/add (s/or-set tag-gen) "foo")]
        (is (= [{"foo" [#{1} #{1}]} tag-gen] (s/remove x "foo")))
      )
    )
  (testing "remove-non-existing"
    (is (= [{"foo" [#{} #{}]} tag-gen] (s/remove (s/or-set tag-gen) "foo")))
    )
  (testing "merge"
    (let [s1 (s/add (s/or-set tag-gen) "foo")]
      (set-next-tag 2)
      (let [s2 (s/remove (s/add s1 "bar") "foo")]
          (is (= [{"foo" [#{1} #{1}] "bar" [#{2} #{}]} tag-gen] (s/merge s1 s2)))
        )
      )
    )
  (testing "value"
    (set-next-tag 1)
    (let [
          s1 (s/or-set tag-gen)
          s2 (s/add s1 "foo")
          s3 (s/remove s2 "foo")
          _ (set-next-tag 2) ;; yolo
          s4 (s/add s3 "foo")
          s5 (s/add s4 "bar")
          ]
      (is (= #{} (s/value s1)))
      (is (= #{"foo"} (s/value s2)))
      (is (= #{} (s/value s3)))
      (is (= #{"foo"} (s/value s4)))
      (is (= #{"foo" "bar"} (s/value s5)))
    )
  )
)
