/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class BatchReplacer {

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("using BatchReplaceMain dir patternfile");
			return;
		}
		String dir = args[0];
		String properties = args[1];
		LineNumberReader reader = new LineNumberReader(new FileReader(properties));
		String lineContent = null;
		Map profiles = new HashMap();
		Profile profile = null;
		do {
			lineContent = reader.readLine();
			if (null != lineContent) {
				if (StringUtils.isEmpty(lineContent)) {
					continue;
				}
				if (-1 == lineContent.indexOf('=')) {
					profile = new Profile();
					profiles.put(lineContent, profile);
				} else {
					Pair pair = new Pair(StringUtils.substringBefore(lineContent, "="),
							StringUtils.substringAfter(lineContent, "="));
					profile.pairs.add(pair);
				}
			}
		} while (null != lineContent);
		replaceFile(profiles, dir);
	}

	public static void replaceFile(final Map profiles, String fileName) throws Exception,
			FileNotFoundException {
		File file = new File(fileName);
		if (file.isFile() && !file.isHidden()) {
			final Profile profile = (Profile) profiles.get(StringUtils.substringAfterLast(fileName,
					"."));
			if (null == profile) { return; }
			System.out.println("processing " + fileName);
			String filecontent = FileUtils.readFileToString(file);
//			if (!filecontent.startsWith("[#ftl]")) {
//				filecontent = "[#ftl]\n" + filecontent;
//			}
			StringBuilder sb = new StringBuilder();
			if (null != filecontent) {
				for (Iterator iter = profile.pairs.iterator(); iter.hasNext();) {
					Pair pair = (Pair) iter.next();
					filecontent = pair.replaceAll(filecontent);
				}
			}
			writeToFile(filecontent, fileName);
		} else {
			String[] subFiles = file.list(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					if (StringUtils.contains(name, "svn")) return false;
					if (dir.isDirectory()) return true;
					boolean matched = false;
					for (Iterator iter = profiles.keySet().iterator(); iter.hasNext();) {
						matched = (name.endsWith((String) iter.next()));
						if (matched) return true;
					}
					return false;
				}
			});
			if (null != subFiles) {
				for (int i = 0; i < subFiles.length; i++) {
					replaceFile(profiles, fileName + '/' + subFiles[i]);
				}
			}
		}
	}

	public static void writeToFile(String str, String fileName) throws Exception {
		FileWriter fw = new FileWriter(fileName);
		fw.write(str);
		fw.close();
	}
}

class Profile {

	String extension;

	List pairs = new ArrayList();
}

class Pair {

	Pattern pattern;

	String key;

	String value;

	public Pair(String key, String value) {
		super();
		this.key = key;
		pattern = Pattern.compile(key);
		this.value = value;
	}

	public String replaceAll(String input) {
		return pattern.matcher(input).replaceAll(value);
	}

	public String toString() {
		return key + "=" + value;
	}

}
