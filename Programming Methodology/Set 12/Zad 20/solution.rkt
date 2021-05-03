#lang racket

(require "graph.rkt")
(provide bag-stack@ bag-fifo@)


;;||======================||
;;||    Praca grupowa:    ||
;;||                      ||
;;||      Jakub Bok       ||
;;||    Karol Kęciński    ||
;;||     Jakub Świgło     ||
;;||======================||

;; struktura danych - stos
(define-unit bag-stack@
  (import)
  (export bag^)
  ; stos reprezentuję jako listę
  (define (bag? l) (list? l))
  ; pusty stos - lista pusta
  (define empty-bag null)
  (define (bag-empty? l) (null? l))
  ; dodaję consem
  (define (bag-insert l a) (cons a l))
  ; peek to 1 element
  (define (bag-peek l) (car l))
  ; usunięcie to zwrócenie wszystkiego poza 1 elementem
  (define (bag-remove l) (cdr l))
  )

;; struktura danych - kolejka FIFO
(define-unit bag-fifo@
  (import)
  (export bag^)
  ; kolejka to lista otagowana i lista wejściowa i wyjściowa tak jak w wskazówce
  (define (bag? l) (and (list? l)
                        (eq? (length l) 3)
                        (eq? (first l) 'kolejka)
                        (list? (second l))
                        (list? (third l))))
  ; pusta kolejka to kolejka z nullami 
  (define empty-bag (list 'kolejka null null))
  (define (bag-empty? l) (and (list? l)
                              (eq? (length l) 3)
                              (eq? (first l) 'kolejka)
                              (null? (second l))
                              (null? (third l))))
  ; konstruktor kolejki, jak w zadaniu
  (define (kolejka we wy)
    (if (null? wy)
        (list 'kolejka null (reverse we))
        (list 'kolejka we wy)))
  ; dodajemy do kolejki dodając do listy wyjściawej
  (define (bag-insert l a)
    ; dopasowujemy do listy tagowanej
    (match l
      [(list 'kolejka we wy)
       ; przepisujemy listę wyjściową a do wejściowej dodajemy element
       (kolejka (cons a we) wy)]))

  ; peek zwraca pierwszy element listy wyjściowej
  (define (bag-peek l) (car (third l)))

  ; remove usuwa pierwszy element listy wyjściowej
  (define (bag-remove l) (cdr l)
    (match l
      [(list 'kolejka we wy) (kolejka we (cdr wy))]))
  )

;; otwarcie komponentów stosu i kolejki

(define-values/invoke-unit bag-stack@
  (import)
  (export (prefix stack: bag^)))

(define-values/invoke-unit bag-fifo@
  (import)
  (export (prefix fifo: bag^)))

;; testy w Quickchecku
(require quickcheck)

;; testy kolejek i stosów
(define-unit bag-tests@
  (import bag^)
  (export)
  
  ;; test przykładowy: jeśli do pustej struktury dodamy element
  ;; i od razu go usuniemy, wynikowa struktura jest pusta
  (quickcheck
   (property ([s arbitrary-symbol])
             (bag-empty? (bag-remove (bag-insert empty-bag s)))))
  ;; TODO: napisz inne własności do sprawdzenia!
  
  ; lista po dodaniu elementu nie będzie pusta
  (quickcheck
   (property ([s arbitrary-symbol])
             (not (bag-empty? (bag-insert empty-bag s)))))
  ; czy jak dodamy element do lisy to dostaniemy go peekiem
  (quickcheck
   (property ([s arbitrary-symbol])
             (eq? s (bag-peek (bag-insert empty-bag s)))))
 

  )

;; uruchomienie testów dla obu struktur danych

(invoke-unit bag-tests@ (import (prefix stack: bag^)))
(invoke-unit bag-tests@ (import (prefix fifo: bag^)))

;; TODO: napisz też testy własności, które zachodzą tylko dla jednej
;; z dwóch zaimplementowanych struktur danych

(define-unit stack-tests@
  (import bag^)
  (export)
  
  ;; test przykładowy: jeśli do pustej struktury dodamy element
  ;; i od razu go usuniemy, wynikowa struktura jest pusta
  (quickcheck
   (property ([s arbitrary-symbol])
             (bag-empty? (bag-remove (bag-insert empty-bag s)))))
  ;; TODO: napisz inne własności do sprawdzenia!

  ; dodany element jest pierwszy
  (quickcheck
   (property ([s1 arbitrary-symbol]
              [s2 arbitrary-symbol]
              [s3 arbitrary-symbol])
             (eq? s3 (bag-peek
                      (bag-insert
                       (bag-insert
                        (bag-insert empty-bag s1) s2) s3)))))

  
  ; kolejność w 3 elementowej kolejce jest poprawna
  (quickcheck
   (property ([s1 arbitrary-symbol]
              [s2 arbitrary-symbol]
              [s3 arbitrary-symbol])
             (let ([ kolejka (bag-insert
                              (bag-insert
                               (bag-insert empty-bag s1) s2) s3)])
               (and
                (eq? s3 (bag-peek kolejka))
                (let ([kolejka (bag-remove kolejka)])
                  (and
                   (eq? s2 (bag-peek kolejka))
                   (let ([kolejka (bag-remove kolejka)])
                     (eq? s1 (bag-peek kolejka)))))))))
  )
(invoke-unit stack-tests@ (import (prefix stack: bag^)))


(define-unit fifo-tests@
  (import bag^)
  (export)
  
  ;; test przykładowy: jeśli do pustej struktury dodamy element
  ;; i od razu go usuniemy, wynikowa struktura jest pusta
  (quickcheck
   (property ([s arbitrary-symbol])
             (bag-empty? (bag-remove (bag-insert empty-bag s)))))
  ;; TODO: napisz inne własności do sprawdzenia!

  ; pierwszy dodany element jest pierwszy
  (quickcheck
   (property ([s1 arbitrary-symbol]
              [s2 arbitrary-symbol]
              [s3 arbitrary-symbol])
             (eq? s1 (bag-peek
                      (bag-insert
                       (bag-insert
                        (bag-insert empty-bag s1) s2) s3)))))

  
  ; kolejność na 3 elementowym stosie jest poprawna
  (quickcheck
   (property ([s1 arbitrary-symbol]
              [s2 arbitrary-symbol]
              [s3 arbitrary-symbol])
             (let ([ kolejka (bag-insert
                              (bag-insert
                               (bag-insert empty-bag s1) s2) s3)])
               (and
                (eq? s1 (bag-peek kolejka))
                (let ([kolejka (bag-remove kolejka)])
                  (and
                   (eq? s2 (bag-peek kolejka))
                   (let ([kolejka (bag-remove kolejka)])
                     (eq? s3 (bag-peek kolejka)))))))))
  )
(invoke-unit fifo-tests@ (import (prefix fifo: bag^)))




;; otwarcie komponentu grafu
(define-values/invoke-unit/infer simple-graph@)

;; otwarcie komponentów przeszukiwania 
;; w głąb i wszerz
(define-values/invoke-unit graph-search@
  (import graph^ (prefix stack: bag^))
  (export (prefix dfs: graph-search^)))

(define-values/invoke-unit graph-search@
  (import graph^ (prefix fifo: bag^))
  (export (prefix bfs: graph-search^)))

;; graf testowy
(define test-graph
  (graph
   (list 1 2 3 4)
   (list (edge 1 3)
         (edge 1 2)
         (edge 2 4))))

;; TODO: napisz inne testowe grafy


(define test-graph2
  (graph
   (list 1 2 )
   (list (edge 1 2)
         (edge 2 1))))

(define test-graph3
  (graph
   (list 1 2 3 4 5 6 7 8 9 10)
   (list (edge 1 2)
         (edge 1 3)
         (edge 2 4)
         (edge 2 5)
         (edge 3 6)
         (edge 3 7))))


;; uruchomienie przeszukiwania na przykładowym grafie
(bfs:search test-graph 1)
(dfs:search test-graph 1)
;; TODO: uruchom przeszukiwanie na swoich przykładowych grafach

; graf2 
(bfs:search test-graph2 1)
(dfs:search test-graph2 1)


; graf3 - od 1 
(bfs:search test-graph3 1)
(dfs:search test-graph3 1)


; graf3 - od 2
(bfs:search test-graph3 2)
(dfs:search test-graph3 2)