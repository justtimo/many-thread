package com.wby.thread.manythread.character21并发.node4终结任务.child3中断;//: concurrency/Interrupting.java
// Interrupting a blocked thread.

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 正如你所想象的，在Runnable.runO方法的中间打断它，与等待该方法到达对cancel标志的测试。或者到达程序员准备好离开该方法的其他一些地方相比，要棘手得多。当你打断被阻塞的任务时，可能需要清理资源。
 * 正因为这一点，在任务的runO方法中间打断，更像是抛出的异常，因此在Java线程中的这种类型的异常中断中用到了异常e（这会滑向异常的不恰当用法，因为这意味着你经常用它们来控制执行流程）。
 * 为了在以这种方式终止任务时，返回众所周知的良好状态，你必须仔细考虑代码的执行路径，并仔细编写catch子句以正确清除所有事物。
 *
 * Thread类包含interruptO方法，因此你可以终止被阻塞的任务，这个方法将设置线程的中断状态。如果一个线程已经被阻塞，或者试图执行一个阻塞操作，那么设置这个线程的中断状态将抛出InterruptedException。
 * 当抛出该异常或者该任务调用Thread.interruptedO时，中断状态将被复位。正如你将看到的，Thread.interruptedO提供了离开runO循环而不抛出异常的第二种方式。
 *
 * 为了调用interruptO），你必须持有Thread对象。你可能已经注意到了，新的concurrent类库似平在避免对Thread对象的直接操作，转而尽量通过Executor来执行所有操作。
 * 如果你在 Executor上调用shutdownNowO），那么它将发送一个interrupt）调用给它启动的所有线程。这么做是有意义的，因为当你完成工程中的某个部分或者整个程序时，通常会希望同时关闭某个特定Executor的所有任务。
 * 然而，你有时也会希望只中断某个单一任务。如果使用Executor，那么通过调用submitO而不是executorO来启动任务，就可以持有该任务的上下文。
 * submit（）将返回一个泛型Future<?>，其中有一个未修饰的参数，因为你永远都不会在其上调用get（）——持有这种Future的关键在于你可以在其上调用cancelO，并因此可以使用它来中断某个特定任务。
 * 如果你将true传递给cancelO，那么它就会拥有在该线程上调用interruptO以停止这个线程的权限。因 【185此，cancel（）是一种中断由Executor启动的单个线程的方式。
 *
 * 下面的示例用Executor展示了基本的interrupt）用法∶
 */
class SleepBlocked implements Runnable {
  public void run() {
    try {
      TimeUnit.SECONDS.sleep(100);
    } catch(InterruptedException e) {
      print("InterruptedException");
    }
    print("Exiting SleepBlocked.run()");
  }
}

class IOBlocked implements Runnable {
  private InputStream in;
  public IOBlocked(InputStream is) { in = is; }
  public void run() {
    try {
      print("Waiting for read():");
      in.read();
    } catch(IOException e) {
      if(Thread.currentThread().isInterrupted()) {
        print("Interrupted from blocked I/O");
      } else {
        throw new RuntimeException(e);
      }
    }
    print("Exiting IOBlocked.run()");
  }
}

class SynchronizedBlocked implements Runnable {
  public synchronized void f() {
    while(true) // Never releases lock
      Thread.yield();
  }
  public SynchronizedBlocked() {
    new Thread() {
      public void run() {
        f(); // Lock acquired by this thread
      }
    }.start();
  }
  public void run() {
    print("Trying to call f()");
    f();
    print("Exiting SynchronizedBlocked.run()");
  }
}

