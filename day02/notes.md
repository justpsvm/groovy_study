## 可选形参

```groovy
def sum(a,b = 100){
	a + b
}

println sum(1)
println sum(1,1)

101
2
```

groovy 中的函数构造器行参是可选的，但要确保可选的行参一定是放在函数的末尾 ，如上面代码所示 在第二个值没有传递的时候 默认是100。

那么这里有一个问题，在 groovy 中的方法重载是怎样的？

```groovy
def sum(a,b = 100){
	a + b
}

def sum(a,b,c){
	a + b
}

println sum(1)
println sum(1,1)

101
2
```

看起来和 java 一样，再改动下:

```groovy
def sum(a,b = 100){
	a + b
}

def sum(a,b,c = 100){
	a + b
}

println sum(1)
println sum(1,1)

 The method with default parameters "java.lang.Object sum(java.lang.Object, java.lang.Object, java.lang.Object)" defines a method "java.lang.Object sum(java.lang.Object, java.lang.Object)" that is already defined.
. At [7:1]  @ line 7, column 1.
   def sum(a,b,c = 100){
   ^
```

这里编译就不会通过了，目前可以知道的是 `如果函数行参定义了默认值 那么就不能再定义名字相同并不具备默认值的函数` 这里先挖个坑 后面弄明白了再来补充。

另外，groovy 会把末尾数组行参作为可选的:

```groovy
def log(String target,String[] logs){
	println "$target:" + logs
}

// log("module","load 1","load2")
log("module")

module:[]
```

## 多赋值

这是一个把字符串按照 `/` 进行分割的功能:

```groovy
def category(catagorys){
	catagorys.split("/")
}

def (category1,category2) = category("apple/banana")

println "$category1,$category2"

apple,banana
```

其中 `(category1,category2)` 代表接收一个多值参数。

多赋值这个特性，我们可以实现: `交换两个变量的值不使用临时变量`:

```groovy
def v1 = 1
def v2 = 2

(v1,v2) = [v2,v1]

println "$v1,$v2"
```

个人感觉这种是使用数组实现的，这是 Java 中的实现方式:

```java
private static int[] swap(int v1, int v2) {
    return new int[]{v2,v1};
}
    
int v1 = 1;
int v2 = 2;

int[] retVal = swap(v1, v2);
v1 = retVal[0];
v2 = retVal[1];

System.out.println(v1);
System.out.println(v2);

2
1
```

当接受参数和返回参数不一致时 groovy 是这样处理的:

- 实际接受参数 < 返回参数 自动抛弃多余的参数
- 返回参数 < 实际接受参数 没有匹配上的参数为 null

举例说明:

实际接受参数 < 返回参数:

```groovy
def (category1) = category("apple/banana")

println "$category1"

apple
```

返回参数 < 实际接受参数:

```groovy
def (category1,category2,category3) = ["apple","banana"]

println "$category1,$category2,$category3"

apple,banana,null
```

如果是这样写:

```groovy
def (category1,category2,category3) = category("apple/banana")

println "$category1,$category2,$category3"

Caught: java.lang.ArrayIndexOutOfBoundsException: 2
java.lang.ArrayIndexOutOfBoundsException: 2
	at manyvalue.run(manyvalue.groovy:10)
```

会抛出一个异常，暂时理解为 `返回参数 < 实际接受参数 自动填充null` 这种只适用于静态分配的参数 动态参数会抛出数组越界异常。


## 接口实现

在 Java 中要实现接口需要实现接口中的所有方法，在 groovy 中没有强制要求实现接口的所有方法 可以只定义自己关心的:


```groovy
interface UserService{
	def add()
	def delete()
	def queryAll()
}

def operation(service){
	service.add()
	service.delete()
	service.queryAll()
}

userService = {
	println 'call'
}

operation(userService as UserService)

call
call
call
```

能看到输出了3次 call,上面的每次方法调用都会进入 `userService` 函数中。修改下代码:

```groovy

userService = [
	add: {println 'call add'},
	delete: {println 'call delete'},
	queryAll: {println 'call queryAll'}
]

call add
call delete
call queryAll
```

只需要实现用得到的函数 ，没有使用到的函数不实现也不会报错，如果使用没有实现的函数则会抛出异常:

```groovy

def operation(service){
	service.queryAll()
}

userService = [
	add: {println 'call add'},
	delete: {println 'call delete'}
]

java.lang.UnsupportedOperationException
	at com.sun.proxy.$Proxy14.queryAll(Unknown Source)
	at UserService$queryAll$1.call(Unknown Source)
	at implInterface.operation(implInterface.groovy:10)
	at implInterface.run(implInterface.groovy:18)
```

groovy 中的接口实现主要是借助 as 操作符号

> Java 要实现上面的功能 代码会变得非常长且冗余。

## 布尔求值

groovy 会根据当前的上下文 自动转换类型到布尔值,而 Java 不是这样的:

```java
Object flag = "haha";
if(flag){} //error
```

这段代码根本是跑不起来的，因为 Java 要求 if 需要接收一个布尔表达式 类似: `if(flag != null){}`

而 groovy 会尝试类型推断:

```groovy
def flag = "haha"

if(flag){
	println "hello"
}

```

在需要布尔表达式的时候，如果传递的对象是引用类型 那么是这样处理的:

```java
if(param != null) 
```

> ~~groovy 会检查引用对象是否为 null 把 null 作为 false 非 null 作为 true。~~

上面的说法可能是不太正确的 准确的说是根据不同的引用类型做不同的处理。

| 类型         | 转换为布尔 true 的条件      |
| ------------ | --------------------------- |
| Boolean      | 值为 true                   |
| Collection   | 集合不为空                  |
| Character    | 值不为0                     |
| CharSequence | 长度大于0                   |
| Enumeration  | Has More Elements() 为 true |
| Iterator     | hasNext() 为 true           |
| Number       | Double 值不为0              |
| Map          | 映射不为空                  |
| Matcher      | 至少有一个匹配              |
| Object[]     | 长度大于0                   |
| 其它任何类型 | 引用                        |


> 除开 groovy 自带的布尔约定，我们还可以使用 asBoolean 自定义约定 

## 操作符重载

指的是一些符号的特殊含义 ，比如 Java 中的 `.` 代表操作方法或者对象 但在 groovy 中 可以使用自定义操作符号 也可以使用默认操作符号。

```groovy

for(ch = 'a';ch <= 'f';ch++){
	println "$ch"
}

a
b
c
d
e
f
```

groovy 使用了这里的 `++` 操作符替换为 `String.next()` 函数

> `String.next` 函数是 groovy 添加的 Java 中不包含这个函数。


也可以使用 `<<` 操作符给数组添加:

```groovy
def categorys = ["apple"]
categorys << "banana" << "loquat"
println categorys


[apple, banana, loquat]
```

< 这里的 `<<` 转化为 Collection 中的 leftShift 方法,另外观察上面代码 发现在追加之后还可以继续追加 相当于链式调用 这对于 Java 来说必须自定义变长数组才能实现。



