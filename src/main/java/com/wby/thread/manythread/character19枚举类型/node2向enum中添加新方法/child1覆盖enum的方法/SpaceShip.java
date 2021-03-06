package com.wby.thread.manythread.character19枚举类型.node2向enum中添加新方法.child1覆盖enum的方法;


/**
 * 覆盖toSringO方法，给我们提供了另一种方式来为枚举实例生成不同的字符串描述信息在下面的示例中，我们使用的就是实例的名字，不过我们希望改变其格式。覆盖enum的 toSringO方法与覆盖一般类的方法没有区别∶
 */
//: enumerated/SpaceShip.java
public enum SpaceShip {
  SCOUT, CARGO, TRANSPORT, CRUISER, BATTLESHIP, MOTHERSHIP;
  public String toString() {
    String id = name();
    String lower = id.substring(1).toLowerCase();
    return id.charAt(0) + lower;
  }
  public static void main(String[] args) {
    for(SpaceShip s : values()) {
      System.out.println(s);
    }
  }
} /* Output:
Scout
Cargo
Transport
Cruiser
Battleship
Mothership
*///:~
/**
 * toString（方法通过调用name（）方法取得SpaceShip的名字，然后将其修改为只有首字母大写的格式。
 */
