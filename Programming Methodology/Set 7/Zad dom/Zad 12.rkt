#lang racket

(provide (struct-out var-free) (struct-out var-bound) (struct-out let-expr) (struct-out var-expr) (struct-out const) (struct-out pos) (struct-out binop) annotate-expression)

;; Praca Grupowa
;; Jakub Bok
;; Karol Kęciński
;; Jakub Świgło


;; ---------------
;; Jezyk wejsciowy
;; ---------------

(struct pos (file line col)     #:transparent)
  
(struct const    (val)          #:transparent)
(struct binop    (op l r)       #:transparent)
(struct var-expr (id)           #:transparent)
(struct let-expr (loc id e1 e2) #:transparent)

(define (expr? e)
  (match e
    [(const n)      (number? n)]
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x)   (symbol? x)]
    [(let-expr loc x e1 e2)
     (and (pos? loc) (symbol? x) (expr? e1) (expr? e2))]
    [_ false]))

(define (make-pos s)
  (pos (syntax-source s)
       (syntax-line   s)
       (syntax-column s)))

(define (parse e)
  (let ([r (syntax-e e)])
    (cond
      [(number? r) (const r)]
      [(symbol? r) (var-expr r)]
      [(and (list? r) (= 3 (length r)))
       (match (syntax-e (car r))
         ['let (let* ([e-def (syntax-e (second r))]
                      [x     (syntax-e (first e-def))])
                 (let-expr (make-pos (first e-def))
                           (if (symbol? x) x (error "parse error!"))
                           (parse (second e-def))
                           (parse (third r))))]
         [op   (binop op (parse (second r)) (parse (third r)))])]
      [else (error "parse error!")])))

;; ---------------
;; Jezyk wyjsciowy
;; ---------------

(struct var-free  (id)     #:transparent)
(struct var-bound (pos id) #:transparent)

(define (expr-annot? e)
  (match e
    [(const n)         (number? n)]
    [(binop op l r)    (and (symbol? op) (expr-annot? l) (expr-annot? r))]
    [(var-free x)      (symbol? x)]
    [(var-bound loc x) (and (pos? loc) (symbol? x))]
    [(let-expr loc x e1 e2)
     (and (pos? loc) (symbol? x) (expr-annot? e1) (expr-annot? e2))]
    [_ false]))

(struct env (expression))       ;;Tworzenie środowiska na zapamiętywanie pozycji zmiennych

(define (ev-add x y envir)      ;;Dodawanie pozycji
(env (cons (cons x y) (env-expression envir))))

(define (ev-search x envir)       ;;Wyszukiwanie pozycji
  (define (search expression)
    (cond [(empty? expression) null]
          [(equal? x (caar expression)) (cdar expression)]
          [else (search (cdr expression))]))
  (search (env-expression envir)))

(define (annotate-expression e)
    (define (iter e envir)
        (match e
            [(const c) (const c)]
            [(var-expr z)
              (let([pos (ev-search z envir)])
                (if (null? pos)
                  (var-free z)
                  (var-bound pos z)))]
            [(binop op left right)(binop op (iter left envir)(iter right envir))]
            [(let-expr loc z left right)
              (let-expr loc z (iter left envir) (iter right (ev-add z loc envir)))]))
    (iter e (env null)))

(parse #'(let [x 5] (* (let [x 6] (* x x)) x)))
(display "\n NEW LINE \n")
(annotate-expression (parse #'(let [x 5] (* (let [x 6] (* x x)) x))))
(display "\n NEW LINE \n")
(expr-annot? (annotate-expression (parse #'(let [x 5] (* (let [x 6] (* x x)) x)))))


  



  