(ns spectacles.presenter
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [spectacles.scheduler]))

(q/defsketch spectacles
  :title "Presentation Cycle"
  :size :fullscreen

  :setup spectacles.scheduler/setup
  :update spectacles.scheduler/update
  :draw spectacles.scheduler/draw

  :middleware [m/fun-mode]
  :features [:keep-on-top :present]
  :bgcolor "#000000")

(defn -main
  "Main NO-OP entry point"
  []
  (println "Presentation started..."))
