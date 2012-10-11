/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.util.MakeIterator;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.beangle.struts2.view.template.Theme;

import com.opensymphony.xwork2.util.ValueStack;

import freemarker.template.utility.StringUtil;

/**
 * Data table
 * 
 * @author chaostone
 */
public class Grid extends ClosingUIBean {
  private List<Col> cols = CollectUtils.newArrayList();
  private Set<Object> colTitles = CollectUtils.newHashSet();
  private Object items;
  private String var;
  private String caption;
  // gridbar
  private String bar;
  private String sortable = "true";
  private String filterable = "false";
  private Map<String, String> filters = CollectUtils.newHashMap();

  /** 重新载入的时间间隔（以秒为单位） */
  private String refresh;

  /** 没有数据时显示的文本 */
  private String emptyMsg;

  public Grid(ValueStack stack) {
    super(stack);
  }

  public boolean getHasbar() {
    return (null != bar || items instanceof Page);
  }

  public boolean isPageable() {
    return items instanceof Page<?>;
  }

  public boolean isNotFullPage() {
    if ((items instanceof Page)) return ((Page<?>) items).size() < ((Page<?>) items).getPageSize();
    else return ((Collection<?>) items).isEmpty();
  }

  public String defaultSort(String property) {
    return Strings.concat(var, ".", property);
  }

  public boolean isSortable(Col cln) {
    Object sortby = cln.getParameters().get("sort");
    if (null != sortby) return true;
    return ("true".equals(sortable) && !Objects.equals(cln.getSortable(), "false") && null != cln
        .getProperty());
  }

