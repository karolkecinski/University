#lang racket
(provide cube-root)
(define(cube-root x)
  (define (iter guess)
    (if(good-enough guess)
       guess
       (iter (improve guess))))

  (define (improve guess)
    (/ (+ (/ x (* guess guess)) (* 2 guess)) 3))

  (define (good-enough guess)
    (< (abs (- (* guess guess guess) x)) 0.0001))

  (iter 1.0))

(cube-root 27) ;; wynik = 3
(cube-root 8) ;; wynik = 2
(cube-root 1) ;; wynik = 1
(cube-root 1000) ;; wynik = 10
;; Autor: Karol Kęciński