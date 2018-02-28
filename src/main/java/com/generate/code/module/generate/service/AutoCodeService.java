package com.generate.code.module.generate.service;

import com.generate.code.module.generate.entity.TableNamesEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author by@Deng
 * @create 2017-10-23 01:43
 */
@Service
public interface AutoCodeService {

    /**
     * 获取所有数据库名称
     *
     * @return
     */
    List<String> getDatabaseName();


    /**
     * 获取数据库对应的表名称
     *
     * @return
     */
    List<String> getTablesName(String dataBaseName);


    /**
     * 根据数据库名和表明查询表各个字段
     *
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getTableColumnsByTableName(Map<String, String> paramMap);


    /**
     * 生成文件
     *
     * @author by@Deng
     * @date 2017/9/29 下午9:26
     */
    Integer addFileByTableName(TableNamesEntity tableNamesEntity, Map<String, String> map) throws Exception;


    /**
     * 根据模板创建文件
     *
     * @author by@Deng
     * @date 2017/10/17 下午10:20
     */
    void createTemplate(Map<String, Object> map) throws Exception;


    /**
     * 创建文件头部的导包、注释
     *
     * @author by@Deng
     * @date 2017/9/30 上午11:45
     */
    String createTopCode(TableNamesEntity tableNamesEntity, Map<String,String> map);


    /**
     * 根据路径获取文件名和包名
     *
     * @author by@Deng
     * @date 2017/10/17 下午7:56
     */
    Map<String,String> getFileName(String path);
}
