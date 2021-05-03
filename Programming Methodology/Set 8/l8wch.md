# MP lista 8

## Ćwiczenie 1
```racket= 
#lang racket


; Do let-env.rkt dodajemy wartosci boolowskie
;
; Miejsca, ktore sie zmienily oznaczone sa przez !!!

; --------- ;
; Wyrazenia ;
; --------- ;

(struct const    (val)      #:transparent)
(struct binop    (op l r)   #:transparent)
(struct var-expr (id)       #:transparent)
(struct let-expr (id e1 e2) #:transparent)
(struct if-expr  (eb et ef) #:transparent)

(define (expr? e)
  (match e
    [(const n) (or (number? n) (eq? n 'true) (eq? n 'false))] ; <---------- zad1
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(if-expr eb et ef) ; <--------------------------------------- !!!
     (and (expr? eb) (expr? et) (expr? ef))]
    [_ false]))

(define (parse q)
  (cond
    [(number? q) (const q)]
    [(eq? q 'true)  (const 'true)]  ; <---------- zad1
    [(eq? q 'false) (const 'false)] ; ; <---------- zad1
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]
    [(and (list? q) (eq? (length q) 4) (eq? (first q) 'if)) ; <--- !!!
     (if-expr (parse (second q))
              (parse (third q))
              (parse (fourth q)))]
    [(and (list? q) (eq? (length q) 3) (symbol? (first q)))
     (binop (first q)
            (parse (second q))
            (parse (third q)))]))

(define (test-parse) (parse '(let [x (+ 2 2)] (+ x 1))))

; ---------- ;
; Srodowiska ;
; ---------- ;

(struct environ (xs))

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (cons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (car (car xs))) (cdr (car xs))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))

; --------- ;
; Ewaluacja ;
; --------- ;

; Bierze funkcję zwracającą #t/#f.
; Zwraca funkcję zwracającą 1/0.
(define (to-01 fn)
  (lambda (x y)
    (if (fn x y) 1 0)))
; ['= (to-01 =)] ['> (to-01 >)] ['>= (to-01 >=)] ['< (to-01 <)] ['<= (to-01 <=)]


(define (value? v)
  (or (number? v) (boolean? v)))

(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /] ['% modulo] ; <----------- !!!
            ['=   (lambda (x y) (if (= x y)  1 0))]               
            ['>   (lambda (x y) (if (> x y)  1 0))] 
            ['>=  (lambda (x y) (if (>= x y) 1 0))] 
            ['<   (lambda (x y) (if (< x y)  1 0))] 
            ['<=  (lambda (x y) (if (<= x y) 1 0))]
            ['and (lambda (x y) (if (and (= x 1) (= y 1)) 1 0))]
            ['or  (lambda (x y) (if (or  (= x 1) (= y 1)) 1 0))]))

(define (eval-env e env)
  (match e
    [(const n) (cond ; <---------- zad1
                 [(eq? n 'false) 0]
                 [(eq? n 'true)  1]
                 [else n])]
    [(binop op l r) ((op->proc op) (eval-env l env)
                                   (eval-env r env))]
    [(let-expr x e1 e2)
     (eval-env e2 (env-add x (eval-env e1 env) env))]
    [(var-expr x) (env-lookup x env)]
    [(if-expr eb et ef) (if (= (eval-env eb env) 0) ; <---------- zad1
                            (eval-env ef env)
                            (eval-env et env))]))

(define (eval e) (eval-env e env-empty))

; powinien dać 1/0
(define program
  '(if (or (> (% 123 10) 5)
           false)
       (+ 2 3)
       (/ 2 0)))

(define (test-eval) (eval (parse program)))
```
```
> (parse '(if 0 false true))
(if-expr (const 0) (const 'false) (const 'true))
```

```
; powinien dać 1/0
(define program
  '(if (= 1 2)
       (+ 2 3)
       (/ 2 1)))
       

> (eval (parse program))
2
```
## Ćwiczenie 2
```racket=
#lang racket
; Do let-env.rkt dodajemy wartosci boolowskie
;
; Miejsca, ktore sie zmienily oznaczone sa przez !!!

; --------- ;
; Wyrazenia ;
; --------- ;

(struct const    (val)      #:transparent)
(struct binop    (op l r)   #:transparent)
(struct var-expr (id)       #:transparent)
(struct let-expr (id e1 e2) #:transparent)
(struct if-expr  (eb et ef) #:transparent)
(struct unop (op e)         #:transparent) ; <--- zmiana


    
(define (expr? e)
  (match e
    [(const n) (or (number? n) (boolean? n))]
    [(unop op e) (and (expr? e) (symbol? op))] ;---> zmiana
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(if-expr eb et ef)
     (and (expr? eb) (expr? et) (expr? ef))]
    [_ false]))

(define (parse q)
  (cond
    [(number? q) (const q)]
    [(eq? q 'true)  (const true)]  
    [(eq? q 'false) (const false)] 
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 2) (symbol? (first q))) (unop (first q) (parse (second q)))]  ;<----- ZMIANA
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]
    [(and (list? q) (eq? (length q) 4) (eq? (first q) 'if)) 
     (if-expr (parse (second q))
              (parse (third q))
              (parse (fourth q)))]
    [(and (list? q) (eq? (length q) 3) (symbol? (first q)))
     (binop (first q)
            (parse (second q))
            (parse (third q)))]))

(define (test-parse) (parse '(let [x (+ 2 2)] (+ x 1))))

; ---------- ;
; Srodowiska ;
; ---------- ;

(struct environ (xs))

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (cons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (car (car xs))) (cdr (car xs))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))

; --------- ;
; Ewaluacja ;
; --------- ;

(define (value? v)
  (or (number? v) (boolean? v)))

(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /] ['% modulo]
            ['= =] ['> >] ['>= >=] ['< <] ['<= <=]
            ['and (lambda (x y) (and x y))]
            ['or  (lambda (x y) (or  x y))]
            ['boolean? boolean?] ['number? number?])) ;----> zmiana

(define (eval-env e env)
  (match e
    [(const n) n]
    [(unop op e) ((op->proc op) (eval-env e env))] ;-> zmiana
    [(binop op l r) ((op->proc op) (eval-env l env)
                                   (eval-env r env))]
    [(let-expr x e1 e2)
     (eval-env e2 (env-add x (eval-env e1 env) env))]
    [(var-expr x) (env-lookup x env)]
    [(if-expr eb et ef) (if (eval-env eb env) ; 
                            (eval-env et env)
                            (eval-env ef env))]))

(define (eval e) (eval-env e env-empty))

(define program
  '(if (or (< (% 123 10) 5)
           true)
       (+ 2 3)
       (/ 2 0)))

(define (test-eval) (eval (parse program)))

(eval (parse '(number? (if (< 0 (+ (* 2 2) 7)) 5 true)))) ;-> #t

(eval (parse '(boolean? (if (< 0 (+ (* 2 2) 7)) 5 true)))) ;-> #f

(eval (parse '(boolean? (if (> 0 (+ (* 2 2) 7)) 5 true)))) ;-> #t
```

