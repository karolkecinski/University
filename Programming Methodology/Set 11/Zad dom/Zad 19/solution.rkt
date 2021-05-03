#lang racket

;; |------------|
;; |   Zad 19   |
;; |------------|

;; |-----------------------------------------|
;; | Praca grupowa:                          |
;; | Karol Kęciński, Jakub Świgło, Jakub Bok |
;; |-----------------------------------------|

(require racket/contract)

(provide (contract-out
            [with-labels  with-labels/c]
            [foldr-map  foldr-map/c]
            [pair-from  pair-from/c ]))
(provide with-labels/c  foldr-map/c  pair-from/c)

;; |------------------------|
;; |  Definicje kontraktów  |
;; |------------------------|

(define with-labels/c
  (parametric->/c
   [a b] (-> (-> a b) (listof a) (listof (listof (or/c a b))))
   )
)

(define foldr-map/c 
  (parametric->/c
   [a acc xs] (-> (-> a acc (cons/c xs acc)) acc (listof a) (cons/c (listof xs) acc))
   )
)

(define pair-from/c
  (parametric->/c
   [a b c] (-> (-> a b) (-> a c) (-> a (cons/c b c)))
   )
)

;; |----------------------|
;; |  Definicje procedur  |
;; |----------------------|

(define/contract (with-labels Gen xs)
  with-labels/c
    (define (format Gen x) (list(list (Gen x) x)))
        
    (append-map (lambda (x) (format Gen x)) xs)
)

(define/contract (foldr-map f BeginValue xs)
 foldr-map/c
  (define (folding BeginValue xs ys)
    (if (null? xs)
        (cons ys BeginValue)
        (let [(val (f (car xs) BeginValue))]
          (folding (cdr val) (cdr xs) (cons (car val) ys)))))
  
  (folding BeginValue (reverse xs) null)
)

(define/contract (pair-from f g) pair-from/c (lambda (x) (cons (f x) (g x))))

;; |---------|
;; |  TESTY  |
;; |---------|

(with-labels number->string (list 1 2 3))
(foldr-map (lambda (x a) (cons a (+ a x))) 0 '(1 2 3))
((pair-from (lambda (x) (+ x 1)) (lambda (x) (* x 2))) 2)

;; |--------------------|
;; |    Własne testy    |
;; |--------------------|

(with-labels (lambda (x) (* x 5)) (list 1 2 3))
(foldr-map (lambda (x a) (cons a (+ a x))) 100 '(1 0 1 0 1))
((pair-from (lambda (x) 1) (lambda (x) (* x 100))) 2)
