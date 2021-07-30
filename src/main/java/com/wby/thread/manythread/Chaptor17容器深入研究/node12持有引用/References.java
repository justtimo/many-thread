package com.wby.thread.manythread.Chaptor17容器深入研究.node12持有引用;//: containers/References.java
// Demonstrates Reference objects

import java.lang.ref.*;
import java.util.LinkedList;

/**
* @Description: iava.lang，ref类库包含了一组类，这些类为垃圾回收提供了更大的灵活性。当存在可能会耗尽内存的大对象的时候，这些类显得特别有用。
 * 有三个继承自抽象类Reference的类∶ SoftReference、WeakReference和PhantomReference。当垃圾回收器正在考察的对象只能通过某个Reference对象才"可获得"时，
 * 上述这些不同的派生类为垃圾回收器提供了不同级别的间接性指示。
 *
 * 对象是可获得的（reachable），是指此对象可在程序中的某处找到。这意味着你在栈中有一个普通的引用，而它正指向此对象;也可能是你的引用指向某个对象，而那个对象含有另一个引用指向正在讨论的对象;
 * 也可能有更多的中间链接。如果一个对象是"可获得的"，垃圾回收器就不能释放它，因为它仍然为你的程序所用。如果一个对象不是"可获得的"，那么你的程序将无法使用到它，所以将其回收是安全的。
 *
 * 如果想继续持有对某个对象的引【用，希望以后还能够访问到该对象。但是也，希望能够允许垃圾回收器释放它，这时就应该使用Reference对象。这样，你可以继续使用该对象，而在内存消耗殆尽的时候又允许释放该对象。
 *
 * 以Reference对象作为你和普通引用之间的媒介（代理），另外，一定不能有普通的引用指向那个对象，这样就能达到上述目的。（普通的引用指没有经Reference对象包装过的引用。）
 * 如果垃圾回收器发现某个对象通过普通引用是可获得的，该对象就不会被释放。
 *
 * SoftReference、WeakReference和PhantomReference由强到弱排列，对应不同级别的"可获得性"。
 * Softreference用以实现内存敏感的高速缓存。
 * Weak reference是为实现"规范映射"（canonicalizing mappings）而设计的，它不妨碍坟圾回收器回收映射的"键"（或"值"）。"规范映射"中对象的实例可以在程序的多处被同时使用，以节省存储空间。
 * Phantomreference用以调度回收前的清理工作，它比Java终止机制更灵活。
 *
 * 使用SoftReference和WeakReference时，可以选择是否要将它们放入ReferenceOueue（用作"回收前清理工作"的工具）。而PhantomReference只能依赖于ReferenceQueue。下面是一个简单的示例∶
*/
class VeryBig {
  private static final int SIZE = 10000;
  private long[] la = new long[SIZE];
  private String ident;
  public VeryBig(String id) { ident = id; }
  public String toString() { return ident; }
  protected void finalize() {
    System.out.println("Finalizing " + ident);
  }
}

public class References {
  private static ReferenceQueue<VeryBig> rq =
    new ReferenceQueue<VeryBig>();
  public static void checkQueue() {
    Reference<? extends VeryBig> inq = rq.poll();
    if(inq != null)
      System.out.println("In queue: " + inq.get());
  }
  public static void main(String[] args) {
    int size = 10;
    // Or, choose size via the command line:
    if(args.length > 0)
      size = new Integer(args[0]);
    LinkedList<SoftReference<VeryBig>> sa =
      new LinkedList<SoftReference<VeryBig>>();
    for(int i = 0; i < size; i++) {
      sa.add(new SoftReference<VeryBig>(
        new VeryBig("Soft " + i), rq));
      System.out.println("Just created: " + sa.getLast());
      checkQueue();
    }
    LinkedList<WeakReference<VeryBig>> wa =
      new LinkedList<WeakReference<VeryBig>>();
    for(int i = 0; i < size; i++) {
      wa.add(new WeakReference<VeryBig>(
        new VeryBig("Weak " + i), rq));
      System.out.println("Just created: " + wa.getLast());
      checkQueue();
    }
    SoftReference<VeryBig> s =
      new SoftReference<VeryBig>(new VeryBig("Soft"));
    WeakReference<VeryBig> w =
      new WeakReference<VeryBig>(new VeryBig("Weak"));
    System.gc();
    LinkedList<PhantomReference<VeryBig>> pa =
      new LinkedList<PhantomReference<VeryBig>>();
    for(int i = 0; i < size; i++) {
      pa.add(new PhantomReference<VeryBig>(
        new VeryBig("Phantom " + i), rq));
      System.out.println("Just created: " + pa.getLast());
      checkQueue();
    }
  }
} /* (Execute to see output) *///:~
/**
* @Description: 运行此程序可以看到（将输出重定向到一个文本文件中，便可以查看分页的输出），尽管还要通过Reference对象访问那些对象（使用getO）取得实际的对象引用），
 * 但对象还是被垃圾回收器回收了。还可以看到，ReferenceQueue总是生成一个包含null对象的Reference。要利用此机制，可以继承特定的Reference类，然后为这个新类添加一些更有用的方法。
*/
