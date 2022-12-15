package cn.edu.uzz.controller;

import cn.edu.uzz.pojo.ProductInfo;
import cn.edu.uzz.service.ProductInfoService;
import cn.edu.uzz.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Classname ProductInfoAction
 * @Description TODO
 * @Date 2022/11/25 14:20
 * @Created by Administrator
 */
@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    private String saveFileName = "";
    //静态变量，每页五条数据
    private static final int PAGE_SIZE = 5;
    //在所有的控制层中，一定要有业务逻辑层对象
    @Autowired
    private ProductInfoService infoService;

    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        //获取所有的商品信息
        List<ProductInfo> infoList = infoService.getAll();
        //把商品信息通过request放入list中
        request.setAttribute("list",infoList);
        return "product";//转到product.jsp页面中
    }

    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info = infoService.splitPage(1, PAGE_SIZE);
        request.setAttribute("info",info);
        return "product";
    }

    /**
     * ajax分页处理，不需要返回值
     *
     * @param page    获取的第几页的数据，通过ajax传递的参数
     * @param session 会话
     */
    @ResponseBody//做分页是ajax请求，需要绕过ViewResolver
    @RequestMapping("/ajaxsplit")
    public void ajaxSplit(int page, HttpSession session){
        //获取到当前page页参数的页面数据，为PageInfo对象
        PageInfo info = infoService.splitPage(page, PAGE_SIZE);
        //处理完的数据会回到ajax的success中，然后读取info.list中的数据，
        //可以用session读取到指定页面的所有数据
        session.setAttribute("info",info);
    }

    /**
     * ajax上传图片并在div中回显
     *
     * @param pimage  pimage 专门进行当前上传文件流对象的接收，和input标签中的name属性一样，如果不一样，图片回传不了
     * @param request 请求
     * @return {@code Object} 返回json对象
     */
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        //获取长传后，UUID加密后的文件名字
        //getOriginalFilename,获取图片的原始名字
        saveFileName = FileNameUtil.getUUIDFileName()
                + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //获取项目中图片存放的路i纪念馆
        String path = request.getServletContext().getRealPath("/image_big");
        try {
            //这里捕获下异常，有可能加载不到文件
            //转存文件
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
        }
        //import org.json.JSONObject;
        JSONObject object = new JSONObject();
        //封装图片的路径
        object.put("imgurl",saveFileName);
        return object.toString();
    }


    /**
     * 新增商品
     *
     * @param info 商品信息
     * @return {@code String} 新增商品成功后，跳转到商品列表界面
     */
    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        //新增商品的时候，除了获取商品的一些信息，还要获取图片的名称，所以我们把saveFileName提升为全局变量
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        //ctrl+alt+t
        int num = -1;
        try {
            //当有多个insert数据处理的时候，可能会发生异常
            num = infoService.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (num > 0) {
            //插入数据成功
            request.setAttribute("msg","新增商品成功！！！");
        } else {
            //插入数据失败
            request.setAttribute("msg","新增商品失败！！！");
        }
        //在新增商品成功后，应该重新访问数据库，所以跳转到分页的action上
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(int pid, Model model){
        ProductInfo productInfo = infoService.getById(pid);
        //将查询到的数据封装到model中，在update.jsp中显示model对象中的内容
        model.addAttribute("prod",productInfo);
        return "update";//
    }

    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        //在ajax的异步上传时候，如果有上传过图片，saveFileName变量中包含上传的图片名称
        //如果没有上传saveFileName为空
        if (!saveFileName.equals("")){
            //有过异步上传
            info.setpImage(saveFileName);
        }
        //如果没有异步上传 ，savaFileName为空
        //可以使用info的隐藏表单域提供pImage的原始图片的名称
        int num = -1;
        try {
            num = infoService.updateProduct(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num>0){
            request.setAttribute("msg","更新成功");
        } else {
            request.setAttribute("msg","更新失败");
        }
        //处理完更新后，saveFileName中可能存在数据，有可能会影响下一次更新或者新增商品的saveFileName变量
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/delete")
    public String deleteProduct(int pid,HttpServletRequest request){

        int num = -1;
        try {
            num = infoService.deleteProduct(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (num>0){
            request.setAttribute("msg","删除成功");
        }else {
            request.setAttribute("msg","删除失败");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=utf-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        //获取第一页的数据
        PageInfo pageInfo = infoService.splitPage(1,PAGE_SIZE);
        //吧pageInfo放在请求域中
        request.getSession().setAttribute("info",pageInfo);
        //获取是否成功删除的msg，然后返回给客户端
        return request.getAttribute("msg");
    }


    //pids:1,2,3,4...
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request){
        //将得到的ids通过split拆分
        String[] ps = pids.split(",");
        try {
            int i = infoService.deleteBatch(ps);

            if (i>0){
                request.setAttribute("msg","批量删除成功");
            }else {
                request.setAttribute("msg","批量删除失败");
            }
        } catch (Exception e) {
            //如果商品在订单中提示不可删除
            request.setAttribute("msg","不可删除");
            e.printStackTrace();
        }

        return "forward:/prod/deleteAjaxSplit.action";
    }
}