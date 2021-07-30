package com.wby.thread.manythread.Character18JAVAIO系统.node10新IO.child1转换数据;//: io/BufferToText.java
// Converting text to and from ByteBuffers

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 *回过头看GetChannel.java这个程序就会发现，为了输出文件中的信息，我们必须每次只读取一个字节的数据，然后将每个byte类型强制转换成char类型。这种方法似乎有点原始——
 * 如果我们查看一下java.nio.CharBuffer这个类，将会发现它有一个toStringO方法是这样定义的∶"返回一个包含缓冲器中所有字符的字符串。"
 *
 * 既然ByteBuffer可以看作是具有asCharBuffer（）方法的 CharBuffer，那么为什么不用它呢? 正如下面的输出语句中第一行所见，这种方法并不能解决问题∶
 */
public class BufferToText {
  private static final int BSIZE = 1024;
  public static void main(String[] args) throws Exception {
    FileChannel fc =
      new FileOutputStream("data2.txt").getChannel();
    fc.write(ByteBuffer.wrap("Some text".getBytes()));
    fc.close();
    fc = new FileInputStream("data2.txt").getChannel();
    ByteBuffer buff = ByteBuffer.allocate(BSIZE);
    fc.read(buff);
    buff.flip();
    // Doesn't work:
    System.out.println(buff.asCharBuffer());
    // Decode using this system's default Charset:
    buff.rewind();
    String encoding = System.getProperty("file.encoding");
    System.out.println("Decoded using " + encoding + ": "
      + Charset.forName(encoding).decode(buff));
    // Or, we could encode with something that will print:
    fc = new FileOutputStream("data2.txt").getChannel();
    fc.write(ByteBuffer.wrap(
      "Some text".getBytes("UTF-16BE")));
    fc.close();
    // Now try reading again:
    fc = new FileInputStream("data2.txt").getChannel();
    buff.clear();
    fc.read(buff);
    buff.flip();
    System.out.println(buff.asCharBuffer());
    // Use a CharBuffer to write through:
    fc = new FileOutputStream("data2.txt").getChannel();
    buff = ByteBuffer.allocate(24); // More than needed
    buff.asCharBuffer().put("Some text");
    fc.write(buff);
    fc.close();
    // Read and display:
    fc = new FileInputStream("data2.txt").getChannel();
    buff.clear();
    fc.read(buff);
    buff.flip();
    System.out.println(buff.asCharBuffer());
  }
} /* Output:
????
Decoded using Cp1252: Some text
Some text
Some text
*///:~
/**
* 缓冲器容纳的是普通的字节，为了把它们转换成字符，我们要么在输入它们的时候对其进行编码（这样，它们输出时才具有意义），要么在将其从缓冲器输出时对它们进行解码。
 *
 * 可以使用java.nio.charset.Charset类实现这些功能，该类提供了把数据编码成多种不同类型的字符集的工具∶
*/
class AvailableCharSets {
  public static void main(String[] args) {
    SortedMap<String,Charset> charSets =
            Charset.availableCharsets();
    Iterator<String> it = charSets.keySet().iterator();
    while(it.hasNext()) {
      String csName = it.next();
      printnb(csName);
      Iterator aliases =
              charSets.get(csName).aliases().iterator();
      if(aliases.hasNext())
        printnb(": ");
      while(aliases.hasNext()) {
        printnb(aliases.next());
        if(aliases.hasNext())
          printnb(", ");
      }
      print();
    }
  }
} /* Output:
Big5: csBig5
Big5-HKSCS: big5-hkscs, big5hk, big5-hkscs:unicode3.0, big5hkscs, Big5_HKSCS
EUC-JP: eucjis, x-eucjp, csEUCPkdFmtjapanese, eucjp, Extended_UNIX_Code_Packed_Format_for_Japanese, x-euc-jp, euc_jp
EUC-KR: ksc5601, 5601, ksc5601_1987, ksc_5601, ksc5601-1987, euc_kr, ks_c_5601-1987, euckr, csEUCKR
GB18030: gb18030-2000
GB2312: gb2312-1980, gb2312, EUC_CN, gb2312-80, euc-cn, euccn, x-EUC-CN
GBK: windows-936, CP936
...
*///:~

/**

* 让我们返回到BufferToText.java，如果我们想对缓冲器调用rewindO方法（调用该方法是为了返回到数据开始部分），接着使用平台的默认字符集对数据进行decode（），那么作为结果的 CharBuffer可以很好地输出打EJ到控制台。
 * 可以使用Svstem.getProperty（"fle.encoding"）发现默认字符集，它会产生代表字符集名称的字符串。把该字符串传送给Charset.forName（O）用以产生 Charset对象，可以用它对字符串进行解码。
 *
 * 另一选择是在读文件时，使用能够产生可打印的输出的字符集进行encode（），正如在 BufferToText.java中第3部分所看到的那样。这里，UTF-16BE可以把文本写到文件中，
 * 当读取时，我们只需要把它转换成CharBuffer，就会产生所期望的文本。
 *
 * 最后，让我们来看看若是通过CharBuffer向ByteBuffer写入，会发生什么情况（后面将会深入了解）。注意我们为ByteBuffer分配了24个字节。既然一个字符需要2个字节，
 * 那么一个 ByteBuffer足可以容纳12个字符，但是"Some text"只有9个字符，剩余的内容为零的字节仍出现在由它的 toStringO所产生的CharBuffer的表示中，我们可以在输出中看到。
*/


















