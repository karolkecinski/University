#lang racket

(define/contract (inc i)
  (-> integer? integer?)
  (+ i 1))

(define/contract (square x)
  (-> number? number?)
  (* x x))

(define/contract (cube x)
  (-> number? number?)
  (* x x x))

(define/contract (average x y)
  (-> number? number? number?)
  (/ (x y) 2))

(define/contract (dist x y)
  (-> number? number? number?)
  (abs (- x y)))

;; definicje złożonych kontraktów
(define natural/c (and/c integer? (not/c negative?)))
(define exact-natural/c (and/c natural/c exact?))
(define positive-natural/c (and/c integer? positive?))

;; kontrakty złożone z predykatów też są predykatami!
;; (natural/c 1)

;; silnia jako procedura rekurencyjna
;; jeśli damy samo natural/c, (fact 1000.0) łamie kontrakt
(define/contract (fact n)
  (-> exact-natural/c positive-natural/c)
  (if (= n 0)
      1
      (* n (fact (- n 1)))))

;; silnia jako procedura iteracyjna
(define/contract (fact-it n)
  (-> exact-natural/c positive-natural/c)
  (define (fact-iter n i c)
    (if (> i n)
        c
        (fact-iter n (+ i 1) (* i c))))
  (fact-iter n 1 1))

;; sumowanie ciągów
;; kontrakt wyższego rzędu!
(define/contract (sum val next start end)
  (-> (-> number? number?) (-> number? number?) number? number? number?)
  (if (> start end)
      0
      (+ (val start)
         (sum val next (next start) end))))

;; przykład: (sum square inc 0 5)
;; problem:  (sum cube boolean? 1 4)
;; zamiana (next start) na (next #f) psuje kontrakt na argument

;; uwaga -- polaryzacja kontraktu:
;; - dodatnia pod parzystą liczbą wystąpień na pozycji argumentu
;; - ujemna   pod nieparzystą
;; kto ma obowiązek zapewnić spełnienie kontraktu:
;; - kontrakt dodatni -- definicja
;; - kontrakt ujemny  -- użytkownik

;; obliczanie (przybliżonego) punktu stałego funkcji f przez iterację, let pozwala uniknąć powtarzania obliczeń
;; test kontraktu: (fix-point positive? 0)
(define/contract (good-enough? x y)
  (-> number? number? boolean?)
  (< (dist x y) 0.00001))

(define/contract (fixed-point f s)
  (-> (-> number? number?) number? number?)
  (define (iter k)
    (let ((new-k (f k)))
      (if (good-enough? k new-k)
          k
          (iter new-k))))
  (iter s))

;; próba obliczania pierwiastka kwadratowego z x jako punktu stałego funkcji y ↦ x / y zapętla się
;; stosujemy tłumienie z uśrednieniem: procedurę wyższego rzędu zwracającą procedurę jako wynik
(define/contract (average-damp f)
  (-> (-> number? number?) (-> number? number?))
  (lambda (x) (/ (+ x (f x)) 2)))

(define/contract (sqrt-ad x)
  (-> positive? number?)
  (fixed-point (average-damp (lambda (y) (/ x y))) 1.0))

;; obliczanie pochodnej funkcji z definicji przyjmując dx za "odpowiednio małą" wartość (zamiast "prawdziwej" granicy)
(define/contract (deriv f)
  (-> (-> number? number?) (-> number? number?))
  (let ((dx 0.000001))
    (lambda (x) (/ (- (f (+ x dx)) (f x)) dx))))

;; przekształcenie Newtona: x ↦ x - f(x) / f'(x) pozwala obliczyć miejsce zerowe f jako punkt stały tej transformacji
(define/contract (newton-transform f)
  (-> (-> number? number?) (-> number? number?))
  (lambda (x)
    (- x
       (/ (f x)
          ((deriv f) x)))))

(define/contract (newtons-method f x)
  (-> (-> number? number?) number? number?)
  (fixed-point (newton-transform f) x))

;; zastosowania
(define/contract pi positive? (newtons-method sin 3))

(define/contract (sqrt-nm x)
  (-> positive? number?)
  (newtons-method (lambda (y) (- x (square y))) 1.0))


;; możemy wyabstrahować wzorzec widoczny w definicjach sqrt: znaleźć punkt stały pewnej funkcji *przy użyciu* transformacji
;; argumentem fix-point-of-transform jest procedura przetwarzająca procedury w procedury!
(define/contract (fixed-point-of-transform transform f x)
  (-> (-> (-> number? number?) (-> number? number?))
      (-> number? number?)
      number?
      number?)
  (fixed-point (transform f) x))

(define/contract (sqrt-ad-t x)
  (-> positive? number?)
  (fixed-point-of-transform average-damp (lambda (y) (/ x y)) 1.0))