import groovy.transform.*

@Canonical
class Person{

	def name
	def age


	Person(){
		println 'creating Person'
	}

}

class Worker{

	@Lazy def person = new Person([name: "张三",age: 20])


	Worker(){
		println 'creating Worker'
	}
}



def worker = new Worker()

println worker.person.name





