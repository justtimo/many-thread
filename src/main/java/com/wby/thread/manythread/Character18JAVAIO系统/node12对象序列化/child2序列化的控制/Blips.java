package com.wby.thread.manythread.Character18JAVAIO系统.node12对象序列化.child2序列化的控制;//: io/Blips.java
// Simple use of Externalizable & a pitfall.

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
/**
 * 正如大家所看到的，默认的序列化机制并不难操纵。然而，如果有特殊的需要那又该怎么办呢? 例如，也许要考虑特殊的安全问题，而且你不希望对象的某—部分被序列化;
 * 或者—个对象被还原以后，某子对象需要重新创建，从而不必将该子对象序列化。
 *
 * 在这些特殊情况下，可通过实现Externalizable接口——代替实现Serializable接口——来对序列化过程进行控制。这个Externalizable接口继承了Serializable接口，
 * 同时增添了两个方法; writeExternal0和readExternal0。这两个方法会在序列化和反序列化还原的过程中被自动调用，以便执行一些特殊操作。
 *
 * 下面这个例子展示了Externalizable接口方法的简单实现。注意Blip1和Blip2除了细微的差别之外，几乎完全一致（研究一下代码，看看你能否发现）;
 */
class Blip1 implements Externalizable {
  public Blip1() {
    print("Blip1 Constructor");
  }
  public void writeExternal(ObjectOutput out)
      throws IOException {
    print("Blip1.writeExternal");
  }
  public void readExternal(ObjectInput in)
     throws IOException, ClassNotFoundException {
    print("Blip1.readExternal");
  }
}

class Blip2 implements Externalizable {
  Blip2() {
    print("Blip2 Constructor");
  }
  public void writeExternal(ObjectOutput out)
      throws IOException {
    print("Blip2.writeExternal");
  }
  public void readExternal(ObjectInput in)
     throws IOException, ClassNotFoundException {
    print("Blip2.readExternal");
  }
}