public class Interrupting {
  private static ExecutorService exec =
    Executors.newCachedThreadPool();
  static void test(Runnable r) throws InterruptedException{
    Future<?> f = exec.submit(r);
    TimeUnit.MILLISECONDS.sleep(100);
    print("Interrupting " + r.getClass().getName());
    f.cancel(true); // Interrupts if running
    print("Interrupt sent to " + r.getClass().getName());
  }
  public static void main(String[] args) throws Exception {
    test(new SleepBlocked());
    test(new IOBlocked(System.in));
    test(new SynchronizedBlocked());
    TimeUnit.SECONDS.sleep(3);
    print("Aborting with System.exit(0)");
    System.exit(0); // ... since last 2 interrupts failed
  }
} /* Output: (95% match)
Interrupting SleepBlocked
InterruptedException
Exiting SleepBlocked.run()
Interrupt sent to SleepBlocked
Waiting for read():
Interrupting IOBlocked
Interrupt sent to IOBlocked
Trying to call f()
Interrupting SynchronizedBlocked
Interrupt sent to SynchronizedBlocked
Aborting with System.exit(0)
*///:~
/**
 * 上面的每个任务都表示了一种不同类型的阻塞。SleepBlock是可中断的阻塞示例，而 IOBlocked和SynchronizedBlocked是不可中断的阳塞示例9。这个程序证明I/O和在synchronized块上的等待是不可中断的，
 * 但是通过浏览代码，你也可以预见到这一点——无论是I/O还是尝试调用synchronized方法，都不需要任何InterruptedException处理器。
 *
 * 前两个类很简单直观∶在第一个类中runO方法调用了sleep（O，而在第二个类中调用了read0。但是，为了演示SynchronizedBlock，我们必须首先获取锁。这是通过在构造器中创建匿名的 Thread类的实例来实现的，
 * 这个匿名Thread类的对象通过调用fO）获取了对象锁（这个线程必须有别于为SynchronizedBlock驱动run（）的线程，因为一个线程可以多次获得某个对象锁）。由于 fO永远都不返回，因此这个锁永远不会释放，
 * 而SynchronizedBlock.runO在试图调用fO，并阻塞以等待这个锁被释放。
 *
 * 从输出中可以看到，你能够中断对sleepO的调用（或者任何要求抛出InterruptedException的调用）。但是，你不能中断正在试图获取synchronized锁或者试图执行I/O操作的线程。这有点令人烦恼，
 * 特别是在创建执行I/O的任务时，因为这意味着I/O具有锁住你的多线程程序的潜在可能。特别是对于基于Web的程序，这更是关乎利害。
 *
 * 对于这类问题，有一个略显笨拙但是有时确实行之有效的解决方案，即关闭任务在其上发生阻塞的底层资源∶
 */
class CloseResource {
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    ServerSocket server = new ServerSocket(8080);
    InputStream socketInput =
            new Socket("localhost", 8080).getInputStream();
    exec.execute(new IOBlocked(socketInput));
    exec.execute(new IOBlocked(System.in));
    TimeUnit.MILLISECONDS.sleep(100);
    print("Shutting down all threads");
    exec.shutdownNow();
    TimeUnit.SECONDS.sleep(1);
    print("Closing " + socketInput.getClass().getName());
    socketInput.close(); // Releases blocked thread
    TimeUnit.SECONDS.sleep(1);
    print("Closing " + System.in.getClass().getName());
    System.in.close(); // Releases blocked thread
  }
} /* Output: (85% match)
Waiting for read():
Waiting for read():
Shutting down all threads
Closing java.net.SocketInputStream
Interrupted from blocked I/O
Exiting IOBlocked.run()
Closing java.io.BufferedInputStream
Exiting IOBlocked.run()
*///:~
/**
 * 在shutdownNowO）被调用之后以及在两个输入流上调用closeO之前的延迟强调的是一旦底层资源被关闭，任务将解除阻塞。请注意，有一点很有趣，interrupt（）看起来发生在关闭Socket而不是关闭System.in的时刻。
 *
 * 幸运的是，在第18章中介绍的各种nio类提供了更人性化的I/O中断。被阳塞的nio通道会自动地响应中断∶
 */
class NIOBlocked implements Runnable {
  private final SocketChannel sc;
  public NIOBlocked(SocketChannel sc) { this.sc = sc; }
  public void run() {
    try {
      print("Waiting for read() in " + this);
      sc.read(ByteBuffer.allocate(1));
    } catch(ClosedByInterruptException e) {
      print("ClosedByInterruptException");
    } catch(AsynchronousCloseException e) {
      print("AsynchronousCloseException");
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    print("Exiting NIOBlocked.run() " + this);
  }
}

class NIOInterruption {
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    ServerSocket server = new ServerSocket(8080);
    InetSocketAddress isa =
            new InetSocketAddress("localhost", 8080);
    SocketChannel sc1 = SocketChannel.open(isa);
    SocketChannel sc2 = SocketChannel.open(isa);
    Future<?> f = exec.submit(new NIOBlocked(sc1));
    exec.execute(new NIOBlocked(sc2));
    exec.shutdown();
    TimeUnit.SECONDS.sleep(1);
    // Produce an interrupt via cancel:
    f.cancel(true);
    TimeUnit.SECONDS.sleep(1);
    // Release the block by closing the channel:
    sc2.close();
  }
} /* Output: (Sample)
Waiting for read() in NIOBlocked@7a84e4
Waiting for read() in NIOBlocked@15c7850
ClosedByInterruptException
Exiting NIOBlocked.run() NIOBlocked@15c7850
AsynchronousCloseException
Exiting NIOBlocked.run() NIOBlocked@7a84e4
*///:~
/**
 * 如你所见，你还可以关闭底层资源以释放锁，尽管这种做法一般不是必需的。注意，使用 execute（）来启动两个任务，并调用e.shutdownNow）将可以很容易地终止所有事物，
 * 而对于捕获上面示例中的Future，只有在将中断发送给一个线程，同时不发送给另一个线程时才是必需的
 *
 * 被互斥所阻塞
 * 就像在Interrupting。.java中看到的，如果你尝试着在—个对象 上调用其svnchronized方法，而这个对象的锁已经被其他任务获得，那么调用任务将被挂起（阻塞），直至这个锁可获得。下面的示例说明了同一个互斥可以如何能被同一个任务多次获得;
 */
