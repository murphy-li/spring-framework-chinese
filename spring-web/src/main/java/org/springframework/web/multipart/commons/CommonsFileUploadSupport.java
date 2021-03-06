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

package org.springframework.web.multipart.commons;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.io.Resource;
import org.springframework.core.log.LogFormatUtils;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

/**
 * Base class for multipart resolvers that use Apache Commons FileUpload
 * 1.2 or above.
 *
 * <p>Provides common configuration properties and parsing functionality
 * for multipart requests, using a Map of Spring CommonsMultipartFile instances
 * as representation of uploaded files and a String-based parameter Map as
 * representation of uploaded form fields.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see CommonsMultipartFile
 * @see CommonsMultipartResolver
 */
/**
 * 使用Apache Commons FileUpload 1.2或更高版本的多部分解析程序的基类。 
 *  <p>使用Spring CommonsMultipartFile实例的映射表示上载的文件，使用基于String的参数Map表示上载的表单字段，为多部分请求提供通用的配置属性和解析功能。 
 *  @author  Juergen Hoeller @始于2.0 
 * @see  CommonsMultipartFile 
 * @see  CommonsMultipartResolver
 */
public abstract class CommonsFileUploadSupport {

	protected final Log logger = LogFactory.getLog(getClass());

	private final DiskFileItemFactory fileItemFactory;

	private final FileUpload fileUpload;

	private boolean uploadTempDirSpecified = false;

	private boolean preserveFilename = false;


	/**
	 * Instantiate a new CommonsFileUploadSupport with its
	 * corresponding FileItemFactory and FileUpload instances.
	 * @see #newFileItemFactory
	 * @see #newFileUpload
	 */
	/**
	 * 用其对应的FileItemFactory和FileUpload实例实例化一个新的CommonsFileUploadSupport。 
	 *  
	 * @see  #newFileItemFactory 
	 * @see  #newFileUpload
	 */
	public CommonsFileUploadSupport() {
		this.fileItemFactory = newFileItemFactory();
		this.fileUpload = newFileUpload(getFileItemFactory());
	}


	/**
	 * Return the underlying {@code org.apache.commons.fileupload.disk.DiskFileItemFactory}
	 * instance. There is hardly any need to access this.
	 * @return the underlying DiskFileItemFactory instance
	 */
	/**
	 * 返回基础的{@code  org.apache.commons.fileupload.disk.DiskFileItemFactory}实例。 
	 * 几乎不需要访问它。 
	 *  
	 * @return 基础DiskFileItemFactory实例
	 */
	public DiskFileItemFactory getFileItemFactory() {
		return this.fileItemFactory;
	}

	/**
	 * Return the underlying {@code org.apache.commons.fileupload.FileUpload}
	 * instance. There is hardly any need to access this.
	 * @return the underlying FileUpload instance
	 */
	/**
	 * 返回基础的{@code  org.apache.commons.fileupload.FileUpload}实例。 
	 * 几乎不需要访问它。 
	 *  
	 * @return 基础FileUpload实例
	 */
	public FileUpload getFileUpload() {
		return this.fileUpload;
	}

	/**
	 * Set the maximum allowed size (in bytes) before an upload gets rejected.
	 * -1 indicates no limit (the default).
	 * @param maxUploadSize the maximum upload size allowed
	 * @see org.apache.commons.fileupload.FileUploadBase#setSizeMax
	 */
	/**
	 * 设置上传被拒绝之前允许的最大大小（以字节为单位）。 
	 *  -1表示无限制（默认）。 
	 *  
	 * @param  maxUploadSize允许的最大上传大小
	 * @see  org.apache.commons.fileupload.FileUploadBase＃setSizeMax
	 */
	public void setMaxUploadSize(long maxUploadSize) {
		this.fileUpload.setSizeMax(maxUploadSize);
	}

