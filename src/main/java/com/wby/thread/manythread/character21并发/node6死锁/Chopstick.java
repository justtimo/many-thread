package com.wby.thread.manythread.character21并发.node6死锁;//: concurrency/Chopstick.java
// Chopsticks for dining philosophers.

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 现在你理解了，一个对象可以有synchronized方法或其他形式的加锁机制来防止别的任务在互斥还没有释放的时候就访问这个对象。你已经学习过，任务可以变成阻塞状态，
 * 所以就可能出现这种情况∶某个任务在等待另一个任务，而后者又等待别的任务，这样一直下去，直到这个链条上的任务又在等待第一个任务释放锁。
 * 这得到了一个任务之间相互等待的连续循环，没有哪个线程能继续。这被称之为死锁( 当两个任务可以修改它们的状态（它们不会阻塞）时，你还可以使用活锁，但是这么做不会得到什么有用的改进。)。
 *
 * 如果你运行一个程序，而它马上就死锁了，你可以立即跟踪下去。真正的问题在于，程序可能看起来工作良好，但是具有潜在的死锁危险。这时，死锁可能发生，而事先却没有任何征兆，所以缺陷会潜伏在你的程序里，
 * 直到客户发现它出平意料地发生 （以一种几平肯定是很难重现的方式发生）。因此，在编写并发程序的时候，进行仔细的程序设计以防止死锁是关键部分。
 * 由Edsger Dijikstra提出的哲学 家就餐问题是一个经典的死锁例证。该问题的基本描述中是指定五个哲学家（不过这里的例子中将允许任意数目）。这些哲学家将花部分时间思考，花部分时间就餐。
 * 当他们思考的时候，不需要任何共享资源;但当他们就餐时，将使用有限数量的餐具。在问题的原始描述中，餐具是叉子。要吃到桌子中央盘子里的意大利面条需要用两把叉子，
 * 不过把餐具看成是筷子更合理;很明显，哲学家要就餐就需要两根筷子。
 *
 * 问题中引入的难点是; 作为哲学家，他们很穷，所以他们只能买五根筷子（更一般地讲，筷子和哲学家的数量相同）。他们围坐在桌子周围，每人之间放一根筷子。
 * 当一个哲学家要就餐的时候，这个哲学家必须同时得到左边和右边的筷子。如果一个哲学家左边或右边已经有人在使用筷子了，那么这个哲学家就必须等待，直至可得到必需的筷子。
 */
public class Chopstick {
  private boolean taken = false;
  public synchronized
  void take() throws InterruptedException {
    while(taken)
      wait();
    taken = true;
  }
  public synchronized void drop() {
    taken = false;
    notifyAll();
  }
} ///:~
/**
 * 任何两个Philosopher都不能成功take（）同一根筷子。另外，如果一根Chopstick已经被某个 Philosopher获得，那么另一个Philosopher可以wait（），直至这根Chopstick的当前持有者调用 drop）使其可用为止。
 *
 * 当一个Philosopher任务调用takeO时，这个Philosopher将等待，直至taken标志变为false（直至当前持有Chopstick的Philosopher释放它）。然后这个任务会将taken标志设置为true，
 * 以表示现在由新的Philosopher持有这根Chopstick。当这个Philosopher使用完这根Chopstick时，它会调用dropO来修改标志的状态，并notifyAllO所有其他的Philosopher，这些Philosopher中有些可能就在wait（这根Chopstick。
 */
class Philosopher implements Runnable {
  private Chopstick left;
  private Chopstick right;
  private final int id;
  private final int ponderFactor;
  private Random rand = new Random(47);
  private void pause() throws InterruptedException {
    if(ponderFactor == 0) return;
    TimeUnit.MILLISECONDS.sleep(
            rand.nextInt(ponderFactor * 250));
  }
  public Philosopher(Chopstick left, Chopstick right,
                     int ident, int ponder) {
    this.left = left;
    this.right = right;
    id = ident;
    ponderFactor = ponder;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        print(this + " " + "thinking");
        pause();
        // Philosopher becomes hungry
        print(this + " " + "grabbing right");
        right.take();
        print(this + " " + "grabbing left");
        left.take();
        print(this + " " + "eating");
        pause();
        right.drop();
        left.drop();
      }
    } catch(InterruptedException e) {
      print(this + " " + "exiting via interrupt");
    }
  }
  public String toString() { return "Philosopher " + id; }
} ///:~
/**
 * 在Philosopher， runO中，每个Philosopher只是不断地思考和吃饭。如果PonderFactor不为0，则pauseO方法会休眠（sleepsO）一段随机的时间。通过使用这种方式，
 * 你将看到Philosopher会在思考上花掉一段随机化的时间，然后尝试着获取（takeO）右边和左边的Chopstick，随后在吃饭上再花掉一段随机化的时间，之后重复此过程。
 *
 * 现在我们可以建立这个程序的将会产生死锁的版本了;
 */
