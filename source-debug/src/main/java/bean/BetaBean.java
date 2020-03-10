package bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import printer.MingLogger;
import printer.MingLoggerImpl;

import javax.annotation.PostConstruct;

/**
 * @author 17326
 * @date 2020/3/5 0:08
 */
@Component
public class BetaBean implements MyBean {
	@Autowired
	AlphaBean alphaBean;
	MingLogger logger = MingLoggerImpl.getLogger(getClass());
	@PostConstruct
	@Override
	public void postConstruct(){
		logger.info("正在执行postConstruct, lifeCycleBeanAlpha值为：" + alphaBean);
	}

	@Override
	public Object getAutoWiredDepedency() {
		return alphaBean;
	}

	public BetaBean(){
		logger.info(getClass().getName() + "实例化中myBeanAlpha值为：" + alphaBean);
	}
}