	/**
	 * Set the maximum allowed size (in bytes) for each individual file before
	 * an upload gets rejected. -1 indicates no limit (the default).
	 * @param maxUploadSizePerFile the maximum upload size per file
	 * @since 4.2
	 * @see org.apache.commons.fileupload.FileUploadBase#setFileSizeMax
	 */
	/**
	 * 在拒绝上传之前，为每个文件设置允许的最大大小（以字节为单位）。 
	 *  -1表示无限制（默认）。 
	 *  
	 * @param  maxUploadSizePerFile每个文件的最大上传大小（自4.2开始）
	 * @see  org.apache.commons.fileupload.FileUploadBase＃setFileSizeMax
	 */
	public void setMaxUploadSizePerFile(long maxUploadSizePerFile) {
		this.fileUpload.setFileSizeMax(maxUploadSizePerFile);
	}

	/**
	 * Set the maximum allowed size (in bytes) before uploads are written to disk.
	 * Uploaded files will still be received past this amount, but they will not be
	 * stored in memory. Default is 10240, according to Commons FileUpload.
	 * @param maxInMemorySize the maximum in memory size allowed
	 * @see org.apache.commons.fileupload.disk.DiskFileItemFactory#setSizeThreshold
	 */
	/**
	 * 设置在将上传内容写入磁盘之前允许的最大大小（以字节为单位）。 
	 * 超过此数量仍将接收上载的文件，但不会将其存储在内存中。 
	 * 根据Commons FileUpload，默认值为10240。 
	 *  
	 * @param  maxInMemorySize允许的最大内存大小
	 * @see  org.apache.commons.fileupload.disk.DiskFileItemFactory＃setSizeThreshold
	 */
	public void setMaxInMemorySize(int maxInMemorySize) {
		this.fileItemFactory.setSizeThreshold(maxInMemorySize);
	}

	/**
	 * Set the default character encoding to use for parsing requests,
	 * to be applied to headers of individual parts and to form fields.
	 * Default is ISO-8859-1, according to the Servlet spec.
	 * <p>If the request specifies a character encoding itself, the request
	 * encoding will override this setting. This also allows for generically
	 * overriding the character encoding in a filter that invokes the
	 * {@code ServletRequest.setCharacterEncoding} method.
	 * @param defaultEncoding the character encoding to use
	 * @see javax.servlet.ServletRequest#getCharacterEncoding
	 * @see javax.servlet.ServletRequest#setCharacterEncoding
	 * @see WebUtils#DEFAULT_CHARACTER_ENCODING
	 * @see org.apache.commons.fileupload.FileUploadBase#setHeaderEncoding
	 */
	/**
	 * 设置用于解析请求的默认字符编码，该默认字符编码将应用于各个部分的标题以及表单字段。 
	 * 根据Servlet规范，默认值为ISO-8859-1。 
	 *  <p>如果请求本身指定了字符编码，则请求编码将覆盖此设置。 
	 * 这也允许在调用{@code  ServletRequest.setCharacterEncoding}方法的过滤器中通用重写字符编码。 
	 *  
	 * @param  defaultEncoding字符编码以使用
	 * @see  javax.servlet.ServletRequest＃getCharacterEncoding 
	 * @see  javax.servlet.ServletRequest＃setCharacterEncoding 
	 * @see  WebUtils＃DEFAULT_CHARACTER_ENCODING 
	 * @see  org.apache.commons.fileupload .FileUploadBase＃setHeaderEncoding
	 */
	public void setDefaultEncoding(String defaultEncoding) {
		this.fileUpload.setHeaderEncoding(defaultEncoding);
	}

