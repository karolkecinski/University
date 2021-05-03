#lang racket
(provide cont-frac)
;;Karol Kęciński

(define (square x)
  (* x x))

(define (dist x y)
  (abs (- x y)))

(define (close-enough? x y)
   (< (dist x y) 0.0001))

(define (cont-frac Num Den x)
  
  (define (licznik Num Den n)
    (cond [(= -1 n) 1]
           [(= 0 n) 0]
           [else
            (+ (* (Den n) (licznik Num Den (- n 1))) (* (Num n x) (licznik Num Den (- n 2))))]
     )
  )
  
  (define (mianownik Num Den n)
    (cond [(= -1 n) 0]
          [(= 0 n) 1]
          [else
           (+ (* (Den n) (mianownik Num Den (- n 1))) (* (Num n x) (mianownik Num Den (- n 2))))]
     )
  )
  
  (define (oblicz n)
    (/ (licznik Num Den n) (mianownik Num Den n)))

  (define (wylicz_i poprzednik Num Den n)
    (let ((nastepnik (oblicz n)))
      (if (close-enough? poprzednik nastepnik)
          poprzednik
          (wylicz_i nastepnik Num Den (+ n 1)))))
  
  (wylicz_i 0.0 Num Den 1)
)

(cont-frac
 (lambda (i x) (if (= i 1) x (square (* (- i 1) x))))
 (lambda (i) (- (* 2 i) 1))
 1.0)
 
(atan 1.0)

(cont-frac
 (lambda (i x) (if (= i 1) x (square (* (- i 1) x))))
 (lambda (i) (- (* 2 i) 1))
 0.0)
 
(atan 0.0)

(cont-frac
 (lambda (i x) (if (= i 1) x (square (* (- i 1) x))))
 (lambda (i) (- (* 2 i) 1))
 -1.0)
 
(atan -1.0)