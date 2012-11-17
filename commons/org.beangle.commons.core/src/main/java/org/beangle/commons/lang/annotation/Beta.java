/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Signifies that a public API (public class, method or field) is subject to
 * incompatible changes, or even removal, in a future release. An API bearing
 * this annotation is exempt from any compatibility guarantees made by its
 * containing library. Note that the presence of this annotation implies nothing
 * about the quality or performance of the API in question, only the fact that
 * it is not "API-frozen."
 * 
 * @author chaostone
 * @since 3.0.2
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface Beta {

}
