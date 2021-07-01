package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node1泛型和类型安全的容器;

import java.util.ArrayList;

/**
 * ArrayList使用get（）访问对象，此时需要使用索引，就像数组一样，但是不需要方括号（这里是操作符重载的用武之地，C++和C#的容器类都是用操作符重载生成了更简洁的语法）
 *
 * 下面例子中，我们将Apple和Origin都放入容器中，正常情况下，编译器会报警告信息，因为我们没有使用泛型。
 * 这里使用@SuperWarning注解来抑制警告信息，@SuperWarning注解及其参数表示只有有关“不受检查的异常”的警告信息应该被抑制
 */
public class ApplesAndOriginsWithoutGenerics {
    public static void main(String[] args) {
        ArrayList<Object> apples = new ArrayList<Object>();
        for (int i = 0; i < 3; i++) {
            apples.add(new Apple());
        }
        for (int i = 0; i < apples.size(); i++) {
            ((Apple)apples.get(i)).id();

        }
    }
}
class Apple{
    private static long counter;
    private final long id=counter++;
    public long id(){
        return id;
    }
}
class Origin{}
/**
 * @Description: 下面的例子使用了泛型 ,此时取对象不需要类型转换了。
 * 而且，如果不需要使用每个元素的索引，你可以使用foreach语法来选择List中的每个元素
 */
class AppleAndOrigin{
    public static void main(String[] args) {
        ArrayList<Apple> apples = new ArrayList<Apple>();
        for (int i = 0; i < 3; i++) {
            apples.add(new Apple());
        }
        for (int i = 0; i < apples.size(); i++) {
            System.out.println(apples.get(i).id());
        }
        for (Apple apple : apples) {// ........................foreach
            System.out.println(apple.id());
        }
    }
}
/**
* @Description: 向上转型也可以像作用于其他类型一样作用于泛型
*/
class GrannySmith extends Apple{}
class Gala extends Apple { }
class Fuji extends Apple { }
class Breaburn extends Apple { }

class GenricsAndUpcasting{
    public static void main(String[] args) {
        ArrayList<Apple> apples = new ArrayList<Apple>();
        apples.add(new GrannySmith());
        apples.add(new Gala());
        apples.add(new Fuji());
        apples.add(new Breaburn());
        for (Apple apple : apples) {
            System.out.println(apple);
        }
    }
}
/**
* @Description:  你可以将Apple的子类添加到被指定为保存Apple对象的容器中。
 *  程序的输出是从Object默认的toString方法产生的，该方法将打印类名，后面跟随该对象的散列码的无符号十六进制表示（散列码是通过hashCode（）产生的）。
*/
