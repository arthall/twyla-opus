; This file is a practice example of using translate to rotate the screen as
; the dots are drawn

(ns spectacles.kaleidoscope
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def max_diameter 150)
(def min_diameter 15)
(def max_speed 10)

(defn new_dot []
  (let [xspeed (* (rand-nth '(1 -1)) (q/random max_speed))
        yspeed (if (= xspeed 0)
                 (* (rand-nth '(1 -1)) (q/random 1 max_speed))
                 (* (rand-nth '(1 -1)) (q/random max_speed)))]
    {:x 0
     :y 0
     :xspeed xspeed
     :yspeed yspeed
     :diameter (q/random min_diameter max_diameter)
     :color [(q/random 256) (q/random 256) (q/random 256) (q/random 10 200)]}))

(defn setup []
  (q/background 0)
  (q/smooth)
  (q/frame-rate 60)
  {:ldots (list (new_dot))
   :rdots (list (new_dot))
   :logo (q/load-image "resources/images/lifeinlights-logo.png")})

(defn draw-dots [x y direction dots func]
  (q/push-matrix)
  (q/translate x y)
  (q/rotate (* direction (q/radians (mod (* (q/frame-count) 2) 360))))

  (let [items (count dots)]
    (dotimes [n items]
      (let [dot (nth dots n)]
        (apply q/fill (:color dot))
        (func (:x dot) (:y dot) (:diameter dot) (:diameter dot)))))
  (q/pop-matrix))

(defn draw [state]
  (q/no-stroke)
  (q/fill 0 0 0 25)
  (q/rect 0 0 (q/width) (q/height))
  (q/image (:logo state) (- (/ (q/width) 2) 100) (- (/ (q/height) 2) 100))
  (draw-dots 300 300 1 (:ldots state) q/rect)
  (draw-dots (/ (q/width) 2) (/ (q/height) 2) -1 (:rdots state) q/ellipse))

(defn update-position [m]
  (-> m
      (update-in [:x] + (:xspeed m))
      (update-in [:y] + (:yspeed m))))

(defn add-dot [m]
  (if (< 50 (q/random 100))
    (conj m (new_dot)) ; Add a new dot
    m)) ; Do nothing and return the original list

(defn remove-dots [m]
  (filter #(let [{:keys [x y diameter]} %
                 radious (* 0.5 diameter)
                 limit (+ (max (q/height) (q/width)) radious)]
             (and
              (< (- limit) x limit)
              (< (- limit) y limit))) m))

(defn update-dots [m]
  (map update-position (remove-dots (add-dot m))))

(defn update [state]
  (let [{:keys [ldots rdots]} state]
    (assoc state :ldots (update-dots ldots)
                 :rdots (update-dots rdots))))

(q/defsketch practice
    :title "Kaleidoscope"
    :size :fullscreen
    :setup setup
    :draw draw
    :update update
    :features [:keep-on-top]
    :middleware [m/fun-mode m/navigation-2d])

