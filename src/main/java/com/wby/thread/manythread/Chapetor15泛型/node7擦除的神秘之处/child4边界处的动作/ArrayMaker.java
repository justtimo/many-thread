package com.wby.thread.manythread.Chapetor15泛型.node7擦除的神秘之处.child4边界处的动作;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/12 11:12
 * @Description:    正因为有了擦除，泛型令人困惑的方面源自这样一个事实，即可以表示没有任何意义的事物。例如：
 */
public class ArrayMaker<T> {
    private Class<T> kind;
    public ArrayMaker(Class<T> kind) { this.kind = kind; }
    @SuppressWarnings("unchecked")
    T[] create(int size) {
        return (T[]) Array.newInstance(kind, size);
    }
    public static void main(String[] args) {
        ArrayMaker<String> stringMaker =
                new ArrayMaker<String>(String.class);
        String[] stringArray = stringMaker.create(9);
        System.out.println(Arrays.toString(stringArray));
    }
} /* Output:
[null, null, null, null, null, null, null, null, null]
*///:~
/**
* @Description: 即使kind被存储为Class<T>，擦除也意味着他是即将被存储为Class，没有任何参数。因此，当你使用它时，例如创建数组时：Array.newInstance
 * 实际上并么有拥有kind所蕴含的类型信息，因此这不会产生具体的结果，所以必须转型，这将产生一条令你无法满意的警告。
 *
 * 注意，对于在泛型中创建数组，使用Array.newInstance是推荐的方式。
 *
 * 如果要创建一个容器而不是数组，情况就不痛了
*/
class ListMaker<T> {
    List<T> create() {
        return new ArrayList<T>();
    }
    public static void main(String[] args) {
        ListMaker<String> stringMaker= new ListMaker<String>();
        List<String> stringList = stringMaker.create();
    }
} ///:~
/**
* @Description: 编译器不会给出任何警告，尽管我们(从擦除中)知道在create()内部的 new ArrayList<T>()中的<T>被移除了---运行时，这个类的内部没有任何
 *  <T>，因此这看起来毫无意义。但是如果你遵从这种思路，并将这个表达是改为new ArrayList(),编译器就会给出警告：PS实际并没有，java版本为1.8
 *
 *  本例中，这是否真的毫无意义呢？如果返回list之前，将某些对象放入其中，就像下面这样，会怎么样呢？？
*/
class FilledListMaker<T> {
    List<T> create(T t, int n) {
        List<T> result = new ArrayList<T>();
        for(int i = 0; i < n; i++)
            result.add(t);
        return result;
    }
    public static void main(String[] args) {
        FilledListMaker<String> stringMaker =
                new FilledListMaker<String>();
        List<String> list = stringMaker.create("Hello", 4);
        System.out.println(list);
    }
} /* Output:
[Hello, Hello, Hello, Hello]
*///:~
/**
* @Description: 即使编译器无法知道有关create()中的T任何信息，但是他仍旧可以在编译器确保你放置到result中的对象具有T类型，使其适合ArrayList<T>。
 * 因此，即使擦除在方法或类内部移除了有关实际类型的信息，编译器仍旧可以确保在方法或类中使用的类型的内部一致性。
 *
 * 因为擦除在方法中移除了类型信息，所以运行时的问题就是 边界 ：即对象进入和离开方法的地点。
 * 这些正是编译器在编译期执行类型检查并插入转型代码的低点，。请考虑下面的非泛型示例：
*/
class SimpleHolder {
    private Object obj;
    public void set(Object obj) { this.obj = obj; }
    public Object get() { return obj; }
    public static void main(String[] args) {
        SimpleHolder holder = new SimpleHolder();
        holder.set("Item");
        String s = (String)holder.get();
    }
} ///:~
/**
* @Description:  如果用javap -c SimpleHolder反编译这个类，就可以得到下面的（经过编辑的）内容：
*/
///:~
/**
* @Description: set()和get()方法将直接存储和产生值，而转型是在调用get方法是接受检查的
 *  现在将泛型合并到上面的代码中：
*/
class GenericHolder<T> {
    private T obj;
    public void set(T obj) {
        this.obj = obj;
    }
    public T get() {
        return obj;
    }
    public static void main(String[] args) {
        GenericHolder<String> holder =
                new GenericHolder<String>();
        holder.set("Item");
        String s = holder.get();
    }
} ///:~
/**
* @Description: 从get方法返回之后的转型消失了，但是我们还知道传递给set方法的值在编译器会接受检查。下面是相关的字节码
*/
///:~
/**
* @Description: 所产生的字节码是相同的，。对进入set方法的类型进行检查是不需要的，因为这将由编译器执行。
 * 而从对get方法返回的值进行转型仍旧是需要的，但这与你自己必须执行的操作是一样的----此处它将由编译器自动插入，因此你写入(和读取)的代码的
 * 噪声将更小。
 *
 * 由于所产生的get和set方法的字节码相同，所以在泛型中所有动作都发生在边界处---对传递进来的值进行额外的编译期检查，并插入对传递出去的值的转型。这
 * 有助于澄清对擦除的混淆。记住： 边界就是发生动作的地方。
*/
