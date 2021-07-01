package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node3添加一组元素;

import java.util.*;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/30 14:33
 * @Description: Arrays.asList()接受一个数组或是一个用逗号分割的元素列表（使用可变参数），并将其转换为一个List对象。
 *  Collection.addAll()接受一个Collection对象，以及一个数组或是逗号分割的列表，将元素添加到Collection中。
 */
public class AddGroups {
    public static void main(String[] args) {
        Collection<Integer> collection =
                new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        Integer[] moreInts={6,7,8,9,10};
        collection.addAll(Arrays.asList(moreInts));
        Collections.addAll(collection,11,12,13,14,15);
        Collections.addAll(collection,moreInts);
        List<Integer> list = Arrays.asList(16, 17, 18, 19, 20);
        list.set(1,99);
        //list.add(21);//报错，：不支持的操作
    }
}
/**
* @Description:  Collection的构造器可以接受另一个Collection，用它来将自身初始化，因此你可以使用Array.List()来为这个构造器产生输入。
 *  但是，Collection.addAll()方法运行起来要快得多，而且构建一个不包含元素的Collection，然后调用addAll（）这种方式很方便，因此他是首选方式。
 *
 *  Collection.addAll()方法只能接受另一个Collection对象作为参数，因此他不如Arrays.asList或Collections.addAll方法灵活，这两个方法使用的都是可变参数列表。
 *
 *  你可以直接使用Arrays.asList的输出，并将其当做一个List。但是这种情况下，其底层是一个数组，因此不能调整大小，当试图使用add或delete时，会报错。
 *
 *  Arrays.asList方法的限制是他对所产生的的List的类型做出了理想假设，而并没有注意到你会怼他赋予什么类型。这时就会有如下问题，见下例：
*/
class Snow{}
class Powder extends Snow{}
class Light extends Powder{}
class Heavy extends Powder{}
class Crust extends Snow{}
class Slush extends Snow{}

class AsListInterface{
    public static void main(String[] args) {
        List<Snow> snows1 =
                Arrays.asList(new Crust(), new Slush(), new Powder());
        //wont compile
        List<Snow> snows2 =
                Arrays.asList(new Light(), new Heavy());
        Collections.addAll(snows2);

        ArrayList<Snow> snows3 = new ArrayList<>();
        Collections.addAll(snows3,new Light(),new Heavy());

        List<Snow> snows4 = Arrays.<Snow>asList(new Light(), new Heavy());
        List<Snow> snows5 = Arrays.asList(new Light(), new Heavy());

    }
}
/**
* @Description:  当试图创建snows2，Arrays.asList中只有Powder类型，因此他会创建List<Powder>而不是 List<Snow>尽管Collections.addAll工作的很好，因为他从第一个参数了解到目标类型是什么。
 *
 * 正如创建snows4时看到的，可以在Arrays.asList中间插入一条线索，告诉编译器对于Arrays.asList产生的List类型，实际的目标类型是什么。这称为 显式类型参数说明
 *
*/
