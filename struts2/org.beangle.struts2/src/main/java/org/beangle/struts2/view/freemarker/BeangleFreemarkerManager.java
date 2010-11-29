/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.StrutsClassTemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateException;

public class BeangleFreemarkerManager extends org.apache.struts2.views.freemarker.FreemarkerManager {

	private final Logger logger = LoggerFactory.getLogger(BeangleFreemarkerManager.class);

	protected BeansWrapper getObjectWrapper() {
		BeansWrapper wrapper = new BeangleObjectWrapper(altMapWrapper);
		wrapper.setUseCache(cacheBeanWrapper);
		return wrapper;
	}

	/**
	 * The default template loader is a MultiTemplateLoader which includes a
	 * ClassTemplateLoader and a WebappTemplateLoader (and a FileTemplateLoader
	 * depending on the init-parameter 'TemplatePath').
	 * <p/>
	 * The ClassTemplateLoader will resolve fully qualified template includes
	 * that begin with a slash. for example /com/company/template/common.ftl
	 * <p/>
	 * The WebappTemplateLoader attempts to resolve templates relative to the
	 * web root folder
	 */
	protected TemplateLoader getTemplateLoader(ServletContext servletContext) {
		// construct a FileTemplateLoader for the init-param 'TemplatePath'
		FileTemplateLoader templatePathLoader = null;

		String templatePath = servletContext.getInitParameter("TemplatePath");
		if (templatePath == null) {
			templatePath = servletContext.getInitParameter("templatePath");
		}

		if (templatePath != null) {
			try {
				templatePathLoader = new FileTemplateLoader(new File(templatePath));
			} catch (IOException e) {
				logger.error("Invalid template path specified:{} ", e.getMessage(), e);
			}
		}

		String webappTemplatePath = servletContext.getInitParameter("webappTemplatePath");
		WebappTemplateLoader wtl = null;

		if (null != webappTemplatePath) {
			wtl = new WebappTemplateLoader(servletContext, webappTemplatePath);
		} else {
			wtl = new WebappTemplateLoader(servletContext);
		}
		StrutsClassTemplateLoader strutsClassTemplateLoader = new StrutsClassTemplateLoader();

		// 带有固定前缀的类模板加载器
		PrefixClassTemplateLoader prefixClassTemplateLoader = new PrefixClassTemplateLoader(
				webappTemplatePath);
		// presume that most apps will require the class and
		// webapp template loader if people wish to
		return templatePathLoader != null ? new MultiTemplateLoader(new TemplateLoader[] {
				templatePathLoader, wtl, strutsClassTemplateLoader, prefixClassTemplateLoader })
				: new MultiTemplateLoader(new TemplateLoader[] { wtl, strutsClassTemplateLoader,
						prefixClassTemplateLoader });
	}

	/**
	 * Load the settings from the /META-INF/freemarker.properties and
	 * /freemarker.properties file on the classpath
	 * 
	 * @see freemarker.template.Configuration#setSettings for the definition of
	 *      valid settings
	 */
	protected void loadSettings(ServletContext servletContext,
			freemarker.template.Configuration configuration) {
		try {
			Properties properties = new Properties();
			Enumeration<?> em = BeangleFreemarkerManager.class.getClassLoader().getResources(
					"META-INF/freemarker.properties");
			while (em.hasMoreElements()) {
				properties.putAll(getProperties((URL) em.nextElement()));
			}
			em = BeangleFreemarkerManager.class.getClassLoader().getResources(
					"freemarker.properties");
			while (em.hasMoreElements()) {
				properties.putAll(getProperties((URL) em.nextElement()));
			}
			configuration.setSettings(properties);
			for (Iterator<Object> iter = properties.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				logger.info("{}={}", key, properties.get(key));
			}
			configuration.setSharedVariable("pagechecker", new PageChecker());
		} catch (IOException e) {
			logger.error("Error while loading freemarker.properties", e);
		} catch (TemplateException e) {
			logger.error("Error while setting freemarker.properties", e);
		}
	}

	private Properties getProperties(URL url) {
		logger.info("loading {}", url);
		InputStream in = null;
		try {
			in = url.openStream();
			if (in != null) {
				Properties p = new Properties();
				p.load(in);
				return p;
			}
		} catch (IOException e) {
			logger.error("Error while loading " + url, e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException io) {
					logger.warn("Unable to close input stream", io);
				}
			}
		}
		return null;
	}
}
