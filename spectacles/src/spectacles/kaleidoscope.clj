; This file is a practice example of using translate to rotate the screen as
; the dots are drawn

(ns spectacles.kaleidoscope
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def max_diameter 150)
(def min_diameter 15)
(def max_speed 10)

(defn random-int [start stop]
  (let [x (rand-int (- stop start))]
    (+ x start)))

(defn new_dot []
  (let [xspeed (* (rand-nth '(1 -1)) (rand-int max_speed))
        yspeed (if (= xspeed 0)
                 (* (rand-nth '(1 -1)) (random-int 1 max_speed))
                 (* (rand-nth '(1 -1)) (rand-int max_speed)))]
    {:x 0
     :y 0
     :xspeed xspeed
     :yspeed yspeed
     :diameter (random-int min_diameter max_diameter)
     :color [(rand-int 255) (rand-int 255) (rand-int 255) (random-int 10 200)]}))

(defn setup []
  (q/background 0)
  (q/smooth)
  (q/frame-rate 60)
  {:ldots (list (new_dot))
   :rdots (list (new_dot))})

(defn draw [state]
  ;; (q/background 0)
  (q/no-stroke)
  (q/fill 0 0 0 25)
  (q/rect 0 0 (q/width) (q/height))
  (q/push-matrix)
  (q/translate 300 300)
  ;; (q/translate (/ (q/width) 2) (/ (q/height) 2))
  (q/rotate (q/radians (mod (* (q/frame-count) 2) 360)))
  (let [dots (:ldots state)
        items (count (:ldots state))]
    (dotimes [n items]
      (let [dot (nth dots n)]
        (apply q/fill (:color dot))
        ;;(q/ellipse (:x dot) (:y dot) (:diameter dot) (:diameter dot)))))
        (q/rect (:x dot) (:y dot) (:diameter dot) (:diameter dot)))))
  (q/pop-matrix)
  (q/push-matrix)
  ;; (q/translate 300 300)
  (q/translate (/ (q/width) 2) (/ (q/height) 2))
  (q/rotate (- (q/radians (mod (* (q/frame-count) 2) 360))))
  (let [dots (:rdots state)
        items (count (:rdots state))]
    (dotimes [n items]
      (let [dot (nth dots n)]
        (apply q/fill (:color dot))
        (q/ellipse (:x dot) (:y dot) (:diameter dot) (:diameter dot)))))
  (q/pop-matrix)
  )

(defn update-position [m]
  (let [x (update-in m [:x] + (:xspeed m))]
    (update-in x [:y] + (:yspeed m))))

(defn add-dot [m]
  (if (< 50 (rand-int 100))
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

