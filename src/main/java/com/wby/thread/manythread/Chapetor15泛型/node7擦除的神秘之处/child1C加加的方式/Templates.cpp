//: generics/Templates.cpp
#include <iostream>
using namespace std;

template<class T> class Manipulator {
  T obj;
public:
  Manipulator(T x) { obj = x; }
  void manipulate() { obj.f(); }
};

class HasF {
public:
  void f() { cout << "HasF::f()" << endl; }
};

int main() {
  HasF hf;
  Manipulator<HasF> manipulator(hf);
  manipulator.manipulate();
} /* Output:
HasF::f()
///:~
/*
Manipulator类存储了一个类型T的对象，有意思的地方是manipulate()方法，它在obj上调用方法f()。
它怎么知道f()方法是为类型参数T而存在的呢？当你实例化这个模板时，C++编译器将进行检查，因此在Manipulator<HasF>被
实例化的这一刻，他看到HasF拥有一个方法f()。如果情况并非如此，就会得到一个编译器错误，这样类型安全就得到了保障。

用C++编写这种代码很简单，因为当模板被实例化时，模板代码知道其模板参数的类型。
java泛型就不痛了。下面是HasF的java版本：见Manipulation.java
*/
