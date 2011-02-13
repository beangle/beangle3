/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.replacer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Batch replace file with pattern. Using<B> BatchReplaceMain dir patternfile
 * [encoding]</B>
 * <p>
 * Pattern file like this.
 * 
 * <pre>
 * ftl
 * &lt;#(.*?)&gt;=[#$1]
 * &lt;/#(.*?)&gt;=[/#$1]
 * &lt;/@&gt;=[/@]
 * &lt;@(.*?)/&gt;=[@$1/]
 * &lt;@(.*?)&gt;=[@$1]
 * &lt;/@(.*?)&gt;=[/@]
 * </pre>
 * 
 * @author chaostone
 */
public class BatchReplaceMain {

	private static Logger logger = LoggerFactory.getLogger(BatchReplaceMain.class);

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			logger.info("using BatchReplaceMain dir patternfile encoding");
			return;
		}
		String dir = args[0];
		if (!new File(dir).exists()) {
			logger.error("{} not a valid file or directory", dir);
			return;
		}
		String properties = args[1];
		if (!new File(properties).exists()) {
			logger.info("{} not valid file or directory", properties);
		}
		String encoding = null;
		if (args.length >= 3) {
			encoding = args[2];
		}
		@SuppressWarnings("unchecked")
		List<String> lines = FileUtils.readLines(new File(properties));
		Map<String, List<Replacer>> profiles = CollectUtils.newHashMap();
		List<Replacer> replacers = null;
		for (String line : lines) {
			if (StringUtils.isEmpty(line)) {
				continue;
			}
			if (-1 == line.indexOf('=')) {
				replacers = CollectUtils.newArrayList();
				profiles.put(line, replacers);
			} else {
				line = StringUtils.replace(line, "\\=", "~~~~");
				String older = StringUtils.replace(StringUtils.substringBefore(line, "="), "~~~~", "=");
				String newer = StringUtils.replace(StringUtils.substringAfter(line, "="), "~~~~", "=");
				older = StringUtils.replace(older, "\\n", "\n");
				older = StringUtils.replace(older, "\\t", "\t");
				newer = StringUtils.replace(newer, "\\n", "\n");
				newer = StringUtils.replace(newer, "\\t", "\t");
				Replacer pair = new Replacer(older, newer);
				replacers.add(pair);
			}
		}
		replaceFile(dir, profiles, encoding);
	}

	public static void replaceFile(String fileName, final Map<String, List<Replacer>> profiles,
			String encoding) throws Exception, FileNotFoundException {
		File file = new File(fileName);
		if (file.isFile() && !file.isHidden()) {
			List<Replacer> replacers = profiles.get(StringUtils.substringAfterLast(fileName, "."));
			if (null == replacers) { return; }
			logger.info("processing {}", fileName);
			String filecontent = FileUtils.readFileToString(file, encoding);
			filecontent = Replacer.process(filecontent, replacers);
			writeToFile(filecontent, fileName, encoding);
		} else {
			String[] subFiles = file.list(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					if (dir.isDirectory()) return true;
					boolean matched = false;
					for (String key : profiles.keySet()) {
						matched = name.endsWith(key);
						if (matched) return true;
					}
					return false;
				}
			});
			if (null != subFiles) {
				for (int i = 0; i < subFiles.length; i++) {
					replaceFile(fileName + '/' + subFiles[i], profiles, encoding);
				}
			}
		}
	}

	public static void writeToFile(String str, String fileName, String encoding) throws Exception {
		OutputStreamWriter writer = null;
		if (null == encoding) {
			writer = new OutputStreamWriter(new FileOutputStream(fileName));
		} else {
			writer = new OutputStreamWriter(new FileOutputStream(fileName), encoding);
		}
		writer.write(str);
		writer.close();
	}
}
