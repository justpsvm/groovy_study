

/*

def foo(str){
	println str.execute().text
}

def foo(str){
	if(str != null){
		println str.execute().text
	}
}
foo(null)
*/


def foo(str){
	println str?.execute()?.text
}

foo('git --version')
