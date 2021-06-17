package com.wby.thread.manythread.interfacce.适配器模式.filters;

import com.wby.thread.manythread.interfacce.适配器模式.Waveform;

import java.util.Arrays;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/7 14:59
 * @Description:
 * Filter与Processor具有相同的接口元素，但并非是他继承自Processor，因此不能将Filter用户Apply.process()方法。
 * 即便这样做可以正常运行。这是因为Apply.process()和Processor之间的耦合过紧。此外他输入和输出的是Waveform，已经超出
 * 所需要的的程度，这就使得复用Apply.process()时，复用被禁止了。
 *
 * 但是如果Processor是一个接口，这个限制就松动很多。使得你可以复用Apply.process()
 */
public class Filter   {
    public String name(){
        return getClass().getSimpleName();
    }
    public Waveform process(Waveform input){
        return input;
    }
}
interface Processor{
    public String name();
    Object process(Object input);
}
class Apply{
    public static void process(Processor p,Object o){
        System.out.println("using Processor "+p.name());
        System.out.println(p.process(o));
    }
}
/**
* @Author: LangWeiXian
* @Description: TODO 复用代码的第一种方式是遵循接口编写他们的类
*/
abstract class StringProcessor  implements Processor{
    public String name() {
        return getClass().getSimpleName();
    }
    public abstract String process(Object input);

    public static String s="wo di ge shen a ,shenme dongxi";

    public static void main(String[] args) {
        Apply.process(new Upcase(),s);
        Apply.process(new Downcase(),s);
        Apply.process(new Splitter(),s);
    }

}
class Upcase extends StringProcessor {
    public String process(Object input){
        return ((String) input).toUpperCase();
    }
}
class Downcase extends StringProcessor {
    public String process(Object input){
        return ((String) input).toLowerCase();
    }
}
class Splitter extends StringProcessor {
    public String process(Object input) {
        return Arrays.toString(((String)input).split(""));
    }
}
/**
* @Author: LangWeiXian
* @Description: TODO
 * 但是，更常见的情况是：你无法修改你想使用的类：比如使用导入的jar包中的类。
 * 在这些情况下，可以使用适配器设计模式。
 * 适配器中的代码将接受你所拥有的接口，并产生你所需要的接口，
*/
class FilterAdpter implements Processor{
    Filter filter;

    public FilterAdpter(Filter filter){
        this.filter = filter;
    }

    @Override
    public String name() {
        return filter.name();
    }

    @Override
    public Object process(Object input) {
        return filter.process((Waveform) input);
    }
}
class FilterProcessor{
    public static void main(String[] args) {
        Waveform waveform = new Waveform();
        Apply.process(new FilterAdpter(new LowPass(1.0)),waveform);
        Apply.process(new FilterAdpter(new HighPass(2.0)),waveform);
        Apply.process(new FilterAdpter(new BandPass(3.0,4.0)),waveform);
    }
}

