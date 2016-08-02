(ns cycledraw.components)

(defn color-selector [selected-color-id work app] (fn [color color-id]
                                                      [:div.selector {:class (when (= color-id selected-color-id) "selected")}
                                                       [:input {:type "color" :value (parse-color color)}]]))

(defn color-manager [work app] (let [ {current-pallete-frame :current-pallete-frame
                                       palletes :pallettes} work
                                      {selected-color-id :selected-color-id app}
                                      color (nth selected-color-id (nth current-pallette-frame selected-pallette))
                                      ] [:div.color-indicator {:style (str "background-color:" (parse-color color))}]))

(defn pallete-manager [work app] (fn [] (let [{selected-colorid :selected-color-id} app
                                              {palletes :pallettes
                                               current-pallette-frame :current-pallette-frame} work
                                              colors (nth current-pallette-frame palletes)]
                                          (map (color-selector selected-color-id work app) colors))))

(defn tool-manager [work app] (fn [] [:button.selected "pencil"]))

(defn pallette-frame-manager [work app] (let [{current-pallete-frame :current-pallete-frame } app
                                              next-pallette-frame (fn [] )
                                              previous-pallette-frame (fn [] )]
                                          (fn [] [:div
                                                  [:button {:on-click previous-pallette-frame}]
                                                  [:span current-pallete-frame]
                                                  [:button {:on-click next-pallette-frame}]])))

(defn canvas-frame-manager [work app] (fn [] ))
