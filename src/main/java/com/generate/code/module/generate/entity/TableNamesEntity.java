package com.generate.code.module.generate.entity;

import java.util.List;

/**
 * 封装前台传值实体类
 *
 * @author by@Deng
 * @create 2017-08-01 23:08
 */
public class TableNamesEntity {

    private String dataBaseName;    //数据库名称
    private String tableName;   //表名
    private String author;  //作者
    private String moduleName;  //模块名
    private String className;   //类名
    private String codePath;    //生成代码路径
    private List<TableCodeEntity> tableCodeEntityList;  //表属性

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }



    public List<TableCodeEntity> getTableCodeEntityList() {
        return tableCodeEntityList;
    }

    public void setTableCodeEntityList(List<TableCodeEntity> tableCodeEntityList) {
        this.tableCodeEntityList = tableCodeEntityList;
    }


    @Override
    public String toString() {
        return "TableNamesEntity{" +
                "dataBaseName='" + dataBaseName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", author='" + author + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", className='" + className + '\'' +
                ", codePath='" + codePath + '\'' +
                ", tableCodeEntityList=" + tableCodeEntityList +
                '}';
    }

}
