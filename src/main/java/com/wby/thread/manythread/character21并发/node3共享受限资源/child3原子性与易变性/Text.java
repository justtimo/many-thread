package com.wby.thread.manythread.character21并发.node3共享受限资源.child3原子性与易变性;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 在有关Java线程的讨论中，一个常不正确的知识是"原子操作不需要进行同步控制"。原子操作是不能被线程调度机制中断的操作;一旦操作开始，那么它一定可以在可能发生的"上下文切换"之前（切换到其他线程执行）执行完毕。
 * 依赖于原子性是很棘手且很危险的，如果你是一个并发专家，或者你得到了来自这样的专家的帮助，你才应该使用原子性来代替同步。如果你认为自己足够聪明可以应付这种玩火似的情况，那么请接受下面的测试;
 *      Goetz测试;如果你可以编写用于现代微处理器的高性能JVM，那么就有资格去考虑是否可以避免同步e。
 *
 * 了解原子性是很有用的，并且要知道原子性与其他高级技术一道，在java.util.concurrent类库中已经实现了某些更加巧妙的构件。但是要坚决抵挡住完全依赖自己的能力去进行处理的这种欲望，请看看之前表述的Brian的同步规则。
 *
 * 原子性可以应用于除long和double之外的所有基本类型之上的"简单操作"。对于读取和写入除long和double之外的基本类型变量这样的操作，可以保证它们会被当作不可分（原子）的操作来操作内存。
 * 但是JVM可以将64位（long和double变量）的读取和写入当作两个分离的32位操作来执行，这就产生了在一个读取和写入操作中间发生上下文切换，从而导致不同的任务可以看到不正确结果的可能性（这有时被称为字撕裂，因为你可能会看到部分被修改过的数值）。
 * 但是，当你定义long或double变量时，如果使用volatile关键字，就会获得（简单的赋值与返回操作的） 原子性（注意，在Java SE5之前，volatile-直未能正确地工作）。不同的JVM可以任意地提供更强的保证，但是你不应该依赖于平台相关的特性。
 *
 * 因此，原子操作可由线程机制来保证其不可中断，专家级的程序员可以利用这一点来编写无锁的代码，这些代码不需要被同步。但是即便是这样，它也是一种过于简化的机制。有时，甚至看起来应该是安全的原子操作，实际上也可能不安全。
 * 本书的读者通常不能通过前面提及的Goetz测试，因此也就不具备用原子操作来替换同步的能力。尝试着移除同步通常是一种表示不成熟优化的信号，并且将会给你招致大量的麻烦，而你却可能没有收获多少好处，甚至压根没有任何好处。
 *
 * 在多处理器系统（现在以 多核处理器的形式出现。即在单个芯片上有多个CPU）上。相对于单处理器系统而言，可视性问题远比原子性问题多得多。一个任务做出的修改，即使在不中断的意义上讲是原子性的，
 * 对其他任务也可能是不可视的（例如，修改只是暂时性地存储在本地处理器的缓存中），因此不同的任务对应用的状态有不同的视图。另一方面，同步机制强制在处理器系统中，一个任务做出的修改必须在应用中是可视的。
 * 如果没有同步机制，那么修改时可视将无法确定。
 *
 * volatile关键字还确保了应用中的可视性。如果你将一个域声明为volatile的，那么只要对这个域产生了写操作，那么所有的读操作就都可以看到这个修改。
 * 即便使用了本地缓存，情况也确实如此，volatile域会立即被写入到主存中，而读取操作就发生在主存中。
 *
 * 理解原子性和易变性是不同的概念这一点很重要。在非volatile域 上的原子操作不必刷新到主存中去，因此其他读取该域的任务也不必看到这个新值。如果多个任务在同时访问某个域，
 * 那么这个域就应该是volatile的。否则。这个域就应该只能经由同步来访问。同步也会导致向主存中刷新，因此如果一个域完全由synchronized方法或语句块来防护，那就不必将其设置为是 volatile的约。
 *
 * 一个任务所作的任何写入操作对这个任务来说都是可视的，因此如果它只需要在这个任务内部可视，那么你就不需要将其设置为volatile的。
 *
 * 当一个域的值依赖于它之前的值时（例如递增一个计数器），volatile就无法工作了。如果某个域的值受到其他域的值的限制，那么volatile也无法工作，例如Range类的lower和upper边界就必须遵循lower<=upper的限制。
 *
 * 使用volatile而不是synchronized的唯一安全的情况是类中只有一个可变的域。
 * 再次提醒，你的第一选择应该是使用synchronized关键字，这是最安全的方式，而尝试其他任何方式都是有风险的。
 *
 * 什么才属于原子操作呢?对域中的值做赋值和返回操作通常都是原子性的，但是，在C++中，甚至下面的操作都可能是原子性的∶
 *      i++;    // Might be atomic in C+* /
 *      i += 2;.// Might be atomic in C++
 * 但是在C++中，这要取决于编译器和处理器。你无法编写出依赖于原子性的C++跨平台代码，因为C++没有像Java（在Java SE5中）那样一致的内存模型9。
 * 在Java中，上面的操作肯定不是原子性的，正如从下面的方法所产生的JVM指令中可以看到的那样∶
 */
class Atomicity {
    int i;
    void f1() { i++; }
    void f2() { i += 3; }
} /* Output: (Sample)
...
void f1();
  Code:
   0:        aload_0
   1:        dup
   2:        getfield        #2; //Field i:I
   5:        iconst_1
   6:        iadd
   7:        putfield        #2; //Field i:I
   10:        return

void f2();
  Code:
   0:        aload_0
   1:        dup
   2:        getfield        #2; //Field i:I
   5:        iconst_3
   6:        iadd
   7:        putfield        #2; //Field i:I
   10:        return
*///:~

