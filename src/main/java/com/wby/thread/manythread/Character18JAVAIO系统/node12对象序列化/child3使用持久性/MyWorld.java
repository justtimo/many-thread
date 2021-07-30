package com.wby.thread.manythread.Character18JAVAIO系统.node12对象序列化.child3使用持久性;//: io/MyWorld.java

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
/**
 * 一个比较诱人的使用序列化技术的想法是∶存储程序的一些状态，以便我们随后可以很容易地将程序恢复到当前状态。但是在我们能够这样做之前，必须回答几个问题。
 * 如果我们将两个对象——它们都具有指向第三个对象的引用——进行序列化，会发生什么情况? 当我们从它们的序列化状态恢复这两个对象时，第三个对象会只出现一次吗?
 * 如果将这两个对象序列化成独立的文件，然后在代码的不同部分对它们进行反序列化还原，又会怎样呢?
 *
 * 下面这个例子说明了上述问题∶
 */
class House implements Serializable {}

class Animal implements Serializable {
  private String name;
  private House preferredHouse;
  Animal(String nm, House h) {
    name = nm;
    preferredHouse = h;
  }
  public String toString() {
    return name + "[" + super.toString() +
      "], " + preferredHouse + "\n";
  }
}

public class MyWorld {
  public static void main(String[] args)
  throws IOException, ClassNotFoundException {
    House house = new House();
    List<Animal> animals = new ArrayList<Animal>();
    animals.add(new Animal("Bosco the dog", house));
    animals.add(new Animal("Ralph the hamster", house));
    animals.add(new Animal("Molly the cat", house));
    print("animals: " + animals);
    ByteArrayOutputStream buf1 =
      new ByteArrayOutputStream();
    ObjectOutputStream o1 = new ObjectOutputStream(buf1);
    o1.writeObject(animals);
    o1.writeObject(animals); // Write a 2nd set
    // Write to a different stream:
    ByteArrayOutputStream buf2 =
      new ByteArrayOutputStream();
    ObjectOutputStream o2 = new ObjectOutputStream(buf2);
    o2.writeObject(animals);
    // Now get them back:
    ObjectInputStream in1 = new ObjectInputStream(
      new ByteArrayInputStream(buf1.toByteArray()));
    ObjectInputStream in2 = new ObjectInputStream(
      new ByteArrayInputStream(buf2.toByteArray()));
    List
      animals1 = (List)in1.readObject(),
      animals2 = (List)in1.readObject(),
      animals3 = (List)in2.readObject();
    print("animals1: " + animals1);
    print("animals2: " + animals2);
    print("animals3: " + animals3);
  }
} /* Output: (Sample)
animals: [Bosco the dog[Animal@addbf1], House@42e816
, Ralph the hamster[Animal@9304b1], House@42e816
, Molly the cat[Animal@190d11], House@42e816
]
animals1: [Bosco the dog[Animal@de6f34], House@156ee8e
, Ralph the hamster[Animal@47b480], House@156ee8e
, Molly the cat[Animal@19b49e6], House@156ee8e
]
animals2: [Bosco the dog[Animal@de6f34], House@156ee8e
, Ralph the hamster[Animal@47b480], House@156ee8e
, Molly the cat[Animal@19b49e6], House@156ee8e
]
animals3: [Bosco the dog[Animal@10d448], House@e0e1c6
, Ralph the hamster[Animal@6ca1c], House@e0e1c6
, Molly the cat[Animal@1bf216a], House@e0e1c6
]
*///:~
/**
* 这里有一件有趣的事∶我们可以通过一个字节数组来使用对象序列化，从而实现对任何可 Serializable对象的"深度复制"（deep copy）——深度复制意味着我们复制的是整个对象网，而不仅仅是基本对象及其引用。
 * 复制对象将在本书的在线补充材料中进行深入地探讨。
 *
 * 在这个例子中，Animal对象包含有House类型的字段。在main（）方法中，创建了一个 Animal列表并将其两次序列化，分别送至不同的流。当其被反序列化还原并被打印时，
 * 我们可以看到所示的执行某次运行后的结果（每次运行时，对象将会处在不同的内存地址）。
 *
 * 当然，我们期望这些反序列化还原后的对象地址与原来的地址不同。但请注意，在 animals1和 animals2中却出现了相同的地址，包括二者共享的那个指向House对象的引用。另一方面，当恢复animals3时，
 * 系统无法知道另一个流内的对象是第一个流内的对象的别名，因此它会产生出完全不同的对象网。
 *
 * 只要将任何对象序列化到单一流中，就可以恢复出与我们写出时一样的对象网，并且没有任何意外重复复制出的对象。当然，我们可以在写出第一个对象和写出最后一个对象期间改变这些对象的状态，但是这是我们自己的事，
 * 无论对象在被序列化时处干什么状态（无论它们和其他对象有什么样的连接关系），它们都可以被写出。
 *
 * 如果我们想保存系统状态，最安全的做法是将其作为"原子"操作进行序列化。如果我们序列化了某些东西，再去做其他一些工作，再来序列化更多的东西，如此等等，那么将无法安全地保存系统状态。
 * 取而代之的是，将构成系统状态的所有对象都置入单一容器内，并在一个操作中将该容器直接写出。然后同样只需一次方法调用，即可以将其恢复。
 *
 * 下面这个例子是一个想象的计算机辅助设计（CAD）系统，该例演示了这一方法。此外，它还引入了static字段的问题;如果我们查看JDK文档，就会发现Class是Serializable的，
 * 因此只需直接对Class对象序列化，就可以很容易地保存static字段。在任何情况下，这都是一种明智的做法。
*/
abstract class Shape implements Serializable {
  public static final int RED = 1, BLUE = 2, GREEN = 3;
  private int xPos, yPos, dimension;
  private static Random rand = new Random(47);
  private static int counter = 0;
  public abstract void setColor(int newColor);
  public abstract int getColor();
  public Shape(int xVal, int yVal, int dim) {
    xPos = xVal;
    yPos = yVal;
    dimension = dim;
  }
  public String toString() {
    return getClass() +
            "color[" + getColor() + "] xPos[" + xPos +
            "] yPos[" + yPos + "] dim[" + dimension + "]\n";
  }
  public static Shape randomFactory() {
    int xVal = rand.nextInt(100);
    int yVal = rand.nextInt(100);
    int dim = rand.nextInt(100);
    switch(counter++ % 3) {
      default:
      case 0: return new Circle(xVal, yVal, dim);
      case 1: return new Square(xVal, yVal, dim);
      case 2: return new Line(xVal, yVal, dim);
    }
  }
}

