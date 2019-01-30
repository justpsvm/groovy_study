import groovy.transform.*

@Singleton(lazy = true)
class Person {

	def say(){
		println "hello world"
	}

}

Person.instance.say()

