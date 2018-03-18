#!/usr/bin/env racket

#lang racket

(define (get-input)
  (current-input-port))

(define (is-valid str)
  (define num (string->number str))
  (if (and num (> num 10))
      #t
      #f))

(define (filter1 l)
  (match (string-split l)
    [(cons _ (cons b _)) (is-valid b)]
    [_ #f]))

(define (process in filterer)
  (for ([l (in-lines in)] #:when (filterer l))
    (displayln l)))

(process (get-input) filter1)
