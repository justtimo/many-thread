package com.wby.thread.manythread.charector14类型信息.node9接口与类型信息;//: typeinfo/InterfaceViolation.java
// Sneaking around an interface.



/**
* @Description: interface关键字的一种重要目标就是允许程序员隔离构件、进行降低耦合性。
 * 如果你编写接口，那么就可以实现这一目标，但是通过类型信息，这种耦合性还是会传播出去---接口并非是对解耦的一种无懈可击的保障。
 * 下面是个例子，先是一个接口：
*/
interface A{
  void  f();
}
/**
 * @Description: 然后实现这个接口，你可以看到代码是如何围绕着实际的实现类型潜行的：
 */
class B implements A {
  public void f() {}
  public void g() {}
}

public class InterfaceViolation {
  public static void main(String[] args) {
    A a = new B();
    a.f();
    // a.g(); // Compile error
    System.out.println(a.getClass().getName());
    if(a instanceof B) {
      B b = (B)a;
      b.g();
    }
  }
} /* Output:
B
*///:~
/**
* @Description:  通过RTTI我们发现a是被当做B实现的。通过将其转型为B，我们可以调用不在A中的方法。
 *  这是完全合法并可接受的，但是你也许并不想客户端程序员这么做，因为这给了他们机会，使得他们的代码与你的代码耦合度过高。
 *  也就是说，interface关键字并没有保护我，本例中使用B来实现A这是事实并且可查的(Windows操作系统)
 *
 *  一种解决方案是直接声明，如果程序员决定使用实际的类而不是接口，他们需要自己负责。但这可能还不够，也许你希望用更严苛的控制。
 *  最简单的方式就是对实现使用包访问权限，这样在包外部的客户端就不能看到他了：见HiddenC.java
*/

