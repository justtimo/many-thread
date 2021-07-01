package com.wby.thread.manythread.Chapetor10内部类.node8为什么要内部类.内部类与控制框架;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/24 20:41
 * @Description: 在将要介绍的 控制框架 中，可以看到更多的使用内部类的具体例子。
 *  应用程序框架 就是被设计用于解决某些特定问题的一个类或一组类。
 *  要应用某个应用程序框架，通常是继承一个类或多个类，并覆盖某些方法。在覆盖后的方法中，编写代码定制应用程序框架提供的通用解决方案，以解决你的特定问题（这是设计模式中 模板方法 的一个例子）
 *  模板方法包含算法的基本结构，并且会调用一个或多个可覆盖的方法，以完成算法动作。
 *  设计模式总是将变化的事物与保持不变的事物分离开，在这个模式中，模板方法是保持不变的事物，可覆盖的方法就是变化的事物。
 *
 *  控制框架是一类特殊的应用程序框架。，它用来解决响应事件的需求。
 *  主要用来响应事件的系统被称作 事件驱动系统。
 *
 *  要理解内部类是如何允许简单地创建过程以及如何使用控制框架的，参考这个例子：它的工作就是在事件‘就绪’的时候执行事件。对于控制什么，控制框架并不包含任何具体信息。
 *  那些信息是在实现算法的action（）部分时，通过继承提供的
 *  首先，接口描述了要控制的事件。因为默认基于时间执行控制，所以使用抽象类代替接口。下面的例子还包含了某些实现
 */
public abstract class Event {
    private long eventTime;
    protected final long delayTime;
    public Event(Long delayTime){
        this.delayTime = delayTime;
        start();
    }
    public void start() {
        eventTime=System.nanoTime()+delayTime;
    }
    public boolean ready(){
        return System.nanoTime() >= eventTime;
    }
    public abstract void action();
}
/**
* @Description: 当希望运行Event并随后调用start()时，那么构造器就会捕获（从对象创建的时候开始的）时间，此时间是这样得来的：start（）获取当前时间，然后加上一个延迟时间，这样生成触发事件的时间。
 *  start（）是一个独立的方法，而没有包含在构造器内，因为这样就可以在事件运行以后重新启动计时器，也就是能够重复使用Event对象。例如：如果想要重复一个事件，只需要简单地在action（）中调用start（）方法。
 *  ready（）告诉你何时可以运行action（）方法了。当然，可以在导出类中覆盖ready（），使得Event能够基于时间以外的其他因素而触发。
 *
 *  下面的例子包含了一个用来管理并触发事件的实际控制框架。Event对象被保存在List<Event>类型的容器对象中，
*/
 class Controller{
    private List<Event> eventList=new ArrayList<Event>();
    public void addEvent(Event c){
        eventList.add(c);
    }
    public void run(){
        while (eventList.size() > 0)
            for (Event e : new ArrayList<Event>(eventList)) {
                if (e.ready()){
                    System.out.println(e);
                    e.action();
                    eventList.remove(e);
                }
            }
    }
}
/**
* @Description: TODO run()方法遍历寻找就绪的（ready）、要运行的Event对象。对找到的每一个ready（）事件，打印其信息，调用其antion（）方法，然后从队列中移除此Event。
 *
 *  注意，在目前的设计中，你并不知道Event到底做了什么。这正是此设计的关键所在，“使变化的事物与不变的事物互相分离”。
 *  换句话说，“变化向量”就是各种不同的Event对象所具有的不同行为，而你通过创建不同的Event子类来表现不同的行为。
 *  这正是内部类要做的事情，，内部类允许：
 *      1. 控制框架的完整实现是由单个的类创建的，从而使得实现的细节被封装起来。内部类用来表示解决问题所需要的各种不同的action（）
 *      2. 内部类能够很容易地访问外围类的任意成员，所以可以避免这种实现变得笨拙。如果没有这种能力，代码将变得繁琐以至于让人使用别的方法
 *
 *  考虑此控制框架的一个特定实现，如控制温室的运作：控制灯光、水、温度调节器的开关，以及响铃和重新启动系统，每个行为都是不同的。
 *  控制框架的设计使得分离这些不同的代码非常容易。使用内部类，可以在单一的类里面产生对同一个基类Event的多种导出版本。
 *  对于温室系统的每一种行为，都继承一个新的Event内部类，并在要实现的action（）中编写控制代码。
 *
 *  作为典型的应用程序框架，GreenhouseControls类继承子Controller
*/
class GreenhouseControls extends Controller{
    private boolean light= false;
    public class LightOn extends Event{
        public LightOn(Long delayTime) {
            super(delayTime);
        }
        @Override
        public void action() {
            light = true;
        }
        @Override
        public String toString() {
            return "Light is on";
        }
    }
    public class LightOff extends Event{
        public LightOff(Long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            light=false;
        }
        @Override
        public String toString() {
            return "Light is off";
        }
    }

