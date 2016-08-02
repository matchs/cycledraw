(ns cycledraw.canvas
  (:require [reagent.core :as reagent :refer [atom]]
            [cycledraw.tools :as tools]
            [cycledraw.components :refer all]))

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
                  :bitmap []
                  :current-frame 0}))

(defn parse-color [{r :r g :g b :b}] (str "rgb(" r "," g "," b ")"))

(defn generate-default-pallette [{pallette-size :pallette-size}]
  (map #({:r %1 :g %1 :b %1}) (range 0 pallette-size)))


(defn generate-default-bitmap [{{width :width height :height} :resolution
                             pallette-size :pallette-size}]
  (map #([map #({:color-id 0}) (range 0 width)]) (range 0 height)))

(defn new-pallette! [work frame generate-pallete]
  (swap! work update-in [:pallettes frame] (generate-pallete work)))

(defn new-bitmap! [work frame generate-bitmap]
  (swap! work update-in [:bitmap frame] (generate-bitmap work)))

(defn initialize! [work]
   (let [ref-work @work]  (new-pallette! refwork 0 generate-default-pallette)
                          (new-bitmap! ref-work 0 generate-default-bitmap)
                          work))


(defn run-tool [x y color-id pallette {tool :tool}]
  (fn [_] ((:tool tools) x y color-id)))

(defn item-renderer [x y color-id tool bitmap selected-color-id]
  (fn [] [:div.clickable {:on-click (tool x y color-id)}]))

(defn row-renderer [y tool]
  (fn [color-id x] [item-renderer x y color-id tool]))

(defn render [bitmap pallette item-renderer row-renderer tool]
  (map-indexed (fn [row y] [:div.row (map-indexed (row-renderer y tool) row)]) bitmap))

(defn renderize-bitmap [work item-renderer row-renderer app tools]
  (let [{{width :width
          height :height} :resolution
         pallettes :pallettes
         current-pallette-frame :current-pallette-frame
         bitmap :bitmap
         current-frame :current-frame} work
        selected-pallette (nth current-pallette-frame pallette)
        selected-bitmap (nth current-frame bitmap)
        {selected-tool :selected-tool
         selected-color-id :selected-color-id} app
        tool ((get selected-tool tools) selected-color-id bitmap)]
    (render selected-bitmap selected-pallette item-renderer row-renderer tool)))

(defn canvas-page [work app]
  (let [initialized-work (initialize! work)]
                               (fn [] (renderize-bitmap initialized-work item-renderer row-renderer app tools))))


(defn canvas-ui [] (fn [] [:main
                           [:header]
                           [:nav]
                           [:section
                            [:article [canvas-page work app]]
                            [:aside
                             [:div.colors [color-manager work app]]
                             [:div.tools [tool-manager work app]]
                             [:div.pallette [pallete-manager work app]]]]
                           [:footer
                            [:div.pallete-frame [pallete-frame-manager work app]]
                            [:div.canvas.frame [canvas-frame-manager work app]]]]))