class Circle extends Shape {
  private static int color = RED;
  public Circle(int xVal, int yVal, int dim) {
    super(xVal, yVal, dim);
  }
  public void setColor(int newColor) { color = newColor; }
  public int getColor() { return color; }
}

class Square extends Shape {
  private static int color;
  public Square(int xVal, int yVal, int dim) {
    super(xVal, yVal, dim);
    color = RED;
  }
  public void setColor(int newColor) { color = newColor; }
  public int getColor() { return color; }
}

class Line extends Shape {
  private static int color = RED;
  public static void
  serializeStaticState(ObjectOutputStream os)
          throws IOException { os.writeInt(color); }
  public static void
  deserializeStaticState(ObjectInputStream os)
          throws IOException { color = os.readInt(); }
  public Line(int xVal, int yVal, int dim) {
    super(xVal, yVal, dim);
  }
  public void setColor(int newColor) { color = newColor; }
  public int getColor() { return color; }
}

class StoreCADState {
  public static void main(String[] args) throws Exception {
    List<Class<? extends Shape>> shapeTypes =
            new ArrayList<Class<? extends Shape>>();
    // Add references to the class objects:
    shapeTypes.add(Circle.class);
    shapeTypes.add(Square.class);
    shapeTypes.add(Line.class);
    List<Shape> shapes = new ArrayList<Shape>();
    // Make some shapes:
    for(int i = 0; i < 10; i++)
      shapes.add(Shape.randomFactory());
    // Set all the static colors to GREEN:
    for(int i = 0; i < 10; i++)
      ((Shape)shapes.get(i)).setColor(Shape.GREEN);
    // Save the state vector:
    ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream("CADState.out"));
    out.writeObject(shapeTypes);
    Line.serializeStaticState(out);
    out.writeObject(shapes);
    // Display the shapes:
    System.out.println(shapes);
  }
} /* Output:
[class Circlecolor[3] xPos[58] yPos[55] dim[93]
, class Squarecolor[3] xPos[61] yPos[61] dim[29]
, class Linecolor[3] xPos[68] yPos[0] dim[22]
, class Circlecolor[3] xPos[7] yPos[88] dim[28]
, class Squarecolor[3] xPos[51] yPos[89] dim[9]
, class Linecolor[3] xPos[78] yPos[98] dim[61]
, class Circlecolor[3] xPos[20] yPos[58] dim[16]
, class Squarecolor[3] xPos[40] yPos[11] dim[22]
, class Linecolor[3] xPos[4] yPos[83] dim[6]
, class Circlecolor[3] xPos[75] yPos[10] dim[42]
]
*///:~
/**
*  Shape类实现了Serializable，所以任何自Shape继承的类也都会自动是Serializable的。每个 Shape都含有数据，而且每个派生自Shape的类都包含一个static字段，用来确定各种Shape类型的颜色（如果
 *  将static字段置入基类，只会产生一个static字段，因为static字段不能在派生类中复制）。可对基类中的方法进行重载，以便为不同的类型设置颜色（static方法不会动态绑定，所以这些都是普通的方法）。
 *  每次调用randomFactoryO方法时，它都会使用不同的随机数作为 Shape的数据，从而创建不同的Shape。
 *
 * 在mainO中，一个ArrayList用于保存Class对象，而另一个用于保存几何形状。
 * 恢复对象相当直观∶
*/
class RecoverCADState {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws Exception {
    ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("CADState.out"));
    // Read in the same order they were written:
    List<Class<? extends Shape>> shapeTypes =
            (List<Class<? extends Shape>>)in.readObject();
    Line.deserializeStaticState(in);
    List<Shape> shapes = (List<Shape>)in.readObject();
    System.out.println(shapes);
  }
} /* Output:
[class Circlecolor[1] xPos[58] yPos[55] dim[93]
, class Squarecolor[0] xPos[61] yPos[61] dim[29]
, class Linecolor[3] xPos[68] yPos[0] dim[22]
, class Circlecolor[1] xPos[7] yPos[88] dim[28]
, class Squarecolor[0] xPos[51] yPos[89] dim[9]
, class Linecolor[3] xPos[78] yPos[98] dim[61]
, class Circlecolor[1] xPos[20] yPos[58] dim[16]
, class Squarecolor[0] xPos[40] yPos[11] dim[22]
, class Linecolor[3] xPos[4] yPos[83] dim[6]
, class Circlecolor[1] xPos[75] yPos[10] dim[42]
]
*///:~
/**
*  可以看到，xPos、yPos以及dim的值都被成功地保存和恢复了，但是对static信息的读取却出现了问题。所有读回的颜色应该都是"3"，但是真实情况却并非如此。Circle的值为1（定义为RED），
 *  而Square的值为0（记住，它们是在构造器中被初始化的）。看上去似平static数据根本没有被序列化! 确实如此——尽管Class类是Serializable的，但它却不能按我们所期望的方式运行。
 *  所以假如想序列化static值，必须自己动手去实现。
 *
 * 这正是Line中的serializeStaticStateO和deserializeStaticStateO两个static方法的用途。可以看到，它们是作为存储和读取过程的一部分被显式地调用的。（注意必须维护写入序列化文件和从该文件中读回的顺序。）
 * 因此，为了使CADStatejava正确运转起来，我们必须;
 *  1）为几何形状添加serializeStaticState（）和deserializeStaticStateO。
 *  2）移除ArrayList shapeTypes以及与之有关的所有代码。
 *  3）在几何形状内添加对新的序列化和反序列化还原静态方法的调用。
 *
 * 另一个要注意的问题是安全，，因为序列化也会将private数据保存下来。如果你关心安全问题，那么应将其标记成transient。但是这之后，还必须设计一种安全的保存信息的方法，以便在执行恢复时可以复位那些private变量。
*/
