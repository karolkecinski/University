#lang racket
(provide partition quicksort)

(define (partition xs seq)
  (let ([pivot (car xs)])
    (let ([x (filter (lambda (x) (seq x pivot)) (cdr xs))]
          [y (filter (lambda (x) (not (seq x pivot))) (cdr xs))])
      (if (< (length x) (length y))
          (cons (cons pivot x) y)
          (cons x (cons pivot y))))))

(define (quicksort xs seq)
  (if (< (length xs) 2)
      xs
      (let ([temp (partition xs seq)])
         (append (quicksort (car temp) seq) (quicksort (cdr temp) seq)))))

(quicksort '(5 2 3 4 1) <)
(quicksort '(35 10 24 13 3 56 86 13) <)
(quicksort '(8 3 111 1 2 4 1 3 6 8 25 3 7 4 3) <)
(quicksort '(8 3 111 1 2 4 1 3 6 8 25 3 7 4 3) >)
