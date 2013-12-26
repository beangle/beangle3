package org.beangle.struts2.captcha.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Jcaptcha extends AbstractCaptcha {

  private String imageStyle="width:70px; height:22px;cursor: pointer;vertical-align:top;";
  
  private String inputStyle="width:45px;height:16px";

  public Jcaptcha(ValueStack stack) {
    super(stack);
  }

  public String getImageStyle() {
    return imageStyle;
  }

  public void setImageStyle(String imageStyle) {
    this.imageStyle = imageStyle;
  }

  public String getInputStyle() {
    return inputStyle;
  }

  public void setInputStyle(String inputStyle) {
    this.inputStyle = inputStyle;
  }

}
