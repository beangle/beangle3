/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.transfer.io;

/**
 * 导出writer注册表
 * 
 * @author chaostone
 */
public interface TransferFormatRegistry {

	public Writer getWriter(String format);

	public Reader getReader(String format);

	public <T extends Writer> void registerWriter(String format, Class<T> writerClazz);

	public <T extends Reader> void registerReader(String format, Class<T> readerClazz);

	public void unRegisterWriter(String format);

	public void unRegisterReader(String format);
}
