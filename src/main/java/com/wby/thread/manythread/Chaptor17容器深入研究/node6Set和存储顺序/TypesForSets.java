package com.wby.thread.manythread.Chaptor17容器深入研究.node6Set和存储顺序;//: containers/TypesForSets.java
// Methods necessary to put your own type in a Set.

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
/**
* @Description: 11章中set示例对基本的Set执行的操作提供了很好的介绍。
 * 当你创建自己的类型是，要意识到Set需要一种方式来维护存储顺序，而存储顺序如何维护，则是在Set的不同实现之间会有所变化。
 * 因此，不同的Set实现不仅具有不同的行为，而且他们对于可以在特定的Set中放置的元素的类型也有不同的要求：
 *  Set 存入Set的每个元素必须唯一，因为Set不保存重复原宿。加入Set的元素必须定义equals方法确保对象的唯一性。Set与Collection有完全一样的接口。
 *      Set接口不保证维护元素的次序
 *  HashSet*  为快速查找而设计的Set。存入HashSet的元素必须定义hashCode()
 *  TreeSet   保证次序的Set，底层为树结构。使用它可以从Set中提取有序的序列。元素必须实现Comparable接口
 *  LinkedHashSet   具有HashSet的查询速度，且内部使用链表维护元素的顺序(插入的次序).于是在使用迭代器遍历Set时，结果会按元素插入的次序显示。
 *                  元素必须定义hashCode方法
 *
 *  在HashSet上打星号表示：如果没有其他限制，这应该是你默认的选择。因为他对速度进行了优化。
 *
 *  定义hashCode方法的机制稍后介绍。你必须为散列存储和树形存储都创建一个equals方法，但是hashCode方法只有在这个类将会被置于HashSet(这是可能的，因为
 *  HashSet通常是你Set实现的首选)或者LinkedHashSet中是才是必需的。但是，对于良好的编程风格而言，你应该在覆盖equals方法时，总是同时覆盖hashCode方法。
 *
 *  下面的例子演示了为了成功地使用特定的Set实现类型而必须定义的方法：
*/
class SetType {
  int i;
  public SetType(int n) { i = n; }
  public boolean equals(Object o) {
    return o instanceof SetType && (i == ((SetType)o).i);
  }
  public String toString() { return Integer.toString(i); }
}

class HashType extends SetType {
  public HashType(int n) { super(n); }
  public int hashCode() { return i; }
}

class TreeType extends SetType
implements Comparable<TreeType> {
  public TreeType(int n) { super(n); }
  public int compareTo(TreeType arg) {
    return (arg.i < i ? -1 : (arg.i == i ? 0 : 1));
  }
}

public class TypesForSets {
  static <T> Set<T> fill(Set<T> set, Class<T> type) {
    try {
      for(int i = 0; i < 10; i++)
          set.add(
            type.getConstructor(int.class).newInstance(i));
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
    return set;
  }
  static <T> void test(Set<T> set, Class<T> type) {
    fill(set, type);
    fill(set, type); // Try to add duplicates
    fill(set, type);
    System.out.println(set);
  }
  public static void main(String[] args) {
    test(new HashSet<HashType>(), HashType.class);
    test(new LinkedHashSet<HashType>(), HashType.class);
    test(new TreeSet<TreeType>(), TreeType.class);
    // Things that don't work:
    test(new HashSet<SetType>(), SetType.class);
    test(new HashSet<TreeType>(), TreeType.class);
    test(new LinkedHashSet<SetType>(), SetType.class);
    test(new LinkedHashSet<TreeType>(), TreeType.class);
    try {
      test(new TreeSet<SetType>(), SetType.class);
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }
    try {
      test(new TreeSet<HashType>(), HashType.class);
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }
  }
} /* Output: (Sample)
[2, 4, 9, 8, 6, 1, 3, 7, 5, 0]
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
[9, 9, 7, 5, 1, 2, 6, 3, 0, 7, 2, 4, 4, 7, 9, 1, 3, 6, 2, 4, 3, 0, 5, 0, 8, 8, 8, 6, 5, 1]
[0, 5, 5, 6, 5, 0, 3, 1, 9, 8, 4, 2, 3, 9, 7, 3, 4, 4, 0, 7, 1, 9, 6, 2, 1, 8, 2, 8, 6, 7]
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
java.lang.ClassCastException: SetType cannot be cast to java.lang.Comparable
java.lang.ClassCastException: HashType cannot be cast to java.lang.Comparable
*///:~
/**
* @Description: 为了证明那些方法对于某种特定的Set是必须的，并且同时还需要避免代码重复，我们创建了三个类。基类SetType只存储一个int，并且通过toString方法产生
 * 他的值，因为所有在Set中存储的类都必须具有equals（）方法，因此在基类中也有该方法。其等价性是基于这个int类型的i的值来确定的。
 * HashType继承自SetType，并且添加了hashCode（）方法，该方法对于放置到Set的散列实现中的对象来说是必需的。
 * TreeType实现了Comparable接口，如果一个对象被用于任何种类的排序容器中，例如 SortedSet（TreeSet是其唯一实现），那么它必须实现这个接口。
 * 注意，在compareTo中，我没有使用"简洁明了"的形式return i-i2，因为这是一个常见的编程错误，它只有在i和i2都是无符号的int（如果Java确实有unsigned关键字的话，
 * 但实际上并没有）时才能正确工作。对于Java的有符号int，它就会出错，因为int不够大，不足以表现两个有符号int的差。
 *
 * 例如i是很大的正整数，而j是很大的负整数，i-j就会溢出并且返回负值，这就不正确了。
 *
 * 你通常会希望compareTo方法可以产生与equals方法一致的自然排序。
 * 如果equals（）对于某个特定比较产生true，那么compareTo（）对于该比较应该返回0，如果equals方法对于某个比较产生false，那么compareTo对于该比较应该返回非0值。
 *
 * 在TypesForSets中，fill和test方法都是用泛型定义的，这是为了避免代码重复。为了验证某个Set的行为。test会在被测Set上调用fiII三次。尝试着在其中引入重复对象，filII方法可以
 * 接受任何类型的Set，以及其相同类型Class对象，它使用Class对象来发现并接受int参数的构造器，然后调用该构造器将元素添加到Set中。
 *
 * 从输出中可以看到，HashSet以某种神秘的顺序保存所有的元素 （这将在本章稍后进行解释）， LinkedHashSet按照元素插入的顺序保存元素，而TreeSet按照排序顺序维护元素 （
 * 按照 compareTo（）的实现方式，这里维护的是降序）。
 *
 * 如果我们尝试着将没有恰当地支持必需的操作的类型用于需要这些方法的Set，那么就会有大麻烦了。
 * 对于没有重新定义hashCode（）方法的SetType或TreeType，如果将它们放置到任何散列实现中都会产生重复值，这样就违反了Set的基本契约。这相当烦人，因为这甚至不会有运行时错误。
 * 但是，默认的hashCode是合法的，因此这是合法的行为，即便它不正确。
 * 确保这种程序的正确性的唯一可靠方法就是将单元测试合并到你的构建系统中。
 *
 * 如果我们尝试着在TreeSet中使用没有实现Comparable的类型。那么你将会得到更确定的结果∶ 在TreeSet试图将该对象当作Comparable使用时，将抛出一个异常。
 *
*/
