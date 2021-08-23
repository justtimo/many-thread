package com.wby.thread.manythread.character9接口.工厂方法设计模式;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/9 19:15
 * @Description: 如果不是工厂方法，你就必须在某处指定将要创建的Service的确切类型，以便调用合适的构造器
 */
public class 工厂方法设计模式 {
    public static void serviceConsumer(ServiceFactory fact){
        Service s = fact.getService();
        s.method1();
        s.method2();
    }
    public static void main(String[] args) {
        serviceConsumer(new Implementation1Factiory());
        serviceConsumer(new Implementation2Factiory());
    }
}
interface Service{
    void method1();
    void method2();
}
interface ServiceFactory{
    Service getService();
}
class Implementation1 implements Service {

    @Override
    public void method1() {
        System.out.println("Implementation1 method1");
    }

    @Override
    public void method2() {
        System.out.println("Implementation1 method2");
    }
}
class Implementation1Factiory implements ServiceFactory{

    @Override
    public Service getService() {
        return new Implementation1();
    }
}
class Implementation2 implements Service {

    @Override
    public void method1() {
        System.out.println("Implementation2 method1");
    }

    @Override
    public void method2() {
        System.out.println("Implementation2 method2");
    }
}
class Implementation2Factiory implements ServiceFactory{

    @Override
    public Service getService() {
        return new Implementation2();
    }
}
