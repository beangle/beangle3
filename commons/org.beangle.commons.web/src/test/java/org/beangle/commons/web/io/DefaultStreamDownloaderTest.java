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
package org.beangle.commons.web.io;

import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.codec.net.URLCodec;
import org.beangle.commons.http.mime.MimeTypeProvider;
import org.beangle.commons.lang.ClassLoaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class DefaultStreamDownloaderTest {

  StreamDownloader streamDownloader = new DefaultStreamDownloader(new MimeTypeProvider());

  public void download() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    URL testDoc = ClassLoaders.getResource("download.txt",getClass());
    streamDownloader.download(request, response, testDoc, null);
  }

  public void ecode() throws Exception {
    String value = "汉字-english and .;";
    String ecodedValue = new URLCodec().encode(value, "utf-8");
    String orginValue = URLDecoder.decode(ecodedValue, "utf-8");
    Assert.assertEquals(orginValue, value);
  }

  public void ecode2() throws Exception {
    String value = "汉字-english and .;";
    String encodedValue = new org.apache.commons.codec.net.BCodec().encode(value);
    String orginValue = new org.apache.commons.codec.net.BCodec().decode(encodedValue);
    Assert.assertEquals(orginValue, value);
  }
}
