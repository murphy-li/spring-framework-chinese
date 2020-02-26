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

package org.springframework.beans.testfixture.beans;

import java.io.Serializable;

import org.springframework.util.ObjectUtils;

/**
 * Serializable implementation of the Person interface.
 *
 * @author Rod Johnson
 */
/**
 * Person接口的可序列化实现。 
 *  @author 罗德·约翰逊
 */
@SuppressWarnings("serial")
public class SerializablePerson implements Person, Serializable {

	private String name;

	private int age;


	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public Object echo(Object o) throws Throwable {
		if (o instanceof Throwable) {
			throw (Throwable) o;
		}
		return o;
	}


	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SerializablePerson)) {
			return false;
		}
		SerializablePerson p = (SerializablePerson) other;
		return p.age == age && ObjectUtils.nullSafeEquals(name, p.name);
	}

	@Override
	public int hashCode() {
		return SerializablePerson.class.hashCode();
	}

}
