(ns PMHunt.covid19-dashboard
  (:require [oz.core :as oz]
            [clojure.string :as s]))

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
              :y {:aggregate "location"
                  :field "cases"
                  :type "quantitative"}
              :color {:field "location"
                      :type "nominal"}}})

(oz/view! stacked-bar)

;; now lets use Hiccup style syntax to define a dashboard using these views

(def dashboard
  [:div
   [:h1 "Covid19 Tracker - mock data"]
   [:p "mock data to experiment with different views"]
   [:div {:style {:display "flex" :flex-direction "column"}}
    [:vega-lite line-plot]
    [:vega-lite stacked-bar]]])

(oz/view! dashboard)

;; now let's try it with some real data

(require '[clojure.java.io :as io])
(require '[clojure.data.csv :as csv])

;; debugging classpath issue with io/resource
(require '[clojure.java.classpath :as cp])
(cp/classpath)

(def covid-uk-daily-indicators
  (csv/read-csv
   (slurp
    (io/resource "data/daily-indicators.csv"))))

(def covid-uk-cases
  (csv/read-csv
   (slurp
    (io/resource "data/coronavirus-cases_latest.csv"))))

(def covid-uk-deaths
  (csv/read-csv
   (slurp
    (io/resource "data/coronavirus-deaths_latest.csv"))))

(def covid-uk-daily-indicators
  (csv/read-csv
   (slurp
    (io/resource "data/daily-indicators.csv"))))

(def covid-uk-daily-indicators-map
  (zipmap (first covid-uk-daily-indicators) (second covid-uk-daily-indicators)))

(def dashboard-headlines
  [:div
   [:h1 "Covid19 Tracker - real headline data"]
   [:p (str "Headline figures for: " (get covid-uk-daily-indicators-map "DateVal"))]
   [:div {:style {:display "flex" :flex-direction "column"}}
    [:h2 (str "Total UK Cases: " (get covid-uk-daily-indicators-map "TotalUKCases"))]
    [:h2 (str "England Cases: " (get covid-uk-daily-indicators-map "EnglandCases") )]
    [:h2 (str "Wales Cases: " (get covid-uk-daily-indicators-map "WalesCases"))]]
   [:div {:style {:display "flex" :flex-direction "column"}}
    [:vega-lite line-plot]
    [:vega-lite stacked-bar]]])

(oz/view! dashboard-headlines)


(def covid-uk-deaths-map
  (for [x (range (count covid-uk-deaths))]
    (zipmap (first covid-uk-deaths) (nth covid-uk-deaths x))))


(def covid-uk-cases-map
  (for [x (range (count covid-uk-cases))]
    (zipmap (first covid-uk-cases) (nth covid-uk-cases x))))

(def line-plot-real
  {:data {:values covid-uk-deaths-map}
   :encoding {:x {:field "Reporting date" :type "quantitative"}
              :y {:field "Daily hospital deaths" :type "quantitative"}
              :color {:field "Area name" :type "nominal"}}
   :mark "line"})

;; Render the plot
(oz/view! line-plot-real)

(def stacked-bar
  {:data {:values (mock-data-set "England" "Wales" "Scotland" "Northern Ireland")}
   :mark "bar"
   :encoding {:x {:field "day"
                  :type "ordinal"}
              :y {:aggregate "location"
                  :field "cases"
                  :type "quantitative"}
              :color {:field "location"
                      :type "nominal"}}})

(oz/view! stacked-bar)




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
              :y {:aggregate "location"
                  :field "cases"
                  :type "quantitative"}
              :color {:field "location"
                      :type "nominal"}}})

(oz/view! stacked-bar)

;; now lets use Hiccup style syntax to define a dashboard using these views

(def dashboard
  [:div
   [:h1 "Covid19 Tracker - mock data"]
   [:p "mock data to experiment with different views"]
   [:div {:style {:display "flex" :flex-direction "column"}}
    [:vega-lite line-plot]
    [:vega-lite stacked-bar]]])

(oz/view! dashboard)

;; now let's try it with some real data

(require '[clojure.java.io :as io])
(require '[clojure.data.csv :as csv])

;; debugging classpath issue with io/resource
(require '[clojure.java.classpath :as cp])
(cp/classpath)

(def covid-uk-daily-indicators
  (csv/read-csv
   (slurp
    (io/resource "data/daily-indicators.csv"))))

(def covid-uk-cases
  (csv/read-csv
   (slurp
    (io/resource "data/coronavirus-cases_latest.csv"))))

(def covid-uk-deaths
  (csv/read-csv
   (slurp
    (io/resource "data/coronavirus-deaths_latest.csv"))))

(def covid-uk-daily-indicators
  (csv/read-csv
   (slurp
    (io/resource "data/daily-indicators.csv"))))

(def covid-uk-daily-indicators-map
  (zipmap (first covid-uk-daily-indicators) (second covid-uk-daily-indicators)))

(def dashboard-headlines
  [:div
   [:h1 "Covid19 Tracker - real headline data"]
   [:p (str "Headline figures for: " (get covid-uk-daily-indicators-map "DateVal"))]
   [:div {:style {:display "flex" :flex-direction "column"}}
    [:h2 (str "Total UK Cases: " (get covid-uk-daily-indicators-map "TotalUKCases"))]
    [:h2 (str "England Cases: " (get covid-uk-daily-indicators-map "EnglandCases") )]
    [:h2 (str "Wales Cases: " (get covid-uk-daily-indicators-map "WalesCases"))]]
   [:div {:style {:display "flex" :flex-direction "column"}}
    [:vega-lite line-plot]
    [:vega-lite stacked-bar]]])

(oz/view! dashboard-headlines)

(defn fix-blank-day [v]
  (if (s/blank? (nth v 4)) (assoc v 4 "0") v))

(defn fix-blank-cum [v]
  (if (s/blank? (nth v 5)) (assoc v 5 "0") v))

(defn fix-blanks-day [data]
  (map fix-blank-day data))

(defn fix-blanks-cum [data]
  (map fix-blank-cum data))

(def covid-uk-deaths-noblanks
  (fix-blanks-day (fix-blanks-cum covid-uk-deaths)))

(def covid-uk-deaths-num
  (map (fn [[an ac at rd dhd chd]]
         [an ac at rd (Long/parseLong dhd) (Long/parseLong chd)])
       (rest covid-uk-deaths-noblanks)))

(def covid-uk-deaths-zip
  (for [x (range (count covid-uk-deaths))]
    (zipmap (first covid-uk-deaths) (nth covid-uk-deaths-num x))))





(oz/start-server!)


;; TODO figure out how to deal with date strings
(def line-plot-real-deaths
  {:data {:values (take 100 covid-uk-deaths-zip)}
   :encoding {:x {:field "Reporting date" :timeUnit "date" :type "temporal"}
              :y {:field "Daily hospital deaths" :type "quantitative"}
              :color {:field "Area name" :type "nominal"}}
   :mark "line"})

;; Render the plot
(oz/view! line-plot-real-deaths)


(def stacked-bar
  {:data {:values covid-uk-deaths-zip}
   :mark "bar"
   :encoding {:x {:field "Reporting date"
                  :timeUnit "date"
                  :type "temporal"}
              :y {:aggregate "Area name"
                  :field "Daily hospital deaths"
                  :type "quantitative"}
              :color {:field "Area name"
                      :type "nominal"}}})

(oz/view! stacked-bar)
