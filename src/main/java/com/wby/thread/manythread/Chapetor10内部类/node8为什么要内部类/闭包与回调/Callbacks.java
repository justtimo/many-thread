package com.wby.thread.manythread.Chapetor10内部类.node8为什么要内部类.闭包与回调;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/22 19:22
 * @Description: 闭包是一个可调用对象，他记录了一些信息，信息来自于它的作用域。
 *  通过这个定义，可以看到内部类是面向对象的闭包，因为他不仅包含外围类对象（创建内部类的作用域）的信息，还自动拥有一个指向此外围对象的引用，在此作用域内，内部类有权操作包括private的所有成员
 *  人们认为java应该包含某种类似指针的机制，以便 允许回调。通过回调，对象能够携带一些信息，这些信息允许它在稍后某个时刻调用初始的对象。
 *  通过内部类提供闭包功能是优良的解决方案，他比指针更灵活、安全。见下例：
 */
public class Callbacks {
    public static void main(String[] args) {
        Calleel c1 = new Calleel();
        Calleel2 c2 = new Calleel2();

        MyIncrement.f(c2);

        Caller caller1 = new Caller(c1);
        Caller caller2 = new Caller(c2.getCallbackRefrence());
        caller1.go();
        caller1.go();
        caller2.go();
        caller2.go();
    }
}


interface Incrementable{
    void increment();
}

class MyIncrement{
    public void increment() {
        System.out.println("other operation");
    }
    static void f(MyIncrement mi){
        mi.increment();
    }
}

class Calleel implements Incrementable {
    private int i=0;

    @Override
    public void increment() {
        i++;
        System.out.println(i);
    }
}

class Calleel2 extends MyIncrement{
    private int i=0;
    public void increment() {
        super.increment();
        i++;
        System.out.println(i);
    }
    private class Closure implements Incrementable{
        @Override
        public void increment() {
            Calleel2.this.increment();
        }
    }
    Incrementable getCallbackRefrence(){
        return new Closure();
    }
}


class Caller{
    private Incrementable callbackRefrence;
    Caller(Incrementable cbh) {
        callbackRefrence = cbh;
    }
    void go(){
        callbackRefrence.increment();
    }
}
/**
* @Description: 这个例子进一步展示了外围类实现了一个接口与内部类实现此接口之间的区别。
 *  Callee1是简单地解决方式。
 *  Callee2继承自MyIncrement，后者已经有了一个不同的increment()方法，并且与Incrementable接口期望的increment（）方法完全不相关。
 *  所以如果Callee2继承了MyIncrement，就不能为了Incrementable的用途而覆盖increment()方法，
 *
 *  于是只能使用内部类独立的实现Incrementable。还要注意，当创建一个内部类时，并没有在外围类接口中添加东西，也没有修改外围类的接口。
 *
 *  注意，在Callee2中除了getCallbackRefrence()以外，其他成员都是private的。要想建立与外部世界的任何连接，interface Incrementable都是必需的。在这里可以看到，interface是如何允许接口与接口的实现完全独立的。
 *
 *  内部类Closure实现了Incrementable，以提供一个Callee2的’钩子‘--而且是一个安全的钩子。无论谁获得此Incrementable的引用，都只能调用increment()，除此之外没有其他功能。
 *
 *  Callee的构造器需要一个Incrementable的引用作为参数（虽然可以在任意时刻捕获回调引用），然后在某个时刻，Callee对象可以使用此引用回调Callee类。
 *
 *  回调的价值在于他的灵活性---可以在运行时动态的决定需要调用什么方法。
*/

