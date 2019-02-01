## 动态类型需要自律

由于动态类型是在运行中推导上下文所需要的对象，如果错误的传递了不包含所需要执行的函数时 会抛出异常:

```groovy
def takeHepler(helper){
    helper.sendFlowers();
}

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

}


takeHepler(new Man())
takeHepler(new WoMan())
takeHepler(new Dog())

Caught: groovy.lang.MissingMethodException: No signature of method: Dog.sendFlowers() is applicable for argument types: () values: []
groovy.lang.MissingMethodException: No signature of method: Dog.sendFlowers() is applicable for argument types: () values: []
    at WoMan$sendFlowers.call(Unknown Source)
    at dynamic.takeHepler(dynamic.groovy:4)
    at dynamic$takeHepler.callCurrent(Unknown Source)
    at dynamic.run(dynamic.groovy:31)

```

要解决这个问题 可以使用 `respondsTo` 方法:


```groovy
def takeHepler(helper){
    if(helper.metaClass.respondsTo(helper,'sendFlowers')){
        helper.sendFlowers();
    }
    
}
```

通过上面的方式来判断对象是否有能力去做这件事。

## 可选类型

groovy 虽然是动态类型的，但是可以根据使用让 groovy 变为一个静态类型 这两个极端开发者可以进行切换。

比如我们使用 `def` 去定义一个方法的返回值 然后这个方法实际会返回什么值我们不确定，所以你也可以确定返回的类型 比如基本数据类型和引用数据类型。

```groovy
X var = 1
```

在上面的代码中，如果 `X` 是一个引用对象，那么在执行中 `groovy` 会把这段代码变为下面这样:

```groovy
X var = (X)1
```

这里如果转换失败了 会抛出异常信息。 所以在 groovy 中 我们可以把类型定义的很模糊 让 groovy 去帮我们处理，也可以定义的很精准。


## 多方法

先观察下面的 Java 代码:

```java
import java.math.BigDecimal;

class Employee{

    public void raise(Number number){
        System.out.println("Employee got raise");
    }
}

class Executive extends Employee{

    @Override
    public void raise(Number number){
        System.out.println("Executive got raise");
    }

    public void raise(BigDecimal bigDecimal){
        System.out.println("Executive got outlandish raise");
    }
}



public class MultiMethod {

    public static void main(String[] args) {
        call(new Employee());
        call(new Executive());
    }

    static void call(Employee employee){
        employee.raise(new BigDecimal(1000.00));
    }
}

```

执行之后的输出结果是:

```bash
Employee got raise
Executive got raise
```

代码中重写了 `raise` 方法,让 `raise` 变成了一个多态的函数`(相同的表现，不同的实现)`.

看起来没有什么问题，多态的调用特征本就是: `被调用的方法依赖并不是目标的引用类型，而是所引用对象的实际类型`

在 `call` 方法中，`call` 作为一个被调用的方法，`Employee` 表示所引用对象的实际类型 也就是目标的实际类型，而传递到 `call` 方法中的为目标的引用类型。

然后在 groovy 中 结果就不是这样了:


```groovy
class Employee{

    public void raise(Number number){
        System.out.println("Employee got raise");
    }
}

class Executive extends Employee{

    @Override
    public void raise(Number number){
        System.out.println("Executive got raise");
    }

    public void raise(BigDecimal bigDecimal){
        System.out.println("Executive got outlandish raise");
    }
}


def call(Employee employee){
        employee.raise(new BigDecimal(1000.00));
}


call(new Employee());
call(new Executive());
```

结果:

```bash
Employee got raise
Executive got outlandish raise
```

看到了吗，和 Java 截然相反。

如果一个类中有重载的方法，groovy 会聪明的选择正确的实现，不仅基于目标对象(调用方法的对象) ，还基于所提供的参数。

> 因为方法分配基于多个实体--目标加参数。 所以这被称作多分派或多方法。

```java
import java.util.ArrayList;
import java.util.Collection;

public class UseingCollection {

    public static void main(String[] args) {

        ArrayList<String> arr1 = new ArrayList<>();
        Collection<String> arr2 = arr1;

        arr1.add("apple");
        arr1.add("huawei");
        arr1.add("xiaonmi");

//        arr1.remove(0);
        arr2.remove(0);
        
        System.out.println(arr1.size());
        System.out.println(arr2.size());
    }
}
```

结果:

```bash
3
3
```

这里 arr2 和 arr1 都指向的是同一个引用。由于 arr2 是 `Collection` 实例，而 `Collection` 中只提供了 `remove(Object obj)` 方法，根据索引进行删除并不是 `Collection` 对象的方法，数组中并没有`0`这个对象 所以无法移出 如果要实现 则需要这样写:

```java
import java.util.ArrayList;
import java.util.Collection;

public class UseingCollection {

    public static void main(String[] args) {

        ArrayList<String> arr1 = new ArrayList<>();
        Collection<String> arr2 = arr1;

        arr1.add("apple");
        arr1.add("huawei");
        arr1.add("xiaonmi");

//        arr1.remove(0);
//        arr2.remove(0);

        ((ArrayList<String>) arr2).remove(0);

        System.out.println(arr1.size());
        System.out.println(arr2.size());
    }
}
```

结果:

```bash
2
2
```

强制转换为 `ArrayList` ，然后调用 `remove(int index)` 进行移除。

接下来把上面的代码放到 groovy 中:

```groovy
ArrayList<String> arr1 = new ArrayList<>();
Collection<String> arr2 = arr1;

arr1.add("apple");
arr1.add("huawei");
arr1.add("xiaonmi");

//arr1.remove(0);
arr2.remove(0);

System.out.println(arr1.size());
System.out.println(arr2.size());
```

结果:

```bash
2
2
```

在 groovy 中是可以进行正常的移除的，这是因为动态和多方法的特性。

## 启用静态类型检查

```groovy
def call(String str){
    str.toUppeCase()
}
```

这是一个把字符串转换为大写的功能，可如果我们写错了方法名 默认请下 还是可以通过编译 只是在实际调用中会抛出异常。

可以通过添加 `@TypeChecked` 注解开启静态类型检查:

```groovy
@groovy.transform.TypeChecked
def call(String str){
    str.toUppeCase()
}
```

编译时:
```bash
[Static type checking] - Cannot find matching method java.lang.String#toUppeCase(). Please check if the declared type is correct and if the method exists.
 @ line 6, column 2.
    str.toUppeCase()
    ^

1 error
```

在开启静态类型检查的时候，需要确认形参是具体的静态类型，如果没有静态类型 那么一样会抛出异常 比如下面这样:

```groovy
@groovy.transform.TypeChecked
def call(str){
    str.toUppeCase()
}
```
