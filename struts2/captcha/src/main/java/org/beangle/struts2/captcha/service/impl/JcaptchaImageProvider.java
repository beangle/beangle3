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
package org.beangle.struts2.captcha.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.beangle.struts2.captcha.service.CaptchaProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

public class JcaptchaImageProvider implements CaptchaProvider {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private ImageCaptchaService imageCaptchaService;

  @Override
  public boolean verify(HttpServletRequest request, String response) {
    String captchaId = request.getSession().getId();
    if (response == null) return false;
    try {
      return imageCaptchaService.validateResponseForID(captchaId, response).booleanValue();
    } catch (CaptchaServiceException e) {
      throw e;
    }
  }

  @Override
  public ByteArrayOutputStream generater(HttpServletRequest request) {
    ByteArrayOutputStream imageOut = new ByteArrayOutputStream();
    // Captcha Id is the session ID
    String captchaId = request.getSession().getId();
    logger.debug("Generating Captcha Image for SessionID : " + captchaId);
    // Generate Captcha Image
    BufferedImage image = imageCaptchaService.getImageChallengeForID(captchaId, request.getLocale());
    // Encode to JPEG Stream
    try {
      ImageIO.write(image, "jpg", imageOut);
    } catch (IOException e) {
      logger.error("Unable to JPEG encode the Captcha Image due to IOException", e);
      throw new IllegalArgumentException("Unable to JPEG encode the Captcha Image", e);
    }
    return imageOut;
  }

  public void setImageCaptchaService(ImageCaptchaService imageCaptchaService) {
    this.imageCaptchaService = imageCaptchaService;
  }

}
