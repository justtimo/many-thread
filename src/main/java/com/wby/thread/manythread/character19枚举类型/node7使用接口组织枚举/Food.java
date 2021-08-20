//: enumerated/menu/Food.java
// Subcategorization of enums within interfaces.
package com.wby.thread.manythread.character19枚举类型.node7使用接口组织枚举;

import com.wby.thread.manythread.character19枚举类型.node6随机选取.Enums;

/**
 * 无法从enum继承子类有时很令人沮丧。这种需求有时源自我们希望扩展原enum中的元素，
 *
 * 有时是因为我们希望使用子类将一个enum中的元素进行分组。
 *
 * 在一个接口的内部，创建实现该接口的枚举，以此将元素进行分组，可以达到将枚举元素分类组织的目的。举例来说，假设你想用enum来表示不同类别的食物，同时还希望每个enum元素仍然保持Food类型。那可以这样实现∶
 */
public interface Food {
  enum Appetizer implements Food {
    SALAD, SOUP, SPRING_ROLLS;
  }
  enum MainCourse implements Food {
    LASAGNE, BURRITO, PAD_THAI,
    LENTILS, HUMMOUS, VINDALOO;
  }
  enum Dessert implements Food {
    TIRAMISU, GELATO, BLACK_FOREST_CAKE,
    FRUIT, CREME_CARAMEL;
  }
  enum Coffee implements Food {
    BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
    LATTE, CAPPUCCINO, TEA, HERB_TEA;
  }
} ///:~
/**
 * 对于enum而言，实现接口是使其子类化的唯一办法，所以嵌入在Food中的每个enum都实现了Food接口。现在，在下面的程序中，我们可以说"所有东西都是某种类型的Food";
 */
class TypeOfFood {
  public static void main(String[] args) {
    Food food = Food.Appetizer.SALAD;
    food = Food.MainCourse.LASAGNE;
    food = Food.Dessert.GELATO;
    food = Food.Coffee.CAPPUCCINO;
  }
} ///:~
/**
 * 如果enum类型实现了Food接口，那么我们就可以将其实例向上转型为Food，所以上例中的所有东西都是Food。
 * 然而，当你需要与一大堆类型打交道时，接口就不如enum好用了。例如，如果你想创建一个"枚举的枚举"，那么可以创建一个新的enum，然后用其实例包装Food中的每一个enum类，
 */
enum Course {
  APPETIZER(Food.Appetizer.class),
  MAINCOURSE(Food.MainCourse.class),
  DESSERT(Food.Dessert.class),
  COFFEE(Food.Coffee.class);
  private Food[] values;
  private Course(Class<? extends Food> kind) {
    values = kind.getEnumConstants();
  }
  public Food randomSelection() {
    return Enums.random(values);
  }
} ///:~
/**
 * 在上面的程序中，每一个Course的实例都将其对应的Class对象作为构造器的参数。通过 getEnumConstantsO）方法，可以从该Class对象中取得某个Food子类的所有enum实例。
 * 这些实例在randomSelectionO中被用到。因此，通过从每一个Course实例中随机地选择一个Food，我们便能够生成一份菜单∶
 */
class Meal {
  public static void main(String[] args) {
    for(int i = 0; i < 5; i++) {
      for(Course course : Course.values()) {
        Food food = course.randomSelection();
        System.out.println(food);
      }
      System.out.println("---");
    }
  }
} /* Output:
SPRING_ROLLS
VINDALOO
FRUIT
DECAF_COFFEE
---
SOUP
VINDALOO
FRUIT
TEA
---
SALAD
BURRITO
FRUIT
TEA
---
SALAD
BURRITO
CREME_CARAMEL
LATTE
---
SOUP
BURRITO
TIRAMISU
ESPRESSO
---
*///:~

/**
 * 在这个例子中，我们通过遍历每一个Course实例来获得"枚举的枚举"的值。稍后，在 VendingMachine.java中，我们会看到另一种组织枚举实例的方式，但其也有一些其他的限制。
 * 此外，还有一种更简洁的管理枚举的办法，就是将一个enum嵌套在另一个enum内。就像这样∶
 */
enum SecurityCategory {
  STOCK(Security.Stock.class), BOND(Security.Bond.class);
  Security[] values;
  SecurityCategory(Class<? extends Security> kind) {
    values = kind.getEnumConstants();
  }
  interface Security {
    enum Stock implements Security { SHORT, LONG, MARGIN }
    enum Bond implements Security { MUNICIPAL, JUNK }
  }
  public Security randomSelection() {
    return Enums.random(values);
  }
  public static void main(String[] args) {
    for(int i = 0; i < 10; i++) {
      SecurityCategory category =
              Enums.random(SecurityCategory.class);
      System.out.println(category + ": " +
              category.randomSelection());
    }
  }
} /* Output:
BOND: MUNICIPAL
BOND: MUNICIPAL
STOCK: MARGIN
STOCK: MARGIN
BOND: JUNK
STOCK: SHORT
STOCK: LONG
STOCK: LONG
BOND: MUNICIPAL
BOND: JUNK
*///:~
/**
 * Security接口的作用是将其所包含的enum组合成一个公共类型，这一点是有必要的。然后， SecurityCategory才能将Security中的enum作为其构造器的参数使用，以起到组织的效果。
 * 如果我们将这种方式应用于Food的例子，结果应该这样∶
 */
enum Meal2 {
  APPETIZER(Food.Appetizer.class),
  MAINCOURSE(Food.MainCourse.class),
  DESSERT(Food.Dessert.class),
  COFFEE(Food.Coffee.class);
  private Food[] values;
  private Meal2(Class<? extends Food> kind) {
    values = kind.getEnumConstants();
  }
  public interface Food {
    enum Appetizer implements Food {
      SALAD, SOUP, SPRING_ROLLS;
    }
    enum MainCourse implements Food {
      LASAGNE, BURRITO, PAD_THAI,
      LENTILS, HUMMOUS, VINDALOO;
    }
    enum Dessert implements Food {
      TIRAMISU, GELATO, BLACK_FOREST_CAKE,
      FRUIT, CREME_CARAMEL;
    }
    enum Coffee implements Food {
      BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
      LATTE, CAPPUCCINO, TEA, HERB_TEA;
    }
  }
  public Food randomSelection() {
    return Enums.random(values);
  }
  public static void main(String[] args) {
    for(int i = 0; i < 5; i++) {
      for(Meal2 meal : Meal2.values()) {
        Food food = meal.randomSelection();
        System.out.println(food);
      }
      System.out.println("---");
    }
  }
} /* Same output as Meal.java *///:~
/**
 * 其实，这仅仅是重新组织了一下代码，不过多数情况下，这种方式使你的代码具有更清晰的结构
 */









