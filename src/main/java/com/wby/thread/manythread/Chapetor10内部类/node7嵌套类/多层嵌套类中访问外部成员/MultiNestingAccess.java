package com.wby.thread.manythread.Chapetor10内部类.node7嵌套类.多层嵌套类中访问外部成员;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/17 20:35
 * @Description: 一个内部类嵌套多少层不重要。他能透明的访问所有他嵌入的外围类的所有成员
 *  可以看到在class B中，调用g（）和f（）不需要任何条件（即使他们被定义为private）
 *  这个例子同事展示了如何从不同的类里创建多层嵌套的内部类对象的基本方法。 。new语法能产生正确的作用域，所以不必在调用构造器时限定类名
 */
public class MultiNestingAccess {
    public static void main(String[] args) {
        MMA mma = new MMA();
        MMA.A a = mma.new A();
        MMA.A.B b = a.new B();
        b.h();
    }
}
class MMA{
    private void f(){}
    class A{
        private void g(){}
        public class B{
            void h(){
                g();
                f();
            }
        }
    }
}
