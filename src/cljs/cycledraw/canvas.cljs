(ns cycledraw.canvas
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.ratom :refer [reaction]]
            [cycledraw.tools :as tools]))

(def tools {
             :pencil tools/pencil
             })

(def app (atom {
                 :selected-tool :pencil
                 :selected-color-id 0
                 }))

(def work (atom  {:resolution {
                                :width 320
                                :height 280
                                }
                  :pallette-size 256
                  :pallettes []
                  :current-pallette-frame 0
                  :canvas []
                  :current-frame 0}))

(defn generate-default-pallette [{pallette-size :pallette-size}]
  (map #({:r %1 :g %1 :b %1}) (range 0 pallette-size)))


(defn generate-meta-canvas [{{width :width height :height} :resolution
                             pallette-size :pallette-size}]
  (map #([map #({:color-id 0}) (range 0 width)]) (range 0 height)))

(defn new-pallette! [work frame] (swap! work update-in [:pallettes framew] (generate-default-pallette @work)))
(defn new-meta-canvas! [work frame] (swap! work update-in [:canvas frame] (generate-meta-canvas @work)))

(defn initialize! [work] (new-pallette! work 0)
  (new-meta-canvas! 0)
  work)


(defn run-tool [x y color-id pallette {tool :tool}] (fn [_] ((:tool tools) x y color-id)))

(defn item-renderer [x y color-id tool canvas selected-color-id] (fn [] [:div.clickable {:on-click (tool x y color-id)}]))

(defn row-renderer [y tool] (fn [color-id x] [item-renderer x y color-id tool]))

(defn render [canvas pallette item-renderer row-renderer tool] (map-indexed (fn [row y] [:div.row (map-indexed (row-renderer y tool) row)]) canvas))

(defn renderize-canvas [work item-renderer row-renderer app tools] (let [{{width :width height :height} :resolution
                                                                          pallettes :pallettes
                                                                          current-pallette-frame :current-pallette-frame
                                                                          canvas :canvas
                                                                          current-frame :current-frame} work
                                                                         selected-pallette (nth current-pallette-frame pallette)
                                                                         selected-canvas (nth current-frame canvas)
                                                                         {selected-tool :selected-tool
                                                                          selected-color-id :selected-color-id} app
                                                                         tool ((get selected-tool tools) selected-color-id canvas)]
                                                                     (render selected-canvas selected-pallette item-renderer row-renderer tool)))

(defn canvas-page [] (let [initialized-work (initialize! work)] (fn [] (renderize-canvas initialized-work item-renderer row-renderer app tools))))
