package com.wby.thread.manythread.Character18JAVAIO系统.node6IO流的典型使用方式.child5存储和恢复数据;//: io/StoringAndRecoveringData.java
import java.io.*;
/**
 * @Description:  PrintWriter可以对数据进行格式化，以便人们的阅读。但是为了输出可供另一个"流"恢复的数据，我们需要用DataOutputStream写入数据，并用DataInputStream恢复数据。
 * 当然，这些流可以是任何形式，但在下面的示例中使用的是一个文件，并且对于读和写都进行了缓冲处理。
 *
 * 注意DataOutputStream和DataInputStream是面向字节的，因此要使用InputStream和 OutputStream。
 */
public class StoringAndRecoveringData {
  public static void main(String[] args)
  throws IOException {
    DataOutputStream out = new DataOutputStream(
      new BufferedOutputStream(
        new FileOutputStream("Data.txt")));
    out.writeDouble(3.14159);
    out.writeUTF("That was pi");
    out.writeDouble(1.41413);
    out.writeUTF("Square root of 2");
    out.close();
    DataInputStream in = new DataInputStream(
      new BufferedInputStream(
        new FileInputStream("Data.txt")));
    System.out.println(in.readDouble());
    // Only readUTF() will recover the
    // Java-UTF String properly:
    System.out.println(in.readUTF());
    System.out.println(in.readDouble());
    System.out.println(in.readUTF());
  }
} /* Output:
3.14159
That was pi
1.41413
Square root of 2
*///:~
/**
* @Description: 如果我们使用DataOutputStream写入数据，Java保证我们可以使用DataInputStream准确地读取数据——无论读和写数据的平台多么不同。
 * 这一点很有价值，因为我们都知道，人们曾经花费了大量时间去处理特定干平台的数据问题。只要两个平台上都有Java，这种问题就不会再发生·。
 *
 * 当我们使用DataOutputStream时。写字符串并目让DatalnputStream能够恢复它的唯一可靠的做法就是使用UTF-8编码，在这个示例中是用writeUTFO）和readUTFO）来实现的。
 * UTF-8是—种多字节格式，其编码长度根据实际使用的字符集会有所变化。如果我们使用的只是ASCII或者几乎都是ASCII字符（只占7位），那么就显得极其浪费空间和带宽，
 * 所以UTF-8将ASCII字符编码成单一字节的形式，而非ASCII 字符则编码成两到三个字节的形式。另外，字符串的长度存储在UTF-8字符串的前两个字节中。
 *
 * 但是，writeUTFO） 和readUTF（）使用的是适合于Java的 UTF-8变体（JDK文档中有这些方法的详尽描述），因此如果我们用一个非Java程序读取用 writeUTFO所写的字符串时，必须编写一些特殊代码才能正确读取字符串。
 *
 * 有了writeUTFO和 readUTFO，我们就可以用DataOutputStream把字符串和其他数据类型相混合，我们知道字符串完全可以作为Unicode来存储，并且可以很容易地使用DataInput- Stream来恢复它。
 *
 * writeDouble（）将double类型的数字存储到流中，并用相应的readDoubleO恢复它（对于其他的数据类型，也有类似方法用于读写）。但是为了保证所有的读方法都能够正常工作，
 * 我们必须知道流中数据项所在的确切位置，因为极有可能将保存的double数据作为一个简单的字节序列、 char或其他类型读入。
 * 因此，我们必须∶要么为文件中的数据采用固定的格式;
 * 要么将额外的信息保存到文件中，以便能够对其进行解析以确定数据的存放位置。
 * 注意，对象序列化和XML（本章稍后都会介绍）可能是更容易的存储和读取复杂数据结构的方式。
*/