public class Blips {
  public static void main(String[] args)
  throws IOException, ClassNotFoundException {
    print("Constructing objects:");
    Blip1 b1 = new Blip1();
    Blip2 b2 = new Blip2();
    ObjectOutputStream o = new ObjectOutputStream(
      new FileOutputStream("Blips.out"));
    print("Saving objects:");
    o.writeObject(b1);
    o.writeObject(b2);
    o.close();
    // Now get them back:
    ObjectInputStream in = new ObjectInputStream(
      new FileInputStream("Blips.out"));
    print("Recovering b1:");
    b1 = (Blip1)in.readObject();
    // OOPS! Throws an exception:
//! print("Recovering b2:");
//! b2 = (Blip2)in.readObject();
  }
} /* Output:
Constructing objects:
Blip1 Constructor
Blip2 Constructor
Saving objects:
Blip1.writeExternal
Blip2.writeExternal
Recovering b1:
Blip1 Constructor
Blip1.readExternal
*///:~
/**
* 上例中没有恢复Blip2对象，因为那样做会导致一个异常。你找出Blip1和Blip2之间的区别了吗? Blip1的构造器是"公共的"（public），Blip2的构造器却不是，这样就会在恢复时造成异常。
 * 试试将Blip2的构造器变成public的，然后删除!注释标记，看看是否能得到正确的结果。
 *
 * 恢复b1后，会调用Blip1默认构造器。这与恢复一个Serializable对象不同。对于Serializable对象，对象完全以它存储的二进制位为基础来构造，而不调用构造器。
 * 而对于一个Externalizable对象，所有普通的默认构造器都会被调用（包括在字段定义时的初始化），然后调用readExternal0。必须注意这一点——所有默认的构造器都会被调用，才能使Externalizable对象产生正确的行为。
 *
 * 下面这个例子示范了如何完整保存和恢复一个Externalizable对象;
*/
class Blip3 implements Externalizable {
  private int i;
  private String s; // No initialization
  public Blip3() {
    print("Blip3 Constructor");
    // s, i not initialized
  }
  public Blip3(String x, int a) {
    print("Blip3(String x, int a)");
    s = x;
    i = a;
    // s & i initialized only in non-default constructor.
  }
  public String toString() { return s + i; }
  public void writeExternal(ObjectOutput out)
          throws IOException {
    print("Blip3.writeExternal");
    // You must do this:
    out.writeObject(s);
    out.writeInt(i);
  }
  public void readExternal(ObjectInput in)
          throws IOException, ClassNotFoundException {
    print("Blip3.readExternal");
    // You must do this:
    s = (String)in.readObject();
    i = in.readInt();
  }
  public static void main(String[] args)
          throws IOException, ClassNotFoundException {
    print("Constructing objects:");
    Blip3 b3 = new Blip3("A String ", 47);
    print(b3);
    ObjectOutputStream o = new ObjectOutputStream(
            new FileOutputStream("Blip3.out"));
    print("Saving object:");
    o.writeObject(b3);
    o.close();
    // Now get it back:
    ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("Blip3.out"));
    print("Recovering b3:");
    b3 = (Blip3)in.readObject();
    print(b3);
  }
} /* Output:
Constructing objects:
Blip3(String x, int a)
A String 47
Saving object:
Blip3.writeExternal
Recovering b3:
Blip3 Constructor
Blip3.readExternal
A String 47
*///:~
/**
*  其中，字段s和i只在第二个构造器中初始化，而不是在默认的构造器中初始化。这意味着假如不在readExternal（）中初始化s和i，s就会为nulI，而i就会为零（因为在创建对象的第一步中将对象的存储空间清理为0）。
 *  如果注释掉跟随于"You must do this"后面的两行代码，然后运行程序，就会发现当对象被还原后，s是null，而i是零。
 *
 * 我们如果从一个Externalizable对象继承，通常需要调用基类版本的writeExternalO和read- ExternalO）来为基类组件提供恰当的存储和恢复功能。
 *
 * 因此，为了正常运行，我们不仅需要在writeExternal（）方法（没有任何默认行为来为 Externalizable对象写入任何成员对象）中将来自对象的重要信息写入，还必须在readExternal0方法中恢复数据。
 * 起先，可能会有一点迷惑，因为Externalizable对象的默认构造行为使其看起来似平像某种自动发生的存储与恢复操作。但实际上并非如此。
*/

