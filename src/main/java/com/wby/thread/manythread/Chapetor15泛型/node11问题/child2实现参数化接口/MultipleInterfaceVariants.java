package com.wby.thread.manythread.Chapetor15泛型.node11问题.child2实现参数化接口;//: generics/MultipleInterfaceVariants.java
// {CompileTimeError} (Won't compile)

interface Payable<T> {}

class Employee implements Payable<Employee> {}

//class Hourly extends Employee implements Payable<Hourly> {} ///:~
/**
* @Description: Hourlu不能编译，因为擦除会将Payable<Employee>和Payable<Hourly>简化为相同的类Payable，这样，上面的代码就意味着在重复两次
 * 地实现相同的接口。有趣的是，如果从Payable的两种用法中都移除掉泛型参数(就像编译器在擦除阶段所做的那样)这段代码就可以编译。
 *
 * 在使用某些更基本的java接口，例如Comparable<T>时，这个问题可能会变得十分恼人，就像你再本节稍后会看到的那样。
*/
