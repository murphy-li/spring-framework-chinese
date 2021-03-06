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

package org.springframework.context.support;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;

/**
 * Helper class for easy access to messages from a MessageSource,
 * providing various overloaded getMessage methods.
 *
 * <p>Available from ApplicationObjectSupport, but also reusable
 * as a standalone helper to delegate to in application objects.
 *
 * @author Juergen Hoeller
 * @since 23.10.2003
 * @see ApplicationObjectSupport#getMessageSourceAccessor
 */
/**
 * Helper类，用于轻松访问来自MessageSource的消息，并提供各种重载的getMessage方法。 
 *  <p>可从ApplicationObjectSupport获得，但也可作为独立的助手重用，以委托给应用程序对象。 
 *  @author  Juergen Hoeller @2003年10月23日起
 * @see  ApplicationObjectSupport＃getMessageSourceAccessor
 */
public class MessageSourceAccessor {

	private final MessageSource messageSource;

	@Nullable
	private final Locale defaultLocale;


	/**
	 * Create a new MessageSourceAccessor, using LocaleContextHolder's locale
	 * as default locale.
	 * @param messageSource the MessageSource to wrap
	 * @see org.springframework.context.i18n.LocaleContextHolder#getLocale()
	 */
	/**
	 * 使用LocaleContextHolder的语言环境作为默认语言环境，创建一个新的MessageSourceAccessor。 
	 *  
	 * @param  messageSource要包装
	 * @see  org.springframework.context.i18n.LocaleContextHolder＃getLocale（）的MessageSource
	 */
	public MessageSourceAccessor(MessageSource messageSource) {
		this.messageSource = messageSource;
		this.defaultLocale = null;
	}

	/**
	 * Create a new MessageSourceAccessor, using the given default locale.
	 * @param messageSource the MessageSource to wrap
	 * @param defaultLocale the default locale to use for message access
	 */
	/**
	 * 使用给定的默认语言环境创建一个新的MessageSourceAccessor。 
	 *  
	 * @param  messageSource要包装的MessageSource 
	 * @param  defaultLocale用于消息访问的默认语言环境
	 */
	public MessageSourceAccessor(MessageSource messageSource, Locale defaultLocale) {
		this.messageSource = messageSource;
		this.defaultLocale = defaultLocale;
	}


	/**
	 * Return the default locale to use if no explicit locale has been given.
	 * <p>The default implementation returns the default locale passed into the
	 * corresponding constructor, or LocaleContextHolder's locale as fallback.
	 * Can be overridden in subclasses.
	 * @see #MessageSourceAccessor(org.springframework.context.MessageSource, java.util.Locale)
	 * @see org.springframework.context.i18n.LocaleContextHolder#getLocale()
	 */
	/**
	 * 如果未提供任何明确的语言环境，则返回要使用的默认语言环境。 
	 *  <p>默认实现返回传递给相应构造函数的默认语言环境，或作为回退的LocaleContextHolder的语言环境。 
	 * 可以在子类中覆盖。 
	 *  
	 * @see  #MessageSourceAccessor（org.springframework.context.MessageSource，java.util.Locale）
	 * @see  org.springframework.context.i18n.LocaleContextHolder＃getLocale（）
	 */
	protected Locale getDefaultLocale() {
		return (this.defaultLocale != null ? this.defaultLocale : LocaleContextHolder.getLocale());
	}

	/**
	 * Retrieve the message for the given code and the default Locale.
	 * @param code code of the message
	 * @param defaultMessage the String to return if the lookup fails
	 * @return the message
	 */
	/**
	 * 检索给定代码和默认区域设置的消息。 
	 * 消息的
	 * @param 代码
	 * @param  defaultMessage如果查找失败，返回的字符串
	 * @return 消息
	 */
	public String getMessage(String code, String defaultMessage) {
		String msg = this.messageSource.getMessage(code, null, defaultMessage, getDefaultLocale());
		return (msg != null ? msg : "");
	}

	/**
	 * Retrieve the message for the given code and the given Locale.
	 * @param code code of the message
	 * @param defaultMessage the String to return if the lookup fails
	 * @param locale the Locale in which to do lookup
	 * @return the message
	 */
	/**
	 * 检索给定代码和给定语言环境的消息。 
	 * 消息的
	 * @param 代码
	 * @param  defaultMessage查找失败时返回的字符串
	 * @param 语言环境查找消息的语言环境
	 * @return 
	 */
	public String getMessage(String code, String defaultMessage, Locale locale) {
		String msg = this.messageSource.getMessage(code, null, defaultMessage, locale);
		return (msg != null ? msg : "");
	}

	/**
	 * Retrieve the message for the given code and the default Locale.
	 * @param code code of the message
	 * @param args arguments for the message, or {@code null} if none
	 * @param defaultMessage the String to return if the lookup fails
	 * @return the message
	 */
	/**
	 * 检索给定代码和默认区域设置的消息。 
	 * 消息的
	 * @param 消息代码
	 * @param 消息的args参数，如果没有则为{@code  null} 
	 * @param  defaultMessage如果查找失败，返回的字符串
	 * @return 消息
	 */
	public String getMessage(String code, @Nullable Object[] args, String defaultMessage) {
		String msg = this.messageSource.getMessage(code, args, defaultMessage, getDefaultLocale());
		return (msg != null ? msg : "");
	}

