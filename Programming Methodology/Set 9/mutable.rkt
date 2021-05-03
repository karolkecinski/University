#lang racket

(define (cycle xs)
  (define (list->mlist xs)
    (cond [(null? xs) null]
          [else (mcons (car xs) (list->mlist (cdr xs)))]))
  (define (first-to-last! mxs)
    (define (aux mys)
      (cond [(null? mys) (error "Can't cycle empty list")]
            [(and (mpair? mys) (null? (mcdr mys)))
             (set-mcdr! mys mxs)]
            [else (aux (mcdr mys))]))
    (aux mxs))
  (let ([mxs (list->mlist xs)])
    (begin (first-to-last! mxs)
           mxs)))

(define (mtake n mxs)
  (cond [(= n 0) null]
        [(null? mxs) null]
        [else (cons (mcar mxs) (mtake (- n 1) (mcdr mxs)))]))

(define (test)
  (mtake 15 (cycle '(0 1 2 3))))