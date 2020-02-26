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

package org.springframework.orm.jpa.persistenceunit;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * Spring's base implementation of the JPA
 * {@link javax.persistence.spi.PersistenceUnitInfo} interface,
 * used to bootstrap an {@code EntityManagerFactory} in a container.
 *
 * <p>This implementation is largely a JavaBean, offering mutators
 * for all standard {@code PersistenceUnitInfo} properties.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Costin Leau
 * @since 2.0
 */
/**
 * Spring的JPA {@link  javax.persistence.spi.PersistenceUnitInfo}接口的基础实现，用于在容器中引导{@code  EntityManagerFactory}。 
 *  <p>此实现很大程度上是JavaBean，为所有标准{@code  PersistenceUnitInfo}属性提供了变量。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller @author  Costin Leau @since 2.0
 */
public class MutablePersistenceUnitInfo implements SmartPersistenceUnitInfo {

	@Nullable
	private String persistenceUnitName;

	@Nullable
	private String persistenceProviderClassName;

	@Nullable
	private PersistenceUnitTransactionType transactionType;

	@Nullable
	private DataSource nonJtaDataSource;

	@Nullable
	private DataSource jtaDataSource;

	private final List<String> mappingFileNames = new LinkedList<>();

	private List<URL> jarFileUrls = new LinkedList<>();

	@Nullable
	private URL persistenceUnitRootUrl;

	private final List<String> managedClassNames = new LinkedList<>();

	private final List<String> managedPackages = new LinkedList<>();

	private boolean excludeUnlistedClasses = false;

	private SharedCacheMode sharedCacheMode = SharedCacheMode.UNSPECIFIED;

	private ValidationMode validationMode = ValidationMode.AUTO;

	private Properties properties = new Properties();

	private String persistenceXMLSchemaVersion = "2.0";

	@Nullable
	private String persistenceProviderPackageName;


