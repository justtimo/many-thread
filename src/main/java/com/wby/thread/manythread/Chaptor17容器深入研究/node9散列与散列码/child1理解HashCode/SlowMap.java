package com.wby.thread.manythread.Chaptor17容器深入研究.node9散列与散列码.child1理解HashCode;//: containers/SlowMap.java
// A Map implemented with ArrayLists.

import com.wby.thread.manythread.net.mindview.util.Countries;

import java.util.*;

/**
* @Description: 前面的例子只是正确解决问题的第一步。它只说明，如果不为你的键覆盖hashCode（）和 equalsO，
 * 那么使用散列的数据结构（HashSet，HashMap，LinkedHashSet或LinkedHashMap）就无法正确处理你的键。然而，要很好地解决此问题，你必须了解这些数据结构的内部构造。
 *
 * 首先，使用散列的目的在于∶想要使用一个对象来查找另一个对象。
 * 不过使用TreeMap或者你自己实现的Map也可以达到此目的。
 * 与散列实现相反，下面的示例用一对ArrayLists实现了一个Map。与AssociativeArray.java不同，这其中包含了Map接口的完整实现，因此提供了 entrySet（）方法∶
 *
 * class MapEntry是因为报错而是用IDEA自动创建的一个类，可忽略
*/
public class SlowMap<K,V> extends AbstractMap<K,V> {
  private List<K> keys = new ArrayList<K>();
  private List<V> values = new ArrayList<V>();
  public V put(K key, V value) {
    V oldValue = get(key); // The old value or null
    if(!keys.contains(key)) {
      keys.add(key);
      values.add(value);
    } else
      values.set(keys.indexOf(key), value);
    return oldValue;
  }
  public V get(Object key) { // key is type Object, not K
    if(!keys.contains(key))
      return null;
    return values.get(keys.indexOf(key));
  }
  public Set<Entry<K,V>> entrySet() {
    Set<Entry<K,V>> set= new HashSet<Entry<K,V>>();
    Iterator<K> ki = keys.iterator();
    Iterator<V> vi = values.iterator();
    while(ki.hasNext())
      set.add(new MapEntry<K,V>(ki.next(), vi.next()));
    return set;
  }
  public static void main(String[] args) {
    SlowMap<String,String> m= new SlowMap<String,String>();
    m.putAll(Countries.capitals(15));
    System.out.println(m);
    System.out.println(m.get("BULGARIA"));
    System.out.println(m.entrySet());
  }

} /* Output:
{CAMEROON=Yaounde, CHAD=N'djamena, CONGO=Brazzaville, CAPE VERDE=Praia, ALGERIA=Algiers, COMOROS=Moroni, CENTRAL AFRICAN REPUBLIC=Bangui, BOTSWANA=Gaberone, BURUNDI=Bujumbura, BENIN=Porto-Novo, BULGARIA=Sofia, EGYPT=Cairo, ANGOLA=Luanda, BURKINA FASO=Ouagadougou, DJIBOUTI=Dijibouti}
Sofia
[CAMEROON=Yaounde, CHAD=N'djamena, CONGO=Brazzaville, CAPE VERDE=Praia, ALGERIA=Algiers, COMOROS=Moroni, CENTRAL AFRICAN REPUBLIC=Bangui, BOTSWANA=Gaberone, BURUNDI=Bujumbura, BENIN=Porto-Novo, BULGARIA=Sofia, EGYPT=Cairo, ANGOLA=Luanda, BURKINA FASO=Ouagadougou, DJIBOUTI=Dijibouti]
*///:~
/**
 * @Description: putO方法只是将键与值放入相应的ArrayList。为了与Map接口保持一致，它必须返回旧的键，或者在没有任何旧键的情况下返回null。
 * <p>
 * 同样遵循了Map规范，getO会在键不在SlowMap中的时候产生null。如果键存在，它将被用来查找表示它在keys列表中的位置的数值型索引，并且这个数字被用作索引来产生与values列表相关联的值。
 * 注意，在getO中key的类型是Object，而不是你所期望的参数化类型K（并且是在 AssociativeArray.java中真正使用的类型）。这是将泛型注入到Java语言中的时刻如此之晚所导致的结果——
 * 如果泛型是Java语言最初就具备的属性，那么get（）就可以执行其参数的类型。
 * <p>
 * Map.entrySet（）方法必须产生一个Map.Entry对象集。但是，Map.Entry是一个接口，用来描述依赖于实现的结构，因此如果你想要创建自己的Map类型，就必须同时定义Map.Entry的实现∶
 */

