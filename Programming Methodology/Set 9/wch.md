# MP lista 9


|Nazwisko     | Imię       | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10| 11| 12|
|-------------|------------|---|---|---|---|---|---|---|---|---|---|---|---|
| Gunia       | Piotr      | X | X |   |   |   |   |   |   |   |   |   |   |
| Jarząbek    | Adam       |   |   |   |   | X | X |   |   |   |   |   |   |
| Kasprzak    | Kamil      | X | X |   |   |   |   |   |   |   |   |   |   |
| Kowalik     | Damian     |   |   |   |   |   |   |   |   |   | X |   | X |
| Leonowicz   | Mateusz    |   |   |   |   |   |   |   | X |   |   | X |   |
| Polaszek    | Bartosz    | X | X |   |   |   |   |   |   |   |   |   |   |
| Uniatowicz  | Aleksander |   |   |   |   |   |   |   |   | X | X |   |   |
| Wyrzykowska | Maria      |   |   |   | X |   |   | X |   |   |   |   |   |
| Zientara    | Maciej     |   | X | X |   |   |   |   |   |   |   |   |   |
| Zmarzły     | Paweł      |   |   |   |   |   |   |   | X | X |   |   |   |



## Ćwiczenie 1
> Kamil Kasprzak
```racket=
(define (mreverse! mli)
  (define (do-it lis)
    (if (null? (mcdr lis))
          (begin
            (set-mcar! mli (mcar lis))
            (mcdr mli))
          (let* ([old-var (mcar lis)]
                 [new-li (do-it (mcdr lis))])
            (begin
              (set-mcar! new-li old-var)
              (mcdr new-li)))
        ))
  (void (if (null? mli)
      null
      (do-it mli))))
      ;Testy
      
      (define my-list (cons 1 (cons 2 (cons 3 (cons 4 (cons 5 (cons 6 null)))))))
      (define (list->mlist li)
  (if (null? li)
      null
      (mcons (car li) (list->mlist (cdr li)))))

(define mlis (list->mlist my-list))

      mlis
      (mreverse! mlis)
      mlis
```

## Ćwiczenie 2
Maciej Zientara
```racket=
;#lang racket

(struct bdlist (v [prev #:mutable] [next #:mutable]) #:transparent)

(define (beginning bd) (if (eq? null (bdlist-prev bd))
                           bd
                           (beginning (bdlist-prev bd))))

(define (list->bdlist t)
  (define (change bd x)
    (cond
      [(eq? x null) (beginning bd)]
      [else (set-bdlist-next! bd (bdlist (car x) bd null)) (change (bdlist-next bd) (cdr x))])
    )
  (if (eq? t null) null (change (bdlist (car t) null null) (cdr t)))
)

;(list->bdlist '(1 2 3 4 5))    

(define (bdfilter fun bd)
  (define (func ret bd)
    (cond
      [(eq? null bd) (beginning ret)]
      [(fun (bdlist-v bd)) (cond
                             [(eq? ret null) (func (bdlist (bdlist-v bd) null null) (bdlist-next bd))]
                             [else (set-bdlist-next! ret (bdlist (bdlist-v bd) ret null)) (func (bdlist-next ret) (bdlist-next bd))])]
      [else (func ret (bdlist-next bd))])
    )
  (func null bd)
)
;(list->bdlist '(-1 2 1 -3 5))
;(bdfilter positive? (list->bdlist '(-1 2 -1 3 -5)))
;(bdfilter (lambda (x) (= 0 (modulo x 2))) (list->bdlist '(2 7 -1 -4 -5)))


(define (last b)
  (match b
    ['() null]
    [(bdlist _ _ '()) b]
    [(bdlist _ _ n) (last n)]))
(define (bdlist->revlist bs)
  (define (revlist bs)
    (if (null? bs)
        null
        (cons (bdlist-v bs) (revlist (bdlist-prev bs)))))
  (let ((b (last bs))) (revlist b)))


```
## Ćwiczenie 3
Maciej Zientara
```racket=

(define (cycle-bdlist! bd)
  (define (findlast x) (if (eq? (bdlist-next x) null) x (findlast (bdlist-next x))))
  (define last (findlast bd))
  (set-bdlist-prev! bd last)
  (set-bdlist-next! last bd)
  bd
)

(define (decycle-bdlist! bd)
  (set-bdlist-next! (bdlist-prev bd) null)
  (set-bdlist-prev! bd null)
  bd
)

;(cycle-bdlist! (list->bdlist '(1 2 3)))
;(decycle-bdlist! (cycle-bdlist! (list->bdlist '(1 2 3))))

```
## Ćwiczenie 4
>Maria Wyrzykowska
```racket= 
#lang racket


; --------- ;
; Wyrazenia ;
; --------- ;

(struct const       (val)      #:transparent)
(struct binop       (op l r)   #:transparent)
(struct var-expr    (id)       #:transparent)
(struct let-expr    (id e1 e2) #:transparent)
(struct letrec-expr (id e1 e2) #:transparent) 
(struct if-expr     (eb et ef) #:transparent)
(struct cons-expr   (e1 e2)    #:transparent)
(struct car-expr    (e)        #:transparent)
(struct cdr-expr    (e)        #:transparent)
(struct null-expr   ()         #:transparent)
(struct null?-expr  (e)        #:transparent)
(struct app         (f e)      #:transparent)
(struct lam         (id e)     #:transparent)
(struct sym-expr    (s)        #:transparent) ;<---

(define (expr? e)
  (match e
    [(const n) (or (number? n) (boolean? n))]
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(letrec-expr x e1 e2) 
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
    [(sym-expr s) (expr? s)] ;<----
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
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'quote))
          (sym-expr (second q))] ;<---
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
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'letrec)) 
     (letrec-expr (first (second q))
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
            (parse (third q)))]
    ))

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

(struct blackhole () #:transparent) 
(struct environ (xs) #:transparent)

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (mcons x v) (environ-xs env)))) 
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (mcar (car xs)))
             (match (mcdr (car xs))
               [(blackhole) (error "Stuck forever in a black hole!")]
               [x x])]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))
(define (env-update! x v xs)
  (define (assoc-update! xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (mcar (car xs))) (set-mcdr! (car xs) v)]
          [else (env-update! x v (cdr xs))]))
  (assoc-update! (environ-xs xs)))

; --------- ;
; Ewaluacja ;
; --------- ;

(struct clo (id e env) #:transparent)

(define (value? v)
  (or (number? v)
      (boolean? v)
      (and (pair? v) (value? (car v)) (value? (cdr v)))
      (null? v)
      (clo? v)
      (blackhole? v)
      (sym-expr? v))) ;<--

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
    [(sym-expr s) (sym-expr s)] ;<---
    [(letrec-expr x e1 e2)
     (let* ([new-env (env-add x (blackhole) env)]
            [v (eval-env e1 new-env)])
       (begin
          (env-update! x v new-env)
          (eval-env e2 new-env)))]
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

(define (eval e) (eval-env e env-empty))

(eval (parse '(quote x)))
(eval (parse '(quote aaa)))
(eval (parse '(quote (+ 0 aaa))))
```

     

