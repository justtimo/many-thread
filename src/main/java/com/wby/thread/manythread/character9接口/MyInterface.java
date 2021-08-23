package com.wby.thread.manythread.character9接口;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/7 14:18
 * @Description: interface不仅仅是一个极度抽象的类。因为它允许创建一个能够被向上转型为多种基类的类型，实现某种类似多重继变种的特性
 *      接口可以包含域。但这些隐式的都是static和fianal的
 *      只要一个方法操作的是类而非接口，那么你就只能使用这个类及其子类。如果想要将这个方法应用在不在此继承结构中的某个类，就要倒霉了。接口很大程度上放宽了这种限制
 */
public class MyInterface {

}
interface InstrumentInter{
     int i = 5; //static & finnal
    void play(Noted n);
     String what();
    void adjust();
}

