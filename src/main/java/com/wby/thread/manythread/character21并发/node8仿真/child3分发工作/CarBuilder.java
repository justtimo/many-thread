package com.wby.thread.manythread.character21并发.node8仿真.child3分发工作;//: concurrency/CarBuilder.java
// A complex example of tasks working together.

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 下面的仿真示例将本章的许多概念都结合在了一起。考虑一个假想的用于汽车的机器人组装线，每辆Car都将分多个阶段构建，从创建底盘开始，紧跟着是安装发动机、车厢和轮子。
 */
class Car {
  private final int id;
  private boolean
    engine = false, driveTrain = false, wheels = false;
  public Car(int idn)  { id = idn; }
  // Empty Car object:
  public Car()  { id = -1; }
  public synchronized int getId() { return id; }
  public synchronized void addEngine() { engine = true; }
  public synchronized void addDriveTrain() {
    driveTrain = true;
  }
  public synchronized void addWheels() { wheels = true; }
  public synchronized String toString() {
    return "Car " + id + " [" + " engine: " + engine
      + " driveTrain: " + driveTrain
      + " wheels: " + wheels + " ]";
  }
}

class CarQueue extends LinkedBlockingQueue<Car> {}

class ChassisBuilder implements Runnable {
  private CarQueue carQueue;
  private int counter = 0;
  public ChassisBuilder(CarQueue cq) { carQueue = cq; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        TimeUnit.MILLISECONDS.sleep(500);
        // Make chassis:
        Car c = new Car(counter++);
        print("ChassisBuilder created " + c);
        // Insert into queue
        carQueue.put(c);
      }
    } catch(InterruptedException e) {
      print("Interrupted: ChassisBuilder");
    }
    print("ChassisBuilder off");
  }
}

class Assembler implements Runnable {
  private CarQueue chassisQueue, finishingQueue;
  private Car car;
  private CyclicBarrier barrier = new CyclicBarrier(4);
  private RobotPool robotPool;
  public Assembler(CarQueue cq, CarQueue fq, RobotPool rp){
    chassisQueue = cq;
    finishingQueue = fq;
    robotPool = rp;
  }
  public Car car() { return car; }
  public CyclicBarrier barrier() { return barrier; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        // Blocks until chassis is available:
        car = chassisQueue.take();
        // Hire robots to perform work:
        robotPool.hire(EngineRobot.class, this);
        robotPool.hire(DriveTrainRobot.class, this);
        robotPool.hire(WheelRobot.class, this);
        barrier.await(); // Until the robots finish
        // Put car into finishingQueue for further work
        finishingQueue.put(car);
      }
    } catch(InterruptedException e) {
      print("Exiting Assembler via interrupt");
    } catch(BrokenBarrierException e) {
      // This one we want to know about
      throw new RuntimeException(e);
    }
    print("Assembler off");
  }
}

class Reporter implements Runnable {
  private CarQueue carQueue;
  public Reporter(CarQueue cq) { carQueue = cq; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        print(carQueue.take());
      }
    } catch(InterruptedException e) {
      print("Exiting Reporter via interrupt");
    }
    print("Reporter off");
  }
}

abstract class Robot implements Runnable {
  private RobotPool pool;
  public Robot(RobotPool p) { pool = p; }
  protected Assembler assembler;
  public Robot assignAssembler(Assembler assembler) {
    this.assembler = assembler;
    return this;
  }
  private boolean engage = false;
  public synchronized void engage() {
    engage = true;
    notifyAll();
  }
  // The part of run() that's different for each robot:
  abstract protected void performService();
  public void run() {
    try {
      powerDown(); // Wait until needed
      while(!Thread.interrupted()) {
        performService();
        assembler.barrier().await(); // Synchronize
        // We're done with that job...
        powerDown();
      }
    } catch(InterruptedException e) {
      print("Exiting " + this + " via interrupt");
    } catch(BrokenBarrierException e) {
      // This one we want to know about
      throw new RuntimeException(e);
    }
    print(this + " off");
  }
  private synchronized void
  powerDown() throws InterruptedException {
    engage = false;
    assembler = null; // Disconnect from the Assembler
    // Put ourselves back in the available pool:
    pool.release(this);
    while(engage == false)  // Power down
      wait();
  }
  public String toString() { return getClass().getName(); }
}

class EngineRobot extends Robot {
  public EngineRobot(RobotPool pool) { super(pool); }
  protected void performService() {
    print(this + " installing engine");
    assembler.car().addEngine();
  }
}

