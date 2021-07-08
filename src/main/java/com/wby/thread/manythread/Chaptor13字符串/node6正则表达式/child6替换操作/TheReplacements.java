package com.wby.thread.manythread.Chaptor13字符串.node6正则表达式.child6替换操作;//: strings/TheReplacements.java

import com.wby.thread.manythread.net.mindview.util.TextFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/*! Here's a block of text to use as input to
    the regular expression matcher. Note that we'll
    first extract the block of text by looking for
    the special delimiters, then process the
    extracted block. !*/

/**
 * @Description:  正则表达式特别方便替换文本，它提供了很多方法：
 * String replaceFirst(String regex, String replacement)以参数字符串replacement替换掉第一个匹配成功的部分。
 * String replaceAll(String regex, String replacement)以参数字符串replacement替换掉所有匹配成功的部分。
 *
 * Matcher appendReplacement(StringBuffer sb, String replacement)执行渐进式的替换。
 * 这是一个很重要的方法，它允许你调用其他方法来生成或处理replacement(replaceFirst和replaceAll只能使用一个固定的字符串)，是你能以编程的方式将目标分割成组，从而完成更强大的替换功能。
 *
 *StringBuffer appendTail(StringBuffer sb)，在执行了一次或多次appendReplacement方法之后，调用此方法可以将输入字符串余下的部分复制到sb中
 */
public class TheReplacements {
  public static void main(String[] args) throws Exception {
    String s = TextFile.read("TheReplacements.java");
    // Match the specially commented block of text above:
    Matcher mInput =
      Pattern.compile("/\\*!(.*)!\\*/", Pattern.DOTALL)
        .matcher(s);
    if(mInput.find())
      s = mInput.group(1); // Captured by parentheses
    // Replace two or more spaces with a single space:
    s = s.replaceAll(" {2,}", " ");
    // Replace one or more spaces at the beginning of each
    // line with no spaces. Must enable MULTILINE mode:
    s = s.replaceAll("(?m)^ +", "");
    print(s);
    s = s.replaceFirst("[aeiou]", "(VOWEL1)");
    StringBuffer sbuf = new StringBuffer();
    Pattern p = Pattern.compile("[aeiou]");
    Matcher m = p.matcher(s);
    // Process the find information as you
    // perform the replacements:
    while(m.find())
      m.appendReplacement(sbuf, m.group().toUpperCase());
    // Put in the remainder of the text:
    m.appendTail(sbuf);
    print(sbuf);
  }
} /* Output:
Here's a block of text to use as input to
the regular expression matcher. Note that we'll
first extract the block of text by looking for
the special delimiters, then process the
extracted block.
H(VOWEL1)rE's A blOck Of tExt tO UsE As InpUt tO
thE rEgUlAr ExprEssIOn mAtchEr. NOtE thAt wE'll
fIrst ExtrAct thE blOck Of tExt by lOOkIng fOr
thE spEcIAl dElImItErs, thEn prOcEss thE
ExtrActEd blOck.
*///:~
/**
* @Description: mInput用来匹配在/*!和！*/
/**
* @Description: 之间的所有内容。
 * 然后将存在两个或两个以上的空格缩减为一个空格（replaceAll），并且删除每一行开头的所有空格（为了使每一行都达到效果，这里特意打开了多行状态）
 * 这两个替换操作所使用的replaceAll是String自带的方法，这里使用此方法更为方便。
 * 注意，因为这两个替换操作都只使用了一次replaceAll，所以与其编译成Pattern，不如直接使用String的replaceAll，这样开销更小
 *
 * replaceAll和replaceFirst用来替换的只能是普通字符串。如果想对字符串进行特殊的处理，应该使用appendReplacement。
 * 这个方法允许你再执行替换过程中，操作用来替换的字符串
 *
 * 这个例子中，先构造了sbuf来保存最终结果，然后用group选择一个组，并对其进行处理，将正则表达式找到的元音字母转换为大写。
 * 然后在调用appendTail；但是如果你想模拟replaceFirst（或替换n次的行为），那就只需要执行一次替换，然后使用appendTail将剩余未处理的部分存入sbuf即可
 *
 * 同时appendReplacement允许你通过$g直接找到匹配的某个组，这里的g就是组号
*/
