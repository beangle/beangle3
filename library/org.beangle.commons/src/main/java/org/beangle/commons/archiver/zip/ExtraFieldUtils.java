/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.archiver.zip;

import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;

import org.beangle.commons.collection.CollectUtils;

/**
 * ZipExtraField related methods
 */
// CheckStyle:HideUtilityClassConstructorCheck OFF (bc)
@SuppressWarnings("unchecked")
public class ExtraFieldUtils {

	private static final int WORD = 4;

	/**
	 * Static registry of known extra fields.
	 * 
	 * @since 1.1
	 */
	private static final Map implementations;

	static {
		implementations = CollectUtils.newHashMap();
		register(AsiExtraField.class);
		register(JarMarker.class);
		register(UnicodePathExtraField.class);
		register(UnicodeCommentExtraField.class);
	}

	/**
	 * Register a ZipExtraField implementation.
	 * <p>
	 * The given class must have a no-arg constructor and implement the {@link ZipExtraField
	 * ZipExtraField interface}.
	 * </p>
	 * 
	 * @param c
	 *            the class to register
	 * @since 1.1
	 */
	public static void register(Class c) {
		try {
			ZipExtraField ze = (ZipExtraField) c.newInstance();
			implementations.put(ze.getHeaderId(), c);
		} catch (ClassCastException cc) {
			throw new RuntimeException(c + " doesn\'t implement ZipExtraField");
		} catch (InstantiationException ie) {
			throw new RuntimeException(c + " is not a concrete class");
		} catch (IllegalAccessException ie) {
			throw new RuntimeException(c + "\'s no-arg constructor is not public");
		}
	}

	/**
	 * Create an instance of the approriate ExtraField, falls back to {@link UnrecognizedExtraField
	 * UnrecognizedExtraField}.
	 * 
	 * @param headerId
	 *            the header identifier
	 * @return an instance of the appropiate ExtraField
	 * @exception InstantiationException
	 *                if unable to instantiate the class
	 * @exception IllegalAccessException
	 *                if not allowed to instatiate the class
	 * @since 1.1
	 */
	public static ZipExtraField createExtraField(ZipShort headerId) throws InstantiationException,
			IllegalAccessException {
		Class<?> c = (Class<?>) implementations.get(headerId);
		if (c != null) { return (ZipExtraField) c.newInstance(); }
		UnrecognizedExtraField u = new UnrecognizedExtraField();
		u.setHeaderId(headerId);
		return u;
	}

	/**
	 * Split the array into ExtraFields and populate them with the given data as
	 * local file data.
	 * 
	 * @param data
	 *            an array of bytes as it appears in local file data
	 * @return an array of ExtraFields
	 * @throws ZipException
	 *             on error
	 */
	public static ZipExtraField[] parse(byte[] data) throws ZipException {
		return parse(data, true);
	}

	/**
	 * Split the array into ExtraFields and populate them with the given data.
	 * 
	 * @param data
	 *            an array of bytes
	 * @param local
	 *            whether data originates from the local file data or the
	 *            central directory
	 * @return an array of ExtraFields
	 * @since 1.1
	 * @throws ZipException
	 *             on error
	 */
	public static ZipExtraField[] parse(byte[] data, boolean local) throws ZipException {
		List<ZipExtraField> v = CollectUtils.newArrayList();
		int start = 0;
		while (start <= data.length - WORD) {
			ZipShort headerId = new ZipShort(data, start);
			int length = (new ZipShort(data, start + 2)).getValue();
			if (start + WORD + length > data.length) { throw new ZipException("data starting at " + start
					+ " is in unknown format"); }
			try {
				ZipExtraField ze = createExtraField(headerId);
				if (local || !(ze instanceof CentralDirectoryParsingZipExtraField)) {
					ze.parseFromLocalFileData(data, start + WORD, length);
				} else {
					((CentralDirectoryParsingZipExtraField) ze).parseFromCentralDirectoryData(data, start
							+ WORD, length);
				}
				v.add(ze);
			} catch (InstantiationException ie) {
				throw new ZipException(ie.getMessage());
			} catch (IllegalAccessException iae) {
				throw new ZipException(iae.getMessage());
			}
			start += (length + WORD);
		}

		ZipExtraField[] result = new ZipExtraField[v.size()];
		return v.toArray(result);
	}

	/**
	 * Merges the local file data fields of the given ZipExtraFields.
	 * 
	 * @param data
	 *            an array of ExtraFiles
	 * @return an array of bytes
	 * @since 1.1
	 */
	public static byte[] mergeLocalFileDataData(ZipExtraField[] data) {
		int sum = WORD * data.length;
		for (int i = 0; i < data.length; i++) {
			sum += data[i].getLocalFileDataLength().getValue();
		}
		byte[] result = new byte[sum];
		int start = 0;
		for (int i = 0; i < data.length; i++) {
			System.arraycopy(data[i].getHeaderId().getBytes(), 0, result, start, 2);
			System.arraycopy(data[i].getLocalFileDataLength().getBytes(), 0, result, start + 2, 2);
			byte[] local = data[i].getLocalFileDataData();
			System.arraycopy(local, 0, result, start + WORD, local.length);
			start += (local.length + WORD);
		}
		return result;
	}

	/**
	 * Merges the central directory fields of the given ZipExtraFields.
	 * 
	 * @param data
	 *            an array of ExtraFields
	 * @return an array of bytes
	 * @since 1.1
	 */
	public static byte[] mergeCentralDirectoryData(ZipExtraField[] data) {
		int sum = WORD * data.length;
		for (int i = 0; i < data.length; i++) {
			sum += data[i].getCentralDirectoryLength().getValue();
		}
		byte[] result = new byte[sum];
		int start = 0;
		for (int i = 0; i < data.length; i++) {
			System.arraycopy(data[i].getHeaderId().getBytes(), 0, result, start, 2);
			System.arraycopy(data[i].getCentralDirectoryLength().getBytes(), 0, result, start + 2, 2);
			byte[] local = data[i].getCentralDirectoryData();
			System.arraycopy(local, 0, result, start + WORD, local.length);
			start += (local.length + WORD);
		}
		return result;
	}
}
