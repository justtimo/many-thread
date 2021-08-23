package com.wby.thread.manythread.character21并发.node9性能调优.child2免锁容器;//: concurrency/Tester.java
// Framework to test performance of concurrency containers.

import com.wby.thread.manythread.net.mindview.util.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * 就像在第11章中所强调的，容器是所有编程中的基础工具，这其中自然也包括并发编程。出于这个原因，像Vector和Hashtable这类早期容器具有许多synchronized方法，
 * 当它们用于非多线程的应用程序中时，便会导致不可接受的开销。在Java1.2中，新的容器类库是不同步的，并且 Collections类提供t了各种static的同步的装饰方法。
 * 从而来同步不同类型的容器。尽管这是—种改进，因为它使你可以选择在你的容器中是否要使用同步，但是这种开销仍旧是基于synchronized加锁机制的。
 * Java SE5特别添加了新的容器，通过使用更灵巧的技术来消除加锁，从而提高线程安全的性能。
 *
 * 这些免锁容器背后的通用策略是;对容器的修改可以与读取操作同时发生，只要读取者只能看到完成修改的结果即可。修改是在容器数据结构的某个部分的一个单独的副本（有时是整个数据结构的副本）上执行的，
 * 并且这个副本在修改过程中是不可视的。只有当修改完成时，被修改的结构才会自动地与主数据结构进行交换，之后读取者就可以看到这个修改了。
 *
 * 在CopyOnWriteArrayList中，写入将导致创建整个底层数组的副本，而源数组将保留在原地，使得复制的数组在被修改时，读取操作可以安全地执行。 当修改完成时，—个原子性的操作将把新的数组换入，
 * 使得新的读取操作可以看到这个新的修改。CopyOnWriteArrayList的好处之一是当多个迭代器同时遍历和修改这个列表时，不会抛出ConcurrentModificationException，因此你不必编写特殊的代码去防范这种异常，就像你以前必须作的那样。
 *
 * CopyOnWriteArraySet将使用CopyOnWriteArrayList来实现其免锁行为。
 *
 * ConcurrentHashMap和ConcurrentLinkedQueue使用了类似的技术，允许并发的读取和写入，但是容器中只有部分内容而不是整个容器可以被复制和修改。
 * 然而，任何修改在完成之前，读取者仍旧不能看到它们。ConcurrentHashMap不会抛出ConcurrentModificationException异常。
 *
 * 乐观锁
 * 只要你主要是从免锁容器中读取，那么它就会比其synchronized对应物快许多，因为获取和释放锁的开销被省掉了。如果需要向免锁容器中执行少量写入，那么情况仍旧如此，但是什么算"少量"?这是一个很有意思的问题。
 * 本节将介绍有关在各种不同条件下，这些容器在性能方面差异的大致概念。
 *
 * 我将从一个泛型框架着手，它专门用于在任何类型的容器上执行测试，包括各种Map在内，其中泛型参数C表示容器的类型∶
 */
public abstract class Tester<C> {
  static int testReps = 10;
  static int testCycles = 1000;
  static int containerSize = 1000;
  abstract C containerInitializer();
  abstract void startReadersAndWriters();
  C testContainer;
  String testId;
  int nReaders;
  int nWriters;
  volatile long readResult = 0;
  volatile long readTime = 0;
  volatile long writeTime = 0;
  CountDownLatch endLatch;
  static ExecutorService exec =
    Executors.newCachedThreadPool();
  Integer[] writeData;
  Tester(String testId, int nReaders, int nWriters) {
    this.testId = testId + " " +
      nReaders + "r " + nWriters + "w";
    this.nReaders = nReaders;
    this.nWriters = nWriters;
    writeData = Generated.array(Integer.class,
      new RandomGenerator.Integer(), containerSize);
    for(int i = 0; i < testReps; i++) {
      runTest();
      readTime = 0;
      writeTime = 0;
    }
  }
  void runTest() {
    endLatch = new CountDownLatch(nReaders + nWriters);
    testContainer = containerInitializer();
    startReadersAndWriters();
    try {
      endLatch.await();
    } catch(InterruptedException ex) {
      System.out.println("endLatch interrupted");
    }
    System.out.printf("%-27s %14d %14d\n",
      testId, readTime, writeTime);
    if(readTime != 0 && writeTime != 0)
      System.out.printf("%-27s %14d\n",
        "readTime + writeTime =", readTime + writeTime);
  }
  abstract class TestTask implements Runnable {
    abstract void test();
    abstract void putResults();
    long duration;
    public void run() {
      long startTime = System.nanoTime();
      test();
      duration = System.nanoTime() - startTime;
      synchronized(Tester.this) {
        putResults();
      }
      endLatch.countDown();
    }
  }
  public static void initMain(String[] args) {
    if(args.length > 0)
      testReps = new Integer(args[0]);
    if(args.length > 1)
      testCycles = new Integer(args[1]);
    if(args.length > 2)
      containerSize = new Integer(args[2]);
    System.out.printf("%-27s %14s %14s\n",
      "Type", "Read time", "Write time");
  }
} ///:~
/**
 * abstract方法containerInitializerO返回将被测试的初始化后的容器，它被存储在testContainer域中。另一个abstract方法startReadersAndWritersO启动读取者和写入者任务，它们将读取和修改待测容器。
 * 不同的测试在运行时将具有数量变化的读取者和写入者，这样就可以观察到锁竞争（针对synchronized容器而言）和写入（针对免锁容器而言）的效果。
 *
 * 我们向构造器提供了各种有关测试的信息（参数标识符应该是自解释的），然后它会调用 runTestO方法repetitions次。runTestO）将创建一个CountDownLatch（因此测试可以知道所有任何何时完成）、初始化容器，
 * 然后调用startReadersAndWritersO，并等待它们全部完成。
 *
 * 每个Reader和Writer类都基于TestTask，它可以度量其抽象方法testO的执行时间，然后在—个synchronized块中调用putResults（去存储度量结果。
 *
 * 为了使用这个框架（其中你可以识别出模版方法设计模式），我们必须让想要测试的特定类型的容器继承Tester，并提供适合的Reader和Writer类∶
 */
