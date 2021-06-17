package com.wby.thread.manythread.Chapetor10内部类.node6匿名内部类.再访工厂方法;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/17 19:06
 * @Description:  现在用于Implementation1和Implementation2的构造器都可以是private的，并且没有必要创建作为工厂的具名类。
 *  另外，你经常只需要单一的工厂对象，因此在本例子中他被创建为Service实现中的一个static域。
 *   这样的语法也更有实际意义
 */
public class Factories {
    public static void serviceConsumer(ServiceFactory sf){
        Service service = sf.getService();
        service.method1();;
        service.method2();
    }

    public static void main(String[] args) {
        serviceConsumer(Implementation1.factory);
        serviceConsumer(Implementation2.factory);
    }
}
interface Service{
    void method1();
    void method2();
}
interface ServiceFactory {
    Service getService();
}
class Implementation1 implements Service {
    private Implementation1(){}

    @Override
    public void method1() {
        System.out.println("Implementation1 method1");
    }

    @Override
    public void method2() {
        System.out.println("Implementation1 method2");
    }
    public static ServiceFactory factory=new ServiceFactory() {
        @Override
        public Service getService() {
            return new Implementation1();
        }
    };
}
class Implementation2 implements Service {
    private Implementation2(){}

    @Override
    public void method1() {
        System.out.println("Implementation2 method1");
    }

    @Override
    public void method2() {
        System.out.println("Implementation2 method2");
    }
    public static ServiceFactory factory=new ServiceFactory() {
        @Override
        public Service getService() {
            return new Implementation2();
        }
    };
}
