//: net/mindview/atunit/Test.java
// The @Test tag.
package com.wby.thread.manythread.character20注解.node1基本语法.child1定义注解;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 下面就是前例中用到的注解@Test的定义。可以看到，注解的定义看起来很像接口的定义。事实上，与其他任何Java接口一样，注解也将会编译成class文件。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {} ///:~
/**
 * 见UseCase
 */



