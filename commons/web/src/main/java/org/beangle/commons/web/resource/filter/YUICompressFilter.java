package org.beangle.commons.web.resource.filter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.lang.Charsets;
import org.beangle.commons.web.resource.ProcessChain;
import org.beangle.commons.web.resource.ProcessContext;
import org.beangle.commons.web.resource.ResourceFilter;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class YUICompressFilter implements ResourceFilter {

  private int lineBreak = 4000;

  @Override
  public void filter(ProcessContext context, HttpServletRequest request, HttpServletResponse response,
      ProcessChain chain) throws IOException {
    chain.process(context, request, response);
    if ("no".equals(request.getParameter("compress"))) return;
    if (response.getContentType().equals("text/javascript")) {
      for (ProcessContext.Resource res : context.resources) {
        if (null != res.data && !res.path.endsWith("min.js")) {
          StringReader sr = new StringReader(new String(res.data, Charsets.UTF_8));
          JavaScriptCompressor compressor = new JavaScriptCompressor(sr, null);
          StringWriter writer = new StringWriter(res.data.length);
          compressor.compress(writer, lineBreak, false, false, true, true);
          res.data = writer.getBuffer().toString().getBytes();
        }
      }
    } else if (response.getContentType().equals("text/css")) {
      for (ProcessContext.Resource res : context.resources) {
        if (null != res.data) {
          StringReader sr = new StringReader(new String(res.data, Charsets.UTF_8));
          CssCompressor compressor = new CssCompressor(sr);
          StringWriter writer = new StringWriter(res.data.length);
          compressor.compress(writer, lineBreak);
          res.data = writer.getBuffer().toString().getBytes();
        }
      }
    }

  }

  public void setLineBreak(int lineBreak) {
    this.lineBreak = lineBreak;
  }

}
