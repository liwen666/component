package customer;
import com.temp.component.dubbo.provider.IProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 
 * 项目名称：dubboconsumer   
 * 类名称：MConsumer   
 * 类描述： 消费者  
 * 创建人：YinXiangBing   
 * 创建时间：2015年8月18日 下午4:54:33    
 * @version 1.0   
 *
 */
public class MConsumer {
	private static final Logger logger = LoggerFactory.getLogger(MConsumer.class);

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "customer/application.xml" });
		context.start();
		logger.info("Welcome home! The client locale is {}." );
		IProduct product = (IProduct) context.getBean("productService");// 获取远程服务代理
		String val = product.getProductName();// 执行远程方法
		System.out.println(val);// 显示调用结果
		System.in.read();//按任意键退出
	}


 
}