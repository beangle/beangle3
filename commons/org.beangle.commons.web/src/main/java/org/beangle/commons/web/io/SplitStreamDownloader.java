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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.beangle.commons.http.mime.MimeTypeProvider;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.time.Stopwatch;

/**
 * SplitStreamDownloader
 * <p>
 * Split download senario like this:
 * <li>Server first response:200</li>
 * 
 * <pre>
 * Content-Length=106786028
 * Accept-Ranges=bytes
 * </pre>
 * 
 * <li>Client send request :</li>
 * 
 * <pre>
 * Range: bytes=2000070-106786027
 * </pre>
 * 
 * <li>Server send next response:206</li>
 * 
 * <pre>
 * Content-Length=106786028
 * Content-Range=bytes 2000070-106786027/106786028
 * </pre>
 * 
 * @author chaostone
 * @since 2.4
 */
public class SplitStreamDownloader extends DefaultStreamDownloader {

  public SplitStreamDownloader() {
    super();
  }

  public SplitStreamDownloader(MimeTypeProvider mimeTypeProvider) {
    super(mimeTypeProvider);
  }

  @Override
  public void download(HttpServletRequest request, HttpServletResponse response, InputStream input,
      String name, String display) {
    String attach = getAttachName(name, display);
    response.reset();
    addContent(request, response, attach);
    response.setHeader("Accept-Ranges", "bytes");
    response.setHeader("connection", "Keep-Alive");
    int length = 0;
    long start = 0L;
    long begin = 0L;
    long stop = 0L;
    Stopwatch watch = new Stopwatch().start();
    try {
      length = input.available();
      stop = length - 1;
      response.setContentLength(length);
      String rangestr = request.getHeader("Range");
      if (null != rangestr) {
        String[] readlength = Strings.substringAfter(rangestr, "bytes=").split("-");
        start = Long.parseLong(readlength[0]);
        if (readlength.length > 1 && Strings.isNotEmpty(readlength[1])) {
          stop = Long.parseLong(readlength[1]);
        }
        if (start != 0) {
          response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
          String crange = "bytes " + start + "-" + stop + "/" + length;
          response.setHeader("Content-Range", crange);
        }
      }
      OutputStream output = response.getOutputStream();
      input.skip(start);
      begin = start;
      int size = 4 * 1024;
      byte[] buffer = new byte[size];
      for (int step = maxStep(start, stop, size); step > 0; step = maxStep(start, stop, size)) {
        int readed = input.read(buffer, 0, step);
        if (readed == -1) break;
        output.write(buffer, 0, readed);
        start += readed;
      }
    } catch (IOException e) {
    } catch (Exception e) {
      logger.warn("download file error " + attach, e);
    } finally {
      IOUtils.closeQuietly(input);
      if (logger.isDebugEnabled()) {
        String percent = null;
        if (length == 0) {
          percent = "100%";
        } else {
          percent = ((int) (((start - begin) * 1.0 / length) * 10000)) / 100.0f + "%";
        }
        long time = watch.elapsedMillis();
        int rate = 0;
        if (start - begin > 0) {
          rate = (int) (((start - begin) * 1.0 / time * 1000) / 1024);
        }
        logger.debug("{}({}-{}/{}) download {}[{}] in {} ms with {} KB/s",
            array(attach, begin, stop, length, start - begin, percent, time, rate));
      }
    }
  }

  private Object[] array(Object... objects) {
    return objects;
  }

  public int maxStep(long start, long stop, int bufferSize) {
    if (stop - start + 1 >= bufferSize) {
      return bufferSize;
    } else {
      return (int) (stop - start + 1);
    }
  }

}
