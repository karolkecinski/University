#lang racket

;; Praca wspólna:
;; Karol Kęciński
;; Piotr Mucha

(require rackunit)
(require rackunit/text-ui)

(define (size lista)
  
  (define (size-iter lista number)
    (if (null? lista) number (size-iter (cdr lista) (+ 1 number)) ))
  
  (size-iter lista 0))

(define (append-fixed la lb)
  
  (define (append-rek first second)
    (if (or (null? first) (empty? first))
        second
        (cons (car first) (append-rek (cdr first) second))))
  
  (cond
        ((and (list? la) (list? lb)) (append-rek la lb))
        ((and (number? la) (list? lb)) (append-rek (list la) lb))
        ((and (list? la) (number? lb)) (append-rek la (list lb)))
        ((and (number? la) (number? lb)) (append-rek (list la) (list lb)))
        (else null)))

(define (merge la lb)
  
    (define (merge-iter la lb acc)
      (cond
        ((null? la) (append-fixed acc lb))
        ((null? lb) (append-fixed acc la))
        ((> (car la) (car lb)) (merge-iter la (cdr lb) (append-fixed acc (car lb))))
        (else (merge-iter (cdr la) lb (append-fixed acc (car la))))))

     (merge-iter la lb null))

(define (split lista)
    
  (define length (/ (size lista) 2))
    
  (define (split-iter lista acc n)
    (if (not (< n length))
      (cons acc (list lista))
      (split-iter (cdr lista) (append acc (list (car lista))) (+ n 1))))
  (split-iter lista null 0))

(define (merge-sort lista)
(if (> (size lista) 1)
    (merge (merge-sort (car (split lista))) (merge-sort (car (cdr (split lista)))))
    lista))





(merge-sort '(4 7 2 8 55 3 0 414 2))
(merge-sort '(0 0 0 2 0 0 0))
(merge-sort '())
(merge-sort '(9999 9998 9997 9996 9995 9994))