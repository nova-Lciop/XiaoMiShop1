package cn.edu.uzz.service;

import cn.edu.uzz.pojo.Admin;

/**
 * admin的业务层
 * 业务层是一个接口
 * @author JokerQ
 * @Classname AdminService
 * @Description TODO
 * @Date 2022/11/23 10:31
 * @Created by Administrator
 */
public interface AdminService {
    /**
     * 登录
     *
     * @param name 名字
     * @param pwd  密码
     * @return {@code Admin}
     */
    Admin login(String name, String pwd);
}
