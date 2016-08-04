(ns cycledraw.core-spec
  (:require-macros [speclj.core :refer [describe it should= should should-not]])
  (:require [speclj.core :as spc]
            [reagent.core :as reagent :refer [atom]]
            [cycledraw.components :as comps]))


(describe "## convert from color object to rgb"
          (it "is all black"
              (should=
                "rgb(0,0,0)" (comps/parse-color-rgb {:r 0 :g 0 :b 0}))))

(describe "## convert from color object to hex"
          (it "is all black"
              (should=
                "#000000" (comps/parse-color-hex {:r 0 :g 0 :b 0}))))
