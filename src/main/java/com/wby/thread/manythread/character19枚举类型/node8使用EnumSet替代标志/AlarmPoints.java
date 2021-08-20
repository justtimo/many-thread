//: enumerated/AlarmPoints.java
package com.wby.thread.manythread.character19枚举类型.node8使用EnumSet替代标志;

import java.util.EnumSet;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * Set是一种集合，只能向其中添加不重复的对象。当然，enum也要求其成员都是唯一的，所以enum看起来也具有集合的行为。不过，由于不能从enum中删除或添加元素，
 * 所以它只能算是不太有用的集合。Java SE5引入EnumSet，是为了通过enum创建一种替代品，以替代传统的基于int的"位标志"。这种标志可以用来表示某种"开/关"信息，
 * 不过，使用这种标志，我们最终操作的只是一些bit，而不是这些bit想要表达的概念，因此很容易写出令人难以理解的代码。
 *
 * EnumSet的设计充分考虑到了速度因素，因为它必须与非常高效的bit标志相竞争（其操作与HashSet相比，非常地快）。就其内部而言，它（可能）就是将一个long值作为比特向量，
 * 所以EnumSet非常快速高效。使用EnumSet的优点是，它在说明一个二进制位是否存在时，具有更好的表达能力，并且无需担心性能。
 *
 * EnumSet中的元素必须来自一个enum。下面的enum表示在一座大楼中，警报传感器的安放位置∶
 */
public enum AlarmPoints {
  STAIR1, STAIR2, LOBBY, OFFICE1, OFFICE2, OFFICE3,
  OFFICE4, BATHROOM, UTILITY, KITCHEN
} ///:~
/**
 * 然后，我们用EnumSet来跟踪报警器的状态∶
 */
class EnumSets {
  public static void main(String[] args) {
    EnumSet<AlarmPoints> points =
            EnumSet.noneOf(AlarmPoints.class); // Empty set
    points.add(AlarmPoints.BATHROOM);
    print(points);
    points.addAll(EnumSet.of(AlarmPoints.STAIR1, AlarmPoints.STAIR2, AlarmPoints.KITCHEN));
    print(points);
    points = EnumSet.allOf(AlarmPoints.class);
    points.removeAll(EnumSet.of(AlarmPoints.STAIR1, AlarmPoints.STAIR2, AlarmPoints.KITCHEN));
    print(points);
    points.removeAll(EnumSet.range(AlarmPoints.OFFICE1, AlarmPoints.OFFICE4));
    print(points);
    points = EnumSet.complementOf(points);
    print(points);
  }
} /* Output:
[BATHROOM]
[STAIR1, STAIR2, BATHROOM, KITCHEN]
[LOBBY, OFFICE1, OFFICE2, OFFICE3, OFFICE4, BATHROOM, UTILITY]
[LOBBY, BATHROOM, UTILITY]
[STAIR1, STAIR2, OFFICE1, OFFICE2, OFFICE3, OFFICE4, KITCHEN]
*///:~
/**
 * 使用static import可以简化enum常量的使用。EnumSet的方法的名字都相当直观，你可以查阅JDK文档找到其完整详细的描述。如果仔细研究了EnumSet的文档，你还会发现一个有趣的地方;ofO）方法被重载了很多次，
 * 不但为可变数量参数进行了重载，而且为接收2至5个显式的参数的情况都进行了重载。这也从侧面表现了EnumSet对性能的关注。因为，其实只使用可变参数已经可以解决整个问题了，
 * 但是对比显式的参数，会有一点性能损失。采用现在这种设计，当你只使用2到5个参数调用ofO）方法时，你可以调用对应的重载过的方法（速度稍快一点），而当你使用一个参数或多过5个参数时，
 * 你调用的将是使用可变参数的of（）方法。注意，如果你只使用一个参数，编译器并不会构造可变参数的数组，所以与调用只有一个参数的方法相比，也就不会有额外的性能损耗。
 *
 * EnumSet的基础是long，一个long值有64位，而一个enum实例只需一位bit表示其是否存在。也就是说，在不超过一个long的表达能力的情况下，你的EnumSet可以应用于最多不超过64个
 * 元素的enum。如果enum超过了64个元素会发生什么呢?
 */
class BigEnumSet {
  enum Big { A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10,
    A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21,
    A22, A23, A24, A25, A26, A27, A28, A29, A30, A31, A32,
    A33, A34, A35, A36, A37, A38, A39, A40, A41, A42, A43,
    A44, A45, A46, A47, A48, A49, A50, A51, A52, A53, A54,
    A55, A56, A57, A58, A59, A60, A61, A62, A63, A64, A65,
    A66, A67, A68, A69, A70, A71, A72, A73, A74, A75 }
  public static void main(String[] args) {
    EnumSet<Big> bigEnumSet = EnumSet.allOf(Big.class);
    System.out.println(bigEnumSet);
  }
} /* Output:
[A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, A23, A24, A25, A26, A27, A28, A29, A30, A31, A32, A33, A34, A35, A36, A37, A38, A39, A40, A41, A42, A43, A44, A45, A46, A47, A48, A49, A50, A51, A52, A53, A54, A55, A56, A57, A58, A59, A60, A61, A62, A63, A64, A65, A66, A67, A68, A69, A70, A71, A72, A73, A74, A75]
*///:~
/**
 * 显然，EnumSet可以应用干多过64个元素的enum，所以我猜测。Enum会在必要的时候增加一个long。
 * 找到EnumSet的源代码，解释其工作原理。
 */
