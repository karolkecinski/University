#lang racket

(provide (struct-out const) (struct-out binop) (struct-out var-expr) (struct-out let-expr) (struct-out var-dead) find-dead-vars)

;; Praca grupowa:
;;
;; Karol Kęciński
;; Jakub Świgło
;; Jakub Bok

; --------- ;
; Wyrazenia ;
; --------- ;

(struct const    (val)      #:transparent)
(struct binop    (op l r)   #:transparent)
(struct var-expr (id)       #:transparent)
(struct var-dead (id)       #:transparent)
(struct let-expr (id e1 e2) #:transparent)

(define (expr? e)
  (match e
    [(const n) (number? n)]
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(var-dead x) (symbol? x)]
    [(let-expr x e1 e2) (and (symbol? x) (expr? e1) (expr? e2))]
    [_ false]))

(define (parse q)
  (cond
    [(number? q) (const q)]
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]
    [(and (list? q) (eq? (length q) 3) (symbol? (first q)))
     (binop (first q)
            (parse (second q))
            (parse (third q)))]))

; ---------------------------------- ;
; Wyszukaj ostatnie uzycie zmiennych ;
; ---------------------------------- ;

(define (find-dead-vars e)
  (match e
    [(binop op l r)(binop op (find-dead-vars l)(find-dead-vars r))]
    [(let-expr id e1 e2) (let-expr id (find-dead-vars e1) (find-dead-vars (choose-dead e2 id)))]
    [_ e]))

(define (dead? e x)
  (match e
    [(binop op l r)(or (dead? r x)(dead? l x))]
    [(var-dead s) (eq? x s)]
    [(let-expr id e1 e2) 
      (if (eq? id x)
        (dead? e1 x)
        (or (dead? e2 x)(dead? e1 x)))]
    [_ #f]))

(define (choose-dead e x)
    (match e
      
      [(binop op l r)
        (if (dead? (choose-dead r x) x)
          (binop op l (choose-dead r x))
          (binop op (choose-dead l x) r))]
      
      [(var-expr s)
        (if (eq? x s)
          (var-dead x)
          (var-expr s))]
      
      [(let-expr id e1 e2) 
       (if (eq? id x)
         (let-expr id (choose-dead e1 x) e2)
         (if (dead? (choose-dead e2 x))
           (let-expr id e1 (choose-dead e2 x))
           (let-expr id (choose-dead e1 x) e2)))]
      
      [_ e]))

(find-dead-vars (let-expr 'x (const  500)(binop '* (var-expr 'x) (let-expr 'x (binop '+ 200 (var-expr 'x)) (binop '* (var-expr 'x)(var-expr 'x))))))
(display "\n")
(find-dead-vars (let-expr 'x (const  123) (binop '+ (var-expr 'x) (var-expr 'x))))