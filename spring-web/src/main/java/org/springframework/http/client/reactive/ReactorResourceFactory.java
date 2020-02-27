/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2020 the original author or authors.
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
 * 版权所有2002-2020的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.http.client.reactive;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

import reactor.netty.http.HttpResources;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Factory to manage Reactor Netty resources, i.e. {@link LoopResources} for
 * event loop threads, and {@link ConnectionProvider} for the connection pool,
 * within the lifecycle of a Spring {@code ApplicationContext}.
 *
 * <p>This factory implements {@link InitializingBean} and {@link DisposableBean}
 * and is expected typically to be declared as a Spring-managed bean.
 *
 * @author Rossen Stoyanchev
 * @since 5.1
 */
/**
 * 工厂在Spring {@code  ApplicationContext}的生命周期内管理Reactor Netty资源，即用于事件循环线程的{@link  LoopResources}和用于连接池的{@link  ConnectionProvider}。 
 *  <p>该工厂实现{@link  InitializingBean}和{@link  DisposableBean}，通常应将其声明为Spring托管的bean。 
 *  @author  Rossen Stoyanchev @从5.1开始
 */
public class ReactorResourceFactory implements InitializingBean, DisposableBean {

	private boolean useGlobalResources = true;

	@Nullable
	private Consumer<HttpResources> globalResourcesConsumer;

	@SuppressWarnings("deprecation")
	private Supplier<ConnectionProvider> connectionProviderSupplier = () -> ConnectionProvider.fixed("webflux", 500);

	@Nullable
	private ConnectionProvider connectionProvider;

	private Supplier<LoopResources> loopResourcesSupplier = () -> LoopResources.create("webflux-http");

	@Nullable
	private LoopResources loopResources;

	private boolean manageConnectionProvider = false;

	private boolean manageLoopResources = false;

	private Duration shutdownQuietPeriod = Duration.ofSeconds(LoopResources.DEFAULT_SHUTDOWN_QUIET_PERIOD);

	private Duration shutdownTimeout = Duration.ofSeconds(LoopResources.DEFAULT_SHUTDOWN_TIMEOUT);


	/**
	 * Whether to use global Reactor Netty resources via {@link HttpResources}.
	 * <p>Default is "true" in which case this factory initializes and stops the
	 * global Reactor Netty resources within Spring's {@code ApplicationContext}
	 * lifecycle. If set to "false" the factory manages its resources independent
	 * of the global ones.
	 * @param useGlobalResources whether to expose and manage the global resources
	 * @see #addGlobalResourcesConsumer(Consumer)
	 */
	/**
	 * 是否通过{@link  HttpResources}使用全局Reactor Netty资源。 
	 *  <p>默认值为"true"，在这种情况下，此工厂将初始化并停止Spring在{@code  ApplicationContext}生命周期内的全局Reactor Netty资源。 
	 * 如果设置为"false"，则工厂将独立于全局资源来管理其资源。 
	 *  
	 * @param  useGlobalResources是否公开和管理全局资源
	 * @see  #addGlobalResourcesConsumer（Consumer）
	 */
	public void setUseGlobalResources(boolean useGlobalResources) {
		this.useGlobalResources = useGlobalResources;
	}

	/**
	 * Whether this factory exposes the global
	 * {@link reactor.netty.http.HttpResources HttpResources} holder.
	 */
	/**
	 * 该工厂是否公开全局{@link  Reactor.netty.http.HttpResources HttpResources}持有者。 
	 * 
	 */
	public boolean isUseGlobalResources() {
		return this.useGlobalResources;
	}

	/**
	 * Add a Consumer for configuring the global Reactor Netty resources on
	 * startup. When this option is used, {@link #setUseGlobalResources} is also
	 * enabled.
	 * @param consumer the consumer to apply
	 * @see #setUseGlobalResources(boolean)
	 */
	/**
	 * 添加用于在启动时配置全局Reactor Netty资源的使用者。 
	 * 使用此选项时，还将启用{@link  #setUseGlobalResources}。 
	 *  
	 * @param 消费者消费者申请
	 * @see  #setUseGlobalResources（boolean）
	 */
	public void addGlobalResourcesConsumer(Consumer<HttpResources> consumer) {
		this.useGlobalResources = true;
		this.globalResourcesConsumer = this.globalResourcesConsumer != null ?
				this.globalResourcesConsumer.andThen(consumer) : consumer;
	}

