/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.archiver.zip;

import java.util.zip.ZipException;

/**
 * General format of extra field data.
 * <p>
 * Extra fields usually appear twice per file, once in the local file data and once in the central
 * directory. Usually they are the same, but they don't have to be.
 * {@link java.util.zip.ZipOutputStream java.util.zip.ZipOutputStream} will only use the local file
 * data in both places.
 * </p>
 */
public interface ZipExtraField {

	/**
	 * The Header-ID.
	 * 
	 * @return the header id
	 * @since 1.1
	 */
	ZipShort getHeaderId();

	/**
	 * Length of the extra field in the local file data - without Header-ID or
	 * length specifier.
	 * 
	 * @return the length of the field in the local file data
	 * @since 1.1
	 */
	ZipShort getLocalFileDataLength();

	/**
	 * Length of the extra field in the central directory - without Header-ID or
	 * length specifier.
	 * 
	 * @return the length of the field in the central directory
	 * @since 1.1
	 */
	ZipShort getCentralDirectoryLength();

	/**
	 * The actual data to put into local file data - without Header-ID or length
	 * specifier.
	 * 
	 * @return the data
	 * @since 1.1
	 */
	byte[] getLocalFileDataData();

	/**
	 * The actual data to put into central directory - without Header-ID or
	 * length specifier.
	 * 
	 * @return the data
	 * @since 1.1
	 */
	byte[] getCentralDirectoryData();

	/**
	 * Populate data from this array as if it was in local file data.
	 * 
	 * @param data
	 *            an array of bytes
	 * @param offset
	 *            the start offset
	 * @param length
	 *            the number of bytes in the array from offset
	 * @since 1.1
	 * @throws ZipException
	 *             on error
	 */
	void parseFromLocalFileData(byte[] data, int offset, int length) throws ZipException;
}