## Ćwiczenie 3
```racket=
#lang racket
; Do let-env.rkt dodajemy wartosci boolowskie
;
; Miejsca, ktore sie zmienily oznaczone sa przez !!!

; --------- ;
; Wyrazenia ;
; --------- ;
(struct binop2    (op l r)   #:transparent)
(struct const    (val)      #:transparent)
(struct binop    (op l r)   #:transparent)
(struct var-expr (id)       #:transparent)
(struct let-expr (id e1 e2) #:transparent)
(struct if-expr  (eb et ef) #:transparent)

(define (expr? e)
  (match e
    [(const n) (or (number? n) (eq? n 'true) (eq? n 'false))] ; <---------- zad1
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(if-expr eb et ef) ; <--------------------------------------- !!!
     (and (expr? eb) (expr? et) (expr? ef))]
    [_ false]))

(define (parse q)
  (cond
    [(number? q) (const q)]
    [(eq? q 'true)  (const 'true)]  ; <---------- zad1
    [(eq? q 'false) (const 'false)] ; ; <---------- zad1
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]
    [(and (list? q) (eq? (length q) 4) (eq? (first q) 'if)) ; <--- !!!
     (if-expr (parse (second q))
              (parse (third q))
              (parse (fourth q)))]
       [(and (list? q) (eq? (length q) 3) (or (eq? (first q) 'and) (eq? (first q) 'or) ) )
     (binop2 (first q)
            (parse (second q))
            (parse (third q)))]

    
    [(and (list? q) (eq? (length q) 3) (symbol? (first q)))
     (binop (first q)
            (parse (second q))
            (parse (third q)))]))

(define (test-parse) (parse '(let [x (+ 2 2)] (+ x 1))))

; ---------- ;
; Srodowiska ;
; ---------- ;

(struct environ (xs))

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (cons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (car (car xs))) (cdr (car xs))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))

; --------- ;
; Ewaluacja ;
; --------- ;

(define (value? v)
  (or (number? v) (boolean? v)))

(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /] ['% modulo] ; <----------- !!!
            ['=   (lambda (x y) (if (= x y)  1 0))]               
            ['>   (lambda (x y) (if (> x y)  1 0))] 
            ['>=  (lambda (x y) (if (>= x y) 1 0))] 
            ['<   (lambda (x y) (if (< x y)  1 0))] 
            ['<=  (lambda (x y) (if (<= x y) 1 0))]
            ))



(define (zmien x)
(if (= x 0) false true)
  )

(define (spowrotem x)
(if x 1 0)
  )

;; spowrotem -> z-powrotem

(define (eval-env e env)
  (match e
    [(const n) (cond ; <---------- zad1
                 [(eq? n 'false) 0]
                 [(eq? n 'true)  1]
                 [else n])]
    [(binop2 op l r) (cond [(eq? op 'and)  (spowrotem (and (zmien (eval-env l env   )) (zmien (eval-env r env)))) ]
                           [(eq? op 'or)   (spowrotem (or  (zmien (eval-env l env)) (zmien (eval-env r env)))) ]           
	)]
    
    ; [(binop 'and l r) (spowrotem (and (zmien (eval-env l env   )) (zmien (eval-env r env))))]
    [(binop op l r) ((op->proc op) (eval-env l env)
                                   (eval-env r env))]
    [(let-expr x e1 e2)
     (eval-env e2 (env-add x (eval-env e1 env) env))]
    [(var-expr x) (env-lookup x env)]
    [(if-expr eb et ef) (if (= (eval-env eb env) 0) ; <---------- zad1
                            (eval-env ef env)
                            (eval-env et env))]))

(define (eval e) (eval-env e env-empty))

(define program
  '(if (or (> (% 123 10) 5)
           false)
       (+ 2 3)
       (/ 2 0)))

(define (test-eval) (eval (parse program)))
```

można było też potraktować and i or jako lukier syntaktyczny i zmienić na if w trakcie parsowania:
``` racket==
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'and)); <----------------- !!!
     (if-expr (parse (second q))
              (parse (third q))
              (const false))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'or)); <----------------- !!!
     (if-expr (parse (second q))
              (const 'true)
              (parse (third q)))]
```

## Ćwiczenie 4

