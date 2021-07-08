package com.wby.thread.manythread.charector14类型信息.node8空对象;//: typeinfo/Person.java
// A class with a Null Object.

import com.wby.thread.manythread.net.mindview.util.Null;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
* @Description: 当你使用内置的null表示缺少对象时，在每次使用引用时都必须测试其是否为null，这很枯燥而且会产生相当乏味的代码，他自己没有其他任何行为。
 *  有时引入 空对象 的思想将会很有用，他可以接受传递给他的所代表的对象的消息，但是将返回表示为实际上并不存在任何“真实”对象的值。
 *  通过这种方式，你可以假设所有的对象都是有效的，而不必浪费编程精力去检查null(并阅读所产生的代码)
 *
 *  尽管想象一种可以自动为我们创建空对象的编程语言很有趣，但实际上，到处使用空对象并没有意义--有时检查null就可以了。
 *  空对象最有用之处在于他更接近数据，因为对象表示的是问题空间内的实体。
 *  一个简单的例子，许多系统都有Person类，在代码中，很多情况你没有一个实际的人(或者你有，但是你还没有这个人的全部信息)，因此，你会使用一个null引用并测试他。
 *  与此不同的是，我们可以使用空对象。但是即使空对象可以响应“实际”对象可以响应的所有消息，你任然需要某种方式测试其是否为空。
 *  要达到此目的，简单地方式是创建一个标记接口：
