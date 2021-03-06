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

package org.springframework.aop.target;

import java.util.HashSet;
import java.util.Set;

import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.NamedThreadLocal;

/**
 * Alternative to an object pool. This {@link org.springframework.aop.TargetSource}
 * uses a threading model in which every thread has its own copy of the target.
 * There's no contention for targets. Target object creation is kept to a minimum
 * on the running server.
 *
 * <p>Application code is written as to a normal pool; callers can't assume they
 * will be dealing with the same instance in invocations in different threads.
 * However, state can be relied on during the operations of a single thread:
 * for example, if one caller makes repeated calls on the AOP proxy.
 *
 * <p>Cleanup of thread-bound objects is performed on BeanFactory destruction,
 * calling their {@code DisposableBean.destroy()} method if available.
 * Be aware that many thread-bound objects can be around until the application
 * actually shuts down.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @see ThreadLocalTargetSourceStats
 * @see org.springframework.beans.factory.DisposableBean#destroy()
 */
/**
 * 替代对象池。 
 * 此{@link  org.springframework.aop.TargetSource}使用线程模型，其中每个线程都有自己的目标副本。 
 * 目标无争。 
 * 目标对象的创建在正在运行的服务器上保持最少。 
 *  <p>应用程序代码是针对普通池编写的； 
 * 调用者无法假定他们将在不同线程的调用中处理同一实例。 
 * 但是，可以在单个线程的操作期间依赖状态：例如，如果一个调用者在AOP代理上进行重复调用。 
 *  <p>对线程绑定对象的清理在BeanFactory销毁时执行，调用它们的{@code  DisposableBean.destroy（）}方法（如果可用）。 
 * 请注意，在应用程序实际关闭之前，可能存在许多线程绑定对象。 
 *  @author  Rod Johnson @author  Juergen Hoeller @author  Rob Harrop 
 * @see  ThreadLocalTargetSourceStats 
 * @see  org.springframework.beans.factory.DisposableBean＃destroy（）
 */
@SuppressWarnings("serial")
public class ThreadLocalTargetSource extends AbstractPrototypeBasedTargetSource
		implements ThreadLocalTargetSourceStats, DisposableBean {

	/**
	 * ThreadLocal holding the target associated with the current
	 * thread. Unlike most ThreadLocals, which are static, this variable
	 * is meant to be per thread per instance of the ThreadLocalTargetSource class.
	 */
	/**
	 * ThreadLocal保存与当前线程关联的目标。 
	 * 与大多数静态的ThreadLocal不同，此变量用于ThreadLocalTargetSource类的每个实例的每个线程。 
	 * 
	 */
	private final ThreadLocal<Object> targetInThread =
			new NamedThreadLocal<>("Thread-local instance of bean '" + getTargetBeanName() + "'");

	/**
	 * Set of managed targets, enabling us to keep track of the targets we've created.
	 */
	/**
	 * 一组托管目标，使我们能够跟踪已创建的目标。 
	 * 
	 */
	private final Set<Object> targetSet = new HashSet<>();

	private int invocationCount;

	private int hitCount;


	/**
	 * Implementation of abstract getTarget() method.
	 * We look for a target held in a ThreadLocal. If we don't find one,
	 * we create one and bind it to the thread. No synchronization is required.
	 */
	/**
	 * 抽象getTarget（）方法的实现。 
	 * 我们寻找ThreadLocal中保存的目标。 
	 * 如果找不到，我们将创建一个并将其绑定到线程。 
	 * 不需要同步。 
	 * 
	 */
	@Override
	public Object getTarget() throws BeansException {
		++this.invocationCount;
		Object target = this.targetInThread.get();
		if (target == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("No target for prototype '" + getTargetBeanName() + "' bound to thread: " +
						"creating one and binding it to thread '" + Thread.currentThread().getName() + "'");
			}
			// Associate target with ThreadLocal.
			target = newPrototypeInstance();
			this.targetInThread.set(target);
			synchronized (this.targetSet) {
				this.targetSet.add(target);
			}
		}
		else {
			++this.hitCount;
		}
		return target;
	}

	/**
	 * Dispose of targets if necessary; clear ThreadLocal.
	 * @see #destroyPrototypeInstance
	 */
	/**
	 * 如有必要，处置目标； 
	 * 清除ThreadLocal。 
	 *  
	 * @see  #destroyPrototypeInstance
	 */
	@Override
	public void destroy() {
		logger.debug("Destroying ThreadLocalTargetSource bindings");
		synchronized (this.targetSet) {
			for (Object target : this.targetSet) {
				destroyPrototypeInstance(target);
			}
			this.targetSet.clear();
		}
		// Clear ThreadLocal, just in case.
		this.targetInThread.remove();
	}


	@Override
	public int getInvocationCount() {
		return this.invocationCount;
	}

	@Override
	public int getHitCount() {
		return this.hitCount;
	}

	@Override
	public int getObjectCount() {
		synchronized (this.targetSet) {
			return this.targetSet.size();
		}
	}


	/**
	 * Return an introduction advisor mixin that allows the AOP proxy to be
	 * cast to ThreadLocalInvokerStats.
	 */
	/**
	 * 返回一个导入顾问Mixin，它允许将AOP代理强制转换为ThreadLocalInvokerStats。 
	 * 
	 */
	public IntroductionAdvisor getStatsMixin() {
		DelegatingIntroductionInterceptor dii = new DelegatingIntroductionInterceptor(this);
		return new DefaultIntroductionAdvisor(dii, ThreadLocalTargetSourceStats.class);
	}

}
