//: annotations/Testable.java
package com.wby.thread.manythread.character20注解.node1基本语法;

import com.wby.thread.manythread.net.mindview.atunit.Test;

/**
 * 在下面的例子中，使用@Test对testExecuteO）方法进行注解。该注解本身并不做任何事情，但是编译器要确保在其构造路径上必须有@Test注解的定义。
 * 你将在本章中看到，程序员可以创建一个通过反射机制来运行testExecute（）方法的工具。
 */
public class Testable {
  public void execute() {
    System.out.println("Executing..");
  }
  @Test
  void testExecute() { execute(); }
} ///:~
/**
 * 被注解的方法与其他的方法没有区别。在这个例子中，注解@Test可以与任何修饰符共同作用干方法，例如public、static或void。从语法的角度来看，注解的使用方式几，平与修饰符的使用一模一样。
 */
