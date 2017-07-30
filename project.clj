(defproject cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :min-lein-version "2.3.4"

  :source-paths ["src" "src/cljs"]
  :dependencies [[compojure "1.2.1"]
                 [ring "1.3.1"]
                 [org.clojure/clojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [ring/ring-jetty-adapter "0.3.8"]
                 [com.novemberain/monger "3.1.0"]
                 [lein-environ "0.4.0"]
                 [ring/ring-json "0.4.0"]
                 [ring-json-response "0.2.0"]
                 [org.clojure/data.json "0.2.6"]]

  :plugins [[lein-ring "0.8.13"]
            [com.cemerick/austin "0.2.0-SNAPSHOT"]
            [lein-cljsbuild "1.0.3"]]


  :main cljs.handler


  :ring {:handler cljs.handler/app}

  :profiles
    {:dev {:dependencies [[ring-mock "0.1.5"]
                          [javax.servlet/servlet-api "2.5"]]

           :injections [(require '[cljs.brepl :refer [connect-to-browser]]
                                 '[cemerick.austin.repls])
                        (defn browser-repl-env []
                          (reset! cemerick.austin.repls/browser-repl-env
                                   (cemerick.austin/repl-env)))
                        (defn browser-repl []
                          (cemerick.austin.repls/cljs-repl
                            (browser-repl-env)))]
           :env {:mongodb_uri "mongodb://heroku_s7n8h4sj:rads5hr91viec1asgo1brvlaq1@ds125623.mlab.com:25623/heroku_s7n8h4sj"}}
     })
