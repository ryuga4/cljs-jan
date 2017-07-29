(ns cljs.db
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [com.mongodb DB WriteConcern MongoOptions ServerAddress
                        ]
           (org.bson.types ObjectId)))


(def uri (get (System/getenv) "MONGODB_URI" "mongodb://heroku_s7n8h4sj:rads5hr91viec1asgo1brvlaq1@ds125623.mlab.com:25623/heroku_s7n8h4sj"))

(def mongodb (mg/connect-via-uri uri))
(def db (mongodb :db))
(def conn (mongodb :conn))
(def coll "Coll")

(defn add-user
  [id name]
  (mc/insert db coll {:_id (ObjectId.) :id id :name name :deals {}})
  )

(defn print-ret
  [x]
  (println x)
  x)

(defn send-money
  [id id2 money]
  (let [id_map (mc/find-one-as-map db coll {:id id})
        id2_map (mc/find-one-as-map db coll {:id id2})
        deals (id_map :deals)
        deals2 (id_map :deals2)

        id_map_2 (print-ret (if (some nil? [deals (deals id2)])
                              (assoc-in id_map [:deals id2] 0)
                              id_map))
        id2_map_2 (print-ret (if (some nil? [deals2 (deals id)])
                               (assoc-in id2_map [:deals id] 0)
                               id2_map))
        ]
    (mc/update db coll {:id id}  (update-in id_map_2 [:deals id2] + money))
    (mc/update db coll {:id id2} (update-in id2_map_2 [:deals id] - money))
    ))

(defn add-friend
  [id id2]
  (let [id_map (mc/find-one-as-map db coll {:id id})
        deals (id_map :deals)
        id_map2 (if (some nil? [deals (deals id2)])
                  (assoc-in id_map [:deals id2] 0)
                  id_map)]
    (mc/update db coll {:id id} id_map2)))


(defn total-data
  []
  (mc/find-maps db coll {}))


(defn get-one
  [id]
  (mc/find-one-as-map db coll {:id id}))

(defn delete-all
  []
  (mc/remove db coll))