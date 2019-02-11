
def factorial(BigInteger number){
	if(number == 1) 1 else number * factorial(number - 1)
}

try{
	println "factorial of 5 is ${factorial(5)}"
	//println "factorial of 5000 is ${factorial(5000)}"
}catch(Throwable ex){
	println "${ex.class.name}"
}
