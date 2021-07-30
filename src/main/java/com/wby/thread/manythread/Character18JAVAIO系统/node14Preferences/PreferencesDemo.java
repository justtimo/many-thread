package com.wby.thread.manythread.Character18JAVAIO系统.node14Preferences;//: io/PreferencesDemo.java

import java.util.prefs.Preferences;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* Preferences API与对象序列化相比，前者与对象持久性更密切，因为它可以自动存储和读取信息。不过，它只能用于小的、受限的数据集合——我们只能存储基本类型和字符串，并且每个字符串的存储长度不能超过8K （不是很小，
 * 但我们也并不想用它来创建任何重要的东西）。顾名思义，Preferences API用于存储和读取用户的偏好（preferences）以及程序配置项的设置。
 *
 * Preferences是—个键-值集合（类似映射），存储在一个节点层次结构中。尽管节点层次结构可用来创建更为复杂的结构，但通常是创建以你的类名命名的单一节点，然后将信息存储于其中。
 *
 * 下面是一个简单的例子∶
*/
public class PreferencesDemo {
  public static void main(String[] args) throws Exception {
    Preferences prefs = Preferences
      .userNodeForPackage(PreferencesDemo.class);
    prefs.put("Location", "Oz");
    prefs.put("Footwear", "Ruby Slippers");
    prefs.putInt("Companions", 4);
    prefs.putBoolean("Are there witches?", true);
    int usageCount = prefs.getInt("UsageCount", 0);
    usageCount++;
    prefs.putInt("UsageCount", usageCount);
    for(String key : prefs.keys())
      print(key + ": "+ prefs.get(key, null));
    // You must always provide a default value:
    print("How many companions does Dorothy have? " +
      prefs.getInt("Companions", 0));
  }
} /* Output: (Sample)
Location: Oz
Footwear: Ruby Slippers
Companions: 4
Are there witches?: true
UsageCount: 53
How many companions does Dorothy have? 4
*///:~
/**
 * 这里用的是userNodeForPackageO，但我们也可以选择用systemNodeForPackageO;虽然可以任意选择，但最好将"user"用于个别用户的偏好，将"system"用于通用的安装配置。
 * 因为 mainO是静态的，因此PreferencesDemo.class可以用来标识节点;但是在非静态方法内部，我们通常使用getClass0。
 * 尽管我们不一定非要把当前的类作为节点标识符，但这仍不失为一种很有用的方法。
 *
 * —日我们创建了节点，就可以用它来加载或者读取数据了。在这个例子中。向节点载入了各种不同类型的数据项，然后获取其keysO。它们是以String【】的形式返回的，如果你习惯于keysO属于集合类库，
 * 那么这个返回结果可能并不是你所期望的。注意getO的第二个参数，如果某个关键字下没有任何条目，那么这个参数就是所产生的默认值。当在一个关键字集合内迭代时，我们总要确信条目是存在的，
 * 因此用nul作为默认值是安全的，但是通常我们会获得一个具名的关键字，就像下面这条语句∶
 *      prefs.getInt ("Companions",0));
 * 在通常情况下，我们希望提供一个合理的默认值。实际上，典型的习惯用法可见下面几行∶
 *      int usageCount = prefs.getInt("UsageCount",0); usageCount++;
 *      prefs.putInt("UsageCount",usageCount);
 * 这样，在我们第一次运行程序时，UsageCount的值是0，但在随后引用中，它将会是非零值。
 *
 * 在我们运行PreferencesDemo.java时，会发现每次运行程序时，UsageCount的值都会增加1.但是数据存储到哪里了呢?
 * 在程序第一次运行之后。并没有出现任何本地文件。Preferences API利用合适的系统资源完成了这个任务，并且这些资源会随操作系统不同而不同。
 * 例如在Windows里，就使用注册表（因为它已经有"键值对"这样的节点对层次结构了）。但是最重要的一点是，它已经神奇般地为我们存储了信息，所以我们不必担心不同的操作系统是怎么运作的。
 *
 * 还有更多的Preferences API，参阅JDK文档可很容易地理解更深的细节。
 */
