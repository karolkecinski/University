#lang racket

(require "props.rkt")
(provide falsifiable-cnf?)

(define (lit? f)
  (or (var? f)
      (and (neg? f)
           (var? (neg-subf f)))))

(define (lit-pos v)
  v)

(define (lit-neg v)
  (neg v))

(define (lit-var l)
  (if (var? l)
      l
      (neg-subf l)))

(define (lit-pos? l)
  (var? l))

(define (to-nnf f)
  (cond
   [(var? f)  (lit-pos f)]
   [(neg? f)  (to-nnf-neg (neg-subf f))]
   [(conj? f) (conj (to-nnf (conj-left f))
                    (to-nnf (conj-right f)))]
   [(disj? f) (disj (to-nnf (disj-left f))
                    (to-nnf (disj-right f)))]))

(define (to-nnf-neg f)
  (cond
   [(var? f)  (lit-neg f)]
   [(neg? f)  (to-nnf (neg-subf f))]
   [(conj? f) (disj (to-nnf-neg (conj-left f))
                    (to-nnf-neg (conj-right f)))]
   [(disj? f) (conj (to-nnf-neg (disj-left f))
                    (to-nnf-neg (disj-right f)))]))

(define (mk-cnf xss)
  (cons 'cnf xss))

(define (clause? f)
  (and (list? f)
       (andmap lit? f)))

(define (cnf? f)
  (and (pair? f)
       (eq? 'cnf (car f))
       (list? (cdr f))
       (andmap clause? (cdr f))))

(define (to-cnf f)
  (define (join xss yss)
    (apply append (map (lambda (xs) (map (lambda (ys) (append xs ys)) yss)) xss)))
  (define (go f)
    (cond
     [(lit? f)  (list (list f))]
     [(conj? f) (append (go (conj-left f))
                        (go (conj-right f)))]
     [(disj? f) (join (go (disj-left f))
                      (go (disj-right f)))]))
  (mk-cnf (go f)))

(define (free-vars f)
    (define (go f)
        (cond ((var? f)
               (list f))
              ((neg? f)
               (go (neg-subf f)))
              ((disj? f)
               (append (go (disj-left f)) 
                       (go (disj-rght f))))
              ((conj? f)
               (append (go (conj-left f)) 
                       (go (conj-rght f))))))
    (remove-duplicates (go f)))

(define (conj-left f)
    (second f))
    
(define (conj-rght f)
    (third f))
    
(define (disj-left f)
    (second f))
    
(define (disj-rght f)
    (third f))

(define (gen-vals xs)
  (if (null? xs)
      (list null)
      (let*
          ((vss  (gen-vals (cdr xs)))
           (x    (car xs))
           (vst  (map (lambda (vs) (cons (list x true)  vs)) vss))
           (vsf  (map (lambda (vs) (cons (list x false) vs)) vss)))
        (append vst vsf))))

(define (eval-formula f v)
    (cond
        ((var? f) (second (assoc f v)))
        ((neg? f) (not (eval-formula (neg-subf f) v)))
        ((conj? f) (and
            (eval-formula (conj-left f) v)
            (eval-formula (conj-rght f) v)))
        ((disj? f) (or
            (eval-formula (disj-left f) v)
            (eval-formula (disj-rght f) v)))
    ))

(define (falsifiable-eval? f)
    (define (is-false v)
        (not (eval-formula f v)))
    (let* [
        (vars (free-vars f))
        (all-vals (gen-vals vars))
        (false-vals (filter is-false all-vals))
        ]
        (if (= (length false-vals) 0)
            false
            (first false-vals))))


(define (falsifiable-cnf? f)
  (define (check_clause f)
    (cond [(null? (f)) #f]
          [(iter (car f) null)]
          [else (check_clause (cdr f))]))

  (define (iter lista wartosciowanie)
    (cond [(null? lista) wartosciowanie]
          [(var? (car lista)) (append wartosciowanie (list (car lista) #f))]
          [(neg? (car lista)) (append wartosciowanie (list (car lista) #t))]
          ))
  
  (check_clause (cdr f)))



(falsifiable-cnf? (to-cnf ('a)))