## Ćwiczenie 5
Adam Jarząbek

```racket=
(define (MAP prog) `(letrec 
                [map 
                (lambda (proc xs)
                     (if (null? xs)
                          null
                          (cons (proc (car xs))
                                (map proc (cdr xs)))))]
                ,prog))

(eval (parse (MAP  '(map (lambda (x) (+ x 1)) (cons 1 (cons 2 (cons 3 null)))))))




(define FILTER '(letrec 
                    [filter
                    (lambda (pred xs)
                        (if (null? xs)
                            xs
                            (if (pred (car xs))
                                (cons (car xs) (filter pred (cdr xs)))
                                (filter pred (cdr xs)))))]
                  (filter
                   (lambda (x) (< x 0))
                   (cons -1 (cons 1 (cons 2 (cons -2 null)))))))
                   
(eval (parse FILTER))


(define APPEND '(letrec
                    [append 
                    (lambda (xs ys)
                      (if (null? xs)
                         ys
                         (cons (car xs)
                               (append (cdr xs) ys))))]
                    (append
                     (cons 1 (cons 2 null))
                     (cons 3 (cons 4 (cons 5 null))))))      

(eval (parse APPEND))

```

## Ćwiczenie 6
```racket=
(define (insert-sort xs)
    (define (insert x xs)
      (if (null? xs)
          (cons x null)
          (if (>= (car xs) x)
              (cons x xs)
              (cons (car xs) (insert x (cdr xs))))))
  (define (insert-sort-rec xs ys)
    (if (null? xs)
        ys
        (insert-sort-rec (cdr xs) (insert (car xs) ys))))
  (insert-sort-rec xs null))
  

(define (INSERT-SORT lst) `(letrec [insert (lambda (x xs)
                                       (if (null? xs)
                                           (cons x null)
                                           (if (>= (car xs) x)
                                               (cons x xs)
                                               (cons (car xs) (insert x (cdr xs))))))]
                       (letrec [insert-sort-rec (lambda (xs ys)
                                                  (if (null? xs)
                                                      ys
                                                      (insert-sort-rec (cdr xs) (insert (car xs) ys))))]
                         (insert-sort-rec ,lst null))))
  
(eval (parse (INSERT-SORT '(cons -1 (cons 1 (cons 2 (cons -2 null)))) )))


(define (randomlist n)
  (if (= n 0) 'null
      `(cons ,(random 100) ,(randomlist (- n 1)))))

