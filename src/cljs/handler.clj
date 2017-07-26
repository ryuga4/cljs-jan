(ns cljs.handler
  (:use [ring.adapter.jetty])
  (:require [cljs.layout :refer [say]]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            ))

(defroutes app-routes

  (GET "/" [] (say "I am ready to be created!"))

  (route/resources "/")
  (route/not-found "not found"))

(def app
  (handler/site app-routes))


(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty app {:port port})))
