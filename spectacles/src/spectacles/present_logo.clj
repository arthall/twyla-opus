(ns spectacles.present-logo
  (:require [quil.core :as q]
            [clj-time.core :as t]))

(defn setup []
  (q/color-mode :hsb)
  (q/background 0)
  (q/frame-rate 24)

  {:logo (q/load-image "resources/images/lifeinlights-logo.png")
   :message ""})

(defn update [state]
  (let [period (.toPeriod (t/interval (t/now) (t/date-time 2016 2 11 18 0)))]
    (assoc state :message (str (t/in-days period) ":"
                               (.getHours period) ":"
                               (.getMinutes period) ":"
                               (.getSeconds period) )) ) )

(defn draw [state]
  (q/background 0)
  (q/image (:logo state) (- (/ (q/width) 2) 100) (- (/ (q/height) 2) 100))
  (q/stroke 255)
  (q/fill 255)
  (q/text-font (q/create-font "monofur" 56))
  (q/text (:message state)
          (- (/ (q/width) 2) (/ (q/text-width (:message state)) 2))
          (+ (/ (q/height) 2) 200))
  )

;; (q/defsketch present-logo
;;   :title "Present Logo"
;;   :size [1280 720]

;;   :setup setup
;;   :update update
;;   :draw draw

;;   :middleware [quil.middleware/fun-mode]
;;                                         ;  :features [:keep-on-top :present]
;;   :bgcolor "#000000")
