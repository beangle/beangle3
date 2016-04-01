/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.commons.transfer.importer;

import java.util.Locale;
import java.util.Vector;

import org.beangle.commons.transfer.Transfer;
import org.beangle.commons.transfer.TransferListener;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.io.Reader;
import org.beangle.commons.transfer.io.TransferFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 导入的抽象和缺省实现
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class AbstractImporter implements Importer {
  private final static Logger logger = LoggerFactory.getLogger(AbstractImporter.class);

  /**
   * 数据读取对象
   */
  protected Reader reader;

  /**
   * 转换结果
   */
  protected TransferResult transferResult;

  /**
   * 导入准备
   */
  protected ImportPrepare prepare;
  /**
   * 监听器列表
   */
  protected Vector<TransferListener> listeners = new Vector<TransferListener>();

  /**
   * 成功记录数
   */
  protected int success = 0;

  /**
   * 失败记录数
   */
  protected int fail = 0;

  /**
   * 下一个要读取的位置
   */
  protected int index = 0;

  /**
   * {@inheritDoc} 进行转换
   */
  public void transfer(TransferResult tr) {
    this.transferResult = tr;
    this.transferResult.setTransfer(this);
    long transferStartAt = System.currentTimeMillis();
    try {
      prepare.prepare(this);
    } catch (Exception e) {
      // 预导入发生位置错误，错误信息已经记录在tr了
      return;
    }
    for (final TransferListener listener : listeners) {
      listener.onStart(tr);
    }
    while (read()) {
      long transferItemStart = System.currentTimeMillis();
      index++;
      beforeImportItem();
      if (!isDataValid()) continue;
      int errors = tr.errors();
      // 实体转换开始
      for (final TransferListener listener : listeners) {
        listener.onItemStart(tr);
      }
      // 如果转换前已经存在错误,则不进行转换
      if (tr.errors() > errors) continue;
      // 进行转换
      transferItem();
      // 实体转换结束
      for (final TransferListener listener : listeners) {
        listener.onItemFinish(tr);
      }
      // 如果导入过程中没有错误，将成功记录数增一
      if (tr.errors() == errors) this.success++;
      else this.fail++;

      if (logger.isDebugEnabled()) {
        logger.debug("importer item:{} take time: {}", getTranferIndex(),
            (System.currentTimeMillis() - transferItemStart));
      }
    }
    for (final TransferListener listener : listeners) {
      listener.onFinish(tr);
    }
    reader.close();
    logger.debug("importer elapse: {}", (System.currentTimeMillis() - transferStartAt));
  }

  /**
   * <p>
   * Getter for the field <code>fail</code>.
   * </p>
   */
  public int getFail() {
    return fail;
  }

  /**
   * <p>
   * Getter for the field <code>success</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getSuccess() {
    return success;
  }

  /**
   * <p>
   * Getter for the field <code>reader</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.transfer.io.Reader} object.
   */
  public Reader getReader() {
    return reader;
  }

  public void setReader(Reader reader) {
    this.reader = reader;
  }

  /**
   * <p>
   * ignoreNull.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean ignoreNull() {
    return true;
  }

  /**
   * <p>
   * getLocale.
   * </p>
   * 
   * @return a {@link java.util.Locale} object.
   */
  public Locale getLocale() {
    return Locale.getDefault();
  }

  /**
   * <p>
   * getFormat.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public TransferFormat getFormat() {
    return reader.getFormat();
  }

  /**
   * <p>
   * getTranferIndex.
   * </p>
   * 
   * @return a int.
   */
  public int getTranferIndex() {
    return index;
  }

  public Transfer addListener(TransferListener listener) {
    listeners.add(listener);
    listener.setTransfer(this);
    return this;
  }

  /**
   * <p>
   * Getter for the field <code>prepare</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.transfer.importer.ImportPrepare} object.
   */
  public ImportPrepare getPrepare() {
    return prepare;
  }

  public void setPrepare(ImportPrepare prepare) {
    this.prepare = prepare;
  }

  /**
   * <p>
   * beforeImportItem.
   * </p>
   */
  protected void beforeImportItem() {
  };
}
