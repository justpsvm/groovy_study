## 闭包委托

在 groovy 中，闭包会有下面三种属性:

- this
- owner
- delegate

其中，`delegate` 在默认情况下等于 `owner`。

> 它们的作用是确定由哪个对象处理该闭包的调用

接下来观察下这三个属性:


```groovy
def examiningClosure(closure){
	closure()
}


examiningClosure{
	println "In First Closure:"

	println "class is " + getClass().name

	println "this is " + this + ", super: " + this.getClass().superclass.name 
	println "owner is " + owner + ", super: " + owner.getClass().superclass.name 
	println "delegate is " + delegate + ", super: " + delegate.getClass().superclass.name 

	examiningClosure{
		println "In Inner First Closure:"

		println "class is " + getClass().name

		println "this is " + this + ", super: " + this.getClass().superclass.name 
		println "owner is " + owner + ", super: " + owner.getClass().superclass.name 
		println "delegate is " + delegate + ", super: " + delegate.getClass().superclass.name 		
	}
}
```

输出结果:

```bash
In First Closure:
class is ThisOwnerDelegate$_run_closure1
this is ThisOwnerDelegate@383bfa16, super: groovy.lang.Script
owner is ThisOwnerDelegate@383bfa16, super: groovy.lang.Script
delegate is ThisOwnerDelegate@383bfa16, super: groovy.lang.Script
In Inner First Closure:
class is ThisOwnerDelegate$_run_closure1$_closure2
this is ThisOwnerDelegate@383bfa16, super: groovy.lang.Script
owner is ThisOwnerDelegate$_run_closure1@47faa49c, super: groovy.lang.Closure
delegate is ThisOwnerDelegate$_run_closure1@47faa49c, super: groovy.lang.Closure
```

上面在闭包调用中 确定了 `this/owner/delegate` 这三个属性信息，之后在调用第二个闭包时向它传递了第一个闭包 因为第二个闭包定义在第一个闭包里。

从执行结果中看，闭包被创建成了内部类，`delegate` 被 设置成了 `owner` 所以输出结果是一致的。

观察下面的代码:

```groovy
class Handler{
	def f1(){
		println "f1 of Handler called..."	
	}
	def f2(){
		println "f2 of Handler called..."	
	}
}

class Example{

	def foo(closure){
		closure.delegate = new Handler()
		closure()
	}
}

def f1(){
	println "f1 of Script called..."
}

new Example().foo{
	f1()
	f2()
}
```

在这段代码中，闭包内 `f1/f2` 方法调用首先被路由到闭包的上下文对象 `this` 中，如果没有找到这些方法，则到 `delegate` 

结果:

```groovy
f1 of Script called...
f2 of Handler called...
```

这一段代码: `closure.delegate = new Handler()` 设置了闭包的 `delegate` 属性，不过在多线程环境下会有副作用，在单线程中是没有任何问题的。

## 使用尾递归编写程序

这是一段乘阶函数:

```groovy
def factorial(BigInteger number){
	if(number == 1) 1 else number * factorial(number - 1)
}

try{
	println "factorial of 5 is ${factorial(5)}"
}catch(Throwable ex){
	println "${ex.class.name}"
}
```

5 是一个比较小的数字，但如果是 5000 呢? 

```groovy
println "factorial of 5000 is ${factorial(5000)}"
```

结果:

```bash
java.lang.StackOverflowError
```

借助编译器优化技术和语言支持，递归程序可以转换为迭代过程。使用这种变换，可以编写出性能极高且非常优雅的代码，在 groovy 对闭包提供了一个特殊的方法来解决这个问题:


```groovy
def factorial

factorial = { int number,BigInteger theFactorial -> 
	number == 1 ? theFactorial : factorial.trampoline(number -1,number * theFactorial)
}.trampoline()

println "factorial of 5000 is ${factorial(5000,1).bitCount()}"
```

结果:

```bash
factorial of 5000 is 24654
```

要使用 `trampoline` 必须将递归函数实现为闭包。
