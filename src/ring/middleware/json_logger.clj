(ns ring.middleware.json-logger
  (:require [clojure.data.json :as json]))

(defn wrap-json-logger [handler]
  (fn [request]
    (let [start (System/currentTimeMillis)
          save-println (fn [line] ; see http://yellerapp.com/posts/2014-12-11-14-race-condition-in-clojure-println.html
                         (.write *out* (str line "\n"))
                         (flush))
          log (fn [request response]
                (-> {:method (:request-method request)
                     :uri (:uri request)
                     :query-string (:query-string request)
                     :remote-addr (:remote-addr request)
                     :duration (- (System/currentTimeMillis) start)}
                    (merge response)
                    (json/write-str :escape-slash false :escape-js-separators false)
                    save-println))]
      (try
        (let [response (handler request)]
          (log request {:status (:status response)})
          response)
        (catch Exception e
          (log request {:status 500
                        :exception (str (class e))
                        :exception-message (.getMessage e)})
          (throw e))))))
