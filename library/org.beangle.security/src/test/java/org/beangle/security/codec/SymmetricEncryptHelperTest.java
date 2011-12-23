/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.codec;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SymmetricEncryptHelperTest {
	@Test
	public void testEncryptAndDescrypt() throws Exception {
		String value = "2005122338";
		String encryptText = SymmetricEncryptHelper.toHexString(SymmetricEncryptHelper.encypt(
				"ABCDEFGH".getBytes(), value.getBytes()));
		Assert.assertEquals("37E23121FBA00D576224161E50870CAB", encryptText);
		Assert.assertEquals(
				"2005122338",
				new String(SymmetricEncryptHelper.decrypt("ABCDEFGH".getBytes(),
						SymmetricEncryptHelper.fromHexString(encryptText))));
	}
}
