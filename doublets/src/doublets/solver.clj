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
              (if (>= 1 (distance word1 word2))
                true
                false)
              false))

(neighboor? "door" "boor")
(neighboor? "door" "buor")

(defn check-in-seq [list-ref list-c]
  (remove #(some #{%} list-ref) list-c))

(check-in-seq [1 2 3 4] [1 3 4 5])

;;Funcion que devuelve un vecino valido
(defn valid-neighboor [word1 word-target words-seq]
  (let [possible-positions (take (count word1) (range))
        nearest (filter (fn [x] (change? word1 word-target x))
                              possible-positions)
        valid-nearest (check-in-seq words-seq (filter #(not= word1 %1)
                                (map #(new-word word1 word-target %1)
                                     nearest)))
        other-words (filter #(= (count word1) (count %1)) words)
        other-words-v (check-in-seq words-seq (filter #(and (neighboor? word1 %1) (not= word1 %1))
                                other-words))]
    (if (empty? valid-nearest)
        ;;ordenar por cercania a la segunda
        (first  other-words-v)
      (first  valid-nearest)
    )))


(defn doublets-r [word1 word2 word-seq]
  (if (= word1 word2) word-seq
      (let [new-w (valid-neighboor word1 word2 word-seq)]
        (recur new-w word2 (conj word-seq new-w)))
    ))

(defn doublets [word1 word2]
  
   (if (= (count word1) (count word2)) (doublets-r word1 word2 [word1]) [])) 

(doublets "door" "lock")
(doublets "bank" "loan")
(doublets "wheat" "bread")
(doublets "ye" "freezer")
