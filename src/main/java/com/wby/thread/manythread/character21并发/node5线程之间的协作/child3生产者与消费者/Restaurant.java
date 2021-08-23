package com.wby.thread.manythread.character21并发.node5线程之间的协作.child3生产者与消费者;//: concurrency/Restaurant.java
// The producer-consumer approach to task cooperation.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 * 请考虑这样一个饭店，它有一个厨师和一个服务员。这个服务员必须等待厨师准备好膳食。当厨师准备好时，他会通知服务员，之后服务员上菜，然后返回继续等待。
 * 这是一个任务协作的示例∶厨师代表生产者，而服务员代表消费者。两个任务必须在膳食被生产和消费时进行握手，而系统必须以有序的方式关闭。下面是对这个叙述建模的代码∶
 */
class Meal {
  private final int orderNum;
  public Meal(int orderNum) { this.orderNum = orderNum; }
  public String toString() { return "Meal " + orderNum; }
}

class WaitPerson implements Runnable {
  private Restaurant restaurant;
  public WaitPerson(Restaurant r) { restaurant = r; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        synchronized(this) {
          while(restaurant.meal == null)
            wait(); // ... for the chef to produce a meal
        }
        print("Waitperson got " + restaurant.meal);
        synchronized(restaurant.chef) {
          restaurant.meal = null;
          restaurant.chef.notifyAll(); // Ready for another
        }
      }
    } catch(InterruptedException e) {
      print("WaitPerson interrupted");
    }
  }
}

class Chef implements Runnable {
  private Restaurant restaurant;
  private int count = 0;
  public Chef(Restaurant r) { restaurant = r; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        synchronized(this) {
          while(restaurant.meal != null)
            wait(); // ... for the meal to be taken
        }
        if(++count == 10) {
          print("Out of food, closing");
          restaurant.exec.shutdownNow();
        }
        printnb("Order up! ");
        synchronized(restaurant.waitPerson) {
          restaurant.meal = new Meal(count);
          restaurant.waitPerson.notifyAll();
        }
        TimeUnit.MILLISECONDS.sleep(100);
      }
    } catch(InterruptedException e) {
      print("Chef interrupted");
    }
  }
}

public class Restaurant {
  Meal meal;
  ExecutorService exec = Executors.newCachedThreadPool();
  WaitPerson waitPerson = new WaitPerson(this);
  Chef chef = new Chef(this);
  public Restaurant() {
    exec.execute(chef);
    exec.execute(waitPerson);
  }
  public static void main(String[] args) {
    new Restaurant();
  }
} /* Output:
Order up! Waitperson got Meal 1
Order up! Waitperson got Meal 2
Order up! Waitperson got Meal 3
Order up! Waitperson got Meal 4
Order up! Waitperson got Meal 5
Order up! Waitperson got Meal 6
Order up! Waitperson got Meal 7
Order up! Waitperson got Meal 8
Order up! Waitperson got Meal 9
Out of food, closing
WaitPerson interrupted
Order up! Chef interrupted
*///:~
/**
 * Restaurant是WaitPerson和Chef的焦点，他们都必须知道在为哪个Restaurant工作，因为他们必须和这家饭店的"餐窗"打交道，以便放置或拿取膳食restaurant.meal。
 * 在run（）中， WaitPerson进入waitO）模式，停止其任务，直至被Chef的notifyAll（）唤醒。由于这是一个非常简单的程序，因此我们知道只有一个任务将在WaitPerson的锁上等待∶即WaitPerson任务自身。
 * 出于这个原因，理论上可以调用notifyO而不是notifyAlIO。但是，在更复杂的情况下，可能会有多个任务在某个特定对象锁上等待，因此你不知道哪个任务应该被唤醒。
 * 因此，调用notifyAlIO要更安全一些，这样可以唤醒等待这个锁的所有任务，而每个任务都必须决定这个通知是否与自己相关。
 *
 * 一旦Chef送上Meal并通知WaitPerson，这个Chef就将等待，直至WaitPerson收集到订单并通知Chef，之后Chef就可以烧下一份Meal了。
 *
 * 注意，waitO被包装在一个whileO语句中，这个语句在不断地测试正在等待的事物。乍看上去这有点怪——如果在等待一个订单，一旦你被唤醒，这个订单就必定是可获得的，对吗?正如前面注意到的，
 * 问题是在并发应用中，某个其他的任务可能会在WaitPerson被唤醒时，会突然插足并拿走订单，唯一安全的方式是使用下面这种waitO的惯用法（当然要在恰当的同步内部，【1210并采用防止错失信号可能性的程序设计）∶
 *    while(condi tion1sNotHet){
 *         wait();
 *    }
 * 这可以保证在你退出等待循环之前，条件将得到满足，并且如果你收到了关于某事物的通知，而它与这个条件并无关系 （就象在使用notifyAlIO时可能发生的情况一样），或者在你完全退出等待循环之前，这个条件发生了变化，都可以确保你可以重返等待状态。
 *
 * 请注意观察，对notifyAllO的调用必须首先捕获waitPerson上的锁，而在WaitPerson.runO中的对waitO）的调用会自动地释放这个锁，因此这是有可能实现的。因为调用notifyAllO必然拥有这个锁，
 * 所以这可以保证两个试图在同一个对象上调用notifyAll（O的任务不会互相冲突。
 *
 * 通过把整个run（方法体放到一个try语句块中，可使得这两个runO方法都被设计为可以有序地关闭。catch子句将紧挨着run（）方法的结束括号之前结束，因此，如果这个任务收到了 InterruptedException异常，它将在捕获异常之后立即结束。
 *
 * 注意，在Chef中，在调用shutdownNowO之后，你应该直接从runO返回，并且通常这就是你应该做的。但是，以这种方式执行还有一些更有趣的东西。记住，shutdownNowO）将向所有由ExecutorService启动的任务发送interrupt（），
 * 但是在Chef中，任务并没有在获得该interrupt（）之后立即关闭，因为当任务试图进入一个（可中断的）阻塞操作时，这个中断只能抛出 InterruptedException。因此，你将看到首先显示了"Order up!"，
 * 然后当Chef试图调用sleepO时，抛出了InterruptedException。如果移除对sleepO的调用，那么这个任务将回到runO循环的】顶部，并由于Thread.interrupted（）测试而退出，同时并不抛出异常。
 *
 * 在前面的示例中，对于一个任务而言，只有一个单一的地点用于存放对象，从而使得另一个任务稍后可以使用这个对象。但是。在典型的生产者-消费者实现中。应使用先进先出队列来存储被生产和消费的对象。
 * 你将在本章稍后学习有关这种队列的知识。
 *
 * 练习24∶（1）使用waitO和notifyAllO解决单个生产者、单个消费者问题。生产者不能溢出接收者的缓冲区，而这在生产者比消费者速度快时完全有可能发生。如果消费者比生产者速度快，那么消费者不能读取多次相同数据。不要对生产者和消费者的相对速度作任何假设。
 * 练习25，（1）在Restaurant.java的Chef类中，在调用shutdownNowO之后从runO中return，观察行为上的差异。
 * 练习26;（8）向Restaurantjava中添加一个BusBoy类。在上菜之后，WaitPerson应该通知 BusBoy清理。

 */

