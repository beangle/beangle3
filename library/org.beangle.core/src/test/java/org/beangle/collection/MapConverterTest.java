/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.collection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

import org.beangle.bean.converters.Converter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class MapConverterTest {

	Map<String, Object> datas = CollectUtils.newHashMap();
	MapConverter converter = new MapConverter(Converter.getDefault());

	@BeforeClass
	public void setUp() {
		datas.put("empty1", "");
		datas.put("empty2", null);
		datas.put("empty3", new String[] { "" });
	}

	public void testGetDate() {
		int year = 2010;
		int month = 9;
		int day = 1;
		datas.put("birthday", year + "-" + month + "-" + day);

		Date birthday = converter.get(datas, "birthday", Date.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(birthday);
		assertEquals(calendar.get(Calendar.YEAR), year);
		assertEquals(calendar.get(Calendar.MONTH), month - 1);
		assertEquals(calendar.get(Calendar.DAY_OF_MONTH), day);

		datas.put("birthday", new Date[] { birthday });
		Date birthday2 = converter.get(datas, "birthday", Date.class);
		assertEquals(birthday, birthday2);
	}

	public void testGetArray() {
		datas.put("name", new String[] { "me" });
		Object name = converter.get(datas, "name");
		assertNotNull(name);
		assertEquals(name, "me");

	}

	public void testGetNull() {
		boolean empty1 = converter.getBool(datas, "empty1");
		assertFalse(empty1);
		empty1 = converter.getBool(datas, "empty2");
		assertFalse(empty1);
		Boolean emptyB1 = converter.getBoolean(datas, "empty1");
		assertNull(emptyB1);
		emptyB1 = converter.getBoolean(datas, "empty2");
		assertNull(emptyB1);

		Long id = converter.getLong(datas, "empty1");
		assertNull(id);
		id = converter.getLong(datas, "empty2");
		assertNull(id);

		id = converter.getLong(datas, "empty3");
		assertNull(id);
	}
}
