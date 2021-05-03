#lang typed/racket

(: length (-> (Listof Any) Nonnegative-Integer))
(define (length xs)
  (if (null? xs)
      0
      (+ 1 (length (cdr xs)))))

(: append (All (a) (-> (Listof a) (Listof a) (Listof a))))
(define (append xs ys)
  (if (null? xs)
      ys
      (cons (car xs) (append (cdr xs) ys))))

(: map (All (a b) (-> (-> a b) (Listof a) (Listof b))))
(define (map f xs)
  (if (null? xs)
      null
      (cons (f (car xs))
            (map f (cdr xs)))))

(: fold-right (All (a b) (-> (-> a b b) b (Listof a) b)))
(define (fold-right op nval xs)
  (if (null? xs)
      nval
      (op (car xs)
          (fold-right op nval (cdr xs)))))
