package com.wby.thread.manythread.Chaptor13字符串.node7扫描输入;//: strings/BetterRead.java

import java.util.Scanner;

/**
* @Description: Scanner构造器可以接受可以任意类型的输入对象，包括File对象、InputStream、String或者想此例中的Readable对象。
 *  Readable是1.5新增的接口，表示：具有read方法的某个东西。
 *  有了Scanner，所有的基本类型(除了char)都有对应的next方法，包括BigDecimal和BigInteger。
 *  所有的next方法，只有在找到一个完整的分词之后才能返回。
 *  Scanner还有hasNext方法，用以判断下一个输入分词是否所需的类型
 *
 *  这两个例子中有一个有趣的区别：BetterRead.java并没有针对IOException添加try区块，因为Scanner有一个假设，在输入结束时会抛出IOException，所以Scanner会把IOException吞掉
 *  不过，你可以通过ioException方法，你可以找到最近发生的异常，因此，你可以在必要时检查他
*/
public class BetterRead {
  public static void main(String[] args) {
    Scanner stdin = new Scanner(SimpleRead.input);
    System.out.println("What is your name?");
    String name = stdin.nextLine();
    System.out.println(name);
    System.out.println(
      "How old are you? What is your favorite double?");
    System.out.println("(input: <age> <double>)");
    int age = stdin.nextInt();
    double favorite = stdin.nextDouble();
    System.out.println(age);
    System.out.println(favorite);
    System.out.format("Hi %s.\n", name);
    System.out.format("In 5 years you will be %d.\n",
      age + 5);
    System.out.format("My favorite double is %f.",
      favorite / 2);
  }
} /* Output:
What is your name?
Sir Robin of Camelot
How old are you? What is your favorite double?
(input: <age> <double>)
22
1.61803
Hi Sir Robin of Camelot.
In 5 years you will be 27.
My favorite double is 0.809015.
*///:~
