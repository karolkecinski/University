# MP lista 10

|Nazwisko     | Imię       | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
|-------------|------------|---|---|---|---|---|---|---|
| Jarząbek    | Adam       |   |   | X | X |   |   |   |
| Kowalik     | Damian     | X |   |   |   |   |   | X |
| Leonowicz   | Mateusz    |   |   |   |   | X | X |   |
| Uniatowicz  | Aleksander |   |   |   |   | X | X |   |
| Wyrzykowska | Maria      | X | X |   |   |   |   |   |
| Zientara    | Maciej     |   |   | X | X |   |   |   |
| Zmarzły     | Paweł      |   | X |   |   |   |   | X |





## Ćwiczenie 1

> Maria Wyrzykowska
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

(define (randomlist n)
  (if (= n 0) 'null
      `(cons ,(random 100) ,(randomlist (- n 1)))))

(define (rlist n) ;; losowe listy w rackecie
  (if (= n 0) null
      (cons (random 100) (rlist (- n 1)))))

(define MAP
  `(letrec [map (lambda (xs fun)
                  (if (null? xs)
                      null
                      (cons (fun (car xs)) (map (cdr xs) fun))))]
     (map ,(randomlist 1000) (lambda (x) (* x 2)))))

(define WHILE_FIB
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

(define (racket-fib n)
  (if (or (= n 1) (= n 2))
      1
      (+ (racket-fib (- n 1)) (racket-fib (- n 2)))))

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

(define (racket-gcd a b)
  (if ( = b 0)
      a
      (racket-gcd b (modulo a b))))

(time (length (eval (parse (INSERT-SORT (randomlist 1000))))))
(time (length (insert-sort (rlist 1000))))
(time (length (eval (parse MAP))))
(time (length (map (lambda (x) (* x 2)) (rlist 1000))))
(time (fib 20))
(time (racket-fib 20))
(time (gcd 116241 217))
(time (racket-gcd 116241 217))
```

## Ćwiczenie 2

> Paweł Zmarzły

https://docs.racket-lang.org/guide/pattern-macros.html
https://docs.racket-lang.org/reference/syntax-model.html

do error-composition.rkt:

```racket=
;; Runtime error
(struct runtime-error (reason))

(define (let-error-old c k) ; <- ZMIANA
  (if (runtime-error? c) c (k c)))

(define-syntax let-error ; <- NOWE
  (syntax-rules ()
    [(let-error [x e1] e2 ...)
      (let-error-old e1 (lambda (x) e2 ...))]))

;...

;; Ewaluator
;; -- zwraca wartość lub runtime error
(define (eval-env e env)
  (match e
    [(const n)
     n]

    [(var-expr x)
     (env-lookup x env)]

    [(let-expr x e1 e2)
     (let ((v1 (eval-env e1 env))) ; <- to też można by zmienić
       (if (runtime-error? v1)
           v1
           (eval-env e2 (env-add x v1 env))))]

    [(letrec-expr f ef eb)
     (let ((new-env (env-add f (blackhole) env)))
       (let-error [vf (eval-env ef new-env)]
                      (env-update! f vf new-env)
                      (eval-env eb new-env)))]

    [(if-expr eb et ef)
     (let-error [vb (eval-env eb env)]
         (match vb
           [#t (eval-env et env)]
           [#f (eval-env ef env)]
           [v  (error "Not a boolean:" v)]))]

    [(lambda-expr x e)
     (clo x e env)]

    [(app-expr ef ea)
     (let-error [vf (eval-env ef env)]
     (let-error [va (eval-env ea env)]
       (match vf
         [(clo x e env)
          (eval-env e (env-add x va env))]
         [(builtin p args nm)
          (if (= nm 1)
              (apply p (reverse (cons va args)))
              (builtin p (cons va args) (- nm 1)))]
         [_ (error "Not a function:" vf)])))]))

(define (eval e)
  (eval-env e start-env))

(eval (parse '(letrec (test (lambda (x) 5)) (test 1))))
```

## Ćwiczenie 3
Maciej Zientara
```racket=
#lang racket

;; Składnia abstrakcyjna
(struct const (val)                 #:transparent)
(struct var-expr (name)             #:transparent)
(struct let-expr (id bound body)    #:transparent)
(struct letrec-expr (id bound body) #:transparent)
(struct if-expr  (eb et ef)         #:transparent)
(struct lambda-expr (arg body)      #:transparent)
(struct app-expr (fun arg)          #:transparent)
(struct handle (e1 e2)              #:transparent);<-----------!!!

(define (keyword s)
  (member s '(true false null and or if cond else lambda let letrec)))

(define (expr? e)
  (match e
    [(const n)           (or (number? n)
                             (boolean? n)
                             (null? n)
                             (string? n))]
    [(var-expr id)       (symbol? id)]
    [(let-expr x e1 e2 ) (and (symbol? x)
                              (expr? e1)
                              (expr? e2))]
    [(letrec-expr x e1 e2) (and (symbol? x)
                                (expr? e1)
                                (expr? e2))]
    [(if-expr eb et ef)  (and (expr? eb)
                              (expr? et)
                              (expr? ef))]
    [(lambda-expr x e)   (and (symbol? x)
                              (expr? e))]
    [(app-expr ef ea)    (and (expr? ef)
                              (expr? ea))]
    [(handle e1 e2)     (and (expr? e1) 
                            (expr? e2))]
    [_                   false]))

;; Parsowanie (zacytowane wyrażenie -> składnia abstrakcyjna)
(define (parse q)
  (cond
    [(number? q)     (const q)]
    [(string? q)     (const q)]
    [(eq? q 'true)   (const true)]
    [(eq? q 'false)  (const false)]
    [(eq? q 'null)   (const null)]
    [(and (symbol? q)
          (not (keyword q)))
     (var-expr q)]
    [(and (list? q)
          (= (length q) 3)
          (eq? (first q) 'let)
          (list? (second q))
          (= (length (second q)) 2)
          (symbol? (first (second q))))
     (let-expr (first (second q))
               (parse (second (second q)))
               (parse (third q)))]
    [(and (list? q)
          (= (length q) 3)
          (eq? (first q) 'letrec)
          (list? (second q))
          (= (length (second q)) 2)
          (symbol? (first (second q))))
     (letrec-expr (first (second q))
                  (parse (second (second q)))
                  (parse (third q)))]
    [(and (list? q)
          (= (length q) 4)
          (eq? (first q) 'if))
     (if-expr (parse (second q))
              (parse (third q))
              (parse (fourth q)))]
    [(and (list? q)
          (= (length q) 3)
          (eq? (first q) 'handle)) ; <---------- NOWE
     (handle (parse (second q))
              (parse (third q)))]
    [(and (list? q)
          (pair? q)
          (eq? (first q) 'and))
     (desugar-and (map parse (cdr q)))]
    [(and (list? q)
          (pair? q)
          (eq? (first q) 'or))
     (desugar-or (map parse (cdr q)))]
    [(and (list? q)
          (>= (length q) 2)
          (eq? (first q) 'cond))
     (parse-cond (cdr q))]
    [(and (list? q)
          (= (length q) 3)
          (eq? (first q) 'lambda)
          (list? (second q))
          (andmap symbol? (second q))
          (cons? (second q)))
     (desugar-lambda (second q) (parse (third q)))]
    [(and (list? q)
          (>= (length q) 2))
     (desugar-app (parse (first q)) (map parse (cdr q)))]
    [else (error "Unrecognized token:" q)]))

(define (parse-cond qs)
  (match qs
    [(list (list 'else q))
     (parse q)]

    [(list (list q _))
     (error "Expected 'else' in last branch but found:" q)]

    [(cons (list qb qt) qs)
     (if-expr (parse qb) (parse qt) (parse-cond qs))]))

(define (desugar-and es)
  (if (null? es)
      (const true)
      (if-expr (car es) (desugar-and (cdr es)) (const false))))

(define (desugar-or es)
  (if (null? es)
      (const false)
      (if-expr (car es) (const true) (desugar-or (cdr es)))))

(define (desugar-lambda xs e)
  (if (null? xs)
      e
      (lambda-expr (car xs) (desugar-lambda (cdr xs) e))))

(define (desugar-app e es)
  (if (null? es)
      e
      (desugar-app (app-expr e (car es)) (cdr es))))

;; Środowiska
(struct blackhole ())
(struct environ (xs))

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (mcons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs)
           (error "Unknown identifier" x)]
          [(eq? x (mcar (car xs)))
           (let ((v (mcdr (car xs))))
             (if (blackhole? v)
                 (error "Jumped into blackhole at" x)
                 v))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))
(define (env-update! x v env)
  (define (assoc-update xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (mcar (car xs)))
           (set-mcdr! (car xs) v)]
          [else (assoc-update (cdr xs))]))
  (assoc-update (environ-xs env)))

;; Domknięcia
(struct clo (arg body env) #:transparent)

;; Procedury wbudowane, gdzie
;; proc — Racketowa procedura którą należy uruchomić
;; args — lista dotychczas dostarczonych argumentów
;; pnum — liczba brakujących argumentów (> 0)
;; W ten sposób pozwalamy na częściową aplikację Racketowych procedur
;; — zauważmy że zawsze znamy pnum, bo w naszym języku arność
;; procedury jest ustalona z góry
(struct builtin (proc args pnum) #:transparent)

;; Pomocnicze konstruktory procedur unarnych i binarnych
(define (builtin/1 p)
  (builtin p null 1))
(define (builtin/2 p)
  (builtin p null 2))

;; Procedury
(define (proc? v)
  (or (and (clo? v)
           (symbol?  (clo-arg v))
           (expr?    (clo-body v))
           (environ? (clo-env v)))
      (and (builtin? v)
           (procedure? (builtin-proc v))
           (andmap value? (builtin-args v))
           (natural? (builtin-pnum v))
           (> (builtin-pnum v) 0))))

;; Definicja typu wartości
(define (value? v)
  (or (number? v)
      (boolean? v)
      (null? v)
      (string? v)
      (and (cons? v)
           (value? (car v))
           (value? (cdr v)))
      (proc? v)))

;; Runtime error
(struct runtime-error (reason) #:transparent)

(define (let-error c k)
  (if (runtime-error? c) c (k c)))

;; Środowisko początkowe (przypisujące procedury wbudowane ich nazwom)

(define start-env
  (foldl (lambda (p env) (env-add (first p) (second p) env))
         env-empty
         `((+        ,(builtin/2 +))
           (-        ,(builtin/2 -))
           (*        ,(builtin/2 *))
           (/        ,(builtin/2 /))
           (~        ,(builtin/1 -))
           (<        ,(builtin/2 <))
           (>        ,(builtin/2 >))
           (=        ,(builtin/2 =))
           (<=       ,(builtin/2 <=))
           (>=       ,(builtin/2 >=))
           (not      ,(builtin/1 not))
           (cons     ,(builtin/2 cons))
           (car      ,(builtin/1 car))
           (cdr      ,(builtin/1 cdr))
           (pair?    ,(builtin/1 cons?))
           (null?    ,(builtin/1 null?))
           (boolean? ,(builtin/1 boolean?))
           (number?  ,(builtin/1 number?))
           (procedure? ,(builtin/1 (lambda (x) (or (clo? x) (builtin? x)))))
           (string?  ,(builtin/1 string?))
           (string-= ,(builtin/2 string=?))
           (error    ,(builtin/1 runtime-error))
           ;; and so on, and so on
           )))

;; Ewaluator
;; -- zwraca wartość lub runtime error
(define (eval-env e env)
  (match e
    [(const n)
     n]

    [(var-expr x)
     (env-lookup x env)]

    [(let-expr x e1 e2)
     (let ((v1 (eval-env e1 env)))
       (if (runtime-error? v1)
           v1
           (eval-env e2 (env-add x v1 env))))]

    [(letrec-expr f ef eb)
     (let ((new-env (env-add f (blackhole) env)))
       (let-error (eval-env ef new-env)
         (lambda (vf) (env-update! f vf new-env)
                      (eval-env eb new-env))))]

    [(handle e1 e2) (let ((eval1 (eval-env e1 env)));<-----------!!!
                      (if (runtime-error? eval1)
                          (eval-env e2 env)
                          eval1))]
    
    [(if-expr eb et ef)
     (let-error (eval-env eb env)
       (lambda (vb)
         (match vb
           [#t (eval-env et env)]
           [#f (eval-env ef env)]
           [v  (error "Not a boolean:" v)])))]

    [(lambda-expr x e)
     (clo x e env)]

    [(app-expr ef ea)
     (let-error (eval-env ef env) (lambda (vf)
     (let-error (eval-env ea env) (lambda (va)
       (match vf
         [(clo x e env)
          (eval-env e (env-add x va env))]
         [(builtin p args nm)
          (if (= nm 1)
              (apply p (reverse (cons va args)))
              (builtin p (cons va args) (- nm 1)))]
         [_ (error "Not a function:" vf)])))))]))

(define (eval e)
  (eval-env e start-env))

(eval (parse '(handle "proper" "error")))
(eval (parse '(handle (error "error") "proper")))








;; REPL — interpreter interaktywny (read-eval-print loop)

;; dodajemy składnię na wiązanie zmiennych "na poziomie interpretera"
;; i komendę wyjścia "exit" ...
(struct letrec-repl (id expr))
(struct let-repl (id expr))
(struct exit-repl ())

;; ... a także rozszerzoną procedurę parsującą te dodatkowe komendy i
;; prostą obsługę błędów
(define (parse-repl q)
  (with-handlers
      ([exn? (lambda (exn)
               (display "Parse error! ")
               (displayln (exn-message exn)))])
    (cond
      [(eq? q 'exit)   (exit-repl)]
      [(and (list? q)
            (= 3 (length q))
            (eq? (first q) 'let))
       (let-repl (second q) (parse (third q)))]
      [(and (list? q)
            (= 3 (length q))
            (eq? (first q) 'letrec))
       (letrec-repl (second q) (parse (third q)))]
      [else (parse q)])))

;; trochę zamieszania w procedurze eval-repl wynika z rudymentarnej
;; obsługi błędów: nie chcemy żeby błąd w interpretowanym programie
;; kończył działanie całego interpretera!
(define (eval-repl c env continue)
  (define (eval-with-err e env)
    (with-handlers
        ([exn? (lambda (exn)
                 (display "Error! ")
                 (displayln (exn-message exn)))])
      (let ((a (eval-env e env)))
        (if (runtime-error? a)
            (begin
              (displayln "runtime-error: ")
              (displayln (runtime-error-reason a)))
            a))))
  (match c
    [(exit-repl)
     (void)]

    [(let-repl x e)
     (let ((v (eval-with-err e env)))
       (if (void? v)
           (continue env)
           (continue (env-add x v env))))]

    [(letrec-repl f e)
     (let* ((new-env (env-add f (blackhole) env))
            (v       (eval-with-err e new-env)))
       (if (void? v)
           (continue env)
           (begin
             (env-update! f v new-env)
             (continue new-env))))]

    [_
     (let ((v (eval-with-err c env)))
       (unless (void? v)
         (displayln v))
       (continue env))]))

;; I w końcu interaktywny interpreter
(define (repl)
  (define (go env)
    (display "FUN > ")
    (let* ((q (read))
           (c (parse-repl q)))
      (if (void? c)
          (go env)
          (eval-repl c env go))))
  (displayln "Welcome to the FUN functional language interpreter!")
  (go start-env))


```
## Ćwiczenie 4
>Adam Jarząbek
```racket=
(define start-env
  (foldl (lambda (p env) (env-add (first p) (second p) env))
         env-empty
         `((+        ,(builtin/2 +))
           (-        ,(builtin/2 -))
           (*        ,(builtin/2 *))
           (/        ,(builtin/2 /))
           (~        ,(builtin/1 -))
           (<        ,(builtin/2 <))
           (>        ,(builtin/2 >))
           (=        ,(builtin/2 =))
           (<=       ,(builtin/2 <=))
           (>=       ,(builtin/2 >=))
           (not      ,(builtin/1 not))
           (cons     ,(builtin/2 cons))
           (car      ,(builtin/1 car))
           (cdr      ,(builtin/1 cdr))
           (pair?    ,(builtin/1 cons?))
           (null?    ,(builtin/1 null?))
           (boolean? ,(builtin/1 boolean?))
           (number?  ,(builtin/1 number?))
           (procedure? ,(builtin/1 (lambda (x) (or (clo? x) (builtin? x)))))
           (string?  ,(builtin/1 string?))
           (string-= ,(builtin/2 string=?))
           (fail     ,(builtin (lambda (x) null) null 1))  ; <------ zadanie 4
           ;; and so on, and so on
           )))
           

(eval (parse '(fail 1)))

```
## Ćwiczenie 5

> Aleksander Uniatowicz


```racket=

; nondeterminism-monadic.rkt

; stara wersja
(define effect-env
  (foldl (lambda (p env) (env-add (first p) (second p) env))
         start-env
         `((choose ,(effect-builtin/2 (lambda (x y) (list x y))))
           )))

; nowa wersja 
(define effect-env
  (foldl (lambda (p env) (env-add (first p) (second p) env))
         start-env
         `((choose ,(effect-builtin/1 identity)) ; <- NOWE
           (fail ,(effect-builtin/1 (lambda (x) null)))
           )))

```
## Ćwiczenie 6

> Mateusz Leonowicz

```racket=
#lang racket

; nondeterminism-monadic.rkt

;; Składnia abstrakcyjna
(struct const (val)                 #:transparent)
(struct var-expr (name)             #:transparent)
(struct let-expr (id bound body)    #:transparent)
(struct letrec-expr (id bound body) #:transparent)
(struct if-expr  (eb et ef)         #:transparent)
(struct lambda-expr (arg body)      #:transparent)
(struct app-expr (fun arg)          #:transparent)

(define (keyword s)
  (member s '(true false null and or if cond else lambda let letrec)))

(define (expr? e)
  (match e
    [(const n)           (or (number? n)
                             (boolean? n)
                             (null? n)
                             (string? n))]
    [(var-expr id)       (symbol? id)]
    [(let-expr x e1 e2 ) (and (symbol? x)
                              (expr? e1)
                              (expr? e2))]
    [(letrec-expr x e1 e2) (and (symbol? x)
                                (expr? e1)
                                (expr? e2))]
    [(if-expr eb et ef)  (and (expr? eb)
                              (expr? et)
                              (expr? ef))]
    [(lambda-expr x e)   (and (symbol? x)
                              (expr? e))]
    [(app-expr ef ea)    (and (expr? ef)
                              (expr? ea))]
    [_                   false]))

;; Parsowanie (zacytowane wyrażenie -> składnia abstrakcyjna)
(define (parse q)
  (cond
   [(number? q)     (const q)]
   [(string? q)     (const q)]
   [(eq? q 'true)   (const true)]
   [(eq? q 'false)  (const false)]
   [(eq? q 'null)   (const null)]
   [(and (symbol? q)
         (not (keyword q)))
    (var-expr q)]
   [(and (list? q)
         (= (length q) 3)
         (eq? (first q) 'let)
         (list? (second q))
         (= (length (second q)) 2)
         (symbol? (first (second q))))
    (let-expr (first (second q))
              (parse (second (second q)))
              (parse (third q)))]
   [(and (list? q)
         (= (length q) 3)
         (eq? (first q) 'letrec)
         (list? (second q))
         (= (length (second q)) 2)
         (symbol? (first (second q))))
    (letrec-expr (first (second q))
                 (parse (second (second q)))
                 (parse (third q)))]
   [(and (list? q)
         (= (length q) 4)
         (eq? (first q) 'if))
    (if-expr (parse (second q))
             (parse (third q))
             (parse (fourth q)))]
   [(and (list? q)
         (pair? q)
         (eq? (first q) 'and))
    (desugar-and (map parse (cdr q)))]
   [(and (list? q)
         (pair? q)
         (eq? (first q) 'or))
    (desugar-or (map parse (cdr q)))]
   [(and (list? q)
         (>= (length q) 2)
         (eq? (first q) 'cond))
    (parse-cond (cdr q))]
   [(and (list? q)
         (= (length q) 3)
         (eq? (first q) 'lambda)
         (list? (second q))
         (andmap symbol? (second q))
         (cons? (second q)))
    (desugar-lambda (second q) (parse (third q)))]
   [(and (list? q)
         (>= (length q) 2))
    (desugar-app (parse (first q)) (map parse (cdr q)))]
   [else (error "Unrecognized token:" q)]))

(define (parse-cond qs)
  (match qs
    [(list (list 'else q))
     (parse q)]

    [(list (list q _))
     (error "Expected 'else' in last branch but found:" q)]

    [(cons (list qb qt) qs)
     (if-expr (parse qb) (parse qt) (parse-cond qs))]))

(define (desugar-and es)
  (if (null? es)
      (const true)
      (if-expr (car es) (desugar-and (cdr es)) (const false))))

(define (desugar-or es)
  (if (null? es)
      (const false)
      (if-expr (car es) (const true) (desugar-or (cdr es)))))

(define (desugar-lambda xs e)
  (if (null? xs)
      e
      (lambda-expr (car xs) (desugar-lambda (cdr xs) e))))

(define (desugar-app e es)
  (if (null? es)
      e
      (desugar-app (app-expr e (car es)) (cdr es))))

;; Środowiska
(struct blackhole ())
(struct environ (xs))

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (mcons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs)
           (error "Unknown identifier" x)]
          [(eq? x (mcar (car xs)))
           (let ((v (mcdr (car xs))))
             (if (blackhole? v)
                 (error "Jumped into blackhole at" x)
                 v))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))
(define (env-update! x v env)
  (define (assoc-update xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (mcar (car xs)))
           (set-mcdr! (car xs) v)]
          [else (assoc-update (cdr xs))]))
  (assoc-update (environ-xs env)))

;; Domknięcia
(struct clo (arg body env))

;; Procedury wbudowane, gdzie
;; proc — Racketowa procedura którą należy uruchomić
;; args — lista dotychczas dostarczonych argumentów
;; pnum — liczba brakujących argumentów (> 0)
;; W ten sposób pozwalamy na częściową aplikację Racketowych procedur
;; — zauważmy że zawsze znamy pnum, bo w naszym języku arność
;; procedury jest ustalona z góry
(struct builtin (proc args pnum) #:transparent)

;; Pomocnicze konstruktory procedur unarnych i binarnych
(define (builtin/1 p)
  (builtin (lambda (x) (return (p x))) null 1))
(define (builtin/2 p)
  (builtin (lambda (x y) (return (p x y))) null 2))

;; Procedury
(define (proc? v)
  (or (and (clo? v)
           (symbol?  (clo-arg v))
           (expr?    (clo-body v))
           (environ? (clo-env v)))
      (and (builtin? v)
           (procedure? (builtin-proc v))
           (andmap value? (builtin-args v))
           (natural? (builtin-pnum v))
           (> (builtin-pnum v) 0))))

;; Definicja typu wartości
(define (value? v)
  (or (number? v)
      (boolean? v)
      (null? v)
      (string? v)
      (and (cons? v)
           (value? (car v))
           (value? (cdr v)))
      (proc? v)))

;; Środowisko początkowe (przypisujące procedury wbudowane ich nazwom)

(define start-env
  (foldl (lambda (p env) (env-add (first p) (second p) env))
         env-empty
         `((+        ,(builtin/2 +))
           (-        ,(builtin/2 -))
           (*        ,(builtin/2 *))
           (/        ,(builtin/2 /))
           (~        ,(builtin/1 -))
           (<        ,(builtin/2 <))
           (>        ,(builtin/2 >))
           (=        ,(builtin/2 =))
           (<=       ,(builtin/2 <=))
           (>=       ,(builtin/2 >=))
           (not      ,(builtin/1 not))
           (cons     ,(builtin/2 cons))
           (car      ,(builtin/1 car))
           (cdr      ,(builtin/1 cdr))
           (pair?    ,(builtin/1 cons?))
           (null?    ,(builtin/1 null?))
           (boolean? ,(builtin/1 boolean?))
           (number?  ,(builtin/1 number?))
           (procedure? ,(builtin/1 (lambda (x) (or (clo? x) (builtin? x)))))
           (string?  ,(builtin/1 string?))
           (string-= ,(builtin/2 string=?))
           ;; and so on, and so on
           )))

;; Efekt

(define (effect-builtin/1 p)
  (builtin p null 1))
(define (effect-builtin/2 p)
  (builtin p null 2))

(define effect-env
  (foldl (lambda (p env) (env-add (first p) (second p) env))
         start-env
         `((choose ,(effect-builtin/1 identity)) ; <- NOWE
           (fail ,(effect-builtin/1 (lambda (x) null)))
           )))

; FUN > (+ 2 (choose (cons 1 (cons 2 null))))
; (3 4)

(define (bind c k) (append-map k c))

(define (return x) (list x))

;; Ewaluator
(define (eval-env e env)
  (match e
    [(const n)
     (return n)]

    [(var-expr x)
     (return (env-lookup x env))]

    [(let-expr x e1 e2)
     (bind (eval-env e1 env) (lambda (v1)
       (eval-env e2 (env-add x v1 env))))]

    [(letrec-expr f ef eb)
     (let ((new-env (env-add f (blackhole) env)))
     (bind (eval-env ef new-env) (lambda (vf)
       (env-update! f vf new-env)
       (eval-env eb new-env))))]

    [(if-expr eb et ef)
     (bind (eval-env eb env) (lambda (vb)
     (match vb
       [#t   (eval-env et env)]
       [#f   (eval-env ef env)]
       [v    (error "Not a boolean:" v)])))]

    [(lambda-expr x e)
     (return (clo x e env))]

    [(app-expr ef ea)
     (bind (eval-env ef env) (lambda (vf)
     (bind (eval-env ea env) (lambda (va)
       (match vf
         [(clo x e env)
          (eval-env e (env-add x va env))]
         [(builtin p args nm)
          (if (= nm 1)
              (apply p (reverse (cons va args)))
              (return (builtin p (cons va args) (- nm 1))))]
         [_ (error "Not a function:" vf)])))))]))

(define (eval e)
  (eval-env e effect-env))


;; Przykladowy program

(define hetmans
  '(letrec (from-n-to-1 (lambda (x)
                          (if (= 0 x)
                              null
                              (cons x (from-n-to-1 (- x 1))))))
     (letrec (my-filter (lambda (f x xs)
                          (if (null? xs)
                              null
                              (if (f x (car xs))
                                  (cons (car xs) (my-filter f x (cdr xs)))
                                  (my-filter f x (cdr xs))))))
       (letrec (length (lambda (xs)
                         (if (null? xs)
                             0
                             (+ 1 (length (cdr xs))))))
         (letrec (adjoin-position (lambda (row col)
                                    (cons col row)))
           (letrec (abs (lambda (x)
                          (if (< 0 x)
                              (- 0 x)
                              x)))
             (letrec (conflicts-with-other? (lambda (this other)
                                              (or
                                               (= (cdr this) (cdr other))              ; ten sam wiersz
                                               (= (abs (- (cdr this) (cdr other)))
                                                  (abs (- (car this) (car other))))))) ; ta sama przekątna
               (letrec (safe? (lambda (positions) 
                                (= 0 (length (my-filter
                                              conflicts-with-other?
                                              (car positions)
                                              (cdr positions))))))
                 (letrec (hetmans (lambda (n positions)
                                    (if (safe? positions)
                                        (if (= (length positions) n)
                                            positions
                                            (hetmans n (cons (cons (+ 1 (length positions)) (choose (from-n-to-1 n))) positions)))
                                        (fail false))))
                   ; Chcemy listę par (kolumna . wiersz).
                   (hetmans 8 (cons (cons 1 (choose (from-n-to-1 8))) null)))))))))))

;;(eval (parse test))
(eval (parse hetmans))
```
wersja dla choose dwuargumentowego 
```racket=
(define (queens n) ; n musi byc < 16; juz dla 8 trwa za dlugo
  `(letrec (my-choose (lambda (n)
                        (if (= n 1)
                            (choose 0 1)
                            (+ (* (my-choose (- n 1)) 2)
                               (choose 0 1)))))
     (let (emptyboard null)
       (let (adjoin-position (lambda (row rest) (cons row rest)))
         (letrec (helper (lambda (row down up positions)
                           (if (null? positions) null
                               (let (x (car positions))
                                 (if (= x row) (fail 1)
                                     (if (= x down) (fail 1)
                                         (if (= x up) (fail 1)
                                             (cons x (helper row
                                                             (- down 1)
                                                             (+ up 1)
                                                             (cdr positions))))))))))
           (letrec (queen-cols (lambda (k)
                                 (if (= k 0) emptyboard
                                     (let (new-row (my-choose 4))
                                       (if (>= new-row ,n)
                                           (fail 1)
                                           (adjoin-position new-row (helper
                                                                     new-row
                                                                     (- new-row 1)
                                                                     (+ new-row 1)
                                                                     (queen-cols (- k 1)))))))))
             (queen-cols ,n)))))))
```



## Ćwiczenie 7

```racket=
(define (bind state-modifier continue)
  (lambda (old-state)
    (let* [(result (state-modifier old-state))
           (value (car result))
           (state (cdr result))]
      ((continue value) state))))

(define (return x)
  (lambda (s) (cons x s)))

; użycie
(bind (eval-env e1 env) (lambda (v1)
    (eval-env e2 (env-add x v1 env))))
    
; Nowa procedura
(define (replace cell val memory)
  (define (iter acc xs)
    (match xs
      [(cons (cons (== cell) _) xs) 
       (append acc (list (cons cell val)) xs)]
      [(cons x xs) 
       (iter (cons x acc) xs)]
      ['() (error "Error! Not found:" cell)]))
  (iter null memory))

; Zmiany:
(define effect-env
  (foldl (lambda (p env) (env-add (first p) (second p) env))
         start-env
         
          ;; Po lewej wartość a po prawej nowy stan
         `((put ,(effect-builtin/2 (lambda (cell val) (lambda (memory)
             (cons false (replace cell val memory))))))
         
           (get ,(effect-builtin/1 (lambda (cell) (lambda (memory) 
              (let ((elem (assoc cell memory)))
                (if elem
                  (cons (cdr elem) memory)
                  (error "Error! Not found: " cell))))))))))


;(begin
;    (put "x" 10)
;    (put "y" 20)
;    (+ (begin (put "y" 30) (get "x"))
;       (get "y")))


(define TEST
  '(let (x (put "x" 10))
    (let (x (put "y" 20))
    (+
      (let (x (put "y" 30))
        (get "x"))
      (get "y")))))

((eval (parse TEST)) (list (cons "x" 0) (cons "y" 0)))
                  

(define PROGRAM
  '(letrec [rand (lambda (x) (let [x (get "x")]
                               (begin (put "x" (% (* (+ x 17) 37) 397))
                                      x)))]
   (letrec [gen (lambda (n) (if (= n 0) null
                                (cons (rand false) (gen (- n 1)))))]
     (gen 20))))

((eval (parse PROGRAM)) (list (cons "x" 5)))
```


wersja z cytowaniem, begin itd

```racket=
#lang racket

;; dodajemy wiele komórek pamieci identyfikowanych przez nazwy


;; Składnia abstrakcyjna
(struct const (val)                 #:transparent)
(struct var-expr (name)             #:transparent)
(struct let-expr (id bound body)    #:transparent)
(struct letrec-expr (id bound body) #:transparent)
(struct if-expr  (eb et ef)         #:transparent)
(struct lambda-expr (arg body)      #:transparent)
(struct app-expr (fun arg)          #:transparent)
(struct quote-expr  (e)        #:transparent) ;<------------------!!!

(define (keyword s)
  (member s '(true false null and or if cond else lambda let letrec)))

(define (expr? e)
  (match e
    [(const n)           (or (number? n)
                             (boolean? n)
                             (null? n)
                             (string? n))]
    [(var-expr id)       (symbol? id)]
    [(quote-expr e) (expr? e)]    ;<---------------------------------!!!
    [(let-expr x e1 e2 ) (and (symbol? x)
                              (expr? e1)
                              (expr? e2))]
    [(letrec-expr x e1 e2) (and (symbol? x)
                                (expr? e1)
                                (expr? e2))]
    [(if-expr eb et ef)  (and (expr? eb)
                              (expr? et)
                              (expr? ef))]
    [(lambda-expr x e)   (and (symbol? x)
                              (expr? e))]
    [(app-expr ef ea)    (and (expr? ef)
                              (expr? ea))]
    [_                   false]))

;; Parsowanie (zacytowane wyrażenie -> składnia abstrakcyjna)
(define (parse q)
  (cond
   [(number? q)     (const q)]
   [(string? q)     (const q)]
   [(eq? q 'true)   (const true)]
   [(eq? q 'false)  (const false)]
   [(eq? q 'null)   (const null)]
   [(and (symbol? q)
         (not (keyword q)))
    (var-expr q)]
   [(and (list? q) (eq? (length q) 2) (eq? (first q) 'quote)) ;<-----!!!
     (quote-expr (second q))]
   [(and (list? q)
         (= (length q) 3)
         (eq? (first q) 'let)
         (list? (second q))
         (= (length (second q)) 2)
         (symbol? (first (second q))))
    (let-expr (first (second q))
              (parse (second (second q)))
              (parse (third q)))]
   [(and (list? q)
         (= (length q) 3)
         (eq? (first q) 'letrec)
         (list? (second q))
         (= (length (second q)) 2)
         (symbol? (first (second q))))
    (letrec-expr (first (second q))
                 (parse (second (second q)))
                 (parse (third q)))]
   [(and (list? q)
         (= (length q) 4)
         (eq? (first q) 'if))
    (if-expr (parse (second q))
             (parse (third q))
             (parse (fourth q)))]
   [(and (list? q)
         (pair? q)
         (eq? (first q) 'and))
    (desugar-and (map parse (cdr q)))]
   [(and (list? q)
         (pair? q)
         (eq? (first q) 'or))
    (desugar-or (map parse (cdr q)))]
   [(and (list? q)
         (>= (length q) 2)
         (eq? (first q) 'cond))
    (parse-cond (cdr q))]
   [(and (list? q) (eq? (first q) 'begin)                     ;<---------------!!!
         (>= (length q) 2)) 
    (desugar-begin (map parse (cdr q)))]
   [(and (list? q)
         (= (length q) 3)
         (eq? (first q) 'lambda)
         (list? (second q))
         (andmap symbol? (second q))
         (cons? (second q)))
    (desugar-lambda (second q) (parse (third q)))]
   [(and (list? q)
         (>= (length q) 2))
    (desugar-app (parse (first q)) (map parse (cdr q)))]
   [else (error "Unrecognized token:" q)]))

(define (parse-cond qs)
  (match qs
    [(list (list 'else q))
     (parse q)]

    [(list (list q _))
     (error "Expected 'else' in last branch but found:" q)]

    [(cons (list qb qt) qs)
     (if-expr (parse qb) (parse qt) (parse-cond qs))]))

(define (desugar-begin es)  ;;<-----------------------------------!!!
  (if (null? (cdr es))
      (car es)
      (app-expr (app-expr (var-expr 'begin) (car es)) (desugar-begin (cdr es)))))

(define (desugar-and es)
  (if (null? es)
      (const true)
      (if-expr (car es) (desugar-and (cdr es)) (const false))))

(define (desugar-or es)
  (if (null? es)
      (const false)
      (if-expr (car es) (const true) (desugar-or (cdr es)))))

(define (desugar-lambda xs e)
  (if (null? xs)
      e
      (lambda-expr (car xs) (desugar-lambda (cdr xs) e))))

(define (desugar-app e es)
  (if (null? es)
      e
      (desugar-app (app-expr e (car es)) (cdr es))))

;; Środowiska
(struct blackhole ())
(struct environ (xs)   #:transparent) ;;<------------------------------!!!

(define env-empty (environ null))
(define (env-add x v env)
  (environ (cons (mcons x v) (environ-xs env))))
(define (env-lookup x env)
  (define (assoc-lookup xs)
    (cond [(null? xs)
           (error "Unknown identifier" x)]
          [(eq? x (mcar (car xs)))
           (let ((v (mcdr (car xs))))
             (if (blackhole? v)
                 (error "Jumped into blackhole at" x)
                 v))]
          [else (assoc-lookup (cdr xs))]))
  (assoc-lookup (environ-xs env)))
(define (env-update! x v env)
  (define (assoc-update xs)
    (cond [(null? xs) (error "Unknown identifier" x)]
          [(eq? x (mcar (car xs)))
           (set-mcdr! (car xs) v)]
          [else (assoc-update (cdr xs))]))
  (assoc-update (environ-xs env)))

(define (env-lookup-opt x env) ;<---------------------!!!
  (define (assoc-lookup xs)
    (cond [(null? xs)  false]
          [(eq? x (mcar (car xs)))
           (let ((v (mcdr (car xs))))
                 (if (number? v) v (error "not a number!" v )))]
          [else (assoc-lookup (cdr xs))]))
    (assoc-lookup (environ-xs env)))

(define (env-update-or-add x v env)
  (if (env-lookup-opt x env)
      (begin (env-update! x v env) env)
      (env-add x v env)))
  

;; Domknięcia
(struct clo (arg body env))

;; Procedury wbudowane, gdzie
;; proc — Racketowa procedura którą należy uruchomić
;; args — lista dotychczas dostarczonych argumentów
;; pnum — liczba brakujących argumentów (> 0)
;; W ten sposób pozwalamy na częściową aplikację Racketowych procedur
;; — zauważmy że zawsze znamy pnum, bo w naszym języku arność
;; procedury jest ustalona z góry
(struct builtin (proc args pnum) #:transparent)

;; Pomocnicze konstruktory procedur unarnych i binarnych
(define (builtin/1 p)
  (builtin (lambda (x) (return (p x))) null 1))
(define (builtin/2 p)
  (builtin (lambda (x y) (return (p x y))) null 2))

;; Procedury
(define (proc? v)
  (or (and (clo? v)
           (symbol?  (clo-arg v))
           (expr?    (clo-body v))
           (environ? (clo-env v)))
      (and (builtin? v)
           (procedure? (builtin-proc v))
           (andmap value? (builtin-args v))
           (natural? (builtin-pnum v))
           (> (builtin-pnum v) 0))))

;; Definicja typu wartości
(define (value? v)
  (or (number? v)
      (boolean? v)
      (null? v)
      (quote-expr? v)  ;<----------------------------------!!!
      (string? v)
      (and (cons? v)
           (value? (car v))
           (value? (cdr v)))
      (proc? v)))

;; Środowisko początkowe (przypisujące procedury wbudowane ich nazwom)

(define start-env
  (foldl (lambda (p env) (env-add (first p) (second p) env))
         env-empty
         `((+        ,(builtin/2 +))
           (-        ,(builtin/2 -))
           (*        ,(builtin/2 *))
           (/        ,(builtin/2 /))
           (~        ,(builtin/1 -))
           (<        ,(builtin/2 <))
           (>        ,(builtin/2 >))
           (=        ,(builtin/2 =))
           (<=       ,(builtin/2 <=))
           (>=       ,(builtin/2 >=))
           (%        ,(builtin/2 modulo))
           (not      ,(builtin/1 not))
           (cons     ,(builtin/2 cons))
           (car      ,(builtin/1 car))
           (cdr      ,(builtin/1 cdr))
           (pair?    ,(builtin/1 cons?))
           (null?    ,(builtin/1 null?))
           (boolean? ,(builtin/1 boolean?))
           (number?  ,(builtin/1 number?))
           (procedure? ,(builtin/1 (lambda (x) (or (clo? x) (builtin? x)))))
           (string?  ,(builtin/1 string?))
           (string-= ,(builtin/2 string=?))
           (begin    ,(builtin/2 (lambda (x y) y))) ; <----------- !!!
           ;; and so on, and so on
           )))

;; Efekt

(define (effect-builtin/1 p)
  (builtin p null 1))
(define (effect-builtin/2 p)
  (builtin p null 2))

(define effect-env
  (foldl (lambda (p env) (env-add (first p) (second p) env))
         start-env
         `((put ,(effect-builtin/2
                  (lambda (name val)    ;<----------------------!!!
                    (lambda (state)
                      (cons false (env-update-or-add
                                   (quote-expr-e name)
                                   val
                                   state))))))
           (get ,(effect-builtin/1
                  (lambda (name)        ;<----------------------!!!
                    (lambda (state) (cons (env-lookup
                                           (quote-expr-e name) state) state)))))
           )))

(define (bind c k)
  (lambda (s)
    (let ((r (c s)))
      ((k (car r)) (cdr r)))))

(define (return x)
  (lambda (s) (cons x s)))

;; Ewaluator
(define (eval-env e env)
  (match e
    [(const n)
     (return n)]

    [(var-expr x)
     (return (env-lookup x env))]

    [(quote-expr e1) (return e) ]   ;<-----------------------------!!!
   [(let-expr x e1 e2)
     (bind (eval-env e1 env) (lambda (v1)
       (eval-env e2 (env-add x v1 env))))]

    [(letrec-expr f ef eb)
     (let ((new-env (env-add f (blackhole) env)))
     (bind (eval-env ef new-env) (lambda (vf)
       (env-update! f vf new-env)
       (eval-env eb new-env))))]

    [(if-expr eb et ef)
     (bind (eval-env eb env) (lambda (vb) 
     (match vb
       [#t   (eval-env et env)]
       [#f   (eval-env ef env)]
       [v    (error "Not a boolean:" v)])))]

    [(lambda-expr x e)
     (return (clo x e env))]

    [(app-expr ef ea)
     (bind (eval-env ef env) (lambda (vf)
     (bind (eval-env ea env) (lambda (va)
       (match vf
         [(clo x e env)
          (eval-env e (env-add x va env))]
         [(builtin p args nm)
          (if (= nm 1)
              (apply p (reverse (cons va args)))
              (return (builtin p (cons va args) (- nm 1))))]
         [_ (error "Not a function:" vf)])))))]))

(define (eval e)
  (eval-env e effect-env))

(define (test1)
  ((eval (parse '(begin (put 'x 10) (get 'x)))) env-empty))

(define (test1a)
  ((eval (parse '(begin (put 'x 10) (put 'y 20) (get 'x)))) env-empty))

(define (test1b)
  ((eval (parse '(begin (put 'x 10) (put 'x 5) (get 'x)))) env-empty))



(define (test2)
  ((eval (parse '(begin
                   (put 'x 10)
                   (put 'y 20)
                   (+ (begin
                        (put 'y 30)
                        (get 'x))
                      (get 'y)))))
   env-empty))


(define (test3)
  ((eval (parse '(begin (put 'x 10) (get 'y)))) env-empty))
```