	public void setPersistenceUnitName(@Nullable String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	@Override
	@Nullable
	public String getPersistenceUnitName() {
		return this.persistenceUnitName;
	}

	public void setPersistenceProviderClassName(@Nullable String persistenceProviderClassName) {
		this.persistenceProviderClassName = persistenceProviderClassName;
	}

	@Override
	@Nullable
	public String getPersistenceProviderClassName() {
		return this.persistenceProviderClassName;
	}

	public void setTransactionType(PersistenceUnitTransactionType transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public PersistenceUnitTransactionType getTransactionType() {
		if (this.transactionType != null) {
			return this.transactionType;
		}
		else {
			return (this.jtaDataSource != null ?
					PersistenceUnitTransactionType.JTA : PersistenceUnitTransactionType.RESOURCE_LOCAL);
		}
	}

	public void setJtaDataSource(@Nullable DataSource jtaDataSource) {
		this.jtaDataSource = jtaDataSource;
	}

	@Override
	@Nullable
	public DataSource getJtaDataSource() {
		return this.jtaDataSource;
	}

	public void setNonJtaDataSource(@Nullable DataSource nonJtaDataSource) {
		this.nonJtaDataSource = nonJtaDataSource;
	}

	@Override
	@Nullable
	public DataSource getNonJtaDataSource() {
		return this.nonJtaDataSource;
	}

	public void addMappingFileName(String mappingFileName) {
		this.mappingFileNames.add(mappingFileName);
	}

	@Override
	public List<String> getMappingFileNames() {
		return this.mappingFileNames;
	}

	public void addJarFileUrl(URL jarFileUrl) {
		this.jarFileUrls.add(jarFileUrl);
	}

	@Override
	public List<URL> getJarFileUrls() {
		return this.jarFileUrls;
	}

	public void setPersistenceUnitRootUrl(@Nullable URL persistenceUnitRootUrl) {
		this.persistenceUnitRootUrl = persistenceUnitRootUrl;
	}

	@Override
	@Nullable
	public URL getPersistenceUnitRootUrl() {
		return this.persistenceUnitRootUrl;
	}

	/**
	 * Add a managed class name to the persistence provider's metadata.
	 * @see javax.persistence.spi.PersistenceUnitInfo#getManagedClassNames()
	 * @see #addManagedPackage
	 */
	/**
	 * 将托管类名称添加到持久性提供程序的元数据中。 
	 *  
	 * @see  javax.persistence.spi.PersistenceUnitInfo＃getManagedClassNames（）
	 * @see  #addManagedPackage
	 */
	public void addManagedClassName(String managedClassName) {
		this.managedClassNames.add(managedClassName);
	}

	@Override
	public List<String> getManagedClassNames() {
		return this.managedClassNames;
	}

	/**
	 * Add a managed package to the persistence provider's metadata.
	 * <p>Note: This refers to annotated {@code package-info.java} files. It does
	 * <i>not</i> trigger entity scanning in the specified package; this is
	 * rather the job of {@link DefaultPersistenceUnitManager#setPackagesToScan}.
	 * @since 4.1
	 * @see SmartPersistenceUnitInfo#getManagedPackages()
	 * @see #addManagedClassName
	 */
	/**
	 * 将托管程序包添加到持久性提供程序的元数据。 
	 *  <p>注意：这是指带注释的{@code  package-info.java}文件。 
	 * 它不会<i>不</ i>触发指定包中的实体扫描； 
	 * 这是{@link  DefaultPersistenceUnitManager＃setPackagesToScan}的工作。 
	 *  @since 4.1 
	 * @see  SmartPersistenceUnitInfo＃getManagedPackages（）
	 * @see  #addManagedClassName
	 */
	public void addManagedPackage(String packageName) {
		this.managedPackages.add(packageName);
	}

	@Override
	public List<String> getManagedPackages() {
		return this.managedPackages;
	}

	public void setExcludeUnlistedClasses(boolean excludeUnlistedClasses) {
		this.excludeUnlistedClasses = excludeUnlistedClasses;
	}

	@Override
	public boolean excludeUnlistedClasses() {
		return this.excludeUnlistedClasses;
	}

	public void setSharedCacheMode(SharedCacheMode sharedCacheMode) {
		this.sharedCacheMode = sharedCacheMode;
	}

	@Override
	public SharedCacheMode getSharedCacheMode() {
		return this.sharedCacheMode;
	}

	public void setValidationMode(ValidationMode validationMode) {
		this.validationMode = validationMode;
	}

	@Override
	public ValidationMode getValidationMode() {
		return this.validationMode;
	}

	public void addProperty(String name, String value) {
		this.properties.setProperty(name, value);
	}

	public void setProperties(Properties properties) {
		Assert.notNull(properties, "Properties must not be null");
		this.properties = properties;
	}

	@Override
	public Properties getProperties() {
		return this.properties;
	}

	public void setPersistenceXMLSchemaVersion(String persistenceXMLSchemaVersion) {
		this.persistenceXMLSchemaVersion = persistenceXMLSchemaVersion;
	}

	@Override
	public String getPersistenceXMLSchemaVersion() {
		return this.persistenceXMLSchemaVersion;
	}

	@Override
	public void setPersistenceProviderPackageName(@Nullable String persistenceProviderPackageName) {
		this.persistenceProviderPackageName = persistenceProviderPackageName;
	}

	@Nullable
	public String getPersistenceProviderPackageName() {
		return this.persistenceProviderPackageName;
	}


	/**
	 * This implementation returns the default ClassLoader.
	 * @see org.springframework.util.ClassUtils#getDefaultClassLoader()
	 */
	/**
	 * 此实现返回默认的ClassLoader。 
	 *  
	 * @see  org.springframework.util.ClassUtils＃getDefaultClassLoader（）
	 */
	@Override
	@Nullable
	public ClassLoader getClassLoader() {
		return ClassUtils.getDefaultClassLoader();
	}

	/**
	 * This implementation throws an UnsupportedOperationException.
	 */
	/**
	 * 此实现引发UnsupportedOperationException。 
	 * 
	 */
	@Override
	public void addTransformer(ClassTransformer classTransformer) {
		throw new UnsupportedOperationException("addTransformer not supported");
	}

	/**
	 * This implementation throws an UnsupportedOperationException.
	 */
	/**
	 * 此实现引发UnsupportedOperationException。 
	 * 
	 */
	@Override
	public ClassLoader getNewTempClassLoader() {
		throw new UnsupportedOperationException("getNewTempClassLoader not supported");
	}


	@Override
	public String toString() {
		return "PersistenceUnitInfo: name '" + this.persistenceUnitName +
				"', root URL [" + this.persistenceUnitRootUrl + "]";
	}

}
