
/*

def str1 = "hello"
def str2 = str1
def str3 = new String("hello")
def str4 = "Hello"

println "str1.is(str2): ${str1.is(str2)}"
println "str1.is(str3): ${str1.is(str3)}"
println "str1.is(str4): ${str1.is(str4)}"


// println "str1 == str2: ${str1 == str2}"
// println "str1 == str3: ${str1 == str3}"
// println "str1 == str4: ${str1 == str4}"
*/


class Person{

	boolean equals(other){
		println 'Call Person.equals'
	}
}

class People implements Comparable{


	boolean equals(other){
		println 'Call People.equals'
	}

	int compareTo(other){
		println 'Call comparaTo'	
	}

}

new Person() == new Person()

println new People() == new People()









