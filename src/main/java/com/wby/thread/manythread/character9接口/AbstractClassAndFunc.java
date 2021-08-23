package com.wby.thread.manythread.character9接口;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/7 13:52
 * @Description: 接口和内部类为我们提供了一种将接口与实现分离的更加结构化的方法
 */
public class AbstractClassAndFunc {
    /**
    * @Author: LangWeiXian
    * @date: 2021/6/7 13:52
    * @Description: TODO 9.1抽象类与抽象方法
     * 抽象类：是普通类和接口之间的一种中庸之道。
     *  在构建某些具有未实现方法的类时，除了接口，抽象类也是一种重要且必须的工具。
     *  因为不可能总是使用纯接口？？？？？？为什么不能使用纯接口
     *
     *  包含抽象方法的类叫做抽象类。如果一个类包含一个或多个抽象方法，该类必须为抽象类。
     *  如果继承抽象类，并想创建该类的新对象，则必须要实现基类中的所有抽象方法。如果不这么做，则这个类也必须是抽象类
    */
    static void tune(Instrument i){
        i.play(Noted.MIDDLE_C);
    }
    static void tuneAll(Instrument[] e){
        for (Instrument instrument : e) {
            tune(instrument);
        }
    }

    public static void main(String[] args) {
        Instrument[] oc={new Wind(),new Percussion(),new Stringed(),new Brand(),new WoodWind()};
        tuneAll(oc);
    }

}
enum Noted{
    MIDDLE_C;
}

abstract class Instrument{
    private int i;
    public abstract void play(Noted n);
    public String what(){return "Instrument";}
    public abstract void adjust();
}
class Wind extends Instrument {

    @Override
    public void play(Noted n) {
        System.out.println("wind play "+n);
    }
    public String what(){return "Wind";}

    @Override
    public void adjust() {

    }
}
class Percussion extends Instrument {

    @Override
    public void play(Noted n) {
        System.out.println("Percussion play "+n);
    }
    public String what(){return "Percussion";}

    @Override
    public void adjust() {

    }
}

class Stringed extends Instrument {

    @Override
    public void play(Noted n) {
        System.out.println("Stringed play "+n);
    }

    @Override
    public void adjust() {

    }
}
class Brand extends Wind{
    public void play(Noted n) {
        System.out.println("Brand play "+n);
    }
    public void adjust() {
        System.out.println("Brand adjust ");
    }
}
class WoodWind extends Wind {
    public void play(Noted n) {
        System.out.println("WoodWind play "+n);
    }
    public String what(){return "WoodWind";}
}



