(ns influencerstracker.views.index
  (:require [hiccup.core :refer [html]]
            [hiccup.form]
            [hiccup.page :as page]
            [hiccup.table :refer :all]
            [influencerstracker.twitchapi :refer :all]
            [yesql.core :refer [defquery defqueries]]
            [influencerstracker.db :refer :all]
            [clj-time.format :as f]))

;;(f/unparse (f/formatters :date) (clj-time.coerce/from-long 893362442345))

(defn homepage 
  [title]
     (hiccup.table/to-table1d (influencerstracker.db/find-influencers)
                               [:username "Username" :game "Game" :views "Peak Views" :timestamp "Time"] {:table-attrs {:class "table"}}))
