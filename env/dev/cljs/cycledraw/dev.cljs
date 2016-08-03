(ns ^:figwheel-no-load cycledraw.dev
  (:require [cycledraw.core :as core]
            [figwheel.client :as figwheel :include-macros true]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :jsload-callback core/mount-root)

(core/init!)
