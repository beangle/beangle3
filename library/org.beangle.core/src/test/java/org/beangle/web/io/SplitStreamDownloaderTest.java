/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.io;

import java.io.File;
import java.net.URL;

import org.beangle.http.mime.MimeTypeProvider;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class SplitStreamDownloaderTest {

	StreamDownloader streamDownloader = new SplitStreamDownloader(new MimeTypeProvider());

	public void download() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		URL testDoc = DefaultStreamDownloaderTest.class.getResource("/download.txt");
		streamDownloader.download(request, response, testDoc, null);
		Assert.assertEquals(response.getStatus(), 200);
		Assert.assertEquals(response.getHeader("Accept-Ranges"), "bytes");

		Assert.assertEquals(response.getContentLength(), 59);

		File file = new File(testDoc.toURI());
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		request.addHeader("Range", "bytes=5-12");
		streamDownloader.download(request, response, testDoc, null);
		Assert.assertEquals(response.getStatus(), 206);
		Assert.assertEquals(response.getHeader("Content-Range"), "bytes 5-12/" + file.length());
		String content = response.getContentAsString();
		Assert.assertEquals(content, "document");
	}
}
