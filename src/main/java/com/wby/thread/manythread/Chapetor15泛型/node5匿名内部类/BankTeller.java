package com.wby.thread.manythread.Chapetor15泛型.node5匿名内部类;//: generics/BankTeller.java
// A very simple bank teller simulation.

import com.wby.thread.manythread.net.mindview.util.Generator;
import java.util.*;

/**
* @Description: 泛型还可以应用于内部类以及匿名内部类。下面的示例使用匿名内部类实现了Generator接口：
*/
class Customer {
  private static long counter = 1;
  private final long id = counter++;
  private Customer() {}
  public String toString() { return "Customer " + id; }
  // A method to produce Generator objects:
  public static Generator<Customer> generator() {
    return new Generator<Customer>() {
      public Customer next() { return new Customer(); }
    };
  }
}

class Teller {
  private static long counter = 1;
  private final long id = counter++;
  private Teller() {}
  public String toString() { return "Teller " + id; }
  // A single Generator object:
  public static Generator<Teller> generator =
    new Generator<Teller>() {
      public Teller next() { return new Teller(); }
    };
}

public class BankTeller {
  public static void serve(Teller t, Customer c) {
    System.out.println(t + " serves " + c);
  }
  public static void main(String[] args) {
    Random rand = new Random(47);
    Queue<Customer> line = new LinkedList<Customer>();
    Generators.fill(line, Customer.generator(), 15);
    List<Teller> tellers = new ArrayList<Teller>();
    Generators.fill(tellers, Teller.generator, 4);
    for(Customer c : line)
      serve(tellers.get(rand.nextInt(tellers.size())), c);
  }
} /* Output:
Teller 3 serves Customer 1
Teller 2 serves Customer 2
Teller 3 serves Customer 3
Teller 1 serves Customer 4
Teller 1 serves Customer 5
Teller 3 serves Customer 6
Teller 1 serves Customer 7
Teller 2 serves Customer 8
Teller 3 serves Customer 9
Teller 3 serves Customer 10
Teller 2 serves Customer 11
Teller 4 serves Customer 12
Teller 2 serves Customer 13
Teller 1 serves Customer 14
Teller 1 serves Customer 15
*///:~
/**
* @Description: Customer和Teller类都只有private的构造器，这可以强制你必须使用Customer对象。Customer有一个generator方法 ，每次执行
 * 他都会生成一个新的Generator<Customer>对象。我们其实不需要多个Generator对象，Teller就只创建了一个public的generator对象。
 * 在main（）方法中可以看到，这两种创建Generator的方式都是fill（）中用到了
 *
 *  由于customer中的generator（0方法，以及Teller中的Generator对象都声明称了static的，所以他们无法作为接口的一部分，因此无法用接口这种特定的
 *  惯用法来泛化这二者。尽管如此，他们在fill（）方法中都工作的很好
 *
 *  21章中，我们还会看到关于这个排队问题的另外一个版本
*/
