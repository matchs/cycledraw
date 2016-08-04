(ns cycledraw.cards
  (:require [cljs.test :refer-macros [is testing]]
            [cycledraw.components :as comp]
            [reagent.core :as reagent :refer [atom]]
            [devtools.core :as devtools])
  (:require-macros
   [devcards.core
    :as dc
    :refer [defcard defcard-doc defcard-rg deftest]]))

(deftest parse-color-rgb-test
  "## convert from color object to rgb"
  (testing "all black"
    (is (= (comp/parse-color-rgb {:r 0 :g 0 :b 0}) "rgb(0,0,0)"))))

(deftest parse-color-hes-test
  "## convert from color object to hex"
  (testing "all black"
    (is (= (comp/parse-color-hex {:r 0 :g 0 :b 0}) "#000000"))))

(def color-selector-state (atom {:chosen-color ""
                                 :switched-color ""}))
(defcard-rg color-selector
  "*Color selector*"
  (fn [{selected-color-id  :selected-color-id
        change-color-handler :change-color-handler}] (let [selector (comp/color-selector selected-color-id change-color-handler)]
                                                       [:div [selector "#000000" 0] [:p (:chosen-color @color-selector-state)]]))
  {:selected-color-id 0
   :change-color-handler (fn [ev] (swap! color-selector-state update-in [:chosen-color] (fn [] (-> ev .-target .-value))))})

(reagent/render [:div] (.getElementById js/document "app"))


;; remember to run 'lein figwheel devcards' and then browse to
;; http://localhost:3449/cards
