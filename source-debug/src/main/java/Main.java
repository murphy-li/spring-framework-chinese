/** Generated by english-annotation-buster, Powered by Google Translate.**/
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author 17326
 * @date 2020/2/24 23:58
 */
/** 
 * @author 17326
 *    @date 2020年2月24日23:58
 */
public class Main {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		final String bean = applicationContext.getBean(String.class);
		System.out.println(bean);
	}
}