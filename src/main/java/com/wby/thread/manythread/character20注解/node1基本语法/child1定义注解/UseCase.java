package com.wby.thread.manythread.character20注解.node1基本语法.child1定义注解;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 除了@符号以外，@Test的定义很像一个空的接口。定义注解时，会需要一些元注解（meta-annotation），如@Target和@Retention。
 * @Target用来定义你的注解将应用于什么地方（例如是一个方法或者一个域）。
 * @Rectetion用来定义该注解在哪一个级别可用，在源代码中（SOURCE）、类文件中（CLASS）或者运行时（RUNTIME）。
 *
 * 在注解中，一般都会包含一些元素以表示某些值。当分析处理注解时，程序或工具可以利用这些值。注解的元素看起来就像接口的方法，唯一的区别是你可以为其指定默认值。
 * 没有元素的注解称为标记注解（marker annotation），例如上例中的@Test。
 *
 * 下面是一个简单的注解，我们可以用它来跟踪一个项目中的用例。如果一个方法或一组方法实现了某个用例的需求，那么程序员可以为此方法加上该注解。
 * 于是，项目经理通过计算已经实现的用例，就可以很好地掌控项目的进展。而如果要更新或修改系统的业务逻辑，则维护该项目的开发人员也可以很容易地在代码中找到对应的用例。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
    public int id();
    public String description() default "no description";
} ///:~
/**
 * 见PasswordUtils
 */
