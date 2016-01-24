(ns spectacles.scheduler
  (:require [spectacles.present-logo]
            [spectacles.starfield]))

(def sketches [{:setup  spectacles.present-logo/setup
                :update spectacles.present-logo/update
                :draw   spectacles.present-logo/draw}

               {:setup  spectacles.starfield/setup
                :update spectacles.starfield/update-state
                :draw   spectacles.starfield/draw-state}])

(def active-sketch (atom {:sketch (first sketches)
                          :expiration :never}))

(defn setup []
  ;; determine which is the first sketch to be active and invoke its setup function
  ((:setup (:sketch @active-sketch))))

(defn update [state]
  ;; determine whether it's time to swap sketches
  ;; if not, invoke the active sketch's draw function
  ;; if so, swap to the new sketch's draw function, initializing with its setup function
  ((:update (:sketch @active-sketch)) state))

(defn draw [state]
  ;; invoke the active sketch's draw function
  ((:draw (:sketch @active-sketch)) state))
