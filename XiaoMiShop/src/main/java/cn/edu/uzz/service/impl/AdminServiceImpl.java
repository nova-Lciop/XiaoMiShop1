package cn.edu.uzz.service.impl;

import cn.edu.uzz.mapper.AdminMapper;
import cn.edu.uzz.pojo.Admin;
import cn.edu.uzz.pojo.AdminExample;
import cn.edu.uzz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname AdminServiceImpl
 * @Description TODO
 * @Date 2022/11/23 10:35
 * @Created by Administrator
 */
@Service//指明我们这个class是业务层
public class AdminServiceImpl implements AdminService {
    /**
     * admin映射器
     * 在业务逻辑中，一定会有数据库访问层对象
     */
    @Autowired
    private AdminMapper adminMapper;
    /**
     * 登录业务实现
     * select * from admin where a_name= 'admin';
     * @param name 名字
     * @param pwd  密码
     * @return {@code Admin}
     */
    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户名来到数据库中查询用户的对象是否存在
        AdminExample adminExample = new AdminExample();
        //添加查询的条件 a_name，传入参数name,主要功能是完成a_name = 'admin' 的拼接
        adminExample.createCriteria().andANameEqualTo(name);
        //通过数据访问层的方法查询用户是否存在
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        //判断集合中是否存在数据
        if(adminList.size() > 0){
            //查询到了用户数据
            //假设用户名是唯一的，我们查到的第一条数据就是我们要的用户名
            Admin admin = adminList.get(0);
            //查询到后，我们可以进行密码的比较
            //如果后台有加密的话，我们需要对传入的密码进行md5加密后与数据库中的内容相比较
            if(admin.getaPass().equals(pwd)){
                return admin;
            }
        }
        return null;
    }
}
