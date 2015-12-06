(ns spectacles.present-logo
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/color-mode :hsb)
  (q/background 0)
  (q/frame-rate 4) {:logo (q/load-image "resources/images/lifeinlights-logo.png")})

(defn update-state [state] state)

(defn draw-state [state]
  (q/image (:logo state) (- (/ (q/width) 2) 100) (- (/ (q/height) 2) 100)))

(q/defsketch spectacles
  :title "Presentation Cycle"
  :size [1280 720]
                                        ; setup function called only once, during sketch initialization.
  :setup setup
                                        ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode]
;;  :features [:keep-on-top :present :no-start]
  :bgcolor 0)
