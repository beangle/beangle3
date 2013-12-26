package org.beangle.struts2.captcha.component;

import org.beangle.struts2.captcha.service.CaptchaProvider;
import org.beangle.struts2.view.component.ClosingUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public abstract class AbstractCaptcha extends ClosingUIBean {

  protected String name = "captcha_response";

  protected CaptchaProvider provider;

  protected AbstractCaptcha(ValueStack stack) {
    super(stack);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CaptchaProvider getProvider() {
    return provider;
  }

  public void setProvider(CaptchaProvider provider) {
    this.provider = provider;
  }

}
