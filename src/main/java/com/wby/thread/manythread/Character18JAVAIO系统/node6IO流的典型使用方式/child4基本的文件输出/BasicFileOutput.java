package com.wby.thread.manythread.Character18JAVAIO系统.node6IO流的典型使用方式.child4基本的文件输出;//: io/BasicFileOutput.java

import com.wby.thread.manythread.Character18JAVAIO系统.node6IO流的典型使用方式.child1缓冲输入文件.BufferedInputFile;

import java.io.*;

/**
* @Description: FileWriter对象可以向文件写入数据。首先，创建一个与指定文件连接的FileWriter。实际上，我们通常会用BufferedWriter将其包装起来用以缓冲输出
 * （尝试移除此包装来感受对性能的影响——缓冲往往能显著地增加I/O操作的性能）。
 *
 * 在本例中，为了提供格式化机制，它被装饰成了PrintWriter。按照这种方式创建的数据文件可作为普通文本文件读取。
*/
public class BasicFileOutput {
  static String file = "BasicFileOutput.out";
  public static void main(String[] args)
  throws IOException {
    BufferedReader in = new BufferedReader(
      new StringReader(
        BufferedInputFile.read("BasicFileOutput.java")));
    PrintWriter out = new PrintWriter(
      new BufferedWriter(new FileWriter(file)));
    int lineCount = 1;
    String s;
    while((s = in.readLine()) != null )
      out.println(lineCount++ + ": " + s);
    out.close();
    // Show the stored file:
    System.out.println(BufferedInputFile.read(file));
  }
} /* (Execute to see output) *///:~
/**
* @Description: 当文本行被写入文件时，行号就会增加。注意并未用到LineNumberInputStream，因为这个类没有多大帮助，所以我们没必要用它。从本例中可以看出，记录自己的行号很容易。
 *
 * 一旦读完输入数据流，readLineO）会返回null。我们可以看到要为out显式调用close（）。如果我们不为所有的输出文件调用closeO，就会发现缓冲区内容不会被刷新清空，那么它们也就不完整。
*/
/**
* @Description: 文本文件输出的快捷方式
 * Java SE5在PrintWriter中添加了一个辅助构造器，使得你不必在每次希望创建文本文件并向其中写入时，都去执行所有的装饰工作。
 *
 * 下面是用这种快捷方式重写的BasicFileOutput.java∶
*/
class FileOutputShortcut {
  static String file = "FileOutputShortcut.out";
  public static void main(String[] args)
          throws IOException {
    BufferedReader in = new BufferedReader(
            new StringReader(
                    BufferedInputFile.read("FileOutputShortcut.java")));
    // Here's the shortcut:
    PrintWriter out = new PrintWriter(file);
    int lineCount = 1;
    String s;
    while((s = in.readLine()) != null )
      out.println(lineCount++ + ": " + s);
    out.close();
    // Show the stored file:
    System.out.println(BufferedInputFile.read(file));
  }
} /* (Execute to see output) *///:~
/**
* @Description:  你仍旧是在进行缓存，只是不必自己去实现。遗憾的是，其他常见的写入任务都没有快捷方式，因此典型的I/O仍旧包含大量的冗余文本。
 * 但是，本书所使用的在本章稍后进行定义的 TextFile工具简化了这些常见任务。
*/
