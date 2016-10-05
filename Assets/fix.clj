(ns fix
  (:use
    arcadia.core
    arcadia.linear
    hard.core
    hard.input
    pdfn.core
    tween.core)
  (:require 
    [arcadia.repl :as repl]))



'(swap! repl/server-running not)

(defn start-repl  [go] (try (repl/start-server 11211) (catch Exception e (log e))))
(defn update-repl [go] (try (repl/eval-queue)         (catch Exception e (log e))))


(defn jump-tween [o] 
  (timeline* :loop
    (AND (tween {:position (v3+ (>v3 o) (v3 (?f -1 1)(?f -1 1)(?f -1 1)))} o (?f 0.1 1.0) :pow3)
         (tween {:local {:scale (v3 (?f 0.2 2))}} o 1.0 :pow3))
    (tween {:material {:color (color (?f 0.5 1)(?f 1)(?f 0.3 1))}} o 0.5 :pow3)))




(defn jump [o] (dorun (map #(do % (jump-tween (clone! :sphere))) (range 100))))





'(hook+ (the camera) :start #'text-message)
'(hook+ (the camera) :update #'fix/update-repl)
'(hook+ (the camera) :start #'fix/start-repl)

'(clear-cloned!)

'(require '[clojure.pprint :as pprint] )
'(ppexpand (tween {:local {:position (v3 0 4 0)}} (the Sphere) 1.0))


'(mapv (partial arcadia.compiler/aot-namespace "Assets/hard" ) [
  'hard.animation 'hard.gobpool 'hard.input 'hard.mesh 'hard.physics 'hard.seed 'hard.sound])

 '(arcadia.compiler/aot-namespace "Assets/tween" 'tween.core)





(deftype StringPair [^:volatile-mutable ^System.String a ^:volatile-mutable ^System.String b])

(deftag System.String         
 {:pair fix.StringPair 
  :identity ""
  :lerp 
  (fn [a b r]
    (let [idx (int (* (max (count a) (count b)) r))]
      (apply str (concat (take idx b) (drop idx a)))))})

(deftween [:text-mesh :text] [this]
{:base (.GetComponent this UnityEngine.TextMesh)
 :get (.text this)
 :tag System.String})

(deftween [:text-mesh :color] [this]
{:base (.GetComponent this UnityEngine.TextMesh)
 :get (.color this)
 :tag UnityEngine.Color})


(defn text-message [o]
  (dorun 
    (map 
     #(let [o (clone! :title (v3 0 (+ 8 (* % -1.5)) 0))]
      (timeline* :loop
        (wait (* % 0.4))
        (tween {:text-mesh {:text "WHY HELLO THERE."   :color (color 1 1 1)}} o 1.2)
        (wait 1.0)
        (tween {:text-mesh {:text "                "}} o 0.7)
        (tween {:text-mesh {:text "       :)       "}} o 0.6)
        (wait 0.7)
        (tween {:text-mesh {:color (color 0 0.5 1)}} o 0.2)
        (tween {:text-mesh {:text "I AM NARRATION!!"}} o 0.8)
        (wait 0.5)
        (tween {:text-mesh {:color (color (?f 1)(?f 1)(?f 1))}} o 0.2)
        (tween {:text-mesh {:text (apply str (shuffle (seq "I AM NARRATION!!")))}} o 0.5)
        (wait 1)
        (tween {:text-mesh {:text "                "}} o 0.8)))
      (range 20))))




