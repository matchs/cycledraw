(ns cycledraw.components)

(defn parse-color [{r :r g :g b :b}] (str "rgb(" r "," g "," b ")"))

(defn color-selector [selected-color-id change-color-handler switch-color-handler] (fn [color color-id]
                                                      [:div.selector {:class (when (= color-id selected-color-id) "selected")}
                                                       [:input {:type "color"
                                                                :value (parse-color color)
                                                                :on-change change-color-handler
                                                                :on-context-menu (switch-color-handler color-id)}]]))

(defn color-manager [work app] (let [ {current-pallette-frame :current-pallette-frame
                                       pallettes :pallettes} work
                                      {selected-color-id :selected-color-id} app
                                      color (nth selected-color-id (nth current-pallette-frame pallettes))
                                      ] [:div.color-indicator {:style (str "background-color:" (parse-color color))}]))

(defn pallette-manager [work app] (fn [] (let [{selected-color-id :selected-color-id} app
                                              {palletres :pallettes
                                               current-pallette-frame :current-pallette-frame} work
                                              colors (nth current-pallette-frame palletres)]
                                          (map (color-selector selected-color-id work app) colors))))

(defn tool-manager [work app] (fn [] [:button.selected "pencil"]))

(defn pallette-frame-manager [work app] (let [{current-pallette-frame :current-pallette-frame } app
                                              next-pallette-frame (fn [] )
                                              previous-pallette-frame (fn [] )]
                                          (fn [] [:div
                                                  [:button {:on-click previous-pallette-frame}]
                                                  [:span current-pallette-frame]
                                                  [:button {:on-click next-pallette-frame}]])))

(defn canvas-frame-manager [work app] (fn [] ))
