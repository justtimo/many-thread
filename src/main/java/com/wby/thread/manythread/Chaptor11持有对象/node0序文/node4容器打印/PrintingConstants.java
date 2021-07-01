package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node4容器打印;

import java.util.*;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/30 15:31
 * @Description:
 */
public class PrintingConstants {
    static Collection fill(Collection<String> collection){
        collection.add("rat");
        collection.add("cat");
        collection.add("dog");
        collection.add("dog");
        return collection;
    }

    static Map fill(Map<String,String> map){
        map.put("rat","Fuzzy");
        map.put("cat","Rags");
        map.put("dog","Bosco");
        map.put("dog","Spot");
        return map;
    }

    public static void main(String[] args) {
        System.out.println(fill(new ArrayList<>()));
        System.out.println(fill(new LinkedList<>()));
        System.out.println(fill(new HashSet<>()));
        System.out.println(fill(new TreeSet<>()));
        System.out.println(fill(new LinkedHashSet<>()));
        System.out.println(fill(new HashMap<>()));
        System.out.println(fill(new TreeMap<>()));
        System.out.println(fill(new LinkedHashMap<>()));
    }
}
/**
* @Description: 这里展示了java容器类库中主要的两种类型：Collection和Map，他们的区别在于容器中每个“槽”保存的元素个数。
 *  Collection：每个槽只能保存一个元素。此类容器包括List：以特定顺序保存一组元素；Set元素不能重复；Queue只允许在容器一端插入对象，并从另一端移除对象
 *  Map：每个槽保存了两个对象，即键和值
 *
 *  ArrayList和LinkedList都是List类型，都按照插入顺序保存元素，区别在于执行某些类型操作时的性能，而且LinkedList包含的操作也多余ArrayList
 *  HashSet、TreeSet、LinkedHashSet都是Set类型。但是输出也显示了他们存储元素方式的不同。
 *      hashSet是相当复杂的方式存储的，但是这种技术是最快获取元素的方式，因此存储顺序看起来并无实际意义（通常只关心某成员是否在Set中），
 *      如果存储顺序很重要，name可以使用TreeSet，他按照比较结果的升序保存对象；或者LinkedHashMap，他按照添加顺序保存对象。
 *
 *  Map：也称 关联数组。键和值在Map中保存顺序并不是他们的插入顺序，因为HashMap实现使用的是一种非常快的算法来控制顺序。
 *      TreeMap按照比较结果的升序保存键
 *      LinkedHashMap则按照插入顺序保存键，同时还保留了HashMap的查询速度。
*/