```racket=
#lang racket

; Do boolean.rkt dodajemy pary
;
; Miejsca, ktore sie zmienily oznaczone sa przez !!!

; --------- ;
; Wyrazenia ;
; --------- ;

(struct const     (val)      #:transparent)
(struct binop     (op l r)   #:transparent)
(struct var-expr  (id)       #:transparent)
(struct let-expr  (id e1 e2) #:transparent)
(struct if-expr   (eb et ef) #:transparent)
(struct cons-expr (e1 e2)    #:transparent) ; <------------------- !!!
(struct car-expr  (e)        #:transparent) ; <------------------- !!!
(struct cdr-expr  (e)        #:transparent) ; <------------------- !!!

(struct pair?-expr (e) #:transparent)

(define (expr? e)
  (match e
    [(const n) (or (number? n) (boolean? n))]
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(if-expr eb et ef)
     (and (expr? eb) (expr? et) (expr? ef))]
    [(cons-expr e1 e2) (and (expr? e1) (expr? e2))] ; <----------- !!!
    [(car-expr e) (expr? e)] ; <---------------------------------- !!!
    [(cdr-expr e) (expr? e)] ; <---------------------------------- !!!
    [(pair?-expr e) (expr? e) ]
    [_ false]))

(define (parse q)
  (cond
    [(number? q) (const q)]
    [(eq? q 'true)  (const true)]
    [(eq? q 'false) (const false)]
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'cons)) ; <- !!!
     (cons-expr (parse (second q))
                (parse (third q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'car)) ; <-- !!!
     (car-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'cdr)) ; <-- !!!
     (cdr-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]
    [(and (list? q) (eq? (length q) 4) (eq? (first q) 'if))
     (if-expr (parse (second q))
              (parse (third q))
              (parse (fourth q)))]
   
   [(and (list? q) (eq? (length q) 2) (eq? (first q) 'pair?))
     (pair?-expr (parse (second q)))]
   
   
   [(and (list? q) (eq? (length q) 3) (symbol? (first q)))
     (binop (first q)
            (parse (second q))
            (parse (third q)))]))

(define (test-parse) (parse '(let [x (+ 2 2)] (+ x 1))))

; ---------- ;
; Srodowiska ;
; ---------- ;

(struct environ (xs))

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (cons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (car (car xs))) (cdr (car xs))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))

; --------- ;
; Ewaluacja ;
; --------- ;

(define (value? v)
  (or (number? v)
      (boolean? v)
      (and (pair? v) (value? (car v)) (value? (cdr v)))))

(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /] ['% modulo]
            ['= =] ['> >] ['>= >=] ['< <] ['<= <=]
            ['and (lambda (x y) (and x y))]
            ['or  (lambda (x y) (or  x y))]))

(define (eval-env e env)
  (match e
    [(const n) n]
    [(binop op l r) ((op->proc op) (eval-env l env)
                                   (eval-env r env))]
    [(let-expr x e1 e2)
     (eval-env e2 (env-add x (eval-env e1 env) env))]
    [(var-expr x) (env-lookup x env)]
    [(if-expr eb et ef) (if (eval-env eb env)
                            (eval-env et env)
                            (eval-env ef env))]
    [(cons-expr e1 e2) (cons (eval-env e1 env) ; <---------------- !!!
                             (eval-env e2 env))]
    [(car-expr e) (car (eval-env e env))] ; <--------------------- !!!
    [(cdr-expr e) (cdr (eval-env e env))]
    
    [(pair?-expr e) (pair? (eval-env e env))]
    )) ; <------------------- !!!
    
    

(define (eval e) (eval-env e env-empty))

(define program
  '(car (if true (cons 1 2) false)))

(define (test-eval) (eval (parse program)))
```

## Ćwiczenie 5

```
; Z fun.rkt:
[(and (list? q) (pair? q) (not (op->proc (car q)))) ; <------- !!!
     (parse-app q)]
```

```
(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /] ['% modulo]
            ['= =] ['> >] ['>= >=] ['< <] ['<= <=]
            ['pair? pair?] ; <- NOWE
            ['not not] ; <- NOWE
            ['car car] ; <- NOWE
            ['cdr cdr] ; <- NOWE
            ['and (lambda (x y) (and x y))]
            ['or  (lambda (x y) (or  x y))]
            [_ false]))
```

## Ćwiczenie 6
```racket
#lang racket

; Do boolean.rkt dodajemy pary
;
; Miejsca, ktore sie zmienily oznaczone sa przez !!!

; --------- ;
; Wyrazenia ;
; --------- ;

(struct const     (val)      #:transparent)
(struct binop     (op l r)   #:transparent)
(struct var-expr  (id)       #:transparent)
(struct let-expr  (id e1 e2) #:transparent)
(struct if-expr   (eb et ef) #:transparent)
(struct cons-expr (e1 e2)    #:transparent) ; <------------------- !!!
(struct car-expr  (e)        #:transparent) ; <------------------- !!!
(struct cdr-expr  (e)        #:transparent) ; <------------------- !!!

(define (expr? e)
  (match e
    [(const n) (or (number? n) (boolean? n))]
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(if-expr eb et ef)
     (and (expr? eb) (expr? et) (expr? ef))]
    [(cons-expr e1 e2) (and (expr? e1) (expr? e2))] ; <----------- !!!
    [(car-expr e) (expr? e)] ; <---------------------------------- !!!
    [(cdr-expr e) (expr? e)] ; <---------------------------------- !!!
    [_ false]))

(define (parse q)
  (define (isCR? l)
    (define (rec x) (if (or (eq? (car x) #\d) (eq? (car x) #\a))
                        (rec (cdr x))
                        (and (eq? (car x) #\r) (eq? (cdr x) null))))
    (if (eq? (car l) #\c)
        (rec (cdr l))
        false))
  (define (parseCR l rest)
    (match (car l)
      [#\a (car-expr (parseCR (cdr l) rest))]
      [#\d (cdr-expr (parseCR (cdr l) rest))]
      [#\r (parse rest)]))
  (cond
    [(number? q) (const q)]
    [(eq? q 'true)  (const true)]
    [(eq? q 'false) (const false)]
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'cons)) ; <- !!!
     (cons-expr (parse (second q))
                (parse (third q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'car)) ; <-- !!!
     (car-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'cdr)) ; <-- !!!
     (cdr-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]
    [(and (list? q) (eq? (length q) 4) (eq? (first q) 'if))
     (if-expr (parse (second q))
              (parse (third q))
              (parse (fourth q)))]
    [(and (list? q) (eq? (length q) 3) (symbol? (first q)))
     (binop (first q)
            (parse (second q))
            (parse (third q)))]
    [(and (list? q) (eq? (length q) 2) (isCR? (string->list (symbol->string (first q)))))
     (parseCR (cdr (string->list (symbol->string (first q)))) (second q))]))

(define (test-parse) (parse '(let [x (+ 2 2)] (+ x 1))))


; ---------- ;
; Srodowiska ;
; ---------- ;

(struct environ (xs))

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (cons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (car (car xs))) (cdr (car xs))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))

; --------- ;
; Ewaluacja ;
; --------- ;

(define (value? v)
  (or (number? v)
      (boolean? v)
      (and (pair? v) (value? (car v)) (value? (cdr v)))))

(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /] ['% modulo]
            ['= =] ['> >] ['>= >=] ['< <] ['<= <=]
            ['and (lambda (x y) (and x y))]
            ['or  (lambda (x y) (or  x y))]))

(define (eval-env e env)
  (match e
    [(const n) n]
    [(binop op l r) ((op->proc op) (eval-env l env)
                                   (eval-env r env))]
    [(let-expr x e1 e2)
     (eval-env e2 (env-add x (eval-env e1 env) env))]
    [(var-expr x) (env-lookup x env)]
    [(if-expr eb et ef) (if (eval-env eb env)
                            (eval-env et env)
                            (eval-env ef env))]
    [(cons-expr e1 e2) (cons (eval-env e1 env) ; <---------------- !!!
                             (eval-env e2 env))]
    [(car-expr e) (car (eval-env e env))] ; <--------------------- !!!
    [(cdr-expr e) (cdr (eval-env e env))])) ; <------------------- !!!

(define (eval e) (eval-env e env-empty))

(define program
  '(car (if true (cons 1 2) false)))

(define (test-eval) (eval (parse program)))

(eval (parse '(cadr (cons 1 (cons 2 3))))) ;zwraca 2
```

