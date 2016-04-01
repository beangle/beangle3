/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.struts2.captcha;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.struts2.captcha.action.ImageAction;
import org.beangle.struts2.captcha.service.impl.JcaptchaGmailEngine;
import org.beangle.struts2.captcha.service.impl.JcaptchaImageProvider;
import org.beangle.struts2.captcha.service.impl.RecaptchaProvider;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

public class CaptchaModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(ImageAction.class);
    bind("recaptchaProvider", RecaptchaProvider.class);
    bind("jcaptchaImageProvider", JcaptchaImageProvider.class);
    bind("captchaService", DefaultManageableImageCaptchaService.class).property("captchaEngine",
        bean(JcaptchaGmailEngine.class)).property("minGuarantedStorageDelayInSeconds", "600");
  }

}
