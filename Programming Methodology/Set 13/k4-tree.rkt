#lang typed/racket

;;; drzewa binarne

(define-type Leaf 'leaf)
(define-type (Node a) (List 'node a (Tree a) (Tree a)))
(define-type (Tree a) (U Leaf (Node a)))

(define-predicate leaf? Leaf)
(define-predicate node? (Node Any))
(define-predicate tree? (Tree Any))

(: leaf Leaf)
(define leaf 'leaf)

(: node-val (All [a] (-> (Node a) a)))
(define (node-val x)
  (cadr x))

(: node-left (All [a] (-> (Node a) (Node a))))
(define (node-left x)
  (caddr x))

(: node-right (All [a] (-> (Node a) (Node a))))
(define (node-right x)
  (cadddr x))

(: make-node (All [a] (-> a (Tree a) (Tree a) (Node a))))
(define (make-node v l r)
  (list 'node v l r))

;;; wyszukiwanie i wstawianie w drzewach przeszukiwań binarnych

(: find-bst (-> Real (Tree Real) Boolean))
(define (find-bst x t)
  (cond [(leaf? t)          false]
        [(= x (node-val t)) true]
        [(< x (node-val t)) (find-bst x (node-left t))]
        [else (find-bst x (node-right t))]))

(: insert-bst (-> Real (Tree Real) (Tree Real)))
(define (insert-bst x t)
  (cond [(leaf? t)
         (make-node x leaf leaf)]
        [(< x (node-val t))
         (make-node (node-val t)
                    (insert-bst x (node-left t))
                    (node-right t))]
        [else
         (make-node (node-val t)
                    (node-left t)
                    (insert-bst x (node-right t)))]))

(: tree-map (All [a b] (-> (-> a b) (Tree a) (Tree b)))) 
(define (tree-map f t)
  (cond [(leaf? t) t]
        [else (make-node (f (node-val t))
                         (tree-map f (node-left t))
                         (tree-map f (node-right t)))]))

(: example-tree (Tree Real))
(define example-tree
  (make-node 2 (make-node 1 leaf leaf) (make-node 3 leaf leaf)))

