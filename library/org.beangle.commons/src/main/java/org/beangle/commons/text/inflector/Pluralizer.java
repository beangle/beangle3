/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.inflector;

/**
 * <code>Pluralizer</code> converts singular word forms to their plural forms.
 * 
 * @author chaostone
 */
public interface Pluralizer {

	public String pluralize(String word);

	public String pluralize(String word, int number);
}
