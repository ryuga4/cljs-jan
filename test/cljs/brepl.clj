(ns cljs.brepl
  (:require [cemerick.austin :as a]
            [cemerick.austin.repls :refer [browser-repl-env cljs-repl]]
            [ring.adapter.jetty :refer [run-jetty]]
            [cljs.handler :refer [app-routes]]))

(defn run
  []
  (defonce ^:private server
    (run-jetty #'app-routes {:port 3000 :join? false}))
  server)

(defn connect-to-browser []
  (run)
  (let [repl-env (reset! browser-repl-env
                         (a/repl-env))]
    (cljs-repl repl-env)))
