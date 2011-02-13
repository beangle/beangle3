/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.archiver.zip;

/**
 * Info-ZIP Unicode Comment Extra Field (0x6375):
 * <p>
 * Stores the UTF-8 version of the file comment as stored in the central directory header.
 * </p>
 * 
 * <pre>
 *         Value         Size        Description
 *         -----         ----        -----------
 *  (UCom) 0x6375        Short       tag for this extra block type ("uc")
 *         TSize         Short       total data size for this block
 *         Version       1 byte      version of this extra field, currently 1
 *         ComCRC32      4 bytes     Comment Field CRC32 Checksum
 *         UnicodeCom    Variable    UTF-8 version of the entry comment
 * </pre>
 */
public class UnicodeCommentExtraField extends AbstractUnicodeExtraField {

	public static final ZipShort UCOM_ID = new ZipShort(0x6375);

	public UnicodeCommentExtraField() {
	}

	/**
	 * Assemble as unicode comment extension from the name given as text as well
	 * as the encoded bytes actually written to the archive.
	 * 
	 * @param text
	 *            The file name
	 * @param bytes
	 *            the bytes actually written to the archive
	 * @param off
	 *            The offset of the encoded comment in <code>bytes</code>.
	 * @param len
	 *            The length of the encoded comment or comment in <code>bytes</code>.
	 */
	public UnicodeCommentExtraField(String text, byte[] bytes, int off, int len) {
		super(text, bytes, off, len);
	}

	/**
	 * Assemble as unicode comment extension from the comment given as text as
	 * well as the bytes actually written to the archive.
	 * 
	 * @param comment
	 *            The file comment
	 * @param bytes
	 *            the bytes actually written to the archive
	 */
	public UnicodeCommentExtraField(String comment, byte[] bytes) {
		super(comment, bytes);
	}

	public ZipShort getHeaderId() {
		return UCOM_ID;
	}

}
