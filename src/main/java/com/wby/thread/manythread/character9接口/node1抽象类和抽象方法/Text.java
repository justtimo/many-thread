package com.wby.thread.manythread.character9接口.node1抽象类和抽象方法;

import com.wby.thread.manythread.character21并发.node9性能调优.child2免锁容器.Tester;
import com.wby.thread.manythread.net.mindview.util.CountingGenerator;
import com.wby.thread.manythread.net.mindview.util.MapData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在第8章所有"乐器"的例子中，基类Instrument中的方法往往是"哑"（dummy）方法。若要调用这些方法，就会出现一些错误。这是因为Instrument类的目的是为它的所有导出类创建一个通用接口。
 *
 * 在那些示例中。建立这个通用接口的唯—理由是。不同的子类可以用不同的方式表示出此接口。通用接口建立起一种基本形式，以此表示所有导出类的共同部分。另一种说法是将 Instrument类称作抽象基类，或简称抽象类。
 *
 * 如果我们只有一个像Instrument这样的抽象类，那么该类的对象几乎没有任何意义。我们创建抽象类是希望通过这个通用接口操纵一系列类。因此，Instrument只是表示了一个接口，
 *
 * 没有具体的实现内容;因此，创建一个Instrument对象没有什么意义，并且我们可能还想阻止使用者这样做。通过让Instrument中的所有方都产生错误，就可以实现这个目的。
 * 但是这样做会将错误信息延迟到运行时才获得，并且需要在客户端进行可靠、详尽的测试。所以最好是在编译时捕获这些问题。
 *
 * 为此。Java提供—个叫做抽象方法9的机制，这种方法是不完整的，仅有声明而没有方法体下面是抽象方法声明所采用的语法∶
 *      abstract void f();
 *
 * 句含抽象方法的类叫做抽象类。如果—个类包含一个或多个抽象方法，该类必须被限定为抽象的。（否则，编译器就会报错。）
 *
 * 如果一个抽象类不完整，那么当我们试图产生该类的对象时，编译器会怎样处理呢?由于为抽象类创建对象是不安全的，所以我们会从编译器那里得到一条出错消息。这样，编译器会确保抽象类的纯粹性，我们不必担心会误用它。
 *
 * 如果从一个抽象类继承，并想创建该新类的对象，那么就必须为基类中的所有抽象方法提供方法定义。如果不这样做（可以选择不做），那么导出类便也是抽象类，且编译器将会强制我们用abstract关键字来限定这个类。
 *
 * 我们也可能会创建一个没有任何抽象方法的抽象类。考虑这种情况;如果有一个类，让其包含任何abstract方法都显得没有实际意义，而且我们也想要阻止产生这个类的任何对象，那么这时这样做就很有用了。
 *
 * 第8章Instrument类可以很容易地转化成abstract类。既然使某个类成为抽象类并不需要所有的方法都是抽象的，所以仅需将某些方法声明为抽象的即可。如下图所示∶
 * 下面是修改过的"管弦乐器"的例子，其中采用了抽象类和抽象方法∶
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
 * 我们可以看出，除了基类，实际上并没有什么改变。
 * 创建抽象类和抽象方法非常有用，因为它们可以使类的抽象性明确起来，并告诉用户和编译器打算怎样来使用它们。抽象类还是很有用的重构工具，因为它们使得我们可以很容易地将公共方法沿着继承层次结构向上移动。
 */
public class Text {
}
