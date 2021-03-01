(ns influencerstracker.core
  (:require [clojure.java.jdbc :as sql]
            [clj-time.core :as t]
            [clj-time.coerce :as c]
            [clj-time.local :as l]
            [yesql.core :refer [defquery defqueries]]
            [twitchapi :refer :all]))

(def database {:subprotocol "postgresql"
         :subname "//localhost/influencers"
         :user "postgres"})

; id SERIAL-autoincrement :name, :game, :views, :language, :timestamp
(defn create-influencers-table! []
  (sql/db-do-commands database
                      (sql/create-table-ddl
                       :influencers
                       [[:id "SERIAL"]
                        [:user_id "varchar(200)"]
                        [:username "varchar(200)"]
                        [:game "varchar(200)"]
                        [:views "varchar(200)"]
                        [:language "varchar(200)"]
                        [:timestamp "TIMESTAMP"]])))

(defqueries "influencers.sql" {:connection database})

(find-influencers)

(create-influencer {:name "bar" :game "wow" :views "1000" :language "en" :timestamp (str  (new java.util.Date))})

(influencers-count)

(influencer-by-id {:id 2})

(create-influencer 
 {:user_id (twitchapi/get-streams-top-id)
  :username (twitchapi/get-streams-top-name) 
  :game (twitchapi/get-streams-top-gamename) 
  :views (twitchapi/get-stream-viewcount) 
  :language (twitchapi/get-stream-language)})

(influencer-order-by-timestamp)

(influencer-last-7-days)

(change-timestamp {:id 2})


;; loop in background collecting data from twitch api
(defn set-interval [callback ms]
  (future (while true (do (Thread/sleep ms) (callback)))))

;; start work in background
(def job (set-interval 
         (create-influencer
          {:user_id (twitchapi/get-streams-top-id)
           :username (twitchapi/get-streams-top-name)
           :game (twitchapi/get-streams-top-gamename)
           :views (twitchapi/get-stream-viewcount)
           :language (twitchapi/get-stream-language)}) 900000))

;; cancel background job
(future-cancel job)