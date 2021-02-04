(ns twitchapi
  (:require [auth :refere :all]
            [clojure.data.json :as json]
            [clj-http.client :as http]
            [clojure.string :as s]))

(def baseURL "https://api.twitch.tv/helix/")

(defn get-top-games []
  (json/read-str
   (:body
    (http/get (str baseURL "games/top")
              {:headers
               {:client-id auth/client-id
                :Authorization (str "Bearer " auth/access_token)
                :content-type auth/content-type}}))))

(defn get-top-game []
  (get-in (get-top-games) ["data" 0]))

(defn get-game-by-id [id]
  (json/read-str
   (:body
    (http/get (str baseURL "games?id=" id)
              {:headers
               {:client-id auth/client-id
                :Authorization (str "Bearer " auth/access_token)
                :content-type auth/content-type}}))))

(defn search-categories [category]
  (json/read-str
   (:body
    (http/get (str baseURL "search/categories?query=" category)
              {:headers
               {:client-id auth/client-id
                :Authorization (str "Bearer " auth/access_token)
                :content-type auth/content-type}}))))

(defn search-channels [channel live]
 (json/read-str
   (:body
    (http/get (str baseURL "search/channels?query=" channel "&live_only=" live)
              {:headers
               {:client-id auth/client-id
                :Authorization (str "Bearer " auth/access_token)
                :content-type auth/content-type}}))))

(defn get-streams [game_id]
  (json/read-str
   (:body
    (http/get (str baseURL "streams?game_id=" game_id)
              {:headers
               {:client-id auth/client-id
                :Authorization (str "Bearer " auth/access_token)
                :content-type auth/content-type}}))))

(defn get-user [user_id]
  (json/read-str
   (:body
    (http/get (str baseURL "users?id=" user_id)
              {:headers
               {:client-id auth/client-id
                :Authorization (str "Bearer " auth/access_token)
                :content-type auth/content-type}}))))