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
package org.beangle.commons.transfer.exporter;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.beangle.commons.transfer.Transfer;
import org.beangle.commons.transfer.TransferListener;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.io.ItemWriter;
import org.beangle.commons.transfer.io.TransferFormat;
import org.beangle.commons.transfer.io.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Abstract AbstractItemExporter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class AbstractItemExporter implements Exporter {

  /** Constant <code>logger</code> */
  protected static final Logger logger = LoggerFactory.getLogger(AbstractItemExporter.class);

  /** 数据读取对象 */
  protected ItemWriter writer;

  /** 转换结果 */
  protected TransferResult transferResult;

  /** 监听器列表 */
  protected Vector<TransferListener> listeners = new Vector<TransferListener>();

  /** 成功记录数 */
  protected int success = 0;

  /** 失败记录数 */
  protected int fail = 0;

  /** 下一个要输出的位置 */
  protected int index = -1;

  /** 转换的数据集 */
  protected Collection<?> datas;

  protected Iterator<?> iter;

  private Object current;

  /** 转换上下文 */
  protected Context context;

  /** {@inheritDoc} */
  public Transfer addListener(TransferListener listener) {
    listeners.add(listener);
    listener.setTransfer(this);
    return this;
  }

  /**
   * <p>
   * Getter for the field <code>current</code>.
   * </p>
   * 
   * @return a {@link java.lang.Object} object.
   */
  public Object getCurrent() {
    return current;
  }

  /**
   * <p>
   * Getter for the field <code>fail</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getFail() {
    return fail;
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
   * getTranferIndex.
   * </p>
   * 
   * @return a int.
   */
  public int getTranferIndex() {
    return index;
  }

  /** {@inheritDoc} */
  public void transfer(TransferResult tr) {
    this.transferResult = tr;
    tr.setTransfer(this);
    if (!beforeExport()) return;
    for (final TransferListener listener : listeners) {
      listener.onStart(tr);
    }
    while (hasNext()) {
      next();
      int errors = tr.errors();
      // 实体转换开始
      for (final TransferListener listener : listeners) {
        listener.onItemStart(tr);
      }
      long transferItemStart = System.currentTimeMillis();
      // 进行转换
      transferItem();
      // 如果导出过程中没有错误，将成功记录数增一
      if (tr.errors() == errors) {
        this.success++;
      } else {
        this.fail++;
      }
      logger.debug("tranfer item:{}  take time:{}", String.valueOf(getTranferIndex()),
          String.valueOf(System.currentTimeMillis() - transferItemStart));
      // 实体转换结束
      for (final TransferListener listener : listeners) {
        listener.onItemFinish(tr);
      }
    }
    for (final TransferListener listener : listeners) {
      listener.onFinish(tr);
    }
    // 告诉输出者,输出完成
    writer.close();
  }

  /**
   * 导出准备和检查。
   * 
   * @return false 不能开始导出
   */
  protected boolean beforeExport() {
    return true;
  }

  /**
   * <p>
   * transferItem.
   * </p>
   */
  public void transferItem() {
    if (null == getCurrent()) return;
    writer.write(getCurrent());
  }

  /**
   * <p>
   * next.
   * </p>
   */
  protected void next() {
    index++;
    current = iter.next();
  }

  /**
   * <p>
   * hasNext.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean hasNext() {
    return iter.hasNext();
  }

  /**
   * <p>
   * getFormat.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public TransferFormat getFormat() {
    return writer.getFormat();
  }

  /** {@inheritDoc} */
  public void setContext(Context context) {
    Collection<?> items = (Collection<?>) context.getDatas().get("items");
    if (null != items) {
      datas = items;
      iter = datas.iterator();
    }
    this.context = context;
  }

  /**
   * <p>
   * getDataName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getDataName() {
    return null;
  }

  /** {@inheritDoc} */
  public void setWriter(Writer writer) {
    if (writer instanceof ItemWriter) {
      this.writer = (ItemWriter) writer;
    }
  }

  /**
   * <p>
   * Getter for the field <code>writer</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.transfer.io.ItemWriter} object.
   */
  public ItemWriter getWriter() {
    return writer;
  }

}
