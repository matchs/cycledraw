(ns cycledraw.cards
  (:require [cycledraw.components :refer [color-selector
                                          color-manager
                                          pallette-manager
                                          tool-manager
                                          pallette-frame-manager
                                          canvas-frame-manager]]
            [reagent.core :as reagent :refer [atom]]
            [devtools.core :as devtools])
  (:require-macros
   [devcards.core
    :as dc
    :refer [defcard defcard-doc defcard-rg deftest]]))

(devtools/install!)

(def color-selector-state (atom {:chosen-color ""
                                 :switched-color ""}))
(defcard-rg color-selector
  "*Color selector*"
  (fn [{selected-color-id  :selected-color-id
        change-color-handler :change-color-handler
        switch-color-handler :switch-color-handler}] [:div [color-selector selected-color-id change-color-handler switch-color-handler]
          [:p (:chosen-color @color-selector-state)]
          [:p (:switched-color @color-selector-state)]])
  {:selected-color-id 0
   :change-color-handler (fn [] (fn [ev] (swap! color-selector-state update-in [:chosen-color] (fn [] (-> ev .-target .-value )))))
   :switch-color-handler (fn [color-id] (fn [] (swap! color-selector-state update-in [:switched-color] (fn [] color-id))))})

(reagent/render [:div] (.getElementById js/document "app"))


;; remember to run 'lein figwheel devcards' and then browse to
;; http://localhost:3449/cards
