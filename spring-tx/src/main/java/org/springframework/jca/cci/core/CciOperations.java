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

package org.springframework.jca.cci.core;

import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;

import org.springframework.dao.DataAccessException;
import org.springframework.lang.Nullable;

/**
 * Interface that specifies a basic set of CCI operations on an EIS.
 * Implemented by CciTemplate. Not often used, but a useful option
 * to enhance testability, as it can easily be mocked or stubbed.
 *
 * <p>Alternatively, the standard CCI infrastructure can be mocked.
 * However, mocking this interface constitutes significantly less work.
 *
 * @author Juergen Hoeller
 * @since 1.2
 * @see CciTemplate
 */
/**
 * 指定EIS上一组基本CCI操作的接口。 
 * 由CciTemplate实现。 
 * 并不经常使用，但是它是增强可测试性的有用选项，因为它很容易被嘲笑或存根。 
 *  <p>或者，可以模拟标准CCI基础结构。 
 * 但是，模拟此接口将大大减少工作量。 
 *  @author  Juergen Hoeller @始于1.2 
 * @see  CciTemplate
 */
public interface CciOperations {

	/**
	 * Execute a request on an EIS with CCI, implemented as callback action
	 * working on a CCI Connection. This allows for implementing arbitrary
	 * data access operations, within Spring's managed CCI environment:
	 * that is, participating in Spring-managed transactions and converting
	 * JCA ResourceExceptions into Spring's DataAccessException hierarchy.
	 * <p>The callback action can return a result object, for example a
	 * domain object or a collection of domain objects.
	 * @param action the callback object that specifies the action
	 * @return the result object returned by the action, if any
	 * @throws DataAccessException if there is any problem
	 */
	/**
	 * 使用CCI在EIS上执行请求，实现为在CCI连接上工作的回调操作。 
	 * 这允许在Spring的托管CCI环境中实现任意数据访问操作：也就是说，参与Spring托管的事务并将JCA ResourceExceptions转换为Spring的DataAccessException层次结构。 
	 *  <p>回调操作可以返回结果对象，例如域对象或域对象的集合。 
	 *  
	 * @param  action指定操作的回调对象
	 * @return 操作返回的结果对象，如果有的话
	 * @throws  DataAccessException如果有任何问题
	 */
	@Nullable
	<T> T execute(ConnectionCallback<T> action) throws DataAccessException;

	/**
	 * Execute a request on an EIS with CCI, implemented as callback action
	 * working on a CCI Interaction. This allows for implementing arbitrary
	 * data access operations on a single Interaction, within Spring's managed
	 * CCI environment: that is, participating in Spring-managed transactions
	 * and converting JCA ResourceExceptions into Spring's DataAccessException
	 * hierarchy.
	 * <p>The callback action can return a result object, for example a
	 * domain object or a collection of domain objects.
	 * @param action the callback object that specifies the action
	 * @return the result object returned by the action, if any
	 * @throws DataAccessException if there is any problem
	 */
	/**
	 * 使用CCI在EIS上执行请求，实现为对CCI交互起作用的回调操作。 
	 * 这允许在Spring的托管CCI环境中的单个Interaction上实现任意数据访问操作：也就是说，参与Spring托管的事务并将JCA ResourceExceptions转换为Spring的DataAccessException层次结构。 
	 *  <p>回调操作可以返回结果对象，例如域对象或域对象的集合。 
	 *  
	 * @param  action指定操作的回调对象
	 * @return 操作返回的结果对象，如果有的话
	 * @throws  DataAccessException如果有任何问题
	 */
	@Nullable
	<T> T execute(InteractionCallback<T> action) throws DataAccessException;

