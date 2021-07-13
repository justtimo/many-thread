package com.wby.thread.manythread.Chapetor15泛型.node10通配符.child1编译器有多聪明;//: generics/CompilerIntelligence.java

import com.wby.thread.manythread.Chapetor15泛型.node10通配符.Apple;
import com.wby.thread.manythread.Chapetor15泛型.node10通配符.Fruit;

import java.util.Arrays;
import java.util.List;

/**
* @Description: 你可能会猜想自己被阻止去调用任何接受参数的方法，但是请考虑下面的程序：
*/
public class CompilerIntelligence {
  public static void main(String[] args) {
    List<? extends Fruit> flist =
      Arrays.asList(new Apple());
    Apple a = (Apple)flist.get(0); // No warning
    flist.contains(new Apple()); // Argument is 'Object'
    flist.indexOf(new Apple()); // Argument is 'Object'
  }
} ///:~
/**
* @Description: Holder有一个接受T类型对象的set方法，一个get方法，以及一个接受Object对象的equals方法。正如看到的，如果创建一个Holder<Apple>，
 * 不能将其向上转型为Holder<Fruit>，但是可以将其向上转型为Holder<? extends Fruit>.
 * 如果调用get方法，他只会返回一个Fruit---这就是在给定“任何扩展自Fruit的对象”这一边界之后，他所能知道的一切了。
 * 如果能够了解更多的信息，那么你可以转型到某种具体的Fruit类型，而这不会导致任何警告，但是你存在着得到ClassCaseException的风险。
 * set方法不能工作与Apple或Fruit，因为set方法的参数也是“？ Extends Fruit”，这意味着他可以是任何事物，而编译器无法验证“任何事物”的类型安全性。
 *
 * 但是，equals方法工作良好，因为他将接受Object类型而并非T类型的参数。
 * 因此，编译器只关注传递进来和要返回的对象类型，她并不会分析代码，以查看是否执行了任何实际的写入和读取操作。
*/
