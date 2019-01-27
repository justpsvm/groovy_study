
class Person{
	def  name
	def  age

	def getName() {
		return '狄仁杰'
	}

}

Person person = new Person()

person.name = '章三'
person.age = 14

println 'name:' + person.name
println 'age:' + person.age

