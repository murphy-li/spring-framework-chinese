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

package org.springframework.orm.jpa.persistenceunit;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

import javax.persistence.spi.ClassTransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Simple adapter that implements the {@code java.lang.instrument.ClassFileTransformer}
 * interface based on a JPA {@code ClassTransformer} which a JPA PersistenceProvider
 * asks the {@code PersistenceUnitInfo} to install in the current runtime.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 2.0
 * @see javax.persistence.spi.PersistenceUnitInfo#addTransformer(javax.persistence.spi.ClassTransformer)
 */
/**
 * 一个简单的适配器，该适配器基于JPA {@code  ClassTransformer}实现JPA {@code  ClassTransformer}的{@code  java.lang.instrument.ClassFileTransformer}接口，JPA PersistenceProvider要求{@code  PersistenceUnitInfo}安装在当前运行时中。 
 *  @author  Rod Johnson @author  Juergen Hoeller @始于2.0 
 * @see  javax.persistence.spi.PersistenceUnitInfo＃addTransformer（javax.persistence.spi.ClassTransformer）
 */
class ClassFileTransformerAdapter implements ClassFileTransformer {

	private static final Log logger = LogFactory.getLog(ClassFileTransformerAdapter.class);


	private final ClassTransformer classTransformer;

	private boolean currentlyTransforming = false;


	public ClassFileTransformerAdapter(ClassTransformer classTransformer) {
		Assert.notNull(classTransformer, "ClassTransformer must not be null");
		this.classTransformer = classTransformer;
	}


	@Override
	@Nullable
	public byte[] transform(
			ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) {

		synchronized (this) {
			if (this.currentlyTransforming) {
				// Defensively back out when called from within the transform delegate below:
				// in particular, for the over-eager transformer implementation in Hibernate 5.
				return null;
			}

			this.currentlyTransforming = true;
			try {
				byte[] transformed = this.classTransformer.transform(
						loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
				if (transformed != null && logger.isDebugEnabled()) {
					logger.debug("Transformer of class [" + this.classTransformer.getClass().getName() +
							"] transformed class [" + className + "]; bytes in=" +
							classfileBuffer.length + "; bytes out=" + transformed.length);
				}
				return transformed;
			}
			catch (ClassCircularityError ex) {
				if (logger.isErrorEnabled()) {
					logger.error("Circularity error while weaving class [" + className + "] with " +
							"transformer of class [" + this.classTransformer.getClass().getName() + "]", ex);
				}
				throw new IllegalStateException("Failed to weave class [" + className + "]", ex);
			}
			catch (Throwable ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Error weaving class [" + className + "] with transformer of class [" +
							this.classTransformer.getClass().getName() + "]", ex);
				}
				// The exception will be ignored by the class loader, anyway...
				throw new IllegalStateException("Could not weave class [" + className + "]", ex);
			}
			finally {
				this.currentlyTransforming = false;
			}
		}
	}


	@Override
	public String toString() {
		return "Standard ClassFileTransformer wrapping JPA transformer: " + this.classTransformer;
	}

}
