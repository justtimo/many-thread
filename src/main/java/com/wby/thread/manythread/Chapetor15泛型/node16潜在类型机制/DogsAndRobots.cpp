//: generics/DogsAndRobots.cpp

class Dog {
public:
  void speak() {}
  void sit() {}
  void reproduce() {}
};

class Robot {
public:
  void speak() {}
  void sit() {}
  void oilChange() {
};

template<class T> void perform(T anything) {
  anything.speak();
  anything.sit();
}

int main() {
  Dog d;
  Robot r;
  perform(d);
  perform(r);
} ///:~
/*
在Python和C++中，DOg和Robot没有任何共同的东西，只是碰巧有两个方法具有相同的签名。从类型的观点看，他们是完全不同的类型。但是，perform
不关心其参数的具体类型，并且潜在类型机制允许他接受这两种类型的对象。

C++确保了他实际上可以发送的那些消息，如果试图传递错误类型，编译器会给与错误消息。
尽管他们是在不同时期实现这一点的，C++在编译期，而Python在运行时，但是这两种语言都可以确保类型不会被误用，因此被认为是 强类型 的。潜在
类型机制没有损坏强类型机制。

因为泛型是在这场竞赛的后期才添加到java中的，因此没有任何机会可以去实现任何类型的潜在类型机制，因此java没有对这种特性的支持。
所以，初看起来，java的泛型机制比支持潜在类型机制的语言更“缺乏泛化性”。(java使用擦除的泛型实现有时被称为“第二类泛型类型”)
例如，如果试图用java实现上面的例子，那么就会被强制要求使用一个类或接口，并在边界表达式中指定他：
见Performs。java
*/
