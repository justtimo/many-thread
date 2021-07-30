package com.wby.thread.manythread.Character18JAVAIO系统.node10新IO.child3视图缓冲器;//: io/IntBufferDemo.java
// Manipulating ints in a ByteBuffer with an IntBuffer

import java.nio.*;
import java.util.Arrays;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 * 视图缓冲器（view buffer）可以让我们通过某个特定的基本数据类型的视窗查看其底层的 ByteBuffer。ByteBuffer依然是实际存储数据的地方，"支持"着前面的视图，
 * 因此，对视图的任何修改都会映射成为对ByteBuffer中数据的修改。正如我们在上一示例看到的那样，这使我们可以很方便地向ByteBuffer插入数据。
 *
 * 视图还允许我们从ByteBuffer一次一个地（与ByteBuffer所支持的方式相同）或者成批地（放入数组中）读取基本类型值。在下面这个例子中，通过 IntBuffer操纵ByteBuffer中的int型数据∶
 */
public class IntBufferDemo {
  private static final int BSIZE = 1024;
  public static void main(String[] args) {
    ByteBuffer bb = ByteBuffer.allocate(BSIZE);
    IntBuffer ib = bb.asIntBuffer();
    // Store an array of int:
    ib.put(new int[]{ 11, 42, 47, 99, 143, 811, 1016 });
    // Absolute location read and write:
    System.out.println(ib.get(3));
    ib.put(3, 1811);
    // Setting a new limit before rewinding the buffer.
    ib.flip();
    while(ib.hasRemaining()) {
      int i = ib.get();
      System.out.println(i);
    }
  }
} /* Output:
99
11
42
47
1811
143
811
1016
*///:~
/**
* 先用重载后的put（）方法存储一个整数数组。接着get（）和put（）方法调用直接访问底层 ByteBuffer中的某个整数位置。注意，这些通过直接与ByteBuffer对话访问绝对位置的方式也同样适用于基本类型。
 *
 * 一旦底层的ByteBuffer通过视图缓冲器填满了整数或其他基本类型时，就可以直接被写到通道中了。正像从通道中读取那样容易，然后使用视图缓冲器可以把任何数据都转化成某一特定的基本类型。
 *
 * 在下面的例子中，通过在同一个BvteBuffer上建立不同的视图缓冲器，将同一字节序列翻译成了short、int、float、long和double类型的数据。
*/
class ViewBuffers {
  public static void main(String[] args) {
    ByteBuffer bb = ByteBuffer.wrap(
            new byte[]{ 0, 0, 0, 0, 0, 0, 0, 'a' });
    bb.rewind();
    printnb("Byte Buffer ");
    while(bb.hasRemaining())
      printnb(bb.position()+ " -> " + bb.get() + ", ");
    print();
    CharBuffer cb =
            ((ByteBuffer)bb.rewind()).asCharBuffer();
    printnb("Char Buffer ");
    while(cb.hasRemaining())
      printnb(cb.position() + " -> " + cb.get() + ", ");
    print();
    FloatBuffer fb =
            ((ByteBuffer)bb.rewind()).asFloatBuffer();
    printnb("Float Buffer ");
    while(fb.hasRemaining())
      printnb(fb.position()+ " -> " + fb.get() + ", ");
    print();
    IntBuffer ib =
            ((ByteBuffer)bb.rewind()).asIntBuffer();
    printnb("Int Buffer ");
    while(ib.hasRemaining())
      printnb(ib.position()+ " -> " + ib.get() + ", ");
    print();
    LongBuffer lb =
            ((ByteBuffer)bb.rewind()).asLongBuffer();
    printnb("Long Buffer ");
    while(lb.hasRemaining())
      printnb(lb.position()+ " -> " + lb.get() + ", ");
    print();
    ShortBuffer sb =
            ((ByteBuffer)bb.rewind()).asShortBuffer();
    printnb("Short Buffer ");
    while(sb.hasRemaining())
      printnb(sb.position()+ " -> " + sb.get() + ", ");
    print();
    DoubleBuffer db =
            ((ByteBuffer)bb.rewind()).asDoubleBuffer();
    printnb("Double Buffer ");
    while(db.hasRemaining())
      printnb(db.position()+ " -> " + db.get() + ", ");
  }
} /* Output:
Byte Buffer 0 -> 0, 1 -> 0, 2 -> 0, 3 -> 0, 4 -> 0, 5 -> 0, 6 -> 0, 7 -> 97,
Char Buffer 0 ->  , 1 ->  , 2 ->  , 3 -> a,
Float Buffer 0 -> 0.0, 1 -> 1.36E-43,
Int Buffer 0 -> 0, 1 -> 97,
Long Buffer 0 -> 97,
Short Buffer 0 -> 0, 1 -> 0, 2 -> 0, 3 -> 97,
Double Buffer 0 -> 4.8E-322,
*///:~
/**
*  ByteBuffer通过一个被"包装"过的8字节数组产生，然后通过各种不同的基本类型的视图
 * 缓冲器显示了出来。我们可以在下图中看到，当从不同类型的缓冲器读取时，数据显示的方式 957也不同。这与上面程序的输出相对应。
 *
 *  |0 | 0 |0 | 0 | 0 | 0 | 0 | 97|    bytes
 *  |------|------|-------|-------|
 *  |      |      |       |    a  |   chars
 *  |------|------|-------|-------|
 *  |   0 |   0   |    0   |    97|   shorts
 * |------|------|-------|-------|
 *  |      0      |      97       |   ints
 * |------|------|-------|-------|
 * |      0.0    |       1.36E-43|    floats
 * |------|------|-------|-------|
 * |      97                     |    longs
 * |------|------|-------|-------|
 * |      4.8E-322               |    doubles
 * |------|------|-------|-------|
*/


