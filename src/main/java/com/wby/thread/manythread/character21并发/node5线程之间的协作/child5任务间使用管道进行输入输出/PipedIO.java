package com.wby.thread.manythread.character21并发.node5线程之间的协作.child5任务间使用管道进行输入输出;//: concurrency/PipedIO.java
// Using pipes for inter-task I/O

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 * 通过输入/输出在线程间进行通信通常很有用。提供线程功能的类库以"管道"的形式对线程间的输入/输出提供了支持。
 * 它们在Java输入/输出类库中的对应物就是PipedWriter类（允许任务向管道写）和PipedReader类（允许不同任务从同一个管道中读取）。
 * 这个模型可以看成是"生产者-消费者"问题的变体，这里的管道就是一个封装好的解决方案。管道基本上是一个阻塞队列，存在于多个引入BlockingQueue之前的Java版本中。
 *
 * 下面是一个简单例子，两个任务使用一个管道进行通信;
 */
class Sender implements Runnable {
  private Random rand = new Random(47);
  private PipedWriter out = new PipedWriter();
  public PipedWriter getPipedWriter() { return out; }
  public void run() {
    try {
      while(true)
        for(char c = 'A'; c <= 'z'; c++) {
          out.write(c);
          TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
        }
    } catch(IOException e) {
      print(e + " Sender write exception");
    } catch(InterruptedException e) {
      print(e + " Sender sleep interrupted");
    }
  }
}

class Receiver implements Runnable {
  private PipedReader in;
  public Receiver(Sender sender) throws IOException {
    in = new PipedReader(sender.getPipedWriter());
  }
  public void run() {
    try {
      while(true) {
        // Blocks until characters are there:
        printnb("Read: " + (char)in.read() + ", ");
      }
    } catch(IOException e) {
      print(e + " Receiver read exception");
    }
  }
}

public class PipedIO {
  public static void main(String[] args) throws Exception {
    Sender sender = new Sender();
    Receiver receiver = new Receiver(sender);
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(sender);
    exec.execute(receiver);
    TimeUnit.SECONDS.sleep(4);
    exec.shutdownNow();
  }
} /* Output: (65% match)
Read: A, Read: B, Read: C, Read: D, Read: E, Read: F, Read: G, Read: H, Read: I, Read: J, Read: K, Read: L, Read: M, java.lang.InterruptedException: sleep interrupted Sender sleep interrupted
java.io.InterruptedIOException Receiver read exception
*///:~
/**
 * Sender和Receiver代表了需要互相通信两个任务。Sender创建了一个PipedWriter，它是一个单独的对象;但是对于Receiver，PipedReader的建立必须在构造器中与一个PipedWriter相关联。
 * Sender把数据放进Writer，然后休眠一段时间（随机数）。然而，Receiver没有sleep（）和 wait（0。但当它调用readO时，如果没有更多的数据，管道将自动阻塞。
 *
 * 注意sender和receiver是在main（中启动的，即对象构造彻底完毕以后。如果你启动了一个没有构造完毕的对象，在不同的平台上管道可能会产生不一致的行为（注意，BlockingQueue使用起来更加健壮而容易）。
 *
 * 在shutdownNowO被调用时，可以看到PipedReader与普通I/O之间最重要的差异———Piped- Reader是可中断的。如果你将in.readO调用修改为System.in.readO，那么interruptO将不能打断 read）调用。
 *
 * 练习30∶ （1） 修改PipedIO，java，使其使用BlockingQueue而不是管道。
 */
