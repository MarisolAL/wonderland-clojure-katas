(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(+ 1 1)

(def words (-> "/home/marisol/Documentos/ClojureEjemplos/wonderland-clojure-katas/doublets/resources/words.edn"
               (slurp)
               (read-string)))

;;Funcion que verifica si el cambio en la posicion n es valido,
;;para esto se revisa que este en el diccionario la modificada
(defn change? [word1 word2 n]
  (let [new-letter (nth word2 n)
        last-w (conj (take-last (- (count word1) (inc n)) word1) new-letter)
        new-word (apply str (concat (take n word1) last-w))
      ]
    ;;Checar si esta en el diccionario
      (if (= -1 (.indexOf words new-word)) false true)
  ))

(change? "holamundo" "abcabcabc" 4)
(change? "door" "boor" 0)


(defn doublets [word1 word2]
  "make me work")