/**
 * 使用显式的Lock和Condition对象
 * .
 * 在 Java SE5的java.util.concurrent类 库中还 有 额 外 的显 式 工具可 以 用 来 重 写 WaxOMatic.java。使用互斥并允许任务挂起的基本类是Condition，
 * 你可以通过在Condition上调用await（）来挂起一个任务。当外部条件发生变化，意味着某个任务应该继续执行时，你可以通过调用signalO）来通知这个任务，从而唤醒一个任务，
 * 或者调用signalAll（）来唤醒所有在这个 Condition上被其自身挂起的任务（与使用notifyAllO）相比，signalAll（）是更安全的方式）。
 *
 * 下面是WaxOMatic.java的重写版本，它包含一个Condition，用来在waitForWaxing（）或 waitForBuffering（）内部挂起一个任务∶
 */
class Car {
  private Lock lock = new ReentrantLock();
  private Condition condition = lock.newCondition();
  private boolean waxOn = false;
  public void waxed() {
    lock.lock();
    try {
      waxOn = true; // Ready to buff
      condition.signalAll();
    } finally {
      lock.unlock();
    }
  }
  public void buffed() {
    lock.lock();
    try {
      waxOn = false; // Ready for another coat of wax
      condition.signalAll();
    } finally {
      lock.unlock();
    }
  }
  public void waitForWaxing() throws InterruptedException {
    lock.lock();
    try {
      while(waxOn == false)
        condition.await();
    } finally {
      lock.unlock();
    }
  }
  public void waitForBuffing() throws InterruptedException{
    lock.lock();
    try {
      while(waxOn == true)
        condition.await();
    } finally {
      lock.unlock();
    }
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

class WaxOMatic2 {
  public static void main(String[] args) throws Exception {
    Car car = new Car();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new WaxOff(car));
    exec.execute(new WaxOn(car));
    TimeUnit.SECONDS.sleep(5);
    exec.shutdownNow();
  }
} /* Output: (90% match)
Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Exiting via interrupt
Ending Wax Off task
Exiting via interrupt
Ending Wax On task
*///:~
/**
 * 在Car的构造器中，单个的Lock将产生一个Condition对象，这个对象被用来管理任务间的通信。但是，这个Condition对象不包含任何有关处理状态的信息，因此你需要管理额外的表示处理状态的信息，即boolean waxOn.。
 *
 * 每个对lockO的调用都必须紧跟一个try-finally子句，用来保证在所有情况下都可以释放锁。在使用内建版本时，任务在可以调用await（、signalO或signalAllO之前，必须拥有这个锁。
 *
 * 注意。这个解决方案比前一个更加复杂，在本例中这种复杂性并未使你收获更多。Lock和 Condition对象只有在更加困难的多线程问题中才是必需的。
 */
