#lang racket

;; Praca grupowa
;; Authors: Jakub Bok, Jakub Świgło, Karol Kęciński

(provide lcons lnull lnull? lcar lcdr from nats lnth lfilter prime? primes)

(define (lcons x f) (mcons (cons (cons x null) 0) f)) 
(define lnull null)
(define lnull? null?)
(define (lcar xs) (car (car (mcar xs)))) ;;wskazuje na  największą wyliczoną wartość przechowywanej listy

(define (lcdr xs) (begin
                    (set-mcar! xs (cons (cons (lcar ((mcdr xs))) (car (mcar xs))) (+ (cdr (mcar xs)) 1)))
                    (set-mcdr! xs (mcdr ((mcdr xs))))
                    xs))

(define (get-back-to xs n)
  (cond 
    [(= n 0)(car xs)]
    [else (get-back-to (cdr xs) (- n 1))]))

(define (lnth n xs)
  (cond [(= n (cdr (mcar xs))) (lcar xs )]
        [(< n (cdr (mcar xs))) (get-back-to (car (mcar xs)) (- (cdr (mcar xs)) n))]
        [(> n (cdr (mcar xs)))(lnth n (lcdr xs))]))

(define (from n)
  (lcons n (lambda () (from (+ n 1)))))

(define nats
   (from 0))

(define (lfilter p xs )
   (cond [(lnull? xs ) lnull]
         [(p (lcar xs))
           (lcons (lcar xs) (lambda () (lfilter p (lcdr xs))))]
         [else (lfilter p (lcdr xs))]))

(define (prime? n) 
   (define (factors i)
     (cond [(>= i n) (list n)]
           [(= ( modulo n i) 0) (cons i (factors (+ i 1)))]
           [else (factors (+ i 1))]))
  (= (length (factors 1)) 2))

(define primes (lfilter prime? (from 2)))

(time (lnth 1000 primes))
(time (lnth 1001 primes))
(time (lnth 1002 primes))
(time (lnth 997 primes))
(time (lnth 111 primes))

(time (lnth 2000 nats))
(time (lnth 543 nats))
(time (lnth 221 nats))
(time (lnth 2001 nats))
(time (lnth 0 nats))
