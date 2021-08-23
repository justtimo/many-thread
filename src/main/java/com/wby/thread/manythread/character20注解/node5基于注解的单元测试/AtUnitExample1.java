//: annotations/AtUnitExample1.java
package com.wby.thread.manythread.character20注解.node5基于注解的单元测试;

import com.wby.thread.manythread.net.mindview.atunit.Test;
import com.wby.thread.manythread.net.mindview.atunit.TestObjectCleanup;
import com.wby.thread.manythread.net.mindview.atunit.TestObjectCreate;
import com.wby.thread.manythread.net.mindview.atunit.TestProperty;
import com.wby.thread.manythread.net.mindview.util.OSExecute;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 单元测试是对类中的每个方法提供一个或多个测试的一种实践，其目的是为了有规律地测试一个类的各个部分是否具备正确的行为。在Java中，最著名的单元测试工具就是JUnit。在撰写本书时。
 * JUnit户经开始了向JUnit4更新的过程，其目的正是为了融入注解9。对于注解出现之前的JUnit而言，有一个主要的问题，即为了设置并运行JUnit测试需要做大量的形式上的工作。
 * 随着其渐渐的发展，这种负担已经减轻了一些，但注解的出现能够使其更贴近"最简单的单元测试系统"。
 *
 * 使用注解出现之前的JUnit，程序员必须创建一个独立的类来保存其单元测试。有了注解，我们可以直接在要验证的类里面编写测试，这将大大减少道元测试所需的时间和麻烦之处。
 * 采用这种方式还有一个额外的好处，就是能够像测试public方法一样很容易地测试private方法。
 *
 * 这个基于注解的测试框架叫做@Unit。其最基本的测试形式，可能也是你用的最多的一个注解是@Test，我们用@Test来标记测试方法。测试方法不带参数，并返回boolean结果来说明测试成功或失败。
 * 程序员可以任意命名他的测试方法。同时，@Unit测试方法可以是任意你喜欢的访问修饰方式，包括private。
 *
 * 要使用@Unit，程序员必须引入net.mindview.atunit9，用@Unit的测试标记为合适的方法和域打上标记（在接下来的例子中你会学到），然后让你的构建系统对编译后的类运行@Unit。下面是一个简单的例子∶
 *
 */
public class AtUnitExample1 {
  public String methodOne() {
    return "This is methodOne";
  }
  public int methodTwo() {
    System.out.println("This is methodTwo");
    return 2;
  }
  @Test
  boolean methodOneTest() {
    return methodOne().equals("This is methodOne");
  }
  @Test boolean m2() { return methodTwo() == 2; }
  @Test private boolean m3() { return true; }
  // Shows output for failure:
  @Test boolean failureTest() { return false; }
  @Test boolean anotherDisappointment() { return false; }
  public static void main(String[] args) throws Exception {
    OSExecute.command(
      "java net.mindview.atunit.AtUnit AtUnitExample1");
  }
} /* Output:
annotations.AtUnitExample1
  . methodOneTest
  . m2 This is methodTwo

  . m3
  . failureTest (failed)
  . anotherDisappointment (failed)
(5 tests)

>>> 2 FAILURES <<<
  annotations.AtUnitExample1: failureTest
  annotations.AtUnitExample1: anotherDisappointment
*///:~
/**
 * 使用@Unit进行测试的类必须定义在某个包中（即必须包括packae声明）。
 *
 * @Test注解被置于methodOneTest（O、m2O、m3OfailureTestO以及anotherDisappointment（方法之前，它告诉@Unit将这些方法作为单元测试来运行。
 * 同时，@Test将验证并确保这些方法没有参数，并且返回值是boolean或void。程序员编写单元测试时，唯一需要做的就是决定测试是成功还是失败，（对于返回值为boolean的方法）应该返回ture还是false。
 *
 * 如果你熟悉JUnit，你会注意到@Unit的输出带有更多的信息。我们可以看到当前正在运行的测试，因此测试中的输出更是有用，而且在最后，它还能告诉我们导致错误的类和测试。
 *
 * 程序员并非必须将测试方法嵌入到原本的类中，因为有时候这根本做不到。要生成一个非·嵌入式的测试，最简单的办法就是继承∶
 */
class AtUnitExternalTest extends AtUnitExample1 {
  @Test boolean _methodOne() {
    return methodOne().equals("This is methodOne");
  }
  @Test boolean _methodTwo() { return methodTwo() == 2; }
  public static void main(String[] args) throws Exception {
    OSExecute.command(
            "java net.mindview.atunit.AtUnit AtUnitExternalTest");
  }
} /* Output:
annotations.AtUnitExternalTest
  . _methodOne
  . _methodTwo This is methodTwo

OK (2 tests)
*///:~
/**
 * 这个例子还表现出了灵活命名的价值（与JUnit不同，它要求你必须使用test作为测试方法的前缀）。在这里，@Test方法被命名为下划线前缀加上这将要测试的方法的名字（我并不认为这是一个理想的命名形式，只是表现一种可能性罢了）。
 *
 * 或者你还可以使用组合的方式创建非嵌入式的测试∶
 */
