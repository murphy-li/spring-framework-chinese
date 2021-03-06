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

package org.springframework.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.lang.Nullable;

/**
 * Utility methods for resolving resource locations to files in the
 * file system. Mainly for internal use within the framework.
 *
 * <p>Consider using Spring's Resource abstraction in the core package
 * for handling all kinds of file resources in a uniform manner.
 * {@link org.springframework.core.io.ResourceLoader}'s {@code getResource()}
 * method can resolve any location to a {@link org.springframework.core.io.Resource}
 * object, which in turn allows one to obtain a {@code java.io.File} in the
 * file system through its {@code getFile()} method.
 *
 * @author Juergen Hoeller
 * @since 1.1.5
 * @see org.springframework.core.io.Resource
 * @see org.springframework.core.io.ClassPathResource
 * @see org.springframework.core.io.FileSystemResource
 * @see org.springframework.core.io.UrlResource
 * @see org.springframework.core.io.ResourceLoader
 */
/**
 * 用于解决文件系统中文件的资源位置的实用程序方法。 
 * 主要供框架内部使用。 
 *  <p>考虑在核心软件包中使用Spring的Resource抽象来以统一的方式处理各种文件资源。 
 *  {@link  org.springframework.core.io.ResourceLoader}的{@code  getResource（）}方法可以将任何位置解析为{@link  org.springframework.core.io.Resource}对象，依次允许人们通过其{@code  getFile（）}方法在文件系统中获取{@code  java.io.File}。 
 *  @author  Juergen Hoeller @1.1.5起
 * @see  org.springframework.core.io.Resource 
 * @see  org.springframework.core.io.ClassPathResource 
 * @see  org.springframework.core.io.FileSystemResource 
 * @see  org.springframework.core.io.UrlResource 
 * @see  org.springframework.core.io.ResourceLoader
 */
public abstract class ResourceUtils {

	/** Pseudo URL prefix for loading from the class path: "classpath:". */
	/**
	 * 从类路径"classpath："加载的伪URL前缀。 
	 * 
	 */
	public static final String CLASSPATH_URL_PREFIX = "classpath:";

	/** URL prefix for loading from the file system: "file:". */
	/**
	 * 从文件系统加载的URL前缀："file："。 
	 * 
	 */
	public static final String FILE_URL_PREFIX = "file:";

	/** URL prefix for loading from a jar file: "jar:". */
	/**
	 * 从jar文件加载的URL前缀："jar："。 
	 * 
	 */
	public static final String JAR_URL_PREFIX = "jar:";

	/** URL prefix for loading from a war file on Tomcat: "war:". */
	/**
	 * 从Tomcat上的war文件加载的URL前缀："war："。 
	 * 
	 */
	public static final String WAR_URL_PREFIX = "war:";

	/** URL protocol for a file in the file system: "file". */
	/**
	 * 文件系统中文件的URL协议："文件"。 
	 * 
	 */
	public static final String URL_PROTOCOL_FILE = "file";

	/** URL protocol for an entry from a jar file: "jar". */
	/**
	 * jar文件条目的URL协议："jar"。 
	 * 
	 */
	public static final String URL_PROTOCOL_JAR = "jar";

	/** URL protocol for an entry from a war file: "war". */
	/**
	 * 来自war文件的条目的URL协议："war"。 
	 * 
	 */
	public static final String URL_PROTOCOL_WAR = "war";

	/** URL protocol for an entry from a zip file: "zip". */
	/**
	 * 压缩文件中的条目的URL协议："zip"。 
	 * 
	 */
	public static final String URL_PROTOCOL_ZIP = "zip";

	/** URL protocol for an entry from a WebSphere jar file: "wsjar". */
	/**
	 * WebSphere jar文件中条目的URL协议："wsjar"。 
	 * 
	 */
	public static final String URL_PROTOCOL_WSJAR = "wsjar";

	/** URL protocol for an entry from a JBoss jar file: "vfszip". */
	/**
	 * JBoss jar文件中条目的URL协议："vfszip"。 
	 * 
	 */
	public static final String URL_PROTOCOL_VFSZIP = "vfszip";

	/** URL protocol for a JBoss file system resource: "vfsfile". */
	/**
	 * JBoss文件系统资源的URL协议："vfsfile"。 
	 * 
	 */
	public static final String URL_PROTOCOL_VFSFILE = "vfsfile";

