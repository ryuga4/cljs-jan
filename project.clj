(defproject cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :min-lein-version "2.3.4"

  :source-paths ["src" "src/cljs"]

  :dependencies [[compojure "1.2.1"]
                 [ring "1.3.1"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [org.clojure/clojure "1.6.0"]
                 [hiccup "1.0.5"]]

  :plugins [[lein-ring "0.8.13"]
            [com.cemerick/austin "0.2.0-SNAPSHOT"]
            [lein-cljsbuild "1.0.3"]]

  :hooks [leiningen.cljsbuild]

  :cljsbuild {
    :builds [{:source-paths ["src/cljs/cljs"]
              :compiler {:output-to "resources/public/js/cljs.js"
                         :optimizations :whitespace
                         :pretty-print true
                         ;; :source-map "resources/public/js/cljs.js.map"
                         }}]}

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
                            (browser-repl-env)))]}
     })
