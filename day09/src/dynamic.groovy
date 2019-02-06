
def say(call){
	if(call){
		call()
	}else{
		println "default implements"
	}
}


say(){
	println "hello"
}

say()


