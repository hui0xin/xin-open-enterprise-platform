package com.xin.commons.support.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java正则表达式的说明：<br>
 * * 0次或多次<br>
 * + 1次或者多次<br>
 * ？ 0次或1次<br>
 * n 恰好n次<br>
 * {n,m} 从n次到m次<br>
 * <br>
 * . 任意字符（也许能与行终止符匹配，也许不能） <br>
 * \d 数字: [0-9] <br>
 * \D 非数字: [^0-9] <br>
 * \s 空格符: [ \t\n\x0B\f\r] <br>
 * \S 非空格符: [^\s] <br>
 * \w 单词字符: [a-zA-Z_0-9] <br>
 * \W 非单词字符: [^\w] <br>
 * 
 * <br>
 * 7.边界匹配器 <br>
 * ^行的开头，请在正则表达式的开始处使用^。例如：^(abc)表示以abc开头的字符串。注意编译的时候要设置参数MULTILINE，如 Pattern p
 * = Pattern.compile(regex,Pattern.MULTILINE); <br>
 * $行的结尾，请在正则表达式的结束处使用。例如：(^bca).*(abc$)表示以bca开头以abc结尾的行。 <br>
 * \b 单词边界。例如\b(abc)表示单词的开始或结束包含有abc,（abcjj、jjabc 都可以匹配） <br>
 * \B 非单词边界。例如\B(abc)表示单词的中间包含有abc,(jjabcjj匹配而jjabc、abcjj不匹配) <br>
 * \A 输入的开头 <br>
 * \G 上一个匹配的结尾(个人感觉这个参数没什么用)。例如\\Gdog表示在上一个匹配结尾处查找dog如果没有的话则从开头查找,
 * 注意如果开头不是dog则不能匹配。 <br>
 * \Z 输入的结尾，仅用于最后的结束符（如果有的话） <br>
 * 行结束符 是一个或两个字符的序列，标记输入字符序列的行结尾。 <br>
 * 以下代码被识别为行结束符： <br>
 * ‐新行（换行）符 ('\n')、 <br>
 * ‐后面紧跟新行符的回车符 ("\r\n")、 <br>
 * ‐单独的回车符 ('\r')、 <br>
 * ‐下一行字符 ('\u0085')、 <br>
 * ‐行分隔符 ('\u2028') 或 <br>
 * ‐段落分隔符 ('\u2029)。 <br>
 * \z 输入的结尾 <br>
 * 当编译模式时，可以设置一个或多个标志，例如 <br>
 * Pattern pattern = Pattern.compile(patternString,Pattern.CASE_INSENSITIVE +
 * Pattern.UNICODE_CASE); <br>
 * 下面六个标志都是支持的： <br>
 * ‐CASE_INSENSITIVE：匹配字符时与大小写无关，该标志默认只考虑US ASCII字符。 <br>
 * ‐UNICODE_CASE：当与CASE_INSENSITIVE结合时，使用Unicode字母匹配 <br>
 * ‐MULTILINE：^和$匹配一行的开始和结尾，而不是整个输入 <br>
 * ‐UNIX_LINES： 当在多行模式下匹配^和$时，只将'\n'看作行终止符 <br>
 * ‐DOTALL: 当使用此标志时，.符号匹配包括行终止符在内的所有字符 <br>
 * ‐CANON_EQ: 考虑Unicode字符的规范等价
 * 
 * 这个例子如何:
 * 
 * 是这样的： bangdan.jsp?type=1 bangdan.jsp?type=2 bangdan.jsp?type=3
 * bangdan.jsp?type=11 等等替换成 bangdan_1.html bangdan_2.html bangdan_3.html
 * bangdan_11.html
 * 
 * 代码:
 * 
 * String str=
 * "bangdan.jsp?type=1bangdan.jsp?type=2bangdan.jsp?type=3bangdan.jsp?type=11 ";
 * System.out.println( "之前: "+str); java.util.regex.Matcher
 * m=java.util.regex.Pattern.compile( "\\.jsp\\?type=([\\d]+) ").matcher(str);
 * StringBuffer sb=new StringBuffer(); int index=0; while(m.find()) {
 * sb.append(str.substring(index,m.start(1))+ m.group(1)+ ".html ");
 * index=m.end(1); } str=sb.toString().replaceAll( "\\.jsp\\?type= ", "_ ");
 * System.out.println( "之后: "+str);
 * 
 * 
 */
public class RegexUtil {

	private RegexUtil() {
	}

	public static boolean regexMatcher(String pattern, String s) {
		return regexMatcher(pattern, -1, s);
	}

	public static boolean regexMatcher(String pattern, int flags, String s) {
		if (pattern == null || s == null) {
			if (pattern == null && s == null) {
				return true;
			} else {
				return false;
			}
		}
		Pattern patternTmp = flags != -1 ? Pattern.compile(pattern, flags) : Pattern.compile(pattern);
		// Pattern.UNICODE_CASE
		Matcher matcher = patternTmp.matcher(s);
		return matcher.matches();
	}

	public static boolean isEmail(String s) {
		if (StringUtils.isBlank(s) || s.length() < 5 || s.indexOf('@') < 1 || s.indexOf('.') < 1 || s.endsWith("@") || s.endsWith(".")) {
			return false;
		}
		return regexMatcher("^(\\w|\\.|-|\\+)+@(\\w|-)+(\\.(\\w|-)+)+$", -1, s);
	}
	
	/**
	 * @Title: getRegexStr 
	 * @Description: 正则表达式抽取信息
	 * @param @param text
	 * @param @param regex
	 * @param @return  参数说明 
	 * Matcher    返回类型
	 */
	public static Matcher initMatcher(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		return matcher;
	}
	
	/**
	 * 正则表达式提取信息
	 * @param text
	 * @param regex
	 * @return
	 */
	public static String getRegexStr(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		matcher.find();
		String regexStr = matcher.group(0);
		return regexStr;
	}
	
	/**
	 * 正则表达式提取信息
	 * @param text
	 * @param regex
	 * @param index
	 * @return
	 */
	public static String getRegexStr (String text, String regex,Integer index)throws Exception {
		String regexStr = null;
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(text);
			matcher.find();
			regexStr = matcher.group(index);
		} catch (Exception e) {
			regexStr = null;
		}
		return regexStr;
	}
}
