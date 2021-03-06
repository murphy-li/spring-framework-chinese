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

package org.springframework.jms.listener.adapter;

import java.lang.reflect.InvocationTargetException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.listener.SubscriptionNameProvider;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MethodInvoker;
import org.springframework.util.ObjectUtils;

/**
 * Message listener adapter that delegates the handling of messages to target
 * listener methods via reflection, with flexible message type conversion.
 * Allows listener methods to operate on message content types, completely
 * independent from the JMS API.
 *
 * <p>By default, the content of incoming JMS messages gets extracted before
 * being passed into the target listener method, to let the target method
 * operate on message content types such as String or byte array instead of
 * the raw {@link Message}. Message type conversion is delegated to a Spring
 * JMS {@link MessageConverter}. By default, a {@link SimpleMessageConverter}
 * will be used. (If you do not want such automatic message conversion taking
 * place, then be sure to set the {@link #setMessageConverter MessageConverter}
 * to {@code null}.)
 *
 * <p>If a target listener method returns a non-null object (typically of a
 * message content type such as {@code String} or byte array), it will get
 * wrapped in a JMS {@code Message} and sent to the response destination
 * (either the JMS "reply-to" destination or a
 * {@link #setDefaultResponseDestination(javax.jms.Destination) specified default
 * destination}).
 *
 * <p><b>Note:</b> The sending of response messages is only available when
 * using the {@link SessionAwareMessageListener} entry point (typically through a
 * Spring message listener container). Usage as standard JMS {@link MessageListener}
 * does <i>not</i> support the generation of response messages.
 *
 * <p>Find below some examples of method signatures compliant with this
 * adapter class. This first example handles all {@code Message} types
 * and gets passed the contents of each {@code Message} type as an
 * argument. No {@code Message} will be sent back as all of these
 * methods return {@code void}.
 *
 * <pre class="code">public interface MessageContentsDelegate {
 *    void handleMessage(String text);
 *    void handleMessage(Map map);
 *    void handleMessage(byte[] bytes);
 *    void handleMessage(Serializable obj);
 * }</pre>
 *
 * This next example handles all {@code Message} types and gets
 * passed the actual (raw) {@code Message} as an argument. Again, no
 * {@code Message} will be sent back as all of these methods return
 * {@code void}.
 *
 * <pre class="code">public interface RawMessageDelegate {
 *    void handleMessage(TextMessage message);
 *    void handleMessage(MapMessage message);
 *    void handleMessage(BytesMessage message);
 *    void handleMessage(ObjectMessage message);
 * }</pre>
 *
 * This next example illustrates a {@code Message} delegate
 * that just consumes the {@code String} contents of
 * {@link javax.jms.TextMessage TextMessages}. Notice also how the
 * name of the {@code Message} handling method is different from the
 * {@link #ORIGINAL_DEFAULT_LISTENER_METHOD original} (this will have to
 * be configured in the attendant bean definition). Again, no {@code Message}
 * will be sent back as the method returns {@code void}.
 *
 * <pre class="code">public interface TextMessageContentDelegate {
 *    void onMessage(String text);
 * }</pre>
 *
 * This final example illustrates a {@code Message} delegate
 * that just consumes the {@code String} contents of
 * {@link javax.jms.TextMessage TextMessages}. Notice how the return type
 * of this method is {@code String}: This will result in the configured
 * {@link MessageListenerAdapter} sending a {@link javax.jms.TextMessage} in response.
 *
 * <pre class="code">public interface ResponsiveTextMessageContentDelegate {
 *    String handleMessage(String text);
 * }</pre>
 *
 * For further examples and discussion please do refer to the Spring
 * reference documentation which describes this class (and it's attendant
 * XML configuration) in detail.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see #setDelegate
 * @see #setDefaultListenerMethod
 * @see #setDefaultResponseDestination
 * @see #setMessageConverter
 * @see org.springframework.jms.support.converter.SimpleMessageConverter
 * @see org.springframework.jms.listener.SessionAwareMessageListener
 * @see org.springframework.jms.listener.AbstractMessageListenerContainer#setMessageListener
 */
