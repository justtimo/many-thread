package com.wby.thread.manythread.character9接口.node5通过继承来扩展接口;//: interfaces/HorrorShow.java
// Extending an interface with inheritance.

/**
 * 通过继承，可以很容易地在接口中添加新的方法声明，还可以通过继承在新接口中组合数个接口。这两种情况都可以获得新的接口，就像在下例中所看到的∶
 */
interface Monster {
  void menace();
}

interface DangerousMonster extends Monster {
  void destroy();
}

interface Lethal {
  void kill();
}

class DragonZilla implements DangerousMonster {
  public void menace() {}
  public void destroy() {}
}

interface Vampire extends DangerousMonster, Lethal {
  void drinkBlood();
}

class VeryBadVampire implements Vampire {
  public void menace() {}
  public void destroy() {}
  public void kill() {}
  public void drinkBlood() {}
}

public class HorrorShow {
  static void u(Monster b) { b.menace(); }
  static void v(DangerousMonster d) {
    d.menace();
    d.destroy();
  }
  static void w(Lethal l) { l.kill(); }
  public static void main(String[] args) {
    DangerousMonster barney = new DragonZilla();
    u(barney);
    v(barney);
    Vampire vlad = new VeryBadVampire();
    u(vlad);
    v(vlad);
    w(vlad);
  }
} ///:~
/**
 * DangerousMonster是Monster的直接扩展，它产生了一个新接口。DragonZilla中实现了这个接口。
 * 在Vampire中使用的语法仅适用于接口继承。一般情况下，只可以将extends用于单一类，但是可以引用多个基类接口。就像所看到的，只需用逗号将接口名一一分隔开即可。
 */
