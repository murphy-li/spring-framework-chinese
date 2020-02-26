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

import java.util.Date;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Constants;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * A Spring {@link FactoryBean} for creating a Quartz {@link org.quartz.SimpleTrigger}
 * instance, supporting bean-style usage for trigger configuration.
 *
 * <p>{@code SimpleTrigger(Impl)} itself is already a JavaBean but lacks sensible defaults.
 * This class uses the Spring bean name as job name, the Quartz default group ("DEFAULT")
 * as job group, the current time as start time, and indefinite repetition, if not specified.
 *
 * <p>This class will also register the trigger with the job name and group of
 * a given {@link org.quartz.JobDetail}. This allows {@link SchedulerFactoryBean}
 * to automatically register a trigger for the corresponding JobDetail,
 * instead of registering the JobDetail separately.
 *
 * @author Juergen Hoeller
 * @since 3.1
 * @see #setName
 * @see #setGroup
 * @see #setStartDelay
 * @see #setJobDetail
 * @see SchedulerFactoryBean#setTriggers
 * @see SchedulerFactoryBean#setJobDetails
 */
/**
 * 用于创建Quartz {@link  org.quartz.SimpleTrigger}实例的Spring {@link  FactoryBean}，支持触发器配置的bean样式用法。 
 *  <p> {<@code> SimpleTrigger（Impl）}本身已经是JavaBean，但是缺少合理的默认值。 
 * 此类使用Spring bean名称作为作业名称，将Quartz默认组（"DEFAULT"）用作作业组，将当前时间用作开始时间，以及不确定的重复（如果未指定）。 
 *  <p>该类还将使用给定的{@link  org.quartz.JobDetail}的作业名称和组来注册触发器。 
 * 这允许{@link  SchedulerFactoryBean}自动为相应的JobDetail注册触发器，而不是分别注册JobDetail。 
 *  @author 于尔根·霍勒（Juergen Hoeller）@since 3.1起
 */
public class SimpleTriggerFactoryBean implements FactoryBean<SimpleTrigger>, BeanNameAware, InitializingBean {

	/** Constants for the SimpleTrigger class. */
	/**
	 * SimpleTrigger类的常量。 
	 * 
	 */
	private static final Constants constants = new Constants(SimpleTrigger.class);


	@Nullable
	private String name;

	@Nullable
	private String group;

	@Nullable
	private JobDetail jobDetail;

	private JobDataMap jobDataMap = new JobDataMap();

	@Nullable
	private Date startTime;

	private long startDelay;

	private long repeatInterval;

	private int repeatCount = -1;

	private int priority;

	private int misfireInstruction;

	@Nullable
	private String description;

	@Nullable
	private String beanName;

	@Nullable
	private SimpleTrigger simpleTrigger;


	/**
	 * Specify the trigger's name.
	 */
	/**
	 * 指定触发器的名称。 
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Specify the trigger's group.
	 */
	/**
	 * 指定触发器的组。 
	 * 
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Set the JobDetail that this trigger should be associated with.
	 */
	/**
	 * 设置此触发器应与之关联的JobDetail。 
	 * 
	 */
	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	/**
	 * Set the trigger's JobDataMap.
	 * @see #setJobDataAsMap
	 */
	/**
	 * 设置触发器的JobDataMap。 
	 *  
	 * @see  #setJobDataAsMap
	 */
	public void setJobDataMap(JobDataMap jobDataMap) {
		this.jobDataMap = jobDataMap;
	}

	/**
	 * Return the trigger's JobDataMap.
	 */
	/**
	 * 返回触发器的JobDataMap。 
	 * 
	 */
	public JobDataMap getJobDataMap() {
		return this.jobDataMap;
	}

	/**
	 * Register objects in the JobDataMap via a given Map.
	 * <p>These objects will be available to this Trigger only,
	 * in contrast to objects in the JobDetail's data map.
	 * @param jobDataAsMap a Map with String keys and any objects as values
	 * (for example Spring-managed beans)
	 */
	/**
	 * 通过给定的Map在JobDataMap中注册对象。 
	 *  <p>与JobDetail数据图中的对象相反，这些对象仅可用于此触发器。 
	 *  
	 * @param  jobDataAsMap一个具有String键和任何对象作为值的Map（例如Spring管理的bean）
	 */
	public void setJobDataAsMap(Map<String, ?> jobDataAsMap) {
		this.jobDataMap.putAll(jobDataAsMap);
	}

