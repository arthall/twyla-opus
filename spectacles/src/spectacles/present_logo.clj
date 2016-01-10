(ns spectacles.present-logo
  (:require [quil.core :as q]))

(defn setup []
  (q/color-mode :hsb)
  (q/background 0)
  (q/frame-rate 1) {:logo (q/load-image "resources/images/lifeinlights-logo.png")})

(defn update-state [state] state)

(defn draw-state [state]
  (q/image (:logo state) (- (/ (q/width) 2) 100) (- (/ (q/height) 2) 100)))
