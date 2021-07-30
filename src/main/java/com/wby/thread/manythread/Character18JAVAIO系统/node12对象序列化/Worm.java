package com.wby.thread.manythread.Character18JAVAIO系统.node12对象序列化;//: io/Worm.java
// Demonstrates object serialization.

import java.io.*;
import java.util.Random;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
/**
 * 当你创建对象时，只要你需要，它就会一直存在，但是在程序终止时，无论如何它都不会继续存在。尽管这么做肯定是有意义的。但是仍旧存在某些情况。如果对象能修在程序不运行的情况下仍能存在并保存其信息，
 * 那将非常有用。这样，在下次运行程序时，该对象将被重建并目拥有的信息与在程序上次运行时它所拥有的信息相同。当然。你可以通过将信息写入文件或数据库来达到相同的效果，
 * 但是在使万物都成为对象的精神中，如果能够将一个对象声明为是"持久性"的，并为我们处理掉所有细节，那将会显得十分方便。
 *
 * Java的对象序列化将那些实现了Serializable接口的对象转换成一个字节序列，并能够在以后将这个字节序列完全恢复为原来的对象。这一过程甚至可通过网络进行;
 * 这意味着序列化机制能自动弥补不同操作系统之间的差异。也就是说，可以在运行Windows系统的计算机上创建一个对象，将其序列化，通过网络将它发送给一台运行Unix系统的计算机，
 * 然后在那里准确地重新组装，而却不必担心数据在不同机器上的表示会不同，也不必关心字节的顺序或者其他任何细节。
 *
 * 就其本身来说，对象的序列化是非常有趣的，因为利用它可以实现轻量 级持 久 性（lightweight persistence）。"持久性"意味着一个对象的生存周期并不取决于程序是否正在执行;
 * 它可以生存于程序的调用之间。通过将一个序列化对象写入磁盘，然后在重新调用程序时恢复该对象，就能够实现持久性的效果。之所以称其为"轻量级"，是因为不能用某种"persistent"（持久）关键字来简单地定义一个对象，
 * 并让系统自动维护其他细节问题（尽管将来有可能实现）。相反，对象必须在程序中显式地序列化（serialize）和反序列化还原（deserialize）。如果需要一个更严格的持久性机制，
 * 可以考虑像Hibernate之类的工具
 *
 * 对象序列化的概念加入到语言中是为了支持两种主要特性。一是Java的远程方法调用（Remote Method Invocation，RM），它使存活于其他计算机上的对象使用起来就像是存活于本机上一样。
 * 当向远程对象发送消息时，需要通过对象序列化来传输参数和返回值。在《Thinking in Enterprise Java》中有对RMI的具体讨论。
 *
 * 再者，对Java Beans来说，对象的序列化也是必需的（可参看第14章）。使用一个Bean时，一般情况下是在设计阶段对它的状态信息进行配置。这种状态信息必须保存下来，
 * 并在程序启动时进行后期恢复;这种具体工作就是由对象序列化完成的。
 *
 * 只要对象实现了Serializable接口（该接口仅是一个标记接口，不包括任何方法），对象的序列化处理就会非常简单。当序列化的概念被加入到语言中时，许多标准库类都发生了改变，
 * 以便具备序列化特性——其中包括所有基本数据类型的封装器、所有容器类以及许多其他的东西。甚至Class对象也可以被序列化。
 *
 * 要序列化一个对象，首先要创建某些OutputStream对象，然后将其封装在一个ObjectOutput- Stream对象内。这时，只需调用writeObjectO）即可将对象序列化，
 * 并将其发送给OutputStream（对象化序列是基于字节的，因要使用InputStream和OutputStream继承层次结构）。要反向进行该过程（即将一个序列还原为一个对象），
 * 需要将一个InputStream封装在ObjectInputStream内，然后调用readObjectO。和往常一样，我们最后获得的是一个引用，它指向一个向上转型的 Object，所以必须向下转型才能直接设置它们。
 *
 * 对象序列化特别"聪明"的一个地方是它不仅保存了对象的"全景图"，而且能追踪对象内所包含的所有引用，并保存那些对象;接着又能对对象内包含的每个这样的引用进行追踪;依此类推。
 * 这种情况有时被称为"对象网"，单个对象可与之建立连接，而且它还包含了对象的引用数组以及成员对象。如果必须保持一套自己的对象序列化机制，那么维护那些可追踪到所有
 * 链接的代码可能会显得非常麻烦。然而，由于Java的对象序列化似乎找不出什么缺点，所以请尽量不要自己动手，让它用优化的算法自动维护整个对象网。
 *
 * 下面这个例子通过对链接的对象生成一个worm（蠕虫）对序列化机制进行了测试。每个对象都与worm中的下一段链接，同时又与属于不同类（Data）的对象引用数组链接∶
 */