/**
 * 每条指令都会产生一个get和put，它们之间还有一些其他的指令。因此在获取和放置之间，另一个任务可能会修改这个域，所以，这些操作不是原子性的;
 * 如果你盲目地应用原子性概念，那么就会看到在下面程序中的getValueO符合上面的描述;
 */
class AtomicityTest implements Runnable {
    private int i = 0;
    public int getValue() { return i; }
    private synchronized void evenIncrement() { i++; i++; }
    public void run() {
        while(true)
            evenIncrement();
    }
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicityTest at = new AtomicityTest();
        exec.execute(at);
        while(true) {
            int val = at.getValue();
            if(val % 2 != 0) {
                System.out.println(val);
                System.exit(0);
            }
        }
    }
} /* Output: (Sample)
191583767
*///:~

/**
 * 但是，该程序将找到奇数值并终止。尽管return i确实是原子性操作，但是缺少同步使得其数值可以在处于不稳定的中间状态时被读取。除此之外，由于i也不是volatile的，因此还存在可视性问题。
 * getValueO和evenIncrementO必须是synchronized的。在诸如此类情况下，只有并发专家才有能力进行优化，而你还是应该运用Brian的同步规则。
 *
 * 正如第二个示例，考虑一些更简单的事情∶一个产生序列数字的类9。每当nextSerial- Number（被调用时，它必须向调用者返回唯一的值;
 */
class SerialNumberGenerator {
    private static volatile int serialNumber = 0;
    public static int nextSerialNumber() {
        return serialNumber++; // Not thread-safe
    }
} ///:~

/**
 * SerialNumberGenerator与你想象的一样简单，如果你有C++或其他低层语言的背景，那么可能会期望递增是原子性操作，因为C++递增通常可以作为一条微处理器指令来实现（尽管不是以任何可靠的、跨平台的形式实现）。
 * 然而正如前面注意到的，Java递增操作不是原子性的，并且涉及一个读操作和一个写操作，所以即便是在这么简单的操作中，也为产生线程问题留下了空间。正如你所看到的，易变性在文里实际 上不是什么问题。
 * 真正的问题在干nextSerial- Number（）在没有同步的情况下对共享可变值进行了访问。
 *
 * 基本上，如果一个域可能会被多个任务同时访问，或者这些任务中至少有一个是写入任务，那么你就应该将这个域设置为volatile的。如果你将一个域定义为volatile，
 * 那么它就会告诉编译器不要执行任何移除读取和写入操作的优化，这些操作的目的是用线程中的局部变量维护对这个域的精确同步。实际上，读取和写入都是直接针对内存的，而却没有被缓存。
 * 但是，yolatile并不能对递增不是原子性操作这一事实产生影响。
 *
 * 为了测试SerialNumberGenerator，我们需要不会耗尽内存的集（Set），以防需要花费很长的时间来探测问题。这里所示的CircularSet重用了存储int数值的内存，并假设在你生成序列数时，
 * 产生数值覆盖冲突的可能性极小。addO和containsO方法都是synchronized，以防止线程冲突;
 */
// Reuses storage so we don't run out of memory:
class CircularSet {
    private int[] array;
    private int len;
    private int index = 0;
    public CircularSet(int size) {
        array = new int[size];
        len = size;
        // Initialize to a value not produced
        // by the SerialNumberGenerator:
        for(int i = 0; i < size; i++)
            array[i] = -1;
    }
    public synchronized void add(int i) {
        array[index] = i;
        // Wrap index and write over old elements:
        index = ++index % len;
    }
    public synchronized boolean contains(int val) {
        for(int i = 0; i < len; i++)
            if(array[i] == val) return true;
        return false;
    }
}

class SerialNumberChecker {
    private static final int SIZE = 10;
    private static CircularSet serials =
            new CircularSet(1000);
    private static ExecutorService exec =
            Executors.newCachedThreadPool();
    static class SerialChecker implements Runnable {
        public void run() {
            while(true) {
                int serial =
                        SerialNumberGenerator.nextSerialNumber();
                if(serials.contains(serial)) {
                    System.out.println("Duplicate: " + serial);
                    System.exit(0);
                }
                serials.add(serial);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        for(int i = 0; i < SIZE; i++)
            exec.execute(new SerialChecker());
        // Stop after n seconds if there's an argument:
        if(args.length > 0) {
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
            System.out.println("No duplicates detected");
            System.exit(0);
        }
    }
} /* Output: (Sample)
Duplicate: 8468656
*///:~

/**
 * SerialNumberChecker包含一个静态的CircularSet，它持有所产生的所有序列数;另外还包含一个内嵌的SerialChecker类，它可以确保序列数是唯—的。
 * 通过创建多个任务来竞争序列数，你将发现这些任务最终会得到重复的序列数，如果你运行的时间足够长的话。为了解决这个问题，在nextSeriaNumberO前面添加了synchronized关键字。
 *
 * 对基 本类 型的读取和赋值操作被认为是安全的原子性操作。 但是，正 如你在 AtomicityTest.java中看到的，当对象处于不稳定状态时，仍旧很有可能使用原子性操作来访问它们。
 * 对这个问题做出假设是棘手而危险的，最明智的做法就是遵循Brian的同步规则】。
 */






public class Text {
}
