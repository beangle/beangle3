/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.lang;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;

/**
 * <p>
 * Operations on {@link java.lang.String} that are {@code null} safe.
 * </p>
 * 
 * @author chaostone 2005-11-15
 * @version $Id: $
 * @since 3.0
 */
public class Strings {

  /** Constant <code>DELIMITER=","</code> */
  public static final String DELIMITER = ",";

  private static final String Empty = "";

  private static final int Index_not_found = -1;

  Strings() {
  }

  /**
   * <p>
   * Capitalizes a String changing the first letter to title case as per
   * {@link Character#toTitleCase(char)}. No other letters are changed.
   * </p>
   * <p>
   * For a word based algorithm, see returns {@code null}.
   * </p>
   * 
   * <pre>
   * capitalize(null)  = null
   * capitalize("")    = ""
   * capitalize("cat") = "Cat"
   * capitalize("cAt") = "CAt"
   * </pre>
   * 
   * @param str the String to capitalize, may be null
   * @return the capitalized String, {@code null} if null String input
   * @see #uncapitalize(String)
   * @since 2.0
   */
  public static String capitalize(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) { return str; }
    return new StringBuilder(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1))
        .toString();
  }

  /**
   * <p>
   * concat.
   * </p>
   * 
   * @param seq
   *          a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String concat(final String... seq) {
    return join(seq, null);
  }

  /**
   * <p>
   * Checks if CharSequence contains a search CharSequence, handling {@code null}. This method uses
   * {@link String#indexOf(String)} if possible.
   * </p>
   * <p>
   * A {@code null} CharSequence will return {@code false}.
   * </p>
   * 
   * <pre>
   * contains(null, *)     = false
   * contains(*, null)     = false
   * contains("", "")      = true
   * contains("abc", "")   = true
   * contains("abc", "a")  = true
   * contains("abc", "z")  = false
   * </pre>
   * 
   * @param seq the CharSequence to check, may be null
   * @param searchSeq the CharSequence to find, may be null
   * @return true if the CharSequence contains the search CharSequence,
   *         false if not or {@code null} string input
   */
  public static boolean contains(CharSequence seq, CharSequence searchSeq) {
    if (seq == null || searchSeq == null) { return false; }
    return indexOf(seq, searchSeq, 0) >= 0;
  }

  /**
   * <p>
   * Checks if CharSequence contains a search character, handling {@code null}. This method uses
   * {@link String#indexOf(int)} if possible.
   * </p>
   * <p>
   * A {@code null} or empty ("") CharSequence will return {@code false}.
   * </p>
   * 
   * <pre>
   * contains(null, *)    = false
   * contains("", *)      = false
   * contains("abc", 'a') = true
   * contains("abc", 'z') = false
   * </pre>
   * 
   * @param seq
   *          the CharSequence to check, may be null
   * @param searchChar
   *          the character to find
   * @return true if the CharSequence contains the search character,
   *         false if not or {@code null} string input
   * @since 2.0
   * @since 3.0 Changed signature from contains(String, int) to contains(CharSequence, int)
   */
  public static boolean contains(CharSequence seq, int searchChar) {
    if (isEmpty(seq)) { return false; }
    return indexOf(seq, searchChar, 0) >= 0;
  }

  /**
   * count char in host string
   * 
   * @param host
   *          a {@link java.lang.String} object.
   * @param charactor
   *          a char.
   * @return a int.
   */
  public static int count(final String host, final char charactor) {
    int count = 0;
    for (int i = 0; i < host.length(); i++) {
      if (host.charAt(i) == charactor) {
        count++;
      }
    }
    return count;
  }

  /**
   * count inner string in host string
   * 
   * @param host
   *          a {@link java.lang.String} object.
   * @param searchStr
   *          a {@link java.lang.String} object.
   * @return a int.
   */
  public static int count(final String host, final String searchStr) {
    int count = 0;
    for (int startIndex = 0; startIndex < host.length(); startIndex++) {
      int findLoc = host.indexOf(searchStr, startIndex);
      if (findLoc == -1) {
        break;
      } else {
        count++;
        startIndex = findLoc + searchStr.length() - 1;
      }
    }
    return count;
  }

  /**
   * Returns index of searchChar in cs with begin index {@code start}
   * 
   * @param searchChar
   * @param start
   */
  private static int indexOf(CharSequence cs, CharSequence searchChar, int start) {
    return cs.toString().indexOf(searchChar.toString(), start);
  }

  /**
   * <p>
   * Finds the first index in the {@code CharSequence} that matches the specified character.
   * </p>
   * 
   * @param cs the {@code CharSequence} to be processed, not null
   * @param searchChar the char to be searched for
   * @param start the start index, negative starts at the string start
   * @return the index where the search char was found, -1 if not found
   */
  private static int indexOf(CharSequence cs, int searchChar, int start) {
    if (cs instanceof String) {
      return ((String) cs).indexOf(searchChar, start);
    } else {
      int sz = cs.length();
      if (start < 0) {
        start = 0;
      }
      for (int i = start; i < sz; i++) {
        if (cs.charAt(i) == searchChar) { return i; }
      }
      return -1;
    }
  }

  /**
   * <p>
   * insert.
   * </p>
   * 
   * @param str a {@link java.lang.String} object.
   * @param c a {@link java.lang.String} object.
   * @param pos a int.
   * @return a {@link java.lang.String} object.
   */
  public static String insert(final String str, final String c, final int pos) {
    if (str.length() < pos) { return str; }
    return str.substring(0, pos - 1) + c + str.substring(pos);
  }

  /**
   * replace [bigen,end] [1...end] with givenStr
   * 可以使用StringBuilder的replace方法替换该方法
   * 
   * @param str a {@link java.lang.String} object.
   * @param begin a int.
   * @param end a int.
   * @param given a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String insert(final String str, final String given, final int begin, final int end) {
    if (begin < 1 || end > str.length() || end < begin) { return str; }
    return str.substring(0, begin - 1) + given + str.substring(end);
  }

  /**
   * <p>
   * intersectSeq.
   * </p>
   * 
   * @param first
   *          a {@link java.lang.String} object.
   * @param second
   *          a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String intersectSeq(final String first, final String second) {
    return intersectSeq(first, second, DELIMITER);
  }

  /**
   * 返回一个新的逗号相隔字符串，实现其中的单词a-b的功能
   * 
   * @param first a {@link java.lang.String} object.
   * @param second a {@link java.lang.String} object.
   * @param delimiter a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String intersectSeq(final String first, final String second, final String delimiter) {
    if (isEmpty(first) || isEmpty(second)) { return ""; }
    List<String> firstSeq = Arrays.asList(split(first, ','));
    List<String> secondSeq = Arrays.asList(split(second, ','));
    Collection<String> rs = CollectUtils.intersection(firstSeq, secondSeq);
    StringBuilder buf = new StringBuilder();
    for (final String ele : rs) {
      buf.append(delimiter).append(ele);
    }
    if (buf.length() > 0) {
      buf.append(delimiter);
    }
    return buf.toString();
  }

  /**
   * <p>
   * Checks if a CharSequence is whitespace, empty ("") or null.
   * </p>
   * 
   * <pre>
   * isBlank(null)      = true
   * isBlank("")        = true
   * isBlank(" ")       = true
   * isBlank("bob")     = false
   * isBlank("  bob  ") = false
   * </pre>
   * 
   * @param cs
   *          the CharSequence to check, may be null
   * @return {@code true} if the CharSequence is null, empty or whitespace
   * @since 3.0
   */
  public static boolean isBlank(CharSequence cs) {
    int strLen;
    if (cs == null || (strLen = cs.length()) == 0) { return true; }
    for (int i = 0; i < strLen; i++) {
      if (Character.isWhitespace(cs.charAt(i)) == false) { return false; }
    }
    return true;
  }

  /**
   * Returns true is cs is null or cs.length equals 0.
   */
  public static boolean isEmpty(CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

  /**
   * <p>
   * isEqualSeq.
   * </p>
   * 
   * @param first not null
   * @param second not null
   * @return a boolean.
   */
  public static boolean isEqualSeq(final String first, final String second) {
    return isEqualSeq(first, second, DELIMITER);
  }

  /**
   * 判断两个","逗号相隔的字符串中的单词是否完全等同.
   * 
   * @param first
   *          a {@link java.lang.String} object.
   * @param second
   *          a {@link java.lang.String} object.
   * @param delimiter
   *          a {@link java.lang.String} object.
   * @return a boolean.
   */
  public static boolean isEqualSeq(final String first, final String second, final String delimiter) {
    if (isNotEmpty(first) && isNotEmpty(second)) {
      String[] firstWords = split(first, delimiter);
      Set<String> firstSet = CollectUtils.newHashSet();
      for (int i = 0; i < firstWords.length; i++) {
        firstSet.add(firstWords[i]);
      }
      String[] secondWords = split(second, delimiter);
      Set<String> secondSet = CollectUtils.newHashSet();
      for (int i = 0; i < secondWords.length; i++) {
        secondSet.add(secondWords[i]);
      }
      return firstSet.equals(secondSet);
    } else {
      return isEmpty(first) & isEmpty(second);
    }
  }

  /**
   * <p>
   * Checks if a CharSequence is not empty (""), not null and not whitespace only.
   * </p>
   * 
   * <pre>
   * isNotBlank(null)      = false
   * isNotBlank("")        = false
   * isNotBlank(" ")       = false
   * isNotBlank("bob")     = true
   * isNotBlank("  bob  ") = true
   * </pre>
   * 
   * @param cs
   *          the CharSequence to check, may be null
   * @return {@code true} if the CharSequence is
   *         not empty and not null and not whitespace
   * @since 3.0
   */
  public static boolean isNotBlank(CharSequence cs) {
    return !isBlank(cs);
  }

  /**
   * Return true if cs not null and cs has length.
   */
  public static boolean isNotEmpty(CharSequence cs) {
    return !isEmpty(cs);
  }

  /**
   * <p>
   * join.
   * </p>
   * 
   * @param seq
   *          a {@link java.util.Collection} object.
   * @param delimiter
   *          a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String join(final Collection<String> seq, final String delimiter) {
    if (null == seq || seq.size() < 1) {
      return "";
    } else {
      StringBuilder aim = new StringBuilder();
      for (String one : seq) {
        if (null != delimiter && aim.length() > 0) {
          aim.append(delimiter);
        }
        aim.append(one);
      }
      return aim.toString();
    }
  }

  /**
   * <p>
   * join.
   * </p>
   * 
   * @param seq
   *          a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String join(final String... seq) {
    return join(seq, DELIMITER);
  }

  /**
   * 将数组中的字符串，用delimiter串接起来.<br>
   * 首尾不加delimiter
   * 
   * @param seq an array of {@link java.lang.String} objects.
   * @param delimiter a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String join(final String[] seq, final String delimiter) {
    if (null == seq || seq.length < 1) {
      return "";
    } else {
      StringBuilder aim = new StringBuilder();
      for (int i = 0; i < seq.length; i++) {
        if (null != delimiter && aim.length() > 0) {
          aim.append(delimiter);
        }
        aim.append(seq[i]);
      }
      return aim.toString();
    }
  }

  /**
   * 保持逗号分隔的各个单词都是唯一的。并且按照原来的顺序存放。
   * 
   * @param keys
   *          a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String keepSeqUnique(final String keys) {
    String[] keysArray = split(keys, ",");
    List<String> keyList = CollectUtils.newArrayList();
    for (int i = 0; i < keysArray.length; i++) {
      if (!keyList.contains(keysArray[i])) {
        keyList.add(keysArray[i]);
      }
    }
    StringBuilder keyBuf = new StringBuilder();
    for (Iterator<String> iter = keyList.iterator(); iter.hasNext();) {
      keyBuf.append(iter.next());
      if (iter.hasNext()) {
        keyBuf.append(",");
      }
    }
    return keyBuf.toString();
  }

  /**
   * <p>
   * Left pad a String with a specified character.
   * </p>
   * <p>
   * Pad to a size of {@code size}.
   * </p>
   * 
   * <pre>
   * leftPad(null, *, *)     = null
   * leftPad("", 3, 'z')     = "zzz"
   * leftPad("bat", 3, 'z')  = "bat"
   * leftPad("bat", 5, 'z')  = "zzbat"
   * leftPad("bat", 1, 'z')  = "bat"
   * leftPad("bat", -1, 'z') = "bat"
   * </pre>
   * 
   * @param str the String to pad out, may be null
   * @param size the size to pad to
   * @param padChar the character to pad with
   * @return left padded String or original String if no padding is necessary, {@code null} if null
   *         String input
   * @since 3.0
   */
  public static String leftPad(String str, int size, char padChar) {
    if (str == null) { return null; }
    int pads = size - str.length();
    if (pads <= 0) { return str; // returns original String when possible
    }
    return repeat(padChar, pads).concat(str);
  }

  /**
   * <p>
   * Right pad a String with a specified character.
   * </p>
   * <p>
   * The String is padded to the size of {@code size}.
   * </p>
   * 
   * <pre>
   * rightPad(null, *, *)     = null
   * rightPad("", 3, 'z')     = "zzz"
   * rightPad("bat", 3, 'z')  = "bat"
   * rightPad("bat", 5, 'z')  = "batzz"
   * rightPad("bat", 1, 'z')  = "bat"
   * rightPad("bat", -1, 'z') = "bat"
   * </pre>
   * 
   * @param str the String to pad out, may be null
   * @param size the size to pad to
   * @param padChar the character to pad with
   * @return right padded String or original String if no padding is necessary, {@code null} if null
   *         String input
   * @since 3.0
   */
  public static String rightPad(String str, int size, char padChar) {
    if (str == null) { return null; }
    int pads = size - str.length();
    if (pads <= 0) { return str; // returns original String when possible
    }
    return str.concat(repeat(padChar, pads));
  }

  /**
   * <p>
   * mergeSeq.
   * </p>
   * 
   * @param first
   *          a {@link java.lang.String} object.
   * @param second
   *          a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String mergeSeq(final String first, final String second) {
    return mergeSeq(first, second, DELIMITER);
  }

  /**
   * 将两个用delimiter串起来的字符串，合并成新的串，重复的"单词"只出现一次.
   * 如果第一个字符串以delimiter开头，第二个字符串以delimiter结尾，<br>
   * 合并后的字符串仍以delimiter开头和结尾.<br>
   * <p>
   * <blockquote>
   * 
   * <pre>
   * mergeSeq(&quot;,1,2,&quot;, &quot;&quot;) = &quot;,1,2,&quot;;
   * mergeSeq(&quot;,1,2,&quot;, null) = &quot;,1,2,&quot;;
   * mergeSeq(&quot;1,2&quot;, &quot;3&quot;) = &quot;1,2,3&quot;;
   * mergeSeq(&quot;1,2&quot;, &quot;3,&quot;) = &quot;1,2,3,&quot;;
   * mergeSeq(&quot;,1,2&quot;, &quot;3,&quot;) = &quot;,1,2,3,&quot;;
   * mergeSeq(&quot;,1,2,&quot;, &quot;,3,&quot;) = &quot;,1,2,3,&quot;;
   * </pre>
   * 
   * </blockquote>
   * <p>
   * 
   * @param first
   *          a {@link java.lang.String} object.
   * @param second
   *          a {@link java.lang.String} object.
   * @param delimiter
   *          a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String mergeSeq(final String first, final String second, final String delimiter) {
    if (isNotEmpty(second) && isNotEmpty(first)) {
      List<String> firstSeq = Arrays.asList(split(first, delimiter));
      List<String> secondSeq = Arrays.asList(split(second, delimiter));
      Collection<String> rs = CollectUtils.union(firstSeq, secondSeq);
      StringBuilder buf = new StringBuilder();
      for (final String ele : rs)
        buf.append(delimiter).append(ele);
      if (buf.length() > 0) buf.append(delimiter);
      return buf.toString();
    } else {
      return ((first == null) ? "" : first) + ((second == null) ? "" : second);
    }
  }

  /**
   * <p>
   * removeWord.
   * </p>
   * 
   * @param host
   *          a {@link java.lang.String} object.
   * @param word
   *          a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String removeWord(final String host, final String word) {
    return removeWord(host, word, DELIMITER);

  }

  /**
   * <p>
   * removeWord.
   * </p>
   * 
   * @param host
   *          a {@link java.lang.String} object.
   * @param word
   *          a {@link java.lang.String} object.
   * @param delimiter
   *          a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String removeWord(final String host, final String word, final String delimiter) {
    if (host.indexOf(word) == -1) {
      return host;
    } else {
      int beginIndex = host.indexOf(word);
      int endIndex = beginIndex + word.length();
      if (beginIndex == 0) { return host.substring(endIndex + 1); }
      if (endIndex == host.length()) {
        return host.substring(0, beginIndex - delimiter.length());
      } else {
        String before = host.substring(0, beginIndex);
        String after = host.substring(endIndex + 1);
        return before + after;
      }
    }
  }

  /**
   * <p>
   * Returns padding using the specified delimiter repeated to a given length.
   * </p>
   * 
   * <pre>
   * repeat(0, 'e')  = ""
   * repeat(3, 'e')  = "eee"
   * repeat(-2, 'e') = ""
   * </pre>
   * 
   * @param ch character to repeat
   * @param repeat number of times to repeat char, negative treated as zero
   * @return String with repeated character
   * @see #repeat(String, int)
   */
  public static String repeat(char ch, int repeat) {
    char[] buf = new char[repeat];
    for (int i = repeat - 1; i >= 0; i--) {
      buf[i] = ch;
    }
    return new String(buf);
  }

  /**
   * <p>
   * Repeat a String {@code repeat} times to form a new String.
   * </p>
   * 
   * <pre>
   * repeat(null, 2) = null
   * repeat("", 0)   = ""
   * repeat("", 2)   = ""
   * repeat("a", 3)  = "aaa"
   * repeat("ab", 2) = "abab"
   * repeat("a", -2) = ""
   * </pre>
   * 
   * @param str the String to repeat, may be null
   * @param repeat number of times to repeat str, negative treated as zero
   * @return a new String consisting of the original String repeated, {@code null} if null String
   *         input
   * @since 3.0
   */
  public static String repeat(String str, int repeat) {
    if (str == null) return null;
    if (repeat <= 1) {
      Assert.isTrue(repeat >= 0, "invalid count: %s", repeat);
      return (repeat == 0) ? "" : str;
    }
    final int len = str.length();
    final long longSize = (long) len * (long) repeat;
    final int size = (int) longSize;
    if (size != longSize) { throw new ArrayIndexOutOfBoundsException("Required array size too large: "
        + String.valueOf(longSize)); }

    final char[] array = new char[size];
    str.getChars(0, len, array, 0);
    int n;
    for (n = len; n < size - n; n <<= 1) {
      System.arraycopy(array, 0, array, n, n);
    }
    System.arraycopy(array, 0, array, n, size - n);
    return new String(array);

  }

  /**
   * <p>
   * Replaces all occurrences of a String within another String.
   * </p>
   * <p>
   * A {@code null} reference passed to this method is a no-op.
   * </p>
   * 
   * <pre>
   * replace(null, *, *)        = null
   * replace("", *, *)          = ""
   * replace("any", null, *)    = "any"
   * replace("any", *, null)    = "any"
   * replace("any", "", *)      = "any"
   * replace("aba", "a", null)  = "aba"
   * replace("aba", "a", "")    = "b"
   * replace("aba", "a", "z")   = "zbz"
   * </pre>
   * 
   * @param text text to search and replace in, may be null
   * @param searchString the String to search for, may be null
   * @param replacement the String to replace it with, may be null
   * @return the text with any replacements processed, {@code null} if null String input
   */
  public static String replace(String text, String searchString, String replacement) {
    if (isEmpty(text) || isEmpty(searchString) || replacement == null) { return text; }
    int start = 0;
    int end = text.indexOf(searchString, start);
    if (end == -1) { return text; }
    int replLength = searchString.length();
    int increase = replacement.length() - replLength;
    increase = increase < 0 ? 0 : increase;
    increase *= 16;
    StringBuilder buf = new StringBuilder(text.length() + increase);
    while (end != -1) {
      buf.append(text.substring(start, end)).append(replacement);
      start = end + replLength;
      end = text.indexOf(searchString, start);
    }
    buf.append(text.substring(start));
    return buf.toString();
  }

  /**
   * <p>
   * split.
   * </p>
   * 
   * @param target
   *          a {@link java.lang.String} object.
   * @return an array of {@link java.lang.String} objects.
   */
  public static String[] split(String target) {
    return split2(target, new char[] { ',', ';', '\r', '\n', ' ' });
  }

  /**
   * <p>
   * Splits the provided text into an array, separator specified. This is an alternative to using
   * StringTokenizer.
   * </p>
   * A {@code null} input String returns {@code null}.
   * </p>
   * 
   * <pre>
   * split(null, *)         = null
   * split("", *)           = []
   * split("a.b.c", '.')    = ["a", "b", "c"]
   * split("a..b.c", '.')   = ["a", "b", "c"]
   * split("a:b:c", '.')    = ["a:b:c"]
   * split("a b c", ' ')    = ["a", "b", "c"]
   * </pre>
   */
  public static String[] split(String str, char separatorChar) {
    if (str == null) { return null; }
    int len = str.length();
    if (len == 0) return new String[0];
    List<String> list = new ArrayList<String>();
    int i = 0, start = 0;
    boolean match = false;
    while (i < len) {
      if (str.charAt(i) == separatorChar) {
        if (match) {
          list.add(str.substring(start, i));
          match = false;
        }
        start = ++i;
        continue;
      }
      match = true;
      i++;
    }
    if (match) list.add(str.substring(start, i));
    return list.toArray(new String[list.size()]);

  }

  /**
   * <p>
   * split.
   * </p>
   * 
   * @param target
   *          a {@link java.lang.String} object.
   * @param separatorChars
   *          an array of char.
   * @return an array of {@link java.lang.String} objects.
   */
  public static String[] split2(String target, char[] separatorChars) {
    if (null == target) { return new String[0]; }
    char[] sb = target.toCharArray();
    for (char separator : separatorChars) {
      if (separator != ',') {
        for (int i = 0; i < sb.length; i++)
          if (sb[i] == separator) sb[i] = ',';
      }
    }
    String[] targets = split(new String(sb), ',');
    List<String> list = CollectUtils.newArrayList();
    for (String one : targets) {
      if (isNotBlank(one)) list.add(one.trim());
    }
    String[] rs = new String[list.size()];
    list.toArray(rs);
    return rs;
  }

  /**
   * *
   * <p>
   * Splits the provided text into an array, separators specified. This is an alternative to using
   * StringTokenizer.
   * </p>
   * <p>
   * A {@code null} input String returns {@code null}. A {@code null} separatorChars splits on
   * whitespace.
   * </p>
   * 
   * <pre>
   * split(null, *)         = null
   * split("", *)           = []
   * split("abc def", null) = ["abc", "def"]
   * split("abc def", " ")  = ["abc", "def"]
   * split("abc  def", " ") = ["abc", "def"]
   * split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
   * </pre>
   */
  public static String[] split(String str, String separatorChars) {
    if (str == null) { return null; }
    int len = str.length();
    if (len == 0) return new String[0];
    List<String> list = new ArrayList<String>();
    int i = 0, start = 0;
    boolean match = false;
    if (null == separatorChars) separatorChars = " ";
    while (i < len) {
      if (separatorChars.indexOf(str.charAt(i)) >= 0) {
        if (match) {
          list.add(str.substring(start, i));
          match = false;
        }
        start = ++i;
        continue;
      }
      match = true;
      i++;
    }
    if (match) list.add(str.substring(start, i));
    return list.toArray(new String[list.size()]);
  }

  /**
   * 将1-2,3,4-9之类的序列拆分成数组
   * 
   * @param numSeq
   *          a {@link java.lang.String} object.
   * @return an array of {@link java.lang.Integer} objects.
   */
  public static Integer[] splitNumSeq(final String numSeq) {
    if (isEmpty(numSeq)) { return null; }
    String[] numArray = split(numSeq, ',');
    Set<Integer> numSet = CollectUtils.newHashSet();
    for (int i = 0; i < numArray.length; i++) {
      String num = numArray[i];
      if (num.contains("-")) {
        String[] termFromTo = split(num, '-');
        int from = Numbers.toInt(termFromTo[0]);
        int to = Numbers.toInt(termFromTo[1]);
        for (int j = from; j <= to; j++) {
          numSet.add(Integer.valueOf(j));
        }
      } else {
        numSet.add(new Integer(num));
      }
    }
    Integer[] nums = new Integer[numSet.size()];
    numSet.toArray(nums);
    return nums;
  }

  /**
   * <p>
   * splitToInteger.
   * </p>
   * 
   * @param ids
   *          a {@link java.lang.String} object.
   * @return an array of {@link java.lang.Integer} objects.
   */
  public static Integer[] splitToInteger(final String ids) {
    if (isEmpty(ids)) return new Integer[0];
    else return transformToInteger(split(ids, ','));
  }

  /**
   * <p>
   * splitToLong.
   * </p>
   * 
   * @param ids
   *          a {@link java.lang.String} object.
   * @return an array of {@link java.lang.Long} objects.
   */
  public static Long[] splitToLong(final String ids) {
    if (isEmpty(ids)) {
      return new Long[0];
    } else {
      return transformToLong(split(ids, ','));
    }
  }

  /**
   * <p>
   * Gets a substring from the specified String avoiding exceptions.
   * </p>
   * <p>
   * A negative start position can be used to start/end {@code n} characters from the end of the
   * String.
   * </p>
   * <p>
   * The returned substring starts with the character in the {@code start} position and ends before
   * the {@code end} position. All position counting is zero-based -- i.e., to start at the
   * beginning of the string use {@code start = 0}. Negative start and end positions can be used to
   * specify offsets relative to the end of the String.
   * </p>
   * <p>
   * If {@code start} is not strictly to the left of {@code end}, "" is returned.
   * </p>
   * 
   * <pre>
   * substring(null, *, *)    = null
   * substring("", * ,  *)    = "";
   * substring("abc", 0, 2)   = "ab"
   * substring("abc", 2, 0)   = ""
   * substring("abc", 2, 4)   = "c"
   * substring("abc", 4, 6)   = ""
   * substring("abc", 2, 2)   = ""
   * substring("abc", -2, -1) = "b"
   * substring("abc", -4, 2)  = "ab"
   * </pre>
   * 
   * @param str
   *          the String to get the substring from, may be null
   * @param start
   *          the position to start from, negative means
   *          count back from the end of the String by this many characters
   * @param end
   *          the position to end at (exclusive), negative means
   *          count back from the end of the String by this many characters
   * @return substring from start position to end position, {@code null} if null String input
   */
  public static String substring(String str, int start, int end) {
    if (str == null) return null;
    // handle negatives
    if (end < 0) end = str.length() + end; // remember end is negative
    if (start < 0) start = str.length() + start; // remember start is negative
    // check length next
    if (end > str.length()) end = str.length();
    // if start is greater than end, return ""
    if (start > end) return "";
    if (start < 0) start = 0;
    if (end < 0) end = 0;
    return str.substring(start, end);
  }

  /**
   * <p>
   * subtractSeq.
   * </p>
   * 
   * @param first
   *          a {@link java.lang.String} object.
   * @param second
   *          a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String subtractSeq(final String first, final String second) {
    return subtractSeq(first, second, DELIMITER);
  }

  /**
   * 返回一个新的逗号相隔字符串，实现其中的单词a-b的功能. 新的字符串将以,开始,结束<br>
   * 
   * @param first
   *          a {@link java.lang.String} object.
   * @param second
   *          a {@link java.lang.String} object.
   * @param delimiter
   *          a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String subtractSeq(String first, String second, String delimiter) {
    if (isEmpty(first)) { return ""; }
    if (isEmpty(second)) {
      StringBuilder builder = new StringBuilder();
      if (!first.startsWith(delimiter)) {
        builder.append(delimiter).append(first);
      }
      if (!first.endsWith(delimiter)) {
        builder.append(first).append(delimiter);
      }
      return builder.toString();
    }
    List<String> firstSeq = Arrays.asList(split(first, delimiter));
    List<String> secondSeq = Arrays.asList(split(second, delimiter));
    Collection<String> rs = CollectUtils.subtract(firstSeq, secondSeq);
    StringBuilder buf = new StringBuilder();
    for (final String ele : rs)
      buf.append(delimiter).append(ele);
    if (buf.length() > 0) buf.append(delimiter);
    return buf.toString();
  }

  /**
   * <p>
   * transformToInteger.
   * </p>
   * 
   * @param ids
   *          an array of {@link java.lang.String} objects.
   * @return an array of {@link java.lang.Integer} objects.
   */
  public static Integer[] transformToInteger(final String[] ids) {
    Integer[] idsOfInteger = new Integer[ids.length];
    for (int i = 0; i < ids.length; i++) {
      idsOfInteger[i] = new Integer(ids[i]);
    }
    return idsOfInteger;
  }

  /**
   * <p>
   * transformToLong.
   * </p>
   * 
   * @param ids
   *          an array of {@link java.lang.String} objects.
   * @return an array of {@link java.lang.Long} objects.
   */
  public static Long[] transformToLong(final String[] ids) {
    if (null == ids) { return null; }
    Long[] idsOfLong = new Long[ids.length];
    for (int i = 0; i < ids.length; i++) {
      idsOfLong[i] = new Long(ids[i]);
    }
    return idsOfLong;
  }

  /**
   * <p>
   * unCamel.
   * </p>
   */
  public static String unCamel(String str) {
    return unCamel(str, '-', true);
  }

  /**
   * <p>
   * unCamel.
   * </p>
   * 
   * @param str
   *          a {@link java.lang.String} object.
   * @param seperator
   *          a char.
   * @return a {@link java.lang.String} object.
   */
  public static String unCamel(String str, char seperator) {
    return unCamel(str, seperator, true);
  }

  /**
   * 将驼峰表示法转换为下划线小写表示
   * 
   * @param str
   *          a {@link java.lang.String} object.
   * @param seperator
   *          a char.
   * @param lowercase
   *          a boolean.
   * @return a {@link java.lang.String} object.
   */
  public static String unCamel(String str, char seperator, boolean lowercase) {
    char[] ca = str.toCharArray();
    if (3 > ca.length) { return lowercase ? str.toLowerCase() : str; }
    // about five seperator
    StringBuilder build = new StringBuilder(ca.length + 5);
    build.append(lowercase ? toLowerCase(ca[0]) : ca[0]);

    boolean lower1 = isLowerCase(ca[0]);
    int i = 1;
    while (i < ca.length - 1) {
      char cur = ca[i];
      char next = ca[i + 1];
      boolean upper2 = isUpperCase(cur);
      boolean lower3 = isLowerCase(next);
      if (lower1 && upper2 && lower3) {
        build.append(seperator);
        build.append(lowercase ? toLowerCase(cur) : cur);
        build.append(next);
        i += 2;
      } else {
        if (lowercase && upper2) {
          build.append(toLowerCase(cur));
        } else {
          build.append(cur);
        }
        lower1 = !upper2;
        i++;
      }
    }
    if (i == ca.length - 1) {
      build.append(lowercase ? toLowerCase(ca[i]) : ca[i]);
    }
    return build.toString();
  }

  /**
   * <p>
   * Gets the substring before the first occurrence of a separator. The separator is not returned.
   * </p>
   * <p>
   * A {@code null} string input will return {@code null}. An empty ("") string input will return
   * the empty string. A {@code null} separator will return the input string.
   * </p>
   * <p>
   * If nothing is found, the string input is returned.
   * </p>
   * 
   * <pre>
   * substringBefore(null, *)      = null
   * substringBefore("", *)        = ""
   * substringBefore("abc", "a")   = ""
   * substringBefore("abcba", "b") = "a"
   * substringBefore("abc", "c")   = "ab"
   * substringBefore("abc", "d")   = "abc"
   * substringBefore("abc", "")    = ""
   * substringBefore("abc", null)  = "abc"
   * </pre>
   * 
   * @param str the String to get a substring from, may be null
   * @param separator the String to search for, may be null
   * @return the substring before the first occurrence of the separator, {@code null} if null String
   *         input
   * @since 2.0
   */
  public static String substringBefore(String str, String separator) {
    if (isEmpty(str) || separator == null) { return str; }
    if (separator.length() == 0) { return Empty; }
    int pos = str.indexOf(separator);
    if (pos == Index_not_found) { return str; }
    return str.substring(0, pos);
  }

  /**
   * <p>
   * Gets the substring after the first occurrence of a separator. The separator is not returned.
   * </p>
   * <p>
   * A {@code null} string input will return {@code null}. An empty ("") string input will return
   * the empty string. A {@code null} separator will return the empty string if the input string is
   * not {@code null}.
   * </p>
   * <p>
   * If nothing is found, the empty string is returned.
   * </p>
   * 
   * <pre>
   * substringAfter(null, *)      = null
   * substringAfter("", *)        = ""
   * substringAfter(*, null)      = ""
   * substringAfter("abc", "a")   = "bc"
   * substringAfter("abcba", "b") = "cba"
   * substringAfter("abc", "c")   = ""
   * substringAfter("abc", "d")   = ""
   * substringAfter("abc", "")    = "abc"
   * </pre>
   * 
   * @param str the String to get a substring from, may be null
   * @param separator the String to search for, may be null
   * @return the substring after the first occurrence of the separator, {@code null} if null String
   *         input
   * @since 2.0
   */
  public static String substringAfter(String str, String separator) {
    if (isEmpty(str)) { return str; }
    if (separator == null) { return Empty; }
    int pos = str.indexOf(separator);
    if (pos == Index_not_found) { return Empty; }
    return str.substring(pos + separator.length());
  }

  /**
   * <p>
   * Gets the String that is nested in between two Strings. Only the first match is returned.
   * </p>
   * <p>
   * A {@code null} input String returns {@code null}. A {@code null} open/close returns
   * {@code null} (no match). An empty ("") open and close returns an empty string.
   * </p>
   * 
   * <pre>
   * substringBetween("wx[b]yz", "[", "]") = "b"
   * substringBetween(null, *, *)          = null
   * substringBetween(*, null, *)          = null
   * substringBetween(*, *, null)          = null
   * substringBetween("", "", "")          = ""
   * substringBetween("", "", "]")         = null
   * substringBetween("", "[", "]")        = null
   * substringBetween("yabcz", "", "")     = ""
   * substringBetween("yabcz", "y", "z")   = "abc"
   * substringBetween("yabczyabcz", "y", "z")   = "abc"
   * </pre>
   * 
   * @param str the String containing the substring, may be null
   * @param open the String before the substring, may be null
   * @param close the String after the substring, may be null
   * @return the substring, {@code null} if no match
   * @since 3.0
   */
  public static String substringBetween(String str, String open, String close) {
    if (str == null || open == null || close == null) { return null; }
    int start = str.indexOf(open);
    if (start != Index_not_found) {
      int end = str.indexOf(close, start + open.length());
      if (end != Index_not_found) { return str.substring(start + open.length(), end); }
    }
    return null;
  }

  /**
   * <p>
   * Gets the substring before the last occurrence of a separator. The separator is not returned.
   * </p>
   * <p>
   * A {@code null} string input will return {@code null}. An empty ("") string input will return
   * the empty string. An empty or {@code null} separator will return the input string.
   * </p>
   * <p>
   * If nothing is found, the string input is returned.
   * </p>
   * 
   * <pre>
   * substringBeforeLast(null, *)      = null
   * substringBeforeLast("", *)        = ""
   * substringBeforeLast("abcba", "b") = "abc"
   * substringBeforeLast("abc", "c")   = "ab"
   * substringBeforeLast("a", "a")     = ""
   * substringBeforeLast("a", "z")     = "a"
   * substringBeforeLast("a", null)    = "a"
   * substringBeforeLast("a", "")      = "a"
   * </pre>
   * 
   * @param str the String to get a substring from, may be null
   * @param separator the String to search for, may be null
   * @return the substring before the last occurrence of the separator, {@code null} if null String
   *         input
   * @since 3.0
   */
  public static String substringBeforeLast(String str, String separator) {
    if (isEmpty(str) || isEmpty(separator)) { return str; }
    int pos = str.lastIndexOf(separator);
    if (pos == Index_not_found) { return str; }
    return str.substring(0, pos);
  }

  /**
   * <p>
   * Gets the substring after the last occurrence of a separator. The separator is not returned.
   * </p>
   * <p>
   * A {@code null} string input will return {@code null}. An empty ("") string input will return
   * the empty string. An empty or {@code null} separator will return the empty string if the input
   * string is not {@code null}.
   * </p>
   * <p>
   * If nothing is found, the empty string is returned.
   * </p>
   * 
   * <pre>
   * substringAfterLast(null, *)      = null
   * substringAfterLast("", *)        = ""
   * substringAfterLast(*, "")        = ""
   * substringAfterLast(*, null)      = ""
   * substringAfterLast("abc", "a")   = "bc"
   * substringAfterLast("abcba", "b") = "a"
   * substringAfterLast("abc", "c")   = ""
   * substringAfterLast("a", "a")     = ""
   * substringAfterLast("a", "z")     = ""
   * </pre>
   * 
   * @param str the String to get a substring from, may be null
   * @param separator the String to search for, may be null
   * @return the substring after the last occurrence of the separator, {@code null} if null String
   *         input
   * @since 3.0
   */
  public static String substringAfterLast(String str, String separator) {
    if (isEmpty(str)) { return str; }
    if (isEmpty(separator)) { return Empty; }
    int pos = str.lastIndexOf(separator);
    if (pos == Index_not_found || pos == str.length() - separator.length()) { return Empty; }
    return str.substring(pos + separator.length());
  }

  /**
   * <p>
   * Removes control characters (char &lt;= 32) from both ends of this String, handling {@code null}
   * by returning {@code null}.
   * </p>
   * <p>
   * The String is trimmed using {@link String#trim()}. Trim removes start and end characters &lt;=
   * 32.
   * </p>
   * 
   * <pre>
   * trim(null)          = null
   * trim("")            = ""
   * trim("     ")       = ""
   * trim("abc")         = "abc"
   * trim("    abc    ") = "abc"
   * </pre>
   * 
   * @param str the String to be trimmed, may be null
   * @return the trimmed string, {@code null} if null String input
   * @since 3.0
   */
  public static String trim(String str) {
    return str == null ? null : str.trim();
  }

  /**
   * <p>
   * Uncapitalizes a String changing the first letter to title case as per
   * {@link Character#toLowerCase(char)}. No other letters are changed.
   * </p>
   * <p>
   * For a word based algorithm, see String returns {@code null}.
   * </p>
   * 
   * <pre>
   * uncapitalize(null)  = null
   * uncapitalize("")    = ""
   * uncapitalize("Cat") = "cat"
   * uncapitalize("CAT") = "cAT"
   * </pre>
   * 
   * @param str the String to uncapitalize, may be null
   * @return the uncapitalized String, {@code null} if null String input
   * @see #capitalize(String)
   * @since 3.0
   */
  public static String uncapitalize(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) { return str; }
    return new StringBuilder(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1))
        .toString();
  }

  /**
   * <p>
   * Returns either the passed in CharSequence, or if the CharSequence is whitespace, empty ("") or
   * {@code null}, the value of {@code defaultStr}.
   * </p>
   * 
   * @param str
   * @param defaultStr
   */
  public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
    return isBlank(str) ? defaultStr : str;
  }
}
