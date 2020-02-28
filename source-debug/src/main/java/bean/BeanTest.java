package bean;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

/**
 * @author 17326
 * @date 2020/2/28 11:08
 */
@Component
public class BeanTest extends AppConfig {
	String value;
	public BeanTest(){
		this.value = "value";
	}
}