abstract class ListTest extends Tester<List<Integer>> {
  ListTest(String testId, int nReaders, int nWriters) {
    super(testId, nReaders, nWriters);
  }
  class Reader extends TestTask {
    long result = 0;
    void test() {
      for(long i = 0; i < testCycles; i++)
        for(int index = 0; index < containerSize; index++)
          result += testContainer.get(index);
    }
    void putResults() {
      readResult += result;
      readTime += duration;
    }
  }
  class Writer extends TestTask {
    void test() {
      for(long i = 0; i < testCycles; i++)
        for(int index = 0; index < containerSize; index++)
          testContainer.set(index, writeData[index]);
    }
    void putResults() {
      writeTime += duration;
    }
  }
  void startReadersAndWriters() {
    for(int i = 0; i < nReaders; i++)
      exec.execute(new Reader());
    for(int i = 0; i < nWriters; i++)
      exec.execute(new Writer());
  }
}

class SynchronizedArrayListTest extends ListTest {
  List<Integer> containerInitializer() {
    return Collections.synchronizedList(
            new ArrayList<Integer>(
                    new CountingIntegerList(containerSize)));
  }
  SynchronizedArrayListTest(int nReaders, int nWriters) {
    super("Synched ArrayList", nReaders, nWriters);
  }
}

class CopyOnWriteArrayListTest extends ListTest {
  List<Integer> containerInitializer() {
    return new CopyOnWriteArrayList<Integer>(
            new CountingIntegerList(containerSize));
  }
  CopyOnWriteArrayListTest(int nReaders, int nWriters) {
    super("CopyOnWriteArrayList", nReaders, nWriters);
  }
}

