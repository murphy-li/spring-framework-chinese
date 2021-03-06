/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
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
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.NestedIOException;
import org.springframework.lang.Nullable;
import org.springframework.util.ResourceUtils;

/**
 * Convenience base class for {@link Resource} implementations,
 * pre-implementing typical behavior.
 *
 * <p>The "exists" method will check whether a File or InputStream can
 * be opened; "isOpen" will always return false; "getURL" and "getFile"
 * throw an exception; and "toString" will return the description.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 28.12.2003
 */
/**
 * {@link  Resource}实现的便捷基类，预先实现典型行为。 
 *  <p>"exists"方法将检查是否可以打开File或InputStream； 
 *  "isOpen"将始终返回false； 
 *  "getURL"和"getFile"引发异常； 
 * 和"toString"将返回描述。 
 *  @author  Juergen Hoeller @author  Sam Brannen @自28.12.2003起
 */
public abstract class AbstractResource implements Resource {

	/**
	 * This implementation checks whether a File can be opened,
	 * falling back to whether an InputStream can be opened.
	 * This will cover both directories and content resources.
	 */
	/**
	 * 此实现检查是否可以打开File，回退到是否可以打开InputStream。 
	 * 这将涵盖目录和内容资源。 
	 * 
	 */
	@Override
	public boolean exists() {
		// Try file existence: can we find the file in the file system?
		if (isFile()) {
			try {
				return getFile().exists();
			}
			catch (IOException ex) {
				Log logger = LogFactory.getLog(getClass());
				if (logger.isDebugEnabled()) {
					logger.debug("Could not retrieve File for existence check of " + getDescription(), ex);
				}
			}
		}
		// Fall back to stream existence: can we open the stream?
		try {
			getInputStream().close();
			return true;
		}
		catch (Throwable ex) {
			Log logger = LogFactory.getLog(getClass());
			if (logger.isDebugEnabled()) {
				logger.debug("Could not retrieve InputStream for existence check of " + getDescription(), ex);
			}
			return false;
		}
	}

	/**
	 * This implementation always returns {@code true} for a resource
	 * that {@link #exists() exists} (revised as of 5.1).
	 */
	/**
	 * 对于{@link  #exists（）存在}的资源，此实现始终返回{@code  true}（从5.1开始修订）。 
	 * 
	 */
	@Override
	public boolean isReadable() {
		return exists();
	}

	/**
	 * This implementation always returns {@code false}.
	 */
	/**
	 * 此实现始终返回{@code  false}。 
	 * 
	 */
	@Override
	public boolean isOpen() {
		return false;
	}

	/**
	 * This implementation always returns {@code false}.
	 */
	/**
	 * 此实现始终返回{@code  false}。 
	 * 
	 */
	@Override
	public boolean isFile() {
		return false;
	}

	/**
	 * This implementation throws a FileNotFoundException, assuming
	 * that the resource cannot be resolved to a URL.
	 */
	/**
	 * 假定无法将资源解析为URL，则此实现将引发FileNotFoundException。 
	 * 
	 */
	@Override
	public URL getURL() throws IOException {
		throw new FileNotFoundException(getDescription() + " cannot be resolved to URL");
	}

	/**
	 * This implementation builds a URI based on the URL returned
	 * by {@link #getURL()}.
	 */
	/**
	 * 此实现基于{@link  #getURL（）}返回的URL构建URI。 
	 * 
	 */
	@Override
	public URI getURI() throws IOException {
		URL url = getURL();
		try {
			return ResourceUtils.toURI(url);
		}
		catch (URISyntaxException ex) {
			throw new NestedIOException("Invalid URI [" + url + "]", ex);
		}
	}

	/**
	 * This implementation throws a FileNotFoundException, assuming
	 * that the resource cannot be resolved to an absolute file path.
	 */
	/**
	 * 假定无法将资源解析为绝对文件路径，则此实现将引发FileNotFoundException。 
	 * 
	 */
	@Override
	public File getFile() throws IOException {
		throw new FileNotFoundException(getDescription() + " cannot be resolved to absolute file path");
	}

	/**
	 * This implementation returns {@link Channels#newChannel(InputStream)}
	 * with the result of {@link #getInputStream()}.
	 * <p>This is the same as in {@link Resource}'s corresponding default method
	 * but mirrored here for efficient JVM-level dispatching in a class hierarchy.
	 */
	/**
	 * 此实现返回{@link  Channels＃newChannel（InputStream）}，结果为{@link  #getInputStream（）}。 
	 *  <p>这与{@link  Resource}的相应默认方法中的相同，但在此处进行了镜像，以便在类层次结构中进行有效的JVM级别的分派。 
	 * 
	 */
	@Override
	public ReadableByteChannel readableChannel() throws IOException {
		return Channels.newChannel(getInputStream());
	}

