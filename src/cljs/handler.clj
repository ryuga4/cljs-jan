(ns cljs.handler
  (:use [ring.adapter.jetty])
  (:require [cljs.layout :refer [say]]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [monger.core :as mg]
            [monger.collection :as mc]
            )
  (:import [com.mongodb DB WriteConcern MongoOptions ServerAddress
                        ]
           (org.bson.types ObjectId)))

(def uri (System/getenv "MONGODB_URI"))

(defroutes app-routes

  (GET "/" [] (say "I am ready to be created!"))

  (route/resources "/")
  (route/not-found "not found"))

(def app
  (handler/site app-routes))


(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty app {:port port})))


(let [{:keys [conn db]} (mg/connect-via-uri uri)]
  (mc/insert db "Coll" {:_id (ObjectId.) :imie "dupa"}))
