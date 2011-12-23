/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

/**
 * 支持系统实时运行所需的数据实体，属于程序运行时需要存放的状态信息。
 * <p>
 * 例如消息队列处理程序中需要先行持久化的消息，发送完成后再删除；实时监控程序中，监控的各类计数。 这类数据实在程序运行时有意义，一旦程序停止则不需要进行处理，可以进行删除。因此它和生产结果数据
 * 以及日志数据需要分开标记，以便独立处理。
 * 
 * @author chaostone
 * @version $Id: RunningEntity.java Jul 1, 2011 8:39:49 AM chaostone $
 */
public interface FlashEntity {

}
