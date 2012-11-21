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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;

/**
 * System information
 * 
 * @author chaostone
 * @since 3.0.2
 */
public final class SystemInfo {

  private static Os os;
  private static User user;
  private static Java java;
  private static Jvm jvm;
  private static JavaSpec javaSpec;
  private static JvmSpec jvmSpec;
  private static JavaRuntime javaRuntime;
  static final Set<String> usedProperties;

  static {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    Map<String, String> origin = new HashMap(System.getProperties());
    Map<String, String> properties = CollectUtils.newHashMap(origin);
    os = new Os(properties);
    user = new User(properties);
    java = new Java(properties);
    jvm = new Jvm(properties);
    javaSpec = new JavaSpec(properties);
    jvmSpec = new JvmSpec(properties);
    javaRuntime = new JavaRuntime(properties);
    origin.keySet().removeAll(properties.keySet());
    usedProperties = new HashSet<String>(origin.keySet());
  }

  public static Set<String> getUsedproperties() {
    return usedProperties;
  }

  public static Host getHost() {
    return new Host();
  }

  /**
   * Return Os info
   */
  public static Os getOs() {
    return os;
  }

  /**
   * Return User info
   */
  public static User getUser() {
    return user;
  }

  public static Java getJava() {
    return java;
  }

  public static Jvm getJvm() {
    return jvm;
  }

  public static JavaSpec getJavaSpec() {
    return javaSpec;
  }

  public static JvmSpec getJvmSpec() {
    return jvmSpec;
  }

  public static JavaRuntime getJavaRuntime() {
    return javaRuntime;
  }

  public static final class Host {
    String hostname;
    final Map<String, List<String>> addresses = CollectUtils.newHashMap();

    Host() {
      try {
        InetAddress localhost = InetAddress.getLocalHost();
        hostname = localhost.getHostName();
      } catch (UnknownHostException e) {
        hostname = "localhost";
      }
      // IPs
      try {
        for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) {
          NetworkInterface networkInterface = e.nextElement();
          String name = networkInterface.getDisplayName();
          List<String> addrs = CollectUtils.newArrayList();
          for (Enumeration<InetAddress> e2 = networkInterface.getInetAddresses(); e2.hasMoreElements();) {
            addrs.add(e2.nextElement().getHostAddress());
          }
          addresses.put(name, addrs);
        }
      } catch (Exception e) {
      }
    }

    public String getHostname() {
      return hostname;
    }

    public Map<String, List<String>> getAddresses() {
      return addresses;
    }

  }

  public static final class User {
    final String name;
    final String home;
    final String language;
    final String country;

    User(Map<String, String> properties) {
      name = properties.remove("user.name");
      home = properties.remove("user.home");
      language = properties.remove("user.language");
      String country = properties.remove("user.country");
      if (null == country) country = properties.remove("user.region");
      this.country = country;
    }

    public String getName() {
      return name;
    }

    public String getHome() {
      return home;
    }

    public String getLanguage() {
      return language;
    }

    public String getCountry() {
      return country;
    }

  }

  public static final class JavaRuntime {
    final String name;
    final String version;
    final String home;
    final String extDirs;
    final String endorsedDirs;
    final String classpath;
    final String classVersion;
    final String libraryPath;
    final String tmpDir;
    final String fileEncoding;

    JavaRuntime(Map<String, String> properties) {
      name = properties.remove("java.runtime.name");
      version = properties.remove("java.runtime.version");
      home = properties.remove("java.home");
      extDirs = properties.remove("java.ext.dirs");
      endorsedDirs = properties.remove("java.endorsed.dirs");
      classpath = properties.remove("java.class.path");
      classVersion = properties.remove("java.class.version");
      libraryPath = properties.remove("java.library.path");
      tmpDir = properties.remove("java.io.tmpdir");
      fileEncoding = properties.remove("file.encoding");
    }

    public String getName() {
      return name;
    }

    public String getVersion() {
      return version;
    }

    public String getHome() {
      return home;
    }

    public String getExtDirs() {
      return extDirs;
    }

    public String getEndorsedDirs() {
      return endorsedDirs;
    }

    public String getClasspath() {
      return classpath;
    }

    public String getClassVersion() {
      return classVersion;
    }

    public String getLibraryPath() {
      return libraryPath;
    }

    public String getTmpDir() {
      return tmpDir;
    }

    public String getFileEncoding() {
      return fileEncoding;
    }

  }

  public static final class JvmSpec {
    final String name;
    final String version;
    final String vendor;

    JvmSpec(Map<String, String> properties) {
      name = properties.remove("java.vm.specification.name");
      version = properties.remove("java.vm.specification.version");
      vendor = properties.remove("java.vm.specification.vendor");
    }

    public String getName() {
      return name;
    }

    public String getVersion() {
      return version;
    }

    public String getVendor() {
      return vendor;
    }

  }

  public static final class JavaSpec {
    final String name;
    final String version;
    final String vendor;

    JavaSpec(Map<String, String> properties) {
      name = properties.remove("java.specification.name");
      version = properties.remove("java.specification.version");
      vendor = properties.remove("java.specification.vendor");
    }

    public String getName() {
      return name;
    }

    public String getVersion() {
      return version;
    }

    public String getVendor() {
      return vendor;
    }

  }

  public static final class Jvm {
    final String name;
    final String version;
    final String vendor;
    final String info;

    Jvm(Map<String, String> properties) {
      name = properties.remove("java.vm.name");
      version = properties.remove("java.vm.version");
      vendor = properties.remove("java.vm.vendor");
      info = properties.remove("java.vm.info");
    }

    public String getName() {
      return name;
    }

    public String getVersion() {
      return version;
    }

    public String getVendor() {
      return vendor;
    }

    public String getInfo() {
      return info;
    }

  }

  public static final class Java {
    final String version;
    final String vendor;
    final String vendorUrl;

    Java(Map<String, String> properties) {
      version = properties.remove("java.version");
      vendor = properties.remove("java.vendor");
      vendorUrl = properties.remove("java.vendor.url");
    }

    public String getVersion() {
      return version;
    }

    public String getVendor() {
      return vendor;
    }

    public String getVendorUrl() {
      return vendorUrl;
    }

  }

  public static final class Os {
    final String name;
    final String version;
    final String arch;

    final String fileSeparator;
    final String lineSeparator;
    final String pathSeparator;

    Os(Map<String, String> properties) {
      name = properties.remove("os.name");
      version = properties.remove("os.version");
      arch = properties.remove("os.arch");

      fileSeparator = properties.remove("file.separator");
      lineSeparator = properties.remove("line.separator");
      pathSeparator = properties.remove("path.separator");
    }

    public String getName() {
      return name;
    }

    public String getVersion() {
      return version;
    }

    public String getArch() {
      return arch;
    }

    public String getFileSeparator() {
      return fileSeparator;
    }

    public String getLineSeparator() {
      return lineSeparator;
    }

    public String getPathSeparator() {
      return pathSeparator;
    }

  }

}
