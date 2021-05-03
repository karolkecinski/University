#lang racket

;;Praca grupowa:
;;Jakub Świgło
;;Jakub Bok
;;Karol Kęciński

(provide (struct-out const) (struct-out binop) rpn->arith)

;; -------------------------------
;; Wyrazenia w odwr. not. polskiej
;; -------------------------------

(define (rpn-expr? e)
  (and (list? e)
       (pair? e)
       (andmap (lambda (x) (or (number? x) (member x '(+ - * /))))
               e)))

;; ----------------------
;; Wyrazenia arytmetyczne
;; ----------------------

(struct const (val)    #:transparent)
(struct binop (op l r) #:transparent)

(define (arith-expr? e)
  (match e
    [(const n) (number? n)]
    [(binop op l r)
     (and (symbol? op) (arith-expr? l) (arith-expr? r))]
    [_ false]))


;; ----------
;; Kompilacja
;; ----------

(define (rpn->arith e)
  (define (rpn-iter xs stack)
    (cond [(null? xs)(car stack)]
          [(number? (car xs))(rpn-iter (cdr xs)(append (list (const(car xs))) stack))]
          [(symbol? (car xs))(rpn-iter (cdr xs)(append (list (binop (car xs) (first stack)(second stack))) (cddr stack)))]
           ))
  (rpn-iter e null))

; Mozesz tez dodac jakies procedury pomocnicze i testy


(arith-expr? (rpn->arith '(3 4 5 + 2 7 + – )))
(rpn->arith '(3 4 5 + 2 7 + – ))
(arith-expr? (rpn->arith '(2 1 3 7 + + + 4 / 2 * 0 +)))
(rpn->arith '(2 1 3 7 + + + 4 / 2 * 0 +))

