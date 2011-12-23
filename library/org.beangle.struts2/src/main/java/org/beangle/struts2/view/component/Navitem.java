/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.beangle.lang.StrUtils;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Navitem.java Apr 20, 2011 8:46:40 AM chaostone $
 */
public class Navitem extends UIBean {

	private String title;
	private String href;

	private String onclick;
	private String target;
	private boolean selected = false;

	public Navitem(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		this.href = render(this.href);
		this.selected = findAncestor(Navmenu.class).isSelected(this.href);
		if (null == onclick) {
			if (null != target) {
				onclick = StrUtils.concat("return bg.Go(this,'", target, "')");
				target = null;
			} else {
				onclick = "return bg.Go(this)";
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
