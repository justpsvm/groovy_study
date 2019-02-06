
## 使用闭包进行资源清理

在 Java 中只要释放了引用，就不需要担心资源回收的问题，但在有些场合下 我们总是忘记主动释放资源，比如 IO 操作: `close()` `flush()` 等。

来看一下 groovy 是如何利用闭包解决这个问题的:

```groovy
def writer = new FileWriter("/Users/kanshan/1.txt")

writer.write('hello world')
//writer.close() 忘记调用
```

我们打开了一个文件 并写入了一些信息，但是忘记调用了 `close()` 方法 导致资源不会释放。

groovy 对 `FileWriter` 对象添加了 `withWriter` 函数，这个函数支持接收一个闭包:

```groovy
def writer = new FileWriter("/Users/kanshan/1.txt")

writer.withWriter { w -> 
	w.write('hello world')
}
```

使用上面的方式 我们就不用担心资源没有被释放了，正应为闭包的特性 现在的 `close` 调用是自动的。


## Execute Around 模式

> 如果存在一对连续执行的操作，比如打开/关闭，这个时候可以使用 Execute Around 模式。(执行环绕模式?)

举个例子:

```groovy
def getText(path,call){
	def input
	try{
		input = new FileInputStream(new File(path))
		call(input)
	}finally{
		if(input != null){
			input.close()
		}
	}

}


getText("/Users/kanshan/Downloads/1.txt"){input -> 
	byte[] byts = new byte[input.available()]
	input.read(byts)
	println new String(byts)
}
```

输出结果:

```bash
hello world
```

上面就是使用 `Execute Around 模式` 设计的代码，可以看到我们不需要关心打开和关闭这些繁琐的操作

## 动态闭包

这里主要是判断是否提供了闭包的功能，如果没有提供闭包 那么可以使用一些默认的实现:

```groovy
def say(call){
	if(call){
		call()
	}else{
		println "default implements"
	}
}


say(){
	println "hello"
}

say()
```

输出结果:

```groovy
hello
default implements
```

显而易见，很简单的使用方式。

闭包函数自动添加了下面两种属性:

- maximunNumberOfParameters: 闭包参数个数
- parameterTypes: 闭包参数类型
