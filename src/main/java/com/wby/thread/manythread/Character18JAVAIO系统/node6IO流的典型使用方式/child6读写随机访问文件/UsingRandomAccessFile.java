package com.wby.thread.manythread.Character18JAVAIO系统.node6IO流的典型使用方式.child6读写随机访问文件;//: io/UsingRandomAccessFile.java

import java.io.IOException;
import java.io.RandomAccessFile;
/**
* @Description: 使用RandomAccessFile，类似于组合使用了DataInputStream和DataOutputStream （因为它实现了相同的接口∶ Datalnput和DataOutput）。
 * 另外我们可以看到，利用seek（可以在文件中到处移动，并修改文件中的某个值。
 *
 * 在使用RandomAccessFile时，你必须知道文件排版，这样才能正确地操作它。Random- AccessFile拥有读取基本类型和UTF-8字符串的各种具体方法。
 *
 * 下面是示例∶
*/
public class UsingRandomAccessFile {
  static String file = "rtest.dat";
  static void display() throws IOException {
    RandomAccessFile rf = new RandomAccessFile(file, "r");
    for(int i = 0; i < 7; i++)
      System.out.println(
        "Value " + i + ": " + rf.readDouble());
    System.out.println(rf.readUTF());
    rf.close();
  }
  public static void main(String[] args)
  throws IOException {
    RandomAccessFile rf = new RandomAccessFile(file, "rw");
    for(int i = 0; i < 7; i++)
      rf.writeDouble(i*1.414);
    rf.writeUTF("The end of the file");
    rf.close();
    display();
    rf = new RandomAccessFile(file, "rw");
    rf.seek(5*8);
    rf.writeDouble(47.0001);
    rf.close();
    display();
  }
} /* Output:
Value 0: 0.0
Value 1: 1.414
Value 2: 2.828
Value 3: 4.242
Value 4: 5.656
Value 5: 7.069999999999999
Value 6: 8.484
The end of the file
Value 0: 0.0
Value 1: 1.414
Value 2: 2.828
Value 3: 4.242
Value 4: 5.656
Value 5: 47.0001
Value 6: 8.484
The end of the file
*///:~
/**
* @Description: display（方法打开了一个文件，并以double值的形式显示了其中的七个元素。在mainO中，首先创建了文件，然后打开并修改了它。因为double总是8字节长，
 * 所以为了用seekO查找第5个双精度值，你只需用5*8来产生查找位置。
 *
 * 正如先前所指，RandomAccessFile除了实现Datalnput和DataOutput接口之外，有效地与 I/O继承层次结构的其他部分实现了分离。因为它不支持装饰，所以不能将其与InputStream及
 * OutputStream子类的任何部分组合起来。我们必须假定RandomAccessFile已经被正确缓冲，因为我们不能为它添加这样的功能。
 *
 * 可以自行选择的是第二个构造器参数;我们可指定以"只读"（r）方式或"读写"（rw）方式打开一个RandomAccessFile文件。
 *
 * 你可能会考虑使用"内存映射文件"来代替RandomAccessFile。
*/
