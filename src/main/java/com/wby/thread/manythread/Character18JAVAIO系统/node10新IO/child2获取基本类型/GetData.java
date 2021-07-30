package com.wby.thread.manythread.Character18JAVAIO系统.node10新IO.child2获取基本类型;//: io/GetData.java
// Getting different representations from a ByteBuffer

import java.nio.ByteBuffer;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;
/**
 * 尽管ByteBuffer只能保存字节类型的数据，但是它具有可以从其所容纳的字节中产生出各种不同基本类型值的方法。下面这个例子展示了怎样使用这些方法来插入和抽取各种数值;
 */
public class GetData {
  private static final int BSIZE = 1024;
  public static void main(String[] args) {
    ByteBuffer bb = ByteBuffer.allocate(BSIZE);
    // Allocation automatically zeroes the ByteBuffer:
    int i = 0;
    while(i++ < bb.limit())
      if(bb.get() != 0)
        print("nonzero");
    print("i = " + i);
    bb.rewind();
    // Store and read a char array:
    bb.asCharBuffer().put("Howdy!");
    char c;
    while((c = bb.getChar()) != 0)
      printnb(c + " ");
    print();
    bb.rewind();
    // Store and read a short:
    bb.asShortBuffer().put((short)471142);
    print(bb.getShort());
    bb.rewind();
    // Store and read an int:
    bb.asIntBuffer().put(99471142);
    print(bb.getInt());
    bb.rewind();
    // Store and read a long:
    bb.asLongBuffer().put(99471142);
    print(bb.getLong());
    bb.rewind();
    // Store and read a float:
    bb.asFloatBuffer().put(99471142);
    print(bb.getFloat());
    bb.rewind();
    // Store and read a double:
    bb.asDoubleBuffer().put(99471142);
    print(bb.getDouble());
    bb.rewind();
  }
} /* Output:
i = 1025
H o w d y !
12390
99471142
99471142
9.9471144E7
9.9471142E7
*///:~
/**
* 在分配一个ByteBuffer之后，可以通过检测它的值来查看缓冲器的分配方式是否将其内容自动置零——它确实是这样做了。这里一共检测了1024个值（由缓冲器的limitO决定），并且所有的值都是零。
 *
 * 向ByteBuffer插入基本类型数据的最简单的方法是;利用asCharBufferO、asShortBuffer（）等获得该缓冲器上的视图，然后使用视图的putO）方法。我们会发现此方法适用于所有基本数据类型。
 * 仅有一个小小的例外，即，使用ShortBuffer的putO方法时，需要进行类型转换（注意类型转换会截取或改变结果）。而其他所有的视图缓冲器在使用put（）方法时，不需要进行类型转换。
*/
