package bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import printer.MingLogger;
import printer.MingLoggerImpl;

/**
 * @author 17326
 * @date 2020/3/5 0:08
 */
@Component
public class CharlieFactoryBean implements FactoryBean<CharlieFactoryBean> {
	MingLogger logger = MingLoggerImpl.getLogger(getClass());
	public CharlieFactoryBean(){
		logger.info(getClass().getName() + "实例化中");
	}
	@Override
	public CharlieFactoryBean getObject() throws Exception {
		return new CharlieFactoryBean();
	}

	@Override
	public Class<?> getObjectType() {
		return CharlieFactoryBean.class;
	}
}
