/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 版权所有2002-2018的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.context.support;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.core.io.ContextResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

/**
 * {@link org.springframework.core.io.Resource} implementation for
 * {@link javax.servlet.ServletContext} resources, interpreting
 * relative paths within the web application root directory.
 *
 * <p>Always supports stream access and URL access, but only allows
 * {@code java.io.File} access when the web application archive
 * is expanded.
 *
 * @author Juergen Hoeller
 * @since 28.12.2003
 * @see javax.servlet.ServletContext#getResourceAsStream
 * @see javax.servlet.ServletContext#getResource
 * @see javax.servlet.ServletContext#getRealPath
 */
/**
 * {@link  javax.servlet.ServletContext}资源的{@link  org.springframework.core.io.Resource}实现，解释了Web应用程序根目录中的相对路径。 
 *  <p>始终支持流访问和URL访问，但是仅在扩展Web应用程序存档时才允许{@code  java.io.File}访问。 
 *  @author  Juergen Hoeller @2003年12月28日
 * @see  javax.servlet.ServletContext＃getResourceAsStream 
 * @see  javax.servlet.ServletContext＃getResource 
 * @see  javax.servlet.ServletContext＃getRealPath
 */
public class ServletContextResource extends AbstractFileResolvingResource implements ContextResource {

	private final ServletContext servletContext;

	private final String path;


	/**
	 * Create a new ServletContextResource.
	 * <p>The Servlet spec requires that resource paths start with a slash,
	 * even if many containers accept paths without leading slash too.
	 * Consequently, the given path will be prepended with a slash if it
	 * doesn't already start with one.
	 * @param servletContext the ServletContext to load from
	 * @param path the path of the resource
	 */
	/**
	 * 创建一个新的ServletContextResource。 
	 *  <p> Servlet规范要求资源路径以斜杠开头，即使许多容器也接受不带斜杠的路径。 
	 * 因此，如果给定路径尚未以一个斜杠开头，则将在其前面加上一个斜杠。 
	 *  
	 * @param  servletContext从
	 * @param 路径加载的ServletContext资源的路径
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
	/**
	 * 返回此资源的ServletContext。 
	 * 
	 */
	public final ServletContext getServletContext() {
		return this.servletContext;
	}

	/**
	 * Return the path for this resource.
	 */
	/**
	 * 返回此资源的路径。 
	 * 
	 */
	public final String getPath() {
		return this.path;
	}

	/**
	 * This implementation checks {@code ServletContext.getResource}.
	 * @see javax.servlet.ServletContext#getResource(String)
	 */
	/**
	 * 此实现检查{@code  ServletContext.getResource}。 
	 *  
	 * @see  javax.servlet.ServletContext＃getResource（String）
	 */
	@Override
	public boolean exists() {
		try {
			URL url = this.servletContext.getResource(this.path);
			return (url != null);
		}
		catch (MalformedURLException ex) {
			return false;
		}
	}

