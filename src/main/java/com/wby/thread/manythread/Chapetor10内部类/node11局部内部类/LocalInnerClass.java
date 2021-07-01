package com.wby.thread.manythread.Chapetor10内部类.node11局部内部类;

/**
 * @Description:    之前说过，可以在代码块里创建内部类，典型的方式是在一个方法体的里面创建。局部内部类不能有访问说明符，因为他不是外围类的一部分。
 *  但是他可以访问当前代码块内的常量，以及外围类的所有成员。
 *
 *  下面的例子对 局部内部类 和 匿名内部类 的创建爱你进行了比较
 */
public class LocalInnerClass {
    private int count = 0;
    Counter getCounter(final String name){
        class LocalCounter implements Counter {
            private LocalCounter() {
                System.out.println("LocalCounter");
            }
            @Override
            public int next() {
                System.out.println(name);//access local final
                return count++;
            }
        }
        return new LocalCounter();
    }
    Counter getCounter2(final String name){
        return new Counter() {
            //anonymous inner class cannot have name
            //constructor .only an instance initializer
            {
                System.out.println("counter()");
            }
            @Override
            public int next() {
                System.out.println(name);//access local final
                return count++;
            }
        };
    }

    public static void main(String[] args) {
        LocalInnerClass lic = new LocalInnerClass();
        Counter
                c1=lic.getCounter("Local Inner"),
                c2= lic.getCounter2("anonymous inner");
        for (int i = 0; i < 5; i++) {
            System.out.println(c1.next());
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(c2.next());
        }
    }
}
interface Counter{
    int next();
}
/**
* @Description: Counter返回的是序列中的下一个值。我们分别使用局部内部类和匿名内部类实现了这个功能，他们具有相同的行为和能力。
 *  既然局部内部类的名字在方法外是不可见的，那么为什么还要使用局部内部类呢？唯一的理由就是，我们需要一个已命名的构造器，或者需要重载构造器，而匿名内部类只能用于实例初始化。
 *  所以使用局部内部类而不使用匿名内部类的另一个理由是，需要不止一个该内部类对象。
*/
