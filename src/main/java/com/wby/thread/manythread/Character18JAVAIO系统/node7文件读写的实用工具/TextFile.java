//: net/mindview/util/TextFile.java
// Static functions for reading and writing text files as
// a single string, and treating a file as an ArrayList.
package com.wby.thread.manythread.Character18JAVAIO系统.node7文件读写的实用工具;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
* @Description: 一个很常见的程序化任务就是读取文件到内存，修改，然后再写出。Java I/O类库的问题之一就是;它需要编写相当多的代码去执行这些常用操作——没有任何基本的帮助功能可以为我们做这一切。
 * 更糟糕的是，装饰器会使得要记住如何打开文件变成一件相当困难的事。因此，在我们的类库中添加帮助类就显得相当有意义，这样就可以很容易地为我们完成这些基本任务。
 *
 * Java SE5在PrintWriter中添加了方便的构造器，因此你可以很方便地打开一个文本文件进行写入操作。但是，还有许多其他的常见，操作是你需要反复执行的。文就使得消除与这些任务相关联的重复代码就显得很有意义了。
 *
 * 下面的TextFile类在本书前面的示例中就已经被用来简化对文件的读写操作了。它包含的 static方法可以像简单字符串那样读写文本文件，并且我们可以创建一个TextFile对象，
 * 它用一个ArrayList来保存文件的若干行（如此，当我们操纵文件内容时，就可以使用ArrayList的所有功能）。
*/
public class TextFile extends ArrayList<String> {
  // Read a file as a single string:
  public static String read(String fileName) {
    StringBuilder sb = new StringBuilder();
    try {
      BufferedReader in= new BufferedReader(new FileReader(
        new File(fileName).getAbsoluteFile()));
      try {
        String s;
        while((s = in.readLine()) != null) {
          sb.append(s);
          sb.append("\n");
        }
      } finally {
        in.close();
      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    return sb.toString();
  }
  // Write a single file in one method call:
  public static void write(String fileName, String text) {
    try {
      PrintWriter out = new PrintWriter(
        new File(fileName).getAbsoluteFile());
      try {
        out.print(text);
      } finally {
        out.close();
      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  // Read a file, split by any regular expression:
  public TextFile(String fileName, String splitter) {
    super(Arrays.asList(read(fileName).split(splitter)));
    // Regular expression split() often leaves an empty
    // String at the first position:
    if(get(0).equals("")) remove(0);
  }
  // Normally read by lines:
  public TextFile(String fileName) {
    this(fileName, "\n");
  }
  public void write(String fileName) {
    try {
      PrintWriter out = new PrintWriter(
        new File(fileName).getAbsoluteFile());
      try {
        for(String item : this)
          out.println(item);
      } finally {
        out.close();
      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  // Simple test:
  public static void main(String[] args) {
    String file = read("TextFile.java");
    write("test.txt", file);
    TextFile text = new TextFile("test.txt");
    text.write("test2.txt");
    // Break into unique sorted list of words:
    TreeSet<String> words = new TreeSet<String>(
      new TextFile("TextFile.java", "\\W+"));
    // Display the capitalized words:
    System.out.println(words.headSet("a"));
  }
} /* Output:
[0, ArrayList, Arrays, Break, BufferedReader, BufferedWriter, Clean, Display, File, FileReader, FileWriter, IOException, Normally, Output, PrintWriter, Read, Regular, RuntimeException, Simple, Static, String, StringBuilder, System, TextFile, Tools, TreeSet, W, Write]
*///:~
/**
* @Description: readO将每行添加到StringBuffer，并且为每行加上换行符，因为在读的过程中换行符会被去除掉。接着返回一个包含整个文件的字符串。writeO打开文本并将其写入文件。在这两个方法完成时，都要记着用close（O关闭文件。
 *
 * 注意，在任何打开文件的代码在finally子句中，作为防卫措施都添加了对文件的close（调用，以保证文件将会被正确关闭。
 *
 * 这个构造器利用readO）方法将文件转换成字符串，接着使用String.split（O以换行符为界把结果划分成行 （若要频繁使用这个类，我们可以重写此构造器以提高性能）。
 * 遗憾的是没有相应的连接（join）方法，所以那个非静态的write（O方法必须一行一行地输出这些行。因为这个类希望将读取和写入文件的过程简单化，因此所有的IOException都被转型为RuntimeException，
 * 因此用户不必使用try-catch语句块。但是，你可能需要创建另一种版本将IOException传递给调用者。
 *
 * 在mainO方法中，通过执行一个基本测试来确保这些方法正常工作。尽管这个程序不需要创建许多代码，但使用它会节约大量时间，它会使你变得很轻松，在本章后面一些例子中就可以感受到这一点。
 *
 * 另一种解决读取文件问题的方法是使用在Java SE5中引入的java.util.Scanner类。但是，这只能用于读取文件，而不能用于写入文件，并且这个工具（你会注意到它不在java.io包中）主要是设计用来创建编程语言的扫描器或"小语言"的。
*/



















