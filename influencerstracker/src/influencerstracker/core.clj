(ns influencerstracker.core
  (:require [clojure.java.jdbc :as sql]
            [clj-time.core :as t]
            [clj-time.coerce :as c]
            [clj-time.local :as l]
            [yesql.core :refer [defquery defqueries]]))

(def db {:subprotocol "postgresql"
         :subname "//localhost/influencers"
         :user "postgres"})

; id SERIAL-autoincrement :name, :game, :views, :language, :timestamp
(defn create-influencers-table! []
  (sql/db-do-commands db
                      (sql/create-table-ddl
                       :influencers
                       [[:id "SERIAL"]
                        [:name "varchar(200)"]
                        [:game "varchar(200)"]
                        [:views "varchar(200)"]
                        [:language "varchar(200)"]
                        [:timestamp "varchar(200)"]])))

(defqueries "influencers.sql" {:connection db})

(find-influencers)

(create-influencer {:name "bar" :game "wow" :views "1000" :language "en" :timestamp (str  (new java.util.Date))})

(influencers-count)