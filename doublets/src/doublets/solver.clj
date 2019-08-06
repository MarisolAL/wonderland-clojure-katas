(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def words (-> "/home/marisol/Documentos/ClojureEjemplos/wonderland-clojure-katas/doublets/resources/words.edn"
               (slurp)
               (read-string)))
(defn new-word [word1 word2 n]
  (let [new-letter (nth word2 n)
    last-w (conj (take-last (- (count word1) (inc n)) word1) new-letter)
    new-word (apply str (concat (take n word1) last-w))]
      new-word
  ))

;;Funcion que verifica si el cambio en la posicion n es valido,
;;para esto se revisa que este en el diccionario la modificada
(defn change? [word1 word2 n]
  (if (= -1 (.indexOf words (new-word word1 word2 n))) false true)
  )

(change? "holamundo" "abcabcabc" 4)
(change? "door" "boor" 0)

(defn not-eq? [x y]
  (if (not= x y) 1 0))

(defn distance [word1 word2]
  (reduce + (map not-eq? word1 word2)))

(defn neighboor? [word1 word2]
            (if (= (count word1)(count word2))
              (if (>= 1 (distance ))
                true
                false)
              false))

(neighboor? "door" "boor")
(neighboor? "door" "buor")

;;Funcion que devuelve un vecino valido
(defn valid-neighboor [word1 word-target]
  (let [valid-nearest (filter
                              #(change? word1 word-target %1)
                              (take (count word1) (range)))
        other-words (filter #(neighboor? (= (count word1) (count %1))) words)]
    (if (empty? valid-nearest)
      (

        )
      (new-word word1 word-target (first valid-nearest))
    )))

(defn doublets [word1 word2]
  (loop [word-act word1
         word-seq []]
    (when (empty? word-act)
          (conj word-seq (valid-neighboor word-act word2))
          (recur (rest word-act) word-seq))))