/**
*  transient（瞬时）关键字
 * 当我们对序列化进行控制时。可能某个特定子对象不想让Java的序列化机制自动保存与恢复。如果子对象表示的是我们不希望将其序列化的敏感信息（如密码），通常就会面临这种情况。
 * 即使对象中的这些信息是private（私有）属性，一经序列化处理，人们就可以通过读取文件或者拦截网络传输的方式来访问到它。
 *
 * 有一种办法可防止对象的敏感部分被序列化，就是将类实现为Externalizable，如前面所示。这样一来，没有任何东西可以自动序列化，并且可以在writeExternalO内部只对所需部分进行显式的序列化。
 *
 * 然而，如果我们正在操作的是一个Serializable对象，那么所有序列化操作都会自动进行为了能够予以控制，可以用transient（瞬时）关键字逐个字段地关闭序列化，它的意思是"不用麻烦你保存或恢复数据——我自己会处理的"。
 *
 * 例如，假设某个Login对象保存某个特定的登录会话信息。登录的合法性通过校验之后，我们想把数据保存下来，但不包括密码。为做到这一点，最简单的办法是实现Serializable，并将 password字段标志为transient。
 *
 * 下面是具体的代码∶
*/
class Logon implements Serializable {
  private Date date = new Date();
  private String username;
  private transient String password;
  public Logon(String name, String pwd) {
    username = name;
    password = pwd;
  }
  public String toString() {
    return "logon info: \n   username: " + username +
            "\n   date: " + date + "\n   password: " + password;
  }
  public static void main(String[] args) throws Exception {
    Logon a = new Logon("Hulk", "myLittlePony");
    print("logon a = " + a);
    ObjectOutputStream o = new ObjectOutputStream(
            new FileOutputStream("Logon.out"));
    o.writeObject(a);
    o.close();
    TimeUnit.SECONDS.sleep(1); // Delay
    // Now get them back:
    ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("Logon.out"));
    print("Recovering object at " + new Date());
    a = (Logon)in.readObject();
    print("logon a = " + a);
  }
} /* Output: (Sample)
logon a = logon info:
   username: Hulk
   date: Sat Nov 19 15:03:26 MST 2005
   password: myLittlePony
Recovering object at Sat Nov 19 15:03:28 MST 2005
logon a = logon info:
   username: Hulk
   date: Sat Nov 19 15:03:26 MST 2005
   password: null
*///:~
/**
* 可以看到，其中的date和username域是一般的（不是transient的），所以它们会被自动序列化。而password是transient的，所以不会被自动保存到磁盘;另外，自动序列化机制也不会尝试去恢复它。
 * 当对象被恢复时，password域就会变成null。注意，虽然toStringO是用，重载后的+运算符来连接String对象，但是null引用会被自动转换成字符串null。
 *
 * 我们还可以发现∶ date字段被存储了到磁盘并从磁盘上被恢复了出来，而且没有再重新生成。
 *
 * 由于Externalizable对象在默认情况下不保存它们的任何字段，所以transient关键字只能和 Serializable对象一起使用。
 *
 *
 *
 * Externalizable的替代方法
 * 如果不是特别坚持实现Externalizable接口，那么还有另一种方法。我们可以实现Serializa- ble接口，并添加（注意我说的是"添加"，而非"覆盖"或者"实现"）名为writeObjectO和readObjectO）的方法。
 * 这样一旦对象被序列化或者被反序列化还原，就会自动地分别调用这两个方法。也就是说，只要我们提供了这两个方法，就会使用它们而不是默认的序列化机制。
 *
 * 这些方法必须具有准确的方法特征签名∶
 *      private void writeObject(ObjectOutputStream stream)
 *        throws IOException;
 *
 *      private void readObject (ObjectInputStream stream)
 *        throws IOExcept ion,ClassNotFoundException
 * 从设计的观点来看，现在事情变得真是不可思议。首先，我们可能会认为由于这些方法不是基类或者Serializable接口的一部分，所以应该在它们自己的接口中进行定义。
 * 但是注意它们被定义成了private，这意味着它们仅能被这个类的其他成员调用。然而，实际上我们并没有从这个类的其他方法中调用它们，而是ObjectOutputStream和ObjectInputStream对象的writeObject
 * （和 readObjectO方法调用你的对象的writeObjectO和readObjectO方法（注意关于这里用到的相同方法名，我尽量抑制住不去谩骂。一句话;混乱）。
 * 读者可能想知道ObjectOutputStream和 ObjectInputStrean对象是怎样访问你的类中的private方法的。我们只能假设这正是序列化神奇的一部分。
 *
 * 在接口中定义的所有东西都自动是public的，因此如果writeObjectO和 readObjectO必须是 private的，那么它们不会是接口的一部分。因为我们必须要完全遵循其方法特征签名，所以其效果就和实现了接口一样。
 *
 * 在调用ObjectOutputStream.writeObjectO时，会检查所传递的Serializable对象，看看是否实现了它自己的writeObject0。如果是这样，就跳过正常的序列化过程并调用它的writeObject（）。
 * readObjectO）的情形与此相同。
 *
 * 还有另外一个技巧。在你的writeObjectO）内部，可以调用defaultWriteObjectO）来选择执行默认的writeObjectO）。类似地，在readObjectO内部，我们可以调用defaultReadObjectO。
 *
 * 下面这个简单的例子演示了如何对一个Serializable对象的存储与恢复进行控制∶
*/
class SerialCtl implements Serializable {
  private String a;
  private transient String b;
  public SerialCtl(String aa, String bb) {
    a = "Not Transient: " + aa;
    b = "Transient: " + bb;
  }
  public String toString() { return a + "\n" + b; }
  private void writeObject(ObjectOutputStream stream)
          throws IOException {
    stream.defaultWriteObject();
    stream.writeObject(b);
  }
  private void readObject(ObjectInputStream stream)
          throws IOException, ClassNotFoundException {
    stream.defaultReadObject();
    b = (String)stream.readObject();
  }
  public static void main(String[] args)
          throws IOException, ClassNotFoundException {
    SerialCtl sc = new SerialCtl("Test1", "Test2");
    System.out.println("Before:\n" + sc);
    ByteArrayOutputStream buf= new ByteArrayOutputStream();
    ObjectOutputStream o = new ObjectOutputStream(buf);
    o.writeObject(sc);
    // Now get it back:
    ObjectInputStream in = new ObjectInputStream(
            new ByteArrayInputStream(buf.toByteArray()));
    SerialCtl sc2 = (SerialCtl)in.readObject();
    System.out.println("After:\n" + sc2);
  }
} /* Output:
Before:
Not Transient: Test1
Transient: Test2
After:
Not Transient: Test1
Transient: Test2
*///:~
/**
*   在这个例子中，有一个String字段是普通字段，而另一个是transient字段，用来证明非 transient字段由defaultWriteObject（方法保存，而transient字段必须在程序中明确保存和恢复字段是在构造器内部而不是
 *   在定义处进行初始化的，以此可以证实它们在反序列化还原期间没有被一些自动化机制初始化。
 *
 * 如果我们打算使用默认机制写入对象的非transient部分，那么必须调用defaultWriteObjectO作为writeObjectO中的第一个操作，并让defaultReadObject（）作为readObject（中的第一个操作。
 * 这些都是奇怪的方法调用。例如，如果我们正在为ObjectOutputStream调用defaultWrite- Object（O且没有传递任何参数，然而不知何故它却可以运行，并且知道对象的引用以及如何写入非transient部分。
 * 真是奇怪之极。
 *
 * 对transient对象的存储和恢复使用了我们比较熟悉的代码。请再考虑一下在这里所发生的事情。在main（）中，创建SerialCtl对象，然后将其序列化到ObjectOutputStream（注意在这
 * 种情况下，使用的是缓冲区而不是文件——这对于ObjectOutputStream来说是完全一样的）。序列化发生在下面这行代码当中;
 *    o.writeObject(sc);
 *
 * writeObject（）方法必须检查sc，判断它是否拥有自己的writeObjectO）方法（不是检查接口——这里根本就没有接口，也不是检查类的类型，而是利用反射来真正地搜索方法）。
 * 如果有，那么就会使用它。对readObjectO也采用了类似的方法。或许这是解决这个问题的唯—切实可行的方法，但它确实有点古怪。
 *
 *
 * 版本控制
 *
 * 有时可能想要改变可序列化类的版本（比如源类的对象可能保存在数据库中）。虽然Java支持这种做法，但是你可能只在特殊的情况下才这样做，此外，还需要对它有相当深程度的了解（在这里
 * 我们就不再试图达到这一点）。从http∶//java.sun.com处下载的JDK文档中对这一主题进行了非常彻底的论述。
 *
 * 我们会发现在JDK文档中有许多注解是从下面的文字开始的∶
 *    警告 该类的序列化对象和未来的Swing版本不兼容。当前对序列化的支持只适用于短期存储或应用之间的RMI。
 * 这是因为Java的版本控制机制过于简单，因而不能在任何场合都可靠运转，尤其是对 JavaBeans更是如此。有关人员正在设法修正这一设计，也就是警告中的相关部分。
*/
