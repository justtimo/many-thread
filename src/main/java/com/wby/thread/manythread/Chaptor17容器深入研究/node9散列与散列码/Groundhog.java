package com.wby.thread.manythread.Chaptor17容器深入研究.node9散列与散列码;//: containers/Groundhog.java
// Looks plausible, but doesn't work as a HashMap key.

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 在第11章的例子中，标准类库中的类被用作HashMap的键。它用得很好，因为它具备了键所需的全部性质。
 *
 * 当你自己创建用作HashMap的键的类，有可能会忘记在其中放置必需的方法，而这是通常会犯的一个错误。
 * 例如，考虑一个天气预报系统，将Groundhog（土拨鼠）对象与Prediction（预报）对象联系起来。
 * 这看起来很简单。创建这两个类，使用Groundhog作为键，Prediction作为值∶
*/
public class Groundhog {
  protected int number;
  public Groundhog(int n) { number = n; }
  public String toString() {
    return "Groundhog #" + number;
  }
} ///:~

class Prediction {
  private static Random rand = new Random(47);
  private boolean shadow = rand.nextDouble() > 0.5;
  public String toString() {
    if(shadow)
      return "Six more weeks of Winter!";
    else
      return "Early Spring!";
  }
} ///:~

class SpringDetector {
  // Uses a Groundhog or class derived from Groundhog:
  public static <T extends Groundhog>
  void detectSpring(Class<T> type) throws Exception {
    Constructor<T> ghog = type.getConstructor(int.class);
    Map<Groundhog,Prediction> map =
            new HashMap<Groundhog,Prediction>();
    for(int i = 0; i < 10; i++)
      map.put(ghog.newInstance(i), new Prediction());
    print("map = " + map);
    Groundhog gh = ghog.newInstance(3);
    print("Looking up prediction for " + gh);
    if(map.containsKey(gh))
      print(map.get(gh));
    else
      print("Key not found: " + gh);
  }
  public static void main(String[] args) throws Exception {
    detectSpring(Groundhog.class);
  }
} /* Output:
map = {Groundhog #3=Early Spring!, Groundhog #7=Early Spring!, Groundhog #5=Early Spring!, Groundhog #9=Six more weeks of Winter!, Groundhog #8=Six more weeks of Winter!, Groundhog #0=Six more weeks of Winter!, Groundhog #6=Early Spring!, Groundhog #4=Six more weeks of Winter!, Groundhog #1=Six more weeks of Winter!, Groundhog #2=Early Spring!}
Looking up prediction for Groundhog #3
Key not found: Groundhog #3
*///:~
/**
* @Description: 每个Groundhog被给予一个标识数字，于是可以在HashMap中这样查找Prediction∶"给我与Groundhog#3相关的Prediction。"Prediction类包含一个boolean值和一个toString（）方法。
 * boolean值使用java.util.randomO来初始化;
 * 而toStringO方法则解释结果。detectSpringO方法使用反射机制来实例化及使用Groundhog类或任何从Groundhog派生出来的类。
 * 如果我们为解决当前的问题从Groundhog继承创建了一个新类型的时候，detectSpringO方法使用的这个技巧就变得很有用了。
 *
 * 首先会使用Groundhog和与之相关联的Prediction填充HashMap，然后打印此HashMap，以便可以观察它是否被填入了一些内容。然后使用标识数字为3的Groundhog作为键，
 * 查找与之对应的预报内容（可以看到，它一定是在Map中）。
 *
 * 这看起来够简单了，但是它不工作——它无法找到数字3这个键。问题出在Groundhog自动地继承自基类Object，所以这里使用Object的hashCode（）方法生成散列码，而它默认是使用对象
 * 的地址计算散列码。因此，由Groundhog（3）生成的第一个实例的散列码与由Groundhog（3）生成的第二个实例的散列码是不同的，而我们正是使用后者进行查找的。
 *
 * 可能你会认为，只需编写恰当的hashCode（）方法的覆盖版本即可。但是它仍然无法正常运行，除非你同时覆盖equalsO方法，它也是Obiject的一部分。HashMap使用equalsO判断当前的键是否与表中存在的键相同。
 *
 * 正确的equals（方法必须满足下列5个条件;
 *    1）自反性。对任意x，x.equals（x）一定返回true。
 *    2）对称性。对任意x和y，如果y.equals（x）返回true，则x.equals（y）也返回true。
 *    3）传递性。对任意x、y、z，如果有xequals（y）返回ture，y.equals（z）返回true，则x.equals（z）一定返回true。
 *    4）一致性。对任意x和y，如果对象中用于等价比较的信息没有改变，那么无论调用 xequals（y）多少次，返回的结果应该保持一致，要么一直是true，要么一直是false。
 *    5）对任何不是null的x，x.equals（nullI）一定返回false。
 *
 * 再次强调，默认的Object.equalsO只是比较对象的地址，所以一个Groundhog（3）并不等于另一个Groundhog（3）。
 * 因此，如果要使用自已的类作为HashMap的键，必须同时重载hashCodeO和equals（，如下所示∶
*/
class Groundhog2 extends Groundhog {
  public Groundhog2(int n) { super(n); }
  public int hashCode() { return number; }
  public boolean equals(Object o) {
    return o instanceof Groundhog2 &&
            (number == ((Groundhog2)o).number);
  }
} ///:~

class SpringDetector2 {
  public static void main(String[] args) throws Exception {
    SpringDetector.detectSpring(Groundhog2.class);
  }
} /* Output:
map = {Groundhog #2=Early Spring!, Groundhog #4=Six more weeks of Winter!, Groundhog #9=Six more weeks of Winter!, Groundhog #8=Six more weeks of Winter!, Groundhog #6=Early Spring!, Groundhog #1=Six more weeks of Winter!, Groundhog #3=Early Spring!, Groundhog #7=Early Spring!, Groundhog #5=Early Spring!, Groundhog #0=Six more weeks of Winter!}
Looking up prediction for Groundhog #3
Early Spring!
*///:~
/**
* @Description: Groundhog2.hashCode（）返回Groundhog的标识数字（编号）作为散列码。在此例中，程序员负责确保不同的Groundhog具有不同的编号。
 * hashCodeO）并不需要，总是能够返回唯一的标识码（稍后读者会理解其原因），但是equals（）方法必须严格地判断两个对象是否相同。此处的 equalsO）是判断Groundhog的号码，
 * 所以作为HashMap中的键，如果两个Groundhog2对象具有相同的Groundhog编号，程序就出错了。
 *
 * 尽管看起来equals（）方法只是检查其参数是否是Groundhog2的实例（使用第14章中介绍过的instanceof关键字），但是instanceof悄悄地检查了此对象是否为null，
 * 因为如果instanceof左边的参数为null，它会返回false。如果equals（的参数不为nulI目类型正确，则基干每个对象中实际的number数值进行比较。
 * 从输出中可以看到，现在的方式是正确的。当在HashSet中使用自己的类作为键时，必须注意这个问题。
*/
