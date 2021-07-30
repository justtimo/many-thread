package com.wby.thread.manythread.Character18JAVAIO系统.node11压缩.child1用GZIP进行简单压缩;//: io/GZIPcompress.java
// {Args: GZIPcompress.java}

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/**
 * GZIP接口非常简单。因此如果我们只想对单个数据流（而不是一系列互异数据）进行压缩，那么它可能是比较适合的选择。下面是对单个文件进行压缩的例子∶
 */
public class GZIPcompress {
  public static void main(String[] args)
  throws IOException {
    if(args.length == 0) {
      System.out.println(
        "Usage: \nGZIPcompress file\n" +
        "\tUses GZIP compression to compress " +
        "the file to test.gz");
      System.exit(1);
    }
    BufferedReader in = new BufferedReader(
      new FileReader(args[0]));
    BufferedOutputStream out = new BufferedOutputStream(
      new GZIPOutputStream(
        new FileOutputStream("test.gz")));
    System.out.println("Writing file");
    int c;
    while((c = in.read()) != -1)
      out.write(c);
    in.close();
    out.close();
    System.out.println("Reading file");
    BufferedReader in2 = new BufferedReader(
      new InputStreamReader(new GZIPInputStream(
        new FileInputStream("test.gz"))));
    String s;
    while((s = in2.readLine()) != null)
      System.out.println(s);
  }
} /* (Execute to see output) *///:~
/**
* 压缩类的使用非常百观——首接将输出流封装成GZIPOutputStream或ZipOutputtream，并将输入流封装成GZIPInputStream或ZipInputStream即可。其他全部操作就是通常的I/O读写。
 *
 * 这个例子把面向字符的流和面向字节的流混合了起来;输入（in）用Reader类，而GZIPOutputStream的构造器只能接受OutputStream对象，不能接受Writer对象。在打开文件时，GZIPInputStream就会被转换成Reader。
*/
