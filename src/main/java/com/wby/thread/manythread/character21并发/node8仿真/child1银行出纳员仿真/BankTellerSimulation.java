package com.wby.thread.manythread.character21并发.node8仿真.child1银行出纳员仿真;//: concurrency/BankTellerSimulation.java
// Using queues and multithreading.
// {Args: 5}

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 这个经典的仿真可以表示任何属于下面这种类型的情况;对象随机地出现，并且要求由数量有限的服务器提供随机数量的服务时间。通过构建仿真可以确定理想的服务器数量。
 *
 * 在本例中，每个银行顾客要求一定数量的服务时间，这是出纳员必须花费在顾客身上，以服务顾客需求的时间单位的数量。服务时间的数量对每个顾客来说都是不同的，并且是随机确定的。
 * 另外，你不知道在每个时间间隔内有多少顾客会到达，因此这也是随机确定的∶
 */
// Read-only objects don't require synchronization:
class Customer {
  private final int serviceTime;
  public Customer(int tm) { serviceTime = tm; }
  public int getServiceTime() { return serviceTime; }
  public String toString() {
    return "[" + serviceTime + "]";
  }
}

// Teach the customer line to display itself:
class CustomerLine extends ArrayBlockingQueue<Customer> {
  public CustomerLine(int maxLineSize) {
    super(maxLineSize);
  }
  public String toString() {
    if(this.size() == 0)
      return "[Empty]";
    StringBuilder result = new StringBuilder();
    for(Customer customer : this)
      result.append(customer);
    return result.toString();
  }
}

// Randomly add customers to a queue:
class CustomerGenerator implements Runnable {
  private CustomerLine customers;
  private static Random rand = new Random(47);
  public CustomerGenerator(CustomerLine cq) {
    customers = cq;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
        customers.put(new Customer(rand.nextInt(1000)));
      }
    } catch(InterruptedException e) {
      System.out.println("CustomerGenerator interrupted");
    }
    System.out.println("CustomerGenerator terminating");
  }
}

class Teller implements Runnable, Comparable<Teller> {
  private static int counter = 0;
  private final int id = counter++;
  // Customers served during this shift:
  private int customersServed = 0;
  private CustomerLine customers;
  private boolean servingCustomerLine = true;
  public Teller(CustomerLine cq) { customers = cq; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        Customer customer = customers.take();
        TimeUnit.MILLISECONDS.sleep(
          customer.getServiceTime());
        synchronized(this) {
          customersServed++;
          while(!servingCustomerLine)
            wait();
        }
      }
    } catch(InterruptedException e) {
      System.out.println(this + "interrupted");
    }
    System.out.println(this + "terminating");
  }
  public synchronized void doSomethingElse() {
    customersServed = 0;
    servingCustomerLine = false;
  }
  public synchronized void serveCustomerLine() {
    assert !servingCustomerLine:"already serving: " + this;
    servingCustomerLine = true;
    notifyAll();
  }
  public String toString() { return "Teller " + id + " "; }
  public String shortString() { return "T" + id; }
  // Used by priority queue:
  public synchronized int compareTo(Teller other) {
    return customersServed < other.customersServed ? -1 :
      (customersServed == other.customersServed ? 0 : 1);
  }
}

class TellerManager implements Runnable {
  private ExecutorService exec;
  private CustomerLine customers;
  private PriorityQueue<Teller> workingTellers =
    new PriorityQueue<Teller>();
  private Queue<Teller> tellersDoingOtherThings =
    new LinkedList<Teller>();
  private int adjustmentPeriod;
  private static Random rand = new Random(47);
  public TellerManager(ExecutorService e,
    CustomerLine customers, int adjustmentPeriod) {
    exec = e;
    this.customers = customers;
    this.adjustmentPeriod = adjustmentPeriod;
    // Start with a single teller:
    Teller teller = new Teller(customers);
    exec.execute(teller);
    workingTellers.add(teller);
  }
  public void adjustTellerNumber() {
    // This is actually a control system. By adjusting
    // the numbers, you can reveal stability issues in
    // the control mechanism.
    // If line is too long, add another teller:
    if(customers.size() / workingTellers.size() > 2) {
        // If tellers are on break or doing
        // another job, bring one back:
        if(tellersDoingOtherThings.size() > 0) {
          Teller teller = tellersDoingOtherThings.remove();
          teller.serveCustomerLine();
          workingTellers.offer(teller);
          return;
        }
      // Else create (hire) a new teller
      Teller teller = new Teller(customers);
      exec.execute(teller);
      workingTellers.add(teller);
      return;
    }
    // If line is short enough, remove a teller:
    if(workingTellers.size() > 1 &&
      customers.size() / workingTellers.size() < 2)
        reassignOneTeller();
    // If there is no line, we only need one teller:
    if(customers.size() == 0)
      while(workingTellers.size() > 1)
        reassignOneTeller();
  }
  // Give a teller a different job or a break:
  private void reassignOneTeller() {
    Teller teller = workingTellers.poll();
    teller.doSomethingElse();
    tellersDoingOtherThings.offer(teller);
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
        adjustTellerNumber();
        System.out.print(customers + " { ");
        for(Teller teller : workingTellers)
          System.out.print(teller.shortString() + " ");
        System.out.println("}");
      }
    } catch(InterruptedException e) {
      System.out.println(this + "interrupted");
    }
    System.out.println(this + "terminating");
  }
  public String toString() { return "TellerManager "; }
}

