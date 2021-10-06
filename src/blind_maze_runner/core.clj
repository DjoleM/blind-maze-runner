(ns blind-maze-runner.core
  (:gen-class))

(def predef-start-node :2)
(def predef-end-node :4)

(def samples-required 1000)

(def g {
  :1 [:2]
  :2 [:1 :3 :5]
  :3 [:2 :6]
  :4 [:5]
  :5 [:4 :2]
  :6 [:3]
})

(defn evaluate-average
 [records]
 (/ (reduce + records) (count records)))

(defn blind-move
  [current graph]
  (first (shuffle (get graph current))))

(defn blind-run-maze
  [start-node goal-node step graph]
  (loop [current start-node
        step 0]
    (if (= current goal-node)
      (do 
        (println (str "found it in " step))
        step)
      (recur (blind-move current graph) (inc step)))))

(defn find-average-blind-run
  [start-node end-node step graph]
  (loop [sample-count 0
        records []]
    (if (= sample-count samples-required)
      (println (str "Found goal with average run of " (evaluate-average records)))
      (recur (inc sample-count) (conj records (blind-run-maze start-node end-node step graph))))))

(defn -main
  [& args]
  (find-average-blind-run predef-start-node predef-end-node 0 g))
