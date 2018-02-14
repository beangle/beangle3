/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.notification.mail;

import java.util.Collections;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

public class MimeUtils {

  public static final List<InternetAddress> parseAddress(String address, String encoding) {
    if (Strings.isEmpty(address)) return Collections.emptyList();
    try {
      InternetAddress[] parsed = InternetAddress.parse(address);
      List<InternetAddress> returned = CollectUtils.newArrayList();
      for (InternetAddress raw : parsed) {
        returned.add((encoding != null ? new InternetAddress(raw.getAddress(), raw.getPersonal(), encoding)
            : raw));
      }
      return returned;
    } catch (Exception ex) {
      throw new RuntimeException("Failed to parse embedded personal name to correct encoding", ex);
    }
  }
}
