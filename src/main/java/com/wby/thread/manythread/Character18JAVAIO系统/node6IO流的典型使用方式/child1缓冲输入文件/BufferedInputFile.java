package com.wby.thread.manythread.Character18JAVAIO系统.node6IO流的典型使用方式.child1缓冲输入文件;//: io/BufferedInputFile.java

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 15:00
 * @Description: 如果想要打开—个文件用干字符输入。可以L使用以LString或下ie对象作为文件名的 FileInputReader。为了提高速度，我们希望对那个文件进行缓冲，
 * 那么我们将所产生的引用传给一个BufferedReader构造器。由于BufferedReader也提供readLine（方法，所以这是我们的最终对象和进行读取的接口。
 * 当readLineO将返回null时，你就达到了文件的末尾。
 */
public class BufferedInputFile {
  // Throw exceptions to console:
  public static String
  read(String filename) throws IOException {
    // Reading input by lines:
    BufferedReader in = new BufferedReader(
      new FileReader(filename));
    String s;
    StringBuilder sb = new StringBuilder();
    while((s = in.readLine())!= null)
      sb.append(s + "\n");
    in.close();
    return sb.toString();
  }
  public static void main(String[] args)
  throws IOException {
    System.out.print(read("BufferedInputFile.java"));
  }
} /* (Execute to see output) *///:~
/**
* @Description: 字符串sb用来累积文件的全部内容（包括必须添加的换行符，因为readLineO已将它们删掉）。最后，调用close（）关闭文件。
*/
