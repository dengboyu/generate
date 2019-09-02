package com.generate.code.utils;

/**
 * Created by by@Deng on 2017/7/15.
 */
public class ConstUtils {

    //包名
    public static final String rootPath = "src.main.java";
    public static final String mapperName="src.main.resources.mapper";  //mapper路径
//    public static final String packageBase="com.bypro.future";  //包名

    /*****************************国智******************************/
    public static final String packageBase="com.gz.evalution";  //包名
//    public static final String packageBase="com.tt.association";  //包名
//    public static final String packageBase="com.leng.sguide";  //包名
//    public static final String packageBase="com.xsh.activity";  //包名

    public static final String packageName= packageBase + ".module";  //以上两个为类全限定名
    public static final String packageCommonDao= packageBase + ".common.structure.dao.BaseDao";    //公共接口dao
    public static final String packageCommonService=packageBase +".common.structure.service.BaseService";    //公共接口service
    public static final String packageCommonServiceAbstract = packageBase+".common.structure.serviceAbstract.BaseServiceAbstract";  //公共抽象类实现接口



    //换行、空格等
    public static final String RT_1 = "\r\n";
    public static final String BLANK_1 =" ";
    public static final String BLANK_4 ="    ";
    public static final String BLANK_5 =BLANK_4+BLANK_1;
    public static final String BLANK_8 =BLANK_4 + BLANK_4;



}
