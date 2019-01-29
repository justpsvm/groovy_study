
## 使用 @Canonical

在 Java 中 我们重写 `toString` 方法大多数时候是为了查看字段信息，在 groovy 中可以使用 `@Canonical` 注解:

```groovy
import groovy.transform.*

@Canonical
class Person{

	def name;

	def age;
}


def person = new Person([name: "李四",age: 10])

println person

Person(李四, 10)
```

也可以使用排除参数,在打印的时候不进行输出:

```groovy
@Canonical(excludes = "age")
class Person{

	def name;

	def age;
}


def person = new Person([name: "李四",age: 10])

println person

Person(李四)
```

通过 `excludes` 属性 填写需要排除的字段即可。

> 多个字段时 使用 `,` 号进行分割。

## @Delegate

在 Java 中大多数时候 不推荐使用 `extends` 关键字 ，因为这会造成高耦合，而且 Java 中只能有一个派生类，因此可以使用代理模式 但 Java 编写代理模式需要过多的冗余代码 。

在 groovy 中 使用 `@Delegate` 注解即可以实现代理模式:

```groovy
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


hello world
正在吃鸡排
```

通过 Proxy 类代理了`Person` 和 `People` ，在执行完第一句 `@Delegate` 代码后 Proxy 类中已经引入了 `Person.say` 代理，在第二步中 本来是需要引入 `People.say 和 People.eat` 时 发现 `say` 已经存在 Proxy 中了 所以并不会代理 `People.say` 函数。

这个是有 Proxy 类是可以扩展的，如果在 Person 或者 People 添加新的函数 Proxy 也不需要做任何的修改 只需要重写编译代码， groovy 会自己处理。

> 你可以尝试调换两个 `@Delegate` 到位置查看它们的不同。

## Immutable

在 Java 中创建一个不可变的类 需要使用 `final` 修饰符，而在 groovy 中可以使用 `Immutable` 注解:


```groovy
import groovy.transform.*

@Immutable
@Canonical
class Order{
	String orderNum
	Date createDate
}

def order = new Order("EORC210041321G",new Date())

println order

Order(EORC210041321G, Tue Jan 29 23:56:55 CST 2019)
```

当我们使用 `immutable` 注解创建不可变对象时 groovy 会在编译的时候添加等同于参数的构造方法 并且在创建后 对象的任何参数不允许修改。

尝试修改对象属性:
```groovy
order.orderNum = '';

Caught: groovy.lang.ReadOnlyPropertyException: Cannot set readonly property: orderNum for class: Order
groovy.lang.ReadOnlyPropertyException: Cannot set readonly property: orderNum for class: Order
	at Order.setProperty(immutable.groovy)
	at immutable.run(immutable.groovy:15)

```


> 使用 `immutable` 可以轻松创建不可变对象。


## Lazy

类的懒加载模式，在 `Hibernate/Mybatis` 经常看到它的影子。

如果在 Java 中实现类的懒加载，需要代码上面的把控才能做到:

```java

Person person = null;

if(something && person = null){
    person = new Person();
    
    //code...
}

```

作用是只有在类真正使用到的时候 才进行加载，可上面的代码编写较繁琐，在 groovy 中是这样写的:

```groovy
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


creating Worker
creating Person
张三
```

当只初始化 Worker 时:

```groovy
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

creating Worker
```

编写更少的代码，同时又能获得惰性初始化的好处。




