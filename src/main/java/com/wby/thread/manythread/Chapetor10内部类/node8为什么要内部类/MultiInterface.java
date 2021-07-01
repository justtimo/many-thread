package com.wby.thread.manythread.Chapetor10内部类.node8为什么要内部类;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/18 19:32
 * @Description: 一般。内部类继承自某个类或实现某个接口，内部类的代码操作创建他的外部类的对象。所以可以认为：内部类提供了某种进入其外部类的窗口
 * 但是：如果只需要一个对接口的引用，为什么不通过外围类实现那个接口呢？答案是：如果这样能满足要求，那么就应该这么做。
 * 那么区别是什么呢？？外围类不是总能享用到接口的便利，有时需要用到接口的实现。
 * 所以使用内部类的原因是：每个内部类都能独立的继承一个(接口的)实现，所以无论外围类是否继承了某个(接口的)实现，对于内部类都没有影响
 *
 * 如果没有内部类提供的、可以继承多个具体的抽象的类的能力，一些编程问题很难解决。从此看，内部类使得多重继承的解决方案变得完整。
 * 接口解决了一部分问题，而内部类有效的实现了多重继承。也就是说，内部类允许继承多个非接口类型（类或抽象类）
 *
 * 下面的例子展示了这么一种情形：必须在一个类中以某种方式实现两个接口。 由于接口的灵活性，有两种选择：使用单一类，或使用内部类
 */
public class MultiInterface {
    static void testA(A a){}
    static void testB(B b){}

    public static void main(String[] args) {
        X x = new X();
        Y y = new Y();
        testA(x);
        testA(y);
        testB(x);
        testB(y.makeB());
    }
}
interface A{}
interface B{}
class X implements A,B{

}
class Y implements A{
    B makeB(){
        return new B() {
        };
    }
}
/**
* @Description: TODO 上面的例子假设在两种方式下的代码结构都有逻辑意义。然后遇到问题时，问题本身就能给出答案。告诉你是使用单一类，还是使用内部类。
 * 如果拥有的是 抽象类 或 具体的类，而不是接口，那就只能使用内部类才能实现多重继承。
 * 如果不需要解决多重继承，name可以使用别的方式，而不需要使用内部类。
*/
class D{}
abstract class E{}
class Z extends D{
    E makeE(){
        return new E() {
        };
    }
}
class MultiImplemetation{
    static void takesD(D d){}
    static void takesE(E e){}

    public static void main(String[] args) {
        Z z = new Z();
        takesD(z);
        takesE(z.makeE());
    }
}
/**
* @Description: TODO 但是如果使用内部类，。还可以获得其他特性：
 *  1： 内部类可以有多个实例，每个实例都可以有自己的状态，并且与其外围类对象的信息相互独立。
 *  2： 单个外围类中，可以让多个内部类以不同的方式实现同一个接口，或继承同一个类。
 *  3： 创建内部类的时刻并不依赖外围类对象的创建
 *  4： 内部类没有is-a，他是一个独立的实体
 *  举个例子：如果Sequence.java不使用内部类，就必须声明Sequence是一个Selector。对于某个特定Sequence只能有一个Selector。然而使用内部类就很容易拥有另外一个方法reverse（），用它生成一个反方向遍历序列的Selector。
 *  只有内部类才有这样的灵活性。
*/
