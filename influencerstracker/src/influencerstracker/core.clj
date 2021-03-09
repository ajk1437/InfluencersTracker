(ns influencerstracker.core
  (:require [clojure.java.jdbc :as sql]
            [yesql.core :refer [defquery defqueries]]
            [influencerstracker.twitchapi :refer :all]
            [compojure.core :refer [defroutes GET ANY]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as jetty]
            [influencerstracker.views.layout :as layout]
            [influencerstracker.views.contents :as contents]))

(def database {:subprotocol "postgresql"
         :subname "//localhost/influencers"
         :user "postgres"})

(defn parse-int [s]
  (Integer. (re-find  #"\d+" s)))

; id SERIAL-autoincrement :name, :game, :views, :language, :timestamp
(defn create-influencers-table! []
  (sql/db-do-commands database
                      (sql/create-table-ddl
                       :influencers
                       [[:id "SERIAL"]
                        [:user_id "integer"]
                        [:username "varchar(200)"]
                        [:game "varchar(200)"]
                        [:views "integer"]
                        [:language "varchar(200)"]
                        [:timestamp "TIMESTAMP"]])))

(defqueries "influencers.sql" {:connection database})

;; Add influencer to database
(create-influencer!
          {:user_id (parse-int (influencerstracker.twitchapi/get-streams-top-id))
           :username (influencerstracker.twitchapi/get-streams-top-name)
           :game (influencerstracker.twitchapi/get-streams-top-gamename)
           :views (influencerstracker.twitchapi/get-stream-viewcount)
           :language (influencerstracker.twitchapi/get-stream-language)})

;; loop in background collecting data from twitch api
(defn set-interval [callback ms]
  (future (while true (do (Thread/sleep ms) (callback)))))

;; start work in background every 15min (900000 miliseconds) collect data
(def job (set-interval 
         #(create-influencer!
          {:user_id (parse-int (influencerstracker.twitchapi/get-streams-top-id))
           :username (influencerstracker.twitchapi/get-streams-top-name)
           :game (influencerstracker.twitchapi/get-streams-top-gamename)
           :views (influencerstracker.twitchapi/get-stream-viewcount)
           :language (influencerstracker.twitchapi/get-stream-language)}) 900000))

;; cancel background job
(future-cancel job)



(find-influencers)

(influencers-count)

(influencer-by-id {:id 252})

(influencer-order-by-timestamp)

(influencer-last-7-days)

(influencer-with-biggest-live-views)

(game-rank-1)


(defroutes routes
  (GET "/" [] (layout/application "Home" (contents/index)))
  (GET "/hello" [] (layout/application "Hello ???" (contents/hello)))
  (GET "/subscribe" [] (layout/application "Subscrition" (contents/subscribe)))
  (GET "/pagination" [] (layout/application "Pagination" (contents/pagination)))
  (GET "/pages/:id" [id]  (contents/page id))
  (route/resources "/")
  (ANY "*" [] (route/not-found (layout/application "Page Not Found" (contents/not-found)))))

(def application (handler/site routes))

(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (jetty/run-jetty application {:port port :join? false})))

