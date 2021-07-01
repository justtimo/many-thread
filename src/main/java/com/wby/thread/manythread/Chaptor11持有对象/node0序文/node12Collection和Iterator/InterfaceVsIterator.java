package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node12Collection和Iterator;

import java.util.*;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/1 11:34
 * @Description:    Collection是描述所有序列容器的根接口，他可能会被认为是一个‘附属接口’，即因为要表示其他若干个接口的共性而出现的接口
 *  使用接口描述的一个理由是他可以使我们能够创建更通用的代码，因此如果编写的方法接受一个Collection，那么该方法就可以应用于任何实现了Collection的类。
 *
 *  有趣的是，标准C++类库中并没有容器的任何公共基类---容器之间的所有共性都是通过迭代器达成的。在java中，遵循C++似乎很明智，即用迭代器而不是Collection来表示容器之间的共性
 *  但是，两种方法绑定到一起，因为实现Collection意味着需要提供Iterator方法
 */
public class InterfaceVsIterator {
    public static void display(Iterator<String> it){
        while (it.hasNext()){
            String next = it.next();
            System.out.println(next);
        }
    }
    public static void display(Collection<String> c){
        for (String s : c) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>(Arrays.asList("wby", "wby1", "wby2", "wby3", "wby4", "wby5", "wby6","wby7","wby8","wby9","wby10"));
        HashSet<String> stringSet = new HashSet<>(strings);
        Map<String, String> stringMap = new LinkedHashMap<>();
        String[] name = "ww,ww1,ww2,ww3,ww4,ww5,ww6,ww7,ww8,ww9,ww10".split(",");
        for (int i = 0; i < name.length; i++) {
            stringMap.put(name[i],strings.get(i));
        }
        display(strings);
        display(stringSet);
        display(strings.iterator());
        display(stringSet.iterator());
        System.out.println(stringMap);
        System.out.println(stringMap.keySet());
        display(stringMap.values());
        display(stringMap.values().iterator());

    }
}
/**
* @Description: 两个版本的display都可以使用Collection或Map的子类型工作。而且Collection接口和Iterator都可以将display方法与底层容器的特定实现解耦
 *  上例中，两种方式都能奏效。实际上，Collection更为方便，因为他是Iterable类型，所以可以使用foreach，使得代码结构更清晰。
 *
 *  当要实现一个不是Collection的外部类时，由于实现Collection很麻烦，因此使用Iterator就变得吸引人。
*/
class CollectionSqueue extends AbstractCollection<String>{
    ArrayList<String> strings = new ArrayList<>(Arrays.asList("wby", "wby1", "wby2", "wby3", "wby4", "wby5", "wby6","wby7","wby8","wby9","wby10"));

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index=0;
            @Override
            public boolean hasNext() {
                return index<strings.size();
            }

            @Override
            public String next() {
                return strings.get(index++);
            }
        };
    }

    @Override
    public int size() {
        return strings.size();
    }

    public static void main(String[] args) {
        CollectionSqueue strings = new CollectionSqueue();
        InterfaceVsIterator.display(strings);
        InterfaceVsIterator.display(strings.iterator());
    }
}
/**
* @Description: 在本例中，可以看到，实现Collection就必须实现Iterator()，并且只拿实现Iterator()与继承AbstractCollection相比，花费的代价只是略微减少。
 *
 * 但是如果你的类已经继承了其他类，那么就不能再继承AbstractCollection。这种情况下，要实现Collection，就必须实现该接口中的所有方法。此时，继承并提供创建迭代器
 * 的能力就显得容易的多
*/
class StringSequence{
    String[] s="wby, wby1, wby2, wby3, wby4, wby5, wby6,wby7,wby8,wby9,wby10".split(",");
}
class NonCollectionSequene extends StringSequence{
    public Iterator<String> iterator(){
        return new Iterator<String>() {
            private int index=0;
            @Override
            public boolean hasNext() {
                return index<s.length;
            }

            @Override
            public String next() {
                return s[index++];
            }
        };
    }

    public static void main(String[] args) {
        NonCollectionSequene nonCollectionSequene = new NonCollectionSequene();
        InterfaceVsIterator.display(nonCollectionSequene.iterator());
    }
}
/**
* @Description: 生成Iterator是将队列与消费队列的方法连接在一起耦合度最小的方式，并且与实现COllection相比，它在序列类上所施加的约束也少得多。
*/
