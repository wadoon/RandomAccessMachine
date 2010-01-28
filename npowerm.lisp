(define n m x)

(set n 5)
(set m 3)
(set x 1)

(while (> m 0)
	(exec 
		(- m 1)
		(* x n)
	)
)

(print x) 