## @Newify

通常 在 Java 中创建对象需要使用 `new` 关键字，但是在 groovy 中可以不用这样做:

```groovy
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

Person(张三, 1)
```
通过 `@Newify` 注解完成对象的申明，之后在函数体内可以不需要使用 `new` 关键字创建对象。

可以通过两种方式创建对象:

- 类.new() 
- 类()
 
这类似`Ruby` 和 `Python` 的对象创建风格。在创建 `DSL` 时变得非常有用，这看起来创建对象完全是隐式的。


## @Singleton

在 Java 中创建 `单例模式` 总是需要先定义一个静态对象，并私有化构造函数(保证外部无法通过 `new` 关键字创建),最后需要定义一个静态方法用来获取唯一的对象。

在 groovy 中，这看起来简单很多:

```groovy
import groovy.transform.*

@Singleton(lazy = true)
class Person {

	def say(){
		println "hello world"
	}

}

Person.instance.say()

hello world
```

使用 `@Singleton` 关键字可以轻松创建`单例对象`,其中 `lazy=true` 代表对象被使用的时候才创建。

另外 调用 `instance` 会映射到 Java 中的 `getInstance`。

> 和 Java 一样，groovy 会自己添加这个类的私有构造函数，在内部中 也可以使用 `new` 关键字 不过要慎用。

## `==` 等价于 `equals` 

在 Java 中这两种的关系就比较混乱 很多人分不清楚，而在 groovy 中这种混乱更严重了。

先来看一段代码:

```groovy
def str1 = "hello"
def str2 = str1
def str3 = new String("hello")
def str4 = "Hello"

println "str1 == str2: ${str1 == str2}"
println "str1 == str3: ${str1 == str3}"
println "str1 == str4: ${str1 == str4}"

str1 == str2: true
str1 == str3: true
str1 == str4: false
```


上面的 `==` 看起来都和 Java 中的 `equals` 差不多，如果要进行 `==` 操作符的匹配 需要使用到 `is`:

```groovy
def str1 = "hello"
def str2 = str1
def str3 = new String("hello")
def str4 = "Hello"

println "str1.is(str2): ${str1.is(str2)}"
println "str1.is(str3): ${str1.is(str3)}"
println "str1.is(str4): ${str1.is(str4)}"


str1.is(str2): true
str1.is(str3): false
str1.is(str4): false
```

那么可以得出一个结论: `==` 代表 `equals` 而 `is` 代表 `==` 操作符。

但是之前说过了 groovy 只会更加混乱，看看下面的代码:

```groovy
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

new People() == new People()


Call Person.equals
Call comparaTo
```

默认情况下，`==` 映射到 `equals` ，但这仅限于类没有实现 `Comparable` 接口，如果实现了 `Comparable` 那么会调用 `comparaTo` 进行比较。

## 编译时类型检查默认关闭

观察下面的代码:

```groovy
def check(){
	int value = 1
	value = "hh"	
}
```

在 groovy 编译时 并不会报错，除非进行了调用:

```groovy
def check(){
	int value = 1
	value = "hh"	
}

check()

org.codehaus.groovy.runtime.typehandling.GroovyCastException: Cannot cast object 'hh' with class 'java.lang.String' to class 'int'
	at typecheck.check(typecheck.groovy:5)
	at typecheck.run(typecheck.groovy:8)
```

这样也可能是一种优点，这意味着有些代码在运行期间动态进行注入。

> groovy 可以关闭这种动态类型特征 并开启强制类型检查 在后面介绍。

## 禁用方法代码块

而在 groovy 中，是禁止这样做的:

```groovy

def test{
    {
        println 'hello'
    }
}
```

这将带来编译错误，因为 groovy 会认为我们想要定义一个闭包 在 groovy 方法中 不能使用这样的代码块。

## 分号是可选的

在 groovy 中 分号`;`都是可选的，这看起来没有任何问题 但是在特殊的条件下:

```groovy
class Person{

	def name = "张三"

	{
		println "init"
	}

}

new Person()
```

这段代码在编译时会抛出一个异常，可是看起来没有任何问题，我声明了一个 name 属性 并且使用了代码块来做一些初始化操作，可还是抛出了异常。

这是因为 groovy 把下面的代码块看做了一个闭包，如果我们改成 `def name = "张三";` 这样就会通过编译:

```groovy

class Person{

	def name = "张三";

	{
		println "init"
	}

}
new Person()
```

现在 groovy 把代码块看做的是一个实例化的初始器 就不会抛出异常了。

那么我还是不想写分号怎么办呢? 可以通过把`静态代码块放在实例代码块之前即可`:

```groovy
class Person{

	def name = "张三"

	static{
		
	}

	{
		println "init"
	}

}

new Person()
```

## 数组的不同方式创建

在 Java 中，创建一个数组是这样的:

```java
int arr[] = new int[]{1,2,3,4}
```

可是在 groovy 中，上面的代码无法通过编译，在 groovy 中需要这样创建数组:

```groovy
int[] arr = [1,2,3,4]

println arr
println arr.class.name


[1, 2, 3, 4]
[I
```

> JVM 使用 [I 表示 int[]

需要注意的是 如果左边不写类型 而是换成 `def` 关键字，那么 groovy 会创建一个 `ArrayList`:

```groovy
def arr = [1,2,3,4]

println arr
println arr.class.name

[1, 2, 3, 4]
java.util.ArrayList
```

或者可以使用 `as` 关键字声明创建的是一个数组，而不是变长数组:

```groovy
def arr = [1,2,3,4] as int[]


println arr
println arr.class.name

[1, 2, 3, 4]
[I
```
