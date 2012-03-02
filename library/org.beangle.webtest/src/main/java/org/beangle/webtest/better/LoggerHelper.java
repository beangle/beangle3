package org.beangle.webtest.better;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.testng.log4testng.Logger;

public class LoggerHelper {
    
    private static final String format = "{0} {1, time} : {2}";
    
    public static void debug(Logger LOGGER, String message) {
        LOGGER.debug(MessageFormat.format(format, StringUtils.rightPad("#" + Thread.currentThread().getId(), 4), new Date(), message));
    }
}
