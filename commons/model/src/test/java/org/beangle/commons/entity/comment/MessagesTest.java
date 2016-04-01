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
package org.beangle.commons.entity.comment;

import static org.testng.Assert.assertEquals;

import java.util.Locale;

import org.beangle.commons.entity.comment.Messages;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.testng.annotations.Test;

@Test
public class MessagesTest {

  public void testGet() {
    Messages comments = Messages.build(Locale.SIMPLIFIED_CHINESE);
    assertEquals(comments.get(LongIdObject.class, "id"), "非业务主键");

    comments = Messages.build(Locale.US);
    assertEquals(comments.get(LongIdObject.class, "id"), "ID");
  }
}
