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

package org.springframework.web.servlet.mvc.condition;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Supports media type expressions as described in:
 * {@link RequestMapping#consumes()} and {@link RequestMapping#produces()}.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @since 3.1
 */
/**
 * 支持媒体类型表达式，如{@link  RequestMapping＃consumes（）}和{@link  RequestMapping＃produces（）}中所述。 
 *  @author  Arjen Poutsma @author  Rossen Stoyanchev @始于3.1
 */
abstract class AbstractMediaTypeExpression implements MediaTypeExpression, Comparable<AbstractMediaTypeExpression> {

	protected final Log logger = LogFactory.getLog(getClass());

	private final MediaType mediaType;

	private final boolean isNegated;


	AbstractMediaTypeExpression(String expression) {
		if (expression.startsWith("!")) {
			this.isNegated = true;
			expression = expression.substring(1);
		}
		else {
			this.isNegated = false;
		}
		this.mediaType = MediaType.parseMediaType(expression);
	}

	AbstractMediaTypeExpression(MediaType mediaType, boolean negated) {
		this.mediaType = mediaType;
		this.isNegated = negated;
	}


	@Override
	public MediaType getMediaType() {
		return this.mediaType;
	}

	@Override
	public boolean isNegated() {
		return this.isNegated;
	}


	@Override
	public int compareTo(AbstractMediaTypeExpression other) {
		return MediaType.SPECIFICITY_COMPARATOR.compare(this.getMediaType(), other.getMediaType());
	}

	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		AbstractMediaTypeExpression otherExpr = (AbstractMediaTypeExpression) other;
		return (this.mediaType.equals(otherExpr.mediaType) && this.isNegated == otherExpr.isNegated);
	}

	@Override
	public int hashCode() {
		return this.mediaType.hashCode();
	}

	@Override
	public String toString() {
		if (this.isNegated) {
			return '!' + this.mediaType.toString();
		}
		return this.mediaType.toString();
	}

}
