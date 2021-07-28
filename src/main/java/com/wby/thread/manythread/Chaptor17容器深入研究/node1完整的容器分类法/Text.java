package com.wby.thread.manythread.Chaptor17容器深入研究.node1完整的容器分类法;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/19 14:11
 * @Description: 展示了java容器类库的简化图。下面是集合类库更加完备的图。包括抽象类和遗留构建(不包括Queue的实现)
 *
 *  1.5新添加了：
 *      Queue接口(正如11章所介绍的，LinkedList已经为实现该接口做了修改)以及实现PriorityQueue和各种风格的BlockingQueue，其中BlockingQueue将
 *          在21章中介绍。
 *      ConcurrentMap接口以及实现ConcurrentHashMap，他们是用于多线程机制的，同样会在21章中介绍。
 *      CopyOnWriteArrayList和CopyOnWriteArraySet，他们也是用于多线程机制的。
 *      EnumSet和EnumMap，未使用enum而设计的Set和Map的特殊实现，将在19章介绍
 *      在Collections类中的多个便利方法
 *
 *      虚线框表示abstract类，可以看到大量的类的名字都是以Abstract开头的。这些类可能初看起来令人困惑，但是他们只是部分实现了特定接口的工具。。例如，
 *      如果在创建自己的Set，那么并不用从Set接口开始并实现其中的全部方法，只需从AbstractSet继承，然后执行一些创建新类必需的工作。
 *      但是，事实上容器类包含足够多的功能，任何时刻都可以满足你的需求，因此你通常可以忽略以Abstract开头的这些类。
 */
public class Text {
}
