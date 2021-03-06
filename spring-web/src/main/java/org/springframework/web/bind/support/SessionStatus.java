/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2007 the original author or authors.
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
 * 版权所有2002-2007的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.bind.support;

/**
 * Simple interface that can be injected into handler methods, allowing them to
 * signal that their session processing is complete. The handler invoker may
 * then follow up with appropriate cleanup, e.g. of session attributes which
 * have been implicitly created during this handler's processing (according to
 * the
 * {@link org.springframework.web.bind.annotation.SessionAttributes @SessionAttributes}
 * annotation).
 *
 * @author Juergen Hoeller
 * @since 2.5
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see org.springframework.web.bind.annotation.SessionAttributes
 */
/**
 * 可以注入到处理程序方法中的简单接口，使它们可以发出其会话处理已完成的信号。 
 * 然后，处理程序调用者可以进行适当的清理，例如清理。 
 * 在此处理程序的处理期间隐式创建的会话属性（根据{@link  org.springframework.web.bind.annotation.SessionAttributes @SessionAttributes}注解）。 
 *  @author  Juergen Hoeller @since 2.5起
 * @see  org.springframework.web.bind.annotation.RequestMapping 
 * @see  org.springframework.web.bind.annotation.SessionAttributes
 */
public interface SessionStatus {

	/**
	 * Mark the current handler's session processing as complete, allowing for
	 * cleanup of session attributes.
	 */
	/**
	 * 将当前处理程序的会话处理标记为已完成，以便清除会话属性。 
	 * 
	 */
	void setComplete();

	/**
	 * Return whether the current handler's session processing has been marked
	 * as complete.
	 */
	/**
	 * 返回当前处理程序的会话处理是否已标记为完成。 
	 * 
	 */
	boolean isComplete();

}
