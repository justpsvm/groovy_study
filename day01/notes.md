## Helloworld

这是 Java 版本的 HelloWorld: 

```java
public class Helloworld{
    public static void main(String[] args){
        System.out.println("hello world")
    }
}
```

接下来创建一个 `helloworld.groovy` 文件,内容如下:

```groovy
public class Helloworld{
    public static void main(String[] args){
        System.out.println("hello world")
    }
}
```

内容和 java 一样,使用下面的方式运行:

```bash
groovy helloworld.groovy
hello world
```

这里发现有一个差异,在主方法声明 `String[] args` 时 Java 也可也这样写 `String args[]` ，可在 groovy 中会报错:

```bash
/Users/kanshan/work/gitProjects/groovy_study/day01/helloworld.groovy: 2: unexpected token: [ @ line 2, column 39.
       public static void main(String arg[]){
```

这里记录下 后面来填坑。


groovy 是完全兼容 Java 语法的，所以在学习的时候可以慢慢来 groovy 语法不熟悉的时候 使用 java 

### 简化版

这里使用 groovy 创建一个最简单的 helloworld ，代码如下:

```groovy
println "hello world"
```

这里去掉了类和方法的定义 ，没有了 main 方法就依次从上到下执行。

## 默认导入的包

groovy 默认会导入 java 中的下列包:

- java.lang
- java.util
- java.io
- java.net
- java.math.BigDecimal
- java.math.BigInteger
- groovy.lang
- groovy.util

## 循环

下面举几个例子说明和 Java 的不同

### 例一

java:

```java
for(int i = 0 ; i < 3 ; i++){
    System.out.println("hello")
}
```

groovy:
```groovy
for(i in 0..2){
    println 'hello'
}
```

这里是使用 groovy range 0..2 进行循环


### 例二


groovy:

```groovy
0.upto(2){
    println "hello $it"
}

hello 0
hello 1
hello 2
```


这里 groovy 使用到了动态语言的特征，在 java.lang.Integer 类中扩展了一个 upto 的函数，这里 `$it` 代表循环时的索引值 

> 注意 如果把上面的 "hello $it" 替换成 'hello $it' 则不能输出索引值信息 双引号和单引号在 groovy 中意思不同。

也可以使用下面的方式:

```groovy
3.times{
	println "3hello $it"
}
3hello 0
3hello 1
3hello 2
```

## GDK

简单说下 GDK 后面还会详细说到

为了使用更简单的方式调用 Java 系统类库，Groovy 提供了 GDK (Groovy Developer Kit)。

执行这段代码:

```groovy
println 'git --version'.execute().text

git version 2.17.1 (Apple Git-112)
```

这是调用了系统的程序并输出到控制台，如果放在 Java 中 这是不可能的事情。

> 原理上 执行 execute().text 这段代码还是会调用 java 相关的库 只是对于我们而言 更简单了而已。


## 安全导航提示符

```groovy
def foo(str){
	println str.execute().text
}

foo('git --version')

git version 2.17.1 (Apple Git-112)
```

如果 foo 传递的是 null 值，那么接口就是:

```groovy
Caught: java.lang.NullPointerException: Cannot invoke method execute() on null object
java.lang.NullPointerException: Cannot invoke method execute() on null object
	at nullpoint.foo(nullpoint.groovy:2)
	at nullpoint$foo.callCurrent(Unknown Source)
	at nullpoint.run(nullpoint.groovy:6)
```

按照 java 中的逻辑 需要这样进行处理:

```groovy
def foo(str){
	if(str != null){
		println str.execute().text	
	}
	
}
```

实际上可以这样处理:

```groovy
def foo(str){
	println str?.execute()?.text
}

//foo('git --version')
foo(null)
```

`?.` 符号可以避免空指针异常，如上面所示 操作符只有在不为 null 的时候才会调用方法或者属性。


## 异常的处理

Java 中的受检查异常必须进行处理，比如 `Thread.sleep()`,虽然语言层面要求这样做 而程序员们的做法是这样的:

```groovy

try{
    Therad.sleep();
}catch(Exception ex){
    //nothing
}
```

这样实际上违背了Java受检查异常的初衷，如果这里什么都不做 异常就断在这里 不会向上抛，程序会继续向下面执行 而引发雪崩效应。

在 groovy 中 异常是这样处理的:

```grovvy
def getFileStream(filename){
	new FileInputStream(filename)
}

getFileStream("nothing")
```

可以看到在 `new FileInputStream(filename)` 这里没有进行异常捕获，而这在Java中是不太可能的。groovy 不会强迫我们去捕获自己不关心的异常。


## JavaBean

Java 中的 Bean 对象使用起来很繁琐 当然我们也可以不用去遵循 `setter` 和 `getter` 但是这个已经墨守成规了 很多框架都是遵循这个规则。

那么在 Groovy 中 ，创建一个 Bean 的代码如下:


```groovy

class Person{
	def  name
	def  age
}

Person person = new Person()

person.name = '章三'
person.age = 14

println 'name:' + person.name
println 'age:' + person.age

name:章三
age:14
```

看起来 没有 `setter` 和 `getter` 函数 在看下面的代码:

```groovy
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

name:狄仁杰
age:14
```

groovy 还是调用了对应的 set 函数，但是我们并没有声明，可以推断 groovy 自己创建了属性的访问和修改构造器。

### 对象权限

Java 中有 `public/private/protected` 权限，但这些在 groovy 中都没有，groovy 不会区分这些东西。


## 对象灵活初始化

java 中如果需要额外的参数会放在构造函数中，而 groovy 不需要:

```groovy
class Student{
	def name,age,school
}

student = new Student(name:'刘备',age:20,school:'四川大学')

println '姓名:' + student.name
println '年龄:' + student.age
println '学校:' + student.school
```

这里并没有申明任何的构造函数，在创建 `Student` 对象时的参数是类似 Map 的，`(name:'刘备',age:20,school:'四川大学')`。


