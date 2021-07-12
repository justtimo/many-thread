//: generics/InstantiateGenericType.cpp
// C++, not Java!

template<class T> class Foo {
  T x; // Create a field of type T
  T* y; // Pointer to T
public:
  // Initialize the pointer:
  Foo() { y = new T(); }
};

class Bar {};

int main() {
  Foo<Bar> fb;
  Foo<int> fi; // ... and it works with primitives
} ///:~
/*
在Erased.java中创建一个new T()的尝试无法实现，部分原因是擦除，另一部分就是编译器不能验证T具有默认(无参)构造器。
但在C++中，这种操作很自然、只管，并且很安全(他是在编译期受到检查的)

java中的解决方案是传递一个工厂对象，并使用他来创建新的实例。最方便的工厂对象就是Class对象，因此如果使用类型标签，
那么你就可以使用newInstance方法来创建这个类型的新对象
见InstantiateGenericType.java
*/
