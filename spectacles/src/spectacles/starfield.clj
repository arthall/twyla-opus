;; This file is a practice example of using translate to move the origin (0,0)
;; to the center of the screen and rotate to move the x-axis around the new
;; origin. This simplifies the drawing of the lines since there is no longer
;; a Y component to track.  It also uses scale to fit more items on the screen.

(ns spectacles.starfield
  (:require [quil.core :as q]
            [quil.middleware :as m]))

;; These values can be modified to produce changes to the sketch. 
(def max_length 150) ; Maximum starting length of a line
(def min_length 1) ; Minimum starting length of a line
(def max_speed 15) ; Maximum speed for a line
(def min_blue 100)
(def max_blue 256)
(def min_red 200)
(def max_red 256)
(def min_green 200)
(def max_green 256)
(def min_alpha 100)
(def max_alpha 200)
(def starting_x_value 10) ; Distance from the origin for the line

(def stroke_weight 5)
(def background_color 0) ; Background color for the sketch
(def frame_rate 60)
(def over_sampling 2) ; This affects the smoothing.  0 will remove smoothing.
(def reduction 4) ; Change this to modify the scaling.
(def chance_of_new_line 0.75)

(def degrees_in_circle 360)

(defn random-int [start stop]
  (let [x (rand-int (- stop start))]
    (+ x start)))

(defn new_line []
  (let [speed (random-int 1 max_speed)]
    {:x starting_x_value
     :speed speed
     :length (random-int min_length max_length)
     :color [(random-int min_red max_red)
             (random-int min_green max_green)
             (random-int min_blue max_blue)
             (random-int min_alpha max_alpha)]
     :angle (rand-int degrees_in_circle)}))

(defn setup []
  (q/frame-rate frame_rate)
  {:lines (list (new_line))})

(defn draw-lines [lines]
  (let [items (count lines)]
    (dotimes [n items]
      (let [line (nth lines n)
            {x :x length :length color :color angle :angle} line
            y 0]
        (q/push-matrix)
        (q/rotate (q/radians (mod angle degrees_in_circle)))
        (apply q/stroke color)
        (q/line x y (+ x length) y)
        (q/pop-matrix)))))

(defn draw-state [state]
  (q/background background_color)
  (q/stroke-weight stroke_weight)
  (q/push-matrix)
  (let [new_x (/ (q/width) 2)
        new_y (/ (q/height) 2)
        scale (/ 1 reduction)]
    (q/translate new_x new_y)
    (q/scale scale scale))
  (draw-lines (:lines state))
  (q/pop-matrix))

(defn update-position [m]
  (-> m
      (update-in [:length] inc)
      (update-in [:x] + (:speed m))))

(defn add-lines [m]
  (if (< (rand) chance_of_new_line)
    (conj m (new_line)) ; Add a new line
    m)) ; Do nothing and return the original list

(defn remove-lines [m]
  (filter #(let [x (:x %)
                 limit (* reduction (max (q/height) (q/width)))]
              (< 0 x limit)) m))

(defn update-lines [m]
  (map update-position (remove-lines (add-lines m))))

(defn update-state [state]
  (let [lines (:lines state)]
    (assoc state :lines (update-lines lines))))

;; (q/defsketch spectacles
;;   :title "Star Field"
;;   :size [960 480]
;;   ;; Calling q/no-smooth results in a runtime error
;;   :settings (partial q/smooth over_sampling)
;;   :setup setup
;;   :draw draw-state
;;   :update update-state
;;   :features [:keep-on-top]
;;   :middleware [m/fun-mode m/navigation-2d])

