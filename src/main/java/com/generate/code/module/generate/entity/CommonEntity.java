package com.generate.code.module.generate.entity;

/**
 * 公共字段
 *
 * @author by@Deng
 * @create 2017-10-20 09:20
 */
public class CommonEntity {

    public String packageAndImport; //包名和导包
    public String author;   //作者
    public String tableName;    //表名
    public String datetime; //生成文档时间
    public String fileName; //文件名
    public String reqController;    //controller的访问
    public String entityName;   //entity实体类的文件名
    public String daoName;  //daoName
    public String serviceName;  //service的自动注入字段
    public TableNamesEntity tableNamesEntity;


    public String getPackageAndImport() {
        return packageAndImport;
    }

    public void setPackageAndImport(String packageAndImport) {
        this.packageAndImport = packageAndImport;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getReqController() {
        return reqController;
    }

    public void setReqController(String reqController) {
        this.reqController = reqController;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getDaoName() {
        return daoName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public TableNamesEntity getTableNamesEntity() {
        return tableNamesEntity;
    }

    public void setTableNamesEntity(TableNamesEntity tableNamesEntity) {
        this.tableNamesEntity = tableNamesEntity;
    }

    @Override
    public String toString() {
        return "CommonEntity{" +
                "packageAndImport='" + packageAndImport + '\'' +
                ", author='" + author + '\'' +
                ", tableName='" + tableName + '\'' +
                ", datetime='" + datetime + '\'' +
                ", fileName='" + fileName + '\'' +
                ", reqController='" + reqController + '\'' +
                ", entityName='" + entityName + '\'' +
                ", daoName='" + daoName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", tableNamesEntity=" + tableNamesEntity +
                '}';
    }
}