	/**
	 * This implementation reads the entire InputStream to calculate the
	 * content length. Subclasses will almost always be able to provide
	 * a more optimal version of this, e.g. checking a File length.
	 * @see #getInputStream()
	 */
	/**
	 * 此实现读取整个InputStream以计算内容长度。 
	 * 子类几乎总是能够提供更优化的版本，例如检查文件长度。 
	 *  
	 * @see  #getInputStream（）
	 */
	@Override
	public long contentLength() throws IOException {
		InputStream is = getInputStream();
		try {
			long size = 0;
			byte[] buf = new byte[256];
			int read;
			while ((read = is.read(buf)) != -1) {
				size += read;
			}
			return size;
		}
		finally {
			try {
				is.close();
			}
			catch (IOException ex) {
				Log logger = LogFactory.getLog(getClass());
				if (logger.isDebugEnabled()) {
					logger.debug("Could not close content-length InputStream for " + getDescription(), ex);
				}
			}
		}
	}

	/**
	 * This implementation checks the timestamp of the underlying File,
	 * if available.
	 * @see #getFileForLastModifiedCheck()
	 */
	/**
	 * 此实现检查基础文件的时间戳（如果有）。 
	 *  
	 * @see  #getFileForLastModifiedCheck（）
	 */
	@Override
	public long lastModified() throws IOException {
		File fileToCheck = getFileForLastModifiedCheck();
		long lastModified = fileToCheck.lastModified();
		if (lastModified == 0L && !fileToCheck.exists()) {
			throw new FileNotFoundException(getDescription() +
					" cannot be resolved in the file system for checking its last-modified timestamp");
		}
		return lastModified;
	}

	/**
	 * Determine the File to use for timestamp checking.
	 * <p>The default implementation delegates to {@link #getFile()}.
	 * @return the File to use for timestamp checking (never {@code null})
	 * @throws FileNotFoundException if the resource cannot be resolved as
	 * an absolute file path, i.e. is not available in a file system
	 * @throws IOException in case of general resolution/reading failures
	 */
	/**
	 * 确定用于时间戳检查的文件。 
	 *  <p>默认实现委托给{@link  #getFile（）}。 
	 *  
	 * @return 用于时间戳检查的文件（决不{<@@code> null}）
	 * @throws 如果资源无法解析为绝对文件路径，即在文件系统中不可用，则为FileNotFoundException 
	 * @throws 常规解析/读取失败时出现IOException
	 */
	protected File getFileForLastModifiedCheck() throws IOException {
		return getFile();
	}

	/**
	 * This implementation throws a FileNotFoundException, assuming
	 * that relative resources cannot be created for this resource.
	 */
	/**
	 * 假定无法为该资源创建相对资源，则此实现将引发FileNotFoundException。 
	 * 
	 */
	@Override
	public Resource createRelative(String relativePath) throws IOException {
		throw new FileNotFoundException("Cannot create a relative resource for " + getDescription());
	}

	/**
	 * This implementation always returns {@code null},
	 * assuming that this resource type does not have a filename.
	 */
	/**
	 * 假定此资源类型没有文件名，则此实现始终返回{@code  null}。 
	 * 
	 */
	@Override
	@Nullable
	public String getFilename() {
		return null;
	}


	/**
	 * This implementation compares description strings.
	 * @see #getDescription()
	 */
	/**
	 * 此实现比较描述字符串。 
	 *  
	 * @see  #getDescription（）
	 */
	@Override
	public boolean equals(@Nullable Object other) {
		return (this == other || (other instanceof Resource &&
				((Resource) other).getDescription().equals(getDescription())));
	}

	/**
	 * This implementation returns the description's hash code.
	 * @see #getDescription()
	 */
	/**
	 * 此实现返回描述的哈希码。 
	 *  
	 * @see  #getDescription（）
	 */
	@Override
	public int hashCode() {
		return getDescription().hashCode();
	}

	/**
	 * This implementation returns the description of this resource.
	 * @see #getDescription()
	 */
	/**
	 * 此实现返回此资源的描述。 
	 *  
	 * @see  #getDescription（）
	 */
	@Override
	public String toString() {
		return getDescription();
	}

}