```
(define prog
  '(let [xs (cons 1 (cons 2 (cons 3 (cons 4 (cons 5 6)))))]
      (caddddr xs))) 
```      
      

## Ćwiczenie 7
```racket=
#lang racket

; --------- ;
; Wyrazenia ;
; --------- ;

(struct const      (val)      #:transparent)
(struct binop      (op l r)   #:transparent)
(struct var-expr   (id)       #:transparent)
(struct let-expr   (id e1 e2) #:transparent)
(struct if-expr    (eb et ef) #:transparent)
(struct cons-expr  (e1 e2)    #:transparent)
(struct car-expr   (e)        #:transparent)
(struct cdr-expr   (e)        #:transparent)
(struct null-expr  ()         #:transparent)
(struct null?-expr (e)        #:transparent)

(define (expr? e)
  (match e
    [(const n) (or (number? n) (boolean? n))]
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(if-expr eb et ef)
     (and (expr? eb) (expr? et) (expr? ef))]
    [(cons-expr e1 e2) (and (expr? e1) (expr? e2))]
    [(car-expr e) (expr? e)]
    [(cdr-expr e) (expr? e)]
    [(null-expr) true]
    [(null?-expr e) (expr? e)]
    [_ false]))

(define (parse q)

  (define (desugar expr) ;; <-------------- !!!!
      (if (null? expr)
          (null-expr)
          (cons-expr (car expr) (desugar (cdr expr)))))
  
  (cond
    [(number? q) (const q)]
    [(eq? q 'true)  (const true)]
    [(eq? q 'false) (const false)]
    [(eq? q 'null)  (null-expr)]
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'null?))
     (null?-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'cons))
     (cons-expr (parse (second q))
                (parse (third q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'car))
     (car-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'cdr))
     (cdr-expr (parse (second q)))]
    [(and (list? q) (eq? (first q) 'list))   ;; <-------------- !!!!
     (desugar (map parse (cdr q)))]          ;; <-------------- !!!!
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]
    [(and (list? q) (eq? (length q) 4) (eq? (first q) 'if))
     (if-expr (parse (second q))
              (parse (third q))
              (parse (fourth q)))]
    [(and (list? q) (eq? (length q) 3) (symbol? (first q)))
     (binop (first q)
            (parse (second q))
            (parse (third q)))]))
      

; ---------- ;
; Srodowiska ;
; ---------- ;

(struct environ (xs))

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (cons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (car (car xs))) (cdr (car xs))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))

; --------- ;
; Ewaluacja ;
; --------- ;

(define (value? v)
  (or (number? v)
      (boolean? v)
      (and (pair? v) (value? (car v)) (value? (cdr v)))
      (null? v)))

(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /] ['% modulo]
            ['= =] ['> >] ['>= >=] ['< <] ['<= <=]
            ['and (lambda (x y) (and x y))]
            ['or  (lambda (x y) (or  x y))]))

(define (eval-env e env)
  (match e
    [(const n) n]
    [(binop op l r) ((op->proc op) (eval-env l env)
                                   (eval-env r env))]
    [(let-expr x e1 e2)
     (eval-env e2 (env-add x (eval-env e1 env) env))]
    [(var-expr x) (env-lookup x env)]
    [(if-expr eb et ef) (if (eval-env eb env)
                            (eval-env et env)
                            (eval-env ef env))]
    [(cons-expr e1 e2) (cons (eval-env e1 env)
                             (eval-env e2 env))]
    [(car-expr e) (car (eval-env e env))]
    [(cdr-expr e) (cdr (eval-env e env))]
    [(null-expr) null]
    [(null?-expr e) (null? (eval-env e env))]))

(define (eval e) (eval-env e env-empty))

```

## Ćwiczenie 8

