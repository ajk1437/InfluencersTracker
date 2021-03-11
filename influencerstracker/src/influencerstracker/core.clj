(ns influencerstracker.core
  (:require [clojure.java.jdbc :as sql]
            [yesql.core :refer [defquery defqueries]]
            [influencerstracker.twitchapi :refer :all]
            [influencerstracker.db :refer :all]
            [compojure.core :refer [defroutes GET ANY]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as jetty]
            [influencerstracker.views.layout :as layout]
            [influencerstracker.views.contents :as contents]
            [influencerstracker.views.index :as index]))

(defroutes routes
  (GET "/" [] (layout/application "Home" (index/homepage "Influencers tracker")))
  (GET "/hello" [] (layout/application "Hello ???" (contents/hello)))
  (GET "/subscribe" [] (layout/application "Subscrition" (contents/subscribe)))
  (GET "/pagination" [] (layout/application "Pagination" (contents/pagination)))
  ;;(GET "/pages/:id" [id]  (contents/page id))
  (route/resources "/")
  (ANY "*" [] (route/not-found (layout/application "Page Not Found" (contents/not-found)))))

(def application (handler/site routes))

(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (jetty/run-jetty application {:port port :join? false})))

