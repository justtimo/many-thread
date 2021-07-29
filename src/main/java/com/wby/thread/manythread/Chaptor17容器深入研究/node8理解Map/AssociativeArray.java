package com.wby.thread.manythread.Chaptor17容器深入研究.node8理解Map;//: containers/AssociativeArray.java
// Associates keys with values.

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 正如你在第11章中所学到的，映射表（也称为关联数组）的基本思想是它维护的是键-值（对）关联，因此你可以使用键来查找值。标准的Java类库中包含了Map的几种基本实现，
 * 包括∶ HashMap,TreeMap,LinkedHashMap,WeakHashMap,ConcurrentHashMap, IdentityHashMap。
 * 它们都有同样的基本接口Map，但是行为特性各不相同，这表现在效率、键值对的保存及呈现次序、对象的保存周期、映射表如何在多线程程序中工作和判定"键"等价的策略等方面。
 * Map接口实现的数量应该可以让你感觉到这种工具的重要性。
 * 你可以获得对Map更深入的理解，这有助于观察关联数组是如何创建的。下面是一个极其简单的实现∶
*/
public class AssociativeArray<K,V> {
  private Object[][] pairs;
  private int index;
  public AssociativeArray(int length) {
    pairs = new Object[length][2];
  }
  public void put(K key, V value) {
    if(index >= pairs.length)
      throw new ArrayIndexOutOfBoundsException();
    pairs[index++] = new Object[]{ key, value };
  }
  @SuppressWarnings("unchecked")
  public V get(K key) {
    for(int i = 0; i < index; i++)
      if(key.equals(pairs[i][0]))
        return (V)pairs[i][1];
    return null; // Did not find key
  }
  public String toString() {
    StringBuilder result = new StringBuilder();
    for(int i = 0; i < index; i++) {
      result.append(pairs[i][0].toString());
      result.append(" : ");
      result.append(pairs[i][1].toString());
      if(i < index - 1)
        result.append("\n");
    }
    return result.toString();
  }
  public static void main(String[] args) {
    AssociativeArray<String,String> map =
      new AssociativeArray<String,String>(6);
    map.put("sky", "blue");
    map.put("grass", "green");
    map.put("ocean", "dancing");
    map.put("tree", "tall");
    map.put("earth", "brown");
    map.put("sun", "warm");
    try {
      map.put("extra", "object"); // Past the end
    } catch(ArrayIndexOutOfBoundsException e) {
      print("Too many objects!");
    }
    print(map);
    print(map.get("ocean"));
  }
} /* Output:
Too many objects!
sky : blue
grass : green
ocean : dancing
tree : tall
earth : brown
sun : warm
dancing
*///:~
/**
* @Description: 关联数组中的基本方法是put（和get（），但是为了容易显示，toStringO方法被覆盖为可以打印键-值对。为了展示它可以工作，mainO用字符串对加载了一个AssociativeArray，
 * 并打印了所产生的映射表，随后是获取一个值的getO）。
 *
 * 为了使用get（）方法，你需要传递想要查找的key，然后它会将与之相关联的值作为结果返回，或者在找不到的情况下返回null。
 * getO方法使用的可能是能想象到的效率最差的方式来定位值的∶从数组的头部开始，使用equals（方法依次比较键。但这里的关键是简单性而不是效率。
 *
 * 因此上面的版本是说明性的，但是缺乏效率，并且由于具有固定的尺寸而显得很不灵活。幸运的是，在java.util中的各种Map都没有这些问题，并且都可以替代到上面的示例中。
*/
