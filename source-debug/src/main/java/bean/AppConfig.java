/** Generated by english-annotation-buster, Powered by Google Translate.**/
package bean; /** Generated by english-annotation-buster, Powered by Google Translate.**/
 /**
  * 由英语注释破坏程序生成，由Google翻译支持。 
  * 
  */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import printer.MingLogger;

/**
 * @author 17326
 * @date 2020/2/25 0:05
 */
/**
 * @author  17326 @日期2020/2/25 0:05
 */
/** 
 * @author 17326
 *    @date 2020年2月25日0:05
 */
/**
 * @author  17326 @日期2020年2月25日0:05
 */
@Configuration
@ComponentScan("bean")
@Import(MingLogger.class)
public class AppConfig {
	@Bean
	Object java(){
		return new Object();
	}
}
