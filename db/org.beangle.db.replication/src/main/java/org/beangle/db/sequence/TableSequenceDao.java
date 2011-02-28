/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.sequence;

import java.util.List;

/**
 * @author cheneystar 2008-07-23
 */
public interface TableSequenceDao {

	/** 得到所有用户的序列号* */
	public List<String> getAllNames();

	/** 得到数据库中没有被指定的sequence* */
	public List<String> getNoneReferenced();

	/**
	 * 找到所有错误的sequence
	 * 
	 * @return
	 */
	public List<TableSequence> getInconsistent();

	/**
	 * 删除指定的sequence
	 * 
	 * @param sequence_name
	 * @return
	 */
	public boolean drop(String sequence_name);

	public void setRelation(SequenceNamePattern relation);

	public long adjust(TableSequence tableSequence);
}