/**
 * 消息侦听器适配器，通过反射将消息的处理委托给目标侦听器方法，并具有灵活的消息类型转换。 
 * 允许侦听器方法对消息内容类型进行操作，完全独立于JMS API。 
 *  <p>默认情况下，传入的JMS消息的内容在传递到目标侦听器方法之前被提取，以使目标方法对消息内容类型（例如字符串或字节数组）进行操作，而不是原始的{@link  Message} 。 
 * 消息类型转换委托给Spring JMS {@link  MessageConverter}。 
 * 默认情况下，将使用{@link  SimpleMessageConverter}。 
 *  （如果您不希望进行这种自动消息转换，那么请确保将{@link  #setMessageConverter MessageConverter}设置为{@code  null}。 
 * ）<p>如果目标侦听器方法返回非空对象（通常是消息内容类型，例如{@code  String}或字节数组），它将被包装在JMS {@code  Message}中并发送到响应目标（JMS"reply-到"目标"或{@link  #setDefaultResponseDestination（javax.jms.Destination）指定的默认目标}）。 
 *  <p> <b>注意：</ b>仅当使用{@link  SessionAwareMessageListener}入口点（通常通过Spring消息侦听器容器）时，才可以发送响应消息。 
 * 作为标准JMS {@link  MessageListener}的用法<i>不</ i>不支持响应消息的生成。 
 *  <p>在下面找到一些与此适配器类兼容的方法签名的示例。 
 * 第一个示例处理所有{@code  Message}类型，并传递每种{@code  Message}类型的内容作为参数。 
 * 由于所有这些方法都返回{@code  void}，因此不会发送任何{@code 消息}。 
 *  <pre class ="code">公共接口MessageContentsDelegate {void handleMessage（String text）; void handleMessage（Map map）; void handleMessage（byte [] bytes）; void handleMessage（Serializable obj）; } </ pre>下一个示例处理所有{@code  Message}类型，并传递实际的（原始）{@code  Message}作为参数。 
 * 同样，由于所有这些方法都返回{@code  void}，因此不会发送任何{@code  Message}消息。 
 *  <pre class ="code">公共接口RawMessageDelegate {void handleMessage（TextMessage message）; void handleMessage（MapMessage消息）; void handleMessage（BytesMessage消息）; void handleMessage（ObjectMessage消息）; } </ pre>下一个示例说明了一个{@code  Message}委托，该委托仅使用{@link  javax.jms.TextMessage TextMessages}的{@code  String}内容。 
 * 还请注意{@code  Message}处理方法的名称与{@link  #ORIGINAL_DEFAULT_LISTENER_METHOD original}的名称有何不同（必须在附带的bean定义中进行配置）。 
 * 同样，当方法返回{@code  void}时，不会发送任何{@code 消息}。 
 *  <pre class ="code">公共接口TextMessageContentDelegate {void onMessage（String text）; } </ pre>最后一个示例说明了一个{@code  Message}委托，该委托仅使用{@link  javax.jms.TextMessage TextMessages}的{@code  String}内容。 
 * 注意该方法的返回类型是{@code  String}：这将导致配置的{@link  MessageListenerAdapter}发送一个{@link  javax.jms.TextMessage}作为响应。 
 *  <pre class ="code">公共接口ResponsiveTextMessageContentDelegate {字符串handleMessage（String text）; } </ pre>有关更多示例和讨论，请确实参考Spring参考文档，该文档详细描述了该类（及其相关的XML配置）。 
 *  @author  Juergen Hoeller @since 2.0起
 * @see  #setDelegate 
 * @see  #setDefaultListenerMethod 
 * @see  #setDefaultResponseDestination 
 * @see  #setMessageConverter 
 * @see  org.springframework.jms.support.converter.SimpleMessageConverter <@请参阅> org.springframework.jms.listener.SessionAwareMessageListener <@请参阅> org.springframework.jms.listener.AbstractMessageListenerContainer＃setMessageListener
 */
public class MessageListenerAdapter extends AbstractAdaptableMessageListener implements SubscriptionNameProvider {

	/**
	 * Out-of-the-box value for the default listener method: "handleMessage".
	 */
	/**
	 * 默认侦听器方法的即用型值："handleMessage"。 
	 * 
	 */
	public static final String ORIGINAL_DEFAULT_LISTENER_METHOD = "handleMessage";


	private Object delegate;

	private String defaultListenerMethod = ORIGINAL_DEFAULT_LISTENER_METHOD;


	/**
	 * Create a new {@link MessageListenerAdapter} with default settings.
	 */
	/**
	 * 使用默认设置创建一个新的{@link  MessageListenerAdapter}。 
	 * 
	 */
	public MessageListenerAdapter() {
		this.delegate = this;
	}

