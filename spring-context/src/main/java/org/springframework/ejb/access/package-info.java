/** Generated by english-annotation-buster, Powered by Google Translate.**/
/**
 * This package contains classes that allow easy access to EJBs.
 * The basis are AOP interceptors run before and after the EJB invocation.
 * In particular, the classes in this package allow transparent access
 * to stateless session beans (SLSBs) with local interfaces, avoiding
 * the need for application code using them to use EJB-specific APIs
 * and JNDI lookups, and work with business interfaces that could be
 * implemented without using EJB. This provides a valuable decoupling
 * of client (such as web components) and business objects (which may
 * or may not be EJBs). This gives us the choice of introducing EJB
 * into an application (or removing EJB from an application) without
 * affecting code using business objects.
 *
 * <p>The motivation for the classes in this package is discussed in Chapter 11 of
 * <a href="https://www.amazon.com/exec/obidos/tg/detail/-/0764543857/">Expert One-On-One J2EE Design and Development</a>
 * by Rod Johnson (Wrox, 2002).
 *
 * <p>However, the implementation and naming of classes in this package has changed.
 * It now uses FactoryBeans and AOP, rather than the custom bean definitions described in
 * <i>Expert One-on-One J2EE</i>.
 */
/**
 * 该软件包包含允许轻松访问EJB的类。 
 * 基础是AOP拦截器在EJB调用之前和之后运行。 
 * 特别是，此包中的类允许使用本地接口透明访问无状态会话Bean（SLSB），从而避免了使用它们使用EJB特定的API和JNDI查找的应用程序代码的需要，并且可以使用无需使用EJB。 
 * 这提供了客户端（例如Web组件）和业务对象（可能是也可能不是EJB）的有价值的分离。 
 * 这使我们可以选择在应用程序中引入EJB（或从应用程序中删除EJB）而不会影响使用业务对象的代码。 
 *  <p>在<a href="https://www.amazon.com/exec/obidos/tg/detail/-//0764543857/">专家一对一的第11章中讨论了此程序包中的类的动机。 
 *  -Rod Johnson的一个J2EE设计与开发</a>（Wrox，2002年）。 
 *  <p>但是，此程序包中类的实现和命名已更改。 
 * 现在，它使用FactoryBeans和AOP，而不是<i>专家一对一J2EE </ i>中描述的自定义bean定义。 
 * 
 */
@NonNullApi
@NonNullFields
package org.springframework.ejb.access;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;
