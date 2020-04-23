(ns PMHunt.covid19-dashboard
  (:require [oz.core :as oz]))

(oz/start-server!)

;; Rework Oz examples into something for Covid data

(defn mock-data-set
  "generates some mock data for each location name

  Arguments: names as strings, names used as keys
  Returns: sequence of maps, each representing confirmed cases"
  [& locations]
  (for [location locations
        day (range 20)]
    {:day day
     :location location
     :cases (+ (Math/pow (* day (count location)) 0.8)
               (rand-int (count location)))}))

(def line-plot
  {:data {:values (mock-data-set "England" "Wales" "Scotland" "Northern Ireland")}
   :encoding {:x {:field "day" :type "quantitative"}
              :y {:field "cases" :type "quantitative"}
              :color {:field "location" :type "nominal"}}
   :mark "line"})

;; Render the plot
(oz/view! line-plot)

(def stacked-bar
  {:data {:values (mock-data-set "England" "Wales" "Scotland" "Northern Ireland")}
   :mark "bar"
   :encoding {:x {:field "day"
                  :type "ordinal"}
              :y {:aggregate "sum"
                  :field "cases"
                  :type "quantitative"}
              :color {:field "location"
                      :type "nominal"}}})

(oz/view! stacked-bar)
