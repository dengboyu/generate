package com.generate.code.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 时间工具类
 *
 * @author by@Deng
 * @create 2017-09-30 13:53
 */
public class DateUtil {

    private  DateUtil(){}


    /**
     * 获取现在时间
     * pattern为返回需要的格式
     * @author by@Deng
     * @date 2017/9/30 下午1:54
     */
    public static final String getNowTime(String pattern){
        return DateFormatUtils.format(new Date(),pattern);
    }

}
