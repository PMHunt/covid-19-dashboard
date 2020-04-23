(ns PMHunt.covid19-dashboard
  (:require [oz.core :as oz]))

(oz/start-server!)

;; Oz examples

(defn play-data [& names]
  (for [n names
        i (range 20)]
    {:time i :item n :quantity (+ (Math/pow (* i (count n)) 0.8) (rand-int (count n)))}))

(def line-plot
  {:data {:values (play-data "monkey" "slipper" "broom")}
   :encoding {:x {:field "time" :type "quantitative"}
              :y {:field "quantity" :type "quantitative"}
              :color {:field "item" :type "nominal"}}
   :mark "line"})

;; Render the plot
(oz/view! line-plot)

(def stacked-bar
  {:data {:values (play-data "munchkin" "witch" "dog" "lion" "tiger" "bear")}
   :mark "bar"
   :encoding {:x {:field "time"
                  :type "ordinal"}
              :y {:aggregate "sum"
                  :field "quantity"
                  :type "quantitative"}
              :color {:field "item"
                      :type "nominal"}}})

(oz/view! stacked-bar)


;; load some example vega
;; (this may only work from within a checkout of oz haven't checked)

(require '[cheshire.core :as json])

(def contour-plot (oz/load "contour-lines.vega.json"))

(oz/view! contour-plot :mode :vega)

;; end Oz examples

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
