package com.wby.thread.manythread.Chapetor15泛型.node10通配符.child1编译器有多聪明;

import com.wby.thread.manythread.Chapetor15泛型.node10通配符.Apple;
import com.wby.thread.manythread.Chapetor15泛型.node10通配符.Fruit;
import com.wby.thread.manythread.Chapetor15泛型.node10通配符.Orange;

/**
 * @Description: 可以看到，对contains方法和indexOf方法的调用，这两个方法都接受Apple对象作为参数，而这些调用都可以正常执行。这是否意味着编译器实际上
 * 将检查代码，以查看是否有某个特定的方法修改了他的对象？
 * <p>
 * 通过查看ArrayList的文档，我们可以发现，编译器并没有这么聪明。尽管add方法接受一个具有泛型参数类型的参数，但是contains方法和indexOf方法都将
 * 接受Object类型的参数。因此当你指定一个ArrayList<? extends Fruit>时，add方法的参数就变成“? Extends Fruit”。
 * 从中可以看到，编译器并不能了解这里需要Fruit的哪个具体子类型，因此他不会接受任何类型的Fruit。如果先将Apple向上转型为Fruit，也无关紧要---编译器
 * 将直接拒绝对参数列表中设计通配符的方法(例如add方法)的调用。
 * <p>
 * 可以在一个非常简单地Holder类中看到这一点：见Holder.java
 */
public class Holder<T> {
    private T value;

    public Holder() {
    }

    public Holder(T val) {
        value = val;
    }

    public void set(T val) {
        value = val;
    }

    public T get() {
        return value;
    }

    public boolean equals(Object obj) {
        return value.equals(obj);
    }

    public static void main(String[] args) {
        Holder<Apple> Apple = new Holder<Apple>(new Apple());
        Apple d = Apple.get();
        Apple.set(d);
        // Holder<Fruit> Fruit = Apple; // Cannot upcast
        Holder<? extends Fruit> fruit = Apple; // OK
        Fruit p = fruit.get();
        d = (Apple) fruit.get(); // Returns 'Object'
        try {
            Orange c = (Orange) fruit.get(); // No warning
        } catch (Exception e) {
            System.out.println(e);
        }
        // fruit.set(new Apple()); // Cannot call set()
        // fruit.set(new Fruit()); // Cannot call set()
        System.out.println(fruit.equals(d)); // OK
    }
} /* Output: (Sample)
java.lang.ClassCastException: Apple cannot be cast to Orange
true
*///:~
