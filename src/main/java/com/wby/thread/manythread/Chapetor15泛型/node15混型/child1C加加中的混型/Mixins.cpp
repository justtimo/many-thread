//: generics/Mixins.cpp
#include <string>
#include <ctime>
#include <iostream>
using namespace std;
/*
 C++中，使用多重继承最大的理由，就是为了使用混型。
 但是对于混型来说，更有趣、更优雅的方式是使用参数化类型，因为混型就是继承自其类型参数的类。
 在C++中，可以很容易的创建混型，因为C++能够记住其模板参数的类型。

 下面例子中，他有两个混型类型，，一个是的你可以在每个对象中混入拥有一个时间戳这样的属性，另一个可以混入一个序列号
 */
template<class T> class TimeStamped : public T {
  long timeStamp;
public:
  TimeStamped() { timeStamp = time(0); }
  long getStamp() { return timeStamp; }
};

template<class T> class SerialNumbered : public T {
  long serialNumber;
  static long counter;
public:
  SerialNumbered() { serialNumber = counter++; }
  long getSerialNumber() { return serialNumber; }
};

// Define and initialize the static storage:
template<class T> long SerialNumbered<T>::counter = 1;

class Basic {
  string value;
public:
  void set(string val) { value = val; }
  string get() { return value; }
};

int main() {
  TimeStamped<SerialNumbered<Basic> > mixin1, mixin2;
  mixin1.set("test string 1");
  mixin2.set("test string 2");
  cout << mixin1.get() << " " << mixin1.getStamp() <<
    " " << mixin1.getSerialNumber() << endl;
  cout << mixin2.get() << " " << mixin2.getStamp() <<
    " " << mixin2.getSerialNumber() << endl;
} /* Output: (Sample)
test string 1 1129840250 1
test string 2 1129840250 2
*///:~
/*
main中，mixin1和mixin2所产生的类型拥有所混入类型的所有方法。可以将混型看做是一种功能，他可以将现有类映射到新的子类上。注意，使用这种计数来
创建一个混型是多么的轻而易举。基本上，只需要声明“这就是我想要的”，紧跟着他就发生了：
    TimeStamped<SerialNumbered<Basic> > mixin1, mixin2;
遗憾的是，java泛型不允许这样。擦除会忘记基类类型，因此泛型类不能直接继承自一个泛型参数
*/
