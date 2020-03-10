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
public class AlphaBean implements MyBean {
	@Autowired
	BetaBean betaBean;
	MingLogger logger = MingLoggerImpl.getLogger(getClass());
	@PostConstruct
	@Override
	public void postConstruct(){

		logger.info("正在执行postConstruct, lifeCycleBeanBravo值为：" + betaBean);
	}

	@Override
	public Object getAutoWiredDepedency() {
		return betaBean;
	}

	public AlphaBean(){
		logger.info(getClass().getName() + "实例化中myBeanBravo值为：" + betaBean);
	}
}
