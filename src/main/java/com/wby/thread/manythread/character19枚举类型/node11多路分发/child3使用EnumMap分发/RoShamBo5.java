//: enumerated/RoShamBo5.java
// Multiple dispatching using an EnumMap of EnumMaps.
package com.wby.thread.manythread.character19枚举类型.node11多路分发.child3使用EnumMap分发;

import com.wby.thread.manythread.character19枚举类型.node11多路分发.Outcome;
import com.wby.thread.manythread.character19枚举类型.node11多路分发.child1使用enum分发.Competitor;
import com.wby.thread.manythread.character19枚举类型.node11多路分发.child1使用enum分发.RoShamBo;

import java.util.EnumMap;

import static com.wby.thread.manythread.character19枚举类型.node11多路分发.Outcome.*;

/**
 * 使用EnumMap能够实现"真正的"两路分发。EnumMap是为enum专门设计的一种性能非常好的特殊Map。由于我们的目的是摸索出两种未知的类型，所以可以用一个EnumMap的 EnumMap来实现两路分发∶
 */
enum RoShamBo5 implements Competitor<RoShamBo5> {
  PAPER, SCISSORS, ROCK;
  static EnumMap<RoShamBo5,EnumMap<RoShamBo5, Outcome>>
    table = new EnumMap<RoShamBo5,
      EnumMap<RoShamBo5,Outcome>>(RoShamBo5.class);
  static {
    for(RoShamBo5 it : RoShamBo5.values())
      table.put(it,
        new EnumMap<RoShamBo5,Outcome>(RoShamBo5.class));
    initRow(PAPER, DRAW, LOSE, WIN);
    initRow(SCISSORS, WIN, DRAW, LOSE);
    initRow(ROCK, LOSE, WIN, DRAW);
  }
  static void initRow(RoShamBo5 it,
    Outcome vPAPER, Outcome vSCISSORS, Outcome vROCK) {
    EnumMap<RoShamBo5,Outcome> row =
      RoShamBo5.table.get(it);
    row.put(RoShamBo5.PAPER, vPAPER);
    row.put(RoShamBo5.SCISSORS, vSCISSORS);
    row.put(RoShamBo5.ROCK, vROCK);
  }
  public Outcome compete(RoShamBo5 it) {
    return table.get(this).get(it);
  }
  public static void main(String[] args) {
    RoShamBo.play(RoShamBo5.class, 20);
  }
} /* Same output as RoShamBo2.java *///:~
/**
 * 该程序在一个static子句中初始化EnumMap对象，具体见表格似的initRowO方法调用。请注意compete（）方法，您可以看到，在一行语句中发生了两次分发。
 */
