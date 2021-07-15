package com.wby.thread.manythread.Chapetor15泛型.node13动态类型安全;//: generics/CheckedList.java
// Using Collection.checkedList().


import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.pets.Cat;
import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.pets.Dog;
import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.pets.Pet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @Description: 因为可以向1.5之前的代码传递泛型容器，所以旧代码仍旧可能破坏你的容器，1.5的util.Collection中有一组便利工具，可以解决在这种情况下
 * 的类型检查问题，他们是：checkedList、checkedCollection、checkedMap、checkedSet、checkedSortedMap和checkedSortedSet。这些方法每一个
 * 都会将你希望动态检查的容器当做第一个参数接受，并将你希望强制要求的类型作为第二个参数接受
 *
 * 受检查的容器在你试图插入类型不正确的对象时抛出ClassCastException，这与泛型之前的(原生)容器形成了对比，对于后者来说，当你将对象从容器中取出时，如果
 * 使用受检查的容器，就可以发现谁在试图插入不良对象。
 *
 * 然我们用受检查的容器来看看“将猫插入到狗列表中”这个问题。
 * 这里，oldStyleMethod表示遗留代码，因为他接受的是原生的List，而@SuppressWarnings("unchecked")注解对于压制所产生的警告是必需的：
*/
public class CheckedList {
  @SuppressWarnings("unchecked")
  static void oldStyleMethod(List probablyDogs) {
    probablyDogs.add(new Cat());
  }
  public static void main(String[] args) {
    List<Dog> dogs1 = new ArrayList<Dog>();
    oldStyleMethod(dogs1); // Quietly accepts a Cat
    List<Dog> dogs2 = Collections.checkedList(
      new ArrayList<Dog>(), Dog.class);
    try {
      oldStyleMethod(dogs2); // Throws an exception
    } catch(Exception e) {
      System.out.println(e);
    }
    // Derived types work fine:
    List<Pet> pets = Collections.checkedList(
      new ArrayList<Pet>(), Pet.class);
    pets.add(new Dog());
    pets.add(new Cat());
  }
} /* Output:
java.lang.ClassCastException: Attempt to insert class typeinfo.pets.Cat element into collection with element type class typeinfo.pets.Dog
*///:~
/**
* @Description: 运行这个程序时，你会发现插入一个Cat对于dogs1来说没有任何问题，而dogs2立即会在这个错误类型的插入操作上抛出一个异常。还可以看到，
 * 将导出类型的对象放置到将要检查基类型的受检查容器中是没有问题的。
*/