	/**
	 * This implementation delegates to {@code ServletContext.getResourceAsStream},
	 * which returns {@code null} in case of a non-readable resource (e.g. a directory).
	 * @see javax.servlet.ServletContext#getResourceAsStream(String)
	 */
	/**
	 * 此实现委托给{@code  ServletContext.getResourceAsStream}，在资源不可读的情况下（例如目录），它返回{@code  null}。 
	 *  
	 * @see  javax.servlet.ServletContext＃getResourceAsStream（String）
	 */
	@Override
	public boolean isReadable() {
		InputStream is = this.servletContext.getResourceAsStream(this.path);
		if (is != null) {
			try {
				is.close();
			}
			catch (IOException ex) {
				// ignore
			}
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isFile() {
		try {
			URL url = this.servletContext.getResource(this.path);
			if (url != null && ResourceUtils.isFileURL(url)) {
				return true;
			}
			else {
				return (this.servletContext.getRealPath(this.path) != null);
			}
		}
		catch (MalformedURLException ex) {
			return false;
		}
	}

	/**
	 * This implementation delegates to {@code ServletContext.getResourceAsStream},
	 * but throws a FileNotFoundException if no resource found.
	 * @see javax.servlet.ServletContext#getResourceAsStream(String)
	 */
	/**
	 * 此实现委托给{@code  ServletContext.getResourceAsStream}，但是如果找不到资源，则抛出FileNotFoundException。 
	 *  
	 * @see  javax.servlet.ServletContext＃getResourceAsStream（String）
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		InputStream is = this.servletContext.getResourceAsStream(this.path);
		if (is == null) {
			throw new FileNotFoundException("Could not open " + getDescription());
		}
		return is;
	}

	/**
	 * This implementation delegates to {@code ServletContext.getResource},
	 * but throws a FileNotFoundException if no resource found.
	 * @see javax.servlet.ServletContext#getResource(String)
	 */
	/**
	 * 此实现委托给{@code  ServletContext.getResource}，但是如果找不到资源，则抛出FileNotFoundException。 
	 *  
	 * @see  javax.servlet.ServletContext＃getResource（String）
	 */
	@Override
	public URL getURL() throws IOException {
		URL url = this.servletContext.getResource(this.path);
		if (url == null) {
			throw new FileNotFoundException(
					getDescription() + " cannot be resolved to URL because it does not exist");
		}
		return url;
	}

	/**
	 * This implementation resolves "file:" URLs or alternatively delegates to
	 * {@code ServletContext.getRealPath}, throwing a FileNotFoundException
	 * if not found or not resolvable.
	 * @see javax.servlet.ServletContext#getResource(String)
	 * @see javax.servlet.ServletContext#getRealPath(String)
	 */
	/**
	 * 此实现解析"文件："URL，或者替代地委托给{@code  ServletContext.getRealPath}，如果找不到或无法解析，则抛出FileNotFoundException。 
	 *  
	 * @see  javax.servlet.ServletContext＃getResource（String）
	 * @see  javax.servlet.ServletContext＃getRealPath（String）
	 */
	@Override
	public File getFile() throws IOException {
		URL url = this.servletContext.getResource(this.path);
		if (url != null && ResourceUtils.isFileURL(url)) {
			// Proceed with file system resolution...
			return super.getFile();
		}
		else {
			String realPath = WebUtils.getRealPath(this.servletContext, this.path);
			return new File(realPath);
		}
	}

	/**
	 * This implementation creates a ServletContextResource, applying the given path
	 * relative to the path of the underlying file of this resource descriptor.
	 * @see org.springframework.util.StringUtils#applyRelativePath(String, String)
	 */
	/**
	 * 此实现创建一个ServletContextResource，将给定路径应用于相对于该资源描述符的基础文件的路径。 
	 *  
	 * @see  org.springframework.util.StringUtils＃applyRelativePath（String，String）
	 */
	@Override
	public Resource createRelative(String relativePath) {
		String pathToUse = StringUtils.applyRelativePath(this.path, relativePath);
		return new ServletContextResource(this.servletContext, pathToUse);
	}

	/**
	 * This implementation returns the name of the file that this ServletContext
	 * resource refers to.
	 * @see org.springframework.util.StringUtils#getFilename(String)
	 */
	/**
	 * 此实现返回此ServletContext资源引用的文件的名称。 
	 *  
	 * @see  org.springframework.util.StringUtils＃getFilename（String）
	 */
	@Override
	@Nullable
	public String getFilename() {
		return StringUtils.getFilename(this.path);
	}

	/**
	 * This implementation returns a description that includes the ServletContext
	 * resource location.
	 */
	/**
	 * 此实现返回一个包含ServletContext资源位置的描述。 
	 * 
	 */
	@Override
	public String getDescription() {
		return "ServletContext resource [" + this.path + "]";
	}

	@Override
	public String getPathWithinContext() {
		return this.path;
	}


	/**
	 * This implementation compares the underlying ServletContext resource locations.
	 */
	/**
	 * 此实现比较底层的ServletContext资源位置。 
	 * 
	 */
	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ServletContextResource)) {
			return false;
		}
		ServletContextResource otherRes = (ServletContextResource) other;
		return (this.servletContext.equals(otherRes.servletContext) && this.path.equals(otherRes.path));
	}

	/**
	 * This implementation returns the hash code of the underlying
	 * ServletContext resource location.
	 */
	/**
	 * 此实现返回基础ServletContext资源位置的哈希码。 
	 * 
	 */
	@Override
	public int hashCode() {
		return this.path.hashCode();
	}

}
