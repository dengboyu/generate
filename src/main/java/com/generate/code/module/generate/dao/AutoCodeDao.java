package com.generate.code.module.generate.dao;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by by@Deng on 2017/7/5.
 */
@Repository
public interface AutoCodeDao {

    /**
     * 获取所有数据库名称
     * @return
     */
    List<String> getDatabaseName();

    /**
     * 获取数据库对应的表名称
     * @param dataBaseName
     * @return
     */
    List<String> getTableNamesByDBName(String dataBaseName);

    /**
     * 根据数据库名和表明查询表各个字段
     * @param paramMap
     * @return
     */
    List<Map<String,Object>> getTableColumnsByTableName(Map<String, String> paramMap);
}
