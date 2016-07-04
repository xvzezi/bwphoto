package util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * SpringIoC
 * @author Xu Zezi
 * @category Utility
 * @version
 * 		0	get class through classname by IoC.xml
 * 		0.1	get class through bean id by IoC.xml 6/29
 * @since 2016/6/28
 * @Description
 *   A Helper Class Easy To Get IoC Class
 */
public class SpringIoC {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"IoC.xml"});
	public static <T> T classGetter(Class<T> target)
	{
		return context.getBean(target);
	}
	public static <T> T idGetter(String beanId, Class<T> classname)
	{
		return (T)context.getBean(beanId);
	}
}
