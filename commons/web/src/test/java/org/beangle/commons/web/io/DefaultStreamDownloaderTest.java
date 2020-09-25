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
package org.beangle.commons.web.io;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.beangle.commons.codec.net.BCoder;
import org.beangle.commons.http.mime.MimeTypeProvider;
import org.beangle.commons.lang.ClassLoaders;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class DefaultStreamDownloaderTest {

  StreamDownloader streamDownloader = new DefaultStreamDownloader(new MimeTypeProvider());

  public void download() throws IOException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
      OutputStream outputStream = new ByteArrayOutputStream();

      public void write(int b) throws IOException {
        outputStream.write(b);
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setWriteListener(WriteListener writeListener) {

      }

    });
    URL testDoc = ClassLoaders.getResource("download.txt", getClass());
    streamDownloader.download(request, response, testDoc, null);
  }

  public void ecode() throws Exception {
    String value = "汉字-english and .;";
    String ecodedValue = URLEncoder.encode(value, "utf-8");
    String orginValue = URLDecoder.decode(ecodedValue, "utf-8");
    Assert.assertEquals(orginValue, value);
  }

  public void ecode2() throws Exception {
    String value = "汉字-english and .;";
    String encodedValue = new BCoder().encode(value);
    String orginValue = new BCoder().decode(encodedValue);
    Assert.assertEquals(orginValue, value);
  }

}
