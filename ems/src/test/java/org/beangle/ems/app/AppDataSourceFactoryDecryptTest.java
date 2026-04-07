/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.app;

import org.beangle.ems.app.util.PBEEncryptor;
import org.beangle.ems.app.util.PBEEncryptors;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Tests {@link AppDataSourceFactory#decrypt(PBEEncryptor, String)} used after
 * {@link PBEEncryptors#random(String)} in {@link AppDataSourceFactory#postInit()}.
 */
public class AppDataSourceFactoryDecryptTest {

  private static final String KEY = "unit-test-pbe-key";

  @Test
  public void decryptReturnsNullForNull() throws Exception {
    AppDataSourceFactory f = new AppDataSourceFactory();
    PBEEncryptor enc = PBEEncryptors.random(KEY);
    assertNull(f.decrypt(enc, null));
  }

  @Test
  public void decryptLeavesPlainTextUnchanged() throws Exception {
    AppDataSourceFactory f = new AppDataSourceFactory();
    PBEEncryptor enc = PBEEncryptors.random(KEY);
    assertEquals(f.decrypt(enc, "jdbc:mysql://localhost/db"), "jdbc:mysql://localhost/db");
    assertEquals(f.decrypt(enc, "ENC-not-wrapped"), "ENC-not-wrapped");
    assertEquals(f.decrypt(enc, "ENC(no-trailing-paren"), "ENC(no-trailing-paren");
  }

  @Test
  public void decryptUnwrapsEncAndRoundTripsPbe() throws Exception {
    AppDataSourceFactory f = new AppDataSourceFactory();
    PBEEncryptors.RandomPBEEncryptor encryptor = PBEEncryptors.random(KEY);
    String secret = "db-secret-42";
    String cipher = encryptor.encrypt(secret);
    String wrapped = "ENC(" + cipher + ")";

    PBEEncryptors.RandomPBEEncryptor decryptor = PBEEncryptors.random(KEY);
    assertEquals(f.decrypt(decryptor, wrapped), secret);
  }

  @Test
  public void decryptUserPasswordUrlStyleFields() throws Exception {
    AppDataSourceFactory f = new AppDataSourceFactory();
    PBEEncryptors.RandomPBEEncryptor enc = PBEEncryptors.random(KEY);
    String u = enc.encrypt("root");
    String p = enc.encrypt("s3cret");
    String jdbc = enc.encrypt("jdbc:h2:mem:test");

    PBEEncryptors.RandomPBEEncryptor dec = PBEEncryptors.random(KEY);
    assertEquals(f.decrypt(dec, "ENC(" + u + ")"), "root");
    assertEquals(f.decrypt(dec, "ENC(" + p + ")"), "s3cret");
    assertEquals(f.decrypt(dec, "ENC(" + jdbc + ")"), "jdbc:h2:mem:test");
  }
}
