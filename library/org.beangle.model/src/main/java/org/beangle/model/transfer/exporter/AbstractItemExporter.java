/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.exporter;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.beangle.model.transfer.Transfer;
import org.beangle.model.transfer.TransferListener;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.io.ItemWriter;
import org.beangle.model.transfer.io.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractItemExporter implements Exporter {

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

	public Transfer addListener(TransferListener listener) {
		listeners.add(listener);
		listener.setTransfer(this);
		return this;
	}

	public Object getCurrent() {
		return current;
	}

	public int getFail() {
		return fail;
	}

	public Locale getLocale() {
		return Locale.getDefault();
	}

	public int getSuccess() {
		return success;
	}

	public int getTranferIndex() {
		return index;
	}

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

	public void transferItem() {
		if (null == getCurrent()) return;
		writer.write(getCurrent());
	}

	protected void next() {
		index++;
		current = iter.next();
	}

	public boolean hasNext() {
		return iter.hasNext();
	}

	public String getFormat() {
		return writer.getFormat();
	}

	public void setContext(Context context) {
		Collection<?> items = (Collection<?>) context.getDatas().get("items");
		if (null != items) {
			datas = items;
			iter = datas.iterator();
		}
		this.context = context;
	}

	public String getDataName() {
		return null;
	}

	public void setWriter(Writer writer) {
		if (writer instanceof ItemWriter) {
			this.writer = (ItemWriter) writer;
		}
	}

	public ItemWriter getWriter() {
		return writer;
	}

}
