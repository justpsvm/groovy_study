

import groovy.transform.*

/*
@Canonical
class Person{

	def name;

	def age;
}
*/

@Canonical(excludes = "age")
class Person{

	def name;

	def age;
}


def person = new Person([name: "李四",age: 10])

println person

