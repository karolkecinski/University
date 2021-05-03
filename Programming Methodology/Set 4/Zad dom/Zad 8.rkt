#lang racket

(require "leftist.rkt")
(provide heapsort)

(define (heapsort myheap)
  (define (popAll h)
    (if (heap-empty? h)
        null
        (cons (elem-val (heap-min h)) (popAll (heap-pop h))))
   )
  
  (let ((h (foldl
            (lambda (x h)
                    (heap-insert (make-elem x x) h))
            empty-heap myheap)))
    (popAll h))
 )

(heapsort '(3 6 2 6 5 4))
(heapsort '(25 16 34 3 6 412 554 0))
(heapsort '(11 34 0 0 0 11))