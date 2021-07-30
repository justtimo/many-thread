package com.wby.thread.manythread.Character18JAVAIO系统.node6IO流的典型使用方式.child2从内存输入;//: io/MemoryInput.java

import com.wby.thread.manythread.Character18JAVAIO系统.node6IO流的典型使用方式.child1缓冲输入文件.BufferedInputFile;

import java.io.IOException;
import java.io.StringReader;

/**
* @Description: 在下面的示例中，从BufferedInputFile.readO读入的String结果被用来创建一个String- Reader。然后调用read0每次读取一个字符，并把它发送到控制台。
*/
public class MemoryInput {
  public static void main(String[] args)
  throws IOException {
    StringReader in = new StringReader(
      BufferedInputFile.read("MemoryInput.java"));
    int c;
    while((c = in.read()) != -1)
      System.out.print((char)c);
  }
} /* (Execute to see output) *///:~
/**
* @Description: 注意readO是以int形式返回下一字节，因此必须类型转换为char才能正确打印。
*/
