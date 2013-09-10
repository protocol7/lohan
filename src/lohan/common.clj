(ns lohan.common
  (:import [java.net InetAddress NetworkInterface])
  )

(defn default-node []
  (.getHostName (java.net.InetAddress/getLocalHost))
  )

(def clock (atom (fn [] (System/currentTimeMillis))))

(defn tick [] (@clock))

(defn set-clock [c] (swap! clock (fn [_] c)))

(def ^:dynamic mac-address
  (.getHardwareAddress (NetworkInterface/getByInetAddress (InetAddress/getLocalHost)))
  )
