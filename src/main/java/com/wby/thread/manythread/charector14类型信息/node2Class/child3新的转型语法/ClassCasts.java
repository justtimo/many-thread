package com.wby.thread.manythread.charector14类型信息.node2Class.child3新的转型语法;//: typeinfo/ClassCasts.java


/**
 * @Description:  1.5新增了用于Class引用的转型语法，即cast（）
 */
class Building {}

class House extends Building {}

public class ClassCasts {
  public static void main(String[] args) {
    Building b = new House();
    Class<House> houseType = House.class;
    House h = houseType.cast(b);
    h = (House)b; // ... or just do this.
  }
} ///:~
/**
* @Description: cast（）方法接受参数对象，并将其转型为Class引用的类型。
 *  当然，可以发现，与实现了很多功能的main（）中最后一行相比，这种转型好像做了很多额外的工作。
 *
 *  新的转型语法对于无法使用普通转型的情况很有用，在编写泛型代码时，如果你存储了Class引用，并希望以后通过这个引用来执行转型，这种情况就会发生。
 *  这是一种罕见的情况，1.5类库中只有一处使用了cast()（util。DeclarationFilter）
 *  1.5中另外一个没用的新特性是Class.asSubClass(),该方法允许你将一个类对象转型为更加具体的类型
*/