	/**
	 * Determine the default encoding to use for parsing requests.
	 * @see #setDefaultEncoding
	 */
	/**
	 * 确定用于解析请求的默认编码。 
	 *  
	 * @see  #setDefaultEncoding
	 */
	protected String getDefaultEncoding() {
		String encoding = getFileUpload().getHeaderEncoding();
		if (encoding == null) {
			encoding = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		return encoding;
	}

	/**
	 * Set the temporary directory where uploaded files get stored.
	 * Default is the servlet container's temporary directory for the web application.
	 * @see org.springframework.web.util.WebUtils#TEMP_DIR_CONTEXT_ATTRIBUTE
	 */
	/**
	 * 设置存储上传文件的临时目录。 
	 * 默认值为Web应用程序的servlet容器的临时目录。 
	 *  
	 * @see  org.springframework.web.util.WebUtils＃TEMP_DIR_CONTEXT_ATTRIBUTE
	 */
	public void setUploadTempDir(Resource uploadTempDir) throws IOException {
		if (!uploadTempDir.exists() && !uploadTempDir.getFile().mkdirs()) {
			throw new IllegalArgumentException("Given uploadTempDir [" + uploadTempDir + "] could not be created");
		}
		this.fileItemFactory.setRepository(uploadTempDir.getFile());
		this.uploadTempDirSpecified = true;
	}

	/**
	 * Return the temporary directory where uploaded files get stored.
	 * @see #setUploadTempDir
	 */
	/**
	 * 返回存储上传文件的临时目录。 
	 *  
	 * @see  #setUploadTempDir
	 */
	protected boolean isUploadTempDirSpecified() {
		return this.uploadTempDirSpecified;
	}

	/**
	 * Set whether to preserve the filename as sent by the client, not stripping off
	 * path information in {@link CommonsMultipartFile#getOriginalFilename()}.
	 * <p>Default is "false", stripping off path information that may prefix the
	 * actual filename e.g. from Opera. Switch this to "true" for preserving the
	 * client-specified filename as-is, including potential path separators.
	 * @since 4.3.5
	 * @see MultipartFile#getOriginalFilename()
	 * @see CommonsMultipartFile#setPreserveFilename(boolean)
	 */
	/**
	 * 设置是否保留客户端发送的文件名，而不剥离{@link  CommonsMultipartFile＃getOriginalFilename（）}中的路径信息。 
	 *  <p>默认值为"false"，会删除可能在实际文件名前加上前缀的路径信息，例如来自Opera。 
	 * 将此选项切换为"true"以保留客户端指定的文件名，包括潜在的路径分隔符。 
	 *  @since 4.3.5 
	 * @see  MultipartFile＃getOriginalFilename（）
	 * @see  CommonsMultipartFile＃setPreserveFilename（boolean）
	 */
	public void setPreserveFilename(boolean preserveFilename) {
		this.preserveFilename = preserveFilename;
	}


	/**
	 * Factory method for a Commons DiskFileItemFactory instance.
	 * <p>Default implementation returns a standard DiskFileItemFactory.
	 * Can be overridden to use a custom subclass, e.g. for testing purposes.
	 * @return the new DiskFileItemFactory instance
	 */
	/**
	 * Commons DiskFileItemFactory实例的工厂方法。 
	 *  <p>默认实现返回一个标准DiskFileItemFactory。 
	 * 可以覆盖以使用自定义子类，例如用于测试目的。 
	 *  
	 * @return 新的DiskFileItemFactory实例
	 */
	protected DiskFileItemFactory newFileItemFactory() {
		return new DiskFileItemFactory();
	}

	/**
	 * Factory method for a Commons FileUpload instance.
	 * <p><b>To be implemented by subclasses.</b>
	 * @param fileItemFactory the Commons FileItemFactory to build upon
	 * @return the Commons FileUpload instance
	 */
	/**
	 * Commons FileUpload实例的工厂方法。 
	 *  <p> <b>要由子类实现。 
	 * </ b> 
	 * @param  fileItemFactory Commons FileItemFactory基于
	 * @return  Commons FileUpload实例构建
	 */
	protected abstract FileUpload newFileUpload(FileItemFactory fileItemFactory);


	/**
	 * Determine an appropriate FileUpload instance for the given encoding.
	 * <p>Default implementation returns the shared FileUpload instance
	 * if the encoding matches, else creates a new FileUpload instance
	 * with the same configuration other than the desired encoding.
	 * @param encoding the character encoding to use
	 * @return an appropriate FileUpload instance.
	 */
	/**
	 * 为给定的编码确定适当的FileUpload实例。 
	 *  <p>如果编码匹配，则默认实现将返回共享的FileUpload实例，否则将使用所需编码以外的相同配置创建一个新的FileUpload实例。 
	 *  
	 * @param 编码字符编码以使用
	 * @return 适当的FileUpload实例。 
	 * 
	 */
	protected FileUpload prepareFileUpload(@Nullable String encoding) {
		FileUpload fileUpload = getFileUpload();
		FileUpload actualFileUpload = fileUpload;

		// Use new temporary FileUpload instance if the request specifies
		// its own encoding that does not match the default encoding.
		if (encoding != null && !encoding.equals(fileUpload.getHeaderEncoding())) {
			actualFileUpload = newFileUpload(getFileItemFactory());
			actualFileUpload.setSizeMax(fileUpload.getSizeMax());
			actualFileUpload.setFileSizeMax(fileUpload.getFileSizeMax());
			actualFileUpload.setHeaderEncoding(encoding);
		}

		return actualFileUpload;
	}

	/**
	 * Parse the given List of Commons FileItems into a Spring MultipartParsingResult,
	 * containing Spring MultipartFile instances and a Map of multipart parameter.
	 * @param fileItems the Commons FileItems to parse
	 * @param encoding the encoding to use for form fields
	 * @return the Spring MultipartParsingResult
	 * @see CommonsMultipartFile#CommonsMultipartFile(org.apache.commons.fileupload.FileItem)
	 */
	/**
	 * 将给定的Commons FileItems列表解析为一个Spring MultipartParsingResult，其中包含Spring MultipartFile实例和一个multipart参数Map。 
	 *  
	 * @param  fileItems要解析的Commons FileItems 
	 * @param 编码用于表单字段的编码
	 * @return  Spring MultipartParsingResult 
	 * @see  CommonsMultipartFile＃CommonsMultipartFile（org.apache.commons.fileupload.FileItem）
	 */
	protected MultipartParsingResult parseFileItems(List<FileItem> fileItems, String encoding) {
		MultiValueMap<String, MultipartFile> multipartFiles = new LinkedMultiValueMap<>();
		Map<String, String[]> multipartParameters = new HashMap<>();
		Map<String, String> multipartParameterContentTypes = new HashMap<>();

		// Extract multipart files and multipart parameters.
		for (FileItem fileItem : fileItems) {
			if (fileItem.isFormField()) {
				String value;
				String partEncoding = determineEncoding(fileItem.getContentType(), encoding);
				try {
					value = fileItem.getString(partEncoding);
				}
				catch (UnsupportedEncodingException ex) {
					if (logger.isWarnEnabled()) {
						logger.warn("Could not decode multipart item '" + fileItem.getFieldName() +
								"' with encoding '" + partEncoding + "': using platform default");
					}
					value = fileItem.getString();
				}
				String[] curParam = multipartParameters.get(fileItem.getFieldName());
				if (curParam == null) {
					// simple form field
					multipartParameters.put(fileItem.getFieldName(), new String[] {value});
				}
				else {
					// array of simple form fields
					String[] newParam = StringUtils.addStringToArray(curParam, value);
					multipartParameters.put(fileItem.getFieldName(), newParam);
				}
				multipartParameterContentTypes.put(fileItem.getFieldName(), fileItem.getContentType());
			}
			else {
				// multipart file field
				CommonsMultipartFile file = createMultipartFile(fileItem);
				multipartFiles.add(file.getName(), file);
				LogFormatUtils.traceDebug(logger, traceOn ->
						"Part '" + file.getName() + "', size " + file.getSize() +
								" bytes, filename='" + file.getOriginalFilename() + "'" +
								(traceOn ? ", storage=" + file.getStorageDescription() : "")
				);
			}
		}
		return new MultipartParsingResult(multipartFiles, multipartParameters, multipartParameterContentTypes);
	}

	/**
	 * Create a {@link CommonsMultipartFile} wrapper for the given Commons {@link FileItem}.
	 * @param fileItem the Commons FileItem to wrap
	 * @return the corresponding CommonsMultipartFile (potentially a custom subclass)
	 * @since 4.3.5
	 * @see #setPreserveFilename(boolean)
	 * @see CommonsMultipartFile#setPreserveFilename(boolean)
	 */
	/**
	 * 为给定的Commons {@link  FileItem}创建一个{@link  CommonsMultipartFile}包装器。 
	 *  
	 * @param  fileItem Commons FileItem要包装
	 * @return 相应的CommonsMultipartFile（可能是自定义子类）@since 4.3.5 
	 * @see  #setPreserveFilename（boolean）
	 * @see  CommonsMultipartFile＃setPreserveFilename（boolean）
	 */
	protected CommonsMultipartFile createMultipartFile(FileItem fileItem) {
		CommonsMultipartFile multipartFile = new CommonsMultipartFile(fileItem);
		multipartFile.setPreserveFilename(this.preserveFilename);
		return multipartFile;
	}

	/**
	 * Cleanup the Spring MultipartFiles created during multipart parsing,
	 * potentially holding temporary data on disk.
	 * <p>Deletes the underlying Commons FileItem instances.
	 * @param multipartFiles a Collection of MultipartFile instances
	 * @see org.apache.commons.fileupload.FileItem#delete()
	 */
	/**
	 * 清理在多部分分析过程中创建的Spring MultipartFiles，可能将临时数据保存在磁盘上。 
	 *  <p>删除基础Commons FileItem实例。 
	 *  
	 * @param  multipartFiles MultipartFile实例的集合
	 * @see  org.apache.commons.fileupload.FileItem＃delete（）
	 */
	protected void cleanupFileItems(MultiValueMap<String, MultipartFile> multipartFiles) {
		for (List<MultipartFile> files : multipartFiles.values()) {
			for (MultipartFile file : files) {
				if (file instanceof CommonsMultipartFile) {
					CommonsMultipartFile cmf = (CommonsMultipartFile) file;
					cmf.getFileItem().delete();
					LogFormatUtils.traceDebug(logger, traceOn ->
							"Cleaning up part '" + cmf.getName() +
									"', filename '" + cmf.getOriginalFilename() + "'" +
									(traceOn ? ", stored " + cmf.getStorageDescription() : ""));
				}
			}
		}
	}

	private String determineEncoding(String contentTypeHeader, String defaultEncoding) {
		if (!StringUtils.hasText(contentTypeHeader)) {
			return defaultEncoding;
		}
		MediaType contentType = MediaType.parseMediaType(contentTypeHeader);
		Charset charset = contentType.getCharset();
		return (charset != null ? charset.name() : defaultEncoding);
	}


	/**
	 * Holder for a Map of Spring MultipartFiles and a Map of
	 * multipart parameters.
	 */
	/**
	 * Spring MultipartFiles映射和multipart参数映射的持有人。 
	 * 
	 */
	protected static class MultipartParsingResult {

		private final MultiValueMap<String, MultipartFile> multipartFiles;

		private final Map<String, String[]> multipartParameters;

		private final Map<String, String> multipartParameterContentTypes;

		public MultipartParsingResult(MultiValueMap<String, MultipartFile> mpFiles,
				Map<String, String[]> mpParams, Map<String, String> mpParamContentTypes) {

			this.multipartFiles = mpFiles;
			this.multipartParameters = mpParams;
			this.multipartParameterContentTypes = mpParamContentTypes;
		}

		public MultiValueMap<String, MultipartFile> getMultipartFiles() {
			return this.multipartFiles;
		}

		public Map<String, String[]> getMultipartParameters() {
			return this.multipartParameters;
		}

		public Map<String, String> getMultipartParameterContentTypes() {
			return this.multipartParameterContentTypes;
		}
	}

}
