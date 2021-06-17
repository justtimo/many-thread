package com.wby.thread.manythread.abstruct;

import java.util.Arrays;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/1 11:22
 * @Description: 策略设计模式。这类方法包含所要执行的算法中固定不变的部分，而“策略”包含变化的部分。策略就是要传递进去的参数对象，它包含要执行的代码。这里，Processor对象就是一个
 * 策略，在main（）方法中可以看到有三种不同类型的策略应用到了String类型的s对象上
 */
public class Apply {
    public static void process(Processor p,Object s){
        System.out.println("Useing processor "+p.name());
        System.out.println(p.process(s));
    }
    public static String s="ni ke zhen niu bi ";

    public static void main(String[] args) {
        process(new Upcase(),s);
        process(new Downcase(),s);
        process(new Splitter(),s);
    }
}
class Processor{
    public String name(){
        return getClass().getSimpleName();
    }
    Object process(Object input){
        return input;
    }
}
class Upcase extends Processor{
    String process(Object input){
        return ((String) input).toUpperCase();
    }
}
class Downcase extends Processor{
    String process(Object input){
        return ((String) input).toLowerCase();
    }
}
class Splitter extends Processor {
    String process(Object input) {
        return Arrays.toString(((String)input).split(""));
    }
}

