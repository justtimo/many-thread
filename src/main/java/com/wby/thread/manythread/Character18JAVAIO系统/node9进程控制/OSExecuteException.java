//: net/mindview/util/OSExecuteException.java
package com.wby.thread.manythread.Character18JAVAIO系统.node9进程控制;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
* @Description: 你经常会需要在Java内部执行其他操作系统的程序，并且要控制这些程序的输入和输出。 Java类库提供了执行这些操作的类。
 *
 * 一项常见的任务是运行程序，并将产生的输出发送到控制台。本节包含了一个可以简化这项任务的实用工具。在使用这个实用工具时，可能会产生两种类型的错误;
 * 普通的导致异常的错误-—对这些错误我们只需重新抛出一个运行时异常，以及从进程自身的执行过程中产生的错误，我们希望用单独的异常来报告这些错误∶
*/
public class OSExecuteException extends RuntimeException {
  public OSExecuteException(String why) { super(why); }
} ///:~
/**
 * @Description: 要想运行一个程序，你需要向OSExecute.commandO传递一个command字符串，它与你在控制台上运行该程序所键入的命令相同。这个命令被传递给java.lang.ProcessBuilder构造器
 * （它要求这个命令作为一个String对象序列而被传递），然后所产生的ProcessBuilder对象被启动∶
 */
class OSExecute {
  public static void command(String command) {
    boolean err = false;
    try {
      Process process =
              new ProcessBuilder(command.split(" ")).start();
      BufferedReader results = new BufferedReader(
              new InputStreamReader(process.getInputStream()));
      String s;
      while((s = results.readLine())!= null)
        System.out.println(s);
      BufferedReader errors = new BufferedReader(
              new InputStreamReader(process.getErrorStream()));
      // Report errors and return nonzero value
      // to calling process if there are problems:
      while((s = errors.readLine())!= null) {
        System.err.println(s);
        err = true;
      }
    } catch(Exception e) {
      // Compensate for Windows 2000, which throws an
      // exception for the default command line:
      if(!command.startsWith("CMD /C"))
        command("CMD /C " + command);
      else
        throw new RuntimeException(e);
    }
    if(err)
      throw new OSExecuteException("Errors executing " +
              command);
  }
} ///:~
/**
* @Description: 为了捕获程序执行时产生的标准输出流，你需要调用getInputStream（），这是因为 InputStream是我们可以从中读取信息的流。
 * 从程序中产生的结果每次输出一行，因此要使用 readLineO来读取。这里这些行只是直接被打印了出来，但是你还可能希望从commandO中捕获和返回它们。
 * 该程序的错误被发送到了标准错误流，并且通过调用getErrotStreamO）得以捕获。如果存在任何错误，它们都会被打印并且会抛出OSExecuteException，因此调用程序需要处理这个问题。
 *
 * 下面是展示如何使用OSExecute的示例∶
*/
class OSExecuteDemo {
  public static void main(String[] args) {
    OSExecute.command("javap OSExecuteDemo");
  }
} /* Output:
Compiled from "OSExecuteDemo.java"
public class OSExecuteDemo extends java.lang.Object{
    public OSExecuteDemo();
    public static void main(java.lang.String[]);
}
*///:~
/**
* @Description:  这里使用了javap反编译器（随JDK发布）来反编译该程序。
*/
