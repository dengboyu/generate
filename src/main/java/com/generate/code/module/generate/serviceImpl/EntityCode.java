package com.generate.code.module.generate.serviceImpl;

import com.generate.code.module.generate.entity.CommonEntity;
import com.generate.code.module.generate.entity.TableCodeEntity;
import com.generate.code.utils.ConstUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * 生成entity对应方法
 *
 * @author by@Deng
 * @create 2017-10-20 09:23
 */
public class EntityCode extends CommonEntity {
    
    
    /**
     * 返回entity.ftl模板主体对象
     * @author by@Deng
     * @date 2017/10/20 下午1:57
     */
    public String getEntityStr(){

        StringBuffer sb = new StringBuffer();   //最终代码
        StringBuffer proBuffer = new StringBuffer();   //设置属性
        StringBuffer setBuffer = new StringBuffer();    //属性set/get方法
        StringBuffer str = new StringBuffer();    //toString方法

        //循环列字段
        for (TableCodeEntity column : tableNamesEntity.getTableCodeEntityList()) {
            //属性
            String annotation = StringUtils.isEmpty(column.getAnnotation()) ? "" : "//";    //判断注释有无，没有不显示注释符号

            //判断是否是java.util.Date
            if(StringUtils.equals(column.getJavaType(),"java.util.Date")){
                column.setJavaType("Date");
            }
            proBuffer.append(ConstUtils.BLANK_4 + "private" + ConstUtils.BLANK_1 + column.getJavaType() + ConstUtils.BLANK_1
                    + column.getEntityName() + ";" + ConstUtils.BLANK_4 + annotation + column.getAnnotation() + ConstUtils.RT_1);    //字段属性

            //属性的set/get方法
            setBuffer.append(ConstUtils.BLANK_4 + "/**  " + ConstUtils.RT_1 + ConstUtils.BLANK_5 + "* 设置:" + column.getEntityName() + ConstUtils.RT_1 + ConstUtils.BLANK_5 + "*/" + ConstUtils.RT_1);
            setBuffer.append(ConstUtils.BLANK_4 + "public void set" + StringUtils.capitalize(column.getEntityName()) + "(" + column.getJavaType() + " "
                    + column.getEntityName() + ") {" + ConstUtils.RT_1);
            setBuffer.append(ConstUtils.BLANK_8 + "this." + column.getEntityName() + " = " + column.getEntityName() + ";" + ConstUtils.RT_1);
            setBuffer.append(ConstUtils.BLANK_4 + "}" + ConstUtils.RT_1 + ConstUtils.RT_1);
            setBuffer.append(ConstUtils.BLANK_4 + "/** " + ConstUtils.RT_1 + ConstUtils.BLANK_5 + "* 获取:" + column.getEntityName() + ConstUtils.RT_1 + ConstUtils.BLANK_5 + "*/" + ConstUtils.RT_1);
            setBuffer.append(ConstUtils.BLANK_4 + "public " + column.getJavaType() + " get" + StringUtils.capitalize(column.getEntityName()) + "() {" + ConstUtils.RT_1);
            setBuffer.append(ConstUtils.BLANK_8 + "return this." + column.getEntityName() + ";" + ConstUtils.RT_1);
            setBuffer.append(ConstUtils.BLANK_4 + "}" + ConstUtils.RT_1 + ConstUtils.RT_1);

            //toString方法
            if (StringUtils.equals(column.getJavaType(), "String")) {
                str.append(ConstUtils.BLANK_8 + ConstUtils.BLANK_8 + "\", " + column.getEntityName() + "='\" + " + column.getEntityName() + " + '\\'' +" + ConstUtils.RT_1);
            } else if (StringUtils.equals(column.getJavaType(), "byte[]")) {
                str.append(ConstUtils.BLANK_8 + ConstUtils.BLANK_8 + "\", " + column.getEntityName() + "=\" + Arrays.toString(" + column.getEntityName() + ") +" + ConstUtils.RT_1);
            } else {
                str.append(ConstUtils.BLANK_8 + ConstUtils.BLANK_8 + "\", " + column.getEntityName() + "=\" + " + column.getEntityName() + " +" + ConstUtils.RT_1);
            }
        }
        //设置属性
        sb.append(proBuffer.toString() + ConstUtils.RT_1 + ConstUtils.RT_1);

        //属性se/get方法
        sb.append(setBuffer.toString());

        //toString方法
        sb.append(ConstUtils.BLANK_4 + "@Override" + ConstUtils.RT_1);
        sb.append(ConstUtils.BLANK_4 + "public String toString() {" + ConstUtils.RT_1);
        sb.append(ConstUtils.BLANK_8 + "return \"" + fileName + "{\" +" + ConstUtils.RT_1);
        sb.append(StringUtils.removeFirst(str.toString(), ", "));  //列字段
        sb.append(ConstUtils.BLANK_8 + ConstUtils.BLANK_8 + "'}';" + ConstUtils.RT_1 + ConstUtils.BLANK_4 + "}");

        return sb.length() > 0 ? sb.toString() : null;
    }
}
