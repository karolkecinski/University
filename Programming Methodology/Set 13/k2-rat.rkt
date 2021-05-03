#lang typed/racket

;; definiujemy reprezentację liczb wymiernych
(define-type Rat (Pairof Integer Integer))

(: make-rat (-> Integer Integer Rat))
(define (make-rat n d)
  (let ((c (gcd n d)))
    (cons (quotient n c) (quotient d c))))

(: rat-numer (-> Rat Integer))
(define (rat-numer l)
  (car l))

(: rat-denum (-> Rat Integer))
(define (rat-denum l)
  (cdr l))

;; tak zdefiniowany predykat nie zawęża typu
;(: rat? (-> Any Boolean))
;(define (rat? x)
;  (and (pair? x) (integer? (car x)) (integer? (cdr x))))

;; automatyczne definiowanie predykatu z zawężaniem
(define-predicate rat? Rat)
; (:print-type rat?)
; (-> Any Boolean : Rat)

;; i pakiet operacji dla użytkownika
(: integer->rational (-> Integer Rat))
(define (integer->rational n)
  (make-rat n 1))

(: div-rat (-> Rat Rat Rat))
(define (div-rat l1 l2)
  (let ((n (* (rat-numer l1) (rat-denum l2)))
        (d (* (rat-denum l1) (rat-numer l2))))
    (make-rat n d)))

;; wypisywanie liczb wymiernych w formie czytelnej dla człowieka
(: print-rat (-> Rat Void))
(define (print-rat l)
  (display (rat-numer l))
  (display "/")
  (display (rat-denum l)))

;; konwersja wartości jednego z typów na liczbę wymierną
;; wymagane zawężenie typu!
(: to-rat (-> (U Integer Rat) Rat))
(define (to-rat n)
  (cond [(integer? n) (integer->rational n)]
        [(rat? n) n]))
