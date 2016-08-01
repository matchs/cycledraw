(ns cycledraw.prod
  (:require [cycledraw.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
