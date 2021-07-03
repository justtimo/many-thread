package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node13Foreach与迭代器.child1适配器方法惯用法;

import com.wby.thread.manythread.Chaptor11持有对象.node0序文.node13Foreach与迭代器.InterableClass;

import java.util.*;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/1 15:25
 * @Description:    如果现在有一个Iterable类，你想要添加一种或多种在foreach语句中使用这个类的方法，应该怎么做呢？
 *  例如，假设你希望可以选择以向前的方向或是向后的方向迭代一个单词列表。如果直接继承这个类，并覆盖Iterator方法，你只能替换现有的方法，而不能实现选择。
 *  一种解决方案是所谓 “适配器方法” 的惯用法。适配器 部分来自于设计模式，因为你必须提供特定接口以满足foreach语句。
 *  当你有一个接口并需要另一个接口时，编写适配器就可以解决这个问题。
 *
 *  这里，我希望在默认的向前迭代器基础上，添加产生反向迭代器的能力，因此我不能使用覆盖，而是添加了一个能够产生Iterable对象的方法，该对象可以用在foreach
 *  这使得我们可以提供多种使用foreach的方式
 */
public class ReversibleArrayList<T> extends ArrayList<T> {
    public ReversibleArrayList(Collection<T> c){
        super(c);
    }
    public Iterable<T> reverse(){
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    int current=size()-1;
                    public boolean hasNext() {
                        return current > -1;
                    }
                    public T next() {
                        return get(current--);
                    }
                };
            }
        };
    }
}
class AdapterMethodIdiom{
    public static void main(String[] args) {
        ReversibleArrayList<String> ral = new ReversibleArrayList<String>(Arrays.asList("to be success and false or else".split(" ")));
        for (String string : ral) {
            System.out.println(string+" ");
        }
        for (String s : ral.reverse()) {
            System.out.println(s+" ");
        }
    }
}
/**
* @Description: 如果直接将ral对象放置于foreach语句中，将得到（默认的）前向迭代器。但是如果在该对象上调用reverse方法，就会产生不同的行为。
 *  通过这种方式，可以在InterableClass.java中添加两种适配器方法。
*/
class MultiIterableClass extends InterableClass{

    public Iterable<String> reverse(){
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    int current=words.length-1;
                    public boolean hasNext() {
                        return current > -1;
                    }
                    public String next() {
                        return words[current--];
                    }
                };
            }
        };
    }

    public Iterable<String> randomized() {
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                ArrayList<String> strings = new ArrayList<>(Arrays.asList(words));
                System.out.println("打乱之前：  "+strings);
                Collections.shuffle(strings,new Random(47));
                System.out.println("打乱之后：  "+strings);
                return strings.iterator();
            }
        };
    }

    public static void main(String[] args) {
        MultiIterableClass strings = new MultiIterableClass();
        /*for (String s : strings.reverse()) {
            System.out.println(s);
        }*/
        for (String s : strings.randomized()) {
            System.out.println(s);
        }
        /*for (String string : strings) {
            System.out.println(string);
        }*/
    }
}
/**
* @Description: 注意，randomized方法 没有创建自己的Iterator，而是直接返回了被打乱的List中的Iterator
 *  可以看到，shuffle没有影响到原来的数组，只是打乱了shuffle中的引用。
 *  之所以这样，是因为randomized方法用一个ArrayList将Arrays.asList方法的结果包装了起来。
 *  如果这个由Arrays.asList方法产生的List被直接打乱，那么他就会修改底层的数组，就像下面这样：
*/
class ModifyArrayAsList{
    public static void main(String[] args) {
        Random random = new Random();
        Integer[] ia={1,2,3,4,5,6,7,8,9};
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(ia));
        System.out.println("ArrayList before : "+integers);
        Collections.shuffle(integers,random);
        System.out.println("ArrayList after: "+integers);
        System.out.println("ArrayList array: "+Arrays.toString(ia));

        List<Integer> list2 = Arrays.asList(ia);
        System.out.println("Arrays before : "+list2);
        Collections.shuffle(list2,random);
        System.out.println("Arrays after: "+list2);
        System.out.println("Arrays array: "+Arrays.toString(ia));
    }
}
/**
* @Description: 在第一种情况中，Arrays.asList的输出被传递给new ArrayList的构造器，这将创建一个引用ia的元素的ArrayList，因此打乱这些引用不会修改该数组。
 *  但是如果直接使用Arrays.asList(ia)的结果，这种打乱就会修改ia的顺序。
 *  意识到Arrays.asList方法产生的List对象会使用底层数组作为其物理实现是很重要的。
 *  只要你执行的操作会修改这个List，并且你不想原来的数组被修改，那么你应该使用另外一个容器创建一个副本。
*/
