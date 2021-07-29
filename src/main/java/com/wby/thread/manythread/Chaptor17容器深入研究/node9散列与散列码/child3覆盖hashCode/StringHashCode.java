package com.wby.thread.manythread.Chaptor17容器深入研究.node9散列与散列码.child3覆盖hashCode;//: containers/StringHashCode.java

import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.pets.*;

import java.util.*;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 在明白了如何散列之后，编写自己的hashCodeO就更有意义了。
 *
 * 首先，你无法控制bucket数组的下标值的产生。这个值依赖于具体的HashMap对象的容量，而容量的改变与容器的充满程度和负载因子（本章稍后会介绍这个术语）有关。
 * hashCodeO生成的结果，经过处理后成为桶位的下标（在SimpleHashMap中，只是对其取模，模数为bucket数组的大小）。
 *
 * 设计hashCodeO时最重要的因素就是;无论何时，对同一个对象调用hashCode（）都应该生成同样的值。如果在将一个对象用put（）添加进HashMap时产生一个hashCodeO值，
 * 而用get（）取出时却产生了另一个hashCode（）值，那么就无法重新取得该对象了。所以，如果你的 hashCode（）方法依赖于对象中易变的数据，用户就要当心了，
 * 因为此数据发生变化时， hashCodeO）就会生成一个不同的散列码，相当于产生了一个不同的键。
 *
 * 此外，也不应该使hashCodeO依赖于具有唯一性的对象信息，尤其是使用this的值，这只能产生很糟糕的hashCode（）。因为这样做无法生成一个新的键，使之与putO中原始的键值对中的键相同。
 * 这正是SpringDetectorjava的问题所在，因为它默认的hashCodeO使用的是对象的地址。所以，应该使用对象内有意义的识别信息。
 *
 * 下面以String类为例。String有个特点∶ 如果程序中有多个String对象，都包含相同的字符串序列，那么这些String对象都映射到同一块内存区域。
 * 所以new String（"hello"）生成的两个实例，虽然是相互独立的，但是对它们使用hashCodeO应该生成同样的结果。通过下面的程序可以看到这种情况∶