    private boolean water=false;

    public class WaterOn extends Event {
        public WaterOn(Long delayTime) {
            super(delayTime);
        }
        @Override
        public void action() {
            water=true;
        }
        @Override
        public String toString() {
            return "Greenhouse water is On";
        }
    }

    public class WaterOff extends Event {
        public WaterOff(Long delayTime) {
            super(delayTime);
        }
        @Override
        public void action() {
            water=true;
        }
        @Override
        public String toString() {
            return "Greenhouse water is Off";
        }
    }


    private String thermostat="Day";
    public class ThermostatNight extends Event {
        public ThermostatNight(Long delayTime) {
            super(delayTime);
        }
        @Override
        public void action() {
            thermostat="Night";
        }
        @Override
        public String toString() {
            return "thermostat on night setting";
        }
    }

    public class ThermostatDay extends Event {
        public ThermostatDay(Long delayTime) {
            super(delayTime);
        }
        @Override
        public void action() {
            thermostat="Night";
        }
        @Override
        public String toString() {
            return "thermostat on day setting";
        }
    }

    public class Bell extends Event {
        public Bell(Long delayTime) {
            super(delayTime);
        }
        @Override
        public void action() {
            addEvent(new Bell(delayTime));
        }
        @Override
        public String toString() {
            return "bing";
        }
    }

    public class Restart extends Event {
        private Event[] eventList;
        public Restart(Long delayTime,Event[] eventList) {
            super(delayTime);
            this.eventList = eventList;
            for (Event e : eventList) {
                addEvent(e);
            }
        }
        @Override
        public void action() {
            for (Event e : eventList) {
                e.start();
                addEvent(e);
            }
            start();
            addEvent(this);
        }
        @Override
        public String toString() {
            return "Restarting system";
        }
    }

    public static class Terminate extends Event{
        public Terminate(Long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            System.exit(0);
        }
        @Override
        public String toString() {
            return "Terminating";
        }
    }
}
/**
* @Description: TODO 注意，Hight、water和thermostat都属于外围类GreenhouseControls，而这些内部类能够自由的访问那些字段，无需任何限制。而且，action方法通常都涉及对某种硬件的控制
 *  大多数Event类看起来都很相似，但是Bell和Restart则比较特殊。Bell控制响铃，然后在事件列表中增加一个Bell对象，于是过一会他可以再次响铃。
 *  可以注意到，内部类很想多重继承：Bell和Restart有Event的所有方法，并且似乎也拥有外围类GreenhouseControls所有方法
 *  一个由Event对象组成的数组被递交给Restart，该数组要加到控制器上。由于Restart也是一个Event对象，所以同样可以吧Restart对象添加到Restart.action()中，使得系统能够有规律的启动自己
 *
 *  下面的类通过创建一个GreenhouseControls对象，并添加不同的Event对象来配置该系统。
 *  这是命令设计模式的一个例子在eventList中的每一个被封装成对象的请求：
*/

class GreenhouseController{
    public static void main(String[] args) {
        GreenhouseControls gc = new GreenhouseControls();
        gc.addEvent(gc.new Bell(900L));
        Event[] events={
                gc.new ThermostatNight(0L),
                gc.new LightOn(200L),
                gc.new LightOff(400L),
                gc.new WaterOn(600L),
                gc.new WaterOff(800L),
                gc.new ThermostatDay(1400L)
        };
        gc.addEvent(gc.new Restart(2000L,events));
        if (args.length == 1){
            gc.addEvent(
                    new GreenhouseControls.Terminate(
                            new Long(args[0])));
            gc.run();
        }
    }
}

/**
* @Description: TODO 这个类的作用是初始化系统，所以他添加了所有相应的事件。Restart事件反复运行，而且它每次都会将eventList加在到GreenhouseControls对象中。
 *  如果提供了命令参数，系统会将他作为毫秒数，决定什么是时候终止程序（这是测试程序时使用的）。当然，更灵活的方法是避免对事件进行编码，取而代之的是从文件中读取需要的事件
*/



















