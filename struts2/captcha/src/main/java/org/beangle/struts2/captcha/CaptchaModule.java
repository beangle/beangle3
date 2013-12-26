package org.beangle.struts2.captcha;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.struts2.captcha.action.JcaptchaImageAction;
import org.beangle.struts2.captcha.service.impl.JcaptchaGmailEngine;
import org.beangle.struts2.captcha.service.impl.JcaptchaImageProvider;
import org.beangle.struts2.captcha.service.impl.RecaptchaProvider;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

public class CaptchaModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(JcaptchaImageAction.class);
    bind("recaptchaProvider", RecaptchaProvider.class);
    bind("jcaptchaImageProvider", JcaptchaImageProvider.class);
    bind("captchaService", DefaultManageableImageCaptchaService.class).property("captchaEngine",
        bean(JcaptchaGmailEngine.class)).property("minGuarantedStorageDelayInSeconds", "600");
  }

}
