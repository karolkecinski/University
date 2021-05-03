#lang racket

(define (inc n)
  (+ n 1))

;;; ordered elements
(define (make-elem pri val)
  (cons pri val))

(define (elem-priority x)
  (car x))

(define (elem-val x)
  (cdr x))


;; data representation
(define leaf 'leaf)

(define (leaf? h) (eq? 'leaf h))

(define (hnode? h)
  (and (list? h)
       (= 5 (length h));;
       (eq? (car h) 'hnode)
       (natural? (caddr h))))

(define (make-hnode elem heap-a heap-b)
  (cond
    [(> (rank heap-a) (rank heap-b)) (list 'hnode elem (inc (rank heap-b)) heap-a heap-b)]
    [else (list 'hnode elem (inc (rank heap-a)) heap-b heap-a)]))

(define (hnode-elem h)
  (second h))

(define (hnode-left h)
  (fourth h))

(define (hnode-right h)
  (fifth h))

(define (hnode-rank h)
  (third h))

(define (hord? p h)
  (or (leaf? h)
      (<= p (elem-priority (hnode-elem h)))))

(define (heap? h)
  (or (leaf? h)
      (and (hnode? h)
           (heap? (hnode-left h))
           (heap? (hnode-right h))
           (<= (rank (hnode-right h))
               (rank (hnode-left h)))
           (= (rank h) (inc (rank (hnode-right h))))
           (hord? (elem-priority (hnode-elem h))
                  (hnode-left h))
           (hord? (elem-priority (hnode-elem h))
                  (hnode-right h)))))

(define (rank h)
  (if (leaf? h)
      0
      (hnode-rank h)))

;; operations

(define empty-heap leaf)

(define (heap-empty? h)
  (leaf? h))

(define (heap-insert elt heap)
  (heap-merge heap (make-hnode elt leaf leaf)))

(define (heap-min heap)
  (hnode-elem heap))

(define (heap-pop heap)
  (heap-merge (hnode-left heap) (hnode-right heap)))

;;kod

(define (heap-merge first last)
  (cond
   [(leaf? first) last]
   [(leaf? last) first]
   [else (let (
               [e1 (elem-priority (heap-min first))]
               [e2 (elem-priority (heap-min last))])
           (let (
                 [smaller (if (> e1 e2) last first)]
                 [bigger (if (> e1 e2) first last)])
             (let ([e (heap-min smaller)]
                   [hr (hnode-right smaller)]
                   [hl (hnode-left smaller)])
               (make-hnode e hl (heap-merge hr bigger)))))]
   ))

(define (heapsort xs)
  (define (popAll h)
    (if (heap-empty? h)
        null
        (cons (elem-val (heap-min h)) (popAll (heap-pop h)))))
  (let ((h (foldl (lambda (x h)
                    (heap-insert (make-elem x x) h))
            empty-heap xs)))
    (popAll h)))

(define (randlist len max)
  (define (aux len lst)
    (if (= len 0)
        lst
        (aux (- len 1) (cons (random max) lst))))
  (aux len null))

;;; TESTS ;;;


(display "Small tests: \n")
(heapsort (randlist 10 100))
(heapsort (randlist 10 100))