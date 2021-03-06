/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2012 the original author or authors.
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
 * 版权所有2002-2012的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.aop.testfixture.interceptor;

import java.io.Serializable;

/**
 * Subclass of NopInterceptor that is serializable and
 * can be used to test proxy serialization.
 *
 * @author Rod Johnson
 */
/**
 * NopInterceptor的子类是可序列化的，可用于测试代理序列化。 
 *  @author 罗德·约翰逊
 */
@SuppressWarnings("serial")
public class SerializableNopInterceptor extends NopInterceptor implements Serializable {

	/**
	 * We must override this field and the related methods as
	 * otherwise count won't be serialized from the non-serializable
	 * NopInterceptor superclass.
	 */
	/**
	 * 我们必须覆盖此字段，否则相关方法将无法通过不可序列化的NopInterceptor超类序列化计数。 
	 * 
	 */
	private int count;

	@Override
	public int getCount() {
		return this.count;
	}

	@Override
	protected void increment() {
		++count;
	}

}
