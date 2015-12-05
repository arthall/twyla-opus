(ns spectacles.post-map
  (:require [quil.core :as q]
            [quil.middleware :as m])
  (:import [wordcram WordCram]))

(defn setup []
  (q/color-mode :hsb)
  (let [wc (WordCram. (quil.applet/current-applet))]
    (-> wc
        ;;(.fromWebPage "https://twitter.com/WinCityMain")
        ;;(.fromTextString (into-array String ["Life In Lights Winchester"]))
        (.fromTextFile "resources/posts.txt")
        (.withColors (int-array [(q/color 30) (q/color 110) (q/color (q/random 255) 240 200)]))
        (.sizedByWeight 5 120)
        (.withFont"Copse")
        (.angledBetween (float -1.6) (float 1.6))
        (.drawAll))))

(defn update-state [state] {})

(defn draw-state [state] {})

(q/defsketch spectacles
  :title "Social Light"
  :size [1000 600]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode]
  ;;  :features [:keep-on-top :present :no-start]
;;  :features [:no-start]
  :bgcolor 0)


