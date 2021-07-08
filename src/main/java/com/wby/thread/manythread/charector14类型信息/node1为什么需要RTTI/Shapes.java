package com.wby.thread.manythread.charector14类型信息.node1为什么需要RTTI;//: typeinfo/Shapes.java

import java.util.Arrays;
import java.util.List;
/**
 * @Description:  下面看这个例子，它使用了多态的类层次结构，最通用的类型(泛型)是基类Shape，派生出的具体类是Circle、Square、Triangle
 *  面向对象编程的基本目的是：让代码只操纵对基类的引用(这个例子中是Shape)。
 *  这样，如果添加新类（比如从Shape派生出了Rhomboid）扩展程序，不会影响到原来的代码
 *
 *  这个例子的Shape接口动态绑定了draw方法，目的是让程序员使用泛华的Shape引用来调用draw（）。
 *  draw（）在所有的派生类都会被覆盖，并且由于他是被动态绑定的，所以即使通过泛华的Shape引用调用draw方法，也能产生正确的行为。这就是多态
 *
 *  因此通常会创建一个具体的对象，把他向上转型为Shape(忽略对象的具体类型)，并在后面的程序中使用匿名的Shape引用。
 *  你可以像下面这样对Shape层次结构编码：
 */
abstract class Shape {
  void draw() { System.out.println(this + ".draw()"); }
  abstract public String toString();
}

class Circle extends Shape {
  public String toString() { return "Circle"; }
}

class Square extends Shape {
  public String toString() { return "Square"; }
}

class Triangle extends Shape {
  public String toString() { return "Triangle"; }
}

public class Shapes {
  public static void main(String[] args) {
    List<Shape> shapeList = Arrays.asList(
      new Circle(), new Square(), new Triangle()
    );
    for(Shape shape : shapeList)
      shape.draw();
  }
} /* Output:
Circle.draw()
Square.draw()
Triangle.draw()
*///:~
/**
* @Description: 基类中draw方法，他通过传递this参数给print（），间接地使用toString（）方法打印类标识符
 *  注意：toString被声明为了abstract，以此强制要求继承者重写在方法，并可以防止对无格式化的Shape的实例化
 *
 *  如果某个对象出现在字符串表达式中(涉及+和字符串对象的表达式)，toString就会被自动调用，以生成表示该对象的String，这样，draw方法在不同情况下
 *  就打印出不同的消息(多态)
 *
 *  这个例子中，当把SHape对象放入List<Shape>的数组时会向上转型。但向上转型为Shape时也丢失了Shape对象的具体类型。对数组而言，他们只是shape对象
 *
 *  当从数组中取出元素的时候，这种容器---实际上它将所有的事物都当做Object持有---最自动将结果转型回Shape。
 *  这是RTTI最基本的形式，因为在java中，所有的类型转换都是在运行时进行正确性检查的，这也是RTTI的含义：运行时识别一个对象的类型
 *
 *  这个例子中，RTTI类型转换并不彻底，Object被转型为Shape，而不是转型为Circle Square 或Triangle。这是因为目前我们知道这个List<Shape>保存的都是Shape
 *  编译时，将由容器和java泛型系统强制确保这一点；而在运行时，由类型转换操作来确保这一点
 *
 *  接下来就是多态的事情了，Shape对象实际执行什么样的代码，是由引用所指向的具体的对象Circle Square 或Triangle来决定的。
 *  通常，也正是这样要求的：大部分代码尽可能的少了解对象的 具体类型 ，而是只与对象家族中的一个通用表示打交道(这个例子中是Shape)。
 *  这样的代码更容易写、容易读、便于维护；设计也更容易实现、理解和改变。
 *  所以，多态是面向对象编程的基本目标
 *
 *  但是，如果遇到了特殊的编程问题：能够知道某个泛华引用的确切类型，就可以使用最简单的方式去解决他。
 *  例如：假如我们允许用户将具体的某种类型的几何形状全部变成某种特殊颜色，以便突出显示他们；
 *    或者，可能要用某个方法旋转列出所有图形，但是要跳过圆形，因为圆形旋转没有意义。
 *  使用RTTI可以查询某个Shape引用所指向的对象的确定类型，然后选择或者剔除特例。
 */
