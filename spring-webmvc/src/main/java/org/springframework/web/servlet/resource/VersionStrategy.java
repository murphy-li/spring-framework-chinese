/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.servlet.resource;

import org.springframework.core.io.Resource;

/**
 * An extension of {@link VersionPathStrategy} that adds a method
 * to determine the actual version of a {@link Resource}.
 *
 * @author Brian Clozel
 * @author Rossen Stoyanchev
 * @since 4.1
 * @see VersionResourceResolver
*/
/**
 * {@link  VersionPathStrategy}的扩展，添加了一种确定{@link  Resource}实际版本的方法。 
 *  @author  Brian Clozel @author  Rossen Stoyanchev @从4.1开始
 * @see  VersionResourceResolver
 */
public interface VersionStrategy extends VersionPathStrategy {

	/**
	 * Determine the version for the given resource.
	 * @param resource the resource to check
	 * @return the version (never {@code null})
	 */
	/**
	 * 确定给定资源的版本。 
	 *  
	 * @param 资源检查
	 * @return 版本的资源（切勿{@code  null}）
	 */
	String getResourceVersion(Resource resource);

}
