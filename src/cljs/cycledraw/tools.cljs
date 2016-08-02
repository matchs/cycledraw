(ns cycledraw.tools
  (:require [reagent.core :as reagent :refer [atom]]))

(defn pencil [selected-color-id canvas] (fn [x y color-id] (swap! canvas update-in[x y] (fn [_] selected-color-id))))