class ListComparisons {
  public static void main(String[] args) {
    Tester.initMain(args);
    new SynchronizedArrayListTest(10, 0);
    new SynchronizedArrayListTest(9, 1);
    new SynchronizedArrayListTest(5, 5);
    new CopyOnWriteArrayListTest(10, 0);
    new CopyOnWriteArrayListTest(9, 1);
    new CopyOnWriteArrayListTest(5, 5);
    Tester.exec.shutdown();
  }
} /* Output: (Sample)
Type                             Read time     Write time
Synched ArrayList 10r 0w      232158294700              0
Synched ArrayList 9r 1w       198947618203    24918613399
readTime + writeTime =        223866231602
Synched ArrayList 5r 5w       117367305062   132176613508
readTime + writeTime =        249543918570
CopyOnWriteArrayList 10r 0w      758386889              0
CopyOnWriteArrayList 9r 1w       741305671      136145237
readTime + writeTime =           877450908
CopyOnWriteArrayList 5r 5w       212763075    67967464300
readTime + writeTime =         68180227375
*///:~
/**
 * 在ListTest中，Reader和Writer类执行针对List<Integer>的具体动作。在Reader.putResults（）中，duration被存储来起来，result也是一样，这样可以防止这些计算被优化掉。
 * startReaders- AndWritersO被定义为创建和执行具体的Readers和Writers。
 *
 * 一旦创建了ListTest，它就必须被进一步继承，以覆盖containerInitializerO），从而可以创建和初始化具体的测试容器。
 *
 * 在main（中，你可以看到各种测试变体，它们具有不同数量的读取者和写入者。由于存在对 Tester.initMain（args）的调用，所以你可以使用命令行参数来改变测试变量。
 *
 * 默认行是为每个测试运行10次，这有助于稳定输出，而输出是可以变化的，因为存在着诸如 hotspot优化和垃圾回收这样的JVM活动e。你看到的样本输出已经被编辑为只显示每个测试的最后—个迭代。
 * 从输出中可以看到，synchronized ArrayList无论读取者和写入者的数量是多少，都具有大致相同的性能——读取者与其他读取者竞争锁的方式与写入者相同。
 * 但是，CopyOn- WriteArrayList在没有写入者时，速度会快许多，并且在有5个写入者时，速度仍旧明显地快。看起来你应该尽量使用CopyOnWriteArrayList，对列表写入的影响并没有超过短期同步整个列表的影响。
 * 当然，你必须在你的具体应用中尝试这两种不同的方式，以了解到底哪个更好一些。
 *
 * 再次注意，这还不是测试结果绝对不变的良好的基准测试，你的结果几乎肯定是不同的。这里的目标只是让你对两种不同类型的容器的相对行为有个概念上的认识。
 *
 * 因为CopyOnWriteArraySet使用了CopyOnWriteArrayList，所以它的行为与此类似，在这里就不需要另外设计一个单独的测试了。
 *
 * 比较各种Map实现
 * 我们可以使用相同的框架来得到synchronizedHashMap和ConcurrentHashMap在性能方面的比较结果∶
 */
abstract class MapTest
        extends Tester<Map<Integer,Integer>> {
  MapTest(String testId, int nReaders, int nWriters) {
    super(testId, nReaders, nWriters);
  }
  class Reader extends TestTask {
    long result = 0;
    void test() {
      for(long i = 0; i < testCycles; i++)
        for(int index = 0; index < containerSize; index++)
          result += testContainer.get(index);
    }
    void putResults() {
      readResult += result;
      readTime += duration;
    }
  }
  class Writer extends TestTask {
    void test() {
      for(long i = 0; i < testCycles; i++)
        for(int index = 0; index < containerSize; index++)
          testContainer.put(index, writeData[index]);
    }
    void putResults() {
      writeTime += duration;
    }
  }
  void startReadersAndWriters() {
    for(int i = 0; i < nReaders; i++)
      exec.execute(new Reader());
    for(int i = 0; i < nWriters; i++)
      exec.execute(new Writer());
  }
}

class SynchronizedHashMapTest extends MapTest {
  Map<Integer,Integer> containerInitializer() {
    return Collections.synchronizedMap(
            new HashMap<Integer,Integer>(
                    MapData.map(
                            new CountingGenerator.Integer(),
                            new CountingGenerator.Integer(),
                            containerSize)));
  }
  SynchronizedHashMapTest(int nReaders, int nWriters) {
    super("Synched HashMap", nReaders, nWriters);
  }
}

class ConcurrentHashMapTest extends MapTest {
  Map<Integer,Integer> containerInitializer() {
    return new ConcurrentHashMap<Integer,Integer>(
            MapData.map(
                    new CountingGenerator.Integer(),
                    new CountingGenerator.Integer(), containerSize));
  }
  ConcurrentHashMapTest(int nReaders, int nWriters) {
    super("ConcurrentHashMap", nReaders, nWriters);
  }
}

class MapComparisons {
  public static void main(String[] args) {
    Tester.initMain(args);
    new SynchronizedHashMapTest(10, 0);
    new SynchronizedHashMapTest(9, 1);
    new SynchronizedHashMapTest(5, 5);
    new ConcurrentHashMapTest(10, 0);
    new ConcurrentHashMapTest(9, 1);
    new ConcurrentHashMapTest(5, 5);
    Tester.exec.shutdown();
  }
} /* Output: (Sample)
Type                             Read time     Write time
Synched HashMap 10r 0w        306052025049              0
Synched HashMap 9r 1w         428319156207    47697347568
readTime + writeTime =        476016503775
Synched HashMap 5r 5w         243956877760   244012003202
readTime + writeTime =        487968880962
ConcurrentHashMap 10r 0w       23352654318              0
ConcurrentHashMap 9r 1w        18833089400     1541853224
readTime + writeTime =         20374942624
ConcurrentHashMap 5r 5w        12037625732    11850489099
readTime + writeTime =         23888114831
*///:~
/**
 * 向ConcurrentHashMap添加写入者的影响甚至还不如CopyOnWriteArrayList明显，这是因为ConcurrentHashMap使用了一种不同的技术，它可以明显地最小化写入所造成的影响。
 */


























