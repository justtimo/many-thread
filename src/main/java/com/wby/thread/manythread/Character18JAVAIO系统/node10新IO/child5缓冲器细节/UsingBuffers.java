package com.wby.thread.manythread.Character18JAVAIO系统.node10新IO.child5缓冲器细节;//: io/UsingBuffers.java

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
/**
 * Buffer由数据和可以高效地访问及操纵这些数据的四个索引组成，这四个索引是∶ mark（标记），position（位置），limit（界限）和capacity（容量）。下面是用于设置和复位索引以及杏询它们的值的方法。
 *
 * capacty()          返回缓冲区容量
 * clear()            清空缓冲区，将position设置为0，limit设置为容量。我们可以调用此方法覆写缓冲区
 * flip)              将 limit设置为position，position 设置为0。此方法用于准备从缓冲区读取已经写入的数据
 * limit(             返回limit值
 * limit(int lim)     设置 limit值
 * mark               将 mark设置为position
 * postin()           返回position值
 * position（int pos） 设置 position值
 * remainingO         返回 （limit- position）
 * hasRemainingO      若有介于position 和 limit之间的元素，则返回true
 */
/**
 * 在缓冲器中插入和提取数据的方法会更新这些索引，用于反映所发生的变化。
 * 下面的示例用到一个很简单的算法（交换相邻字符），以对CharBuffer中的字符进行编码（scramble）和译码（unscramble）。
 */
public class UsingBuffers {
  private static void symmetricScramble(CharBuffer buffer){
    while(buffer.hasRemaining()) {
      buffer.mark();
      char c1 = buffer.get();
      char c2 = buffer.get();
      buffer.reset();
      buffer.put(c2).put(c1);
    }
  }
  public static void main(String[] args) {
    char[] data = "UsingBuffers".toCharArray();
    ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
    CharBuffer cb = bb.asCharBuffer();
    cb.put(data);
    print(cb.rewind());
    symmetricScramble(cb);
    print(cb.rewind());
    symmetricScramble(cb);
    print(cb.rewind());
  }
} /* Output:
UsingBuffers
sUniBgfuefsr
UsingBuffers
*///:~
/**
* 尽管可以通过对某个char数组调用wrap（）方法来直接产生一个CharBuffer，但是在本例中取而代之的是分配一个底层的ByteBuffer，产生的CharBuffer只是ByteBuffer上的一个视图而已。
 * 这里要强调的是，我们总是以操纵ByteBuffer为目标，因为它可以和通道进行交互。
 *
 * 下面是进入symmetricScramble（）方法时缓冲器的样子∶
 *                                       cap
 *  U    s  i  n  g  B  u  f  f  e  r    s
 *  pos                                  lim
 *
 *
 *  position指针指向缓冲器中的第一个元素，capacity和limit则指向最后一个元素。
 * 在程序的symmetricScramble（O）方法中，迭代执行while循环直到position等于limit。一旦调用缓冲器上相对的get（O或put（O函数，position指针就会随之相应改变。
 * 我们也可以调用绝对的、包含—个索引参数的getO和putO方法（参数指明getO）或putO的发生位置）。不过，这些方法不会改变缓冲器的position指针。
 *
 * 当操纵到while循环时，使用mark（调用来设置mark的值。此时，缓冲器状态如下∶
 *  mar                                  cap
 *  U    s  i  n  g  B  u  f  f  e  r    s
 *  pos                                  lim
 *
 *  两个相对的get（）调用把前两个字符保存到变量c1和c2中，调用完这两个方法后，缓冲器如下∶
 *  mar                                     cap
 *  U    s   i    n  g  B  u  f  f  e  r    s
 *           pos                            lim
 *
 * 为了实现交换，我们要在position=0时写入c2，position=1时写入c1。我们也可以使用绝对的putO）方法来实现，或者使用resetO把position的值设为mark的值∶
 *  mar                                     cap
 *  U    s   i    n  g  B  u  f  f  e  r    s
 *  pos                                     lim
 *
 *  这两个putO）方法先写c2，接着写c1∶
 *  mar                                     cap
 *  s    U   i    n  g  B  u  f  f  e  r    s
 *           pos                            lim
 *
 *  在下一次循环迭代期间，将mark设置成position的当前值∶
 *           mar                            cap
 *  s    U   i    n  g  B  u  f  f  e  r    s
 *           pos                            lim
 *
 *
 *  这个过程将会持续到遍历完整个缓冲器。在while循环的最后，position指向缓冲器的末尾。如果要打印缓冲器，只能打印出position和limit之间的字符。
 *  因此，如果想显示缓冲器的全部内容，必须使用rewindO把position设置到缓冲器的开始位置。下面是调用rewind（）之后缓冲器的状态（mark的值则变得不明确）;
 *
 *  当再次调用symmetricScrambleO功能时，会对CharBuffer进行同样的处理，并将其恢复到初始状态。
*/
