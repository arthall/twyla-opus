;; Example of using translate and rotate. Draws the Live In Lights logo.

(ns spectacles.kaleidoscope
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn new_dot []
  (let [xspeed (* (rand-nth '(1 -1)) (q/random (:max_speed (q/state))))
        yspeed (if (= xspeed 0)
                 (* (rand-nth '(1 -1)) (q/random 1 (:max_speed (q/state))))
                 (* (rand-nth '(1 -1)) (q/random (:max_speed (q/state)))))]
    {:x 0
     :y 0
     :xspeed xspeed
     :yspeed yspeed
     :size (q/random (:min_size (q/state)) (:max_size (q/state)))}))

(defn setup []
  (q/frame-rate 5)
  {:max_size 200
   :min_size 50
   :max_speed 10
   :ldots (list)
   :rdots (list)
   :logo (q/load-image "resources/images/lifeinlights-logo.png")})

(defn draw-dots [x y direction dots func]
  (q/push-matrix)
  (q/translate x y)
  (q/rotate (* direction (q/radians (mod (* (q/frame-count) 2) 360))))

  (let [items (count dots)]
    (dotimes [n items]
      (let [dot (nth dots n)]
        (func (:x dot) (:y dot) (:size dot) (:size dot)))))
  (q/pop-matrix))

(defn draw [state]
  (q/background 0)

  ;; Draw the the particles
  (draw-dots 400 400 1 (:ldots state) (partial q/image (:logo state)))
  (draw-dots (/ (q/width) 2) (/ (q/height) 2) -1 (:rdots state)
             (partial q/image (:logo state))))

(defn update-position [m]
  (-> m
      (update-in [:x] + (:xspeed m))
      (update-in [:y] + (:yspeed m))))

(defn add-dot [m]
  (if (> 5 (q/random 100))
    (conj m (new_dot)) ; Add a new dot
    m)) ; Do nothing and return the original list

(defn remove-dots [m]
  (filter #(let [{:keys [x y size]} %
                 half_size (* 0.5 size)
                 limit (+ (max (q/height) (q/width)) half_size)]
             (and
              (< (- limit) x limit)
              (< (- limit) y limit))) m))

(defn update-dots [m state]
  (map update-position (remove-dots (add-dot m))))

(defn update [state]
  (let [{:keys [ldots rdots]} state]
    (assoc state :ldots (update-dots ldots state)
                 :rdots (update-dots rdots state))))

(q/defsketch practice
    :title "Kaleidoscope-logo"
    :size [1280 720]
    :setup setup
    :draw draw
    :update update
    :features []
    :middleware [m/fun-mode m/navigation-2d])

