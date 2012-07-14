/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.seq;

import java.util.Collections;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;

/**
 * <p>
 * SeqPattern class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SeqPattern {

  private MultiLevelSeqGenerator generator;

  private String pattern;

  private int level;

  private SeqNumStyle seqNumStyle;

  private int seq = 0;

  private final List<Integer> params = CollectUtils.newArrayList();

  // 从模式中进行分析{}
  /**
   * <p>
   * Constructor for SeqPattern.
   * </p>
   * 
   * @param seqStyle a {@link org.beangle.commons.util.seq.SeqNumStyle} object.
   * @param pattern a {@link java.lang.String} object.
   */
  public SeqPattern(SeqNumStyle seqStyle, String pattern) {
    this.seqNumStyle = seqStyle;
    this.pattern = pattern;
    String remainder = pattern;
    while (Strings.isNotEmpty(remainder)) {
      String p = Strings.substringBetween(remainder, "{", "}");
      if (Strings.isEmpty(p)) {
        break;
      }
      if (Numbers.isDigits(p)) {
        params.add(new Integer(p));
      }
      remainder = Strings.substringAfter(remainder, "{" + p + "}");
    }

    Collections.sort(params);
    this.level = ((Integer) params.get(params.size() - 1)).intValue();
    params.remove(params.size() - 1);
  }

  /**
   * <p>
   * curSeqText.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String curSeqText() {
    return seqNumStyle.build(seq);
  }

  /**
   * <p>
   * next.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String next() {
    seq++;
    String text = pattern;
    for (final Integer paramLevel : params) {
      text = Strings.replace(text, "{" + paramLevel + "}", generator.getSytle(paramLevel.intValue())
          .curSeqText());
    }
    return Strings.replace(text, "{" + level + "}", seqNumStyle.build(seq));
  }

  /**
   * <p>
   * reset.
   * </p>
   */
  public void reset() {
    seq = 0;
  }

  /**
   * <p>
   * Getter for the field <code>level</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getLevel() {
    return level;
  }

  /**
   * <p>
   * Setter for the field <code>level</code>.
   * </p>
   * 
   * @param level a int.
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * <p>
   * Getter for the field <code>seqNumStyle</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.util.seq.SeqNumStyle} object.
   */
  public SeqNumStyle getSeqNumStyle() {
    return seqNumStyle;
  }

  /**
   * <p>
   * Setter for the field <code>seqNumStyle</code>.
   * </p>
   * 
   * @param seqNumStyle a {@link org.beangle.commons.util.seq.SeqNumStyle} object.
   */
  public void setSeqNumStyle(SeqNumStyle seqNumStyle) {
    this.seqNumStyle = seqNumStyle;
  }

  /**
   * <p>
   * Setter for the field <code>pattern</code>.
   * </p>
   * 
   * @param pattern a {@link java.lang.String} object.
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  /**
   * <p>
   * Getter for the field <code>pattern</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getPattern() {
    return this.pattern;
  }

  /**
   * <p>
   * Getter for the field <code>generator</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.util.seq.MultiLevelSeqGenerator} object.
   */
  public MultiLevelSeqGenerator getGenerator() {
    return generator;
  }

  /**
   * <p>
   * Setter for the field <code>generator</code>.
   * </p>
   * 
   * @param generator a {@link org.beangle.commons.util.seq.MultiLevelSeqGenerator} object.
   */
  public void setGenerator(MultiLevelSeqGenerator generator) {
    this.generator = generator;
  }

}
