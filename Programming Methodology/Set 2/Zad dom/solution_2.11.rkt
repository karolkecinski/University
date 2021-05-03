#lang racket

(provide nth-root)

(define (identity x) x)

(define (dist x y)
  (abs (- x y)))

(define (compose f g) (lambda (x) (f (g x))))

(define (close-enough? x y)
  (< (dist x y) 0.00001))

(define (repeated p q)
  (lambda (x)
    (if(= q 0)
    (identity x)
    ((compose
      (repeated p (- q 1)) p) x))))

(define (fixed-point f a)
  (let ((b (f a)))
    (if (close-enough? a b)
        a
        (fixed-point f b))))

(define (average-damp f)
  (lambda (x) (/ (+ x (f x)) 2)))

(define (log2 x)
  (/ (log x) (log 2)))

;; Funkcja (nth-root x a) będzie się zapętlała, gdy liczba a będzie przyjmowała wartości
;; kolejnych potęg liczby 2, to znaczy dla (nth-root x (2^3)) liczba tłumień musi
;; wzrosnąć do 3, tyle samo aż do (nth-root x ((2^4)-1)).
;; (nth-root x (2^4) wymaga już 4 tłumień).
;; Wymagana liczba tłumień wynosi: zaokrąglone w dół (log2 a)

(define (nth-root x q)
  (fixed-point
      ((repeated average-damp (floor (log2 q)))
          (lambda (y) (/ x (expt y (- q 1))))) 1.0))

(nth-root 128 7) (display "(nth-root 128 7) Liczba tłumień: ") (floor (log2 7))
(nth-root 256 8) (display "(nth-root 256 8) Liczba tłumień: ") (floor (log2 8))
(nth-root 32768 15) (display "(nth-root 32768 15) Liczba tłumień: ") (floor (log2 15))
(nth-root 65536 16) (display "(nth-root 65536 16) Liczba tłumień: ") (floor (log2 16))
