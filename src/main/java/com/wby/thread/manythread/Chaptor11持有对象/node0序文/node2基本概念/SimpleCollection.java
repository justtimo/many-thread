package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node2基本概念;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/30 14:03
 * @Description: java容器类库被用来保存对象，并将其划分为两个不同的概念
 *  1.Collection：一个独立元素的序列，这些元素服从一条或多条规则。List必须按照插入的顺序保存元素，Set不能有重复的元素。Queue按照 排队规则 来确定对象的产生顺序（通常与他们插入顺序相同）
 *  2.Map：一组成对的“键值对”对象，允许使用键查找值。ArrayList允许你使用数字查找值，某种意义上，他将数字与对象关联在了一起。
 *      映射表允许我们使用一个对象查找另一个对象，他也被称为“关联数组”，因为他将某些对象与另外一些对象关联在了一起；或者称为“字典”，因为你可以使用键对象查找值对象，就像在字典中使用单词定义一样。
 *
 *  例如：List<Apple> apples=new ArrayList<Apple>();注意，ArrayList被向上转型为List，这与前一个例子中的处理方式正好相反。
 *  使用接口的目的在于如果你决定去修改你的实现，你所需的只是在创建时修改它，就像：List<Apple> apples = new LinkedList<>();因此，你应该创建一个具体类的对象，将其转型为对应的接口
 *  然后在其余代码中都能使用这个接口。
 *
 *  这种方式并非总能奏效，因为某些类具有额外的功能，例如，LinkList具有List中未包含的方法，而TreeMap也具有在Map接口中未包含的方法，如果你需要使用这些方法，就不能向上转型为更通用的接口
 *
 *  Collection接口概括了序列的概念---一种存放一组对象的方式。见下例
 */
public class SimpleCollection {
    public static void main(String[] args) {
        Collection<Integer> c = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            c.add(i);
        }
        for (Integer integer : c) {
            System.out.println(integer+" .");
        }
    }
}
/**
* @Description: 上面的例子只使用了Collection方法，因此任何继承自Collection的类的对象都可以正常工作，但是ArrayList是最基本的序列类型。
*/
