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

package org.springframework.web.servlet.view.document;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.web.servlet.view.AbstractView;

/**
 * Abstract superclass for PDF views. Application-specific view classes
 * will extend this class. The view will be held in the subclass itself,
 * not in a template.
 *
 * <p>This view implementation uses Bruno Lowagie's
 * <a href="https://www.lowagie.com/iText">iText</a> API.
 * Known to work with the original iText 2.1.7 as well as its fork
 * <a href="https://github.com/LibrePDF/OpenPDF">OpenPDF</a>.
 * <b>We strongly recommend OpenPDF since it is actively maintained
 * and fixes an important vulnerability for untrusted PDF content.</b>
 *
 * <p>Note: Internet Explorer requires a ".pdf" extension, as it doesn't
 * always respect the declared content type.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Jean-Pierre Pawlak
 * @see AbstractPdfStamperView
 */
/**
 * PDF视图的抽象超类。 
 * 特定于应用程序的视图类将扩展此类。 
 * 视图将保存在子类本身中，而不是模板中。 
 *  <p>此视图实现使用Bruno Lowagie的<a href="https://www.lowagie.com/iText"> iText </a> API。 
 * 已知可以与原始iText 2.1.7以及它的fork <a href="https://github.com/LibrePDF/OpenPDF"> OpenPDF </a>一起使用。 
 *  <b>我们强烈推荐OpenPDF，因为它是主动维护的，并修复了不受信任的PDF内容的重要漏洞。 
 * </ b> <p>注意：Internet Explorer需要扩展名为".pdf"，因为它并不总是遵守声明的。 
 * 内容类型。 
 *  @author  Rod Johnson @author  Juergen Hoeller @author  Jean-Pierre Pawlak 
 * @see  AbstractPdfStamperView
 */
public abstract class AbstractPdfView extends AbstractView {

	/**
	 * This constructor sets the appropriate content type "application/pdf".
	 * Note that IE won't take much notice of this, but there's not a lot we
	 * can do about this. Generated documents should have a ".pdf" extension.
	 */
	/**
	 * 该构造函数设置适当的内容类型"application / pdf"。 
	 * 请注意，IE对此不会引起太多注意，但是对此我们无能为力。 
	 * 生成的文档应具有".pdf"扩展名。 
	 * 
	 */
	public AbstractPdfView() {
		setContentType("application/pdf");
	}


	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected final void renderMergedOutputModel(
			Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// IE workaround: write into byte array first.
		ByteArrayOutputStream baos = createTemporaryOutputStream();

		// Apply preferences and build metadata.
		Document document = newDocument();
		PdfWriter writer = newWriter(document, baos);
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);

		// Build PDF document.
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();

