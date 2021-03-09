(ns influencerstracker.auth
  (:require [clojure.data.json :as json]
            [clj-http.client :as http]
            [clojure.string :as s]))

(def oauth2 "https://id.twitch.tv/oauth2/token?")
(def grant-type "&grant_type=client_credentials")
(def client-id "51fsw18ny5mvqfbc93tbmcpqdxx8w4")
(def client-secret "ph21a7svnn3b6zw29kpa5or163yn69")
;; url for auth
(def get-token (str oauth2 "client_id=" client-id "&client_secret=" client-secret grant-type))
(def content-type "application/json")

(def access_token
  "Get access_token"
  (get (json/read-str (:body (http/post get-token))) "access_token"))

