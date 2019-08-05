(ns alphabet-cipher.coder)

;;Secuencia que representa el alfabeto sobre
;; el que trabajamos
(def alfabeto
  [\a \b \c \d \e \f \g \h \i \j \k \l \m
  \n \o \p \q \r \s \t \u \v \w \x \y \z])

;;Funcion que devuelve la clave repetida
;;tal que cubra todo el mensaje
(defn clave-completa [n s]
  (take n (cycle s)))

;;Funcion que obtiene el indice de un char en el
;;alfabeto definido, esto porque los chars en
;;Clojure van de 97 a 122 para los simbolos
;; en ingles
(defn get-indice [c]
  (- (int c) 97))

;;Funcion que obtiene una secuencia
;;de numeros que representan la cadena
(defn get-num [cadena]
 (map get-indice cadena))

;;Funcion que obtiene el char que representa
;; un numero en el alfabeto
(defn get-char [numero]
  (nth alfabeto numero))

;; Funcion recursiva que cifra un mensaje
;; consumiendo las secuencias mensaje-num y
;; clave-num. m-c funciona como acumulador.
(defn cifra-r [mensaje-num clave-num m-c]
  (if (empty? mensaje-num)
      (apply str (map get-char m-c) )
      (recur
      (rest mensaje-num)
      (rest clave-num)
      (conj m-c
        (mod (+ (first mensaje-num) (first clave-num))
        (count alfabeto))))))

(defn encode [keyword message]
  (cifra-r
    (map get-indice message)
    (map get-indice (clave-completa (count message) keyword))
    []))

;; Funcion recursiva que decifra
(defn decifra-r [mensaje-num clave-num m-d]
    (if (empty? mensaje-num)
        (apply str (map get-char m-d) )
        (recur
        (rest mensaje-num)
        (rest clave-num)
        (conj m-d

          (mod (- (first mensaje-num) (first clave-num))
          (count alfabeto))))))

(defn decode [keyword message]
  (decifra-r
      (map get-indice message)
      (map get-indice (clave-completa (count message) keyword))
      []))

(defn decipher [cipher message]
  (apply str (map get-char
         (map #(mod (- %1 %2) (count alfabeto))
              (map get-indice cipher) (map get-indice message)))))
