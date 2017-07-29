(ns cljs.handler
  (:use [ring.adapter.jetty])
  (:require
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [cljs.db :as db]
            ))



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
  (POST "/send-money" {:keys [params]} (do (let [{:keys [id id2 money]} params]
                                             (db/send-money id id2 money))
                                           (response "Dodany użytkownik")))
  (POST "/add-friend" {:keys [params]} (do (let [{:keys [id id2]} params]
                                             (db/add-friend id id2))
                                           (response "Dodany użytkownik")))
  (POST  "/add-user" {:keys [params]} (do (let [{:keys [id name]} params]
                                            (db/add-user id name))
                                          (response "Dodany użytkownik")))

  (GET "/delete-all" [] (do (db/delete-all)
                            (response "Usunięto wszystkich")))
  (route/resources "/")
  (route/not-found "not found"))

(def app (->
           (handler/site app-routes)
           wrap-json-params))



(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty app {:port port})))





