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
package org.beangle.commons.codec.net;

import java.nio.charset.Charset;

import org.beangle.commons.codec.binary.Base64;
import org.beangle.commons.lang.Charsets;

/**
 * <p>
 * Identical to the Base64 encoding defined by <a href="http://www.ietf.org/rfc/rfc1521.txt">RFC
 * 1521</a> and allows a character set to be specified.
 * </p>
 * <p>
 * <a href="http://www.ietf.org/rfc/rfc1522.txt">RFC 1522</a> describes techniques to allow the
 * encoding of non-ASCII text in various portions of a RFC 822 [2] message header, in a manner which
 * is unlikely to confuse existing message handling software.
 * </p>
 * 
 * @see <a href="http://www.ietf.org/rfc/rfc1522.txt">MIME (Multipurpose Internet Mail Extensions)
 *      Part Two: Message Header Extensions for Non-ASCII Text</a>
 * @author chaostone
 * @since 3.2.0
 */
public final class BCoder {

  /**
   * Separator.
   */
  protected static final char Sep = '?';

  /**
   * Prefix
   */
  protected static final String Postfix = "?=";

  /**
   * Postfix
   */
  protected static final String Prefix = "=?";
  /**
   * The default charset used for string decoding and encoding.
   */
  private final Charset charset;

  /**
   * Default constructor.
   */
  public BCoder() {
    this(Charsets.UTF_8);
  }

  /**
   * Constructor which allows for the selection of a default charset
   * 
   * @param charset the default charset to use.
   * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/nio/charset/Charset.html">Standard
   *      charsets</a>
   */
  public BCoder(Charset charset) {
    super();
    this.charset = charset;
  }

  protected String getEncoding() {
    return "B";
  }

  /**
   * Encodes a string into its Base64 form using the default charset. Unsafe characters are escaped.
   * 
   * @param value string to convert to Base64 form
   * @return Base64 string
   */
  public String encode(String value) {
    if (value == null) { return null; }
    StringBuilder buffer = new StringBuilder();
    buffer.append(Prefix);
    buffer.append(charset);
    buffer.append(Sep);
    buffer.append(getEncoding());
    buffer.append(Sep);
    buffer.append(new String(Base64.encode(value.getBytes(charset))));
    buffer.append(Postfix);
    return buffer.toString();
  }

  /**
   * Decodes a Base64 string into its original form. Escaped characters are converted back to their
   * original
   * representation.
   * 
   * @param value Base64 string to convert into its original form
   * @return original string
   */
  public String decode(String text) {
    if (text == null) { return null; }
    if ((!text.startsWith(Prefix)) || (!text.endsWith(Postfix)))
      throw new IllegalArgumentException("RFC 1522 violation: malformed encoded content");
    int terminator = text.length() - 2;
    int from = 2;
    int to = text.indexOf(Sep, from);
    if (to == terminator) throw new IllegalArgumentException("RFC 1522 violation: charset token not found");
    String charset = text.substring(from, to);
    if (charset.equals("")) throw new IllegalArgumentException("RFC 1522 violation: charset not specified");
    from = to + 1;
    to = text.indexOf(Sep, from);
    if (to == terminator) throw new IllegalArgumentException("RFC 1522 violation: encoding token not found");
    String encoding = text.substring(from, to);
    if (!getEncoding().equalsIgnoreCase(encoding))
      throw new IllegalArgumentException("This codec cannot decode " + encoding + " encoded content");
    from = to + 1;
    to = text.indexOf(Sep, from);
    return new String(Base64.decode(text.substring(from, to).toCharArray()), Charset.forName(charset));
  }

}
