package com.wby.thread.manythread.Chaptor13字符串.node5格式化输出.child6StringFormat;//: strings/DatabaseException.java

import com.wby.thread.manythread.net.mindview.util.BinaryFile;

import java.io.File;

/**
* @Description: String.format()是一个静态方法，他接受与Formatter.format()方法一样的参数，但返回一个String对象
 *  当你只需使用format()方法一次的时候，String.format()用起来很方便。
*/
public class DatabaseException extends Exception {
  public DatabaseException(int transactionID, int queryID, String message) {
    super(String.format("(t%d, q%d) %s", transactionID, queryID, message));
  }
  public static void main(String[] args) {
    try {
      throw new DatabaseException(3, 7, "Write failed");
    } catch(Exception e) {
      System.out.println(e);
    }
  }
} /* Output:
DatabaseException: (t3, q7) Write failed
*///:~
/**
* @Description: 其实String.format内部，他也是创建一个Formatter对象，然后将传入的参数转给该Formatter。
*/
/**
* @Description: 十六进制转储工具。
 *  下面的例子展示了String.format()方法，以可读的十六进制格式将字节数组打印出来：
*/
class Hex {
  public static String format(byte[] data) {
    StringBuilder result = new StringBuilder();
    int n = 0;
    for(byte b : data) {
      if(n % 16 == 0)
        result.append(String.format("%05X: ", n));
      result.append(String.format("%02X ", b));
      n++;
      if(n % 16 == 0) result.append("\n");
    }
    result.append("\n");
    return result.toString();
  }
  public static void main(String[] args) throws Exception {
    if(args.length == 0)
      // Test by displaying this class file:
      System.out.println(
              format(BinaryFile.read("Hex.class")));
    else
      System.out.println(
              format(BinaryFile.read(new File(args[0]))));
  }
} /* Output: (Sample)
00000: CA FE BA BE 00 00 00 31 00 52 0A 00 05 00 22 07
00010: 00 23 0A 00 02 00 22 08 00 24 07 00 25 0A 00 26
00020: 00 27 0A 00 28 00 29 0A 00 02 00 2A 08 00 2B 0A
00030: 00 2C 00 2D 08 00 2E 0A 00 02 00 2F 09 00 30 00
00040: 31 08 00 32 0A 00 33 00 34 0A 00 15 00 35 0A 00
00050: 36 00 37 07 00 38 0A 00 12 00 39 0A 00 33 00 3A
...
*///:~
/**
* @Description: 为了打开以及读入二进制文件，我们用到了另一个工具：net.mindview.util.BinaryFile，18章会讲到他。这里的read()方法将整个文件以byte数组的形式返回
*/
