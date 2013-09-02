(ns lohan.common)

(defn default-node []
  (.getHostName (java.net.InetAddress/getLocalHost))
  )