*/
public class StringHashCode {
  public static void main(String[] args) {
    String[] hellos = "Hello Hello".split(" ");
    System.out.println(hellos[0].hashCode());
    System.out.println(hellos[1].hashCode());
  }
} /* Output: (Sample)
69609650
69609650
*///:~
/**
* @Description: 对于String而言，hashCode（）明显是基于String的内容的。
 *
 * 因此，要想使hashCode（）实用，它必须速度快，并且必须有意义。也就是说，它必须基于对象的内容生成散列码。记得吗，散列码不必是独一无二的（应该更关注生成速度，而不是唯一性），
 * 但是通过hashCodeO和equalsO，必须能够完全确定对象的身份。
 *
 * 因为在生成桶的下标前，hashCode（）还需要做进一步的处理，所以散列码的生成范围并不重要，只要是int即可。
 *
 * 还有另一个影响因素∶ 好的hashCode（）应该产生分布均匀的散列码。如果散列码都集中在一块，那么HashMap或者HashSet在某些区域的负载会很重，这样就不如分布均匀的散列函数快。
 *
 * 在Effective Java Programming Language Guide （Addison-Wesley 2001）这本书中，Joshua Bloch为怎样写出一份像样的hashCode（）给出了基本的指导∶
 * 1）给int变量result赋予某个非零值常量，例如17。
 * 2）为对象内每个有意义的域f（即每个可以做equals（）操作的域）计算出一个int散列码c∶
 * 域类型                                                                          计 算
 * boolean                                                                        c=(f?0:1)
 * byte、char、short或int                                                          c=(int)f
 * long                                                                           c= (int)(f ^( >>>32))
 * float                                                                          c = Float.floatToIntBits(f);
 * double                                                                         long l= Double.doubleToLongBits(f);
 *
 * Object， 其equals（）调用这个域的 equals（）                                        c= (int)(0 ^1>>> 32))
 *                                                                                 c= f.hashCode()
 * 数组                                                                             对每个元素应用上述规则
 *
 * 3） 合并计算得到的散列码∶
 *  result = 37 * result ＋ c;
 *
 * 4）返回 result。
 *
 * 5）检查hashCodeO最后生成的结果，确保相同的对象有相同的散列码。
 *
 * 下面便是遵循这些指导的一个例子∶
*/
class CountedString {
  private static List<String> created =
          new ArrayList<String>();
  private String s;
  private int id = 0;
  public CountedString(String str) {
    s = str;
    created.add(s);
    // id is the total number of instances
    // of this string in use by CountedString:
    for(String s2 : created)
      if(s2.equals(s))
        id++;
  }
  public String toString() {
    return "String: " + s + " id: " + id +
            " hashCode(): " + hashCode();
  }
  public int hashCode() {
    // The very simple approach:
    // return s.hashCode() * id;
    // Using Joshua Bloch's recipe:
    int result = 17;
    result = 37 * result + s.hashCode();
    result = 37 * result + id;
    return result;
  }
  public boolean equals(Object o) {
    return o instanceof CountedString &&
            s.equals(((CountedString)o).s) &&
            id == ((CountedString)o).id;
  }
  public static void main(String[] args) {
    Map<CountedString,Integer> map =
            new HashMap<CountedString,Integer>();
    CountedString[] cs = new CountedString[5];
    for(int i = 0; i < cs.length; i++) {
      cs[i] = new CountedString("hi");
      map.put(cs[i], i); // Autobox int -> Integer
    }
    print(map);
    for(CountedString cstring : cs) {
      print("Looking up " + cstring);
      print(map.get(cstring));
    }
  }
} /* Output: (Sample)
{String: hi id: 4 hashCode(): 146450=3, String: hi id: 1 hashCode(): 146447=0, String: hi id: 3 hashCode(): 146449=2, String: hi id: 5 hashCode(): 146451=4, String: hi id: 2 hashCode(): 146448=1}
Looking up String: hi id: 1 hashCode(): 146447
0
Looking up String: hi id: 2 hashCode(): 146448
1
Looking up String: hi id: 3 hashCode(): 146449
2
Looking up String: hi id: 4 hashCode(): 146450
3
Looking up String: hi id: 5 hashCode(): 146451
4
*///:~
/**
* @Description: CountedString由一个String和一个id组成，此id代表包含相同String的CountedString对象的编号。所有的String都被存储在static ArrayList中，
 * 在构造器中通过迭代遍历此ArrayList完成对id的计算。
 *
 * hashCode（）和equals（）都基于CountedString的这两个域来生成结果;如果它们只基于String或者只基于id，不同的对象就可能产生相同的值。
 *
 * 在mainO中，使用相同的String创建了多个CountedString对象。这说明，虽然String相同，但是由于id不同，所以使得它们的散列码并不相同。
 * 在程序中，HashMap被打印了出来，因此可以看到它内部是如何存储元素的（以无法辨别的次序），然后单独查询每一个键，以此证明查询机制工作正常。
 *
 * 作为第二个示例，请考虑Individual类，它被用作第14章中所定义的typeinfo.pet类库的基类。 Individual类在那一章中就用到了，而它的定义则放到了本章，因此你可以正确地理解其实现;
*/
class Individual implements Comparable<Individual> {
  private static long counter = 0;
  private final long id = counter++;
  private String name;
  public Individual(String name) { this.name = name; }
  // 'name' is optional:
  public Individual() {}
  public String toString() {
    return getClass().getSimpleName() +
            (name == null ? "" : " " + name);
  }
  public long id() { return id; }
  public boolean equals(Object o) {
    return o instanceof Individual &&
            id == ((Individual)o).id;
  }
  public int hashCode() {
    int result = 17;
    if(name != null)
      result = 37 * result + name.hashCode();
    result = 37 * result + (int)id;
    return result;
  }
  public int compareTo(Individual arg) {
    // Compare by class name first:
    String first = getClass().getSimpleName();
    String argFirst = arg.getClass().getSimpleName();
    int firstCompare = first.compareTo(argFirst);
    if(firstCompare != 0)
      return firstCompare;
    if(name != null && arg.name != null) {
      int secondCompare = name.compareTo(arg.name);
      if(secondCompare != 0)
        return secondCompare;
    }
    return (arg.id < id ? -1 : (arg.id == id ? 0 : 1));
  }
} ///:~
/**
* @Description: compareTO0方法有一个比较结构，因此它会产生一个排序序列，排序的规则首先按照实际类型排序，然后如果有名字的话，按照name排序，
 * 最后按照创建的顺序排序。下面的示例说明了它是如何工作的∶
*/
class IndividualTest {
  public static void main(String[] args) {
    /*Set<Individual> pets = new TreeSet<Individual>();*/
    Set<Pet> pets = new TreeSet<Pet>();
    for(List<? extends Pet> lp : MapOfList.petPeople.values()){
      for(Pet p : lp){
        pets.add(p);
      }
    }

    System.out.println(pets);
  }
} /* Output:
[Cat Elsie May, Cat Pinkola, Cat Shackleton, Cat Stanford aka Stinky el Negro, Cymric Molly, Dog Margrett, Mutt Spot, Pug Louie aka Louis Snorkelstein Dupree, Rat Fizzy, Rat Freckly, Rat Fuzzy]
*///:~
/**
* @Description: 由于所有的宠物都有名字，因此它们首先按照类型排序，然后在同类型中按照名字排序。为新类编写正确的hashCodeO和equalsO是很需要技巧的。Apache的Jakarta Commons项目中
 * 有许多工具可以帮助你完成此事，该项目可在jakarta.apache.org/commons的lang下面找到。（此项目还包括许多其他有用的类库，而且它似乎是Java社区对C++的www.boost.org作出的回应）。
*/
class MapOfList {
  public static Map<Person, List<? extends Pet>>
          petPeople = new HashMap<Person, List<? extends Pet>>();
  static {
    petPeople.put(new Person("Dawn"),
            Arrays.asList(new Cymric("Molly"),new Mutt("Spot")));
    petPeople.put(new Person("Kate"),
            Arrays.asList(new Cat("Shackleton"),
                    new Cat("Elsie May"), new Dog("Margrett")));
    petPeople.put(new Person("Marilyn"),
            Arrays.asList(
                    new Pug("Louie aka Louis Snorkelstein Dupree"),
                    new Cat("Stanford aka Stinky el Negro"),
                    new Cat("Pinkola")));
    petPeople.put(new Person("Luke"),
            Arrays.asList(new Rat("Fuzzy"), new Rat("Fizzy")));
    petPeople.put(new Person("Isaac"),
            Arrays.asList(new Rat("Freckly")));
  }
  public static void main(String[] args) {
    print("People: " + petPeople.keySet());
    print("Pets: " + petPeople.values());
    for(Person person : petPeople.keySet()) {
      print(person + " has:");
      for(Pet pet : petPeople.get(person))
        print("    " + pet);
    }
  }
} /* Output:
People: [Person Luke, Person Marilyn, Person Isaac, Person Dawn, Person Kate]
Pets: [[Rat Fuzzy, Rat Fizzy], [Pug Louie aka Louis Snorkelstein Dupree, Cat Stanford aka Stinky el Negro, Cat Pinkola], [Rat Freckly], [Cymric Molly, Mutt Spot], [Cat Shackleton, Cat Elsie May, Dog Margrett]]
Person Luke has:
    Rat Fuzzy
    Rat Fizzy
Person Marilyn has:
    Pug Louie aka Louis Snorkelstein Dupree
    Cat Stanford aka Stinky el Negro
    Cat Pinkola
Person Isaac has:
    Rat Freckly
Person Dawn has:
    Cymric Molly
    Mutt Spot
Person Kate has:
    Cat Shackleton
    Cat Elsie May
    Dog Margrett
*///:~
