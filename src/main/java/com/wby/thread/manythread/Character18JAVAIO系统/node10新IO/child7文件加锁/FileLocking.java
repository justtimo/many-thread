package com.wby.thread.manythread.Character18JAVAIO系统.node10新IO.child7文件加锁;//: io/FileLocking.java

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * JDK1.4引入了文件加锁机制，它允许我们同步访问某个作为共享资源的文件。不过，竞争同一文件的两个线程可能在不同的Java虚拟机上;或者一个是Java线程，另一个是操作系统中其他的某个本地线程。
 * 文件锁对其他的操作系统进程是可见的，因为Java的文件加锁直接映射到了本地操作系统的加锁工具。
 *
 * 下面是一个关于文件加锁的简单例子。
 */
public class FileLocking {
  public static void main(String[] args) throws Exception {
    FileOutputStream fos= new FileOutputStream("file.txt");
    FileLock fl = fos.getChannel().tryLock();
    if(fl != null) {
      System.out.println("Locked File");
      TimeUnit.MILLISECONDS.sleep(100);
      fl.release();
      System.out.println("Released Lock");
    }
    fos.close();
  }
} /* Output:
Locked File
Released Lock
*///:~
/**
* 通过对FileChannel调用tryLockO或lockO，就可以获得整个文件的FileLock。（Socket- Channel、DatagramChannel和ServerSocketChannel不需要加锁，
 * 因为它们是从单进程实体继承而来;我们通常不在两个进程之间共享网络socket。）tryLockO是非阻塞式的，它设法获取锁，但是如果不能获得（当其他一些进程已经持有相同的锁，并且不共享时），
 * 它将直接从方法调用返回。lockO则是阻塞式的，它要阻塞进程直至锁可以获得，或调用lockO）的线程中断，或调用 lockO的通道关闭。使用FileLock.release（可以释放锁。
 *
 * 也可以使用如下方法对文件的一部分上锁;
 *      tryLock(1ong posit1on,long size,boolean shared)
 *          或者
 *      lock(long posit1on,long size, boolean shared)
 * 其中，加锁的区域由size-position决定。第三个参数指定是否是共享锁。
 *
 * 尽管无参数的加锁方法将根据文件尺寸的变化而变化，但是具有固定尺寸的锁不随文件尺寸的变化而变化。如果你获得了某一区域（从position到position+size）上的锁，当文件增大超出position+size时，
 * 那么在position+size之外的部分不会被锁定。无参数的加锁方法会对整个文件进行加锁，甚至文件变大后也是如此。
 *
 * 对独占锁或者共享锁的支持必须由底层的操作系统提供。如果操作系统不支持共享锁并为每一个请求都创建一个锁，那么它就会使用独占锁。
 * 锁的类型（共享或独占）可以通过 FileLock.isShared（进行查询。
*/

/**
* 对映射文件的部分加锁
 * 如前所述，文件映射通常应用于极大的文件。我们可能需要对这种巨大的文件进行部分加锁，以便其他进程可以修改文件中未被加锁的部分。例如，数据库就是这样，因此多个用户可以同时访问到它。
 * 下面例子中有两个线程，分别加锁文件的不同部分。
*/
class LockingMappedFiles {
  static final int LENGTH = 0x8FFFFFF; // 128 MB
  static FileChannel fc;
  public static void main(String[] args) throws Exception {
    fc =
            new RandomAccessFile("test.dat", "rw").getChannel();
    MappedByteBuffer out =
            fc.map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);
    for(int i = 0; i < LENGTH; i++)
      out.put((byte)'x');
    new LockAndModify(out, 0, 0 + LENGTH/3);
    new LockAndModify(out, LENGTH/2, LENGTH/2 + LENGTH/4);
  }
  private static class LockAndModify extends Thread {
    private ByteBuffer buff;
    private int start, end;
    LockAndModify(ByteBuffer mbb, int start, int end) {
      this.start = start;
      this.end = end;
      mbb.limit(end);
      mbb.position(start);
      buff = mbb.slice();
      start();
    }
    public void run() {
      try {
        // Exclusive lock with no overlap:
        FileLock fl = fc.lock(start, end, false);
        System.out.println("Locked: "+ start +" to "+ end);
        // Perform modification:
        while(buff.position() < buff.limit() - 1)
          buff.put((byte)(buff.get() + 1));
        fl.release();
        System.out.println("Released: "+start+" to "+ end);
      } catch(IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
} ///:~
/**
*  线程类LockAndModify 创建了缓冲区和用于修改的slice（），然后在runO中，获得文件通道上的锁（我们不能获得缓冲器上的锁，只能是通道上的）。lockO调用类似于获得一个对象的线程锁——我们现在处在"临界区"，即对该部分的文件具有独占访问权9。
 * 如果有Java虚拟机，它会自动释放锁，或者关闭加锁的通道。不过我们也可以像程序中那样，显式地为FileLock对象调用release（）来释放锁
*/
