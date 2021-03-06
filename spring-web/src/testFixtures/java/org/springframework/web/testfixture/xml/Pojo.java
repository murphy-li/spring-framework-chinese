/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.testfixture.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sebastien Deleuze
 */
/**
 * @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）
 */
@XmlRootElement
public class Pojo {

	private String foo;

	private String bar;

	public Pojo() {
	}

	public Pojo(String foo, String bar) {
		this.foo = foo;
		this.bar = bar;
	}

	public String getFoo() {
		return this.foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public String getBar() {
		return this.bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Pojo) {
			Pojo other = (Pojo) o;
			return this.foo.equals(other.foo) && this.bar.equals(other.bar);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 31 * foo.hashCode() + bar.hashCode();
	}

	@Override
	public String toString() {
		return "Pojo[foo='" + this.foo + "\'" + ", bar='" + this.bar + "\']";
	}
}
