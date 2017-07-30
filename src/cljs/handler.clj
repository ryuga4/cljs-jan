(ns cljs.handler
  (:use [ring.adapter.jetty])
  (:require
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :refer [response content-type]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params wrap-json-body]]
            [cljs.db :as db]
            [ring.util.json-response :refer [json-response]]
            [clojure.data.json :as json]
            ))
(defn toResponse
  [x]
  (response (json/read-str (json/write-str x))))


(defroutes app-routes

  (GET "/" [] (response "I am ready to be created!"))
  (GET "/users" [] (let [res (toResponse (map #(dissoc % :_id :deals) (db/total-data)))]
                     (println res)
                     res))

  (GET "/send-money" [id1 id2 money] (db/send-money id1 id2 (. Integer parseInt  money))
                                     (response "Przesłane"))
  (GET "/add-friend" [id1 id2] (do (db/add-friend id1 id2)
                                   (response "Dodany znajomy")))
  (GET "/add-user" [id name] (do (db/add-user id name)
                                 (response "Dodany użytkownik")))


  (GET "/delete-all" [] (do (db/delete-all)
                            (response "Usunięto wszystkich")))

  (GET "/get-one" [id] (toResponse (:deals (db/get-one id))))
  (route/resources "/")
  (route/not-found "not found"))

(def app (->
           (handler/site app-routes)
           (wrap-json-body)
           (wrap-json-params)
           (wrap-json-response)))



(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty app {:port port})))





