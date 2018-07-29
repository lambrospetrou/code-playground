#!/usr/bin/env racket

#lang racket

(define (filter1 l)
  (define (is-valid str)
    (define num (string->number str))
    (and num (> num 10)))
  (match (string-split l)
    [(cons _ (cons b _)) (is-valid b)]
    [_ #f]))

(define (process in filterer)
  (for ([l (in-lines in)] #:when (filterer l))
    (displayln l)))

(process (current-input-port) filter1)
