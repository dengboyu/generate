package com.generate.code.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 创建文件夹、文件
 *
 * @author by@Deng
 * @create 2017-09-30 16:57
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 判断文件夹是否存在
     * @author by@Deng
     * @date 2017/9/30 下午5:14
     */
    public static Boolean fileExist(String pathFile){
        File filePath = new File(pathFile);
        return filePath.exists();
    }

    /**
     * 创建文件夹
     * @author by@Deng
     * @date 2017/9/30 下午4:57
     */
    public static boolean createDirectory(String filePath) {
        //判断是否以系统后缀符结束
        if (!filePath.endsWith(File.separator)) filePath = filePath + File.separator;

        File descDir = new File(filePath);
        if(descDir.exists()) return false;

        // 创建目录
        if(descDir.mkdirs()) return true;

        return false;
    }


    /**
     * 判断系统
     * @author by@Deng
     * @date 2017/9/30 下午9:48
     */
    public static String checkSystem(){
        String stem=System.getProperty("os.name");
        if(stem.contains("Mac")){
            return "Mac";
        }else if(stem.contains("Windows")){
            return "Windows";
        }
        return null;
    }


    /**
     * 创建单个文件
     * @param descFileName 文件名，包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createFile(String descFileName) {
        File file = new File(descFileName);
        if (file.exists()){
            log.debug("文件 " + descFileName + " 已存在!");
            return false;
        }
        if (descFileName.endsWith(File.separator)) {
            log.debug(descFileName + " 为目录，不能创建目录!");
            return false;
        }
        if (!file.getParentFile().exists()) {
            // 如果文件所在的目录不存在，则创建目录
            if (!file.getParentFile().mkdirs()) {
                log.debug("创建文件所在的目录失败!");
                return false;
            }
        }

        // 创建文件
        try {
            if (file.createNewFile()) {
                log.debug(descFileName + " 文件创建成功!");
                return true;
            } else {
                log.debug(descFileName + " 文件创建失败!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(descFileName + " 文件创建失败!");
            return false;
        }
    }

    /**
     * 写入文件
     * @param fileName 要写入的文件
     */
    public static void writeToFile(String fileName, String content, boolean append) {
        try {
            FileUtils.write(new File(fileName), content, "utf-8", append);
            log.debug("文件 " + fileName + " 写入成功!");
        } catch (IOException e) {
            log.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

}
