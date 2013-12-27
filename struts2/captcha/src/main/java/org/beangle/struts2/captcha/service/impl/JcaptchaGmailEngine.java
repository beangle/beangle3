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

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * captcha like GMAIL
 * 
 * @author chaostone
 */
public class JcaptchaGmailEngine extends ListImageCaptchaEngine {

  protected void buildInitialFactories() {
    Integer minWordLength = new Integer(4);
    Integer maxWordLength = new Integer(5);
    Integer imageWidth = new Integer(90);
    Integer imageHeight = new Integer(35);
    int fontSize = 21;

    // word generator
    WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));

    // word2image components
    TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength, maxWordLength,
        new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11),
            new Color(23, 67, 172) }), new TextDecorator[] {});
    BackgroundGenerator background = new UniColorBackgroundGenerator(imageWidth, imageHeight, Color.white);
    FontGenerator font = new RandomFontGenerator(new Integer(fontSize), Integer.valueOf(fontSize),
        new Font[] { new Font("nyala", Font.BOLD, fontSize), new Font("Bell MT", Font.PLAIN, fontSize),
            new Font("Credit valley", Font.BOLD, fontSize) });

    ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
    ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
    ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});

    WordToImage word2image = new DeformedComposedWordToImage(font, background, randomPaster, backDef,
        textDef, postDef);
    addFactory(new GimpyFactory(dictionnaryWords, word2image));
  }

}
