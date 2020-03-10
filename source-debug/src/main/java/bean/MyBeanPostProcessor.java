package bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import printer.MingLogger;
import printer.MingLoggerImpl;

/**
 * @author 17326
 * @date 2020/3/5 0:10
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
	public MyBeanPostProcessor(){
		logger.info("实例化中");
	}
	MingLogger logger = MingLoggerImpl.getLogger(getClass());

	/**
	 * 实例化、依赖注入完毕，在调用显示的初始化之前完成一些定制的初始化任务
	 * 注意：方法返回值不能为null
	 * 如果返回null那么在后续初始化方法将报空指针异常或者通过getBean()方法获取不到bena实例对象
	 * 因为后置处理器从Spring IoC容器中取出bean实例对象没有再次放回IoC容器中
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof MyBean){
			logger.info("初始化实例之前的后置处理器，实例名：" + beanName + " 实例：" + bean);
			logger.info(beanName + "的依赖为：" + ((MyBean) bean).getAutoWiredDepedency());
		}
		return bean;
	}

	/**
	 * 实例化、依赖注入、初始化完毕时执行
	 * 注意：方法返回值不能为null
	 * 如果返回null那么在后续初始化方法将报空指针异常或者通过getBean()方法获取不到bena实例对象
	 * 因为后置处理器从Spring IoC容器中取出bean实例对象没有再次放回IoC容器中
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof MyBean){
			logger.info("初始化实例之后的后置处理器，实例名：" + beanName + " 实例：" + bean);
			logger.info(beanName + "的依赖为：" + ((MyBean) bean).getAutoWiredDepedency());
		}
		return bean;
	}
}
