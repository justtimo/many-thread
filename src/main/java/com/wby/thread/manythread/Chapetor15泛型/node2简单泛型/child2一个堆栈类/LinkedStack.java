package com.wby.thread.manythread.Chapetor15泛型.node2简单泛型.child2一个堆栈类;//: generics/LinkedStack.java
// A stack implemented with an internal linked structure.

/**
* @Description: 接下来我们看一下稍微复杂的例子：传统的下推堆栈。
 *  11章中，我们看到这个堆栈是作为util.Stack类，用一个LinkedList实现的。
 *  那个例子中，LinkedList本身已经具备了创建堆栈所必须的方法，而Stack可以通过两个泛型的类Stack<T>和LinkedList<T>的组合来创建。在那个示例中，
 *  我们可以看出，泛型类型也就是另一种类型罢了(稍后我们会看到一些例外的情况)
 *  现在我们不用LinkedList，来实现自己的内部链式存储机制。
*/
public class LinkedStack<T> {
  private static class Node<U> {
    U item;
    Node<U> next;
    Node() { item = null; next = null; }
    Node(U item, Node<U> next) {
      this.item = item;
      this.next = next;
    }
    boolean end() { return item == null && next == null; }
  }
  private Node<T> top = new Node<T>(); // End sentinel
  public void push(T item) {
    top = new Node<T>(item, top);
  }
  public T pop() {
    T result = top.item;
    if(!top.end())
      top = top.next;
    return result;
  }
  public static void main(String[] args) {
    LinkedStack<String> lss = new LinkedStack<String>();
    for(String s : "Phasers on stun!".split(" "))
      lss.push(s);
    String s;
    while((s = lss.pop()) != null)
      System.out.println(s);
  }
} /* Output:
stun!
on
Phasers
*///:~
/**
* @Description: 内部类Node也是一个泛型，它拥有自己的类型参数
 *  这个例子使用了一个 末端哨兵 来判断堆栈何时为空。
 *  这个末端哨兵是在构造LinkedStack时创建的。然后，每调用一次push()方法，就会创建一个Node<T>对象，并将其链接到前一个Node<T>对象。当你调用pop()方法时，
 *  总是返回top.item，然后丢弃当前top所指的Node<T>，并将top转移到下一个Node<T>，除非你已经碰到了末端哨兵，这时候就不在移动top了。如果已经到了末端，
 *  客户端程序还继续调用pop()方法，他只能得到null，说明堆栈已经空了。
*/




















