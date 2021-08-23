package com.wby.thread.manythread.character9接口.node6适配接口;//: interfaces/RandomWords.java
// Implementing an interface to conform to a method.

import java.nio.CharBuffer;
import java.util.Random;
import java.util.Scanner;

/**
 * 接口最吸引人的原因之一就是允许同一个接口具有多个不同的具体实现。在简单的情况中， 【331它的体现形式通常是一个接受接口类型的方法，而该接口的实现和向该方法传递的对象则取决于方法的使用者。
 *
 * 因此，接口的一种常见用法就是前面提到的策略设计模式，此时你编写一个执行某些操作的方法，而该方法将接受—个同样是你指定的接口。你主要就是要声明;"你可以用任何你想要的对象来调用我的方法，只要你的对象遵循我的接口。"
 * 这使得你的方法更加灵活、通用，并更具可复用性。
 *
 * 例如，Java SE5的Scanner类 （在第13章中就更多地了解它）的构造器接受的就是一个 Readable接口。你会发现Readable没有用作Java标准类库中其他任何方法的参数，
 * 它是单独为 Scanner创建的，以使得Scanner不必将其参数限制为某个特定类。通过这种方式，Scanner可以作用于更多的类型。
 * 如果你创建了一个新的类，并且想让Scanner可以作用于它，那么你就应该让它成为Readable，就像下面这样∶
 */
public class RandomWords implements Readable {
  private static Random rand = new Random(47);
  private static final char[] capitals =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  private static final char[] lowers =
    "abcdefghijklmnopqrstuvwxyz".toCharArray();
  private static final char[] vowels =
    "aeiou".toCharArray();
  private int count;
  public RandomWords(int count) { this.count = count; }
  public int read(CharBuffer cb) {
    if(count-- == 0)
      return -1; // Indicates end of input
    cb.append(capitals[rand.nextInt(capitals.length)]);
    for(int i = 0; i < 4; i++) {
      cb.append(vowels[rand.nextInt(vowels.length)]);
      cb.append(lowers[rand.nextInt(lowers.length)]);
    }
    cb.append(" ");
    return 10; // Number of characters appended
  }
  public static void main(String[] args) {
    Scanner s = new Scanner(new RandomWords(10));
    while(s.hasNext())
      System.out.println(s.next());
  }
} /* Output:
Yazeruyac
Fowenucor
Goeazimom
Raeuuacio
Nuoadesiw
Hageaikux
Ruqicibui
Numasetih
Kuuuuozog
Waqizeyoy
*///:~
/**
 * Readable接口只要求实现readO方法，在readO内部，将输入内容添加到CharBuffer参数中（有多种方法可以实现此目的，请查看CharBuffer的文档），或者在没有任何输入时返回-1。
 * 假设你有一个还未实现Readable的类，怎样才能让Scanner作用于它呢? 下面这个类就是一个例子，它可以产生随机浮点数∶
 */
class RandomDoubles {
  private static Random rand = new Random(47);
  public double next() { return rand.nextDouble(); }
  public static void main(String[] args) {
    RandomDoubles rd = new RandomDoubles();
    for(int i = 0; i < 7; i ++)
      System.out.print(rd.next() + " ");
  }
} /* Output:
0.7271157860730044 0.5309454508634242 0.16020656493302599 0.18847866977771732 0.5166020801268457 0.2678662084200585 0.2613610344283964
*///:~
/**
 * 我们再次使用了适配器模式，但是在本例中，被适配的类可以通过继承和实现Readable接口来创建。因此，通过使用interface关键字提供的伪多重继承机制，我们可以生成既是Random- Doubles又是Readable的新类∶
 */
class AdaptedRandomDoubles extends RandomDoubles
        implements Readable {
  private int count;
  public AdaptedRandomDoubles(int count) {
    this.count = count;
  }
  public int read(CharBuffer cb) {
    if(count-- == 0)
      return -1;
    String result = Double.toString(next()) + " ";
    cb.append(result);
    return result.length();
  }
  public static void main(String[] args) {
    Scanner s = new Scanner(new AdaptedRandomDoubles(7));
    while(s.hasNextDouble())
      System.out.print(s.nextDouble() + " ");
  }
} /* Output:
0.7271157860730044 0.5309454508634242 0.16020656493302599 0.18847866977771732 0.5166020801268457 0.2678662084200585 0.2613610344283964
*///:~
/**
 * 因为在这种方式中，我们可以在任何现有类之上添加新的接口，所以这意味着让方法接受接口类型，是一种让任何类都可以对该方法进行适配的方式。这就是使用接口而不是类的强大之处。
 */
