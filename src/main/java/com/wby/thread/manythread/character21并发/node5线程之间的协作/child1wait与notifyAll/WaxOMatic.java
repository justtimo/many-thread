//: concurrency/waxomatic/WaxOMatic.java
// Basic task cooperation.
package com.wby.thread.manythread.character21并发.node5线程之间的协作.child1wait与notifyAll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 * waitO使你可以等待某个条件发生变化，而改变这个条件超出了当前方法的控制能力。通常，这种条件将由另一个任务来改变。你肯定不想在你的任务测试这个条件的同时，不断地进行空循环，
 * 这被称为忙等待，通常是一种不良的CPU周期使用方式。因此waitO）会在等待外部世界产生变化的时候将任务挂起，并且只有在notifyO或notifyAlO发生时，即表示发生了某些感兴趣的事物，
 * 这个任务才会被唤醒并去检查所产生的变化。因此，wait（）提供了一种在任务之间对活动同步的方式。
 *
 * 调用sleepO的时候锁并没有被释放，调用yield（）也属于这种情况，理解这一点很重要。另一方面，当一个任务在方法里遇到了对waitO）的调用的时候，线程的执行被挂起，对象上的锁被释放。
 * 因为waitO将释放锁，。这就意味着另—个任务可以获得这个锁。因此在该对象 （班现在是未锁定的）中的其他synchronized方法可以在waitO）期间被调用。
 * 这一点至关重要，因为这些其他的方法通常将会产生改变，而这种改变正是使被挂起的任务重新唤醒所感兴趣的变化。因此，当你调用wait（）时，就是在声明;"我已经刚刚做完能做的所有事情，因此我要在这里等待，
 * 但是我希望其他的synchronized操作在条件适合的情况下能够执行。"
 *
 * 有两种形式的waitO。第—种版本接受毫秒数作为参数，含 义与sleep（）方法里参数的意思相同，都是指"在此期间暂停"。但是与sleepO不同的是，对于wait（而言∶
 *    1）在wait（）期间对象锁是释放的。
 *    2）可以通过notify（、notifyAllO），或者令时间到期，从wait（）中恢复执行。
 *
 * 第二种，也是更常用形式的waitO不接受任何参数。这种wait）将无限等待下去，直到线程接收到notifyO或者notifyAl（消息。
 *
 * waitO、notifyO以及notifyAllO有一个比较特殊的方面，那就是这些方法是基类Object的一部分，而不是属于Thread的一部分。尽管开始看起来有点奇怪——仅仅针对线程的功能却作为通用基类的—部分而实现，
 * 不过这是有道理的，因为这些方法操作的锁也是所有对象的一部分。所以，你可以把waitO放进任何同步控制方法里，而不用考虑这个类是继承自Thread还是实现了 Runnable接口。
 * 实际上，只能在同步控制方法或同步控制块里调用wait（）、notifyO和notifyAlI）（因为不用操作锁，所以sleepO可以在非同步控制方法里调用）。
 *
 * 如果在非同步控制方法里调用这些方法，程序能通过编译，但运行的时候，将得到IegalMonitorStateException异常，并伴随着一些含糊的消息，比如"当前线程不是拥有者"。
 * 消息的意思是，调用wait（）、notifyO）和notifyAllO的任务在调用这些方法前必须"拥有"（获取）对象的锁。
 *
 * 可以让另一个对象执行某种操作以维护其自己的锁。要这么做的话，必须首先得到对象的锁。比如，如果要向对象x发送notifyAlIO，那么就必须在能够取得x的锁的同步控制块中这么做;
 *      synchronized(x){
 *          x.notifyAll();
 *      }
 *
 * 让我们看一个简单的示例，WaxOMaticjava有两个过程∶ 一个是将蜡涂到Car上，一个是抛光它。抛光任务在涂蜡任务完成之前，是不能执行其工作的，而涂蜡任务在涂另一层蜡之前，必须等待抛光任务完成。WaxOn和WaxOff都使用了Car对象，
 * 该对象在这些任务等待条件变化的时候，使用wait（）和notifyAllO来挂起和重新启动这些任务;
 */
class Car {
  private boolean waxOn = false;
  public synchronized void waxed() {
    waxOn = true; // Ready to buff
    notifyAll();
  }
  public synchronized void buffed() {
    waxOn = false; // Ready for another coat of wax
    notifyAll();
  }
  public synchronized void waitForWaxing()
  throws InterruptedException {
    while(waxOn == false)
      wait();
  }
  public synchronized void waitForBuffing()
  throws InterruptedException {
    while(waxOn == true)
      wait();
  }
}

class WaxOn implements Runnable {
  private Car car;
  public WaxOn(Car c) { car = c; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        printnb("Wax On! ");
        TimeUnit.MILLISECONDS.sleep(200);
        car.waxed();
        car.waitForBuffing();
      }
    } catch(InterruptedException e) {
      print("Exiting via interrupt");
    }
    print("Ending Wax On task");
  }
}

class WaxOff implements Runnable {
  private Car car;
  public WaxOff(Car c) { car = c; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        car.waitForWaxing();
        printnb("Wax Off! ");
        TimeUnit.MILLISECONDS.sleep(200);
        car.buffed();
      }
    } catch(InterruptedException e) {
      print("Exiting via interrupt");
    }
    print("Ending Wax Off task");
  }
}

