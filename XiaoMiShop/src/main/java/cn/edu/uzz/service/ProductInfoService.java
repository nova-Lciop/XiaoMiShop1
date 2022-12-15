package cn.edu.uzz.service;

import cn.edu.uzz.pojo.ProductInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 商品信息业务层
 *
 * @author JokerQ
 * @Classname ProductInfoService
 * @Description TODO
 * @Date 2022/11/25 14:08
 * @Created by Administrator
 */
public interface ProductInfoService {
    /**
     * 获取所有商品
     * （目前是不分页显示的）
     * @return {@code List<ProductInfo>}
     */
    List<ProductInfo> getAll();

    /**
     * 分页
     *
     * @param pageNum  当前页
     * @param pageSize 每页取几条数据
     * @return {@code PageInfo}
     */
    PageInfo splitPage(int pageNum, int pageSize);

    /**
     * 插入商品数据
     *
     * @param info 商品信息
     * @return int 受影响的行数
     */
    int save(ProductInfo info);

    /**
     * 通过id查询商品的信息
     *
     * @param pid pid 商品id
     * @return {@code ProductInfo} 商品信息
     */
    ProductInfo getById(int pid);

    /**
     * 更新产品
     *
     * @param info 产品信息
     * @return int 受影响的行
     */
    int updateProduct(ProductInfo info);


    //删除单个产品
    int deleteProduct(int pid);

    int deleteBatch(String[] ids);
}