	/** URL protocol for a general JBoss VFS resource: "vfs". */
	/**
	 * 通用JBoss VFS资源的URL协议："vfs"。 
	 * 
	 */
	public static final String URL_PROTOCOL_VFS = "vfs";

	/** File extension for a regular jar file: ".jar". */
	/**
	 * 常规jar文件的文件扩展名："。 
	 * jar"。 
	 * 
	 */
	public static final String JAR_FILE_EXTENSION = ".jar";

	/** Separator between JAR URL and file path within the JAR: "!/". */
	/**
	 * JAR URL和JAR中文件路径之间的分隔符："！ 
	 * /"。 
	 * 
	 */
	public static final String JAR_URL_SEPARATOR = "!/";

	/** Special separator between WAR URL and jar part on Tomcat. */
	/**
	 * Tomcat上WAR URL和jar部分之间的特殊分隔符。 
	 * 
	 */
	public static final String WAR_URL_SEPARATOR = "*/";


	/**
	 * Return whether the given resource location is a URL:
	 * either a special "classpath" pseudo URL or a standard URL.
	 * @param resourceLocation the location String to check
	 * @return whether the location qualifies as a URL
	 * @see #CLASSPATH_URL_PREFIX
	 * @see java.net.URL
	 */
	/**
	 * 返回给定资源位置是否为URL：特殊的"类路径"伪URL或标准URL。 
	 *  
	 * @param  resourceLocation位置字符串以检查
	 * @return 该位置是否符合URL 
	 * @see  #CLASSPATH_URL_PREFIX 
	 * @see  java.net.URL
	 */
	public static boolean isUrl(@Nullable String resourceLocation) {
		if (resourceLocation == null) {
			return false;
		}
		if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
			return true;
		}
		try {
			new URL(resourceLocation);
			return true;
		}
		catch (MalformedURLException ex) {
			return false;
		}
	}

	/**
	 * Resolve the given resource location to a {@code java.net.URL}.
	 * <p>Does not check whether the URL actually exists; simply returns
	 * the URL that the given location would correspond to.
	 * @param resourceLocation the resource location to resolve: either a
	 * "classpath:" pseudo URL, a "file:" URL, or a plain file path
	 * @return a corresponding URL object
	 * @throws FileNotFoundException if the resource cannot be resolved to a URL
	 */
	/**
	 * 将给定的资源位置解析为{@code  java.net.URL}。 
	 *  <p>不检查URL是否实际存在； 
	 * 只需返回给定位置将对应的URL。 
	 *  
	 * @param  resourceLocation要解析的资源位置："classpath："伪URL，"file："URL或纯文件路径
	 * @return 对应的URL对象
	 * @throws  FileNotFoundException如果资源不能解析为URL
	 */
	public static URL getURL(String resourceLocation) throws FileNotFoundException {
		Assert.notNull(resourceLocation, "Resource location must not be null");
		if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
			String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
			ClassLoader cl = ClassUtils.getDefaultClassLoader();
			URL url = (cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path));
			if (url == null) {
				String description = "class path resource [" + path + "]";
				throw new FileNotFoundException(description +
						" cannot be resolved to URL because it does not exist");
			}
			return url;
		}
		try {
			// try URL
			return new URL(resourceLocation);
		}
		catch (MalformedURLException ex) {
			// no URL -> treat as file path
			try {
				return new File(resourceLocation).toURI().toURL();
			}
			catch (MalformedURLException ex2) {
				throw new FileNotFoundException("Resource location [" + resourceLocation +
						"] is neither a URL not a well-formed file path");
			}
		}
	}

	/**
	 * Resolve the given resource location to a {@code java.io.File},
	 * i.e. to a file in the file system.
	 * <p>Does not check whether the file actually exists; simply returns
	 * the File that the given location would correspond to.
	 * @param resourceLocation the resource location to resolve: either a
	 * "classpath:" pseudo URL, a "file:" URL, or a plain file path
	 * @return a corresponding File object
	 * @throws FileNotFoundException if the resource cannot be resolved to
	 * a file in the file system
	 */
	/**
	 * 将给定的资源位置解析为{@code  java.io.File}，即文件系统中的文件。 
	 *  <p>不检查文件是否实际存在； 
	 * 只需返回给定位置将对应的File。 
	 *  
	 * @param  resourceLocation要解析的资源位置："classpath："伪URL，"file："URL或纯文件路径
	 * @return 相应的File对象
	 * @throws  FileNotFoundException如果资源不能解析为文件系统中的文件
	 */
	public static File getFile(String resourceLocation) throws FileNotFoundException {
		Assert.notNull(resourceLocation, "Resource location must not be null");
		if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
			String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
			String description = "class path resource [" + path + "]";
			ClassLoader cl = ClassUtils.getDefaultClassLoader();
			URL url = (cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path));
			if (url == null) {
				throw new FileNotFoundException(description +
						" cannot be resolved to absolute file path because it does not exist");
			}
			return getFile(url, description);
		}
		try {
			// try URL
			return getFile(new URL(resourceLocation));
		}
		catch (MalformedURLException ex) {
			// no URL -> treat as file path
			return new File(resourceLocation);
		}
	}

	/**
	 * Resolve the given resource URL to a {@code java.io.File},
	 * i.e. to a file in the file system.
	 * @param resourceUrl the resource URL to resolve
	 * @return a corresponding File object
	 * @throws FileNotFoundException if the URL cannot be resolved to
	 * a file in the file system
	 */
	/**
	 * 将给定的资源URL解析为{@code  java.io.File}，即文件系统中的文件。 
	 *  
	 * @param  resourceUrl如果无法将URL解析为文件系统中的文件，则使用资源URL来解析
	 * @return 相应的File对象
	 * @throws  FileNotFoundException
	 */
	public static File getFile(URL resourceUrl) throws FileNotFoundException {
		return getFile(resourceUrl, "URL");
	}

	/**
	 * Resolve the given resource URL to a {@code java.io.File},
	 * i.e. to a file in the file system.
	 * @param resourceUrl the resource URL to resolve
	 * @param description a description of the original resource that
	 * the URL was created for (for example, a class path location)
	 * @return a corresponding File object
	 * @throws FileNotFoundException if the URL cannot be resolved to
	 * a file in the file system
	 */
	/**
	 * 将给定的资源URL解析为{@code  java.io.File}，即文件系统中的文件。 
	 *  
	 * @param  resourceUrl要解析的资源URL 
	 * @param 描述创建URL的原始资源的描述（例如，类路径位置）
	 * @return 相应的File对象
	 * @throws  FileNotFoundException如果URL无法解析为文件系统中的文件
	 */
	public static File getFile(URL resourceUrl, String description) throws FileNotFoundException {
		Assert.notNull(resourceUrl, "Resource URL must not be null");
		if (!URL_PROTOCOL_FILE.equals(resourceUrl.getProtocol())) {
			throw new FileNotFoundException(
					description + " cannot be resolved to absolute file path " +
					"because it does not reside in the file system: " + resourceUrl);
		}
		try {
			return new File(toURI(resourceUrl).getSchemeSpecificPart());
		}
		catch (URISyntaxException ex) {
			// Fallback for URLs that are not valid URIs (should hardly ever happen).
			return new File(resourceUrl.getFile());
		}
	}

	/**
	 * Resolve the given resource URI to a {@code java.io.File},
	 * i.e. to a file in the file system.
	 * @param resourceUri the resource URI to resolve
	 * @return a corresponding File object
	 * @throws FileNotFoundException if the URL cannot be resolved to
	 * a file in the file system
	 * @since 2.5
	 */
	/**
	 * 将给定的资源URI解析为{@code  java.io.File}，即文件系统中的文件。 
	 *  
	 * @param  resourceUri资源URL来解析
	 * @return 相应的File对象
	 * @throws  FileNotFoundException，如果URL无法解析为文件系统中的文件（自2.5版本开始）
	 */
	public static File getFile(URI resourceUri) throws FileNotFoundException {
		return getFile(resourceUri, "URI");
	}

	/**
	 * Resolve the given resource URI to a {@code java.io.File},
	 * i.e. to a file in the file system.
	 * @param resourceUri the resource URI to resolve
	 * @param description a description of the original resource that
	 * the URI was created for (for example, a class path location)
	 * @return a corresponding File object
	 * @throws FileNotFoundException if the URL cannot be resolved to
	 * a file in the file system
	 * @since 2.5
	 */
	/**
	 * 将给定的资源URI解析为{@code  java.io.File}，即文件系统中的文件。 
	 *  
	 * @param  resourceUri要解析的资源URI 
	 * @param 描述创建URI的原始资源的描述（例如，类路径位置）
	 * @return 相应的File对象
	 * @throws  FileNotFoundException如果从2.5开始，URL无法解析为文件系统中的文件
	 */
	public static File getFile(URI resourceUri, String description) throws FileNotFoundException {
		Assert.notNull(resourceUri, "Resource URI must not be null");
		if (!URL_PROTOCOL_FILE.equals(resourceUri.getScheme())) {
			throw new FileNotFoundException(
					description + " cannot be resolved to absolute file path " +
					"because it does not reside in the file system: " + resourceUri);
		}
		return new File(resourceUri.getSchemeSpecificPart());
	}

	/**
	 * Determine whether the given URL points to a resource in the file system,
	 * i.e. has protocol "file", "vfsfile" or "vfs".
	 * @param url the URL to check
	 * @return whether the URL has been identified as a file system URL
	 */
	/**
	 * 确定给定的URL是否指向文件系统中的资源，即是否具有协议"file"，"vfsfile"或"vfs"。 
	 *  
	 * @param 对该URL进行url检查
	 * @return 该URL是否已被标识为文件系统URL
	 */
	public static boolean isFileURL(URL url) {
		String protocol = url.getProtocol();
		return (URL_PROTOCOL_FILE.equals(protocol) || URL_PROTOCOL_VFSFILE.equals(protocol) ||
				URL_PROTOCOL_VFS.equals(protocol));
	}

	/**
	 * Determine whether the given URL points to a resource in a jar file.
	 * i.e. has protocol "jar", "war, ""zip", "vfszip" or "wsjar".
	 * @param url the URL to check
	 * @return whether the URL has been identified as a JAR URL
	 */
	/**
	 * 确定给定的URL是否指向jar文件中的资源。 
	 * 即具有协议"jar"，"war"，"zip"，"vfszip"或"wsjar"。 
	 *  
	 * @param 对该URL进行url以检查
	 * @return 该URL是否已被标识为JAR URL
	 */
	public static boolean isJarURL(URL url) {
		String protocol = url.getProtocol();
		return (URL_PROTOCOL_JAR.equals(protocol) || URL_PROTOCOL_WAR.equals(protocol) ||
				URL_PROTOCOL_ZIP.equals(protocol) || URL_PROTOCOL_VFSZIP.equals(protocol) ||
				URL_PROTOCOL_WSJAR.equals(protocol));
	}

	/**
	 * Determine whether the given URL points to a jar file itself,
	 * that is, has protocol "file" and ends with the ".jar" extension.
	 * @param url the URL to check
	 * @return whether the URL has been identified as a JAR file URL
	 * @since 4.1
	 */
	/**
	 * 确定给定的URL是否指向jar文件本身，即是否具有协议"file"并以".jar"扩展名结尾。 
	 *  
	 * @param 对该URL进行url检查
	 * @return 该URL是否已从4.1开始被标识为JAR文件URL
	 */
	public static boolean isJarFileURL(URL url) {
		return (URL_PROTOCOL_FILE.equals(url.getProtocol()) &&
				url.getPath().toLowerCase().endsWith(JAR_FILE_EXTENSION));
	}

	/**
	 * Extract the URL for the actual jar file from the given URL
	 * (which may point to a resource in a jar file or to a jar file itself).
	 * @param jarUrl the original URL
	 * @return the URL for the actual jar file
	 * @throws MalformedURLException if no valid jar file URL could be extracted
	 */
	/**
	 * 从给定的URL中提取实际jar文件的URL（该URL可能指向jar文件中的资源或jar文件本身）。 
	 *  
	 * @param  jarUrl原始URL 
	 * @return 实际jar文件的URL 
	 * @throws  MalformedURLException如果无法提取有效的jar文件URL
	 */
	public static URL extractJarFileURL(URL jarUrl) throws MalformedURLException {
		String urlFile = jarUrl.getFile();
		int separatorIndex = urlFile.indexOf(JAR_URL_SEPARATOR);
		if (separatorIndex != -1) {
			String jarFile = urlFile.substring(0, separatorIndex);
			try {
				return new URL(jarFile);
			}
			catch (MalformedURLException ex) {
				// Probably no protocol in original jar URL, like "jar:C:/mypath/myjar.jar".
				// This usually indicates that the jar file resides in the file system.
				if (!jarFile.startsWith("/")) {
					jarFile = "/" + jarFile;
				}
				return new URL(FILE_URL_PREFIX + jarFile);
			}
		}
		else {
			return jarUrl;
		}
	}

	/**
	 * Extract the URL for the outermost archive from the given jar/war URL
	 * (which may point to a resource in a jar file or to a jar file itself).
	 * <p>In the case of a jar file nested within a war file, this will return
	 * a URL to the war file since that is the one resolvable in the file system.
	 * @param jarUrl the original URL
	 * @return the URL for the actual jar file
	 * @throws MalformedURLException if no valid jar file URL could be extracted
	 * @since 4.1.8
	 * @see #extractJarFileURL(URL)
	 */
	/**
	 * 从给定的jar / war URL中提取最外面的档案的URL（它可能指向jar文件中的资源或jar文件本身）。 
	 *  <p>如果jar文件嵌套在war文件中，则它将返回URL到war文件，因为该URL是文件系统中可解析的URL。 
	 *  
	 * @param  jarUrl原始URL 
	 * @return 实际jar文件的URL 
	 * @throws  MalformedURLException如果无法提取有效的jar文件URL，则从4.1.8开始
	 * @see  #extractJarFileURL（URL）
	 */
	public static URL extractArchiveURL(URL jarUrl) throws MalformedURLException {
		String urlFile = jarUrl.getFile();

		int endIndex = urlFile.indexOf(WAR_URL_SEPARATOR);
		if (endIndex != -1) {
			// Tomcat's "war:file:...mywar.war*/WEB-INF/lib/myjar.jar!/myentry.txt"
			String warFile = urlFile.substring(0, endIndex);
			if (URL_PROTOCOL_WAR.equals(jarUrl.getProtocol())) {
				return new URL(warFile);
			}
			int startIndex = warFile.indexOf(WAR_URL_PREFIX);
			if (startIndex != -1) {
				return new URL(warFile.substring(startIndex + WAR_URL_PREFIX.length()));
			}
		}

		// Regular "jar:file:...myjar.jar!/myentry.txt"
		return extractJarFileURL(jarUrl);
	}

	/**
	 * Create a URI instance for the given URL,
	 * replacing spaces with "%20" URI encoding first.
	 * @param url the URL to convert into a URI instance
	 * @return the URI instance
	 * @throws URISyntaxException if the URL wasn't a valid URI
	 * @see java.net.URL#toURI()
	 */
	/**
	 * 为给定的URL创建URI实例，首先用"％20"URI编码替换空格。 
	 *  
	 * @param  URL URL以转换为URI实例
	 * @return  URI实例
	 * @throws  URISyntaxException如果URL不是有效的URI 
	 * @see  java.net.URL＃toURI（）
	 */
	public static URI toURI(URL url) throws URISyntaxException {
		return toURI(url.toString());
	}

	/**
	 * Create a URI instance for the given location String,
	 * replacing spaces with "%20" URI encoding first.
	 * @param location the location String to convert into a URI instance
	 * @return the URI instance
	 * @throws URISyntaxException if the location wasn't a valid URI
	 */
	/**
	 * 为给定的位置字符串创建URI实例，首先用"％20"URI编码替换空格。 
	 *  
	 * @param  location将要转换为URI实例的位置String 
	 * @return  URI实例
	 * @throws  URISyntaxException如果位置不是有效的URI
	 */
	public static URI toURI(String location) throws URISyntaxException {
		return new URI(StringUtils.replace(location, " ", "%20"));
	}

	/**
	 * Set the {@link URLConnection#setUseCaches "useCaches"} flag on the
	 * given connection, preferring {@code false} but leaving the
	 * flag at {@code true} for JNLP based resources.
	 * @param con the URLConnection to set the flag on
	 */
	/**
	 * 在给定的连接上设置{@link  URLConnection＃setUseCaches"useCaches"}标志，对于基于JNLP的资源，最好使用{@code  false}，但将标志保留在{@code  true}处。 
	 *  
	 * @param 设置URLConnection来设置标志
	 */
	public static void useCachesIfNecessary(URLConnection con) {
		con.setUseCaches(con.getClass().getSimpleName().startsWith("JNLP"));
	}

}