	/**
	 * Execute the specified interaction on an EIS with CCI.
	 * @param spec the CCI InteractionSpec instance that defines
	 * the interaction (connector-specific)
	 * @param inputRecord the input record
	 * @return the output record
	 * @throws DataAccessException if there is any problem
	 */
	/**
	 * 使用CCI在EIS上执行指定的交互。 
	 *  
	 * @param  spec定义交互的CCI InteractionSpec实例（特定于连接器）
	 * @param  inputRecord输入记录
	 * @return 输出记录
	 * @throws  DataAccessException如果存在任何问题
	 */
	@Nullable
	Record execute(InteractionSpec spec, Record inputRecord) throws DataAccessException;

	/**
	 * Execute the specified interaction on an EIS with CCI.
	 * @param spec the CCI InteractionSpec instance that defines
	 * the interaction (connector-specific)
	 * @param inputRecord the input record
	 * @param outputRecord the output record
	 * @throws DataAccessException if there is any problem
	 */
	/**
	 * 使用CCI在EIS上执行指定的交互。 
	 *  
	 * @param  spec定义交互作用的CCI InteractionSpec实例（特定于连接器）
	 * @param  inputRecord输入记录
	 * @param  outputRecord输出记录
	 * @throws  DataAccessException如果存在任何问题
	 */
	void execute(InteractionSpec spec, Record inputRecord, Record outputRecord) throws DataAccessException;

	/**
	 * Execute the specified interaction on an EIS with CCI.
	 * @param spec the CCI InteractionSpec instance that defines
	 * the interaction (connector-specific)
	 * @param inputCreator object that creates the input record to use
	 * @return the output record
	 * @throws DataAccessException if there is any problem
	 */
	/**
	 * 使用CCI在EIS上执行指定的交互。 
	 *  
	 * @param  spec CCI InteractionSpec实例，该实例定义了交互（特定于连接器）
	 * @param  inputCreator对象，该对象创建输入记录以使用
	 * @return 输出记录
	 * @throws  DataAccessException（如果存在任何问题）
	 */
	Record execute(InteractionSpec spec, RecordCreator inputCreator) throws DataAccessException;

	/**
	 * Execute the specified interaction on an EIS with CCI.
	 * @param spec the CCI InteractionSpec instance that defines
	 * the interaction (connector-specific)
	 * @param inputRecord the input record
	 * @param outputExtractor object to convert the output record to a result object
	 * @return the output data extracted with the RecordExtractor object
	 * @throws DataAccessException if there is any problem
	 */
	/**
	 * 使用CCI在EIS上执行指定的交互。 
	 *  
	 * @param  spec定义交互作用的CCI InteractionSpec实例（特定于连接器）
	 * @param  inputRecord输入记录
	 * @param  outputExtractor对象，将输出记录转换为结果对象，<
	 * @return>，将输出数据提取为如果有任何问题，则RecordExtractor对象
	 * @throws  DataAccessException
	 */
	@Nullable
	<T> T execute(InteractionSpec spec, Record inputRecord, RecordExtractor<T> outputExtractor)
			throws DataAccessException;

	/**
	 * Execute the specified interaction on an EIS with CCI.
	 * @param spec the CCI InteractionSpec instance that defines
	 * the interaction (connector-specific)
	 * @param inputCreator object that creates the input record to use
	 * @param outputExtractor object to convert the output record to a result object
	 * @return the output data extracted with the RecordExtractor object
	 * @throws DataAccessException if there is any problem
	 */
	/**
	 * 使用CCI在EIS上执行指定的交互。 
	 *  
	 * @param  spec CCI InteractionSpec实例，该实例定义了交互（特定于连接器）
	 * @param  inputCreator对象，该对象创建输入记录以使用
	 * @param  outputExtractor对象将输出记录转换为结果对象
	 * @return 如果有任何问题，请使用RecordExtractor对象
	 * @throws  DataAccessException提取的输出数据
	 */
	@Nullable
	<T> T execute(InteractionSpec spec, RecordCreator inputCreator, RecordExtractor<T> outputExtractor)
			throws DataAccessException;

}
