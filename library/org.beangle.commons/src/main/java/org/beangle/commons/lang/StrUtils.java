/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.beangle.commons.collection.CollectUtils;

/**
 * @author chaostone 2005-11-15
 */
public final class StrUtils {
	public static final String DELIMITER = ",";

	private StrUtils() {
	}

	/**
	 * count char in host string
	 * 
	 * @param host
	 * @param charactor
	 * @return
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
	 * @param searchStr
	 * @return
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

	public static String insert(final String str, final String c, final int pos) {
		if (str.length() < pos) { return str; }
		return str.substring(0, pos - 1) + c + str.substring(pos);
	}

	/**
	 * replace [bigen,end] [1...end] with givenStr
	 * 可以使用StringBuilder的replace方法替换该方法
	 * 
	 * @param str
	 * @param c
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String insert(final String str, final String given, final int begin, final int end) {
		if (begin < 1 || end > str.length() || end < begin) { return str; }
		return str.substring(0, begin - 1) + given + str.substring(end);
	}

	public static String unCamel(String str, char seperator) {
		return unCamel(str, seperator, true);
	}

	/**
	 * 将驼峰表示法转换为下划线小写表示
	 * 
	 * @param str
	 * @return
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
				build.append(toLowerCase(cur));
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

	public static String unCamel(String str) {
		return unCamel(str, '-', true);
	}

	/**
	 * @param ids
	 * @return
	 */
	public static Long[] splitToLong(final String ids) {
		if (StringUtils.isEmpty(ids)) {
			return new Long[0];
		} else {
			return transformToLong(StringUtils.split(ids, ","));
		}
	}

	/**
	 * @param ids
	 * @return
	 */
	public static Integer[] splitToInteger(final String ids) {
		if (StringUtils.isEmpty(ids)) {
			return new Integer[0];
		} else {
			return transformToInteger(StringUtils.split(ids, ","));
		}
	}

	public static String[] split(String target) {
		return split(target, new char[] { ',', ';', '\r', '\n', ' ' });
	}

