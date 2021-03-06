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

package org.springframework.util.comparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * A comparator that chains a sequence of one or more Comparators.
 *
 * <p>A compound comparator calls each Comparator in sequence until a single
 * Comparator returns a non-zero result, or the comparators are exhausted and
 * zero is returned.
 *
 * <p>This facilitates in-memory sorting similar to multi-column sorting in SQL.
 * The order of any single Comparator in the list can also be reversed.
 *
 * @author Keith Donald
 * @author Juergen Hoeller
 * @since 1.2.2
 * @param <T> the type of objects that may be compared by this comparator
 * @deprecated as of Spring Framework 5.0, in favor of the standard JDK 8
 * {@link Comparator#thenComparing(Comparator)}
 */
/**
 * 链接一个或多个比较器序列的比较器。 
 *  <p>复合比较器按顺序调用每个比较器，直到单个比较器返回非零结果，或者比较器用尽并返回零。 
 *  <p>这有助于类似于SQL中的多列排序的内存中排序。 
 * 列表中任何单个比较器的顺序也可以颠倒。 
 *  @author  Keith Donald @author  Juergen Hoeller @从1.2.2开始
 * @param  <T>可以从此比较器进行比较的对象的类型@自Spring Framework 5.0起不推荐使用，而支持标准JDK 8 {@link 比较器#thenComparing（比较器）}
 */
@Deprecated
@SuppressWarnings({"serial", "rawtypes"})
public class CompoundComparator<T> implements Comparator<T>, Serializable {

	private final List<InvertibleComparator> comparators;


	/**
	 * Construct a CompoundComparator with initially no Comparators. Clients
	 * must add at least one Comparator before calling the compare method or an
	 * IllegalStateException is thrown.
	 */
	/**
	 * 构造一个最初不带比较器的CompoundComparator。 
	 * 客户端必须至少添加一个Comparator才能调用compare方法，否则将引发IllegalStateException。 
	 * 
	 */
	public CompoundComparator() {
		this.comparators = new ArrayList<>();
	}

	/**
	 * Construct a CompoundComparator from the Comparators in the provided array.
	 * <p>All Comparators will default to ascending sort order,
	 * unless they are InvertibleComparators.
	 * @param comparators the comparators to build into a compound comparator
	 * @see InvertibleComparator
	 */
	/**
	 * 从提供的数组中的Comparators构造一个CompoundComparator。 
	 *  <p>所有比较器将默认为升序排列，除非它们是InvertibleComparators。 
	 *  
	 * @param 比较器将比较器构建为复合比较器
	 * @see  InvertibleComparator
	 */
	@SuppressWarnings("unchecked")
	public CompoundComparator(Comparator... comparators) {
		Assert.notNull(comparators, "Comparators must not be null");
		this.comparators = new ArrayList<>(comparators.length);
		for (Comparator comparator : comparators) {
			addComparator(comparator);
		}
	}


	/**
	 * Add a Comparator to the end of the chain.
	 * <p>The Comparator will default to ascending sort order,
	 * unless it is a InvertibleComparator.
	 * @param comparator the Comparator to add to the end of the chain
	 * @see InvertibleComparator
	 */
	/**
	 * 将比较器添加到链的末尾。 
	 *  <p>比较器将默认为升序排列，除非它是InvertibleComparator。 
	 *  
	 * @param 比较器将比较器添加到链的末尾
	 * @see  InvertibleComparator
	 */
	@SuppressWarnings("unchecked")
	public void addComparator(Comparator<? extends T> comparator) {
		if (comparator instanceof InvertibleComparator) {
			this.comparators.add((InvertibleComparator) comparator);
		}
		else {
			this.comparators.add(new InvertibleComparator(comparator));
		}
	}

	/**
	 * Add a Comparator to the end of the chain using the provided sort order.
	 * @param comparator the Comparator to add to the end of the chain
	 * @param ascending the sort order: ascending (true) or descending (false)
	 */
	/**
	 * 使用提供的排序顺序将比较器添加到链的末尾。 
	 *  
	 * @param 比较器将比较器添加到链的末尾
	 * @param 升序排列：升序（true）或降序（false）
	 */
	@SuppressWarnings("unchecked")
	public void addComparator(Comparator<? extends T> comparator, boolean ascending) {
		this.comparators.add(new InvertibleComparator(comparator, ascending));
	}

	/**
	 * Replace the Comparator at the given index.
	 * <p>The Comparator will default to ascending sort order,
	 * unless it is a InvertibleComparator.
	 * @param index the index of the Comparator to replace
	 * @param comparator the Comparator to place at the given index
	 * @see InvertibleComparator
	 */
	/**
	 * 替换给定索引处的比较器。 
	 *  <p>比较器将默认为升序排列，除非它是InvertibleComparator。 
	 *  
	 * @param 索引比较器的索引以替换
	 * @param 比较器，将比较器放置在给定索引中
	 * @see  InvertibleComparator
	 */
	@SuppressWarnings("unchecked")
	public void setComparator(int index, Comparator<? extends T> comparator) {
		if (comparator instanceof InvertibleComparator) {
			this.comparators.set(index, (InvertibleComparator) comparator);
		}
		else {
			this.comparators.set(index, new InvertibleComparator(comparator));
		}
	}

	/**
	 * Replace the Comparator at the given index using the given sort order.
	 * @param index the index of the Comparator to replace
	 * @param comparator the Comparator to place at the given index
	 * @param ascending the sort order: ascending (true) or descending (false)
	 */
	/**
	 * 使用给定的排序顺序替换给定索引处的Comparator。 
	 *  
	 * @param 索引比较器的索引以替换
	 * @param 比较器，比较器将其放置在给定的索引中
	 * @param 升序排列：升序（true）或降序（false）
	 */
	public void setComparator(int index, Comparator<T> comparator, boolean ascending) {
		this.comparators.set(index, new InvertibleComparator<>(comparator, ascending));
	}

	/**
	 * Invert the sort order of each sort definition contained by this compound
	 * comparator.
	 */
	/**
	 * 反转此复合比较器包含的每个排序定义的排序顺序。 
	 * 
	 */
	public void invertOrder() {
		for (InvertibleComparator comparator : this.comparators) {
			comparator.invertOrder();
		}
	}

	/**
	 * Invert the sort order of the sort definition at the specified index.
	 * @param index the index of the comparator to invert
	 */
	/**
	 * 反转指定索引处的排序定义的排序顺序。 
	 *  
	 * @param  index比较器的索引要反转
	 */
	public void invertOrder(int index) {
		this.comparators.get(index).invertOrder();
	}

	/**
	 * Change the sort order at the given index to ascending.
	 * @param index the index of the comparator to change
	 */
	/**
	 * 将给定索引的排序顺序更改为升序。 
	 *  
	 * @param  index更改比较器的索引
	 */
	public void setAscendingOrder(int index) {
		this.comparators.get(index).setAscending(true);
	}

	/**
	 * Change the sort order at the given index to descending sort.
	 * @param index the index of the comparator to change
	 */
	/**
	 * 将给定索引处的排序顺序更改为降序排序。 
	 *  
	 * @param  index更改比较器的索引
	 */
	public void setDescendingOrder(int index) {
		this.comparators.get(index).setAscending(false);
	}

	/**
	 * Returns the number of aggregated comparators.
	 */
	/**
	 * 返回聚合比较器的数量。 
	 * 
	 */
	public int getComparatorCount() {
		return this.comparators.size();
	}


	@Override
	@SuppressWarnings("unchecked")
	public int compare(T o1, T o2) {
		Assert.state(!this.comparators.isEmpty(),
				"No sort definitions have been added to this CompoundComparator to compare");
		for (InvertibleComparator comparator : this.comparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}


	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(@Nullable Object other) {
		return (this == other || (other instanceof CompoundComparator &&
				this.comparators.equals(((CompoundComparator<T>) other).comparators)));
	}

	@Override
	public int hashCode() {
		return this.comparators.hashCode();
	}

	@Override
	public String toString() {
		return "CompoundComparator: " + this.comparators;
	}

}