	/**
	 * Create a new {@link MessageListenerAdapter} for the given delegate.
	 * @param delegate the delegate object
	 */
	/**
	 * 为给定的委托创建一个新的{@link  MessageListenerAdapter}。 
	 *  
	 * @param 委托委托对象
	 */
	public MessageListenerAdapter(Object delegate) {
		Assert.notNull(delegate, "Delegate must not be null");
		this.delegate = delegate;
	}


	/**
	 * Set a target object to delegate message listening to.
	 * Specified listener methods have to be present on this target object.
	 * <p>If no explicit delegate object has been specified, listener
	 * methods are expected to present on this adapter instance, that is,
	 * on a custom subclass of this adapter, defining listener methods.
	 */
	/**
	 * 设置目标对象以委派侦听消息。 
	 * 指定的侦听器方法必须存在于此目标对象上。 
	 *  <p>如果未指定任何显式委托对象，则应在此适配器实例上（即，在此适配器的自定义子类上）提供侦听器方法，以定义侦听器方法。 
	 * 
	 */
	public void setDelegate(Object delegate) {
		Assert.notNull(delegate, "Delegate must not be null");
		this.delegate = delegate;
	}

	/**
	 * Return the target object to delegate message listening to.
	 */
	/**
	 * 返回目标对象以委托侦听消息。 
	 * 
	 */
	protected Object getDelegate() {
		return this.delegate;
	}

	/**
	 * Specify the name of the default listener method to delegate to,
	 * for the case where no specific listener method has been determined.
	 * Out-of-the-box value is {@link #ORIGINAL_DEFAULT_LISTENER_METHOD "handleMessage"}.
	 * @see #getListenerMethodName
	 */
	/**
	 * 对于尚未确定特定侦听器方法的情况，请指定要委派的默认侦听器方法的名称。 
	 * 现成的值为{@link  #ORIGINAL_DEFAULT_LISTENER_METHOD"handleMessage"}。 
	 *  
	 * @see  #getListenerMethodName
	 */
	public void setDefaultListenerMethod(String defaultListenerMethod) {
		this.defaultListenerMethod = defaultListenerMethod;
	}

	/**
	 * Return the name of the default listener method to delegate to.
	 */
	/**
	 * 返回要委托给的默认侦听器方法的名称。 
	 * 
	 */
	protected String getDefaultListenerMethod() {
		return this.defaultListenerMethod;
	}


	/**
	 * Spring {@link SessionAwareMessageListener} entry point.
	 * <p>Delegates the message to the target listener method, with appropriate
	 * conversion of the message argument. If the target method returns a
	 * non-null object, wrap in a JMS message and send it back.
	 * @param message the incoming JMS message
	 * @param session the JMS session to operate on
	 * @throws JMSException if thrown by JMS API methods
	 */
	/**
	 * 春季{@link  SessionAwareMessageListener}入口点。 
	 *  <p>将消息委托给目标侦听器方法，并适当地转换message参数。 
	 * 如果目标方法返回非空对象，则包装JMS消息并将其发送回。 
	 *  
	 * @param 消息传入的JMS消息
	 * @param 会话JMS会话在
	 * @throws  JMSException上运行（如果由JMS API方法抛出）
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void onMessage(Message message, @Nullable Session session) throws JMSException {
		// Check whether the delegate is a MessageListener impl itself.
		// In that case, the adapter will simply act as a pass-through.
		Object delegate = getDelegate();
		if (delegate != this) {
			if (delegate instanceof SessionAwareMessageListener) {
				Assert.state(session != null, "Session is required for SessionAwareMessageListener");
				((SessionAwareMessageListener<Message>) delegate).onMessage(message, session);
				return;
			}
			if (delegate instanceof MessageListener) {
				((MessageListener) delegate).onMessage(message);
				return;
			}
		}

		// Regular case: find a handler method reflectively.
		Object convertedMessage = extractMessage(message);
		String methodName = getListenerMethodName(message, convertedMessage);

		// Invoke the handler method with appropriate arguments.
		Object[] listenerArguments = buildListenerArguments(convertedMessage);
		Object result = invokeListenerMethod(methodName, listenerArguments);
		if (result != null) {
			handleResult(result, message, session);
		}
		else {
			logger.trace("No result object given - no result to handle");
		}
	}

	@Override
	public String getSubscriptionName() {
		Object delegate = getDelegate();
		if (delegate != this && delegate instanceof SubscriptionNameProvider) {
			return ((SubscriptionNameProvider) delegate).getSubscriptionName();
		}
		else {
			return delegate.getClass().getName();
		}
	}

	/**
	 * Determine the name of the listener method that is supposed to
	 * handle the given message.
	 * <p>The default implementation simply returns the configured
	 * default listener method, if any.
	 * @param originalMessage the JMS request message
	 * @param extractedMessage the converted JMS request message,
	 * to be passed into the listener method as argument
	 * @return the name of the listener method (never {@code null})
	 * @throws JMSException if thrown by JMS API methods
	 * @see #setDefaultListenerMethod
	 */
	/**
	 * 确定应该处理给定消息的侦听器方法的名称。 
	 *  <p>默认实现只是返回配置的默认侦听器方法（如果有）。 
	 *  
	 * @param  originalMessage JMS请求消息
	 * @param  extractedMessage转换后的JMS请求消息，将作为参数传递给侦听器方法
	 * @return 侦听器方法的名称（决不{<@@code> null}）
	 * @throws 如果由JMS API方法抛出，则抛出JMSException 
	 * @see  #setDefaultListenerMethod
	 */
	protected String getListenerMethodName(Message originalMessage, Object extractedMessage) throws JMSException {
		return getDefaultListenerMethod();
	}

