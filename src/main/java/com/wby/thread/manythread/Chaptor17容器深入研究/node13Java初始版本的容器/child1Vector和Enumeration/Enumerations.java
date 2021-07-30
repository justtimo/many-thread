package com.wby.thread.manythread.Chaptor17容器深入研究.node13Java初始版本的容器.child1Vector和Enumeration;//: containers/Enumerations.java
// Java 1.0/1.1 Vector and Enumeration.

import com.wby.thread.manythread.net.mindview.util.Countries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

/**
* @Description: 在Java 1.0/1.1中，Vector是唯一可以自我扩展的序列，所以它被大量使用。它的缺点多到这里都难以描述（可以参见本书的第1版，可从www.MindView.net免费下载）。
 * 基本上，可将其看作ArrayList，但是具有又长又难记的方法名。在订正过的Java容器类类库中，Vector被改造过，可将其归类为Collection和List。
 * 这样做有点不妥当，可能会让人误会Vector变得好用了，实际上这样做只是为了支持Java 2之前的代码。
 *
 * Java 1.0/1.1版的迭代器发明了一个新名字——枚举，取代了为人熟知的术语（迭代器）。此Enumeration 接口比Iterator小，只有两个名字很长的方法;
 * 一个为boolean hasMoreElementsO，如果此枚举包含更多的元素，该方法就返回true;
 * 另一个为Object nextElementO，该方法返回此枚举中的下一个元素（如果还有的话），否则抛出异常。
 *
 * Enumeration 只是接口而不是实现，所以有时新的类库们然使用了旧的Enumeration。这令人十分遗憾，但通常不会造成伤害。虽然在你的代码中应该尽量使用Iterator，但也得有所准备，类库可能会返回给你一个Enumeration。
 *
 * 此外，还可以通过使用Collections.enumerationO方法来从Collection生成一个Enumeration，见下面的例子∶
*/
public class Enumerations {
  public static void main(String[] args) {
    Vector<String> v =
      new Vector<String>(Countries.names(10));
    Enumeration<String> e = v.elements();
    while(e.hasMoreElements())
      System.out.print(e.nextElement() + ", ");
    // Produce an Enumeration from a Collection:
    e = Collections.enumeration(new ArrayList<String>());
  }
} /* Output:
ALGERIA, ANGOLA, BENIN, BOTSWANA, BULGARIA, BURKINA FASO, BURUNDI, CAMEROON, CAPE VERDE, CENTRAL AFRICAN REPUBLIC,
*///:~
/**
* @Description:  可以调用elements（）生成Enumeration，然后使用它进行前序遍历。最后一行代码创建了一个ArrayList，并月，使用enumerationO将ArrayList的Iterator转换成了Enumeration。
 * 这样.即使有需要Enumeration的旧代码，你仍然可以使用新容器。
*/