  public boolean isFilterable(Col cln) {
    return ("true".equals(filterable) && !Objects.equals(cln.getFilterable(), "false") && null != cln
        .getProperty());
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  protected void addCol(Col column) {
    String title = column.getTitle();
    if (null == title) title = column.getProperty();
    if (null == column.getWidth() && column instanceof Boxcol) column.setWidth("25px");
    if (!colTitles.contains(title)) {
      colTitles.add(title);
      cols.add(column);
    }
  }

  public boolean start(Writer writer) {
    generateIdIfEmpty();
    return true;
  }

  public List<Col> getCols() {
    return cols;
  }

  public String getVar() {
    return var;
  }

  public void setVar(String var) {
    this.var = var;
  }

  public String getRefresh() {
    return refresh;
  }

  public void setRefresh(String refresh) {
    int refreshNum = Numbers.toInt(refresh);
    if (refreshNum > 0) this.refresh = String.valueOf(refreshNum);
  }

  public void setItems(Object datas) {
    if (datas instanceof String) {
      this.items = findValue((String) datas);
    } else {
      this.items = datas;
    }
  }

  public Object getItems() {
    return items;
  }

  public String getSortable() {
    return sortable;
  }

  public void setSortable(String sortable) {
    this.sortable = sortable;
  }

  public String getFilterable() {
    return filterable;
  }

  public void setFilterable(String filterable) {
    this.filterable = filterable;
  }

  public String getBar() {
    return bar;
  }

  public String getEmptyMsg() {
    return emptyMsg;
  }

  public void setEmptyMsg(String emptyMsg) {
    this.emptyMsg = emptyMsg;
  }

  public Map<String, String> getFilters() {
    return filters;
  }

  public void setFilters(Map<String, String> filters) {
    this.filters = filters;
  }

  public static class Filter extends ClosingUIBean {
    String property;

    public Filter(ValueStack stack) {
      super(stack);
    }

    @Override
    public boolean doEnd(Writer writer, String body) {
      Grid grid = (Grid) findAncestor(Grid.class);
      if (null != property && null != grid) {
        grid.getFilters().put(property, body);
      }
      return false;
    }

    public String getProperty() {
      return property;
    }

    public void setProperty(String property) {
      this.property = property;
    }

  }

  public static class Bar extends ClosingUIBean {
    private Grid grid;

    public Bar(ValueStack stack) {
      super(stack);
      grid = (Grid) findAncestor(Grid.class);
    }

    @Override
    public boolean doEnd(Writer writer, String body) {
      grid.bar = body;
      return false;
    }

    public Grid getTable() {
      return grid;
    }
  }

  public static class Row extends IterableUIBean {
    private Grid table;
    private String var_index;
    private Iterator<?> iterator;
    private int index = -1;
    protected Object curObj;
    private Boolean innerTr;

    public Row(ValueStack stack) {
      super(stack);
      table = (Grid) findAncestor(Grid.class);
      Object iteratorTarget = table.items;
      iterator = MakeIterator.convert(iteratorTarget);
      if (!iterator.hasNext()) {
        iterator = Collections.singleton(null).iterator();
      }
      this.var_index = table.var + "_index";
    }

    public boolean isHasTr() {
      if (null != innerTr) return innerTr;
      int i = 0;
      innerTr = Boolean.FALSE;
      // max try count is 10
      while (i < body.length() && i < 10) {
        if (body.charAt(i) == '<' && Strings.substring(body, i, i + 3).equals("<tr")) {
          innerTr = Boolean.TRUE;
          break;
        }
        i++;
      }
      return innerTr;
    }

    @Override
    protected boolean next() {
      if (iterator != null && iterator.hasNext()) {
        index++;
        curObj = iterator.next();
        stack.getContext().put(table.var, curObj);
        stack.getContext().put(var_index, index);
        return true;
      } else {
        stack.getContext().remove(table.var);
        stack.getContext().remove(var_index);
      }
      return false;
    }
  }

  /**
   * @author chaostone
   */
  public static class Col extends ClosingUIBean {
    String property;
    String title;
    String width;
    Row row;
    String sortable;
    String filterable;

    public Col(ValueStack stack) {
      super(stack);
    }

    @Override
    public boolean start(Writer writer) {
      row = findAncestor(Row.class);
      if (row.index == 0) row.table.addCol(this);
      return null != row.curObj;
    }

    @Override
    public boolean doEnd(Writer writer, String body) {
      if (getTheme().equals(Theme.DEFAULT_THEME)) {
        try {
          writer.append("<td").append(getParameterString()).append(">");
          if (Strings.isNotEmpty(body)) {
            writer.append(body);
          } else if (null != property) {
            Object val = getValue();
            if (null != val) writer.append(StringUtil.HTMLEnc(val.toString()));
          }
          writer.append("</td>");
        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
      } else {
        return super.doEnd(writer, body);
      }
    }

    public String getProperty() {
      return property;
    }

    public void setProperty(String property) {
      this.property = property;
    }

    /**
     * find value of row.obj's property
     * 
     * @return
     */
    public Object getValue() {
      return getValue(row.curObj, property);
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getPropertyPath() {
      return Strings.concat(row.table.var, ".", property);
    }

    /**
     * 支持按照属性提取国际化英文名
     * 
     * @return
     */
    public String getTitle() {
      if (null == title) {
        title = Strings.concat(row.table.var, ".", property);
      }
      return getText(title);
    }

    public String getWidth() {
      return width;
    }

    public void setWidth(String width) {
      this.width = width;
    }

    public String getSortable() {
      return sortable;
    }

    public void setSortable(String sortable) {
      this.sortable = sortable;
    }

    public String getFilterable() {
      return filterable;
    }

    public void setFilterable(String filterable) {
      this.filterable = filterable;
    }

    public Object getCurObj() {
      return row.curObj;
    }

  }

  public static class Treecol extends Col {

    public Treecol(ValueStack stack) {
      super(stack);
    }

    @Override
    public boolean doEnd(Writer writer, String body) {
      this.body = body;
      try {
        mergeTemplate(writer);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      return false;
    }

  }

  public static class Boxcol extends Col {

    public Boxcol(ValueStack stack) {
      super(stack);
    }

    String type = "checkbox";

    // checkbox or radiobox name
    String boxname = null;

    /** display or none */
    boolean display = true;

    boolean checked;

    @Override
    public boolean start(Writer writer) {
      if (null == property) this.property = "id";

      row = (Row) findAncestor(Row.class);
      if (null == boxname) boxname = row.table.var + "." + property;

      if (row.index == 0) row.table.addCol(this);
      return null != row.curObj;
    }

    @Override
    public boolean doEnd(Writer writer, String body) {
      if (getTheme().equals(Theme.DEFAULT_THEME)) {
        try {
          writer.append("<td class=\"gridselect\"");
          if (null != id) writer.append(" id=\"").append(id).append("\"");
          writer.append(getParameterString()).append(">");
          if (display) {
            writer.append("<input class=\"box\" name=\"").append(boxname).append("\" value=\"")
                .append(String.valueOf(getValue())).append("\" type=\"").append(type).append("\"");
            if (checked) writer.append(" checked=\"checked\"");
            writer.append("/>");
          }
          if (Strings.isNotEmpty(body)) writer.append(body);
          writer.append("</td>");
        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
      } else {
        return super.doEnd(writer, body);
      }
    }

    public String getType() {
      return type;
    }

    @Override
    public String getTitle() {
      return Strings.concat(row.table.var, "_", property);
    }

    public String getBoxname() {
      return boxname;
    }

    public void setBoxname(String boxname) {
      this.boxname = boxname;
    }

    public void setType(String type) {
      this.type = type;
    }

    public boolean isChecked() {
      return checked;
    }

    public void setChecked(boolean checked) {
      this.checked = checked;
    }

    public boolean isDisplay() {
      return display;
    }

    public void setDisplay(boolean display) {
      this.display = display;
    }
  }
}
