#lang racket

;; Praca grupowa:
;; Karol Kęciński
;; Jakub Świgło
;; Jakub Bok

(provide (struct-out complex) parse eval)

(struct complex (re im) #:transparent)

(define value?
  complex?)

;; Ponizej znajduje sie interpreter zwyklych wyrazen arytmetycznych.
;; Zadanie to zmodyfikowac go tak, by dzialal z liczbami zespolonymi.

(struct const (val)    #:transparent)
(struct binop (op l r) #:transparent)

(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /]))

(define (eval e)
  (match e
    [(complex re im) (complex re im)]
    [(binop op l r)
     (let 
      ([re1 (complex-re (eval l))]
       [re2 (complex-re (eval r))]
       [im1 (complex-im (eval l))]
       [im2 (complex-im (eval r))])

       (cond
         [(eq? op '-) (complex ((op->proc op) re1 re2)((op->proc op) im1 im2))]
         [(eq? op '+) (complex ((op->proc op) re1 re2)((op->proc op) im1 im2))]
         [(eq? op '*) (complex (+ (* re1 re2) (- (* im1 im2))) (+ (* re1 im2)(* re2 im1)))]
         [(eq? op '/) (let ([X (+ (* re2 re2) (* im2 im2))])
                         (complex (/ (+ (* re1 re2) (* im1 im2)) X) (/ (- (* re1 im2)(* re2 im1)) X)))]
         ))]
    ))

(define (parse q)
  (cond [(number? q) (complex q 0)]
        [(eq? q 'i)(complex 0 1)]
        [(and (list? q) (eq? (length q) 3) (symbol? (first q))) (binop (first q) (parse (second q))(parse (third q)))]
        ))

(eval (parse '(* i i)))
(eval (parse '(* 5 2)))
(eval (parse '(/ (- 2 (* 3 i)) (- 1 (* 5 i)))))
(eval (parse '(* (+ 5 (* 3 i)) i)))
