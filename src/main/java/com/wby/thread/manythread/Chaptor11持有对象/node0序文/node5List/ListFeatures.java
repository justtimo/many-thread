package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node5List;

import java.util.*;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/30 16:11
 * @Description:    List承诺可以将元素维护在特定序列中。List接口在Collection基础上新增了很多方法，使得可以在List中间插入移除元素
 *
 *  有两种类型的List：
 *      ArrayList，擅长随机访问，但在List中间插入删除元素较慢
 *      LinkedList：它通过较低的代价在List中间删除插入元素，提供了优化的顺序访问。他的随机访问比ArrayList慢，但是他的特性集比ArrayList更大。
 *
 *  下面的例子你需要知道两点：有一个Pet类，及其子类  2：静态的Pet。arrayList方法将返回一个填充了随机选取的Pet对象的ArrayList
 */
public class ListFeatures {
    public static void main(String[] args) {
        Random random = new Random(47);
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        int i=6;
        integers.add(i);
        Integer remove = integers.remove(i);
        boolean contains = integers.contains(i);
        integers.add(3,9999);

        List<Integer> subList = integers.subList(1, 4);
        Collections.sort(subList);

        Collections.shuffle(subList,random);//mix it up

        ArrayList<Integer> copy = new ArrayList<>(subList);
        boolean b = copy.removeAll(subList);
        Integer set = copy.set(1, 33333);//replace a element

        copy.addAll(2,subList);
        copy.retainAll(subList);

        copy.clear();//remove all element
    }
}
/**
* @Description: List允许在创建之后添加删除元素，还可以自己调整尺寸。如果想删除一个元素，则可以将这个元素的引用传递给remove，同样可以根据引用使用indexOf查找该元素在List中的索引位置。
 *  当确定一个元素是否属于List、发现某个元素的索引，以及从List中移除一个元素时，都会用到equals方法。例如，每个Pet都被定义为唯一的对象，因此即使列表中有两个Cymric，再创建一个Cymric，
 *      并把它传递给indexOf方法，其结果任然会是-1（没有找到），而且尝试调用remove（）方法来删除这个对象，也会返回false
 *  对于其他类，equals方法定义可能有所不同。例如，两个String只有在内容完全相同时才会是等价的。因此为了防止意外，就必须意识到List的行为根据equals的行为变化为变化
 *
 *  在List中插入元素是可行的，但是这带来了一个问题：对于LinkedList来说，在列表中插入和删除元素都是廉价的（在本利中，除了对列表中间进行真正的随机访问），但是对于ArrayList来说是代价高昂的
 *  这是否意味着你应该永远不在ArrayList中插入元素，并最好是切换到LinkedList中？不，这仅仅意味着你应该看看你的List实现有可能就是罪魁祸首。
 *
 *  subList（）所产生的的列表幕后任然是初始的列表，因此，对subList返回列表的修改都会反映到初始列表中，反之亦然。
 *
 *  retainAll()方法是一种有效的“交集”操作。注意，所产生的的行为依赖于equals方法
 *
 *  removeAll()方法行为也是依赖于equals方法的，他将从List中移除参数List中的所有元素。
 *
 *  对于List有个重载的addAll方法，可以在List中插入新的列表，而不仅仅只能用Collection。addAll方法将其追加在末尾
 *
 *  toArray方法可以将任意类型的Collection转换为一个数组。这是一个重载方法，无参版本返回的是Object数组，有参版本将会产生指定类型的数据（假设能通过类型检查）。
 *      如果参数数组太小，存放不下所有的List元素，toArray将会创建一个具有合适尺寸的数组
 *
*/
