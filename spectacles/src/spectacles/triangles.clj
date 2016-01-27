(ns spectacles.triangles
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/smooth)
  (q/frame-rate 4)
  {})

(defn draw [state]
  ;; (q/background 255)
  (q/no-stroke)
  (q/fill 255 255 255 10)
  (q/rect 0 0 (q/width) (q/height))
  (q/fill (mod (* 20 (q/frame-count)) 200)
          (mod (* 30 (q/frame-count)) 150)
          (mod (* 10 (q/frame-count)) 255)
          30)
  (q/translate (/ (q/width) 2) (/ (q/height) 2))
  (q/rotate (* (q/frame-count) (/ Math/PI 5)))
  (let [distance (/ (q/height) 3)]
    (q/triangle 0 0 (- (/ distance 2)) distance (/ distance 2) distance))
  state)

(defn update-state [state]
  state)

(q/defsketch practice
  :title "Triangles"
  :size [640 480]
  :setup setup
  :draw draw
  :update update-state
  :features [:keep-on-top]
  :middleware [m/fun-mode m/navigation-2d])

