package com.wby.thread.manythread.charector14类型信息.node4注册工厂;//: typeinfo/RegisteredFactories.java
// Registering Class Factories in the base class.


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description:  生成Pet继承结构中的对象存在着一个问题，即每次向该继承结构添加新的Pet类型时，必须将其添加为LiteralPetCreator.java中的项。
 *  如果系统中已经存在了继承结构的常规的基础，然后在其上要添加更多的类，那么就可能出现问题。
 *
 *  也许可以在每个子类中添加静态初始化器，以使得该初始化器可以将它的类添加到某个List中。遗憾的是，静态初始化器只有在类首先被加载的情况下才能被调用，
 *  因此就会遇到“先有鸡还是先有蛋”的问题；生成器在其列表中不包含这个类，因此他永远不能创建这个类的对象，而这个类也就不能被加载并置于这个列表中。
 *
 *  最主要是因为，你被强制要求自己手工去创建这个列表(除非编写一个工具，可以全面搜索和分析源代码，然后创建和编译这个列表)。
 *  因此，最佳做法应该是，将这个列表置于一个中心的、位置明显的地方，而我们感兴趣的继承结构的基类可能就是这个最佳位置。
 *
 *  这里需要做的其他修改就是使用 工厂方法设计模式 ，将对象的创建工作交给类自己去完成。
 *  工厂方法可以被多态的调用，从而为你创建恰当类型的对象。
 *  下面这个是非常简单的版本，工厂方法就是Factory接口中的create()方法：
 */
interface Factory<T> {
  T create();
}
/**
* @Description: 泛型参数T使得create方法可以在每种Factory实现中返回不同的类型。这也充分利用了协变返回类型
*/
/**
 * @Description: 下面例子中，基类Part包含一个工厂对象的列表。对于应这个由createRandom()产生的类型，他们的工厂都被添加到了partFactories List中，从而被注册到基类
 */
class Part {
  public String toString() {
    return getClass().getSimpleName();
  }

  static List<Factory<? extends Part>> partFactories = new ArrayList<Factory<? extends Part>>();

  static {
    // Collections.addAll() gives an "unchecked generic
    // array creation ... for varargs parameter" warning.
    partFactories.add(new FuelFilter.Factory());
    partFactories.add(new AirFilter.Factory());
    partFactories.add(new CabinAirFilter.Factory());
    partFactories.add(new OilFilter.Factory());
    partFactories.add(new FanBelt.Factory());
    partFactories.add(new PowerSteeringBelt.Factory());
    partFactories.add(new GeneratorBelt.Factory());
  }
  private static Random rand = new Random(47);
  public static Part createRandom() {
    int n = rand.nextInt(partFactories.size());
    return partFactories.get(n).create();
  }
}

class Filter extends Part {}

class FuelFilter extends Filter {
  // Create a Class Factory for each specific type:
  public static class Factory implements com.wby.thread.manythread.charector14类型信息.node4注册工厂.Factory<FuelFilter> {
    public FuelFilter create() {
      return new FuelFilter();
    }
  }
}

class AirFilter extends Filter {
  public static class Factory implements com.wby.thread.manythread.charector14类型信息.node4注册工厂.Factory<AirFilter> {
    public AirFilter create() { return new AirFilter(); }
  }
}

class CabinAirFilter extends Filter {
  public static class Factory implements com.wby.thread.manythread.charector14类型信息.node4注册工厂.Factory<CabinAirFilter> {
    public CabinAirFilter create() {
      return new CabinAirFilter();
    }
  }
}

class OilFilter extends Filter {
  public static class Factory implements com.wby.thread.manythread.charector14类型信息.node4注册工厂.Factory<OilFilter> {
    public OilFilter create() { return new OilFilter(); }
  }
}

class Belt extends Part {}

class FanBelt extends Belt {
  public static class Factory implements com.wby.thread.manythread.charector14类型信息.node4注册工厂.Factory<FanBelt> {
    public FanBelt create() { return new FanBelt(); }
  }
}

class GeneratorBelt extends Belt {
  public static class Factory implements com.wby.thread.manythread.charector14类型信息.node4注册工厂.Factory<GeneratorBelt> {
    public GeneratorBelt create() {
      return new GeneratorBelt();
    }
  }
}

class PowerSteeringBelt extends Belt {
  public static class Factory implements com.wby.thread.manythread.charector14类型信息.node4注册工厂.Factory<PowerSteeringBelt> {
    public PowerSteeringBelt create() {
      return new PowerSteeringBelt();
    }
  }
}

public class RegisteredFactories {
  public static void main(String[] args) {
    for(int i = 0; i < 10; i++)
      System.out.println(Part.createRandom());
  }
} /* Output:
GeneratorBelt
CabinAirFilter
GeneratorBelt
AirFilter
PowerSteeringBelt
CabinAirFilter
FuelFilter
PowerSteeringBelt
PowerSteeringBelt
FuelFilter
*///:~
/**
* @Description: 并非所有在继承结构中的类都应该被实例化，本例中，Filter和Belt只是分类标识，因此你不应该创建他们的实例，而只应该创建他们子类的实例。
 *  如果某个类应该由createRandom()创建，那么他就包含一个内部Factory类。如上所示，重用名字Factory的唯一方式就是限定Factory
 *
 *  尽管可以使用Collections.addAll()来向列表中添加工厂，但是这样做编译器就会抛出关于“创建泛型数组”(这被认为是不可能的)的警告，因此转而使用add()。
 *  createRandom()方法从partFactories中随机的选取一个工厂对象，然后调用create方法，从而产生一个新的Part。
*/