```racket=
#lang racket

; Do pair.rkt dodajemy listy
;
; Miejsca, ktore sie zmienily oznaczone sa przez !!!

; --------- ;
; Wyrazenia ;
; --------- ;

(struct const      (val)      #:transparent)
(struct binop      (op l r)   #:transparent)
(struct var-expr   (id)       #:transparent)
(struct let-expr   (id e1 e2) #:transparent)
(struct if-expr    (eb et ef) #:transparent)
(struct cons-expr  (e1 e2)    #:transparent)
(struct car-expr   (e)        #:transparent)
(struct cdr-expr   (e)        #:transparent)
(struct null-expr  ()         #:transparent) 
(struct null?-expr (e)        #:transparent) 
(struct nth-expr (selector e) #:transparent) ; <- NOWE

(define (expr? e)
  (match e
    [(const n) (or (number? n) (boolean? n))]
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(if-expr eb et ef)
     (and (expr? eb) (expr? et) (expr? ef))]
    [(cons-expr e1 e2) (and (expr? e1) (expr? e2))]
    [(car-expr e) (expr? e)]
    [(cdr-expr e) (expr? e)]
    [(null-expr) true] 
    [(null?-expr e) (expr? e)] 
    ; FIXME: czy ewaluować pierwszy parametr (dla dynamicznych wyrażeń)?
    [(nth-expr selector e) (and (symbol? selector) (expr? e))]
    [_ false]))

(define (parse q)
  (cond
    [(number? q) (const q)]
    [(eq? q 'true)  (const true)]
    [(eq? q 'false) (const false)]
    [(eq? q 'null)  (null-expr)] ; 
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'null?)) 
     (null?-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'cons))
     (cons-expr (parse (second q))
                (parse (third q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'car))
     (car-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'cdr))
     (cdr-expr (parse (second q)))]
    [(and (list? q) (= (length q) 2)) ; <- NOWE
      (nth-expr (first q) (parse (second q)))] ; <- NOWE
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]
    [(and (list? q) (eq? (length q) 4) (eq? (first q) 'if))
     (if-expr (parse (second q))
              (parse (third q))
              (parse (fourth q)))]
    [(and (list? q) (eq? (length q) 3) (symbol? (first q)))
     (binop (first q)
            (parse (second q))
            (parse (third q)))]))

; ---------- ;
; Srodowiska ;
; ---------- ;

(struct environ (xs))

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (cons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (car (car xs))) (cdr (car xs))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))

; --------- ;
; Ewaluacja ;
; --------- ;

(define (value? v)
  (or (number? v)
      (boolean? v)
      (and (pair? v) (value? (car v)) (value? (cdr v)))
      (null? v))) ; 

(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /] ['% modulo]
            ['= =] ['> >] ['>= >=] ['< <] ['<= <=]
            ['and (lambda (x y) (and x y))]
            ['or  (lambda (x y) (or  x y))]))

(define (get-nth n xs) (list-ref xs n)) ; <- NOWE

; Pomysł: pogrupować słowa w grupy (a . modifier).
; `a` w przedziale 0-999, modifier \in {billion, million, thousand}.
(define (selector-to-n selector)
  (define (a-to-number mod)
    (match mod
      ['first 1] ['second 2] ['third 3] ['fourth 4] ['fifth 5]
      ['sixth 6] ['seventh 7] ['eighth 8] ['ninth 9] ['tenth 10]
      ['eleventh 11] ['twelfth 12] ['thirteenth 13] ['fourteenth 14] ['fifteenth 15]
      ['sixteenth 16] ['seventeenth 17] ['eighteenth 18] ['nineteenth 19]

      ; Dla one-hundred etc.
      ; FIXME: wydzielić do innej funkcji, żeby (one xs) nie działało.
      ['one 1] ['two 2] ['three 3] ['four 4] ['five 5]
      ['six 6] ['seven 7] ['eight 8] ['nine 9] ['ten 10]
      ['eleven 11] ['twelve 12] ['thirteen 13] ['fourteen 14] ['fifteen 15]
      ['sixteen 16] ['seventeen 17] ['eighteen 18] ['nineteen 19]

      ['twenty 20] ['thirty 30] ['forty 40] ['fifty 50]
      ['sixty 60] ['seventy 70] ['eighty 80] ['ninety 90]

      [_ false]))

  (define (group-separator? mod)
    (match mod
      ['hundred false]
      ['thousand true]
      ['million  true]
      ['billion  true]
      [_ false]))

  (define (exp-to-number mod)
    (match mod
      ['hundred  100]
      ['thousand 1000]
      ['million  1000000]
      ['billion  1000000000]
      [_ false]))

  (define (group-complete? group)
    (and (not (null? group))
      (group-separator? (car group))))

  ; chunks - lista symboli (słów)
  ; groups - lista list (grup)
  ; grupa to np. (first hundred one)
  (define (chunks-to-groups chunks groups)
    (cond
      ; Warunek stopu iteracji.
      [(null? chunks) groups]
      ; Zaczynamy budować nową listę grup...
      [(null? groups)
        (chunks-to-groups
          (cdr chunks)
          (cons (cons (car chunks) null) null))]
      ; Zaczynamy budować nową grupę...
      [(group-complete? (car groups))
        (chunks-to-groups
          (cdr chunks)
          (cons (cons (car chunks) null) groups))]
      ; Kontynuujemy budowanie grupy...
      [else
        (chunks-to-groups
          (cdr chunks)
          (cons (cons (car chunks) (car groups)) (cdr groups)))]))

  (define (group-to-num group)
    (cond
      [(null? group) 0]
      [(exp-to-number (car group)) (* (exp-to-number (car group)) (group-to-num (cdr group)))]
      [else (+ (a-to-number (car group)) (group-to-num (cdr group)))]))

  (define chunks
    (map string->symbol
      (string-split
        (symbol->string selector)
        "-")))

  (define groups
    (chunks-to-groups chunks null))
  (display "Groups: ")
  (displayln groups)

  (define numbers
    (map group-to-num groups))
  (display "Numbers: ")
  (displayln numbers)

  (define sum
    (foldl + 0 numbers))
  (displayln sum)

  sum)

(define (eval-env e env)
  (match e
    [(const n) n]
    [(binop op l r) ((op->proc op) (eval-env l env)
                                   (eval-env r env))]
    [(let-expr x e1 e2)
     (eval-env e2 (env-add x (eval-env e1 env) env))]
    [(var-expr x) (env-lookup x env)]
    [(if-expr eb et ef) (if (eval-env eb env)
                            (eval-env et env)
                            (eval-env ef env))]
    [(cons-expr e1 e2) (cons (eval-env e1 env)
                             (eval-env e2 env))]
    [(car-expr e) (car (eval-env e env))]
    [(cdr-expr e) (cdr (eval-env e env))]
    [(nth-expr selector e) (get-nth (selector-to-n selector) (eval-env e env))] ; <- NOWE
    [(null-expr) null] 
    [(null?-expr e) (null? (eval-env e env))])) 

(define (eval e) (eval-env e env-empty))

(define program
  '(nine-hundred-ninety-nine-billion-nine-hundred-ninety-nine-million-nine-hundred-ninety-nine-thousand-nine-hundred-ninety-ninth nope))

(eval (parse program))
```