class AtUnitComposition {
  AtUnitExample1 testObject = new AtUnitExample1();
  @Test boolean _methodOne() {
    return
            testObject.methodOne().equals("This is methodOne");
  }
  @Test boolean _methodTwo() {
    return testObject.methodTwo() == 2;
  }
  public static void main(String[] args) throws Exception {
    OSExecute.command(
            "java net.mindview.atunit.AtUnit AtUnitComposition");
  }
} /* Output:
annotations.AtUnitComposition
  . _methodOne
  . _methodTwo This is methodTwo

OK (2 tests)
*///:~
/**
 * 因为每个测试对应一个新创建的AtUnitComposition对象，因此每个测试也对应一个新的成员testObject。
 *
 * @Unit中并没有JUnit里的特殊的assert方法，不过@Test方法仍然允许程序员返回void（如果你还是想用ture或false的话，你仍然可以用boolean作为方法返回值类型），
 * 这是@Test方法的第二种形式。在这种情况下，要表示测试成功，可以使用Java的assert语句。Java的断言机制一般要求程序员在java命令行中加上-ea标志，不过@Unit已经自动打开了该功能。
 * 而要表示测试失败的话，你甚至可以使用异常。@Unit的设计目标之一就是尽可能少地添加额外的语法，而 Java的assert和异常对于报告错误而言，已经足够了。
 * 一个失败的assert或从测试方法中抛出异常，都将被看作一个失败的测试，但是@Unit并不会就在这个失败的测试上打住，它会继续运行，直到所有的测试都运行完毕。下面是一个示例程序∶
 */
class AtUnitExample2 {
  public String methodOne() {
    return "This is methodOne";
  }
  public int methodTwo() {
    System.out.println("This is methodTwo");
    return 2;
  }
  @Test void assertExample() {
    assert methodOne().equals("This is methodOne");
  }
  @Test void assertFailureExample() {
    assert 1 == 2: "What a surprise!";
  }
  @Test void exceptionExample() throws IOException {
    new FileInputStream("nofile.txt"); // Throws
  }
  @Test boolean assertAndReturn() {
    // Assertion with message:
    assert methodTwo() == 2: "methodTwo must equal 2";
    return methodOne().equals("This is methodOne");
  }
  public static void main(String[] args) throws Exception {
    OSExecute.command(
            "java net.mindview.atunit.AtUnit AtUnitExample2");
  }
} /* Output:
annotations.AtUnitExample2
  . assertExample
  . assertFailureExample java.lang.AssertionError: What a surprise!
(failed)
  . exceptionExample java.io.FileNotFoundException: nofile.txt (The system cannot find the file specified)
(failed)
  . assertAndReturn This is methodTwo

(4 tests)

>>> 2 FAILURES <<<
  annotations.AtUnitExample2: assertFailureExample
  annotations.AtUnitExample2: exceptionExample
*///:~
/**
 * 下面的例子使用非嵌入式的测试，并且用到了断言，它将对java.utiLHashSet执行一些简单的测试∶
 */
class HashSetTest {
  HashSet<String> testObject = new HashSet<String>();
  @Test void initialization() {
    assert testObject.isEmpty();
  }
  @Test void _contains() {
    testObject.add("one");
    assert testObject.contains("one");
  }
  @Test void _remove() {
    testObject.add("one");
    testObject.remove("one");
    assert testObject.isEmpty();
  }
  public static void main(String[] args) throws Exception {
    OSExecute.command(
            "java net.mindview.atunit.AtUnit HashSetTest");
  }
} /* Output:
annotations.HashSetTest
  . initialization
  . _remove
  . _contains
OK (3 tests)
*///:~
/**
 * 如果采用继承的方式，可能会更简单，并且也没有一些其他的约束。
 *
 * 练习4∶ （3）验证是否每个测试都会生成一个新的testObject。
 * 练习5∶（1）使用继承的方式修改上面的例子。
 * 练习6∶ （1） 使用HashSetTest.java演示的方式测试LinkedList类。
 * 练习7;（1）使用继承的方式修改前一个练习的结果。
 *
 * 对每一个单元测试而言，@Unit都会用默认的构造器，为该测试所属的类创建出一个新的实例。并在此新创建的对象上运行测试，然后丢弃该对象，以避免对其他测试产生副作用。
 * 如此创建对象导致我们依赖于类的默认构造器。如果你的类没有默认构造器，或者新对象需要复杂的构造过程，那么你可以创建一个static方法专门负责构造对象，
 * 然后用@TestObjectCreaet注解将该方法标记出来，就像这样∶
 */
