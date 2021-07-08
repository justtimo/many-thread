//: typeinfo/pets/Pets.java
// Facade to produce a default PetCreator.
package com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.child4类型转换前先做检查.pets;

import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.child4类型转换前先做检查.PetCreator;

import java.util.ArrayList;

public class Pets {
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
