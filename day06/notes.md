## Java 中的静态检查

```java
class Engine implements Cloneable{
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Car implements Cloneable{
    private Integer year;
    private Engine engine;
    @Override
    protected Object clone() {
        try {
            Car cloned = (Car)super.clone();
            Engine engineCloned = (Engine) engine.clone();
            engine = engineCloned;
            return cloned;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;    //没有必要的做法 只是为了满足编译器
        }
    }
}
```

这是一段对象的复制，因为希望不同的 car 引擎也是不一样的，所以我们重写了 clone 方法。同时复制了不同的引擎。

由于编译器坚持让我们处理`CloneNotSupportException`，才能进行对象的复制操作，其实在实例 `car` 调用 `clone()` 方法时已经明确了类型，但是编译器还是固执的让我们处理异常，而下面的 `Engine.clone()` 也是一样。

有的时候静态类型的检查只会带来烦恼，还是降低效率 在大部分时间内 这种检查只会浪费时间。

> 类型检查当然有他的好处 只不过在现代的 IDE 中，已经可以解决大部分的问题。



## 动态类型

动态类型放宽了对于静态类型的要求，能够在运行中对上下文判断 推导出应有的类型。

动态类型可以在不知道方法具体细节上进行调用，在运行期间 对象会动态的进行响应调用 甚至插入一些动态代码。

而在静态类型中，Java 使用了严格的类型检查 并使用多态继承的关系进行捆绑 然而我们做的大部分的事情 都是为了取悦编译器 比如在上面的那段代码中。

> 动态类型的好处多于静态类型。

## 使用静态类型

下面举一个例子来说明静态类型和动态类型实际的作用。


要实现一个帮忙送花的功能，起初我们可能会这样设计:

```java
class Man{
    boolean sendFlowers(){
        System.out.println("送花");
        return true;
    }
}

boolean takHelp(Man man){
    return man.sendFlowers();
}
```

由于是静态类型 所以我们需要一个已知的类型 这里就是 `Man` ，男人 那么如果也需要女人进行送花呢? 改一改实现:

```java

abstract class Person {
    abstract boolean  sendFlowers();
}

class Man extends Person{
    @Override
    boolean sendFlowers(){
        System.out.println("送花");
        return true;
    }
}

class Woman extends Person{
    @Override
    boolean sendFlowers(){
        System.out.println("送花");
        return true;
    }
}
```

可以看到，我们创建了一个抽象类 而男人和女人都实现了这个抽象类中的方法 有了送花的能力。 看起来还不错 但是有没有可能是动物帮你送花呢? 

```java


interface Helper {
    boolean  sendFlowers();
}

class Man implements Helper{
    @Override
    public boolean sendFlowers(){
        System.out.println("男人送花");
        return true;
    }
}

class Woman implements Helper{
    @Override
    public boolean sendFlowers(){
        System.out.println("女人送花");
        return true;
    }
}

class Dog implements Helper{

    @Override
    public boolean sendFlowers() {
        System.out.println("小狗送花");
        return true;
    }
}
```
由于是动物，所以不能用 Person 进行调用 因为动物不是人 
所以我们定义了一个 `Helper` 接口，并让 `Man` `WoMan` 和 `Dog` 实现了这个接口。


以上就是一个需求变动 并不断的修改接口类型，这样的扩展需要付出一些代价，接下来看看 groovy 的动态语言是如何实现的:

```groovy
def takeHepler(helper){
	helper.sendFlowers();
}
```

没错，在 groovy 中这样定义就可以了，能看到这里并没有固定类型，接下来看看实现:

```groovy
class Man{

	def sendFlowers(){
		println "男人送花"
	}

}

class WoMan{

	def sendFlowers(){
		println "女人送花"
	}

}


class Dog{

	def sendFlowers(){
		println "小狗送花"
	}

}

takeHepler(new Man())
takeHepler(new WoMan())
takeHepler(new Dog())
```

类中并没有实现任何的接口，是通过 groovy 的动态特征 才能够正常的发起调用，这也叫做`能力式设计`。

