(define (rlist n) ;; losowe listy w rackecie
  (if (= n 0) null
      (cons (random 100) (rlist (- n 1)))))


(time (length (eval (parse (INSERT-SORT (randomlist 1000))))))
(time (length (insert-sort (rlist 1000))))

```

## Ćwiczenie 7
> Maria Wyrzykowska

```racket= 
#lang racket


; --------- ;
; Wyrazenia ;
; --------- ;

(struct const       (val)      #:transparent)
(struct binop       (op l r)   #:transparent)
(struct var-expr    (id)       #:transparent)
(struct let-expr    (id e1 e2) #:transparent)
(struct letrec-expr (id e1 e2) #:transparent) 
(struct if-expr     (eb et ef) #:transparent)
(struct cons-expr   (e1 e2)    #:transparent)
(struct car-expr    (e)        #:transparent)
(struct cdr-expr    (e)        #:transparent)
(struct null-expr   ()         #:transparent)
(struct null?-expr  (e)        #:transparent)
(struct app         (f e)      #:transparent)
(struct lam         (id e)     #:transparent)
(struct display-expr (mesg)    #:transparent) ;<-----
(struct read-expr   ()         #:transparent) ;<-----
(struct begin-expr (xs)        #:transparent) ;<-----

(define (expr? e)
  (match e
    [(const n) (or (number? n) (boolean? n))]
    [(begin-expr xs) (andmap expr? xs)]
    [(binop op l r) (and (symbol? op) (expr? l) (expr? r))]
    [(var-expr x) (symbol? x)]
    [(let-expr x e1 e2)
     (and (symbol? x) (expr? e1) (expr? e2))]
    [(letrec-expr x e1 e2) 
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
    [(display-expr mesg) (expr? mesg)];<-----
    [(read-expr) true] ;<-----
    [(begin-expr xs) (expr? xs)] ;<-----
    [_ false]))

(define (parse q)
  (cond
    [(number? q) (const q)]
    [(eq? q 'true)  (const true)]
    [(eq? q 'false) (const false)]
    [(eq? q 'null)  (null-expr)]
    [(eq? q 'read) (read-expr)] ;<-----
    [(symbol? q) (var-expr q)]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'null?))
     (null?-expr (parse (second q)))]
    [(and (list? q) (> (length q) 2) (eq? (first q) 'begin)) ;<-----
     (begin-expr (map parse (cdr q)))] ;<-----
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'cons))
     (cons-expr (parse (second q))
                (parse (third q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'car))
     (car-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'cdr))
     (cdr-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 2) (eq? (first q) 'display)) ;<-----
     (display-expr (parse (second q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'let))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]
    [(and (list? q) (eq? (length q) 3) (eq? (first q) 'letrec))
     (letrec-expr (first (second q))
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

(struct blackhole () #:transparent) 
(struct environ (xs) #:transparent)

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (mcons x v) (environ-xs env)))) 
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (mcar (car xs))) 
             (match (mcdr (car xs))
               [(blackhole) (error "Stuck forever in a black hole!")]
               [x x])]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))
(define (env-update! x v xs)
  (define (assoc-update! xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (mcar (car xs))) (set-mcdr! (car xs) v)]
          [else (env-update! x v (cdr xs))]))
  (assoc-update! (environ-xs xs)))

; --------- ;
; Ewaluacja ;
; --------- ;

(struct clo (id e env) #:transparent)

(define (value? v)
  (or (number? v)
      (boolean? v)
      (and (pair? v) (value? (car v)) (value? (cdr v)))
      (null? v)
      (clo? v)
      (blackhole? v))) 

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
    [(begin-expr xs)
     (if (= (length xs) 1)
         (eval-env (car xs) env)
         (begin
           (eval-env (car xs) env)
           (eval-env (begin-expr (cdr xs)) env)))] ;<-----
    [(display-expr mesg)
     (display (eval-env mesg env))] ;<-----
    [(read-expr) (read)] ;<-----
    [(letrec-expr x e1 e2) 
     (let* ([new-env (env-add x (blackhole) env)]
            [v (eval-env e1 new-env)])
       (begin
          (env-update! x v new-env)
          (eval-env e2 new-env)))]
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

(define (eval e) (eval-env e env-empty))

(define program
  '(letrec [loop (lambda (x)
                   (let (n read)
                     (if (= n 0)
                         (display n)
                         (begin
                           (display n)
                           (loop x)))))]
     (loop 1)))
(eval (parse program))
```


## Ćwiczenie 8


## Ćwiczenie 9

> Paweł Zmarzły

```racket=
(define WHILE_FIB ; <- NOWE
  '{(f1 := 0)
    (f2 := 1)
    (while (> n 0)
      {(n := (- n 1))
        (tmp := (+ f1 f2))
        (f1 := f2)
        (f2 := tmp)})})

(define (fib n)
  (let* ([init-env (env-add 'n n env-empty)]
         [final-env
            (eval-while (parse-while WHILE_FIB) init-env)])
        (env-lookup 'f1 final-env)))

(define WHILE_GCD
  '(while (> b 0)
      {(c := b)
       (b := (% a b))
       (a := c)}))

(define (gcd a b)
  (let* ([init-env (env-add 'b b (env-add 'a a env-empty))]
         [final-env
            (eval-while (parse-while WHILE_GCD) init-env)])
        (env-lookup 'a final-env)))

(fib 7)
(gcd 5 8)
```

## Ćwiczenie 10
> Damian Kowalik
```racket=

;; część parse-while
[(and (list? q) (= (length q) 5) (eq? (car q) 'for)) ; <----- NOWE
 (parse-while `{,(second q)
                (while ,(third q)
                  {,(fifth q)
                   ,(fourth q)})})]


;; test
(define program '{(res := 1)
                  (for (i := 2) (<= i n) (i := (+ i 1))
                       (res := (* res i)))})

(define (fact-for n)
  (let ((new-env (eval-while (parse-while program) (env-add 'n n env-empty))))
       (env-lookup 'res new-env)))
       
(fact-for 6)

```

## Ćwiczenie 11
> Mateusz Leonowicz
```racket=

;; część parse-while
[(and (list? q) (= (length q) 2) (eq? (car q) '++) (symbol? (second q)))
       (assign (second q) (parse (list '+ 1 (second q))))]

(define INC_TEST
  '{(i := 0)
    (while (> t 0)
      {(++ i)
       (t := (- t 1))})})

(define (test n)
  (let* ([init-env  (env-add 't n env-empty)]
         [final-env
           (eval-while (parse-while INC_TEST) init-env)])
    (env-lookup 'i final-env)))

```
## Ćwiczenie 12
>Damian Kowalik

```racket=
(struct break-cmd ()      #:transparent) ; <- NOWE

(define (parse-while q)
  (cond
    [(eq? q 'skip) (skip)]
    [(eq? q 'break) (break-cmd)] ; <- NOWE
    [(null? q) (skip)]
    [(and (list? q) (= (length q) 3) (eq? (second q) ':=))
     (assign (first q)
             (parse (third q)))]
    [(and (list? q) (= (length q) 4) (eq? (car q) 'if))
     (if-cmd (parse (second q))
             (parse-while (third q))
             (parse-while (fourth q)))]
     [(and (list? q) (= (length q) 3) (eq? (car q) 'while))
      (while (parse (second q))
             (parse-while (third q)))]
     [(and (list? q) (= (length q) 2))
      (comp (parse-while (first q))
            (parse-while (second q)))]
     [(and (list? q) (> (length q) 2))
      (comp (parse-while (first q))
            (parse-while (cdr q)))]
     [else (error "while parse error")]))


; Pomysł: break musi zwracać env (żeby
; instrukcje w bloku przed break się wykonały).
; Ale musi oznaczyć go jakoś specjalnie, żeby
; instrukcje w bloku po env się nie wykonały.
; Blok == comp.
(struct break-env (env) #:transparent)  ; <- NOWE

(define (eval-while e env)
  (if (break-env? env) ; <- NOWE
    env ;; to nadal break-env
    (match e
      [(skip) env]
      [(break-cmd) (break-env env)] ; <- NOWE
      [(assign x e)
      (env-update x (eval-env e env) env)]
      [(if-cmd eb ct cf)
      (if (eval-env eb env)
          (eval-while ct env)
          (eval-while cf env))]
      
      ; <- NOWE
      [(while eb cb)
        (if (eval-env eb env)
            (let ((new-env (eval-while cb env)))
                 (if (break-env? new-env)
                     (break-env-env new-env)
                     (eval-while e new-env)))
            env)]

      [(comp c1 c2) (eval-while c2 (eval-while c1 env))])))


;; test
(define WHILE_FACT
  '{(i := 1)
    (control := 0)
    (while true
      {(i := (* i t))
       (t := (- t 1))
       (if (= t 0)
           {break
            (control := 1000)}
            skip)
       (control := (+ control 1))})})

(define (fact-control n)
  (let* ([init-env  (env-add 't n env-empty)]
         [final-env
           (eval-while (parse-while WHILE_FACT) init-env)])
    (cons (env-lookup 'i final-env) (env-lookup 'control final-env))))

(fact-control 5)

```