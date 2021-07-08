package com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查;//: typeinfo/PetCount.java
// Using instanceof.

import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.pets.*;
import java.util.HashMap;
import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 * @Description:  countPets()中使用PetCreator来随机向数组中填充Pet对象。然后用instanceof对该数组中的每个Pet进行测试和计数
 *  对instanceof有比较严格的限制：只可将其与命名类型进行比较，而不能与Class对象作比较。
 *
 *  下面的例子中，一堆instanceof很乏味，但是也没有办法让instanceof聪明起来，让他能够自动创建一个Class对象的数组，然后将目标对象与这
 *  个数组中的对象逐一比较(稍后会看到一个替代方案)
 *
 *  这并非一个那么好的限制，因为如果程序中有许多instanceof表达式，说明你的设计可能存在瑕疵
 */
public class PetCount {
  static class PetCounter extends HashMap<String,Integer> {
    public void count(String type) {
      Integer quantity = get(type);
      if(quantity == null)
        put(type, 1);
      else
        put(type, quantity + 1);
    }
  }
  public static void countPets(PetCreator creator) {
    PetCounter counter= new PetCounter();
    for(Pet pet : creator.createArray(20)) {
      // List each individual pet:
      printnb(pet.getClass().getSimpleName() + " ");
      if(pet instanceof Pet)
        counter.count("Pet");
      if(pet instanceof Dog)
        counter.count("Dog");
      if(pet instanceof Mutt)
        counter.count("Mutt");
      if(pet instanceof Pug)
        counter.count("Pug");
      if(pet instanceof Cat)
        counter.count("Cat");
      if(pet instanceof Manx)
        counter.count("EgyptianMau");
      if(pet instanceof Manx)
        counter.count("Manx");
      if(pet instanceof Manx)
        counter.count("Cymric");
      if(pet instanceof Rodent)
        counter.count("Rodent");
      if(pet instanceof Rat)
        counter.count("Rat");
      if(pet instanceof Mouse)
        counter.count("Mouse");
      if(pet instanceof Hamster)
        counter.count("Hamster");
    }
    // Show the counts:
    print();
    print(counter);
  }
  public static void main(String[] args) {
    countPets(new ForNameCreator());
  }
} /* Output:
Rat Manx Cymric Mutt Pug Cymric Pug Manx Cymric Rat EgyptianMau Hamster EgyptianMau Mutt Mutt Cymric Mouse Pug Mouse Cymric
{Pug=3, Cat=9, Hamster=1, Cymric=7, Mouse=2, Mutt=3, Rodent=5, Pet=20, Manx=7, EgyptianMau=7, Dog=6, Rat=2}
*///:~
/**
* @Description:
*/
