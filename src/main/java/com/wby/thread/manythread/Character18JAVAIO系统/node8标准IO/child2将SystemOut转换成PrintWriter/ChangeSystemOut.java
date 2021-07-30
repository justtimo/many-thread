package com.wby.thread.manythread.Character18JAVAIO系统.node8标准IO.child2将SystemOut转换成PrintWriter;//: io/ChangeSystemOut.java
// Turn System.out into a PrintWriter.

import java.io.PrintWriter;
/**
 * @Description: System.out 是一个PrintStream，而PrintStream是一个OutputStream。PrintWriter有一个可以接受OutputStream作为参数的构造器。
 * 因此，只要需要，就可以使用那个构造器把 System.out 转换成PrintWriter∶
 */
public class ChangeSystemOut {
  public static void main(String[] args) {
    PrintWriter out = new PrintWriter(System.out, true);
    out.println("Hello, world");
  }
} /* Output:
Hello, world
*///:~
/**
* @Description: 重要的是要使用有两个参数的PrintWriter的构造器，并将第二个参数设为true，以便开启自动清空功能;否则，你可能看不到输出。
*/
