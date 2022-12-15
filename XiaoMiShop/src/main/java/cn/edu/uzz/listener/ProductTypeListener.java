package cn.edu.uzz.listener;

import cn.edu.uzz.pojo.ProductType;
import cn.edu.uzz.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * @Classname ProductTypeListener
 * @Description TODO
 * @Date 2022/11/28 16:16
 * @Created by Administrator
 */
@WebListener//注册为监听器
public class ProductTypeListener implements ServletContextListener {
    /**
     * 环境初始化
     *
     * @param servletContextEvent servlet上下文事件
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手动从spring容器中取出ProductTypeServiceImpl对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-*.xml");
        //也可以在ProductTypeServiceImpl的@Service注释后，手动的指定他的bean的别名
        //如果指定了别名，这里getBean()中写入他的别名即可
        ProductTypeService typeService = (ProductTypeService) ac.getBean("productTypeServiceImpl");
        //ProductTypeService typeService = new ProductTypeServiceImpl();
        List<ProductType> typeList = typeService.getAll();
        //放入全局应用的作用域中，可以给新增页面、修改页面、前台查询功能的页面等提供全部商品的类别的集合
        servletContextEvent.getServletContext().setAttribute("ptlist",typeList);
    }

    /**
     * 环境销毁
     *
     * @param servletContextEvent servlet上下文事件
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
