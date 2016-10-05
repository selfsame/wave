(ns testo
  (:use
    arcadia.core
    arcadia.linear
    hard.core
    tween.core)
  (:import SimpleTiledModel))

(defn text! [o s] (set! (.text (.* o >TextMesh)) s))



(defn grid [a x y]
  (get (get a y) x))

(def stm (SimpleTiledModel. "Castle" "" 10 10 false false))
(def stm false)


(.Run stm (rand-int 1000) 100)

(def bm (.Graphics stm))

(.Save bm "Assets/out.png")


(log (grid (.wave stm) 1 1))

(def MAP (atom {}))


(log 
  (vec 
    (for [y (range (count (.wave stm)))
          :let [row (get (.wave stm) y)]]
      (vec 
        (for [x (range (count (first row)))
            :let [tile (get row x)]]
        (if (= 1 (get (frequencies tile) true))
          (swap! MAP conj 
            {[y x] (first 
            (remove nil? (map-indexed 
             #(if %2 %1)
              tile)))})))))))

(do 
  (clear-cloned!)
  (map
  (fn [[[x y] v]] 
    (-> (clone! :text (v3- (v3 (- 9 x) 0 y) (v3 4.5 0 4.5)))
        (text! (str v))))
  @MAP))



(timeline*
 #(not (.Run stm (rand-int 1000) 1))
 #(.Save (.Graphics stm) "Assets/out.png"))


::NOTES '[
  for Graphics, looking for (grid (.wave stm) 1 1) where single true, corresponds to a tile

  coord translation wave[z][9 - x]
]


(deftype foo [^:volatile-mutable bar])

(aset (foo. 7) "bar" 6)


(.set_Item (foo. 7) "bar" 6)