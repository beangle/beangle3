/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.captcha.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.beangle.struts2.action.BaseAction;

import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 图片验证码
 * 
 * @author chaostone
 */
public class ImageAction extends BaseAction {

	private ByteArrayInputStream inputStream;

	private ImageCaptchaService captchaService;

	public String index() throws IOException {
		String captchaId = getRequest().getSession().getId();
		BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, getLocale());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(challenge, "JPEG", os);
		inputStream = new ByteArrayInputStream(os.toByteArray());
		return "success";
	}

	public void setCaptchaService(ImageCaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

}