*/
interface Nulll{}
/**
* @Description: 这使得instanceof可以探测空对象，这并不要求你在所有的类中都添加isNull()方法(毕竟这只是执行RTTI的一种不同方式---为什么不使用内置的工具？)
*/
class Person {
  public final String first;
  public final String last;
  public final String address;
  // etc.
  public Person(String first, String last, String address){
    this.first = first;
    this.last = last;
    this.address = address;
  }
  public String toString() {
    return "Person: " + first + " " + last + " " + address;
  }
  public static class NullPerson
  extends Person implements Null {
    private NullPerson() { super("None", "None", "None"); }
    public String toString() { return "NullPerson"; }
  }
  public static final Person NULL = new NullPerson();
} ///:~
/**
* @Description: 通常空对象都是单例，因此这里将其作为静态final实例创建。这可以正常工作的，因为Person是不可变的---你只能在构造器中设置他的值，但是你不能
 * 修改他们(因为String自身具备内在的不可变性)。如果你想要修改一个NullPerson，那只能用一个新的Person对象替换他。
 * 注意，你可以选择使用instanceof来探测泛化的null还是更具体的NullPerson，但是由于使用了单例方式，所以还可以只使用equals甚至==来与Person.Null比较
*/
/**
* @Description: 假设你获得了一笔投资，现在要招兵买马，但是虚位以待时，你可以将Person空对象放在每个Position上：
*/
class Position {
  private String title;
  private Person person;
  public Position(String jobTitle, Person employee) {
    title = jobTitle;
    person = employee;
    if(person == null)
      person = Person.NULL;
  }
  public Position(String jobTitle) {
    title = jobTitle;
    person = Person.NULL;
  }
  public String getTitle() { return title; }
  public void setTitle(String newTitle) {
    title = newTitle;
  }
  public Person getPerson() { return person; }
  public void setPerson(Person newPerson) {
    person = newPerson;
    if(person == null)
      person = Person.NULL;
  }
  public String toString() {
    return "Position: " + title + " " + person;
  }
} ///:~
/**
* @Description: 有了Position就不需要创建空对象了，以为Person.Null的存在就表示这是一个空Position(稍后可能会发现需要增加一个显示的用于Position的空对象，
 * 但是YAGNI[你永不需要他，这是极限编程的原则之一：做可以工作的最简单的事情]声明：在你的设计草案初稿中，应该努力使用最简单且可以工作的事物，知道程序某个方面要求你
 * 添加额外的特性，而不是一开始就假设他是必需的)
*/
class Staff extends ArrayList<Position> {
  public void add(String title, Person person) {
    add(new Position(title, person));
  }
  public void add(String... titles) {
    for(String title : titles)
      add(new Position(title));
  }
  public Staff(String... titles) { add(titles); }
  public boolean positionAvailable(String title) {
    for(Position position : this)
      if(position.getTitle().equals(title) &&
              position.getPerson() == Person.NULL)
        return true;
    return false;
  }
  public void fillPosition(String title, Person hire) {
    for(Position position : this)
      if(position.getTitle().equals(title) &&
              position.getPerson() == Person.NULL) {
        position.setPerson(hire);
        return;
      }
    throw new RuntimeException(
            "Position " + title + " not available");
  }
  public static void main(String[] args) {
    Staff staff = new Staff("President", "CTO",
            "Marketing Manager", "Product Manager",
            "Project Lead", "Software Engineer",
            "Software Engineer", "Software Engineer",
            "Software Engineer", "Test Engineer",
            "Technical Writer");
    staff.fillPosition("President",
            new Person("Me", "Last", "The Top, Lonely At"));
    staff.fillPosition("Project Lead",
            new Person("Janet", "Planner", "The Burbs"));
    if(staff.positionAvailable("Software Engineer"))
      staff.fillPosition("Software Engineer",
              new Person("Bob", "Coder", "Bright Light City"));
    System.out.println(staff);
  }
} /* Output:
[Position: President Person: Me Last The Top, Lonely At, Position: CTO NullPerson, Position: Marketing Manager NullPerson, Position: Product Manager NullPerson, Position: Project Lead Person: Janet Planner The Burbs, Position: Software Engineer Person: Bob Coder Bright Light City, Position: Software Engineer NullPerson, Position: Software Engineer NullPerson, Position: Software Engineer NullPerson, Position: Test Engineer NullPerson, Position: Technical Writer NullPerson]
*///:~
/**
* @Description: 注意，你在某些地方任然必须测试空对象，这与检查是否为null没有差异，但是在其他地方(例如本例中的toString转换)你就不必执行额外的测试了，而可以
 * 直接假设所有对象都是有效的。
*/
/**
* @Description: 如果你用接口取代具体类，那么就可以使用DynamicProxy来自动创建空对象。
 * 假设我们有一个Robot接口，他定了一个名字、一个模型和一个描述Robot行为能力的List<Operation>.
 * Operation包含一个描述和一个命令(这是一种命令模式类型)：
*/
interface Operation {
  String description();
  void command();
} ///:~
/**
* @Description: 你可以通过调用operation()来访问Robot服务：
*/
interface Robot {
  String name();
  String model();
  List<Operation> operations();
  class Test {
    public static void test(Robot r) {
      if(r instanceof Null)
        System.out.println("[Null Robot]");
      System.out.println("Robot name: " + r.name());
      System.out.println("Robot model: " + r.model());
      for(Operation operation : r.operations()) {
        System.out.println(operation.description());
        operation.command();
      }
    }
  }
} ///:~
/**
* @Description: 这里也使用了嵌套类来执行测试：
 *  现在可以创建一个扫雪Robot：
*/
class SnowRemovalRobot implements Robot {
  private String name;
  public SnowRemovalRobot(String name) {this.name = name;}
  public String name() { return name; }
  public String model() { return "SnowBot Series 11"; }
  public List<Operation> operations() {
    return Arrays.asList(
            new Operation() {
              public String description() {
                return name + " can shovel snow";
              }
              public void command() {
                System.out.println(name + " shoveling snow");
              }
            },
            new Operation() {
              public String description() {
                return name + " can chip ice";
              }
              public void command() {
                System.out.println(name + " chipping ice");
              }
            },
            new Operation() {
              public String description() {
                return name + " can clear the roof";
              }
              public void command() {
                System.out.println(name + " clearing roof");
              }
            }
    );
  }
  public static void main(String[] args) {
    Robot.Test.test(new SnowRemovalRobot("Slusher"));
  }
} /* Output:
Robot name: Slusher
Robot model: SnowBot Series 11
Slusher can shovel snow
Slusher shoveling snow
Slusher can chip ice
Slusher chipping ice
Slusher can clear the roof
Slusher clearing roof
*///:~
/**
* @Description: 假设存在很多不同类型的Robot，我们想对每一种Robot类型都创建一个空对象，去执行某些特殊操作----本例中，即提供空对象所代表的Robot
 *  确切类型的信息。这些信息是通过动态代理捕获的：
*/
class NullRobotProxyHandler implements InvocationHandler {
  private String nullName;
  private Robot proxied = new NRobot();
  NullRobotProxyHandler(Class<? extends Robot> type) {
    nullName = type.getSimpleName() + " NullRobot";
  }
  private class NRobot implements Null, Robot {
    public String name() { return nullName; }
    public String model() { return nullName; }
    public List<Operation> operations() {
      return Collections.emptyList();
    }
  }
  public Object
  invoke(Object proxy, Method method, Object[] args)
          throws Throwable {
    return method.invoke(proxied, args);
  }
}

class NullRobot {
  public static Robot
  newNullRobot(Class<? extends Robot> type) {
    return (Robot) Proxy.newProxyInstance(
            NullRobot.class.getClassLoader(),
            new Class[]{ Null.class, Robot.class },
            new NullRobotProxyHandler(type));
  }
  public static void main(String[] args) {
    Robot[] bots = {
            new SnowRemovalRobot("SnowBee"),
            newNullRobot(SnowRemovalRobot.class)
    };
    for(Robot bot : bots)
      Robot.Test.test(bot);
  }
} /* Output:
Robot name: SnowBee
Robot model: SnowBot Series 11
SnowBee can shovel snow
SnowBee shoveling snow
SnowBee can chip ice
SnowBee chipping ice
SnowBee can clear the roof
SnowBee clearing roof
[Null Robot]
Robot name: SnowRemovalRobot NullRobot
Robot model: SnowRemovalRobot NullRobot
*///:~
/**
* @Description: 无论何时，如果需要一个空Robot对象，只需要调用newNullRobot()，并传递需要代理的RObot的类型。
 *  代理会满足Robot和Null接口的需求，并提供他所代理的类型的确切名字。
*/
