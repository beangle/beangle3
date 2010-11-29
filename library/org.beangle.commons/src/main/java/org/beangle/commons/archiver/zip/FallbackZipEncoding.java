/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.archiver.zip;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * A fallback ZipEncoding, which uses a java.io means to encode names.
 * <p>
 * This implementation is not favorable for encodings other than utf-8, because
 * java.io encodes unmappable character as question marks leading to unreadable
 * ZIP entries on some operating systems.
 * </p>
 * <p>
 * Furthermore this implementation is unable to tell, whether a given name can
 * be safely encoded or not.
 * </p>
 * <p>
 * This implementation acts as a last resort implementation, when neither
 * {@link Simple8BitZipEnoding} nor {@link NioZipEncoding} is available.
 * </p>
 * <p>
 * The methods of this class are reentrant.
 * </p>
 */
class FallbackZipEncoding implements ZipEncoding {
	private final String charset;

	/**
	 * Construct a fallback zip encoding, which uses the platform's default
	 * charset.
	 */
	public FallbackZipEncoding() {
		this.charset = null;
	}

	/**
	 * Construct a fallback zip encoding, which uses the given charset.
	 * 
	 * @param charset
	 *            The name of the charset or <code>null</code> for the
	 *            platform's default character set.
	 */
	public FallbackZipEncoding(String charset) {
		this.charset = charset;
	}

	/**
	 * @see org.apache.tools.zip.ZipEncoding#canEncode(java.lang.String)
	 */
	public boolean canEncode(String name) {
		return true;
	}

	/**
	 * @see org.apache.tools.zip.ZipEncoding#encode(java.lang.String)
	 */
	public ByteBuffer encode(String name) throws IOException {
		if (this.charset == null) {
			return ByteBuffer.wrap(name.getBytes());
		} else {
			return ByteBuffer.wrap(name.getBytes(this.charset));
		}
	}

	/**
	 * @see org.apache.tools.zip.ZipEncoding#decode(byte[])
	 */
	public String decode(byte[] data) throws IOException {
		if (this.charset == null) {
			return new String(data);
		} else {
			return new String(data, this.charset);
		}
	}
}
