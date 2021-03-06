/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.servlet.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

/**
 * Base class for {@link org.springframework.web.servlet.resource.ResourceResolver}
 * implementations. Provides consistent logging.
 *
 * @author Rossen Stoyanchev
 * @since 4.1
 */
/**
 * {@link  org.springframework.web.servlet.resource.ResourceResolver}实现的基类。 
 * 提供一致的日志记录。 
 *  @author  Rossen Stoyanchev @从4.1开始
 */
public abstract class AbstractResourceResolver implements ResourceResolver {

	protected final Log logger = LogFactory.getLog(getClass());


	@Override
	@Nullable
	public Resource resolveResource(@Nullable HttpServletRequest request, String requestPath,
			List<? extends Resource> locations, ResourceResolverChain chain) {

		return resolveResourceInternal(request, requestPath, locations, chain);
	}

	@Override
	@Nullable
	public String resolveUrlPath(String resourceUrlPath, List<? extends Resource> locations,
			ResourceResolverChain chain) {

		return resolveUrlPathInternal(resourceUrlPath, locations, chain);
	}


	@Nullable
	protected abstract Resource resolveResourceInternal(@Nullable HttpServletRequest request,
			String requestPath, List<? extends Resource> locations, ResourceResolverChain chain);

	@Nullable
	protected abstract String resolveUrlPathInternal(String resourceUrlPath,
			List<? extends Resource> locations, ResourceResolverChain chain);

}
