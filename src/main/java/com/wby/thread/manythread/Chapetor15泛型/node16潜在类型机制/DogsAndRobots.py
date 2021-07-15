'''
    本章开头介绍过这样的思想，即：编写尽可能广泛应用的代码。
    为了实现这一点，我们需要各种途径来放松对我们的代码将要作用的类型所做的限制，同时不丢失静态类型检查的好处。
    然后，我们就可以编写出无需修改就可以应用于更多情况的代码，即更加“泛化”的代码

    java泛型看起来向这一方向迈进了一步。当你在编写或使用只是持有对象的泛型时，这些代码将可以工作于任何类型(除了基本类型)。
    或者说，“持有器”泛型能够声明：“我不关心你是什么类型”。如果代码不关心他将要作用的类型，那么这种代码就可以真正的应用于任何地方，并
        因此而相当“泛化”。

    正如你所见，挡在泛型类型上执行操作(即调用Object方法之前的操作)时，就会产生问题，因为擦除要求指定可能会用到的泛型类型的边界，以安全
        的调用代码中的泛型对象上的具体方法。这是对“泛化”概念的明显限制，因为必须限制你的泛型类型，，使他们继承自特定的类，
'''
class Dog:
	def speak(self):
		print('Arf!')
	def sit(self):
		print('Sitting')
	def reproduce(self):
		pass

class Robot:
	def speak(self):
		print('Click!')
	def sit(self):
		print('Clank!')
	def oilChange(self):
		pass

def perform(anything):
	anything.speak()
	anything.speak()

a=Dog()
b=Robot()
perform(a)
perform(b)