class DriveTrainRobot extends Robot {
  public DriveTrainRobot(RobotPool pool) { super(pool); }
  protected void performService() {
    print(this + " installing DriveTrain");
    assembler.car().addDriveTrain();
  }
}

class WheelRobot extends Robot {
  public WheelRobot(RobotPool pool) { super(pool); }
  protected void performService() {
    print(this + " installing Wheels");
    assembler.car().addWheels();
  }
}

class RobotPool {
  // Quietly prevents identical entries:
  private Set<Robot> pool = new HashSet<Robot>();
  public synchronized void add(Robot r) {
    pool.add(r);
    notifyAll();
  }
  public synchronized void
  hire(Class<? extends Robot> robotType, Assembler d)
  throws InterruptedException {
    for(Robot r : pool)
      if(r.getClass().equals(robotType)) {
        pool.remove(r);
        r.assignAssembler(d);
        r.engage(); // Power it up to do the task
        return;
      }
    wait(); // None available
    hire(robotType, d); // Try again, recursively
  }
  public synchronized void release(Robot r) { add(r); }
}

public class CarBuilder {
  public static void main(String[] args) throws Exception {
    CarQueue chassisQueue = new CarQueue(),
             finishingQueue = new CarQueue();
    ExecutorService exec = Executors.newCachedThreadPool();
    RobotPool robotPool = new RobotPool();
    exec.execute(new EngineRobot(robotPool));
    exec.execute(new DriveTrainRobot(robotPool));
    exec.execute(new WheelRobot(robotPool));
    exec.execute(new Assembler(
      chassisQueue, finishingQueue, robotPool));
    exec.execute(new Reporter(finishingQueue));
    // Start everything running by producing chassis:
    exec.execute(new ChassisBuilder(chassisQueue));
    TimeUnit.SECONDS.sleep(7);
    exec.shutdownNow();
  }
} /* (Execute to see output) *///:~
/**
 * Car是经由CarQueue从一个地方传送到另一个地方的，CarQueue是一种LinkedBlocking- Queue类型。ChassisBuilder创建了一个未加修饰的Car，并将它放到了一个CarQueue中。
 * Assembler从一个CarQueue中取走Car，并雇请Robot对其加工。CyclicBarrier使Assembler等待，直至所有的Robot都完成，并且在那一时刻它会将Car放置到即将离开它的CarOueue中，
 * 然后被传送到下一个操作。最终的CarQueue的消费者是一个Reporter对象，它只是打印Car，以显示所有的任务都已经正确的完成了。
 *
 * Robot是在池中管理的，当需要完成工作时，就会从池中雇请适当的Robot。在工作完成时，这个Robot会返回到池中。
 *
 * 在mainO）中创建了所有必需的对象，并初始化了各个任务，最后启动ChassisBuilder，从而启动整个过程（但是，由于LinkedBlockingQueue的行为，使得最先启动它也没有问题）。
 * 注意，这个程序遵循了本章描述的所有有关对象和任务生命周期的设计原则，因此关闭这个过程将是安全的。
 *
 * 你会注意到，Car将其所有方法都设置成了synchronized的。正如它所表现出来的那样，在本例中，这是多余的，因为在工厂的内部，Car是通过队列移动的，并且在任何时刻，只有一个任务能够在某辆车上工作。
 * 基本上，队列可以强制串行化地访问Car。但是这正是你可能会落入的陷阱-——你可能会说"让我们尝试着通过不对Car类同步来进行优化，因为看起来Car在这里并不需要同步。"但是稍后，当这个系统连接到另—个需要Car被同步的系统时，它就会崩溃。
 *
 * Brian Goetz的注释∶
 *      进行这样的声明会简单得多∶"Car可能会被多个线程使用，因此我们需要以明显的方式使其成为线程安全的。"我把这种方式描绘为;在公园中。你会在陡峭的披路上发现-—些保护围栏.
 *      并且可能会发现标记声明∶ "不要倚靠围栏。" 当然，这条规则的真实目的不是要阻止你借助围栏，而是防止你跌落悬崖。但是"不要倚靠围栏"与"不要跌落悬崖"相比，是一条遵循起来，要容易得多的规则。
 *
 * 练习37∶（2）修改CarBuilder.java，在汽车构建过程中添加一个阶段，即添加排气系统、车身和保险杠。与第二个阶段相同，假设这些处理可以由机器人同时执行。
 * 练习38∶（3） 使用CarBuilderjava中的方式，对本章中给出的房屋构建过程建模。
 */



























