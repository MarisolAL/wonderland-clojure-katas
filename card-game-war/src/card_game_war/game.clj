(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(defn play-game [player1-cards player2-cards])

(defn compare-cards [[s1 r1] [s2 r2]]
  (cond
    (> (.indexOf ranks r1) (.indexOf ranks r2)) :player1
    (< (.indexOf ranks r1) (.indexOf ranks r2)) :player2
    :else :tie))

(compare-cards (nth cards 0) (nth cards 0))
(compare-cards (nth cards 1) (nth cards 0))
(compare-cards (nth cards 0) (nth cards 1))

(defn play-round [player1-card player2-card]
  (compare-cards player1-card player2-card))

(defn tie [player1-cards player2-cards bet-cards]
  (print " Tie " (count player1-cards) (count player2-cards) " On table "(count bet-cards) \newline)
  (cond
    (< (count player1-cards) 4) (print " Player 2 won!")
    (< (count player2-cards) 4) (print " Player 1 won!")
    :else
    (let [player1-card (nth player1-cards 3)
          player2-card (nth player2-cards 3)
          result (play-round player1-card player2-card)
          table-cards (concat bet-cards (take 3 player1-cards) (take 3 player2-cards) (list player1-card player2-card))]
      (cond
        (= result :player1) (play-game
                              (concat (nthrest player1-cards 4) table-cards)
                              (nthrest player2-cards 4))
        (= result :player2) (play-game
                              (nthrest player1-cards 4)
                              (concat (nthrest player2-cards 4) table-cards)
                              )
        :else (tie (nthrest player1-cards 4)
                   (nthrest player2-cards 4)
                   table-cards)
        )
      )
    )
  )


(defn play-game [player1-cards player2-cards]
  (print " Round " (count player1-cards) (count player2-cards) \newline)
  (cond
    (empty? player1-cards) (print "Player 2 won!")
    (empty? player2-cards) (print "Player 1 won!")
    :else
    (let [player1-card (first player1-cards)
          player2-card (first player2-cards)
          result (play-round player1-card player2-card)
          bet-cards (list player1-card player2-card)]
      (cond
        (= result :player1) (play-game
                              (conj (conj (rest player1-cards) player1-card) player2-card)
                              (rest player2-cards))
        (= result :player2) (play-game
                              (rest player1-cards)
                              (conj (conj (rest player2-cards) player1-card) player2-card))
        :else (tie (rest player1-cards) (rest player2-cards) bet-cards)

        ))
    ))

(let [shuff (shuffle cards)
      player1-cards (take (/ (count cards) 2) shuff)
      player2-cards (take-last (/ (count cards) 2) shuff)]
  (play-game2 player1-cards player2-cards))

(defn play-game2 [player1-cards player2-cards]
  (print " Round " (count player1-cards) (count player2-cards) \newline)
  (cond
    (empty? player1-cards) (print "Player 2 won!")
    (empty? player2-cards) (print "Player 1 won!")
    :else
    (let [player1-card (first player1-cards)
          player2-card (first player2-cards)
          result (play-round player1-card player2-card)
          bet-cards (list player1-card player2-card)]
      (cond
        (= result :player1) (play-game2
                              (concat (rest player1-cards) (list player1-card player2-card))
                              (rest player2-cards))
        (= result :player2) (play-game2
                              (rest player1-cards)
                              (concat (rest player2-cards) (list player1-card player2-card))
                             )

        (< (count player1-cards) 4) (print " Player 2 won!")
        (< (count player2-cards) 4) (print " Player 1 won!")
        :else
        (loop [player1-cs (rest player1-cards)
               player2-cs (rest player2-cards)
               b-cards bet-cards]
            (let [player1-c (nth player1-cs 3)
                  player2-c (nth player2-cs 3)
                  result-n (play-round player1-c player2-c)
                  table-cards (concat b-cards
                                      (take 3 player1-cs)
                                      (take 3 player2-cs)
                                      (list player1-c player2-c))]
              (print "TIE")
              (cond
                (= result-n :player1) (play-game2
                                        (concat (nthrest player1-cs 4) table-cards)
                                        (nthrest (player2-cs) 4))
                (= result-n :player2) (play-game2
                                        (nthrest player1-cs 4)
                                        (concat (nthrest player2-cs 4) table-cards))
                :else (recur (nthrest player1-cs 4)
                           (nthrest player2-cs 4)
                           table-cards)
                )))))))
