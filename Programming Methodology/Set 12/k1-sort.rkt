#lang racket

(define test-list (list 6 2 3 1 0 4 5 9))

;; wersja 1
(define sorts1/c (-> (listof integer?) (listof integer?)))

(define/contract sort1 sorts1/c (lambda (x) (sort x <)))

(sort1 test-list)

(define/contract sort1-id sorts1/c (lambda (x) x))

(sort1-id test-list)

;; wersja 2

(define (sorted? l)
  (cond [(null? l) #t]
        [(null? (cdr l)) #t]
        [else (and (<= (car l) (cadr l))
                   (sorted? (cdr l)))]))

(define sorts2/c (-> (listof integer?) (and/c (listof integer?) sorted?)))

(define/contract sort2 sorts2/c (lambda (x) (sort x <)))

(sort2 test-list)

(define/contract sort2-id sorts2/c (lambda (x) x))

; kontrakt wychwyci problem
;(sort2-id test-list)

(define/contract sort2-null sorts2/c (lambda (x) '()))

(sort2-null test-list)

;; wersja 3

(define (contains? l1 l2)
  (andmap (lambda (x) (member x l2)) l1))

(define (contains/c l)
  (lambda (l2) (contains? l l2)))

(define sorts3/c
  (->i ([l (listof integer?)])
       [result (l) (and/c (listof integer?)
                          sorted?
                          (contains/c l))]))

(define/contract sort3 sorts3/c (lambda (x) (sort x <)))

(sort3 test-list)

(define/contract sort3-null sorts3/c (lambda (x) '()))

; kontrakt wychwyci problem
;(sort3-null test-list)

(define/contract sort3-add sorts3/c
  (lambda (x) (cons (- (apply min (cons 0 x)) 1) (sort x <))))

(sort3-add test-list)

;; wersja 4

(define (contained/c l)
  (lambda (l2) (contains? l2 l)))

(define sorts4/c
  (->i ([l (listof integer?)])
       [result (l) (and/c (listof integer?)
                          sorted?
                          (contains/c l)
                          (contained/c l))]))

(define/contract sort4-add sorts4/c
  (lambda (x) (cons (- (apply min (cons 0 x)) 1) (sort x <))))

; kontrakt wychwyci problem
;(sort4-add test-list)

(define/contract sort4-dbl sorts4/c (lambda (x) (sort (append x x) <)))

(sort4-dbl test-list)

;; wersja 5

(define (permutation? l1 l2)
  (cond [(and (null? l1) (null? l2)) #t]
        [(null? l1) #f]
        [(not (member (car l1) l2)) #f]
        [else (permutation? (cdr l1) (remove (car l1) l2))]))

(define sorts5/c
  (->i ([l (listof integer?)])
       [result (l) (and/c (listof integer?) sorted?)]
       #:post (l result)
       (permutation? l result)))

(define/contract sort5 sorts5/c (lambda (x) (sort x <)))

(sort5 test-list)

(define/contract sort5-dbl sorts5/c (lambda (x) (sort (append x x) <)))

;; (sort5-dbl test-list)

;; quickcheck

(require quickcheck)

(define (is-sorting s)
  (property ([l (arbitrary-list arbitrary-integer)])
            (let ((sl (s l)))
              (and (sorted? sl) (permutation? l sl)))))

(quickcheck (is-sorting sort1))
(quickcheck (is-sorting sort1-id))
(quickcheck (is-sorting sort2-null))
;;(quickcheck (is-sorting sort3-null))
(quickcheck (is-sorting sort3-add))
(quickcheck (is-sorting sort4-dbl))
