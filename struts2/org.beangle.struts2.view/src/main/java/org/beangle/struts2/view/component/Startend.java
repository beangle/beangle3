/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.struts2.view.component;

import org.beangle.commons.lang.Strings;

import com.opensymphony.xwork2.util.ValueStack;

public class Startend extends UIBean {

  private String label;

  private String name;

  private Object start;

  private Object end;

  private String comment;

  private String required;

  private String format = "yyyy-MM-dd";

  private Date[] dates;

  public Startend(ValueStack stack) {
    super(stack);
  }

  @Override
  protected void evaluateParams() {
    String[] nameArray = Strings.split(name, ',');
    dates = new Date[nameArray.length];
    String format2 = Date.ResvervedFormats.get(format);
    if (null != format2) format = format2;
    String[] requiredArray = Strings.split(required, ',');
    String[] commentArray = Strings.split(comment, ',');
    String[] labelArray = Strings.split(label, ',');
    for (int i = 0; i < nameArray.length; i++) {
      if (i >= 2) break;
      dates[i] = new Date(stack);
      String name = nameArray[i];
      dates[i].setName(name);
      dates[i].setFormat(format);
      if (requiredArray != null) {
        dates[i].setRequired(requiredArray.length == 1 ? required : requiredArray[i]);
      }
      if (commentArray != null) {
        dates[i].setComment(commentArray.length == 1 ? comment : commentArray[i]);
      }
      if (labelArray != null) {
        dates[i].setLabel(labelArray.length == 1 ? label : labelArray[i]);
      }
      dates[i].setTitle(dates[i].getLabel());
      if (i == 0) dates[0].setValue(start);
      else dates[1].setValue(end);

      dates[i].evaluateParams();
    }
    if (dates.length == 2) {
      dates[0].setMaxDate("#F{$dp.$D(\\'" + dates[1].id + "\\')}");
      dates[1].setMinDate("#F{$dp.$D(\\'" + dates[0].id + "\\')}");

      if (labelArray.length == 1) {
        boolean containTime = format.contains("HH:mm");
        dates[0].setTitle(dates[0].getTitle() + getText(containTime ? "common.beginAt" : "common.beginOn"));
        dates[1].setTitle(dates[1].getTitle() + getText(containTime ? "common.endAt" : "common.endOn"));
      }
    }

  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Date[] getDates() {
    return dates;
  }

  public void setDates(Date[] dates) {
    this.dates = dates;
  }

  public Object getStart() {
    return start;
  }

  public void setStart(Object start) {
    this.start = start;
  }

  public Object getEnd() {
    return end;
  }

  public void setEnd(Object end) {
    this.end = end;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
  }

}
