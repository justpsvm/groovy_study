
import groovy.transform.*

@Canonical
class Person{
	String name;
	int age
}

@Newify(Person)
def createPerson(){
	//Person.new("张三",1)
	//Person("张三",1)
	Person(name: "张三",age: 1)
}

def person = createPerson()

println person


