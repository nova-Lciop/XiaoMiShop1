package cn.edu.uzz.service;

import cn.edu.uzz.pojo.ProductType;

import java.util.List;

/**
 * 产品类型业务层
 *
 * @author JokerQ
 * @Classname ProductTypeService
 * @Description TODO
 * @Date 2022/11/28 15:22
 * @Created by Administrator
 */
public interface ProductTypeService {

    /**
     * 获取到数据框中所有的商品的类型
     *
     * @return {@code List<ProductType>}
     */
    List<ProductType> getAll();
}
