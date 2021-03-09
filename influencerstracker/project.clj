(defproject influencerstracker "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-http "2.1.0"]
                 [dire "0.5.4"]
                 [yesql "0.5.3"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.postgresql/postgresql "42.2.6"]
                 [compojure "1.6.1"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [hiccup "1.0.5"]]
  :main influencerstracker.core)
