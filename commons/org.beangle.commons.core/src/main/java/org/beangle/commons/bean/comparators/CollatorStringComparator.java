/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.bean.comparators;

import java.text.Collator;

/**
 * <p>
 * CollatorStringComparator class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class CollatorStringComparator implements StringComparator {
  private boolean asc;

  private Collator collator;

  /**
   * <p>
   * Constructor for CollatorStringComparator.
   * </p>
   */
  public CollatorStringComparator() {
    super();
    collator = Collator.getInstance();
  }

  /**
   * <p>
   * Constructor for CollatorStringComparator.
   * </p>
   * 
   * @param asc a boolean.
   */
  public CollatorStringComparator(final boolean asc) {
    this();
    this.asc = asc;
  }

  /**
   * <p>
   * Constructor for CollatorStringComparator.
   * </p>
   * 
   * @param asc a boolean.
   * @param collator a {@link java.text.Collator} object.
   */
  public CollatorStringComparator(final boolean asc, final Collator collator) {
    this.collator = collator;
    this.asc = asc;
  }

  /**
   * <p>
   * compare.
   * </p>
   * 
   * @param what0 a {@link java.lang.String} object.
   * @param what1 a {@link java.lang.String} object.
   * @return a int.
   */
  public int compare(final String what0, final String what1) {
    return (asc ? 1 : -1) * (collator.compare((null == what0) ? "" : what0, (null == what1) ? "" : what1));
  }

  /**
   * <p>
   * isAsc.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isAsc() {
    return asc;
  }

  /**
   * <p>
   * Setter for the field <code>asc</code>.
   * </p>
   * 
   * @param asc a boolean.
   */
  public void setAsc(final boolean asc) {
    this.asc = asc;
  }

  /**
   * <p>
   * Getter for the field <code>collator</code>.
   * </p>
   * 
   * @return a {@link java.text.Collator} object.
   */
  public Collator getCollator() {
    return collator;
  }

  /**
   * <p>
   * Setter for the field <code>collator</code>.
   * </p>
   * 
   * @param collator a {@link java.text.Collator} object.
   */
  public void setCollator(final Collator collator) {
    this.collator = collator;
  }

}
