package cn.edu.uzz.controller;

import cn.edu.uzz.pojo.Admin;
import cn.edu.uzz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * admin控制层
 *
 * @author JokerQ
 * @Classname AdminAction
 * @Description TODO
 * @Date 2022/11/23 11:12
 * @Created by Administrator
 */
@Controller//注解的意思是声明这个class是控制层，一定要加
@RequestMapping("/admin")//请求地址的路径
public class AdminAction {
    /**
     *     实现登录的判断，要进行相应的跳转
     *     在所有的控制层中，一定要有业务逻辑层对象
     */
    @Autowired
    private AdminService adminService;


    /**
     * 登录
     *
     * @param name    名字
     * @param pwd     密码
     * @param request 请求
     * @return {@code String}
     */
    @RequestMapping("/login")
    private String login(String name, String pwd, HttpServletRequest request){
        //通过业务层的登录方法，传入用户名和密码，返回Admin对象
        Admin admin = adminService.login(name, pwd);
        //判断admin对象是否登录成功
        if (admin != null){
            //登录成功
            //把用户名放到request中，然后登录后，在main.jsp中通过${admin.aName}获取到登录用户的用户名显示在右上角
            request.setAttribute("admin",admin);
            return "main";//登录成功后，跳转到main.jsp
        } else {
            //登录失败
            request.setAttribute("errmsg","用户名或者密码错误！！！");
            return "login";//如果登录失败的话，要跳转回登录页面
        }
    }
}
