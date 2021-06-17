package com.wby.thread.manythread.constructor;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/5/31 18:17
 * @Description:
 */
public class CovariantConstructor {
    public static void main(String[] args) {
        Mill m = new Mill();
        Grain g = m.process();
        System.out.println(g);
       m = new WheatMill();
       g=m.process();
        System.out.println(g);
    }
}
class Grain{
    @Override
    public String toString() {
        return "Grain{}";
    }
}
class Wheat extends Grain {
    @Override
    public String toString() {
        return "Wheat{}";
    }
}
class Mill{
    Grain process(){
        return new Grain();
    }
}
class WheatMill extends Mill{
    Wheat process() {
        return new Wheat();
    }
}
