package com.wby.thread.manythread.Character18JAVAIO系统.node10新IO;//: io/GetChannel.java
// Getting channels from streams

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
* @Description: JDK 1.4的javanio.*包中引入了新的JavaI/O类库，其目的在于提高速度。实际上，旧的I/O包已经使用nio重新实现过，以便充分利用这种速度提高，因此，即使我们不显式地用nio编写代码，也能从中受益。
 * 速度的提高在文件I/O和网络I/O中都有可能发生，我们在这里只研究前者9;对于后者，在《Thinking in Enterprise Java》中有论述。
 *
 * 速度的提高来自于所使用的结构更接近于操作系统执行I/O的方式; 通道和缓冲 器。我们可以把它想像成一个煤矿，通道是一个包含煤层（数据）的矿藏，而缓冲器则是派送到矿藏的卡车。
 * 卡车载满煤炭而归，我们再从卡车上获得煤炭。也就是说，我们并没有直接和通道交互，我们只是和缓冲器交互，并把缓冲器派送到通道。通道要么从缓冲器获得数据，要么向缓冲器发送数据。
 *
 * 唯一直接与通道交互的缓冲器是ByteBuffer——也就是说，可以存储未加工字节的缓冲器。当我们查询JDK文档中的java.nio.ByteBuffer时，会发现它是相当基础的类;
 * 通过告知分配多少存储空间来创建一个ByteBuffer对象，并且还有一个方法选择集，用于以原始的字节形式或基本数据类型输出和读取数据。
 * 但是，没办法输出或读取对象，即使是字符串对象也不行。这种处理虽然很低级，但却正好，因为这是大多数操作系统中更有效的映射方式。
 *
 * 旧I/O类库中有三个类被修改了，用以产生FileChannel。这三个被修改的类是 FileInputStream、FileOutputStream以及用于既读又写的RandomAccessFile。
 * 注意这些是字节操纵流，与低层的nio性质一致。Reader和Writer这种字符模式类不能用于产生通道;
 * 但是 java.nio.channels.Channels类提供了实用方法，用以在通道中产生Reader 和Writer。
 *
 * 下面的简单实例演示了上面三种类型的流，用以产生可写的、可读可写的及可读的通道。
*/
public class GetChannel {
  private static final int BSIZE = 1024;
  public static void main(String[] args) throws Exception {
    // Write a file:
    FileChannel fc =
      new FileOutputStream("data.txt").getChannel();
    fc.write(ByteBuffer.wrap("Some text ".getBytes()));
    fc.close();
    // Add to the end of the file:
    fc =
      new RandomAccessFile("data.txt", "rw").getChannel();
    fc.position(fc.size()); // Move to the end
    fc.write(ByteBuffer.wrap("Some more".getBytes()));
    fc.close();
    // Read the file:
    fc = new FileInputStream("data.txt").getChannel();
    ByteBuffer buff = ByteBuffer.allocate(BSIZE);
    fc.read(buff);
    buff.flip();
    while(buff.hasRemaining())
      System.out.print((char)buff.get());
  }
} /* Output:
Some text Some more
*///:~
/**
* @Description: 对于这里所展示的任何流类，getChannelO将会产生一个FileChannel。通道是一种相当基础的东西;可以向它传送用于读写的ByteBuffer，并且可以锁定文件的某些区域用于独占式访问（稍后讲述）。
 *
 * 将字节存放于ByteBuffer的方法之一是;使用一种"put"方法直接对它们进行填充，填入一个或多个字节，或基本数据类型的值。不过，正如所见，也可以使用warpO方法将已存在的字节数组"包装"到ByteBuffer中。
 * 一旦如此，就不再复制底层的数组，而是把它作为所产生的 ByteBuffer的存储器，我们称之为数组支持的ByteBuffer。
 *
 * data.txt文件用RandomAccessFile被再次打开。注意我们可以在文件内随处移动FileChannel;在这里，我们把它移到最后，以便附加其他的写操作。
 *
 * 对于只读访问，我们必须显式地使用静态的allocate（）方法来分配ByteBuffer。nio的目标就是快速移动大量数据，因此ByteBuffer的大小就显得尤为重要———
 * 实际上，这里使用的1K可能比我们通常要使用的小一点（必须通过实际运行应用程序来找到最佳尺寸）。
 *
 * 甚至达到更高的速度也有可能，方法就是使用allocateDirect（）而不是allocateO，以产生一个与操作系统有更高耦合性的"直接"缓冲器。但是，这种分配的开支会更大，
 * 并且具体实现也随操作系统的不同而不同，因此必须再次实际运行应用程序来查看直接缓冲是否可以使我们获得速度上的优势。
 *
 * 一旦调用readO来告知FileChannel向ByteBuffer存储字节，就必须调用缓冲器上的flip（，让它做好让别人读取字节的准备（是的，这似乎有一点拙劣，但是请记住，它是很拙劣的，但却适用于获取最大速度）。
 * 如果我们打算使用缓冲器执行进一步的readO操作，我们也必须得调用clearO来为每个readO做好准备。这在下面这个简单文件复制程序中可以看到
*/
class ChannelCopy {
  private static final int BSIZE = 1024;
  public static void main(String[] args) throws Exception {
    if(args.length != 2) {
      System.out.println("arguments: sourcefile destfile");
      System.exit(1);
    }
    FileChannel
            in = new FileInputStream(args[0]).getChannel(),
            out = new FileOutputStream(args[1]).getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
    while(in.read(buffer) != -1) {
      buffer.flip(); // Prepare for writing
      out.write(buffer);
      buffer.clear();  // Prepare for reading
    }
  }
} ///:~
/**
* @Description: 可以看到，打开一个FileChannel以用于读，而打开另一个以用于写。ByteBuffer被分配了空间，当FileChannel.readO返回-1时（一个分界符，毋庸置疑，它源于Unix和C），表示我们已经到达了输入的末尾。
 * 每次readO操作之后，就会将数据输入到缓冲器中，flipO则是准备缓冲器以便它的信息可以由writeO提取。writeO操作之后，信息仍在缓冲器中，接着clearO操作则对所有的内部指针重新安排，
 * 以便缓冲器在另一个readO）操作期间能够做好接受数据的准备。
 *
 * 然而，上面那个程序并不是处理此类操作的理想方式。特殊方法transferToO和transferFromO则允许我们将一个通道和另一个通道直接相连∶
*/
class TransferTo {
  public static void main(String[] args) throws Exception {
    if(args.length != 2) {
      System.out.println("arguments: sourcefile destfile");
      System.exit(1);
    }
    FileChannel
            in = new FileInputStream(args[0]).getChannel(),
            out = new FileOutputStream(args[1]).getChannel();
    in.transferTo(0, in.size(), out);
    // Or:
    // out.transferFrom(in, 0, in.size());
  }
} ///:~
/**
* @Description:  虽然我们并不是经常做这类事情，但是了解这一点还是有好处的。
*/
















