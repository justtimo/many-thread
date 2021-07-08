package com.wby.thread.manythread.charector14类型信息.node7动态代理;//: typeinfo/SimpleProxyDemo.java

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static com.wby.thread.manythread.net.mindview.util.Print.print;


/**
* @Description: 代理是基本的设计模式之一，他是你为了提供额外的或不同的操作，而插入的用来替代“实际”对象的对象
 *  这通常涉及与“实际”对象的通信，因此代理通常充当着中间人的角色
 *
 *  下面是展示代理结构的例子：
*/
interface Interface {
  void doSomething();
  void somethingElse(String arg);
}

class RealObject implements Interface {
  public void doSomething() {
    print("doSomething");
  }
  public void somethingElse(String arg) {
    print("somethingElse " + arg);
  }
}

class SimpleProxy implements Interface {
  private Interface proxied;
  public SimpleProxy(Interface proxied) {
    this.proxied = proxied;
  }
  public void doSomething() {
    print("SimpleProxy doSomething");
    proxied.doSomething();
  }
  public void somethingElse(String arg) {
    print("SimpleProxy somethingElse " + arg);
    proxied.somethingElse(arg);
  }
}

class SimpleProxyDemo {
  public static void consumer(Interface iface) {
    iface.doSomething();
    iface.somethingElse("bonobo");
  }
  public static void main(String[] args) {
    consumer(new RealObject());
    consumer(new SimpleProxy(new RealObject()));
  }
} /* Output:
doSomething
somethingElse bonobo
SimpleProxy doSomething
doSomething
SimpleProxy somethingElse bonobo
somethingElse bonobo
*///:~
/**
* @Description: 因为 consumer()接受的Interface，所以他无法知道正在获得的到底是RealObject还是SimpleProxy，因为二者都实现了Interface。
 *  但是SimpleProxy已经被插入到了客户端和RealObject之间，因此他会执行操作然后调用RealObject上相同的方法
 *
 *  任何时刻，只要你想将额外的操作从“实际”对象中分离到不同的地方，特别是你希望能够很容易的做出修改，从没有使用额外操作转为使用这些操作，或者反过来，代理此时就显得很有用
 *  (设计模式的关键就是封装修改---因此你需要修改事务来证明这种模式的正确性)
 *
 *  例如，你想跟踪对RealObject中方法的调用，或者希望度量这些调用的开销，那么该怎么做呢？
 *  这些代码你肯定不希望合并到应用中的代码，因此使用代理可以容易的添加或删除他们。
 *
 *  java的 动态代理 比代理的思想更进一步，因为他可以动态的创建代理并动态的处理对所代理方法的调用。
 *  动态代理上所有的调用都会被定向到单一的 调用处理器 上，他的工作是提示调用的类型并确定相应的对策。
 *  下面是用动态代理重写的SimpleProxyDemo.java
*/
class DynamicProxyHandler implements InvocationHandler {
  private Object proxied;
  public DynamicProxyHandler(Object proxied) {
    this.proxied = proxied;
  }
  public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
    System.out.println("**** proxy: " + proxy.getClass() +
            ", method: " + method + ", args: " + args);
    if(args != null){
      for(Object arg : args){
        System.out.println("  " + arg);
      }
    }
    return method.invoke(proxied, args);
  }
}

class SimpleDynamicProxy {
  public static void consumer(Interface iface) {
    iface.doSomething();
    iface.somethingElse("bonobo");
  }
  public static void main(String[] args) {
    RealObject real = new RealObject();
    consumer(real);
    // Insert a proxy and call again:
    Interface proxy = (Interface) Proxy.newProxyInstance(
            Interface.class.getClassLoader(),
            new Class[]{ Interface.class },
            new DynamicProxyHandler(real)
    );
    consumer(proxy);
  }
} /* Output: (95% match)
doSomething
somethingElse bonobo
**** proxy: class $Proxy0, method: public abstract void Interface.doSomething(), args: null
doSomething
**** proxy: class $Proxy0, method: public abstract void Interface.somethingElse(java.lang.String), args: [Ljava.lang.Object;@42e816
  bonobo
somethingElse bonobo
*///:~
/**
* @Description:  通过调用静态方法Proxy.newProxyInstance()可以创建动态打理，这个方法需要得到一个类加载器(通常可以从已经被加载的对象中获取其类加载器，然后传递给他)，
 * 一个你希望该代理实现的接口列表(不是类或抽象类)，以及一个InvocationHandler接口的一个实现。
 * 动态代理可以将所有调用重定向到调用处理器，因此通常会向调用处理器的构造器传递一个“实际”对象的引用，从而使得调用处理器在执行其中介任务时，可以转发请求。
 *
 * invoke()方法中传递进来了代理对象，以防你需要区分请求的资源，但许多情况下你并不关心这点。然而，invoke()内部，在代理上调用方法时需要格外小心，因为对接口
 * 的调用将被重定向为对代理的调用。
 *
 * 通常你会执行被代理的操作，然后使用method.invoke()将请求转发给被代理对象，并传入必需的参数。
 * 初看起来有些受限，就像你只能执行泛化操作一样，但是，你可以通过传入其他参数，来过滤某些方法的调用：
*/
class MethodSelector implements InvocationHandler {
  private Object proxied;
  public MethodSelector(Object proxied) {
    this.proxied = proxied;
  }
  public Object
  invoke(Object proxy, Method method, Object[] args)
          throws Throwable {
    if(method.getName().equals("interesting"))
      print("Proxy detected the interesting method");
    return method.invoke(proxied, args);
  }
}

interface SomeMethods {
  void boring1();
  void boring2();
  void interesting(String arg);
  void boring3();
}

class Implementation implements SomeMethods {
  public void boring1() { print("boring1"); }
  public void boring2() { print("boring2"); }
  public void interesting(String arg) {
    print("interesting " + arg);
  }
  public void boring3() { print("boring3"); }
}

class SelectingMethods {
  public static void main(String[] args) {
    SomeMethods proxy= (SomeMethods)Proxy.newProxyInstance(
            SomeMethods.class.getClassLoader(),
            new Class[]{ SomeMethods.class },
            new MethodSelector(new Implementation()));
    proxy.boring1();
    proxy.boring2();
    proxy.interesting("bonobo");
    proxy.boring3();
  }
} /* Output:
boring1
boring2
Proxy detected the interesting method
interesting bonobo
boring3
*///:~
/**
* @Description: 这里我们只查看了方法名，但是你还可以查看方法签名的其他方面，甚至可以搜索特定的参数值。
 *  动态代理并非日常使用的工具，但是他可以很好地解决某些问题，可以看 设计模式 这本书来了解更多有关代理以及设计模式的知识。
*/
