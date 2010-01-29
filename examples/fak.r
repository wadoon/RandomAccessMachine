//Fakultaet:
// 		/   1   : n==0
// n! = |   n*(n-1)!   : sonst
//		\
//Initial:
 
//c(2) := n
//c(3) := Zaehler//Akkumulation

//Eingabe:
	LOAD    #3
	
	
//Program:
	STORE    #2	
	LOAD	 #1
	STORE    #3 
	
START:
	LOAD      2
	JZERO   END
	MULT      3
	STORE    #3
	LOAD      2
	SUB      #1
	STORE    #2
	GOTO  START
END:
//Output
	LOAD     3
	PRINT
	END