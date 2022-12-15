package cn.edu.uzz.test;

import cn.edu.uzz.utils.MD5Util;
import org.junit.Test;

/**
 * @Classname MyTest
 * @Description TODO
 * @Date 2022/11/23 10:22
 * @Created by Administrator
 */
public class MyTest {
    //junit
    @Test
    public void testMD5(){
        //对字符串000000进行加密
        String s = MD5Util.getMD5("000000");
        //输出加密后的字符串
        System.out.println(s);
        //670b14728ad9902aecba32e22fa4f6bd
        //c984aed014aec7623a54f0591da07a85fd4b762d
    }
}
