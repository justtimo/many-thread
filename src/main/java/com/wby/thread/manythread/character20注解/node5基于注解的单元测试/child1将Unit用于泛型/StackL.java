//: annotations/StackL.java
// A stack built on a linkedList.
package com.wby.thread.manythread.character20注解.node5基于注解的单元测试.child1将Unit用于泛型;

import com.wby.thread.manythread.net.mindview.atunit.Test;
import com.wby.thread.manythread.net.mindview.util.OSExecute;

import java.util.LinkedList;

/**
 * 泛型为@Unit出了一个难题，因为我们不可能"泛泛地测试"。我们必须针对某个特定类型的参数或参数集才能进行测试。解决的办法很简单∶让测试类继承自泛型类的一个特定版本即可。
 * 下面是一个堆栈的例子∶
 * @param <T>
 */
public class StackL<T> {
  private LinkedList<T> list = new LinkedList<T>();
  public void push(T v) { list.addFirst(v); }
  public T top() { return list.getFirst(); }
  public T pop() { return list.removeFirst(); }
} ///:~
/**
 * 要测试String版的堆栈，就让测试类继承自StackL<String>∶
 */
class StackLStringTest extends StackL<String> {
  @Test
  void _push() {
    push("one");
    assert top().equals("one");
    push("two");
    assert top().equals("two");
  }
  @Test void _pop() {
    push("one");
    push("two");
    assert pop().equals("two");
    assert pop().equals("one");
  }
  @Test void _top() {
    push("A");
    push("B");
    assert top().equals("B");
    assert top().equals("B");
  }
  public static void main(String[] args) throws Exception {
    OSExecute.command(
            "java net.mindview.atunit.AtUnit StackLStringTest");
  }
} /* Output:
annotations.StackLStringTest
  . _push
  . _pop
  . _top
OK (3 tests)
*///:~
/**
 * 这种方法潜在的唯一缺点是;继承使我们失去了访问被测试的类中的private方法的能力。如果这对你很重要，那你要么将private方法改为protected，要么添加—个非private的@TestProperty方法，
 * 由它来调用private方法（稍候我们会看到，AtUnitRemover工具会将@TestProperty方法从产品的代码中自动删除掉）。
 */
