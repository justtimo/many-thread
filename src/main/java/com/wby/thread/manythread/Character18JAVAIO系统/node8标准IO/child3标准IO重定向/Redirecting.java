package com.wby.thread.manythread.Character18JAVAIO系统.node8标准IO.child3标准IO重定向;//: io/Redirecting.java
// Demonstrates standard I/O redirection.
import java.io.*;

/**
 * @Description: Java的System类提供了一些简单的静态方法调用，以允许我们对标准输入、输出和错误I/O流进行重定向∶
 * setIn(InputStream)
 * setOut(PrintStream)
 * setErr(PrintStream)
 *
 * 如果我们突然开始在显示器 上创建大量输出，而这些输出滚动得太快以至干无法阅读时，重定向输出就显得极为有用9。对于我们想重复测试某个特定用户的输入序列的命令行程序来说，重定向输入就很有价值。
 *
 * 下例简单演示了这些方法的使用∶
 */
public class Redirecting {
  public static void main(String[] args)
  throws IOException {
    PrintStream console = System.out;
    BufferedInputStream in = new BufferedInputStream(
      new FileInputStream("Redirecting.java"));
    PrintStream out = new PrintStream(
      new BufferedOutputStream(
        new FileOutputStream("test.out")));
    System.setIn(in);
    System.setOut(out);
    System.setErr(out);
    BufferedReader br = new BufferedReader(
      new InputStreamReader(System.in));
    String s;
    while((s = br.readLine()) != null)
      System.out.println(s);
    out.close(); // Remember this!
    System.setOut(console);
  }
} ///:~
/**
* @Description: 这个程序将标准输入附接到文件上，并将标准输出和标准错误重定向到另一个文件。注意，它在程序开头处存储了对最初的System.out对象的引用，并且在结尾处将系统输出恢复到了该对象上。
 * I/O重定向操纵的是字节流，而不是字符流;因此我们使用的是InputStream和Output- Stream，而不是Reader和Writer。
*/
