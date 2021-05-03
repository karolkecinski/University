#lang racket

;; struktury z których budujemy drzewa binarne
(struct node (v l r) #:transparent)
(struct leaf () #:transparent)

;; predykat: czy dana wartość jest drzewem binarnym?
(define/contract (tree? t)
  (-> any/c boolean?)
  (match t
    [(leaf) true]
    ; wzorzec _ dopasowuje się do każdej wartości
    [(node _ l r) (and (tree? l) (tree? r))]
    ; inaczej niż w (cond ...), jeśli żaden wzorzec się nie dopasował, match kończy się błędem
    [_ false]))

;; przykładowe użycie dopasowania wzorca
;; słaby kontrakt
(define/contract (insert-bst-weak v t)
  (-> any/c tree? tree?)
  (match t
    [(leaf) (node v (leaf) (leaf))]
    [(node w l r)
     (if (< v w)
         (node w (insert-bst v l) r)
         (node w l (insert-bst v r)))]))

;; kontrakt bycia drzewem
;; analogiczny do listof
;; nie działa -- nieskończona rekursja
(define (treeof-bad c)
  (or/c (struct/c leaf) (struct/c node c (treeof c) (treeof c))))

;; prawidłowa wersja
(define (treeof c)
  (flat-rec-contract tree
                     (struct/c leaf)
                     (struct/c node c tree tree)))

;; przykładowe drzewo
(define/contract tree1
  (treeof real?)
  (node 2 (node 1 (leaf) (leaf)) (leaf)))

;; insert-bst z lepszym kontraktem
(define/contract (insert-bst v t)
  (-> real? (treeof real?) (treeof real?))
  (match t
    [(leaf) (node v (leaf) (leaf))]
    [(node w l r)
     (if (< v w)
         (node w (insert-bst v l) r)
         (node w l (insert-bst v r)))]))
