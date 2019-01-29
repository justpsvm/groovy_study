
import groovy.transform.*

class Person{

	def say(){
		println "hello world"
	}	
}

class People{

	def say(){
		println "hello"
	}

	def eat(){
		println "正在吃鸡排"
	}

}

class Proxy{

	@Delegate Person person = new Person();
	@Delegate People people = new People();
}



def proxy = new Proxy()

proxy.say()
proxy.eat()


