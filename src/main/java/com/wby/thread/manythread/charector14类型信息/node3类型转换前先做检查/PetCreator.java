//: typeinfo/pets/PetCreator.java
// Creates random sequences of Pets.
package com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查;

import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.pets.Pet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/7 10:08
 * @Description:
 *  迄今为止，我们已知的RTTI形式包括：
 *      1.传统类型转换，如“Shape”，由RTTI确保类型转换的正确性，如果执行一个错误的类型转换，就会抛出一个ClassCastException
 *      2.代表对象类型的Class对象。通过查询Class对象可以获取运行时所需的信息。
 *
 *  java类型转换时，要执行类型检查，这被称为“类型安全的向下转型”。
 *  由于知道Circle肯定是一个Shape，所以编译器允许自由的做向上转型的赋值操作，而不需要任何显示的转型操作。
 *  但是由于不知道给定的Shape到底是什么Shape，因此如果不通过显式的类型转换，编译器就不允许你执行向下转型的操作。你需要告知编译器你拥有的额外信息，
 *  这些信息使你知道该类型是某种特定类型(编译器将检查向下转型是否合理，因此他不允许向下转型到实际上不是待转型类的子类型上)
 *
 *  RTTI还有第三种形式，就是使用关键字instanceof。他返回一个布尔值，判断对象是不是某个特定类型的实例。例如：
 *      if(x instanceof Dog)
 *          ((Dog) x).bark();
 *  在将x转型为一个Dog前，上面的if语句会检查对象x是否从属于Dog类
 *  进行向下转型前，如果没有其他信息可以告诉你这个对象是什么类型，那么使用instanceof关键字非常重要，否则会得到ClassCastException
 *
 *  一般，可能想要查找某种类型(比如三角形并填充为紫色)，这时可以使用instanceof来计数所有对象
 *  例如：假设你有一个类的继承体系，描述了Pet(以及他们的主人，这是在后面例子中出现的一个非常方便的特性)，见pets文件夹。
 *  这个继承体系中的每个Individual都有一个id和一个可选的名字。但Individual代码复杂性较高，会在17章进行讲解。
 *  你只需要知道你可以创建其具名或不具名的对象，并且每个Individual都有一个id方法，可以返回其唯一的标识符(通过对每个对象计数而创建)，还有个toString方法，如果没有为Individual提供名字则只产生类型名
 *
 *  接下来，我们需要一种方法，通过他可以随机的创建不同类型的宠物，并且还可以创建宠物数组和List。为了使该工具适应多种不同的实现，我们将其定义为抽象类
 */
public abstract class PetCreator {
  private Random rand = new Random(47);

  // The List of the different types of Pet to create:
  public abstract List<Class<? extends Pet>> types();

  public Pet randomPet() { // Create one random Pet
    int n = rand.nextInt(types().size());
    try {
      return types().get(n).newInstance();
    } catch(InstantiationException e) {
      throw new RuntimeException(e);
    } catch(IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
  public Pet[] createArray(int size) {
    Pet[] result = new Pet[size];
    for(int i = 0; i < size; i++)
      result[i] = randomPet();
    return result;
  }
  public ArrayList<Pet> arrayList(int size) {
    ArrayList<Pet> result = new ArrayList<Pet>();
    Collections.addAll(result, createArray(size));
    return result;
  }
} ///:~
/**
* @Description:  抽象的types()在导出类中实现，以获取由Class对象构成的List(这是模板方法设计模式的一种变体)。
 *  注意，因为其类型是“Pet的导出类”，所以newInstance()不需要转型就可以产生Pet。randomPet()随机产生List中的索引，并使用选中的Class对象，通过Class。newInstance()来生成该类的新实例
 *  createArray（）方法使用randomPet（）来填充数组，而arrayList（）方法则使用createArray（）
 *
 *  在调用newInstance()时，可能会得到两种异常，在紧跟try语句块后面的catch子句中可以看到他们的处理。
 *  当你导出PetCreator的子类时，唯一所需提供的就是你希望使用randomPet()和其他方法来创建的宠物类型的List。
 *  types()方法通常只返回对一个静态List的引用
 *
 *  下面是使用了forName()的一个具体实现：
*/
class ForNameCreator extends PetCreator {
  private static List<Class<? extends Pet>> types =
          new ArrayList<Class<? extends Pet>>();
  // Types that you want to be randomly created:
  private static String[] typeNames = {
          "typeinfo.pets.Mutt",
          "typeinfo.pets.Pug",
          "typeinfo.pets.EgyptianMau",
          "typeinfo.pets.Manx",
          "typeinfo.pets.Cymric",
          "typeinfo.pets.Rat",
          "typeinfo.pets.Mouse",
          "typeinfo.pets.Hamster"
  };

  @SuppressWarnings("unchecked")
  private static void loader() {
    try {
      for(String name : typeNames)
        types.add(
                (Class<? extends Pet>) Class.forName(name)
        );
    } catch(ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  static {
    loader();
  }
  public List<Class<? extends Pet>> types() {
    return types;
  }
} ///:~
/**
* @Description:  loader方法用Class.forName()创建了Class对象的List，这可能会产生ClassNotFoundException，这么做是有意义的，因为你传递给它的是
 *  一个在编译期无法验证的String。由于Pet对象在typeinfo包中，因此必须使用包名来引用这些类。
 *
 *  为了产生具有实际类型的Class对象的List，必须使用转型，这会产生编译期警告。loader()被单独定义，然后被置于一个静态初始化子句中，因为@SuppressWarnings注解不能
 *  直接置于静态初始化子句之上。
 *
 *  为了对Pet进行计数，我们需要一个能够跟踪各种不同类型的Pet的数量的工具。Map是此需求的首选，键是Pet类型名，值是保存Pet数量的Integer，我们可以使用instanceof来对Pet进行计数：
 *  见PetCount.java
*/

