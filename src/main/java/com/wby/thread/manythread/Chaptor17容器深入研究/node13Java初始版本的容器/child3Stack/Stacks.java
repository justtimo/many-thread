package com.wby.thread.manythread.Chaptor17容器深入研究.node13Java初始版本的容器.child3Stack;//: containers/Stacks.java
// Demonstration of Stack Class.

import java.util.LinkedList;
import java.util.Stack;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;


/**
* @Description: 前面在使用LinkedList时，已经介绍过"栈"的概念。Java 1.0/1.1 的Stack很奇怪，竟然不是用Vector来构建Stack，而是继承Vector。
 * 所以它拥有Vector所有的特点和行为，再加上一些额外的Stack行为。很难了解设计者是否意识到这样做特别有用处，或者只是一个幼稚的设计。唯一清楚的是，
 * 在匆忙发布之前它没有经过仔细审查，因此这个糟糕的设计仍然挂在这里（但是你永远都不应该使用它）。
 *
 * 这里是Stack的一个简单示例，将enum中的每个String表示压入Stack。它还展示了你可以如何方便地将LinkedList，或者在第11章中创建的Stack类用作栈∶
*/
enum Month { JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
  JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER }

public class Stacks {
  public static void main(String[] args) {
    Stack<String> stack = new Stack<String>();
    for(Month m : Month.values())
      stack.push(m.toString());
    print("stack = " + stack);
    // Treating a stack as a Vector:
    stack.addElement("The last line");
    print("element 5 = " + stack.elementAt(5));
    print("popping elements:");
    while(!stack.empty())
      printnb(stack.pop() + " ");

    // Using a LinkedList as a Stack:
    LinkedList<String> lstack = new LinkedList<String>();
    for(Month m : Month.values())
      lstack.addFirst(m.toString());
    print("lstack = " + lstack);
    while(!lstack.isEmpty())
      printnb(lstack.removeFirst() + " ");

    // Using the Stack class from
    // the Holding Your Objects Chapter:
    Stack<String> stack2 =
      new Stack<String>();
    for(Month m : Month.values())
      stack2.push(m.toString());
    print("stack2 = " + stack2);
    while(!stack2.empty())
      printnb(stack2.pop() + " ");

  }
} /* Output:
stack = [JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER]
element 5 = JUNE
popping elements:
The last line NOVEMBER OCTOBER SEPTEMBER AUGUST JULY JUNE MAY APRIL MARCH FEBRUARY JANUARY lstack = [NOVEMBER, OCTOBER, SEPTEMBER, AUGUST, JULY, JUNE, MAY, APRIL, MARCH, FEBRUARY, JANUARY]
NOVEMBER OCTOBER SEPTEMBER AUGUST JULY JUNE MAY APRIL MARCH FEBRUARY JANUARY stack2 = [NOVEMBER, OCTOBER, SEPTEMBER, AUGUST, JULY, JUNE, MAY, APRIL, MARCH, FEBRUARY, JANUARY]
NOVEMBER OCTOBER SEPTEMBER AUGUST JULY JUNE MAY APRIL MARCH FEBRUARY JANUARY
*///:~
/**
* @Description: String表示是从Month enum常量中生成的，用pushO）插入Stack，然后再从栈的顶端弹出来（用popO）。这里要特别强调∶ 可以在Stack对象上执行Vector的操作。
 * 这不会有任何问题，因为继承的作用使得Stack是一个Vector，因此所有可以对Vector执行的操作，都可以对Stack执行，例如elementAtO。
 *
 * 前面曾经说过，如果需要栈的行为，应该使用LinkedList，或者从LinkedList类中创建的 net.mindview.util.Stack类。
*/
