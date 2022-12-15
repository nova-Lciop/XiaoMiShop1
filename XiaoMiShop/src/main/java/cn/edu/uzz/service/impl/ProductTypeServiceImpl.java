package cn.edu.uzz.service.impl;

import cn.edu.uzz.mapper.ProductTypeMapper;
import cn.edu.uzz.pojo.ProductType;
import cn.edu.uzz.pojo.ProductTypeExample;
import cn.edu.uzz.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname ProductTypeServiceImpl
 * @Description TODO
 * @Date 2022/11/28 15:27
 * @Created by Administrator
 */
@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeMapper typeMapper;

    /**
     * 获取到数据框中所有的商品的类型
     *
     * @return {@code List<ProductType>}
     */
    @Override
    public List<ProductType> getAll() {
        return typeMapper.selectByExample(new ProductTypeExample());
    }
}
