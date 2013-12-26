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
