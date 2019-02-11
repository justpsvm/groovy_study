def factorial

factorial = { int number,BigInteger theFactorial -> 
	number == 1 ? theFactorial : factorial.trampoline(number -1,number * theFactorial)
}.trampoline()

println "factorial of 5000 is ${factorial(5000,1).bitCount()}"