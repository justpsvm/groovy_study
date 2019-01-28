## 自动装箱

这是 JDK5 的新功能，在 groovy 中也是支持的:

```groovy
int num = 5
println num.class.name

java.lang.Integer
```

尽管我们声明的是一个 int 类型 ，实际 groovy 会创建它的包装对象 `Integer`, 这就是一个自动装箱的操作, groovy 会根据实例的使用的方式来进行自动装箱和拆箱。


而如果在 Java 中，这样的写法无法获取到 `class` 对象，因为 int 是一个基本类型。

```java
int num = 1;
System.out.println(num);
```


> 在 2.0 版本之前 groovy 所有的基本类型都被看作对象 考虑到性能问题 groovy 做了一些优化: `基本类型只有在需要的时候才会被看作为对象。`


## 循环 

JDK5 中新增了 `for-each` 循环，而 groovy 天生就比 Java 循环强大:

```groovy
def categorys = ["恐怖","科幻","搞笑"]

for(def category : categorys){
	println category
}

for(category in categorys){
	println category
}
```

第一种循环方式是指定了数据类型，或者使用 def 声明，如果不想声明变量，也不想定义类型，可以使用第二种方式进行循环。

> 在不声明类型和 `def` 时 可把 `:` 操作符替换成 `in` 进行循环。


## enum

> 枚举是尽可能的列出可能出现的值。

groovy 中也是支持枚举的:

```groovy
enum TankMoveEvent {
	上,下,左,右
}

def printDirection(direction){
	println "坦克正在向 $direction 移动"
}

printDirection(TankMoveEvent.上)

def count = TankMoveEvent.values().length

println "坦克一共有 $count 个可移动的方向，分别是:"

for(direction in TankMoveEvent.values()){
	println direction;
}

坦克正在向 上 移动
坦克一共有 4 个可移动的方向，分别是:
上
下
左
右
```

groovy 也是支持原生 Java 对 Enum 操作的方法,比如定义枚举构造器:

```groovy
enum TankMoveEvent {
	上('top'),下('bottom'),左('left'),右('right')
	def code
	TankMoveEvent(code){
		this.code = code
	}
}

def printDirection(direction){
	println "坦克正在向 $direction.code 移动"
}

printDirection(TankMoveEvent.上)

def count = TankMoveEvent.values().length

println "坦克一共有 $count 个可移动的方向，分别是:"

for(direction in TankMoveEvent.values()){
	println direction.code;
}
```

## 变长参数

> 变长参数只能放在函数形参的最后。

groovy 支持两种方式对变长参数对支持:

第一种为 Java 的方式:

```groovy
def sum(a,int ...b){
	for(v in b){
		a += v
	}
	a
}

println sum(1,2,3)

6
```
使用省略号 `...` 表示一个变长参数。

第二种为 groovy 默认的方式:

```groovy
def sum(a,int[] b){
	for(v in b){
		a += v
	}
	a
}

println sum(1,2,3)

6 
```
默认情况下 groovy 会把行参的最后一个数组参数作为变长参数。

另外需要注意: 使用第一种方式下 如果发送的参数中包含了数组参数 那么 groovy 会抛出异常:

```groovy
def sum(a,int ...b){
	for(v in b){
		a += v
	}
	a
}

println sum(1,[2,3])

Caught: groovy.lang.MissingMethodException: No signature of method: arrayparam.sum() is applicable for argument types: (Integer, ArrayList) values: [1, [2, 3]]
Possible solutions: sum(java.lang.Object, [I), run(), run(), dump(), any(), use(java.lang.Class, groovy.lang.Closure)
groovy.lang.MissingMethodException: No signature of method: arrayparam.sum() is applicable for argument types: (Integer, ArrayList) values: [1, [2, 3]]
Possible solutions: sum(java.lang.Object, [I), run(), run(), dump(), any(), use(java.lang.Class, groovy.lang.Closure)
	at arrayparam.run(arrayparam.groovy:22)

```

这种情况可以使用 `as int[]` 进行处理:

```groovy
println sum(1,[2,3] as int[])

6
```

## 静态导入

groovy 支持自定义的注解 语法和 Java 基本一致:

```groovy
import static Math.random

println random()

0.5418778459645922
```

上面这种方式是 Java 中的，来看看 groovy 另外的导入方式:

```groovy
import static Math.random as rd

println rd()

0.008682854111845995
```

通过 `as` 操作符给导入的方法或者对象定义别名，这种也支持普通的导入方式。


## 泛型的支持

在 Java 中只要指定了泛型，那么在接下来的操作中必须要遵循这个规则:

```java
List<Integer> arr = new ArrayList<>();
arr.add(1);
arr.add(3);
arr.add("hh");
```

最后一个向数组添加的语句会抛出异常，因为泛型指定了 `Integer` 类型 就不能添加非 `Integer`类型的数据。

而 groovy 中这种方式并不会报错:

```groovy
List<Integer> arr = new ArrayList<Integer>()

arr << 1
arr << 2
arr << "hh"
arr << 3

for(number in arr){
	println number 
}

1
2
hh
3
```

groovy 的动态类型特征会与泛型类型相互作用，在调用添加的过程中 groovy 只是将类型看做一个输入建议，在真正使用的时候 如果发现类型不匹配 才会抛出异常。

> 泛型在 groovy 中并没有完全的丧失功能，后面会说到 groovy 的严格模式。
