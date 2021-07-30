package com.wby.thread.manythread.Character18JAVAIO系统.node10新IO.child6内存映射文件;//: io/LargeMappedFiles.java
// Creating a very large file using mapping.
// {RunByHand}

import java.io.*;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;
/**
 * 内存映射文件允许我们创建和修改那些因为太大而不能放入内存的文件。有了内存映射文件，我们就可以假定整个文件都放在内存中，而且可以完全把它当作非常大的数组来访问。
 *
 * 这种方法极大地简化了用于修改文件的代码。下面是一个小例子∶
 */
public class LargeMappedFiles {
  static int length = 0x8FFFFFF; // 128 MB
  public static void main(String[] args) throws Exception {
    MappedByteBuffer out =
      new RandomAccessFile("test.dat", "rw").getChannel()
      .map(FileChannel.MapMode.READ_WRITE, 0, length);
    for(int i = 0; i < length; i++)
      out.put((byte)'x');
    print("Finished writing");
    for(int i = length/2; i < length/2 + 6; i++)
      printnb((char)out.get(i));
  }
} ///:~
/**
* 为了既能写又能读，我们先由RandomAccessFile开始，获得该文件上的通道，然后调用 map（产生MappedByteBuffer，这是一种特殊类型的直接缓冲器。
 * 注意我们必须指定映射文件的初始位置和映射区域的长度，这意味着我们可以映射某个大文件的较小的部分。
 *
 * MappedByteBuffer由ByteBuffer继承而来，因此它具有ByteBuffer的所有方法。这里，我们仅仅展示了非常简单的putO和getO，但是我们同样可以使用像asCharBufferO等这样的用法。
 * 前面那个程序创建的文件为128MB，这可能比操作系统所允许一次载入内存的空间大。但似乎我们可以一次访问到整个文件，因为只有一部分文件放入了内存，文件的其他部分被交换了出去。
 * 用这种方式，很大的文件（可达2GB）也可以很容易地修改。注意底层操作系统的文件映射工具是用来最大化地提高性能。
*/

/**
* 性能
 * 尽管"旧"的I/O流在用nio实现后性能有所提高，但是"映射文件访问"往往可以更加显著地加快速度。下面的程序进行了简单的性能比较。
*/
class MappedIO {
  private static int numOfInts = 4000000;
  private static int numOfUbuffInts = 200000;
  private abstract static class Tester {
    private String name;
    public Tester(String name) { this.name = name; }
    public void runTest() {
      System.out.print(name + ": ");
      try {
        long start = System.nanoTime();
        test();
        double duration = System.nanoTime() - start;
        System.out.format("%.2f\n", duration/1.0e9);
      } catch(IOException e) {
        throw new RuntimeException(e);
      }
    }
    public abstract void test() throws IOException;
  }
  private static Tester[] tests = {
          new Tester("Stream Write") {
            public void test() throws IOException {
              DataOutputStream dos = new DataOutputStream(
                      new BufferedOutputStream(
                              new FileOutputStream(new File("temp.tmp"))));
              for(int i = 0; i < numOfInts; i++)
                dos.writeInt(i);
              dos.close();
            }
          },
          new Tester("Mapped Write") {
            public void test() throws IOException {
              FileChannel fc =
                      new RandomAccessFile("temp.tmp", "rw")
                              .getChannel();
              IntBuffer ib = fc.map(
                      FileChannel.MapMode.READ_WRITE, 0, fc.size())
                      .asIntBuffer();
              for(int i = 0; i < numOfInts; i++)
                ib.put(i);
              fc.close();
            }
          },
          new Tester("Stream Read") {
            public void test() throws IOException {
              DataInputStream dis = new DataInputStream(
                      new BufferedInputStream(
                              new FileInputStream("temp.tmp")));
              for(int i = 0; i < numOfInts; i++)
                dis.readInt();
              dis.close();
            }
          },
          new Tester("Mapped Read") {
            public void test() throws IOException {
              FileChannel fc = new FileInputStream(
                      new File("temp.tmp")).getChannel();
              IntBuffer ib = fc.map(
                      FileChannel.MapMode.READ_ONLY, 0, fc.size())
                      .asIntBuffer();
              while(ib.hasRemaining())
                ib.get();
              fc.close();
            }
          },
          new Tester("Stream Read/Write") {
            public void test() throws IOException {
              RandomAccessFile raf = new RandomAccessFile(
                      new File("temp.tmp"), "rw");
              raf.writeInt(1);
              for(int i = 0; i < numOfUbuffInts; i++) {
                raf.seek(raf.length() - 4);
                raf.writeInt(raf.readInt());
              }
              raf.close();
            }
          },
          new Tester("Mapped Read/Write") {
            public void test() throws IOException {
              FileChannel fc = new RandomAccessFile(
                      new File("temp.tmp"), "rw").getChannel();
              IntBuffer ib = fc.map(
                      FileChannel.MapMode.READ_WRITE, 0, fc.size())
                      .asIntBuffer();
              ib.put(0);
              for(int i = 1; i < numOfUbuffInts; i++)
                ib.put(ib.get(i - 1));
              fc.close();
            }
          }
  };
  public static void main(String[] args) {
    for(Tester test : tests)
      test.runTest();
  }
} /* Output: (90% match)
Stream Write: 0.56
Mapped Write: 0.12
Stream Read: 0.80
Mapped Read: 0.07
Stream Read/Write: 5.32
Mapped Read/Write: 0.02
*///:~
/**
* 正如在本书前面的例子中所看到的那样，runTestO被用作是一种模板方法，为在匿名内部子类中定义的testO）的各种实现创建了测试框架）。每种子类都将执行一种测试，因此test（）方法为我们进行各种I/O操作提供了原型。
 * 尽管"映射写"似乎要用到FileOutputStream，但是映射文件中的所有输出必须使用RandomAccessFile，正如前面程序代码中的读/写一样。
 *
 * 注意testO方法包括初始化各种I/O对象的时间，因此，即使建立映射文件的花费很大，但是整体受益比起I/O流来说还是很显著的。
*/
