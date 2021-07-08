//: typeinfo/pets/LiteralPetCreator.java
// Using class literals.
package com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.child4类型转换前先做检查.child1使用类字面常量;

import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.child4类型转换前先做检查.PetCount;
import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.child4类型转换前先做检查.PetCreator;
import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.child4类型转换前先做检查.pets.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description:  如果我们用类字面常量重新实现PetCount，那么改写后的结果在许多方面都会显得更加清晰：
 */
public class LiteralPetCreator extends PetCreator {
  // No try block needed.
  @SuppressWarnings("unchecked")
  public static final List<Class<? extends Pet>> allTypes =
    Collections.unmodifiableList(Arrays.asList(
      Pet.class, Dog.class, Cat.class,  Rodent.class,
      Mutt.class, Pug.class, EgyptianMau.class, Manx.class,
      Cymric.class, Rat.class, Mouse.class,Hamster.class));
  // Types for random creation:
  private static final List<Class<? extends Pet>> types =
    allTypes.subList(allTypes.indexOf(Mutt.class),
      allTypes.size());
  public List<Class<? extends Pet>> types() {
    return types;
  }
  public static void main(String[] args) {
    System.out.println(types);
  }
} /* Output:
[class typeinfo.pets.Mutt, class typeinfo.pets.Pug, class typeinfo.pets.EgyptianMau, class typeinfo.pets.Manx, class typeinfo.pets.Cymric, class typeinfo.pets.Rat, class typeinfo.pets.Mouse, class typeinfo.pets.Hamster]
*///:~
/**
* @Description: 在即将出现的PetCount3.java示例中，我们需要先用所有的Pet类型来预加载一个Map(而仅仅只是那些将要随机生成的类型)，因此allTypes List是必需的。
 *  types列表是allTypes的一部分(通过使用List.subList()创建的)，他包含了确定的宠物类型，因此它被用于随机Pet生成。
 *
 *这一次，生成的types的代码不需要放在try内，因为他会在编译时得到检查，因此他不会抛出任何异常，这与Class.forName()不同
 *
 * 我们现在有了两种PetCreator实现。为了将第二种是实现作为默认实现，我们可以创建一个使用了LiteralPetCreator的外观
*/
class Pets {
  public static final PetCreator creator =
          new LiteralPetCreator();
  public static Pet randomPet() {
    return creator.randomPet();
  }
  public static Pet[] createArray(int size) {
    return creator.createArray(size);
  }
  public static ArrayList<Pet> arrayList(int size) {
    return creator.arrayList(size);
  }
} ///:~
/**
* @Description:  这个类还提供了对randomPet()、createArray()、arrayList()的间接调用。
 *  因为PetCount.countPets()接受的是一个PetCreator参数，我们可以很容易地测试LiteralPetCreator(通过上面的外观)：
*/
class PetCount2 {
  public static void main(String[] args) {
    PetCount.countPets(Pets.creator);
  }
} /* (Execute to see output) *///:~
/**
* @Description: 该实例的输出与PetCount.java相同
*/
