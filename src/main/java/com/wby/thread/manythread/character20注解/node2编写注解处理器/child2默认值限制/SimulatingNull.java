package com.wby.thread.manythread.character20注解.node2编写注解处理器.child2默认值限制;//: annotations/SimulatingNull.java
import java.lang.annotation.*;

/**
 * 编译器对元素的默认值有些过分挑剔。首先，元素不能有不确定的值。也就是说。元素必须要么具有默认值，要么在使用注解时提供元素的值。
 *
 * 其次，对于非基本类型的元素，无论是在源代码中声明时，或是在注解接口中定义默认值时，都不能以null作为其值。这个约束使得处理器很难表现一个元素的存在或缺失的状态，
 * 因为在每个注解的声明中，所有的元素都存在，并且都具有相应的值。为了绕开这个约束，我们只能自己定义一些特殊的值，例如空字符串或负数，以此表示某个元素不存在∶
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimulatingNull {
  public int id() default -1;
  public String description() default "";
} ///:~
/**
 * 在定义注解的时候，这算得上是一个习惯用法。
 */
