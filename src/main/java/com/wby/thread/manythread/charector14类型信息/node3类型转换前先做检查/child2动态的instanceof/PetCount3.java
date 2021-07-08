package com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.child2动态的instanceof;

import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.child1使用类字面常量.LiteralPetCreator;
import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.pets.Pet;
import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.pets.Pets;
import com.wby.thread.manythread.net.mindview.util.MapData;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/7 14:18
 * @Description:    Class.isInstance方法提供了一种动态的测试对象的途径。于是所有的单调的instanceof语句都可以从PetCount.java中移除了
 *  如下所示：
 */
public class PetCount3 {
    static class PetCounter extends LinkedHashMap<Class<? extends Pet>,Integer> {
        public PetCounter() {
            super(MapData.map(LiteralPetCreator.allTypes, 0));
        }
        public void count(Pet pet) {
            // Class.isInstance() eliminates instanceofs:
            for(Map.Entry<Class<? extends Pet>,Integer> pair : entrySet()){
                if(pair.getKey().isInstance(pet)){
                    put(pair.getKey(), pair.getValue() + 1);
                }
            }
        }
        public String toString() {
            StringBuilder result = new StringBuilder("{");
            for(Map.Entry<Class<? extends Pet>,Integer> pair : entrySet()) {
                result.append(pair.getKey().getSimpleName());
                result.append("=");
                result.append(pair.getValue());
                result.append(", ");
            }
            result.delete(result.length()-2, result.length());
            result.append("}");
            return result.toString();
        }
    }
    public static void main(String[] args) {
        PetCounter petCount = new PetCounter();
        for(Pet pet : Pets.createArray(20)) {
            printnb(pet.getClass().getSimpleName() + " ");
            petCount.count(pet);
        }
        print();
        print(petCount);
    }
} /* Output:
Rat Manx Cymric Mutt Pug Cymric Pug Manx Cymric Rat EgyptianMau Hamster EgyptianMau Mutt Mutt Cymric Mouse Pug Mouse Cymric
{Pet=20, Dog=6, Cat=9, Rodent=5, Mutt=3, Pug=3, EgyptianMau=2, Manx=7, Cymric=5, Rat=2, Mouse=2, Hamster=1}
*///:~
/**
* @Description: 为了对所有的不同类型的Pet进行计数，PetCount Map预加载了LiteralPetCreator.allTypes中的类型。
 *  这使用了MapData类，这个类接受一个Iterable(allTypes List)和一个常数值(本例中是0),然后用allTypes中的元素作为键，用0作为值，来填充Map。
 *  如果不预加载这个Map，那么你最终将只能对随机生成的类型进行计数，而不包括如Pet和Cat这样的基类型
 *
 *  可以看到，isInstance()方法使我们不再需要instanceOf表达式。此外，这意外着如果要求添加新类型的Pet，只需要简单地改变LiteralPetCreator.java数组即可。
 *  而不需要改变其他部分(但是在使用instanceof时这是不可能的)
 *  toString()方法已经被重载，使得输出更容易被读取，而该输出与打印Map时所看到的典型输出任然是匹配的
*/
