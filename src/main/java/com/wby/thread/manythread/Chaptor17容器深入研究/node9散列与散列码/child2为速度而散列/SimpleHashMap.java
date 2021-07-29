package com.wby.thread.manythread.Chaptor17容器深入研究.node9散列与散列码.child2为速度而散列;//: containers/SimpleHashMap.java
// A demonstration hashed Map.


import com.wby.thread.manythread.Chaptor17容器深入研究.node9散列与散列码.child1理解HashCode.MapEntry;
import com.wby.thread.manythread.net.mindview.util.Countries;

import java.util.*;

/**
* @Description: SlowMap.java说明了创建一种新的Map并不困难。但是正如它的名称SlowMap所示，它不会很快，所以如果有更好的选择，就应该放弃它。它的问题在于对键的查询，
 * 键没有按照任何特定顺序保存，所以只能使用简单的线性查询，而线性查询是最慢的查询方式。
 *
 * 散列的价值在于速度;散列使得查询得以快速进行。由于瓶颈位于键的查询速度，因此解决方案之一就是保持键的排序状态，然后使用Collections.binarySearchO进行查询（有一个练习会带领读者走完这个过程）。
 *
 * 散列则更进一步，它将键保存在某处，以便能够很快找到。存储一组元素最快的数据结构是数组，所以使用它来表示键的信息（请小心留意，我是说键的信息，而不是键本身）。
 * 但是因为数组不能调整容量，因此就有一个问题∶我们希望在Map中保存数量不确定的值，但是如果键的数量被数组的容量限制了，该怎么办呢?
 *
 * 答案就是∶数组并不保存键本身。而是通过键对象生成一个数字，将其作为数组的下标。这个数字就是散列码，由定义在Object中的、且可能由你的类覆盖的hashCodeO方法（在计算机科学的术语中称为散列函数）生成。
 *
 * 为解决数组容量被固定的问题，不同的键可以产生相同的下标。也就是说，可能会有冲突。因此，数组多大就不重要了，任何键总能在数组中找到它的位置。
 *
 * 于是查询—个值的过程首先就是计算散列码。然后使用散列码香询数组。如果能的够保证没有冲突（如果值的数量是固定的，那么就有可能），那可就有了一个完美的散列函数，
 * 但是这种情况只是特例(完美的散列函数在Java SE5的EnumMap和EnumSet中得到了实现，因为enum定义了固定数量的实例。请查看第19章。)。
 * 通常，冲突由外部链接处理∶数组并不直接保存值，而是保存值的list。然后对list中的值使用equalsO方法进行线性的查询。这部分的查询自然会比较慢，
 * 但是，如果散列函数好的话，数组的每个位置就只有较少的值。因此，不是查询整个list，而是快速地跳到数组的某个位置，只对很少的元素进行比较。这便是HashMap会如此快的原因。
 *
 * 理解了散列的原理，我们就能够实现一个简单的散列Map了∶
*/
public class SimpleHashMap<K,V> extends AbstractMap<K,V> {
  // Choose a prime number for the hash table
  // size, to achieve a uniform distribution:
  static final int SIZE = 997;
  // You can't have a physical array of generics,
  // but you can upcast to one:
  @SuppressWarnings("unchecked")
  LinkedList<MapEntry<K,V>>[] buckets =
    new LinkedList[SIZE];
  public V put(K key, V value) {
    V oldValue = null;
    int index = Math.abs(key.hashCode()) % SIZE;
    if(buckets[index] == null)
      buckets[index] = new LinkedList<MapEntry<K,V>>();
    LinkedList<MapEntry<K,V>> bucket = buckets[index];
    MapEntry<K,V> pair = new MapEntry<K,V>(key, value);
    boolean found = false;
    ListIterator<MapEntry<K,V>> it = bucket.listIterator();
    while(it.hasNext()) {
      MapEntry<K,V> iPair = it.next();
      if(iPair.getKey().equals(key)) {
        oldValue = iPair.getValue();
        it.set(pair); // Replace old with new
        found = true;
        break;
      }
    }
    if(!found)
      buckets[index].add(pair);
    return oldValue;
  }
  public V get(Object key) {
    int index = Math.abs(key.hashCode()) % SIZE;
    if(buckets[index] == null) return null;
    for(MapEntry<K,V> iPair : buckets[index])
      if(iPair.getKey().equals(key))
        return iPair.getValue();
    return null;
  }
  public Set<Entry<K,V>> entrySet() {
    Set<Entry<K,V>> set= new HashSet<Entry<K,V>>();
    for(LinkedList<MapEntry<K,V>> bucket : buckets) {
      if(bucket == null) continue;
      for(MapEntry<K,V> mpair : bucket)
        set.add(mpair);
    }
    return set;
  }
  public static void main(String[] args) {
    SimpleHashMap<String,String> m =
      new SimpleHashMap<String,String>();
    m.putAll(Countries.capitals(25));
    System.out.println(m);
    System.out.println(m.get("ERITREA"));
    System.out.println(m.entrySet());
  }
} /* Output:
{CAMEROON=Yaounde, CONGO=Brazzaville, CHAD=N'djamena, COTE D'IVOIR (IVORY COAST)=Yamoussoukro, CENTRAL AFRICAN REPUBLIC=Bangui, GUINEA=Conakry, BOTSWANA=Gaberone, BISSAU=Bissau, EGYPT=Cairo, ANGOLA=Luanda, BURKINA FASO=Ouagadougou, ERITREA=Asmara, THE GAMBIA=Banjul, KENYA=Nairobi, GABON=Libreville, CAPE VERDE=Praia, ALGERIA=Algiers, COMOROS=Moroni, EQUATORIAL GUINEA=Malabo, BURUNDI=Bujumbura, BENIN=Porto-Novo, BULGARIA=Sofia, GHANA=Accra, DJIBOUTI=Dijibouti, ETHIOPIA=Addis Ababa}
Asmara
[CAMEROON=Yaounde, CONGO=Brazzaville, CHAD=N'djamena, COTE D'IVOIR (IVORY COAST)=Yamoussoukro, CENTRAL AFRICAN REPUBLIC=Bangui, GUINEA=Conakry, BOTSWANA=Gaberone, BISSAU=Bissau, EGYPT=Cairo, ANGOLA=Luanda, BURKINA FASO=Ouagadougou, ERITREA=Asmara, THE GAMBIA=Banjul, KENYA=Nairobi, GABON=Libreville, CAPE VERDE=Praia, ALGERIA=Algiers, COMOROS=Moroni, EQUATORIAL GUINEA=Malabo, BURUNDI=Bujumbura, BENIN=Porto-Novo, BULGARIA=Sofia, GHANA=Accra, DJIBOUTI=Dijibouti, ETHIOPIA=Addis Ababa]
*///:~
/**
* @Description: 由于散列表中的"槽位"（slot）通常称为桶位（bucket），因此我们将表示实际散列表的数组命名为bucket。为使散列分布均匀，桶的数量通常使用质数é(事实证明，质数实际上并不是散列桶的理想容量。
 * 近来，（经过广泛的测试）Java的散列函数都使用2的整数次方。对现代的处理器来说，除法与求余数是最慢的操作。使用2的整数次方长度的散列表，可用掩码代替除法。
 * 因为getO是使用最多的操作，求余数的%操作是其开销最大的部分，而使用2的整数次方可以消除此开销（也可能对hashCode（）有些影响）。)
 * 注意，为了能够自动处理冲突，使用了一个LinkedList的数组;每一个新的元素只是直接添加到list末尾的某个特定桶位中。即使Java不允许你创建泛型数组，那你也可以创建指向这种数组的引用。
 * 这里，向上转型为这种数组是很方便的，这样可以防止在后面的代码中进行额外的转型。
 *
 * 对于putO方法，hashCode（）将针对键而被调用，并且其结果被强制转换为正数。为了使产生的数字适合bucket数组的大小，取模操作符将按照该数组的尺寸取模。如果数组的某个位置是null，
 * 这表示还没有元素被散列至此，所以，为了保存刚散列到该定位的对象，需要创建一个新的LinkedList。一般的过程是，查看当前位置的list中是否有相同的元素，如果有，则将旧的值赋给oldValue，
 * 然后用新的值取代旧的值。标记found用来跟踪是否找到（相同的）旧的键值对，如果没有，则将新的对添加到list的末尾。
 *
 * get（）方法按照与put（）方法相同的方式计算在buckets数组中的索引（这很重要，因为这样可以保证两个方法可以计算出相同的位置）如果此位置有LinkedList存在，就对其进行查询。
 *
 * 注意，这个实现并不意味着对性能进行了调优;它只是想要展示散列映射表执行的各种操作。如果你浏览一下java.util.HashMap的源代码，你就会看到一个调过优的实现。
 * 同样，为了简单，SimpleHashMap使用了与SlowMap相同的方式来实现entrySetO，这个方法有些过于简单，不能用于通用的Map。
*/
