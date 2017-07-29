(ns cljs.handler
  (:use [ring.adapter.jetty])
  (:require
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [cljs.db :as db]
            ))


1
(defroutes app-routes

  (GET "/" [] (response "I am ready to be created!"))
  (GET "/users" [] (-> (db/total-data)
                       (response)
                       (ring.util.response/content-type "json")))
  (GET "/test" [dupa] (response dupa))
  (GET "/send-money" [id1 id2 money] (db/send-money id1 id2 (. Integer parseInt  money))
                                     (response "Przesłane"))
  (GET "/add-friend" [id1 id2] (do (db/add-friend id1 id2)
                                   (response "Dodany znajomy")))
  (GET "/add-user" [id name] (do (db/add-user id name)
                                 (response "Dodany użytkownik")))
  (POST "/send-money" [id1 id2 money] (db/send-money id1 id2 (. Integer parseInt  money))
                                              (response "Przesłane"))
  (POST "/add-friend" [id1 id2] (do (db/add-friend id1 id2)
                                            (response "Dodany znajomy")))
  (POST  "/add-user" [id name] (do (db/add-user id name)
                                          (response "Dodany użytkownik")))

  (GET "/delete-all" [] (do (db/delete-all)
                            (response "Usunięto wszystkich")))
  (route/resources "/")
  (route/not-found "not found"))

(def app
  (handler/site app-routes))



(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty app {:port port})))





