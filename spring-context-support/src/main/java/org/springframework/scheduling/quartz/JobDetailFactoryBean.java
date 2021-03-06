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

package org.springframework.scheduling.quartz;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.JobDetailImpl;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * A Spring {@link FactoryBean} for creating a Quartz {@link org.quartz.JobDetail}
 * instance, supporting bean-style usage for JobDetail configuration.
 *
 * <p>{@code JobDetail(Impl)} itself is already a JavaBean but lacks
 * sensible defaults. This class uses the Spring bean name as job name,
 * and the Quartz default group ("DEFAULT") as job group if not specified.
 *
 * @author Juergen Hoeller
 * @since 3.1
 * @see #setName
 * @see #setGroup
 * @see org.springframework.beans.factory.BeanNameAware
 * @see org.quartz.Scheduler#DEFAULT_GROUP
 */
/**
 * Spring {@link  FactoryBean}用于创建Quartz {@link  org.quartz.JobDetail}实例，支持JobDetail配置的bean风格用法。 
 *  <p> {<@code> JobDetail（Impl）}本身已经是JavaBean，但是缺少合理的默认值。 
 * 此类使用Spring bean名称作为作业名称，并使用Quartz默认组（"DEFAULT"）作为作业组（如果未指定）。 
 *  @author  Juergen Hoeller @始于3.1 
 * @see  #setName 
 * @see  #setGroup 
 * @see  org.springframework.beans.factory.BeanNameAware 
 * @see  org.quartz.Scheduler＃DEFAULT_GROUP
 */