class DeadlockingDiningPhilosophers {
  public static void main(String[] args) throws Exception {
    int ponder = 5;
    if(args.length > 0)
      ponder = Integer.parseInt(args[0]);
    int size = 5;
    if(args.length > 1)
      size = Integer.parseInt(args[1]);
    ExecutorService exec = Executors.newCachedThreadPool();
    Chopstick[] sticks = new Chopstick[size];
    for(int i = 0; i < size; i++)
      sticks[i] = new Chopstick();
    for(int i = 0; i < size; i++)
      exec.execute(new Philosopher(
              sticks[i], sticks[(i+1) % size], i, ponder));
    if(args.length == 3 && args[2].equals("timeout"))
      TimeUnit.SECONDS.sleep(5);
    else {
      System.out.println("Press 'Enter' to quit");
      System.in.read();
    }
    exec.shutdownNow();
  }
} /* (Execute to see output) *///:~
/**
 * 你会发现，如果Philosopher花在思考上的时间非常少，那么当他们想要进餐时、全都会在 Chopstick上产生竞争，而死锁也就会更快地发生。
 *
 * 第一个命令行参数可以调整ponder因子，从而影响每个Philosopher花费在思考上的时间长度。如果有许多Philosopher，或者他们花费很多时间去思考，那么尽管存在死锁的可能，但你可能永远也看不到死锁。值为0的命令行参数倾向于使死锁尽快发生。
 *
 * 注意，Chopstick对象不需要内部标识符，它们是由在数组sticks中的位置来标识的。每个 Philosopher构造器都会得到一个对左边和右边Chopstick对象的引用。
 * 除了最后一个Philosopher，其他所有的Philosopher都是通过将这个Philosopher定位于下一对Chopstick对象之间而被初始化的，而最后一个Philosopher右边的Chopstick是第O个Chopstick，
 * 这样这个循环表也就结束了。因为最后一个Philosopher坐在第一个Philosopher的右边，所以他们会共享第0个Chopstick。现在，所有的Philosopher都有可能希望进餐，从而等待其临近的Philosopher放下它们的Chopstick。这将使程序死锁。
 *
 * 如果Philosopher花费更多的时间去思考而不是进餐（使用非O的ponder值，或者大量的 Philosopher），那么他们请求共享资源（Chopstick）的可能性就会小许多，这样你就会确信该程序不会死锁，
 * 尽管它们并非如此。这个示例相当有趣，因为它演示了看起来可以正确运行，但实际上会死锁的程序。
 *
 * 要修正死锁问题，你必须明白，当以下四个条件同时满足时，就会发生死锁∶
 *    1）互斥条件。任务使用的资源中至少有一个是不能共享的。这里，一根Chopstick一次就只能被一个Philosopher使用。
 *    2）至少有一个任务它必须持有一个资源且正在等待获取一个当前被别的任务持有的资源。也就是说，要发生死锁，Philosopher必须拿着一根Chopstick并且等待另一根。
 *    3）资源不能被任务抢占，任务必须把资源释放当作普通事件。Philosopher很有礼貌，他们不会从其他Philosopher那里抢Chopstick。
 *    4）必须有循环等待，这时，一个任务等待其他任务所持有的资源，后者又在等待另一个任务所持有的资源，这样一直下去，直到有一个任务在等待第一个任务所持有的资源，使得大家都被锁住。
 *    在DeadlockingDiningPhilosophers.java中，因为每个Philosopher都试图先得到右边的 Chopstick，然后得到左边的Chopstick，所以发生了循环等待。
 *
 * 因为要发生死锁的话，所有这些条件必须全部满足;所以要防止死锁的话，只需破坏其中一个即可。在程序中，防止死锁最容易的方法是破坏第4个条件。有这个条件的原因是每个 Philosopher都试图用特定的顺序拿Chopstick;
 * 先右后左。正因为如此，就可能会发生"每个人都拿着右边的Chopstick，并等待左边的Chopstick"的情况，这就是循环等待条件。然而，如果最后一个Philosopher被初始化成先拿左边的Chopstick，
 * 后拿右边的Chopstick，那么这个 Philosopher将永远不会阻止其右边的Philosopher拿起他们的Chopstick。在本例中，这就可以防止循环等待。这只是问题的解决方法之一，
 * 也可以通过破坏其他条件来防止死锁（具体细节请参考更高级的讨论线程的书籍）∶
 */
class FixedDiningPhilosophers {
  public static void main(String[] args) throws Exception {
    int ponder = 5;
    if(args.length > 0)
      ponder = Integer.parseInt(args[0]);
    int size = 5;
    if(args.length > 1)
      size = Integer.parseInt(args[1]);
    ExecutorService exec = Executors.newCachedThreadPool();
    Chopstick[] sticks = new Chopstick[size];
    for(int i = 0; i < size; i++)
      sticks[i] = new Chopstick();
    for(int i = 0; i < size; i++)
      if(i < (size-1))
        exec.execute(new Philosopher(
                sticks[i], sticks[i+1], i, ponder));
      else
        exec.execute(new Philosopher(
                sticks[0], sticks[i], i, ponder));
    if(args.length == 3 && args[2].equals("timeout"))
      TimeUnit.SECONDS.sleep(5);
    else {
      System.out.println("Press 'Enter' to quit");
      System.in.read();
    }
    exec.shutdownNow();
  }
} /* (Execute to see output) *///:~

/**
 * 通过确保最后一个Philosopher先拿起和放下左边的Chopstick，我们可以移除死锁，从而使这个程序平滑地运行。
 *
 * Java对死锁并没有提供语言层面上的支持;能否通过仔细地设计程序来避免死锁，这取决于你自己。对于正在试图调试一个有死锁的程序的程序员来说，这不是什么安慰人的话。
 *
 * 练习31;（8）修改DeadlockingDiningPhilosophers.java，使得当哲学家用完筷子之后，把筷子放在一个筷笼里。当哲学家要就餐的时候，他们就从筷笼里取出下两根可用的筷子。这消除了死锁的可能吗?你能仅仅通过减少可用的筷子数目就重新引入死锁吗?
 */




























