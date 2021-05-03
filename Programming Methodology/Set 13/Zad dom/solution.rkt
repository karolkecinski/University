#lang typed/racket

; Praca Grupowa :: Jakub Bok || Jakub Świgło || Karol Kęciński ;

(provide parse typecheck)

(define-type Value  (U Real Boolean))
(define-type EType (U 'real 'boolean))
(define-type Expr   (U const binop var-expr let-expr if-expr))
(define-type Op     (U '+ '- '* '/ '= '> '>= '< '<= 'and 'or))
(define-predicate value? Value)
(define-predicate expr? Expr)
(define-predicate op? Op)
(struct const    ([val : Value])                            #:transparent)
(struct binop    ([op : Op] [l : Expr] [r : Expr])          #:transparent)
(struct var-expr ([id : Symbol])                            #:transparent)
(struct let-expr ([id : Symbol] [e1 : Expr] [e2 : Expr])    #:transparent)
(struct if-expr  ([e1 : Expr] [e2 : Expr] [e3 : Expr])      #:transparent)

(: parse (-> Any Expr))
(define (parse q)
  (match q
    [(? real? n) (const n)]
    ['true (const true)]
    ['false (const false)]
    [(? symbol? s) (var-expr s)]
    [(list (? symbol? 'let) (list (? symbol? x) expr) body)
      (let-expr x (parse expr) (parse body))]
    [(list 'if pred ifTrue ifFalse)
          (if-expr (parse pred) (parse ifTrue) (parse ifFalse))]
    [(list (? op? op) l r) (binop op (parse l) (parse r))]))



; Srodowisko ;


(define-type Env environ)
(struct environ ([xs : (Listof (Pairof Symbol EType))]))

(: env-empty Env)
(define env-empty (environ null))

(: env-add (-> Symbol EType Env Env))
(define (env-add x v env)
  (environ (cons (cons x v) (environ-xs env))))
(: env-lookup (-> Symbol Env EType))
(define (env-lookup x env)
  (: assoc-lookup (-> (Listof (Pairof Symbol EType)) EType))
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (car (car xs))) (cdr (car xs))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))



(define oper (list '+ '- '* '/ '= '> '>= '< '<= 'and 'or))

(: typecheck-env (-> Expr Env (U EType #f)))
(define (typecheck-env e env)
  (match e
    [(const n)
     (if (boolean? n)
         'boolean
         'real)]
    [(var-expr x) (env-lookup x env)]
    [(if-expr e1 e2 e3)
     (if (eq? 'boolean (typecheck-env e1 env))
         (cond
            [(and (eq? 'real (typecheck-env e2 env)) (eq? 'real (typecheck-env e3 env)))
             'real]
            [(and (eq? 'boolean (typecheck-env e2 env)) (eq? 'boolean (typecheck-env e3 env)))
             'boolean]
            [else #f])
         #f)]
    [(let-expr x e1 e2)
     (let ([y (typecheck-env e1 env)])
       (if (or (eq? 'real y) (eq? 'boolean y))
           (typecheck-env e2 (env-add x y env))
           #f))]
    [(binop op l r)
     (cond
       [(eq? op (first oper))
        (if (and (eq? 'real (typecheck-env l env)) (eq? 'real (typecheck-env r env)))
        'real
         #f)]
       [(eq? op (second oper))
        (if (and (eq? 'real (typecheck-env l env)) (eq? 'real (typecheck-env r env)))
        'real
         #f)]
       [(eq? op (third oper))
        (if (and (eq? 'real (typecheck-env l env)) (eq? 'real (typecheck-env r env)))
        'real
         #f)]
       [(eq? op (fourth oper))
        (if (and (eq? 'real (typecheck-env l env)) (eq? 'real (typecheck-env r env)))
        'real
         #f)]
       [(eq? op (fifth oper))
        (if (and (eq? 'real (typecheck-env l env)) (eq? 'real (typecheck-env r env)))
        'boolean
         #f)]
       [(eq? op (sixth oper))
        (if (and (eq? 'real (typecheck-env l env)) (eq? 'real (typecheck-env r env)))
        'boolean
         #f)]
       [(eq? op (seventh oper))
        (if (and (eq? 'real (typecheck-env l env)) (eq? 'real (typecheck-env r env)))
        'boolean
         #f)]
       [(eq? op (eighth oper))
        (if (and (real? (typecheck-env l env)) (real? (typecheck-env r env)))
        'boolean
         #f)]
       [(eq? op (ninth oper))
        (if (and (eq? 'real (typecheck-env l env)) (eq? 'real (typecheck-env r env)))
        'boolean
         #f)]
       [(eq? op (tenth oper))
        (if (and (eq? 'boolean (typecheck-env l env)) (eq? 'boolean (typecheck-env r env)))
        'boolean
         #f)]
       [(eq? op (last oper))
        (if (and (eq? 'boolean (typecheck-env l env)) (eq? 'boolean (typecheck-env r env)))
        'boolean
         #f)]
       [else #f])]))
    
(: typecheck (-> Expr (U EType #f)))
(define (typecheck e) (typecheck-env e env-empty))


(define test
  '(if true
       (/ 2 2)
       5))
  
(define test2
  '(if true
       (let [x 2] (+ 5 x))
       (/ 2 2)))

(define test3
  '(let [x (+ 2 3)]
     (let [y (< 2 3)]
       (+ x y))))


(typecheck (parse test))
(typecheck (parse test2))
(typecheck (parse test3))


 