## Ćwiczenie 9
```racket=
#lang racket

; Do list.rkt dodajemy procedury
;
; Miejsca, ktore sie zmienily oznaczone sa przez !!!

; --------- ;
; Wyrazenia ;
; --------- ;

(struct const      (val)      #:transparent)
(struct binop      (op l r)   #:transparent)
(struct var-expr   (id)       #:transparent)
(struct let-expr   (id e1 e2) #:transparent)
(struct if-expr    (eb et ef) #:transparent)
(struct cons-expr  (e1 e2)    #:transparent)
(struct car-expr   (e)        #:transparent)
(struct cdr-expr   (e)        #:transparent)
(struct null-expr  ()         #:transparent)
(struct null?-expr (e)        #:transparent)
(struct app        (f e)      #:transparent) 
(struct lam        (id e)     #:transparent) 

(define (expr? e)
  (match e
    [(const n) (or (number? n) (boolean? n))]
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(if-expr eb et ef)
     (and (expr? eb) (expr? et) (expr? ef))]
    [(cons-expr e1 e2) (and (expr? e1) (expr? e2))]
    [(car-expr e) (expr? e)]
    [(cdr-expr e) (expr? e)]
    [(null-expr) true]
    [(null?-expr e) (expr? e)]
    [(app f e) (and (expr? f) (expr? e))] 
    [(lam id e) (and (symbol? id) (expr? e))] 
    [_ false]))

(define (parse q)
  (cond
    [(number? q) (const q)]
    [(eq? q 'true)  (const true)]
    [(eq? q 'false) (const false)]
    [(eq? q 'null)  (null-expr)]
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'null?))
     (null?-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'cons))
     (cons-expr (parse (second q))
                (parse (third q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'car))
     (car-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'cdr))
     (cdr-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]
    [(and (list? q) (eq? (length q) 4) (eq? (first q) 'if))
     (if-expr (parse (second q))
              (parse (third q))
              (parse (fourth q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'lambda)) 
     (parse-lam (second q) (third q))]
    [(and (list? q) (pair? q) (not (op->proc (car q))))
     (parse-app q)]
    [(and (list? q) (eq? (length q) 3) (symbol? (first q)))
     (binop (first q)
            (parse (second q))
            (parse (third q)))]))

(define (parse-app q) ; 
  (define (parse-app-accum q acc)
    (cond [(= 1 (length q)) (app acc (parse (car q)))]
          [else (parse-app-accum (cdr q) (app acc (parse (car q))))]))
  (parse-app-accum (cdr q) (parse (car q))))

(define (parse-lam pat e)
  (cond [(= 1 (length pat))
         (lam (car pat) (parse e))]
        [else
         (lam (car pat) (parse-lam (cdr pat) e))]))

; ---------- ;
; Srodowiska ;
; ---------- ;

(struct environ (xs) #:transparent)

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (cons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (car (car xs))) (cdr (car xs))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))

; --------- ;
; Ewaluacja ;
; --------- ;

(struct clo (id e env) #:transparent) 

(define (value? v)
  (or (number? v)
      (boolean? v)
      (and (pair? v) (value? (car v)) (value? (cdr v)))
      (null? v)
      (clo? v))) 

(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /] ['% modulo]
            ['= =] ['> >] ['>= >=] ['< <] ['<= <=]
            ['and (lambda (x y) (and x y))]
            ['or  (lambda (x y) (or  x y))]
            [_ false]))

(define (eval-env e env)
  (match e
    [(const n) n]
    [(binop op l r) ((op->proc op) (eval-env l env)
                                   (eval-env r env))]
    [(let-expr x e1 e2)
     (eval-env e2 (env-add x (eval-env e1 env) env))]
    [(var-expr x) (env-lookup x env)]
    [(if-expr eb et ef) (if (eval-env eb env)
                            (eval-env et env)
                            (eval-env ef env))]
    [(cons-expr e1 e2) (cons (eval-env e1 env)
                             (eval-env e2 env))]
    [(car-expr e) (car (eval-env e env))]
    [(cdr-expr e) (cdr (eval-env e env))]
    [(null-expr) null]
    [(null?-expr e) (null? (eval-env e env))]
    [(lam x e) (clo x e env)] 
    [(app f e) 
     (let ([vf (eval-env f env)]
           [ve (eval-env e env)])
       (match vf [(clo x body fun-env)
                  (eval-env body (env-add x ve fun-env))]))]))
                  

(define (eval e) (eval-env e env-embedded))

(define env-embedded (env-add 'curry 
                              (eval-env (parse '(lambda (f) 
                                               (lambda (x y) (f (cons x y))))) env-empty)
                              (env-add 'uncurry 
                                       (eval-env (parse '(lambda (f) 
                                                      (lambda (p) (f (car p) 
                                                                     (cdr p))))) env-empty) 
                                    
                                               
                     (env-add 'not (eval-env (parse '(lambda (q) (if q
                             false
                             true))) env-empty) env-empty))))

(define not-program
     '(not true)
     ;(not false)     
     ;(not (or true false))
     )

(eval (parse not-program))

```

> curry := (lambda (f x y) (f (cons x y)))
> uncurry := (lambda (f p) (f (car p) (cdr p)))
 