	/**
	 * Retrieve the message for the given code and the given Locale.
	 * @param code code of the message
	 * @param args arguments for the message, or {@code null} if none
	 * @param defaultMessage the String to return if the lookup fails
	 * @param locale the Locale in which to do lookup
	 * @return the message
	 */
	/**
	 * 检索给定代码和给定语言环境的消息。 
	 * 消息的
	 * @param 代码代码消息的
	 * @param  args参数，如果没有，则为{@code  null} 
	 * @param  defaultMessage查找失败时返回的字符串
	 * @param 语言环境查找消息
	 * @return 的消息
	 */
	public String getMessage(String code, @Nullable Object[] args, String defaultMessage, Locale locale) {
		String msg = this.messageSource.getMessage(code, args, defaultMessage, locale);
		return (msg != null ? msg : "");
	}

	/**
	 * Retrieve the message for the given code and the default Locale.
	 * @param code code of the message
	 * @return the message
	 * @throws org.springframework.context.NoSuchMessageException if not found
	 */
	/**
	 * 检索给定代码和默认区域设置的消息。 
	 * 消息的
	 * @param 代码
	 * @return 消息
	 * @throws  org.springframework.context.NoSuchMessageException（如果未找到）
	 */
	public String getMessage(String code) throws NoSuchMessageException {
		return this.messageSource.getMessage(code, null, getDefaultLocale());
	}

	/**
	 * Retrieve the message for the given code and the given Locale.
	 * @param code code of the message
	 * @param locale the Locale in which to do lookup
	 * @return the message
	 * @throws org.springframework.context.NoSuchMessageException if not found
	 */
	/**
	 * 检索给定代码和给定语言环境的消息。 
	 * 消息的
	 * @param 代码
	 * @param 语言环境在其中进行查找的语言环境
	 * @return 消息
	 * @throws  org.springframework.context.NoSuchMessageException（如果未找到）
	 */
	public String getMessage(String code, Locale locale) throws NoSuchMessageException {
		return this.messageSource.getMessage(code, null, locale);
	}

	/**
	 * Retrieve the message for the given code and the default Locale.
	 * @param code code of the message
	 * @param args arguments for the message, or {@code null} if none
	 * @return the message
	 * @throws org.springframework.context.NoSuchMessageException if not found
	 */
	/**
	 * 检索给定代码和默认区域设置的消息。 
	 * 消息的
	 * @param 消息代码
	 * @param 消息的args参数，如果没有，则为{@code  null} 
	 * @return 消息
	 * @throws  org.springframework.context.NoSuchMessageException
	 */
	public String getMessage(String code, @Nullable Object[] args) throws NoSuchMessageException {
		return this.messageSource.getMessage(code, args, getDefaultLocale());
	}

	/**
	 * Retrieve the message for the given code and the given Locale.
	 * @param code code of the message
	 * @param args arguments for the message, or {@code null} if none
	 * @param locale the Locale in which to do lookup
	 * @return the message
	 * @throws org.springframework.context.NoSuchMessageException if not found
	 */
	/**
	 * 检索给定代码和给定语言环境的消息。 
	 * 消息的
	 * @param 代码
	 * @param 消息的args参数，如果没有，则为{@code  null} 
	 * @param  locale进行查询的语言环境
	 * @return 消息<
	 * @throws > org.springframework.context.NoSuchMessageException如果找不到
	 */
	public String getMessage(String code, @Nullable Object[] args, Locale locale) throws NoSuchMessageException {
		return this.messageSource.getMessage(code, args, locale);
	}

	/**
	 * Retrieve the given MessageSourceResolvable (e.g. an ObjectError instance)
	 * in the default Locale.
	 * @param resolvable the MessageSourceResolvable
	 * @return the message
	 * @throws org.springframework.context.NoSuchMessageException if not found
	 */
	/**
	 * 在默认的区域设置中检索给定的MessageSourceResolvable（例如ObjectError实例）。 
	 *  
	 * @param 可解决MessageSourceResolvable 
	 * @return 消息
	 * @throws  org.springframework.context.NoSuchMessageException
	 */
	public String getMessage(MessageSourceResolvable resolvable) throws NoSuchMessageException {
		return this.messageSource.getMessage(resolvable, getDefaultLocale());
	}

	/**
	 * Retrieve the given MessageSourceResolvable (e.g. an ObjectError instance)
	 * in the given Locale.
	 * @param resolvable the MessageSourceResolvable
	 * @param locale the Locale in which to do lookup
	 * @return the message
	 * @throws org.springframework.context.NoSuchMessageException if not found
	 */
	/**
	 * 在给定的语言环境中检索给定的MessageSourceResolvable（例如ObjectError实例）。 
	 *  
	 * @param 可解析MessageSourceResolvable 
	 * @param 语言环境在其中进行查找的语言环境
	 * @return 消息
	 * @throws  org.springframework.context.NoSuchMessageException（如果未找到）
	 */
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		return this.messageSource.getMessage(resolvable, locale);
	}

}
