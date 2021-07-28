//: net/mindview/util/Pair.java
package com.wby.thread.manythread.Chaptor17容器深入研究.node2填充容器.child2Map生成器;

import com.wby.thread.manythread.net.mindview.util.CountingGenerator;
import com.wby.thread.manythread.net.mindview.util.Generator;
import com.wby.thread.manythread.net.mindview.util.RandomGenerator;

import java.util.Iterator;
import java.util.LinkedHashMap;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 我们可以对Map使用相同的方式，但是这需要有一个Pair类，因为为了组装Map，每次调用Generator的next方法都必须产生一个对象对(一个键和一个值)
*/
public class Pair<K,V> {
  public final K key;
  public final V value;
  public Pair(K k, V v) {
    key = k;
    value = v;
  }
} ///:~
/**
* @Description: key和value域都是public和final的，这是为了使Pair成为只读的数据传输对象(或信使)。
 *  Map适配器现在可以使用各种不同的Generator、Iterator和常量值的组合来填充Map初始化对象：
*/
class MapData<K,V> extends LinkedHashMap<K,V> {
  // A single Pair Generator:
  public MapData(Generator<Pair<K,V>> gen, int quantity) {
    for(int i = 0; i < quantity; i++) {
      Pair<K,V> p = gen.next();
      put(p.key, p.value);
    }
  }
  // Two separate Generators:
  public MapData(Generator<K> genK, Generator<V> genV,
                 int quantity) {
    for(int i = 0; i < quantity; i++) {
      put(genK.next(), genV.next());
    }
  }
  // A key Generator and a single value:
  public MapData(Generator<K> genK, V value, int quantity){
    for(int i = 0; i < quantity; i++) {
      put(genK.next(), value);
    }
  }
  // An Iterable and a value Generator:
  public MapData(Iterable<K> genK, Generator<V> genV) {
    for(K key : genK) {
      put(key, genV.next());
    }
  }
  // An Iterable and a single value:
  public MapData(Iterable<K> genK, V value) {
    for(K key : genK) {
      put(key, value);
    }
  }
  // Generic convenience methods:
  public static <K,V> MapData<K,V>
  map(Generator<Pair<K,V>> gen, int quantity) {
    return new MapData<K,V>(gen, quantity);
  }
  public static <K,V> MapData<K,V>
  map(Generator<K> genK, Generator<V> genV, int quantity) {
    return new MapData<K,V>(genK, genV, quantity);
  }
  public static <K,V> MapData<K,V>
  map(Generator<K> genK, V value, int quantity) {
    return new MapData<K,V>(genK, value, quantity);
  }
  public static <K,V> MapData<K,V>
  map(Iterable<K> genK, Generator<V> genV) {
    return new MapData<K,V>(genK, genV);
  }
  public static <K,V> MapData<K,V>
  map(Iterable<K> genK, V value) {
    return new MapData<K,V>(genK, value);
  }
} ///:~
/**
* @Description: 这给了你一个机会，去选择使用单一的 Generator<Pair<K,V>> 、两个分离的Generator、一个Generator和一个常量值、一个Iterable(
 * 包括任何Collection)和一个Generator，还是一个Iterable和一个单一值。泛型便利方法可以减少在创建MapData类时所必需的类型检查数量。
 *
 * 下面是一个使用MapData的例子。LettersGenerator通过产生一个Iterator还实现了Iterable，通过这种方式，他可以被用来测试MapData.map()方法，而
 * 这些方法都需要用到Iterable：
*/
class Letters implements Generator<Pair<Integer,String>>,
        Iterable<Integer> {
  private int size = 9;
  private int number = 1;
  private char letter = 'A';
  public Pair<Integer,String> next() {
    return new Pair<Integer,String>(
            number++, "" + letter++);
  }
  public Iterator<Integer> iterator() {
    return new Iterator<Integer>() {
      public Integer next() { return number++; }
      public boolean hasNext() { return number < size; }
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}

class MapDataTest {
  public static void main(String[] args) {
    // Pair Generator:
    print(MapData.map(new Letters(), 11));
    // Two separate generators:
    print(MapData.map(new CountingGenerator.Character(),
            new RandomGenerator.String(3), 8));
    // A key Generator and a single value:
    print(MapData.map(new CountingGenerator.Character(),
            "Value", 6));
    // An Iterable and a value Generator:
    print(MapData.map(new Letters(),
            new RandomGenerator.String(3)));
    // An Iterable and a single value:
    print(MapData.map(new Letters(), "Pop"));
  }
} /* Output:
{1=A, 2=B, 3=C, 4=D, 5=E, 6=F, 7=G, 8=H, 9=I, 10=J, 11=K}
{a=YNz, b=brn, c=yGc, d=FOW, e=ZnT, f=cQr, g=Gse, h=GZM}
{a=Value, b=Value, c=Value, d=Value, e=Value, f=Value}
{1=mJM, 2=RoE, 3=suE, 4=cUO, 5=neO, 6=EdL, 7=smw, 8=HLG}
{1=Pop, 2=Pop, 3=Pop, 4=Pop, 5=Pop, 6=Pop, 7=Pop, 8=Pop}
*///:~
/**
* @Description: 这个示例也使用了第16章中的生成器。
 *  可以使用工具来创建任何用于Map或Collection的生成数据集，然后通过构造器或Map.putAll()和Collection.addAll()方法来初始化Map和Collection
*/