```
(define program3 '(let (f (lambda (p) (+ (car p) (cdr p))))
                    ((curry f) 2 3)))
(define program4 '(let (f (lambda (x y) (+ x y)))
                    ((uncurry f) (cons 1 2))))
                    
[uncurry (lambda (f) (lambda (p) (f (car p) (cdr p))))]
[curry (lambda (f) (lambda (x y) (f (cons x y))))]
```

## Ćwiczenie 10
    w 9 

## Ćwiczenie 11

```racket=
#lang racket

; --------- ;
; Wyrazenia ;
; --------- ;

(struct const      (val)      #:transparent)
(struct binop      (op l r)   #:transparent)
(struct var-expr   (id)       #:transparent)
(struct let-expr   (id e1 e2) #:transparent)
(struct if-expr    (eb et ef) #:transparent)
(struct cons-expr  (e1 e2)    #:transparent)
(struct car-expr   (e)        #:transparent)
(struct cdr-expr   (e)        #:transparent)
(struct null-expr  ()         #:transparent)
(struct null?-expr (e)        #:transparent)
(struct app        (f e)      #:transparent) 
(struct lam        (id e)     #:transparent) 
(struct let-lazy   (id e1 e2) #:transparent) ; <----- NOWE
(struct min-clo    (e env)    #:transparent) ; <----- NOWE

(define (expr? e)
  (match e
    [(const n) (or (number? n) (boolean? n))]
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(let-lazy x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(if-expr eb et ef)
     (and (expr? eb) (expr? et) (expr? ef))]
    [(cons-expr e1 e2) (and (expr? e1) (expr? e2))]
    [(car-expr e) (expr? e)]
    [(cdr-expr e) (expr? e)]
    [(null-expr) true]
    [(null?-expr e) (expr? e)]
    [(app f e) (and (expr? f) (expr? e))] ; 
    [(lam id e) (and (symbol? id) (expr? e))]
    [_ false]))

(define (parse q)
  (cond
    [(number? q) (const q)]
    [(eq? q 'true)  (const true)]
    [(eq? q 'false) (const false)]
    [(eq? q 'null)  (null-expr)]
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'null?))
     (null?-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'cons))
     (cons-expr (parse (second q))
                (parse (third q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'car))
     (car-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'cdr))
     (cdr-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]

    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let-lazy)) ; <---- NOWE
     (let-lazy (first (second q))
               (parse (second (second q)))
               (parse (third q)))]

    [(and (list? q) (eq? (length q) 4) (eq? (first q) 'if))
     (if-expr (parse (second q))
              (parse (third q))
              (parse (fourth q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'lambda)) 
     (parse-lam (second q) (third q))]
    [(and (list? q) (pair? q) (not (op->proc (car q)))) 
     (parse-app q)]
    [(and (list? q) (eq? (length q) 3) (symbol? (first q)))
     (binop (first q)
            (parse (second q))
            (parse (third q)))]))

(define (parse-app q)
  (define (parse-app-accum q acc)
    (cond [(= 1 (length q)) (app acc (parse (car q)))]
          [else (parse-app-accum (cdr q) (app acc (parse (car q))))]))
  (parse-app-accum (cdr q) (parse (car q))))

(define (parse-lam pat e) 
  (cond [(= 1 (length pat))
         (lam (car pat) (parse e))]
        [else
         (lam (car pat) (parse-lam (cdr pat) e))]))

; ---------- ;
; Srodowiska ;
; ---------- ;

(struct environ (xs) #:transparent)

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (cons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (car (car xs))) (cdr (car xs))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))

; --------- ;
; Ewaluacja ;
; --------- ;

(struct clo (id e env) #:transparent) 

(define (value? v)
  (or (number? v)
      (boolean? v)
      (and (pair? v) (value? (car v)) (value? (cdr v)))
      (null? v)
      (clo? v))) 

(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /] ['% modulo]
            ['= =] ['> >] ['>= >=] ['< <] ['<= <=]
            ['and (lambda (x y) (and x y))]
            ['or  (lambda (x y) (or  x y))]
            [_ false])) ; 

(define (eval-env e env)
  (match e
    [(const n) n]
    [(binop op l r) ((op->proc op) (eval-env l env)
                                   (eval-env r env))]
    [(let-expr x e1 e2)
     (eval-env e2 (env-add x (eval-env e1 env) env))]

    [(let-lazy x e1 e2)                                 ; <----- NOWE
     (eval-env e2 (env-add x (min-clo e1 env) env))]

    [(var-expr x)                                       ; <---- NOWE
        (let [(x-val (env-lookup x env))]
            (match x-val
                [(min-clo e env) (eval-env e env)]
                [_ x-val]))]

    [(if-expr eb et ef) (if (eval-env eb env)
                            (eval-env et env)
                            (eval-env ef env))]
    [(cons-expr e1 e2) (cons (eval-env e1 env)
                             (eval-env e2 env))]
    [(car-expr e) (car (eval-env e env))]
    [(cdr-expr e) (cdr (eval-env e env))]
    [(null-expr) null]
    [(null?-expr e) (null? (eval-env e env))]
    [(lam x e) (clo x e env)]
    [(app f e) 
     (let ([vf (eval-env f env)]
           [ve (eval-env e env)])
       (match vf [(clo x body fun-env)
                  (eval-env body (env-add x ve fun-env))]))]))

(define (eval e) (eval-env e env-empty))

(define test1 (parse '(let-lazy [x (+ 3 5)] (+ x x))))
(eval test1)

(define test2 (parse '(let-lazy [x 5] 
    (let-lazy [y (+ x 3)] (let-lazy [x 0] (+ x y))))))
(eval test2)

(define test3 (parse '(let-lazy [x (/ 1 0)] 5)))
(eval test3)
```

## Ćwiczenie 12