/**
* 字节存放次序
 * 不同的机器可能会使用不同的字节排序方法来存储数据。"big endian"（高位优先）将最重要的字节存放在地址最低的存储器单元。而"itle endian"（低位优先）则是将最重要的字节放在地址最高的存储器单元。当存储量大于一个字节时，像int、float等，就要考虑字节的顺序问题了。ByteBuffer是以高位优先的形式存储数据的，并且数据在网上传送时也常常使用高位优先的形式。我们可以使用带有参数ByteOrder.BIG_ENDIAN 或ByteOrder.LITTLE_ENDIAN的 order（O方法改变ByteBuffer的字节排序方式。
 * 考虑包含下面两个字节的ByteBuffer∶
 * 0 0 0 0 0 0 0 0| 0 1 1 0 0 0 0 0 1
 * ---------------  -----------------
 *      b1                b2
 *
 * 如果我们以short （ByteBuffer.asShortBufferO）形式读取数据，得到的数字是97（二进制形式为 00000000 01100001）;但是如果将ByteBuffer更改成低位优先形式，仍以short形式读取数据，
 * 得到的数字却是24832（二进制形式为01100001 00000000）。
 *
 * 这个例子展示了怎样通过字节存放模式设置来改变字符中的字节次序∶
*/
class Endians {
  public static void main(String[] args) {
    ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
    bb.asCharBuffer().put("abcdef");
    print(Arrays.toString(bb.array()));
    bb.rewind();
    bb.order(ByteOrder.BIG_ENDIAN);
    bb.asCharBuffer().put("abcdef");
    print(Arrays.toString(bb.array()));
    bb.rewind();
    bb.order(ByteOrder.LITTLE_ENDIAN);
    bb.asCharBuffer().put("abcdef");
    print(Arrays.toString(bb.array()));
  }
} /* Output:
[0, 97, 0, 98, 0, 99, 0, 100, 0, 101, 0, 102]
[0, 97, 0, 98, 0, 99, 0, 100, 0, 101, 0, 102]
[97, 0, 98, 0, 99, 0, 100, 0, 101, 0, 102, 0]
*///:~
/**
* @Description: ByteBuffer有足够的空间，以存储作为外部缓冲器的charArray中的所有字节，因此可以调用arrayO方法显示视图底层的字节。array（）方法是"可选的"，并且我们只能对由数组支持的缓冲器调用此方法;
 * 否则，将会抛出UnsupportedOperationException。
 *
 * 通过CharBuffer视图可以将charArray插入到ByteBuffer中。在底层的字节被显示时，我们会发现默认次序和随后的高位优先次序相同，然而低位优先次序则与之相反。后者交换了这些字节次序。
*/



