	/**
	 * Set a specific start time for the trigger.
	 * <p>Note that a dynamically computed {@link #setStartDelay} specification
	 * overrides a static timestamp set here.
	 */
	/**
	 * 设置触发器的特定开始时间。 
	 *  <p>请注意，动态计算的{@link  #setStartDelay}规范会覆盖此处设置的静态时间戳。 
	 * 
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * Set the start delay in milliseconds.
	 * <p>The start delay is added to the current system time (when the bean starts)
	 * to control the start time of the trigger.
	 * @see #setStartTime
	 */
	/**
	 * 设置启动延迟（以毫秒为单位）。 
	 *  <p>启动延迟被添加到当前系统时间（bean启动时），以控制触发器的启动时间。 
	 *  
	 * @see  #setStartTime
	 */
	public void setStartDelay(long startDelay) {
		Assert.isTrue(startDelay >= 0, "Start delay cannot be negative");
		this.startDelay = startDelay;
	}

	/**
	 * Specify the interval between execution times of this trigger.
	 */
	/**
	 * 指定此触发器的执行时间之间的间隔。 
	 * 
	 */
	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	/**
	 * Specify the number of times this trigger is supposed to fire.
	 * <p>Default is to repeat indefinitely.
	 */
	/**
	 * 指定该触发器应触发的次数。 
	 *  <p>默认为无限期重复。 
	 * 
	 */
	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	/**
	 * Specify the priority of this trigger.
	 */
	/**
	 * 指定此触发器的优先级。 
	 * 
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * Specify a misfire instruction for this trigger.
	 */
	/**
	 * 为此触发器指定一个失火指令。 
	 * 
	 */
	public void setMisfireInstruction(int misfireInstruction) {
		this.misfireInstruction = misfireInstruction;
	}

	/**
	 * Set the misfire instruction via the name of the corresponding
	 * constant in the {@link org.quartz.SimpleTrigger} class.
	 * Default is {@code MISFIRE_INSTRUCTION_SMART_POLICY}.
	 * @see org.quartz.SimpleTrigger#MISFIRE_INSTRUCTION_FIRE_NOW
	 * @see org.quartz.SimpleTrigger#MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT
	 * @see org.quartz.SimpleTrigger#MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT
	 * @see org.quartz.SimpleTrigger#MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT
	 * @see org.quartz.SimpleTrigger#MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT
	 * @see org.quartz.Trigger#MISFIRE_INSTRUCTION_SMART_POLICY
	 */
	/**
	 * 通过{@link  org.quartz.SimpleTrigger}类中相应常数的名称设置断火指令。 
	 * 默认值为{@code  MISFIRE_INSTRUCTION_SMART_POLICY}。 
	 *  
	 * @see  org.quartz.SimpleTrigger＃MISFIRE_INSTRUCTION_FIRE_NOW 
	 * @see  org.quartz.SimpleTrigger＃MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT 
	 * @see  org.quartz.SimpleTrigger＃MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT 
	 * @see  org.quartz.SimpleTrigger＃MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT 
	 * @see 有机crystal.SimpleTrigger＃MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT 
	 * @see  org.quartz.Trigger＃MISFIRE_INSTRUCTION_SMART_POLICY
	 */
	public void setMisfireInstructionName(String constantName) {
		this.misfireInstruction = constants.asNumber(constantName).intValue();
	}

	/**
	 * Associate a textual description with this trigger.
	 */
	/**
	 * 将文本描述与此触发器关联。 
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
	public void afterPropertiesSet() {
		if (this.name == null) {
			this.name = this.beanName;
		}
		if (this.group == null) {
			this.group = Scheduler.DEFAULT_GROUP;
		}
		if (this.jobDetail != null) {
			this.jobDataMap.put("jobDetail", this.jobDetail);
		}
		if (this.startDelay > 0 || this.startTime == null) {
			this.startTime = new Date(System.currentTimeMillis() + this.startDelay);
		}

		SimpleTriggerImpl sti = new SimpleTriggerImpl();
		sti.setName(this.name != null ? this.name : toString());
		sti.setGroup(this.group);
		if (this.jobDetail != null) {
			sti.setJobKey(this.jobDetail.getKey());
		}
		sti.setJobDataMap(this.jobDataMap);
		sti.setStartTime(this.startTime);
		sti.setRepeatInterval(this.repeatInterval);
		sti.setRepeatCount(this.repeatCount);
		sti.setPriority(this.priority);
		sti.setMisfireInstruction(this.misfireInstruction);
		sti.setDescription(this.description);
		this.simpleTrigger = sti;
	}


	@Override
	@Nullable
	public SimpleTrigger getObject() {
		return this.simpleTrigger;
	}

	@Override
	public Class<?> getObjectType() {
		return SimpleTrigger.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
