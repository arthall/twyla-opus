(ns spectacles.presenter
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [spectacles.present-logo]))

(q/defsketch spectacles
  :title "Presentation Cycle"
  :size [1280 720]
                                        ; setup function called only once, during sketch initialization.
  :setup spectacles.present-logo/setup
                                        ; update-state is called on each iteration before draw-state.
  :update spectacles.present-logo/update-state
  :draw spectacles.present-logo/draw-state
  :middleware [m/fun-mode]
  :features [:keep-on-top :present]
  :bgcolor "#000000")

(defn -main
  "Main NO-OP entry point"
  []
  (println "Presentation started..."))