class MultiLock {
  public synchronized void f1(int count) {
    if(count-- > 0) {
      print("f1() calling f2() with count " + count);
      f2(count);
    }
  }
  public synchronized void f2(int count) {
    if(count-- > 0) {
      print("f2() calling f1() with count " + count);
      f1(count);
    }
  }
  public static void main(String[] args) throws Exception {
    final MultiLock multiLock = new MultiLock();
    new Thread() {
      public void run() {
        multiLock.f1(10);
      }
    }.start();
  }
} /* Output:
f1() calling f2() with count 9
f2() calling f1() with count 8
f1() calling f2() with count 7
f2() calling f1() with count 6
f1() calling f2() with count 5
f2() calling f1() with count 4
f1() calling f2() with count 3
f2() calling f1() with count 2
f1() calling f2() with count 1
f2() calling f1() with count 0
*///:~
/**
 * 在mainO中创建了一个调用f1O的Thread，然后f10和f2O）互相调用直至count变为0。由于这个任务已经在第一个对f1O的调用中获得了multiLock对象锁，因此同一个任务将在对f2O的调用中再次获取这个锁，
 * 依此类推。这么做是有意义的，因为一个任务应该能够调用在同一个对象中的其他的synchronized方法，而这个任务已经持有锁了。
 *
 * 就像前面在不可中断的I/O中所观察到的那样，无论在任何时刻，只要任务以不可中断的方式被阻塞，那么都有潜在的会锁住程序的可能。Java SE5并发类库中添加了一个特性，
 * 即在 ReentrantLock上阻塞的任务具备可以被中断的能力，这与在synchronized方法或临界区上阻塞的任务完全不同∶
 */
class BlockedMutex {
  private Lock lock = new ReentrantLock();
  public BlockedMutex() {
    // Acquire it right away, to demonstrate interruption
    // of a task blocked on a ReentrantLock:
    lock.lock();
  }
  public void f() {
    try {
      // This will never be available to a second task
      lock.lockInterruptibly(); // Special call
      print("lock acquired in f()");
    } catch(InterruptedException e) {
      print("Interrupted from lock acquisition in f()");
    }
  }
}

class Blocked2 implements Runnable {
  BlockedMutex blocked = new BlockedMutex();
  public void run() {
    print("Waiting for f() in BlockedMutex");
    blocked.f();
    print("Broken out of blocked call");
  }
}

class Interrupting2 {
  public static void main(String[] args) throws Exception {
    Thread t = new Thread(new Blocked2());
    t.start();
    TimeUnit.SECONDS.sleep(1);
    System.out.println("Issuing t.interrupt()");
    t.interrupt();
  }
} /* Output:
Waiting for f() in BlockedMutex
Issuing t.interrupt()
Interrupted from lock acquisition in f()
Broken out of blocked call
*///:~
/**
 * BlockedMutex类有一个构造器，它要获取所创建对象上自身的Lock，并且从不释放这个锁。出于这个原因，如果你试图从第二个任务中调用fO（不同于创建这个BlockedMutex的任务），
 * 那么将会总是因Mutex不可获得而被阻塞。在Blocked2中，runO方法总是在调用blocked.fO的地方停止。当运行这个程序时，你将会看到，与I/O调用不同，interruptO可以打断被互斥所阻塞的调用
 *
 * 注意，尽管不太可能，但是对tinterruptO的调用确实可以发生在对blocked.fO的调用之前。
 */

















































