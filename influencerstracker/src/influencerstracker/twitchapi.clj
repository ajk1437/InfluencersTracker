(ns influencerstracker.twitchapi
  (:require [influencerstracker.auth :refere :all]
            [clojure.data.json :as json]
            [clj-http.client :as http]
            [clojure.string :as s]))

(def baseURL "https://api.twitch.tv/helix/")

(defn timestamp [] 
  (new java.util.Date))

(defn get-top-games []
  "Return top 10 games with most viewers"
  (json/read-str
   (:body
    (http/get (str baseURL "games/top")
              {:headers
               {:client-id influencerstracker.auth/client-id
                :Authorization (str "Bearer " influencerstracker.auth/access_token)
                :content-type influencerstracker.auth/content-type}}))))

(defn get-top-game []
  (get-in (get-top-games) ["data" 0]))

(defn get-game-by-id [id]
  (json/read-str
   (:body
    (http/get (str baseURL "games?id=" id)
              {:headers
               {:client-id influencerstracker.auth/client-id
                :Authorization (str "Bearer " influencerstracker.auth/access_token)
                :content-type influencerstracker.auth/content-type}}))))

(defn get-top-game-name []
  (get-in (get-top-games) ["data" 0 "name"]))

(defn get-game-by-id [id]
  (json/read-str
   (:body
    (http/get (str baseURL "games?id=" id)
              {:headers
               {:client-id influencerstracker.auth/client-id
                :Authorization (str "Bearer " influencerstracker.auth/access_token)
                :content-type influencerstracker.auth/content-type}}))))

(defn search-categories [category]
  (json/read-str
   (:body
    (http/get (str baseURL "search/categories?query=" category)
              {:headers
               {:client-id influencerstracker.auth/client-id
                :Authorization (str "Bearer " influencerstracker.auth/access_token)
                :content-type influencerstracker.auth/content-type}}))))

(defn search-channels [channel live]
 (json/read-str
   (:body
    (http/get (str baseURL "search/channels?query=" channel "&live_only=" live)
              {:headers
                :Authorization (str "Bearer " influencerstracker.auth/access_token)
               {:client-id influencerstracker.auth/client-id
                :content-type influencerstracker.auth/content-type}}))))

(defn get-streams [game_id]
  (json/read-str
   (:body
    (http/get (str baseURL "streams?game_id=" game_id)
              {:headers
               {:client-id influencerstracker.auth/client-id
                :Authorization (str "Bearer " influencerstracker.auth/access_token)
                :content-type influencerstracker.auth/content-type}}))))

(defn get-streams-user [name]
  (json/read-str
   (:body
    (http/get (str baseURL "streams?user_id=" name)
              {:headers
               {:client-id influencerstracker.auth/client-id
                :Authorization (str "Bearer " influencerstracker.auth/access_token)
                :content-type influencerstracker.auth/content-type}}))))


(defn get-streams-top []
  (json/read-str
   (:body
    (http/get (str baseURL "streams")
              {:headers
               {:client-id influencerstracker.auth/client-id
                :Authorization (str "Bearer " influencerstracker.auth/access_token)
                :content-type influencerstracker.auth/content-type}}))))

(defn get-streams-top-id []
  (get-in (get-streams-top) ["data" 0 "user_id"]))

(defn get-streams-top-name []
  (get-in (get-streams-top) ["data" 0 "user_name"]))

(defn get-streams-top-gamename []
  (get-in (get-streams-top) ["data" 0 "game_name"]))

(defn get-streams-top-view []
  (get-in (get-streams-top) ["data" 0 "game_name"]))

(defn get-stream-viewcount []
  (get-in (get-streams-user (get-streams-top-id)) ["data" 0 "viewer_count"]))

(defn get-stream-language []
  (get-in (get-streams-user (get-streams-top-id)) ["data" 0 "language"]))


(defn get-channel-info [id]
  (json/read-str
   (:body
    (http/get (str baseURL "channels?broadcaster_id=" id)
              {:headers
               {:client-id influencerstracker.auth/client-id
                :Authorization (str "Bearer " influencerstracker.auth/access_token)
                :content-type influencerstracker.auth/content-type}}))))

(defn get-user [user_id]
  (json/read-str
   (:body
    (http/get (str baseURL "users?id=" user_id)
              {:headers
               {:client-id influencerstracker.auth/client-id
                :Authorization (str "Bearer " influencerstracker.auth/access_token)
                :content-type influencerstracker.auth/content-type}}))))

;; (get-in (get-top-games) ["data" 4 "id" "name"])

;; (for [x (range 0 9)] (get-in (get-top-games) ["data" x "name" ""]))

;; (def game-names (for [x (range 0 9)] (get-in (get-top-games) ["data" x "name"])))
;; (def game-ids (for [x (range 0 9)] (get-in (get-top-games) ["data" x "id"])))