public class BankTellerSimulation {
  static final int MAX_LINE_SIZE = 50;
  static final int ADJUSTMENT_PERIOD = 1000;
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    // If line is too long, customers will leave:
    CustomerLine customers =
      new CustomerLine(MAX_LINE_SIZE);
    exec.execute(new CustomerGenerator(customers));
    // Manager will add and remove tellers as necessary:
    exec.execute(new TellerManager(
      exec, customers, ADJUSTMENT_PERIOD));
    if(args.length > 0) // Optional argument
      TimeUnit.SECONDS.sleep(new Integer(args[0]));
    else {
      System.out.println("Press 'Enter' to quit");
      System.in.read();
    }
    exec.shutdownNow();
  }
} /* Output: (Sample)
[429][200][207] { T0 T1 }
[861][258][140][322] { T0 T1 }
[575][342][804][826][896][984] { T0 T1 T2 }
[984][810][141][12][689][992][976][368][395][354] { T0 T1 T2 T3 }
Teller 2 interrupted
Teller 2 terminating
Teller 1 interrupted
Teller 1 terminating
TellerManager interrupted
TellerManager terminating
Teller 3 interrupted
Teller 3 terminating
Teller 0 interrupted
Teller 0 terminating
CustomerGenerator interrupted
CustomerGenerator terminating
*///:~
/**
 * Customer对象非常简单，只包含一个final int域。因为这些对象从来都不发生变化，因此它们是只读对象，并且不需要同步或使用volatile。在这之上，
 * 每个Teller任务在任何时刻都只从输入队列中移除一个Customer，并且在这个Customer上工作直至完成，因此Customer在任何时刻都只由一个任务访问。
 *
 * CustomerLine表示顾客在等待被某个Teller服务时所排成的单一的行。这只是一个Array- BlockingQueue，它具有一个toStringO方法，可以按照我们希望的形式打印结果。
 *
 * CustomerGenerator附着在CustomerLine上，按照随机的时间间隔向这个队列中添加 Customer。
 *
 * Teller从CustomerLine中取走Customer，在任何时刻他都只能处理一个顾客，并且跟踪在这个特定的班次中有他服务的Customer的数量。
 * 当没有足够多的顾客时，他会被告知去执行 doSomethingElse（），而当出现了许多顾客时，他会被告知去执行serveCustomerLine（。
 * 为了选择下一个出纳员，让其回到服务顾客的业务上，compareTo（方法将查看出纳员服务过的顾客数量，使得PriorityQueue可以自动地将工作量最小的出纳员推向前台。
 *
 * TellerManager是各种活动的中心，它跟踪所有的出纳员以及等待服务的顾客。这个仿真中有一件有趣的事情，即它试图发现对于给定的顾客流，最优的出纳员数量是多少。
 * 你可以在adjustTellerNumberO中看到这一点，这是一个控制系统，它能够以稳定的方式添加或移除出纳员。所有的控制系统都具有稳定性问题，如果它们对变化反映过快，
 * 那么它们就是不稳定的，而如果它们反映过慢，则系统会迁移到它的某种极端情况。
 *
 * 练习35∶（8）修改BankTellerSimulation.java，使它表示Web客户端，向具有固定数量的服务器发送请求。这么做的目标是要确定这个服务器组可以处理的负载大小。
 */
