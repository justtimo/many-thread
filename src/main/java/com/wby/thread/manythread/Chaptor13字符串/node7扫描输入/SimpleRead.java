package com.wby.thread.manythread.Chaptor13字符串.node7扫描输入;//: strings/SimpleRead.java

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 *  目前为止，文件的输入读取数据还是很痛苦的一件事。
 *  一般的方式是，读入一行文本，对其进行分词，然后使用Integer、Double等类的方法进行数据解析
 *
 * @author LangWeiXian
 * @date 2021/07/05
 */
public class SimpleRead {
  public static BufferedReader input = new BufferedReader(
    new StringReader("Sir Robin of Camelot\n22 1.61803"));
  public static void main(String[] args) {
    try {
      System.out.println("What is your name?");
      String name = input.readLine();
      System.out.println(name);
      System.out.println(
        "How old are you? What is your favorite double?");
      System.out.println("(input: <age> <double>)");
      String numbers = input.readLine();
      System.out.println(numbers);
      String[] numArray = numbers.split(" ");
      int age = Integer.parseInt(numArray[0]);
      double favorite = Double.parseDouble(numArray[1]);
      System.out.format("Hi %s.\n", name);
      System.out.format("In 5 years you will be %d.\n",
        age + 5);
      System.out.format("My favorite double is %f.",
        favorite / 2);
    } catch(IOException e) {
      System.err.println("I/O exception");
    }
  }
} /* Output:
What is your name?
Sir Robin of Camelot
How old are you? What is your favorite double?
(input: <age> <double>)
22 1.61803
Hi Sir Robin of Camelot.
In 5 years you will be 27.
My favorite double is 0.809015.
*///:~
/**
* @Description:  StringRead将String转换为可读的流对象，然后用这个对象构造BufferedReader，因为我们要使用BufferedReader的readLine方法
 * 最终，我们可以使用readLine方法一次读取一行文本
 *
 * readLine方法将一行输入转为String对象。如果每一行数据正好对应一个输入值，那么这个方法是可行的。但是如果两个输入值在同一行，我们必须分解这个行才能
 * 分别翻译所需的输入值。上面的例子中，分解操作发生在创建numArray时，不过要注意，split方法是1,4中的方法，在此之前，你还需要做一些其他的准备。
 *
 * 1.5新增了Scanner，他可以大大减少扫描输入的工作负担。见BetterRead.java
*/