class AtUnitExample3 {
  private int n;
  public AtUnitExample3(int n) { this.n = n; }
  public int getN() { return n; }
  public String methodOne() {
    return "This is methodOne";
  }
  public int methodTwo() {
    System.out.println("This is methodTwo");
    return 2;
  }
  @TestObjectCreate
  static AtUnitExample3 create() {
    return new AtUnitExample3(47);
  }
  @Test boolean initialization() { return n == 47; }
  @Test boolean methodOneTest() {
    return methodOne().equals("This is methodOne");
  }
  @Test boolean m2() { return methodTwo() == 2; }
  public static void main(String[] args) throws Exception {
    OSExecute.command(
            "java net.mindview.atunit.AtUnit AtUnitExample3");
  }
} /* Output:
annotations.AtUnitExample3
  . initialization
  . methodOneTest
  . m2 This is methodTwo

OK (3 tests)
*///:~
/**
 * 加入了@TestObjectCreaet注解的方法必须声明为static，且必须返回一个你正在测试的类型的对象，这一切都由@Unit负责确保成立。
 *
 * 有的时候，我们需要向单元测试中添加一些额外的域。这时可以使用@TestProperty注解，由它注解的域表示只在单元测试中使用（因此，在我们将产品发布给客户之前，他们应该被删除掉）。
 * 在下面的例子中，一个String通过String-splitO方法被拆散了，从其中读取一个值，这个值将被用来生成测试对象∶
 */
class AtUnitExample4 {
  static String theory = "All brontosauruses " +
          "are thin at one end, much MUCH thicker in the " +
          "middle, and then thin again at the far end.";
  private String word;
  private Random rand = new Random(); // Time-based seed
  public AtUnitExample4(String word) { this.word = word; }
  public String getWord() { return word; }
  public String scrambleWord() {
    List<Character> chars = new ArrayList<Character>();
    for(Character c : word.toCharArray())
      chars.add(c);
    Collections.shuffle(chars, rand);
    StringBuilder result = new StringBuilder();
    for(char ch : chars)
      result.append(ch);
    return result.toString();
  }
  @TestProperty
  static List<String> input =
          Arrays.asList(theory.split(" "));
  @TestProperty
  static Iterator<String> words = input.iterator();
  @TestObjectCreate static AtUnitExample4 create() {
    if(words.hasNext())
      return new AtUnitExample4(words.next());
    else
      return null;
  }
  @Test boolean words() {
    print("'" + getWord() + "'");
    return getWord().equals("are");
  }
  @Test boolean scramble1() {
    // Change to a specific seed to get verifiable results:
    rand = new Random(47);
    print("'" + getWord() + "'");
    String scrambled = scrambleWord();
    print(scrambled);
    return scrambled.equals("lAl");
  }
  @Test boolean scramble2() {
    rand = new Random(74);
    print("'" + getWord() + "'");
    String scrambled = scrambleWord();
    print(scrambled);
    return scrambled.equals("tsaeborornussu");
  }
  public static void main(String[] args) throws Exception {
    System.out.println("starting");
    OSExecute.command(
            "java net.mindview.atunit.AtUnit AtUnitExample4");
  }
} /* Output:
starting
annotations.AtUnitExample4
  . scramble1 'All'
lAl

  . scramble2 'brontosauruses'
tsaeborornussu

  . words 'are'

OK (3 tests)
*///:~
/**
 * @TestProperty也可以用来标记那些只在测试中使用的方法，而他们本身又不是测试方法。注意，这个程序依赖于测试执行的顺序，这可不是一个好的实践。
 *
 * 如果你的测试对象需要执行某些初始化工作，并且使用完毕后还需要进行某些清理工作，那么可以选择使用static @TestObjectCleanup方法，当测试对象使用结束后，该方法会为你执行清理工作。
 * 在下面的例子中，@TestObjectCreate为每个测试对象打开了一个文件，因此必须在丢弃测试对象的时候关闭该文件∶
 */
class AtUnitExample5 {
  private String text;
  public AtUnitExample5(String text) { this.text = text; }
  public String toString() { return text; }
  @TestProperty static PrintWriter output;
  @TestProperty static int counter;
  @TestObjectCreate static AtUnitExample5 create() {
    String id = Integer.toString(counter++);
    try {
      output = new PrintWriter("Test" + id + ".txt");
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    return new AtUnitExample5(id);
  }
  @TestObjectCleanup
  static void
  cleanup(AtUnitExample5 tobj) {
    System.out.println("Running cleanup");
    output.close();
  }
  @Test boolean test1() {
    output.print("test1");
    return true;
  }
  @Test boolean test2() {
    output.print("test2");
    return true;
  }
  @Test boolean test3() {
    output.print("test3");
    return true;
  }
  public static void main(String[] args) throws Exception {
    OSExecute.command(
            "java net.mindview.atunit.AtUnit AtUnitExample5");
  }
} /* Output:
annotations.AtUnitExample5
  . test1
Running cleanup
  . test2
Running cleanup
  . test3
Running cleanup
OK (3 tests)
*///:~
/**
 * 从输出中我们可以看到，清理方法会在每个测试结束后自动运行。
 */
