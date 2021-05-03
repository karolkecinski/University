#lang typed/racket

(define-type Expr (U const binop))
(define-type Value Number)
(define-type BinopSym (U '+ '- '* '/))

(struct const ([val : Number]) #:transparent)
(struct binop ([op : BinopSym] [l : Expr] [r : Expr]) #:transparent)

(define-predicate binop-sym? BinopSym)
(define-predicate expr? Expr)
(define-predicate value? Value)

;; Składnia konkretna

(: parse (-> Any Expr))
(define (parse q)
  (match q
    [_ #:when (number? q) (const q)]
    [`(,op ,e1 ,e2)
     #:when (binop-sym? op)
     (binop op (parse e1) (parse e2))]))

(define test-syntax '(+ 2 (* 2 2)))

(define test-expr (parse test-syntax))

(: op->proc (-> BinopSym (-> Value Value Value)))
(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /]))

(: eval (-> Expr Value))
(define (eval e)
  (match e
    [(const n) n]
    [(binop op l r) ((op->proc op) (eval l) (eval r))]))


(define (test) (eval test-expr))