	/**
	 * Build an array of arguments to be passed into the target listener method.
	 * Allows for multiple method arguments to be built from a single message object.
	 * <p>The default implementation builds an array with the given message object
	 * as sole element. This means that the extracted message will always be passed
	 * into a <i>single</i> method argument, even if it is an array, with the target
	 * method having a corresponding single argument of the array's type declared.
	 * <p>This can be overridden to treat special message content such as arrays
	 * differently, for example passing in each element of the message array
	 * as distinct method argument.
	 * @param extractedMessage the content of the message
	 * @return the array of arguments to be passed into the
	 * listener method (each element of the array corresponding
	 * to a distinct method argument)
	 */
	/**
	 * 构建要传递到目标侦听器方法的参数数组。 
	 * 允许从单个消息对象构建多个方法参数。 
	 *  <p>默认实现使用给定消息对象作为唯一元素构建一个数组。 
	 * 这意味着，即使消息是数组，提取的消息也始终会传递到<i>单个</ i>方法参数中，而目标方法将声明数组类型的相应单个参数。 
	 *  <p>可以重写此方法，以不同方式对待特殊消息内容（例如数组），例如，将消息数组的每个元素作为不同的方法参数传入。 
	 *  
	 * @param  extractedMessage消息的内容
	 * @return 传递给侦听器方法的参数数组（该数组的每个元素对应于一个不同的方法参数）
	 */
	protected Object[] buildListenerArguments(Object extractedMessage) {
		return new Object[] {extractedMessage};
	}

	/**
	 * Invoke the specified listener method.
	 * @param methodName the name of the listener method
	 * @param arguments the message arguments to be passed in
	 * @return the result returned from the listener method
	 * @throws JMSException if thrown by JMS API methods
	 * @see #getListenerMethodName
	 * @see #buildListenerArguments
	 */
	/**
	 * 调用指定的侦听器方法。 
	 *  
	 * @param  methodName侦听器方法的名称
	 * @param 自变量要传入的消息参数
	 * @return 侦听器方法返回的结果
	 * @throws  JMSException（如果由JMS API方法抛出）
	 * @see ＃ getListenerMethodName 
	 * @see  #buildListenerArguments
	 */
	@Nullable
	protected Object invokeListenerMethod(String methodName, Object[] arguments) throws JMSException {
		try {
			MethodInvoker methodInvoker = new MethodInvoker();
			methodInvoker.setTargetObject(getDelegate());
			methodInvoker.setTargetMethod(methodName);
			methodInvoker.setArguments(arguments);
			methodInvoker.prepare();
			return methodInvoker.invoke();
		}
		catch (InvocationTargetException ex) {
			Throwable targetEx = ex.getTargetException();
			if (targetEx instanceof JMSException) {
				throw (JMSException) targetEx;
			}
			else {
				throw new ListenerExecutionFailedException(
						"Listener method '" + methodName + "' threw exception", targetEx);
			}
		}
		catch (Throwable ex) {
			throw new ListenerExecutionFailedException("Failed to invoke target method '" + methodName +
					"' with arguments " + ObjectUtils.nullSafeToString(arguments), ex);
		}
	}

}