	/**
	 * Use this when you don't want to participate in global resources and
	 * you want to customize the creation of the managed {@code ConnectionProvider}.
	 * <p>By default, {@code ConnectionProvider.elastic("http")} is used.
	 * <p>Note that this option is ignored if {@code userGlobalResources=false} or
	 * {@link #setConnectionProvider(ConnectionProvider)} is set.
	 * @param supplier the supplier to use
	 */
	/**
	 * 当您不想参与全局资源并且想要自定义托管{@code  ConnectionProvider}的创建时，请使用此选项。 
	 *  <p>默认情况下，使用{@code  ConnectionProvider.elastic（"http"）}。 
	 *  <p>请注意，如果设置了{@code  userGlobalResources = false}或{@link  #setConnectionProvider（ConnectionProvider）}，则会忽略此选项。 
	 *  
	 * @param 供应商供应商使用
	 */
	public void setConnectionProviderSupplier(Supplier<ConnectionProvider> supplier) {
		this.connectionProviderSupplier = supplier;
	}

	/**
	 * Use this when you want to provide an externally managed
	 * {@link ConnectionProvider} instance.
	 * @param connectionProvider the connection provider to use as is
	 */
	/**
	 * 如果要提供外部管理的{@link  ConnectionProvider}实例，请使用此方法。 
	 *  
	 * @param  connectionProvider连接提供者按原样使用
	 */
	public void setConnectionProvider(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	/**
	 * Return the configured {@link ConnectionProvider}.
	 */
	/**
	 * 返回配置的{@link  ConnectionProvider}。 
	 * 
	 */
	public ConnectionProvider getConnectionProvider() {
		Assert.state(this.connectionProvider != null, "ConnectionProvider not initialized yet");
		return this.connectionProvider;
	}

	/**
	 * Use this when you don't want to participate in global resources and
	 * you want to customize the creation of the managed {@code LoopResources}.
	 * <p>By default, {@code LoopResources.create("reactor-http")} is used.
	 * <p>Note that this option is ignored if {@code userGlobalResources=false} or
	 * {@link #setLoopResources(LoopResources)} is set.
	 * @param supplier the supplier to use
	 */
	/**
	 * 当您不想参与全局资源并且想要自定义托管{@code  LoopResources}的创建时，请使用此选项。 
	 *  <p>默认情况下，使用{@code  LoopResources.create（"reactor-http"）}。 
	 *  <p>请注意，如果设置了{@code  userGlobalResources = false}或{@link  #setLoopResources（LoopResources）}，则会忽略此选项。 
	 *  
	 * @param 供应商供应商使用
	 */
	public void setLoopResourcesSupplier(Supplier<LoopResources> supplier) {
		this.loopResourcesSupplier = supplier;
	}

	/**
	 * Use this option when you want to provide an externally managed
	 * {@link LoopResources} instance.
	 * @param loopResources the loop resources to use as is
	 */
	/**
	 * 当您要提供外部管理的{@link  LoopResources}实例时，请使用此选项。 
	 *  
	 * @param  loop循环使用原样使用的循环资源
	 */
	public void setLoopResources(LoopResources loopResources) {
		this.loopResources = loopResources;
	}

	/**
	 * Return the configured {@link LoopResources}.
	 */
	/**
	 * 返回已配置的{@link  LoopResources}。 
	 * 
	 */
	public LoopResources getLoopResources() {
		Assert.state(this.loopResources != null, "LoopResources not initialized yet");
		return this.loopResources;
	}

	/**
	 * Configure the amount of time we'll wait before shutting down resources.
	 * If a task is submitted during the {@code quietPeriod}, it is guaranteed
	 * to be accepted and the {@code quietPeriod} will start over.
	 * <p>By default, this is set to
	 * {@link LoopResources#DEFAULT_SHUTDOWN_QUIET_PERIOD} which is 2 seconds but
	 * can also be overridden with the system property
	 * {@link reactor.netty.ReactorNetty#SHUTDOWN_QUIET_PERIOD
	 * ReactorNetty.SHUTDOWN_QUIET_PERIOD}.
	 * @since 5.2.4
	 * @see #setShutdownTimeout(Duration)
	 */
	/**
	 * 配置关闭资源之前我们要等待的时间。 
	 * 如果在{@code  quietPeriod}期间提交了任务，则可以确保该任务被接受，并且{@code  quietPeriod}将重新开始。 
	 *  <p>默认情况下，它设置为{@link  LoopResources＃DEFAULT_SHUTDOWN_QUIET_PERIOD}，即2秒，但也可以使用系统属性{@link  Reactor.netty.ReactorNetty＃SHUTDOWN_QUIET_PERIOD ReactorNetty.SHUTDOWN_QUIET_PERIOD}覆盖。 
	 *  @since 5.2.4 
	 * @see  #setShutdownTimeout（持续时间）
	 */
	public void setShutdownQuietPeriod(Duration shutdownQuietPeriod) {
		Assert.notNull(shutdownQuietPeriod, "shutdownQuietPeriod should not be null");
		this.shutdownQuietPeriod = shutdownQuietPeriod;
	}

	/**
	 * Configure the maximum amount of time to wait until the disposal of the
	 * underlying resources regardless if a task was submitted during the
	 * {@code shutdownQuietPeriod}.
	 * <p>By default, this is set to
	 * {@link LoopResources#DEFAULT_SHUTDOWN_TIMEOUT} which is 15 seconds but
	 * can also be overridden with the system property
	 * {@link reactor.netty.ReactorNetty#SHUTDOWN_TIMEOUT
	 * ReactorNetty.SHUTDOWN_TIMEOUT}.
	 * @since 5.2.4
	 * @see #setShutdownQuietPeriod(Duration)
	 */
	/**
	 * 配置最大等待时间，直到基础资源被处置为止，无论是否在{@code  shutdownQuietPeriod}期间提交了任务。 
	 *  <p>默认情况下，它设置为{@link  LoopResources＃DEFAULT_SHUTDOWN_TIMEOUT}，这是15秒，但也可以使用系统属性{@link  react.netty.ReactorNetty＃SHUTDOWN_TIMEOUT ReactorNetty.SHUTDOWN_TIMEOUT}覆盖。 
	 *  @since 5.2.4 
	 * @see  #setShutdownQuietPeriod（持续时间）
	 */
	public void setShutdownTimeout(Duration shutdownTimeout) {
		Assert.notNull(shutdownTimeout, "shutdownQuietPeriod should not be null");
		this.shutdownTimeout = shutdownTimeout;
	}


	@Override
	public void afterPropertiesSet() {
		if (this.useGlobalResources) {
			Assert.isTrue(this.loopResources == null && this.connectionProvider == null,
					"'useGlobalResources' is mutually exclusive with explicitly configured resources");
			HttpResources httpResources = HttpResources.get();
			if (this.globalResourcesConsumer != null) {
				this.globalResourcesConsumer.accept(httpResources);
			}
			this.connectionProvider = httpResources;
			this.loopResources = httpResources;
		}
		else {
			if (this.loopResources == null) {
				this.manageLoopResources = true;
				this.loopResources = this.loopResourcesSupplier.get();
			}
			if (this.connectionProvider == null) {
				this.manageConnectionProvider = true;
				this.connectionProvider = this.connectionProviderSupplier.get();
			}
		}
	}

	@Override
	public void destroy() {
		if (this.useGlobalResources) {
			HttpResources.disposeLoopsAndConnectionsLater(
					this.shutdownQuietPeriod, this.shutdownTimeout).block();
		}
		else {
			try {
				ConnectionProvider provider = this.connectionProvider;
				if (provider != null && this.manageConnectionProvider) {
					provider.disposeLater().block();
				}
			}
			catch (Throwable ex) {
				// ignore
			}

			try {
				LoopResources resources = this.loopResources;
				if (resources != null && this.manageLoopResources) {
					resources.disposeLater(this.shutdownQuietPeriod, this.shutdownTimeout).block();
				}
			}
			catch (Throwable ex) {
				// ignore
			}
		}
	}

}