public class JobDetailFactoryBean
		implements FactoryBean<JobDetail>, BeanNameAware, ApplicationContextAware, InitializingBean {

	@Nullable
	private String name;

	@Nullable
	private String group;

	@Nullable
	private Class<? extends Job> jobClass;

	private JobDataMap jobDataMap = new JobDataMap();

	private boolean durability = false;

	private boolean requestsRecovery = false;

	@Nullable
	private String description;

	@Nullable
	private String beanName;

	@Nullable
	private ApplicationContext applicationContext;

	@Nullable
	private String applicationContextJobDataKey;

	@Nullable
	private JobDetail jobDetail;


	/**
	 * Specify the job's name.
	 */
	/**
	 * 指定作业的名称。 
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Specify the job's group.
	 */
	/**
	 * 指定作业的组。 
	 * 
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Specify the job's implementation class.
	 */
	/**
	 * 指定作业的实现类。 
	 * 
	 */
	public void setJobClass(Class<? extends Job> jobClass) {
		this.jobClass = jobClass;
	}

	/**
	 * Set the job's JobDataMap.
	 * @see #setJobDataAsMap
	 */
	/**
	 * 设置作业的JobDataMap。 
	 *  
	 * @see  #setJobDataAsMap
	 */
	public void setJobDataMap(JobDataMap jobDataMap) {
		this.jobDataMap = jobDataMap;
	}

	/**
	 * Return the job's JobDataMap.
	 */
	/**
	 * 返回作业的JobDataMap。 
	 * 
	 */
	public JobDataMap getJobDataMap() {
		return this.jobDataMap;
	}

	/**
	 * Register objects in the JobDataMap via a given Map.
	 * <p>These objects will be available to this Job only,
	 * in contrast to objects in the SchedulerContext.
	 * <p>Note: When using persistent Jobs whose JobDetail will be kept in the
	 * database, do not put Spring-managed beans or an ApplicationContext
	 * reference into the JobDataMap but rather into the SchedulerContext.
	 * @param jobDataAsMap a Map with String keys and any objects as values
	 * (for example Spring-managed beans)
	 * @see org.springframework.scheduling.quartz.SchedulerFactoryBean#setSchedulerContextAsMap
	 */
	/**
	 * 通过给定的Map在JobDataMap中注册对象。 
	 *  <p>与SchedulerContext中的对象相反，这些对象仅可用于此Job。 
	 *  <p>注意：当使用持久性Jobs并将JobDetail保留在数据库中时，请勿将Spring托管的bean或ApplicationContext引用放入JobDataMap中，而应放入SchedulerContext中。 
	 *  
	 * @param  jobDataAsMap一个具有字符串键和任何对象作为值的映射（例如Spring管理的bean）
	 * @see  org.springframework.scheduling.quartz.SchedulerFactoryBean＃setSchedulerContextAsMap
	 */
	public void setJobDataAsMap(Map<String, ?> jobDataAsMap) {
		getJobDataMap().putAll(jobDataAsMap);
	}

	/**
	 * Specify the job's durability, i.e. whether it should remain stored
	 * in the job store even if no triggers point to it anymore.
	 */
	/**
	 * 指定作业的持久性，即即使没有触发器指向它，是否也应将其保留在作业存储中。 
	 * 
	 */
	public void setDurability(boolean durability) {
		this.durability = durability;
	}

	/**
	 * Set the recovery flag for this job, i.e. whether or not the job should
	 * get re-executed if a 'recovery' or 'fail-over' situation is encountered.
	 */
	/**
	 * 设置此作业的恢复标志，即，如果遇到"恢复"或"故障转移"情况，是否应重新执行该作业。 
	 * 
	 */
	public void setRequestsRecovery(boolean requestsRecovery) {
		this.requestsRecovery = requestsRecovery;
	}

	/**
	 * Set a textual description for this job.
	 */
	/**
	 * 为此工作设置文字说明。 
	 * 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * Set the key of an ApplicationContext reference to expose in the JobDataMap,
	 * for example "applicationContext". Default is none.
	 * Only applicable when running in a Spring ApplicationContext.
	 * <p>In case of a QuartzJobBean, the reference will be applied to the Job
	 * instance as bean property. An "applicationContext" attribute will correspond
	 * to a "setApplicationContext" method in that scenario.
	 * <p>Note that BeanFactory callback interfaces like ApplicationContextAware
	 * are not automatically applied to Quartz Job instances, because Quartz
	 * itself is responsible for the lifecycle of its Jobs.
	 * <p><b>Note: When using persistent job stores where JobDetail contents will
	 * be kept in the database, do not put an ApplicationContext reference into
	 * the JobDataMap but rather into the SchedulerContext.</b>
	 * @see org.springframework.scheduling.quartz.SchedulerFactoryBean#setApplicationContextSchedulerContextKey
	 * @see org.springframework.context.ApplicationContext
	 */
	/**
	 * 设置要在JobDataMap中公开的ApplicationContext引用的键，例如"applicationContext"。 
	 * 默认为无。 
	 * 仅在Spring ApplicationContext中运行时适用。 
	 *  <p>如果是QuartzJobBean，则该引用将作为bean属性应用于Job实例。 
	 * 在这种情况下，"applicationContext"属性将对应于"setApplicationContext"方法。 
	 *  <p>请注意，像ApplicationContextAware这样的BeanFactory回调接口不会自动应用于Quartz Job实例，因为Quartz本身负责其Jobs的生命周期。 
	 *  <p> <b>注意：使用持久性作业存储将JobDetail内容保存在数据库中时，请勿将ApplicationContext引用放入JobDataMap中，而应将其放入SchedulerContext中。 
	 * </ b> 
	 * @see  org.springframework。 
	 *  schedule.quartz.SchedulerFactoryBean＃setApplicationContextSchedulerContextKey 
	 * @see  org.springframework.context.ApplicationContext
	 */
	public void setApplicationContextJobDataKey(String applicationContextJobDataKey) {
		this.applicationContextJobDataKey = applicationContextJobDataKey;
	}


	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.jobClass, "Property 'jobClass' is required");

		if (this.name == null) {
			this.name = this.beanName;
		}
		if (this.group == null) {
			this.group = Scheduler.DEFAULT_GROUP;
		}
		if (this.applicationContextJobDataKey != null) {
			if (this.applicationContext == null) {
				throw new IllegalStateException(
						"JobDetailBean needs to be set up in an ApplicationContext " +
						"to be able to handle an 'applicationContextJobDataKey'");
			}
			getJobDataMap().put(this.applicationContextJobDataKey, this.applicationContext);
		}

		JobDetailImpl jdi = new JobDetailImpl();
		jdi.setName(this.name != null ? this.name : toString());
		jdi.setGroup(this.group);
		jdi.setJobClass(this.jobClass);
		jdi.setJobDataMap(this.jobDataMap);
		jdi.setDurability(this.durability);
		jdi.setRequestsRecovery(this.requestsRecovery);
		jdi.setDescription(this.description);
		this.jobDetail = jdi;
	}


	@Override
	@Nullable
	public JobDetail getObject() {
		return this.jobDetail;
	}

	@Override
	public Class<?> getObjectType() {
		return JobDetail.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