		// Flush to HTTP response.
		writeToResponse(response, baos);
	}

	/**
	 * Create a new document to hold the PDF contents.
	 * <p>By default returns an A4 document, but the subclass can specify any
	 * Document, possibly parameterized via bean properties defined on the View.
	 * @return the newly created iText Document instance
	 * @see com.lowagie.text.Document#Document(com.lowagie.text.Rectangle)
	 */
	/**
	 * 创建一个新文档以保存PDF内容。 
	 *  <p>默认情况下会返回A4文档，但是子类可以指定任何文档，可能通过在View上定义的bean属性进行参数化。 
	 *  
	 * @return 新创建的iText文档实例
	 * @see  com.lowagie.text.Document＃Document（com.lowagie.text.Rectangle）
	 */
	protected Document newDocument() {
		return new Document(PageSize.A4);
	}

	/**
	 * Create a new PdfWriter for the given iText Document.
	 * @param document the iText Document to create a writer for
	 * @param os the OutputStream to write to
	 * @return the PdfWriter instance to use
	 * @throws DocumentException if thrown during writer creation
	 */
	/**
	 * 为给定的iText文档创建一个新的PdfWriter。 
	 *  
	 * @param 文档将iText文档创建为
	 * @param 的编写器，os OutputStream写入PdfWriter实例的
	 * @return  PdfWriter实例，以在编写器创建过程中抛出
	 * @throws  DocumentException
	 */
	protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
		return PdfWriter.getInstance(document, os);
	}

	/**
	 * Prepare the given PdfWriter. Called before building the PDF document,
	 * that is, before the call to {@code Document.open()}.
	 * <p>Useful for registering a page event listener, for example.
	 * The default implementation sets the viewer preferences as returned
	 * by this class's {@code getViewerPreferences()} method.
	 * @param model the model, in case meta information must be populated from it
	 * @param writer the PdfWriter to prepare
	 * @param request in case we need locale etc. Shouldn't look at attributes.
	 * @throws DocumentException if thrown during writer preparation
	 * @see com.lowagie.text.Document#open()
	 * @see com.lowagie.text.pdf.PdfWriter#setPageEvent
	 * @see com.lowagie.text.pdf.PdfWriter#setViewerPreferences
	 * @see #getViewerPreferences()
	 */
	/**
	 * 准备给定的PdfWriter。 
	 * 在构建PDF文档之前调用，即在调用{@code  Document.open（）}之前调用。 
	 *  <p>例如，用于注册页面事件侦听器。 
	 * 默认实现将设置此类的{@code  getViewerPreferences（）}方法返回的查看器首选项。 
	 *  
	 * @param 为模型建模，以防万一必须从中填充元信息。 
	 * 
	 * @param 编写器PdfWriter编写
	 * @param 请求，以防我们需要语言环境等。 
	 * 不应查看属性。 
	 *  
	 * @throws  DocumentException，如果在编写者准备工作期间抛出
	 * @see  com.lowagie.text.Document＃open（）
	 * @see  com.lowagie.text.pdf.PdfWriter＃setPageEvent 
	 * @see  com.lowagie.text。 
	 *  pdf.PdfWriter＃setViewerPreferences 
	 * @see  #getViewerPreferences（）
	 */
	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
			throws DocumentException {

		writer.setViewerPreferences(getViewerPreferences());
	}

	/**
	 * Return the viewer preferences for the PDF file.
	 * <p>By default returns {@code AllowPrinting} and
	 * {@code PageLayoutSinglePage}, but can be subclassed.
	 * The subclass can either have fixed preferences or retrieve
	 * them from bean properties defined on the View.
	 * @return an int containing the bits information against PdfWriter definitions
	 * @see com.lowagie.text.pdf.PdfWriter#AllowPrinting
	 * @see com.lowagie.text.pdf.PdfWriter#PageLayoutSinglePage
	 */
	/**
	 * 返回PDF文件的查看器首选项。 
	 *  <p>默认情况下返回{@code  AllowPrinting}和{@code  PageLayoutSinglePage}，但可以将其子类化。 
	 * 子类可以具有固定的首选项，也可以从View上定义的bean属性中检索它们。 
	 *  
	 * @return 包含针对PdfWriter定义的位信息的int 
	 * @see  com.lowagie.text.pdf.PdfWriter＃AllowPrinting 
	 * @see  com.lowagie.text.pdf.PdfWriter＃PageLayoutSinglePage
	 */
	protected int getViewerPreferences() {
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}

	/**
	 * Populate the iText Document's meta fields (author, title, etc.).
	 * <br>Default is an empty implementation. Subclasses may override this method
	 * to add meta fields such as title, subject, author, creator, keywords, etc.
	 * This method is called after assigning a PdfWriter to the Document and
	 * before calling {@code document.open()}.
	 * @param model the model, in case meta information must be populated from it
	 * @param document the iText document being populated
	 * @param request in case we need locale etc. Shouldn't look at attributes.
	 * @see com.lowagie.text.Document#addTitle
	 * @see com.lowagie.text.Document#addSubject
	 * @see com.lowagie.text.Document#addKeywords
	 * @see com.lowagie.text.Document#addAuthor
	 * @see com.lowagie.text.Document#addCreator
	 * @see com.lowagie.text.Document#addProducer
	 * @see com.lowagie.text.Document#addCreationDate
	 * @see com.lowagie.text.Document#addHeader
	 */
	/**
	 * 填充iText文档的元字段（作者，标题等）。 
	 *  <br>默认为空实施。 
	 * 子类可以重写此方法以添加元字段，例如标题，主题，作者，创建者，关键字等。 
	 * 在为文档分配PdfWriter之后，在调用{@code  document.open（）}之前，将调用此方法。 
	 *  
	 * @param 为模型建模，以防万一必须从其中填充元信息
	 * @param 文档中要填充的iText文档
	 * @param 请求（如果我们需要语言环境等）。 
	 * 不应查看属性。 
	 *  
	 * @see  com.lowagie.text.Document＃addTitle 
	 * @see  com.lowagie.text.Document＃addSubject 
	 * @see  com.lowagie.text.Document＃addKeywords 
	 * @see  com.lowagie.text.Document #addAuthor 
	 * @see  com.lowagie.text.Document＃addCreator 
	 * @see  com.lowagie.text.Document＃addProducer 
	 * @see  com.lowagie.text.Document＃addCreationDate 
	 * @see  com.lowagie.text .Document＃addHeader
	 */
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
	}

	/**
	 * Subclasses must implement this method to build an iText PDF document,
	 * given the model. Called between {@code Document.open()} and
	 * {@code Document.close()} calls.
	 * <p>Note that the passed-in HTTP response is just supposed to be used
	 * for setting cookies or other HTTP headers. The built PDF document itself
	 * will automatically get written to the response after this method returns.
	 * @param model the model Map
	 * @param document the iText Document to add elements to
	 * @param writer the PdfWriter to use
	 * @param request in case we need locale etc. Shouldn't look at attributes.
	 * @param response in case we need to set cookies. Shouldn't write to it.
	 * @throws Exception any exception that occurred during document building
	 * @see com.lowagie.text.Document#open()
	 * @see com.lowagie.text.Document#close()
	 */
	/**
	 * 给定模型，子类必须实现此方法才能构建iText PDF文档。 
	 * 在{@code  Document.open（）}和{@code  Document.close（）}调用之间调用。 
	 *  <p>请注意，传入的HTTP响应仅应用于设置cookie或其他HTTP标头。 
	 * 此方法返回后，内置的PDF文档本身将自动写入响应中。 
	 *  
	 * @param 为模型Map建模
	 * @param 文档iText文档，以便向
	 * @param  writer PdfWriter编写者添加元素，以在需要区域设置等情况下使用
	 * @param 请求。 
	 * 不应查看属性。 
	 *  
	 * @param 响应，以防我们需要设置cookie。 
	 * 不应该写。 
	 *  
	 * @throws 异常在文档构建期间发生的任何异常
	 * @see  com.lowagie.text.Document＃open（）
	 * @see  com.lowagie.text.Document＃close（）
	 */
	protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

}
