/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.beangle.web.util.RequestUtils;
import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.core.io.ContextResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author chaostone
 * @version $Id: ServletContextResource.java Dec 21, 2011 4:53:53 PM chaostone $
 */

public class ServletContextResource extends AbstractFileResolvingResource implements ContextResource {

	private final ServletContext servletContext;

	private final String path;

	/**
	 * Create a new ServletContextResource.
	 * <p>
	 * The Servlet spec requires that resource paths start with a slash, even if many containers
	 * accept paths without leading slash too. Consequently, the given path will be prepended with a
	 * slash if it doesn't already start with one.
	 * 
	 * @param servletContext the ServletContext to load from
	 * @param path the path of the resource
	 */
	public ServletContextResource(ServletContext servletContext, String path) {
		// check ServletContext
		Assert.notNull(servletContext, "Cannot resolve ServletContextResource without ServletContext");
		this.servletContext = servletContext;

		// check path
		Assert.notNull(path, "Path is required");
		String pathToUse = StringUtils.cleanPath(path);
		if (!pathToUse.startsWith("/")) {
			pathToUse = "/" + pathToUse;
		}
		this.path = pathToUse;
	}

	/**
	 * Return the ServletContext for this resource.
	 */
	public final ServletContext getServletContext() {
		return this.servletContext;
	}

	/**
	 * Return the path for this resource.
	 */
	public final String getPath() {
		return this.path;
	}

	/**
	 * This implementation checks <code>ServletContext.getResource</code>.
	 * 
	 * @see javax.servlet.ServletContext#getResource(String)
	 */
	@Override
	public boolean exists() {
		try {
			URL url = this.servletContext.getResource(this.path);
			return (url != null);
		} catch (MalformedURLException ex) {
			return false;
		}
	}

	/**
	 * This implementation delegates to <code>ServletContext.getResourceAsStream</code>,
	 * but throws a FileNotFoundException if no resource found.
	 * 
	 * @see javax.servlet.ServletContext#getResourceAsStream(String)
	 */
	public InputStream getInputStream() throws IOException {
		InputStream is = this.servletContext.getResourceAsStream(this.path);
		if (is == null) { throw new FileNotFoundException("Could not open " + getDescription()); }
		return is;
	}

	/**
	 * This implementation delegates to <code>ServletContext.getResource</code>,
	 * but throws a FileNotFoundException if no resource found.
	 * 
	 * @see javax.servlet.ServletContext#getResource(String)
	 */
	@Override
	public URL getURL() throws IOException {
		URL url = this.servletContext.getResource(this.path);
		if (url == null) { throw new FileNotFoundException(getDescription()
				+ " cannot be resolved to URL because it does not exist"); }
		return url;
	}

	/**
	 * This implementation delegates to <code>ServletContext.getRealPath</code>,
	 * but throws a FileNotFoundException if not found or not resolvable.
	 * 
	 * @see javax.servlet.ServletContext#getRealPath(String)
	 */
	@Override
	public File getFile() throws IOException {
		String realPath = RequestUtils.getRealPath(this.servletContext, this.path);
		return new File(realPath);
	}

	/**
	 * This implementation creates a ServletContextResource, applying the given path
	 * relative to the path of the underlying file of this resource descriptor.
	 * 
	 * @see org.springframework.util.StringUtils#applyRelativePath(String, String)
	 */
	@Override
	public Resource createRelative(String relativePath) {
		String pathToUse = StringUtils.applyRelativePath(this.path, relativePath);
		return new ServletContextResource(this.servletContext, pathToUse);
	}

	/**
	 * This implementation returns the name of the file that this ServletContext
	 * resource refers to.
	 * 
	 * @see org.springframework.util.StringUtils#getFilename(String)
	 */
	@Override
	public String getFilename() {
		return StringUtils.getFilename(this.path);
	}

	/**
	 * This implementation returns a description that includes the ServletContext
	 * resource location.
	 */
	public String getDescription() {
		return "ServletContext resource [" + this.path + "]";
	}

	public String getPathWithinContext() {
		return this.path;
	}

	/**
	 * This implementation compares the underlying ServletContext resource locations.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) { return true; }
		if (obj instanceof ServletContextResource) {
			ServletContextResource otherRes = (ServletContextResource) obj;
			return (this.servletContext.equals(otherRes.servletContext) && this.path.equals(otherRes.path));
		}
		return false;
	}

	/**
	 * This implementation returns the hash code of the underlying
	 * ServletContext resource location.
	 */
	@Override
	public int hashCode() {
		return this.path.hashCode();
	}

}
