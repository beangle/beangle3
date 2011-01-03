/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.agent;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;

/**
 * Enum constants for most common operating systems.
 * 
 * @author chaostone
 */
public enum OsCategory {

	WINDOWS("Windows", "Windows NT 6.1->7", "Windows NT 6->Vista", "Windows NT 5.0->2000",
			"Windows NT 5->XP", "Win98->98", "Windows 98->98", "Windows Phone OS 7->Mobile 7",
			"Windows CE->Mobile", "Windows"),

	// CamelHttpStream is being used by Evolution, an email client for Linux\
	LINUX("Linux", "Fedora/(\\S*)->$1","Linux", "CamelHttpStream"),

	ANDROID("Android", "Android 2->2.x", "GT-P1000->2.x Tablet", "SCH-I800->2.x Tablet",
			"Android 1->1.x", "Android"),

	WEBOS("WebOS", "webOS"),

	PALM("PalmOS", "Palm"),

	IOS("iOS", "iPhone OS 4->4 (iPhone)", "like Mac OS X"),

	MAC_OS("Mac OS", "iPad->(iPad)", "iPhone->(iPhone)", "iPod->(iPod)", "Mac OS X->X",
			"CFNetwork->X", "Mac"),

	MAEMO("Maemo", "Maemo"),

	SYMBIAN("Symbian OS", "SymbianOS/9->9.x", "Series60/3->9.x", "SymbianOS/8->8.x",
			"Series60/2.6->8.x", "Series60/2.8->8.x", "SymbianOS/7->7.x", "SymbianOS/6->6.x",
			"Symbian", "Series60"),

	SERIES40("Series 40", "Nokia6300"),

	SONY_ERICSSON("Sony Ericsson", "SonyEricsson"),

	SUN_OS("SunOS", "SunOS"),

	PSP("Sony Playstation", "Playstation"),

	WII("Nintendo Wii", "Wii"),

	BLACKBERRY("BlackBerryOS", "BlackBerry", "Version/6->6"),

	UNKNOWN("Unknown", new String[0]);

	private final String name;

	@SuppressWarnings("unchecked")
	private final Map<Pattern, String> versionMap = new LinkedMap();

	private OsCategory(String name, String... versions) {
		this.name = name;
		for (String version : versions) {
			String matcheTarget = version;
			String versionNum = "";
			if (StringUtils.contains(version, "->")) {
				matcheTarget = StringUtils.substringBefore(version, "->");
				versionNum = StringUtils.substringAfter(version, "->");
			}
			versionMap.put(Pattern.compile(matcheTarget), versionNum);
		}
	}

	public String match(String agentString) {
		for (Map.Entry<Pattern, String> entry : versionMap.entrySet()) {
			Matcher m = entry.getKey().matcher(agentString);
			if (m.find()) {
				StringBuffer sb = new StringBuffer();
				m.appendReplacement(sb, entry.getValue());
				sb.delete(0, m.start());
				return sb.toString();
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}
}
