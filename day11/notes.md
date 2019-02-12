# 使用字符串

在 Java 中使用字符串很不方便，比如要创建一个字符串必须使用双引号: `"`,如果使用了单引号那么创建的是 char 字符，另外字符串的操作拼接都很费劲 这里重要介绍 groovy 字符串的处理方式。



## 字面常量于表达式

groovy 中可以使用单引号创建字符串字面常量，比如 `'hello'`，而 Java 中会报错 因为 char 只能保存两个字节。groovy 中没有这些区别:

```groovy
def str1 = 'hello'
def str2 = "hello"

println str1.getClass().name
println str2.getClass().name
```

结果:

```bash
java.lang.String
java.lang.String
```

它们都是 `String` 对象，但如果你想创建 `char` 对象也是可以的:

```groovy
def str = 'h' as char

println str.getClass().name
```

结果:

```bash
java.lang.Character
```

groovy 会隐式的创建 `Character` 对象。


对于字面量常量里面可以放什么，groovy 也做的很灵活:

```groovy
println 'He said,"That is Groovy"'
println "He said,'That is Groovy'"
```

结果:

```bash
He said,"That is Groovy"
He said,'That is Groovy'
```

使用单引号包裹的字符串，groovy 会纯粹的看做字面常量 不会参与任何的计算:

```groovy
def value = 100
println 'value is : ${value}'
```

结果:

```bash
value is : ${value}
```

而我们使用双引号，情况就不一样了:

```bash
value is : 100
```

> 要对 String 中的对象进行求值计算 必须使用双引号。

## 字符串不可变

Java 中的字符串是不可变的，groovy 也遵循这种变化，只要创建了一个 String 实例，就不能通过更改器等方法修改其内容了，groovy 可以通过 `[]` 来访问一个字符串的字符:

```groovy
def str = "hello"

println str[0]

try{
	str[0] = 'w'
}catch(Exception ex){
	
}
```

这里我们想修改 str 的第一个字符，结果:

```bash
h
groovy.lang.MissingMethodException: No signature of method: java.lang.String.putAt() is applicable for argument types: (Integer, String) values: [0, w]
Possible solutions: putAt(java.lang.String, java.lang.Object), getAt(int), getAt(int), getAt(java.lang.String), getAt(java.util.Collection), getAt(groovy.lang.EmptyRange)
kanshan at liukanshandeMacBoo
```

尝试修改 String 导致了一个异常。

## 惰性求值

groovy 可以把表达式保存在一个字符串中，稍后进行打印:

```groovy

def str = new StringBuffer("hello")

def text = "say: ${str}"

println text

str.replace(0,5,"hhhhh")

println text
```

把表达式存在了 `text` 变量中，后面更改了 str 值，直接输出也会跟着变。

> groovy 中的字符串被叫做 GString。




















