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

package org.springframework.aop.framework;

/**
 * Interface to be implemented by factories that are able to create
 * AOP proxies based on {@link AdvisedSupport} configuration objects.
 *
 * <p>Proxies should observe the following contract:
 * <ul>
 * <li>They should implement all interfaces that the configuration
 * indicates should be proxied.
 * <li>They should implement the {@link Advised} interface.
 * <li>They should implement the equals method to compare proxied
 * interfaces, advice, and target.
 * <li>They should be serializable if all advisors and target
 * are serializable.
 * <li>They should be thread-safe if advisors and target
 * are thread-safe.
 * </ul>
 *
 * <p>Proxies may or may not allow advice changes to be made.
 * If they do not permit advice changes (for example, because
 * the configuration was frozen) a proxy should throw an
 * {@link AopConfigException} on an attempted advice change.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 */
/**
 * 由能够基于{@link  AdvisedSupport}配置对象创建AOP代理的工厂实现的接口。 
 *  <p>代理应遵守以下约定：<ul> <li>它们应实现配置指示为代理的所有接口。 
 *  <li>他们应该实现{@link  Advised}接口。 
 *  <li>他们应该实施equals方法来比较代理接口，建议和目标。 
 *  <li>如果所有顾问程序和目标都是可序列化的，则它们应该可序列化。 
 *  <li>如果顾问程序和目标服务器是线程安全的，则它们应该是线程安全的。 
 *  </ ul> <p>代理可能会或可能不允许进行建议更改。 
 * 如果他们不允许更改建议（例如，由于配置被冻结），则代理应在尝试更改建议时抛出{@link  AopConfigException}。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller
 */
public interface AopProxyFactory {

	/**
	 * Create an {@link AopProxy} for the given AOP configuration.
	 * @param config the AOP configuration in the form of an
	 * AdvisedSupport object
	 * @return the corresponding AOP proxy
	 * @throws AopConfigException if the configuration is invalid
	 */
	/**
	 * 为给定的AOP配置创建一个{@link  AopProxy}。 
	 *  @param以AdvisedSupport对象
	 * @return 的形式配置AOP配置，如果配置无效，则对应的AOP代理
	 * @throws  AopConfigException
	 */
	AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException;

}
