(set i 5)
(set j 6)
(set k 8)

(def msum (i j k) 
	(return (+ (+ i j) k))
)
(set a (msum i j k) ) 
(set b (msum i i (msum i j k ) ))
(set c (msum i i i) )
(set d (msum k i j) )
(set e (msum (msum i i i) (msum i i i) (msum i i i)))