```racket=
#lang racket

; --------- ;
; Wyrazenia ;
; --------- ;

(struct const      (val)       #:transparent)
(struct binop      (op l r)    #:transparent)
(struct var-expr   (id)        #:transparent)
(struct let-expr   (id e1 e2)  #:transparent)
(struct if-expr    (eb et ef)  #:transparent)
(struct cons-expr  (e1 e2)     #:transparent)
(struct car-expr   (e)         #:transparent)
(struct cdr-expr   (e)         #:transparent)
(struct null-expr  ()          #:transparent)
(struct null?-expr (e)         #:transparent)
(struct app        (f e)       #:transparent) 
(struct lam        (id e)      #:transparent) 

(struct unfold     (seed step) #:transparent) ; <------------------ !!!
(struct scar       (expr)      #:transparent) ; <------------------ !!!
(struct scdr       (expr)      #:transparent) ; <------------------ !!!

(define (expr? e)
  (match e
    [(const n) (or (number? n) (boolean? n))]
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(if-expr eb et ef)
     (and (expr? eb) (expr? et) (expr? ef))]
    [(cons-expr e1 e2) (and (expr? e1) (expr? e2))]
    [(car-expr e) (expr? e)]
    [(cdr-expr e) (expr? e)]
    [(null-expr) true]
    [(null?-expr e) (expr? e)]
    [(app f e) (and (expr? f) (expr? e))] 
    [(lam id e) (and (symbol? id) (expr? e))]
    
    [(unfold seed step) (expr? seed) (expr? step) ]
    [(scar expr) (expr? expr)]                       ; <------------------ !!!
    [(scdr expr) (expr? expr)]
    
    [_ false]))

(define (parse q)
  (cond
    [(number? q) (const q)]
    [(eq? q 'true)  (const true)]
    [(eq? q 'false) (const false)]
    [(eq? q 'null)  (null-expr)]
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'null?))
     (null?-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'cons))
     (cons-expr (parse (second q))
                (parse (third q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'car))
     (car-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'cdr))
     (cdr-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]

    
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'unfold))
     (unfold (parse (second q)) (parse (third q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'scar))           ; <------------------ !!!
     (scar (parse (second q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'scdr))
     (scdr (parse (second q)))]

    
    [(and (list? q) (eq? (length q) 4) (eq? (first q) 'if))
     (if-expr (parse (second q))
              (parse (third q))
              (parse (fourth q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'lambda)) 
     (parse-lam (second q) (third q))]
    [(and (list? q) (pair? q) (not (op->proc (car q))))
     (parse-app q)]
    [(and (list? q) (eq? (length q) 3) (symbol? (first q)))
     (binop (first q)
            (parse (second q))
            (parse (third q)))]))

(define (parse-app q) 
  (define (parse-app-accum q acc)
    (cond [(= 1 (length q)) (app acc (parse (car q)))]
          [else (parse-app-accum (cdr q) (app acc (parse (car q))))]))
  (parse-app-accum (cdr q) (parse (car q))))

(define (parse-lam pat e) 
  (cond [(= 1 (length pat))
         (lam (car pat) (parse e))]
        [else
         (lam (car pat) (parse-lam (cdr pat) e))]))

; ---------- ;
; Srodowiska ;
; ---------- ;

(struct environ (xs) #:transparent)

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (cons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (car (car xs))) (cdr (car xs))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))

; --------- ;
; Ewaluacja ;
; --------- ;

(struct clo (id e env) #:transparent)

(define (value? v)
  (or (number? v)
      (boolean? v)
      (and (pair? v) (value? (car v)) (value? (cdr v)))
      (null? v)
      (unfold? v) ; <------------------ !!!
      (clo? v))) 


(define (op->proc op)
  (match op ['+ +] ['- -] ['* *] ['/ /] ['% modulo]
    ['= =] ['> >] ['>= >=] ['< <] ['<= <=]
    ['and (lambda (x y) (and x y))]
    ['or  (lambda (x y) (or  x y))]
    [_ false]))

(define (eval-env e env)
  (match e
    [(const n) n]
    [(binop op l r) ((op->proc op) (eval-env l env)
                                   (eval-env r env))]
    [(let-expr x e1 e2)
     (eval-env e2 (env-add x (eval-env e1 env) env))]
    [(var-expr x) (env-lookup x env)]
    [(if-expr eb et ef) (if (eval-env eb env)
                            (eval-env et env)
                            (eval-env ef env))]
    [(cons-expr e1 e2) (cons (eval-env e1 env)
                             (eval-env e2 env))]
    [(car-expr e) (car (eval-env e env))]
    [(cdr-expr e) (cdr (eval-env e env))]
    [(null-expr) null]
    
    [(unfold s f) (unfold (eval-env s env) (eval-env f env))]; <------------------------ !!!
    [(scar e)                   ; <------------------------------------------------ !!!
     (match (eval-env e env)
       [(unfold s (clo x body fun-env))
        (car (eval-env body (env-add x s fun-env)))]
       [_ (error "not a unfold")])]
    [(scdr e)                   ; <------------------------------------------------ !!!
     (match (eval-env e env)
       [(unfold s (clo x body fun-env))
        (unfold (cdr (eval-env body (env-add x s fun-env))) (clo x body fun-env))]
       [_ (error "not a unfold")])]

 
    [(null?-expr e) (null? (eval-env e env))]
    [(lam x e) (clo x e env)] 
    [(app f e) 
     (let ([vf (eval-env f env)]
           [ve (eval-env e env)])
       (match vf [(clo x body fun-env)
                  (eval-env body (env-add x ve fun-env))]))]))

(define (eval e) (eval-env e env-empty))

(eval (parse
  '(scar (unfold 0 (lambda (x) (cons (* x x) (+ 1 x)))))))

(eval (parse
  '(scar (scdr (unfold 0 (lambda (x) (cons (* x x) (+ 1 x))))))))

(eval (parse
  '(scar (scdr (scdr (unfold 0 (lambda (x) (cons (* x x) (+ 1
    x)))))))))

(eval (parse
  '(scar (scdr (scdr (scdr (unfold 0 (lambda (x) (cons (* x x) (+ 1
    x))))))))))

(eval (parse
  '(scar (scdr (scdr (scdr (scdr (unfold 0 (lambda (x) (cons (* x x)
    (+ 1 x)))))))))))


```