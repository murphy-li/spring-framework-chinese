package bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import printer.MingLogger;
import printer.MingLoggerImpl;

/**
 * @author 17326
 * @date 2020/3/5 0:09
 */
@Component
public class MyBeanFactoryProcessor implements BeanFactoryPostProcessor {
	public MyBeanFactoryProcessor(){
		logger.info("实例化中");
	}
	MingLogger logger = MingLoggerImpl.getLogger(getClass());
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		logger.info("正在执行postProcessBeanFactory");
	}
}