	public static String[] split(String target, char[] separatorChars) {
		if (null == target) { return new String[0]; }
		char[] sb = target.toCharArray();
		for (char separator : separatorChars) {
			if (separator != ',') for (int i = 0; i < sb.length; i++) {
				if (sb[i] == separator) {
					sb[i] = ',';
				}
			}
		}
		String[] targets = StringUtils.split(new String(sb), ',');
		List<String> list = CollectUtils.newArrayList();
		for (String one : targets) {
			if (StringUtils.isNotBlank(one)) {
				list.add(one.trim());
			}
		}
		String[] rs = new String[list.size()];
		list.toArray(rs);
		return rs;
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
	 * @param second
	 * @return
	 */
	public static String mergeSeq(final String first, final String second, final String delimiter) {
		if (StringUtils.isNotEmpty(second) && StringUtils.isNotEmpty(first)) {
			List<String> firstSeq = Arrays.asList(StringUtils.split(first, delimiter));
			List<String> secondSeq = Arrays.asList(StringUtils.split(second, delimiter));
			@SuppressWarnings("unchecked")
			Collection<String> rs = CollectionUtils.union(firstSeq, secondSeq);
			StringBuilder buf = new StringBuilder();
			for (final String ele : rs) {
				buf.append(delimiter).append(ele);
			}
			if (buf.length() > 0) {
				buf.append(delimiter);
			}
			return buf.toString();
		} else {
			return ((first == null) ? "" : first) + ((second == null) ? "" : second);
		}
	}

	public static String mergeSeq(final String first, final String second) {
		return mergeSeq(first, second, DELIMITER);
	}

	/**
	 * 判断两个","逗号相隔的字符串中的单词是否完全等同.
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean isEqualSeq(final String first, final String second, final String delimiter) {
		if (StringUtils.isNotEmpty(first) && StringUtils.isNotEmpty(second)) {
			String[] firstWords = StringUtils.split(first, delimiter);
			Set<String> firstSet = CollectUtils.newHashSet();
			for (int i = 0; i < firstWords.length; i++) {
				firstSet.add(firstWords[i]);
			}
			String[] secondWords = StringUtils.split(second, delimiter);
			Set<String> secondSet = CollectUtils.newHashSet();
			for (int i = 0; i < secondWords.length; i++) {
				secondSet.add(secondWords[i]);
			}
			return firstSet.equals(secondSet);
		} else {
			return StringUtils.isEmpty(first) & StringUtils.isEmpty(second);
		}
	}

	/**
	 * 返回一个新的逗号相隔字符串，实现其中的单词a-b的功能. 新的字符串将以,开始,结束<br>
	 * 
	 * @param first
	 * @param second
	 * @param delimiter
	 * @return
	 */
	public static String subtractSeq(String first, String second, String delimiter) {
		if (StringUtils.isEmpty(first)) { return ""; }
		if (StringUtils.isEmpty(second)) {
			StringBuilder builder = new StringBuilder();
			if (!first.startsWith(delimiter)) {
				builder.append(delimiter).append(first);
			}
			if (!first.endsWith(delimiter)) {
				builder.append(first).append(delimiter);
			}
			return builder.toString();
		}
		List<String> firstSeq = Arrays.asList(StringUtils.split(first, delimiter));
		List<String> secondSeq = Arrays.asList(StringUtils.split(second, delimiter));
		@SuppressWarnings("unchecked")
		Collection<String> rs = CollectionUtils.subtract(firstSeq, secondSeq);
		StringBuilder buf = new StringBuilder();
		for (final String ele : rs) {
			buf.append(delimiter).append(ele);
		}
		if (buf.length() > 0) {
			buf.append(delimiter);
		}
		return buf.toString();
	}

	public static String subtractSeq(final String first, final String second) {
		return subtractSeq(first, second, DELIMITER);
	}

	/**
	 * 返回一个新的逗号相隔字符串，实现其中的单词a-b的功能
	 * 
	 * @param first
	 * @param second
	 * @param delimiter
	 * @return
	 */
	public static String intersectSeq(final String first, final String second,
			final String delimiter) {
		if (StringUtils.isEmpty(first) || StringUtils.isEmpty(second)) { return ""; }
		List<String> firstSeq = Arrays.asList(StringUtils.split(first, ","));
		List<String> secondSeq = Arrays.asList(StringUtils.split(second, ","));
		@SuppressWarnings("unchecked")
		Collection<String> rs = CollectionUtils.intersection(firstSeq, secondSeq);
		StringBuilder buf = new StringBuilder();
		for (final String ele : rs) {
			buf.append(delimiter).append(ele);
		}
		if (buf.length() > 0) {
			buf.append(delimiter);
		}
		return buf.toString();
	}

	public static String intersectSeq(final String first, final String second) {
		return intersectSeq(first, second, DELIMITER);
	}

	public static boolean isEqualSeq(final String first, final String second) {
		return isEqualSeq(first, second, DELIMITER);
	}

	public static String removeWord(final String host, final String word) {
		return removeWord(host, word, DELIMITER);

	}

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
	 * 将数组中的字符串，用delimiter串接起来.<br>
	 * 首尾不加delimiter
	 * 
	 * @param seq
	 * @param delimiter
	 * @return
	 */
	public static String join(final Object[] seq, final String delimiter) {
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

	public static String concat(final String... seq) {
		return join(seq, null);
	}

	public static String join(final String... seq) {
		return join(seq, DELIMITER);
	}

	/**
	 * @param ids
	 * @return
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
	 * @param ids
	 * @return
	 */
	public static Integer[] transformToInteger(final String[] ids) {
		Integer[] idsOfInteger = new Integer[ids.length];
		for (int i = 0; i < ids.length; i++) {
			idsOfInteger[i] = new Integer(ids[i]);
		}
		return idsOfInteger;
	}

	/**
	 * 将1-2,3,4-9之类的序列拆分成数组
	 * 
	 * @param numSeq
	 * @return
	 */
	public static Integer[] splitNumSeq(final String numSeq) {
		if (StringUtils.isEmpty(numSeq)) { return null; }
		String[] numArray = StringUtils.split(numSeq, ",");
		Set<Integer> numSet = CollectUtils.newHashSet();
		for (int i = 0; i < numArray.length; i++) {
			String num = numArray[i];
			if (StringUtils.contains(num, "-")) {
				String[] termFromTo = StringUtils.split(num, "-");
				int from = NumberUtils.toInt(termFromTo[0]);
				int to = NumberUtils.toInt(termFromTo[1]);
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
	 * 保持逗号分隔的各个单词都是唯一的。并且按照原来的顺序存放。
	 * 
	 * @param keys
	 * @return
	 */
	public static String keepSeqUnique(final String keys) {
		String[] keysArray = StringUtils.split(keys, ",");
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
}
