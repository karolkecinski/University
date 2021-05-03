#lang racket

;; funkcja identycznościowa -- zwraca argument niezmieniony
;; słaby kontrakt:
(define/contract (id-weak x)
  (-> any/c any/c)
  x)

;; kontrakt parametryczny daje lepsze gwarancje:
(define/contract (id x)
  (parametric->/c [a] (-> a a))
  x)

;; można zepsuć funkcję id, zamieniając x na stałą, np. #f
;; wtedy (id #f) również naruszy kontrakt!!
(define/contract (id-broken1 x)
  (parametric->/c [a] (-> a a))
  #f)

;; można też ją zepsuć, próbując obserwować argument:
(define/contract (id-broken2 x)
  (parametric->/c [a] (-> a a))
  (+ x 0))

;; idea: wartości wchodzące do definicji (na pozycjach negatywnych)
;; są "etykietowane" i chronione przed dostępem
;; wartości wychodzące z definicji są sprawdzane, czy mają
;; właściwą etykietę, i etykieta jest usuwana

;; brak usuwania etykiety – procedura zwraca etykietowane wartości
(define/contract (id-test1 x)
  (parametric->/c [a] (-> a any/c))
  x)

;; brak etykietowania – wynik zawsze nie ma etykiety
(define/contract (id-test2 x)
  (parametric->/c [a] (-> any/c a))
  x)

;; funkcja konkatenacji list
;; słaby kontrakt
(define/contract (append-weak xs ys)
  (-> list? list? list?)
  (if (null? xs)
      ys
      (cons (car xs) (append (cdr xs) ys))))

;; kontrakt (listof c) wymaga listy elementów spełniających kontrakt c
;; jeśli c jest predykatem, to (listof c) też jest

;; ((listof number?) '())
;; ((listof number?) '(1 2))
;; ((listof number?) '(1 #f))
;; ((listof number?) '((1)))
;; ((listof number?) 1)

;; kontrakt gwarantuje, że elementami listy wyjściowej będą
;; tylko elementy list wejściowych
(define/contract (append xs ys)
  (parametric->/c [a] (-> (listof a) (listof a) (listof a)))
  (if (null? xs)
      ys
      (cons (car xs) (append (cdr xs) ys))))

;; funkcja filter
;; kontrakt gwarantuje, że predykat będzie wołany tylko na elementach
;; listy i że lista wynikowa zawiera tylko elementy z listy wejściowej
(define/contract (filter p? xs)
  (parametric->/c [a] (-> (-> a boolean?) (listof a) (listof a)))
  (if (null? xs)
      null
      (if (p? (car xs))
          (cons (car xs)
                (filter p? (cdr xs)))
          (filter p? (cdr xs)))))

;; funkcja map
;; kontrakt gwarantuje, że funkcja f otrzyma jako parametr wyłącznie
;; elementy z listy wejściowej, zaś na liście wyjściowej znajdą się
;; wyłącznie wyniki funkcji f
(define/contract (map f xs)
  (parametric->/c [a b]
    (-> (-> a b) (listof a) (listof b)))
  (if (null? xs)
      null
      (cons (f (car xs))
            (map f (cdr xs)))))

;; błędna implementacja map -- odwraca listę
;; podany kontrakt nie sprawdza kolejności
(define/contract (bad-map f xs)
  (parametric->/c [a b]
    (-> (-> a b) (listof a) (listof b)))
  (define (map-iter xs ys)
    (cond [(null? xs) ys]
          [else (map-iter (cdr xs) (cons (f (car xs)) ys))]))
  (map-iter xs '()))

