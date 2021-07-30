package com.wby.thread.manythread.Character18JAVAIO系统.node6IO流的典型使用方式.child3格式化的内存输入;//: io/FormattedMemoryInput.java

import com.wby.thread.manythread.Character18JAVAIO系统.node6IO流的典型使用方式.child1缓冲输入文件.BufferedInputFile;

import java.io.*;

/**
* @Description:  要读取格式化数据，可以使用DataInputStream，它是一个面向字节的I/O类（不是面向字符的）。因此我们必须使用InputStream类而不是Reader类。
 * 当然，我们可以用InputStream以字节的形式读取任何数据（例如一个文件），不过，在这里使用的是字符串。
*/
public class FormattedMemoryInput {
  public static void main(String[] args)
  throws IOException {
    try {
      DataInputStream in = new DataInputStream(
        new ByteArrayInputStream(
         BufferedInputFile.read(
          "FormattedMemoryInput.java").getBytes()));
      while(true)
        System.out.print((char)in.readByte());
    } catch(EOFException e) {
      System.err.println("End of stream");
    }
  }
} /* (Execute to see output) *///:~
/**
* @Description: 必须为ByteArrayInputStream提供字节数组，为了产生该数组String包含了一个可以实现此项工作的getBytes（O方法。所产生的ByteArrayInputStrem是一个适合传递给DatalnputStream的InputStream。
 *
 * 如果我们从DatalnputStream用readByte（O一次一个字节地读取字符，那么任何字节的值都是合法的结果，因此返回值不能用来检测输入是否结束。相反，我们可以使用available（O方法查看还有多少可供存取的字符。
 *
 * 下面这个例子演示了怎样一次一个字节地读取文件;
*/
class TestEOF {
  public static void main(String[] args)
          throws IOException {
    DataInputStream in = new DataInputStream(
            new BufferedInputStream(
                    new FileInputStream("TestEOF.java")));
    while(in.available() != 0)
      System.out.print((char)in.readByte());
  }
} /* (Execute to see output) *///:~
/**
* @Description: 注意，availableO的工作方式会随着所读取的媒介类型的不同而有所不同，字面意思就是"在没有阻塞的情况下所能读取的字节数"。对于文件，这意味着整个文件;但是对于不同类型的流，可能就不是这样的，因此要谨慎使用。
 *
 * 我们也可以通过捕获异常来检测输入的末尾。但是，使用异常进行流控制。被认为是对异常特性的错误使用。
*/
