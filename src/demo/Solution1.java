package demo;

/**
 * Created by 59685 on 2017/8/31.请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的
 * 字符可以出现任意次（包含0次）。 在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是
 * 与"aa.a"和"ab*a"均不匹配
 */
public class Solution1 {
    public boolean match(char[] str, char[] pattern) {
        return match(str, 0, pattern, 0);
    }

    public boolean match(char[] str, int i, char[] pattern, int j) {
        if (i == str.length && j == pattern.length) {
            return true;
        }
        if (i == str.length && j < pattern.length) {
            if (j < pattern.length - 1 && pattern[j + 1] == '*') {
                return match(str, i, pattern, j + 2);
            }
            return false;
        }
        if (i < str.length && j < pattern.length) {
            if (j < pattern.length - 1 && pattern[j + 1] == '*') {
                int temp = i;
                if (match(str, temp, pattern, j + 2)) {
                    return true;
                }
                while (temp<str.length && (pattern[j] == str[temp] || pattern[j] == '.')) {
                    if (match(str, temp + 1, pattern, j + 2)) {
                        return true;
                    }
                    temp++;
                }
                return false;
            } else if (pattern[j] == '.') {
                return match(str, i + 1, pattern, j + 1);
            } else {
                if (pattern[j] == str[i]) {
                    return match(str, i + 1, pattern, j + 1);
                }
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        System.out.println(new Solution1().match("aaa".toCharArray(), "a.a".toCharArray()));
//        System.out.println(new Solution1().match("aaa".toCharArray(), ".*".toCharArray()));
//        System.out.println(new Solution1().match("".toCharArray(), ".*".toCharArray()));
        System.out.println(new Solution1().match("aaa".toCharArray(), "ab*ac*a".toCharArray()));
    }
}
