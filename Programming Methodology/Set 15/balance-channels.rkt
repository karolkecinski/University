#lang racket

(define (thread-receive-a p?)
  (define (f msgs)
    (define msg (thread-receive))
    (if (p? msg)
        (begin (thread-rewind-receive msgs)
               msg)
        (f (cons msg msgs))))
  (f '()))

(struct deposit-msg (amount sender))
(struct deposit-reply (ok))
(struct withdraw-msg (amount sender))
(struct withdraw-reply (ok))
(struct balance-msg (sender))
(struct balance-reply (value))

(define (make-account-thread balance)
  (define (loop)
    (define msg (thread-receive))
    (cond [(deposit-msg? msg)
           (set! balance (+ balance (deposit-msg-amount msg)))
           (when (thread? (deposit-msg-sender msg))
             (thread-send (deposit-msg-sender msg)
                          (deposit-reply #t)))]
          [(withdraw-msg? msg)
           (if (>= balance (withdraw-msg-amount msg))
               (begin (set! balance (- balance (withdraw-msg-amount msg)))
                      (thread-send (withdraw-msg-sender msg)
                                   (withdraw-reply #t)))
               (thread-send (withdraw-msg-sender msg)
                            (withdraw-reply #f)))]
          [(balance-msg? msg)
           (thread-send (balance-msg-sender msg)
                        (balance-reply balance))])
    (loop))
  (thread loop))
     
(define (deposit account amount)
  (thread-send account (deposit-msg amount (current-thread)))
  (define reply (thread-receive-a deposit-reply?))
  (deposit-reply-ok reply))

(define (deposit-async account amount)
  (thread-send account (deposit-msg amount #f)))

(define (withdraw account amount)
  (thread-send account (withdraw-msg amount (current-thread)))
  (define reply (thread-receive-a withdraw-reply?))
  (withdraw-reply-ok reply))

(define (balance account)
  (thread-send account (balance-msg (current-thread)))
  (define reply (thread-receive-a balance-reply?))
  (balance-reply-value reply))

(define acc (make-account-thread 100))

(balance acc)
(deposit acc 50)
(withdraw acc 20)
(balance acc)


