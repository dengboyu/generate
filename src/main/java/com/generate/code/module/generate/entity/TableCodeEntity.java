package com.generate.code.module.generate.entity;

/**
 * 表格实体类
 *
 * @author by@Deng
 * @create 2017-08-01 23:12
 */
public class TableCodeEntity {

    private String entityName;//实体类属性
    private String columnName;//字段名
    private String annotation;//备注
    private String mybatisType;//mybatis类型
    private String javaType;//java类型
    private String primaryKey;//是否主键

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getMybatisType() {
        return mybatisType;
    }

    public void setMybatisType(String mybatisType) {
        this.mybatisType = mybatisType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public String toString() {
        return "TableCodeEntity{" +
                "entityName='" + entityName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", annotation='" + annotation + '\'' +
                ", mybatisType='" + mybatisType + '\'' +
                ", javaType='" + javaType + '\'' +
                ", primaryKey='" + primaryKey + '\'' +
                '}';
    }
}
