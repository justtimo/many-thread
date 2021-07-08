//: net/mindview/util/TypeCounter.java
// Counts instances of a type family.
package com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.child4类型转换前先做检查.child3递归计数;

import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.child4类型转换前先做检查.pets.Pet;
import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.child4类型转换前先做检查.pets.Pets;

import java.util.HashMap;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;


/**
 * @Description:  在PetCount3.PetCounter中的Map预加载了所有不同的Pet类。与映射表不同的是，我们可以使用Class.isAssignableFrom()，并创建一个不局限于对pet计数的通用工具
 */
public class TypeCounter extends HashMap<Class<?>,Integer>{
  private Class<?> baseType;
  public TypeCounter(Class<?> baseType) {
    this.baseType = baseType;
  }
  public void count(Object obj) {
    Class<?> type = obj.getClass();
    if(!baseType.isAssignableFrom(type))
      throw new RuntimeException(obj + " incorrect type: "
        + type + ", should be type or subtype of "
        + baseType);
    countClass(type);
  }
  private void countClass(Class<?> type) {
    Integer quantity = get(type);
    put(type, quantity == null ? 1 : quantity + 1);
    Class<?> superClass = type.getSuperclass();
    if(superClass != null &&
       baseType.isAssignableFrom(superClass))
      countClass(superClass);
  }
  public String toString() {
    StringBuilder result = new StringBuilder("{");
    for(Entry<Class<?>,Integer> pair : entrySet()) {
      result.append(pair.getKey().getSimpleName());
      result.append("=");
      result.append(pair.getValue());
      result.append(", ");
    }
    result.delete(result.length()-2, result.length());
    result.append("}");
    return result.toString();
  }
} ///:~
/**
* @Description: count()获取其参数的Class，然后使用isAssignableFrom()来执行运行时检查，以校验你传递的对象确实属于我们感兴趣的继承结构。
 *  countClass()首先对该类的确切类型计数，然后，如果其超类可以赋值给baseType，countClass()将其超类上递归计数。
*/
class PetCount4 {
  public static void main(String[] args) {
    TypeCounter counter = new TypeCounter(Pet.class);
    for(Pet pet : Pets.createArray(20)) {
      printnb(pet.getClass().getSimpleName() + " ");
      counter.count(pet);
    }
    print();
    print(counter);
  }
} /* Output: (Sample)
Rat Manx Cymric Mutt Pug Cymric Pug Manx Cymric Rat EgyptianMau Hamster EgyptianMau Mutt Mutt Cymric Mouse Pug Mouse Cymric
{Mouse=2, Dog=6, Manx=7, EgyptianMau=2, Rodent=5, Pug=3, Mutt=3, Cymric=5, Cat=9, Hamster=1, Pet=20, Rat=2}
*///:~
/**
* @Description: 正如输出中所看到的，对基类型和确切类型都进行了计数。
*/
