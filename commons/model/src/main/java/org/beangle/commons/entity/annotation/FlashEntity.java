/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.entity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 支持系统实时运行所需的数据实体，属于程序运行时需要存放的状态信息。
 * <p>
 * 例如消息队列处理程序中需要先行持久化的消息，发送完成后再删除；实时监控程序中，监控的各类计数。 这类数据实在程序运行时有意义，一旦程序停止则不需要进行处理，可以进行删除。因此它和生产结果数据
 * 以及日志数据需要分开标记，以便独立处理。
 * 
 * @author chaostone
 * @version $Id: FlashEntity.java Jul 1, 2011 8:39:49 AM chaostone $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface FlashEntity {

}
