#lang racket

;; sygnatura słowników bez kontraktów
;(define-signature dict^
;  (dict? dict-empty? empty-dict dict-insert dict-remove dict-lookup))

;; sygnatura słowników z prostymi kontraktami
;(define-signature dict^
;  ((contracted
;    [dict?       (-> any/c boolean?)]
;    [dict-empty? (-> dict? boolean?)]
;    [empty-dict  (and/c dict? dict-empty?)]
;    [dict-insert (-> dict? string? any/c dict?)]
;    [dict-remove (-> dict? string? dict?)]
;    [dict-lookup (-> dict? string?
;                     (or/c (cons/c string? any/c) #f))])))

;; sygnatura słowników z kontraktami zależnymi
(define-signature dict^
  ((contracted
    [dict?       (-> any/c boolean?)]
    [dict-empty? (-> dict? boolean?)]
    [empty-dict  (and/c dict? dict-empty?)]
    [dict-insert (->i ([d dict?]
                       [k string?]
                       [v any/c])
                      [result (and/c dict? (not/c dict-empty?))]
                      #:post (result k v)
                      (let ((p (dict-lookup result k)))
                        (and
                          (pair? p)
                          (eq? (car p) k)
                          (eq? (cdr p) v))))]
    [dict-remove (->i ([d dict?]
                       [k string?])
                      [result dict?]
                      #:post (result k)
                      (eq? #f (dict-lookup result k)))]
    [dict-lookup (->i ([d dict?]
                       [k string?])
                     (result (or/c (cons/c string? any/c) #f))
                     #:post (result d)
                     (if (dict-empty? d) (eq? #f result) #t))])))
    
;; implementacja słowników na listach
(define-unit dict-list@
  (import)
  (export dict^)

  (define (dict? d)
    (and (list? d)
         (eq? (length d) 2)
         (eq? (car d) 'dict-list)))

  (define (dict-list d) (cadr d))
  (define (dict-cons l) (list 'dict-list l))
  
  (define (dict-empty? d)
    (eq? (dict-list d) '()))

  (define empty-dict (dict-cons '()))

  (define (dict-lookup d k) (assoc k (dict-list d)))

  (define (dict-remove d k)
    (dict-cons (remf (lambda (p) (eq? (car p) k)) (dict-list d))))

  (define (dict-insert d k v)
    (dict-cons (cons (cons k v)
                     (dict-list (dict-remove d k))))))

;; otwarcie implementacji słownika
(define-values/invoke-unit/infer dict-list@)

(define dx1 (dict-insert empty-dict "x" 1))
(define dx2 (dict-insert dx1 "x" 2))
(define dx1y2 (dict-insert dx1 "y" 2))

(require quickcheck)

;; funkcja budująca słownik z listy par
(define (list->dict l)
  (cond [(null? l) empty-dict]
        [else (dict-insert (list->dict (cdr l)) (caar l) (cdar l))]))

;; generator list klucz-wartość
(define arbitrary-dict-list
  (arbitrary-list (arbitrary-pair arbitrary-string arbitrary-integer)))

;; element po dodaniu do słownika jest w słowniku
(quickcheck
 (property
  ([l arbitrary-dict-list]
   [k arbitrary-string]
   [v arbitrary-integer])
  (let* ((d (list->dict l))
         (di (dict-insert d k v))
         (dl (dict-lookup di k)))
    (and (pair? dl)
         (eq? (car dl) k)
         (eq? (cdr dl) v)))))
