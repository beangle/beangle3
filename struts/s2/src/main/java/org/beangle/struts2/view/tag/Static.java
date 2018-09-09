package org.beangle.struts2.view.tag;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

public class Static {

  public static Static Default = new Static();
  static {
    Default.resources.put("bui", "0.0.5");
    Default.resources.put("bootstrap", "3.3.7");
    Default.resources.put("jquery", "1.10.2");
    Default.resources.put("my97", "4.8");
    Default.resources.put("kindeditor", "4.1.11");
  }

  private Map<String, String> resources = CollectUtils.newHashMap();

  public String url(String base, String bundle, String file) {
    String fileName = null;
    if (Strings.isEmpty(file)) {
      fileName = "";
    } else {
      fileName = (file.charAt(0) == '/') ? file : ("/" + file);
    }
    String version = resources.get(bundle);
    return base + "/" + bundle + "/" + version + fileName;
  }

}
