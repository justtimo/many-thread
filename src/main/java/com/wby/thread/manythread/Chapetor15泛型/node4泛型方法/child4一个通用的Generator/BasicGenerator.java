package com.wby.thread.manythread.Chapetor15泛型.node4泛型方法.child4一个通用的Generator;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/9 16:25
 * @Description:    下面的程序可以为任何类构造一个Generator，只要该类具有默认的构造器。为了减少类型声明，
 *  它提供了一个泛型方法，用以生成BasicGenerator：
 */
/*public class BasicGenerator<T> implements Generator<T> {
    private Class<T> type;
    public BasicGenerator(Class<T> type){ this.type = type; }
    public T next() {
        try {
            // Assumes type is a public class:
            return type.newInstance();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    // Produce a Default generator given a type token:
    public static <T> Generator<T> create(Class<T> type) {
        return new BasicGenerator<T>(type);
    }
} ///:~*/

import com.wby.thread.manythread.net.mindview.util.BasicGenerator;
import com.wby.thread.manythread.net.mindview.util.Generator;

/**
* @Description: 这个类提供了一个基本实现，用以生成某个类的对象。
 *  这个类必须具备两个特点：
 *      1.他必须声明为public。(因为BasicGenerator与要处理的类在不同的包中，所以该类必须声明为public，并且不只具有包内访问权限)
 *      2.他必须具备默认的构造器(无参构造器)。
 *  要创建这样的BasicGenerator对象，只需调用create()方法，并传入想要生成的类型。
 *  泛型化的create()方法允许执行BasicGenerator.create(MyType.class)，而不必执行麻烦的new BasicGenerator<MyType>(MyType.class)
 *
 *  例如，下面是一个具有默认构造器的简单的类：
*/
class CountedObject {
    private static long counter = 0;
    private final long id = counter++;
    public long id() { return id; }
    public String toString() { return "CountedObject " + id;}
} ///:~
/**
* @Description:  CountedObject类能够记录下他创建了多少个CountedObject实例，并通过toString()方法告诉我们其编号
 *  使用BasicGenerator，你可以很容易的为CountedObject创建一个Generator：
*/
class BasicGeneratorDemo {
    public static void main(String[] args) {
        Generator<CountedObject> gen =
                BasicGenerator.create(CountedObject.class);
        for(int i = 0; i < 5; i++)
            System.out.println(gen.next());
    }
} /* Output:
CountedObject 0
CountedObject 1
CountedObject 2
CountedObject 3
CountedObject 4
*///:~
/**
* @Description: 可以看到，使用泛型方法创建Generator对象，大大减少了我们要编写的代码。
 *  java泛型要求传入Class对象，以便也可以在create()方法中用它进行类型推断。
*/