class Data implements Serializable {
  private int n;
  public Data(int n) { this.n = n; }
  public String toString() { return Integer.toString(n); }
}

public class Worm implements Serializable {
  private static Random rand = new Random(47);
  private Data[] d = {
    new Data(rand.nextInt(10)),
    new Data(rand.nextInt(10)),
    new Data(rand.nextInt(10))
  };
  private Worm next;
  private char c;
  // Value of i == number of segments
  public Worm(int i, char x) {
    print("Worm constructor: " + i);
    c = x;
    if(--i > 0)
      next = new Worm(i, (char)(x + 1));
  }
  public Worm() {
    print("Default constructor");
  }
  public String toString() {
    StringBuilder result = new StringBuilder(":");
    result.append(c);
    result.append("(");
    for(Data dat : d)
      result.append(dat);
    result.append(")");
    if(next != null)
      result.append(next);
    return result.toString();
  }
  public static void main(String[] args)
  throws ClassNotFoundException, IOException {
    Worm w = new Worm(6, 'a');
    print("w = " + w);
    ObjectOutputStream out = new ObjectOutputStream(
      new FileOutputStream("worm.out"));
    out.writeObject("Worm storage\n");
    out.writeObject(w);
    out.close(); // Also flushes output
    ObjectInputStream in = new ObjectInputStream(
      new FileInputStream("worm.out"));
    String s = (String)in.readObject();
    Worm w2 = (Worm)in.readObject();
    print(s + "w2 = " + w2);
    ByteArrayOutputStream bout =
      new ByteArrayOutputStream();
    ObjectOutputStream out2 = new ObjectOutputStream(bout);
    out2.writeObject("Worm storage\n");
    out2.writeObject(w);
    out2.flush();
    ObjectInputStream in2 = new ObjectInputStream(
      new ByteArrayInputStream(bout.toByteArray()));
    s = (String)in2.readObject();
    Worm w3 = (Worm)in2.readObject();
    print(s + "w3 = " + w3);
  }
} /* Output:
Worm constructor: 6
Worm constructor: 5
Worm constructor: 4
Worm constructor: 3
Worm constructor: 2
Worm constructor: 1
w = :a(853):b(119):c(802):d(788):e(199):f(881)
Worm storage
w2 = :a(853):b(119):c(802):d(788):e(199):f(881)
Worm storage
w3 = :a(853):b(119):c(802):d(788):e(199):f(881)
*///:~
/**
* 更有趣的是，Worm内的Data对象数组是用随机数初始化的（这样就不用怀疑编译器保留了某种原始信息）。每个Worm段都用一个char加以标记。该char是在递归生成链接的Worm列表时自动产生的。
 * 要创建一个Worm，必须告诉构造器你所希望的它的长度。在产生下一个引用时，要调用Worm构造器，并将长度减1，以此类推。最后一个next引用则为null（空），表示已到达 Worm的尾部。
 *
 * 以上这些操作都使得事情变得更加复杂，从而加大了对象序列化的难度。然而，真正的序列化过程却是非常简单的。一旦从另外某个流创建了ObjectOutputStream，writeObjectO就会将对象序列化。
 * 注意也可以为一个String调用writeObjectO。也可以用与DataOutputStream相同的方法写入所有基本数据类型（它们具有同样的接口）。
 *
 * 有两段看起来相似的独立的代码。一个读写的是文件，而另一个读写的是字节数组（ByteArray）。可利用序列化将对象读写到任何DatalnputStream或者DataOutputStream，
 * 甚至包括网络（正如在《Thinking in Enterprise Java》中所述）。
 *
 * 从输出中可以看出，被还原后的对象确实包含了原对象中的所有链接。
 *
 * 注意在对一个Serializable对象进行还原的过程中，没有调用任何构造器，包括默认的构造器。整个对象都是通过从InputStream中取得数据恢复而来的。
*/
