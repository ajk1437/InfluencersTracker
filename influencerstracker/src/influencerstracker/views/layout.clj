  (ns influencerstracker.views.layout
  (:use [hiccup.page :only (html5 include-css include-js)]))

(defn- navigation-top
  []
    [:nav {:class "navbar navbar-light bg-light"}
     [:a {:class "navbar-brand" :href "#"}
      [:img {:src "/docs/4.0/assets/brand/bootstrap-solid.svg" :width "30" :height "30" :alt ""}]]
     [:a {:class "navbar-brand" :href "#"} "Games"]
     [:a {:class "navbar-brand" :href "#"} "Language"]])

(defn application [title & content]
  (html5 {:ng-app "myApp" :lang "en"}
         [:head
          [:title title]
          (include-css "https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css")
          ;; (include-js "http://code.angularjs.org/1.2.3/angular.min.js")
          ;; (include-js "js/ui-bootstrap-tpls-0.7.0.min.js")
          ;; (include-js "js/script.js")

          [:body
           (navigation-top)
           [:div]
           [:div {:class "container"} content]]]))