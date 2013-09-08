(ns lohan.common)

(defn default-node []
  (.getHostName (java.net.InetAddress/getLocalHost))
  )

(def clock (atom (fn [] (System/currentTimeMillis))))

(defn tick [] (@clock))

(defn set-clock [c] (swap! clock (fn [_] c)))
