package com.wby.thread.manythread.character20注解.node2编写注解处理器;//: annotations/UseCaseTracker.java
import com.wby.thread.manythread.character20注解.node1基本语法.child1定义注解.PasswordUtils;
import com.wby.thread.manythread.character20注解.node1基本语法.child1定义注解.UseCase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 如果没有用来读取注解的工具，那注解也不会比注释更有用。使用注解的过程中，很重要的一个部分就是创建与使用注解处理器。Java SE5扩展了反射机制的API，
 * 以帮助程序员构造这类工具。同时，它还提供了一个外部工具apt帮助程序员解析带有注解的Java源代码。
 *
 * 下面是一个非常简单的注解处理器，我们将用它来读取PasswordUtils类，并使用反射机制查找@UseCase标记。我们为其提供了一组id值，然后它会列出在PasswordUtils中找到的用例，以及缺失的用例。
 */
public class UseCaseTracker {
  public static void
  trackUseCases(List<Integer> useCases, Class<?> cl) {
    for(Method m : cl.getDeclaredMethods()) {
      UseCase uc = m.getAnnotation(UseCase.class);
      if(uc != null) {
        System.out.println("Found Use Case:" + uc.id() +
          " " + uc.description());
        useCases.remove(new Integer(uc.id()));
      }
    }
    for(int i : useCases) {
      System.out.println("Warning: Missing use case-" + i);
    }
  }
  public static void main(String[] args) {
    List<Integer> useCases = new ArrayList<Integer>();
    Collections.addAll(useCases, 47, 48, 49, 50);
    trackUseCases(useCases, PasswordUtils.class);
  }
} /* Output:
Found Use Case:47 Passwords must contain at least one numeric
Found Use Case:48 no description
Found Use Case:49 New passwords can't equal previously used ones
Warning: Missing use case-50
*///:~
/**
 * 这个程序用到了两个反射的方法∶ getDeclaredMethods（）和getAnnotation（），它们都属干 AnnotatedElement接口（Class、Method与Field等类都实现了该接口）。
 * getAnnoationO方法返回指定类型的注解对象，在这里就是UseCase。如果被注解的方法上没有该类型的注解，则返回 null值。然后我们通过调用idO和descriptionO方法从返回的UseCase对象中提取元素的值。
 * 其中， encriptPasswordO方法在注解的时候没有指定description的值，因此处理器在处理它对应的注解时，通过description（）方法取得的是默认值no description。
 */
