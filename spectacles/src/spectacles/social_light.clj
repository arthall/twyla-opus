(ns spectacles.social-light
  (:require [quil.core :as q]
            [quil.middleware :as m])
  (:import [wordcram WordCram]))

(defn setup []
  (q/color-mode :hsb)
  (q/background 0)
  (q/frame-rate 2)

  (let [wc (WordCram. (quil.applet/current-applet))]
    (-> wc
        ;;(.fromWebPage "https://twitter.com/WinCityMain")
        ;;(.fromTextString (into-array String ["Life In Lights Winchester"]))
        (.fromTextFile "resources/posts.txt")
        (.withColors (int-array [(q/color 220) (q/color 140) (q/color (q/random 255) 240 200)]))
        (.sizedByWeight 5 80)
        (.withFont"Copse")
        (.angledBetween (float -1.6) (float 1.6)))
    {:wordcram wc})
  )

(defn update-state [state] state)

(defn draw-state [state]
  (if (.hasMore (:wordcram state))
    (.drawNext (:wordcram state)))
  state)

(q/defsketch spectacles
  :title "Social Light"
  :size [800 600]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode]
  :features [:keep-on-top :present :no-start]
  :bgcolor 0)


