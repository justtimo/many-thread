package com.wby.thread.manythread.Chapetor15泛型.node15混型.child2与接口混合;//: generics/Mixins.java

import java.util.Date;

class Mixin extends BasicImp
implements TimeStamped, SerialNumbered {
  private TimeStamped timeStamp = new TimeStampedImp();
  private SerialNumbered serialNumber =
    new SerialNumberedImp();
  public long getStamp() { return timeStamp.getStamp(); }
  public long getSerialNumber() {
    return serialNumber.getSerialNumber();
  }
}

public class Mixins {
  public static void main(String[] args) {
    Mixin mixin1 = new Mixin(), mixin2 = new Mixin();
    mixin1.set("test string 1");
    mixin2.set("test string 2");
    System.out.println(mixin1.get() + " " +
      mixin1.getStamp() +  " " + mixin1.getSerialNumber());
    System.out.println(mixin2.get() + " " +
      mixin2.getStamp() +  " " + mixin2.getSerialNumber());
  }
} /* Output: (Sample)
test string 1 1132437151359 1
test string 2 1132437151359 2
*///:~
/**
* @Description: Mixin类基本上是在使用代理，因此每个混入类型都要求在Mixin中有一个相应的域，而你必须在Mixin中编写所有必需的方法，将方法调用转发给恰当的对象。
 * 这个例子使用了非常简单地类，但是当使用更复杂的混形时，代码会急剧增加：某些IDE可以自动生成代理代码
*/
