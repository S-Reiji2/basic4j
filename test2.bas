a = 5
b = 2.36
c = "TEST"

PRINT ("Hello")
PRINT ("c = ", c)
PRINT ("b = " + b)
PRINT ("b/2 = ", b/2)
PRINT ("1/2 = ", 1/2)
PRINT ("1.0/2.0 = ", 1.0/2.0)
PRINT ("")

DO WHILE a > 0
    IF a >= 0 THEN
        PRINT ("a: ", a)
    ELSE
        a = 0
    ENDIF

    a = a - 1
    b = (b + 1) * 0.5
LOOP

PRINT ("")

FOR i = 0 TO 10
    PRINT("i: ", i)
NEXT i

END