public class WaxOMatic {
  public static void main(String[] args) throws Exception {
    Car car = new Car();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new WaxOff(car));
    exec.execute(new WaxOn(car));
    TimeUnit.SECONDS.sleep(5); // Run for a while...
    exec.shutdownNow(); // Interrupt all tasks
  }
} /* Output: (95% match)
Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Exiting via interrupt
Ending Wax On task
Exiting via interrupt
Ending Wax Off task
*///:~
/**
 * 这里，Car有一个单一的布尔属性waxOn，表示涂蜡-抛光处理的状态。
 *
 * 在waitForWaxing（）中将检查waxOn标志，如果它为false，那么这个调用任务将通过调用 waitO而被挂起。这个行为发生在synchronized方法中这一点很重要，因为在这样的方法中，，
 * 任务已经获得了锁。当你调用wait（）时，线程被挂起，而锁被释放。锁被释放这一点是本质所在，因为为了安全地改变对象的状态（例如，将waxOn改变为true，如果被挂起的任务要继续执行，就必须执行该动作），
 * 其他某个任务就必须能获得这个锁。在本例中，如果另—一个任务调用waxedO来表示"是时候该于点什么了"，那么就必须获得这个锁，从而将waxOn改变为true。
 * 之后，waxed（调用notifyAllO，这将唤醒在对wait（）的调用中被挂起的任务。为了使该任务从 waitO中唤醒，它必须首先重新获得当它进入waitO时释放的锁。在这个锁变得可用之前，这个任务是不会被唤醒的。
 *
 * WaxOn.runO）表示给汽车打蜡过程的第一个步骤，因此它将执行它的操作;调用sleepO以模拟需要涂蜡的时间，然后告知汽车涂蜡结束，并调用waitForBuffingO，这个方法会用一个wait（）调用来挂起这个任务，
 * 直至WaxOff任务调用这辆车的buffedO，，从而改变状态并调用notifyAlI（）为止。另一方面，WaxOffrun（）立即进入waitForWaxingO，并因此而被挂起，直至WaxOn涂完蜡并且waxedO）被调用。
 * 在运行这个程序时，你可以看到当控制权在两个任务之间来回互相传递时，这个两步骤过程在不断地重复。在5秒钟之后，interrupt（）会中止这两个线程;
 * 当你调用某个ExecutorService的shutdownNowO时，它会调用所有由它控制的线程的interrupt
 *
 * 前面的示例强调你必须用一个检查感兴趣的条件的while循环包围wait（）。这很重要，因为;
 *    ·你可能有多个任务出于相同的原因在等待同一个锁，而第一个唤醒任务可能会改变这种状况（即使你没有这么做，有人也会通过继承你的类去这么做）。如果属于这种情况，那么这个任务应该被再次挂起，直至其感兴趣的条件发生变化。
 *    ·在这个任务从其wait（中被唤醒的时刻，有可能会有某个其他的任务已经做出了改变，从而使得这个任务在此时不能执行，或者执行其操作已显得无关紧要。此时，应该通过再次调用wait（）来将其重新挂起。
 *    ·也有可能某些任务出于不同的原因在等待你的对象上的锁（在这种情况下必须使用 notifyAllO）。在这种情况下，你需要检查是否已经由正确的原因唤醒，如果不是，就再次调用wait（。
 *
 * 因此，其本质就是要检查所感兴趣的特定条件，并在条件不满足的情况下返回到wait（中。惯用的方法就是使用while来编写这种代码。
 */
/**
 * 错失的信号
 * 当两个线程使用notifyO/waitO或notifyAlIO/waitO进行协作时，有可能会错过某个信号。假设T1是通知T2的线程，而这两个线程都是使用下面（有缺陷的）方式实现的∶
 * T1:
 *    synchronized(sharedMonitor){
 *        <setup condition for T2>
 *        sharedMonitor.notify();
 *    }
 * T2:
 *    while(someCondition){
 *        // .Point 1
 *        synchronized(sharedNonitor ) {
 *            sharedMonitor .wait();
 *        }
 *    }
 *
 * 之Setup condition for T2>是防止T2调用waitO的一个动作，当然前提是T2还没有调用waitO。假设T2对someCondition求值并发现其为true。在Point1，线程调度器可能切换到了T1。
 * 而 T1将执行其设置，然后调用notifyO。当T2得以继续执行时，此时对于T2来说，时机已经太晚了，以至于不能意识到这个条件已经发生了变化，因此会盲目进入waitO。此时notifyO将错失，
 * 而 T2也将无限地等待这个已经发送过的信号，从而产生死锁。
 *
 * 该问题的解决方案是防止在someConditon变量上产生竞争条件。下面是T2正确的执行方式∶
 *    synchronized(sharedNonitor) {
 *        while(someCondition){
 *            sharedMonitor .wait ();
 *        }
 *    }
 *
 * 现在，如果T1首先执行，当控制返回T2时，它将发现条件发生了变化，从而不会进入waitO。
 * 反过来，如果T2首先执行，那它将进入waitO，并且稍后会由T1唤醒。因此，信号不会错失。
 */
