package org.beangle.struts2.osgi;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.apache.struts2.views.freemarker.StrutsClassTemplateLoader;
import org.beangle.struts2.osgi.loaders.FreeMarkerBundleResourceLoader;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;


/**
 * This class extends FreemarkerManager in core to add a template loader
 * (that finds resources inside bundles) to MultiTemplateLoader
 */
public class BundleFreemarkerManager extends FreemarkerManager {

    private static final Logger LOG = LoggerFactory.getLogger(BundleFreemarkerManager.class);

    protected TemplateLoader createTemplateLoader(ServletContext servletContext, String templatePath) {
        TemplateLoader templatePathLoader = null;

        try {
             if (templatePath.startsWith("class://")) {
                 // substring(7) is intentional as we "reuse" the last slash
                 templatePathLoader = new ClassTemplateLoader(getClass(), templatePath.substring(7));
             } else if (templatePath.startsWith("file://")) {
                 templatePathLoader = new FileTemplateLoader(new File(URI.create(templatePath)));
             }
         } catch (IOException e) {
             LOG.error("Invalid template path specified: " + e.getMessage(), e);
         }

        // presume that most apps will require the class and webapp template loader
        // if people wish to
        return templatePathLoader != null ?
                new MultiTemplateLoader(new TemplateLoader[]{
                        templatePathLoader,
                        new WebappTemplateLoader(servletContext),
                        new StrutsClassTemplateLoader(),
                        new FreeMarkerBundleResourceLoader()
                })
                : new MultiTemplateLoader(new TemplateLoader[]{
                new WebappTemplateLoader(servletContext),
                new StrutsClassTemplateLoader(),
                new FreeMarkerBundleResourceLoader()
        });
    }

}
