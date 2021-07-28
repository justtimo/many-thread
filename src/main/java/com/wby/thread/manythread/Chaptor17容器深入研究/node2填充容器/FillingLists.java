package com.wby.thread.manythread.Chaptor17容器深入研究.node2填充容器;//: containers/FillingLists.java
// The Collections.fill() & Collections.nCopies() methods.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @Description: 虽然容器打印的问题解决了，容器的填充仍然像Arrays一样面临同样的不足。就像Arrays一样，相应的Collections类也有一些使用的static
 * 方法，其中包括fill方法。
 * 与Arrays版本一样，此fille方法也是复制同一个对象引用来填充整个容器的，并且只对List对象有用，但是所产生的列表可以传递给构造期或addAll方法：
*/
class StringAddress {
  private String s;
  public StringAddress(String s) { this.s = s; }
  public String toString() {
    return super.toString() + " " + s;
  }
}

public class FillingLists {
  public static void main(String[] args) {
    List<StringAddress> list= new ArrayList<StringAddress>(
      Collections.nCopies(4, new StringAddress("Hello")));
    System.out.println(list);
    Collections.fill(list, new StringAddress("World!"));
    System.out.println(list);
  }
} /* Output: (Sample)
[StringAddress@82ba41 Hello, StringAddress@82ba41 Hello, StringAddress@82ba41 Hello, StringAddress@82ba41 Hello]
[StringAddress@923e30 World!, StringAddress@923e30 World!, StringAddress@923e30 World!, StringAddress@923e30 World!]
*///:~
/**
* @Description: 上例展示了两种用对单个对象的引用来填充Collection的方式，第一种是使用Collections.nCopies方法创建传递给构造器的List，这里填充的是ArrayList。
 *
 * StringAddress的toString方法调用Object.toString方法并产生该类的名字，后面紧跟该对象的散列码的无符号十六进制表示(通过hashCode生成的)。
 * 从输出中你可以看到所有引用都被设置为指向相同的对象，在第二种方法的Collection.fill()被调用之后也是如此。
 * fill()方法的用处更有限，因为他只能替换已经在List中存在的元素，而不能添加新的元素。
*/
