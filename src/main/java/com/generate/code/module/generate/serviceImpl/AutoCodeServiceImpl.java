package com.generate.code.module.generate.serviceImpl;


import com.generate.code.exception.ByException;
import com.generate.code.module.generate.entity.TableCodeEntity;
import com.generate.code.module.generate.entity.TableNamesEntity;
import com.generate.code.module.generate.dao.AutoCodeDao;
import com.generate.code.module.generate.service.AutoCodeService;
import com.generate.code.utils.ConstUtils;
import com.generate.code.utils.FileUtils;
import freemarker.template.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

/**
 * Created by by@Deng on 2017/7/5.
 */
@Service
public class AutoCodeServiceImpl implements AutoCodeService{

    @Autowired
    private AutoCodeDao autoCodeDao;

    private Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);  //configuration对象
    private String domain = StringUtils.substring(ConstUtils.packageName, 0, ConstUtils.packageName.indexOf(".")); //域名


    /**
     * 获取所有数据库名称
     *
     * @return
     */
    @Override
    public List<String> getDatabaseName() {
        List<String> list = autoCodeDao.getDatabaseName();
        //把系统自带数据库删除
        list.remove("information_schema");
        list.remove("mysql");
        list.remove("performance_schema");
        list.remove("sys");

        return list;
    }

    /**
     * 获取数据库对应的表名称
     *
     * @return
     */
    @Override
    public List<String> getTablesName(String dataBaseName) {
        return autoCodeDao.getTableNamesByDBName(dataBaseName);
    }


    /**
     * 根据数据库名和表名查询表各个字段
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<Map<String, Object>> getTableColumnsByTableName(Map<String, String> paramMap) {
        List<Map<String, Object>> list = autoCodeDao.getTableColumnsByTableName(paramMap);
        for (Map<String, Object> map : list) {

            //将每列字段对应成实体类
            String[] columnName = map.get("columnName").toString().split("_");
            if (columnName.length > 1) {
                String entityName = columnName[0];
                String[] column = ArrayUtils.remove(columnName, 0);
                for (String str : column) {
                    str = str.substring(0, 1).toUpperCase() + str.substring(1);    //把首字母变大写
                    entityName += str;
                    map.put("entityName", entityName);
                }
            } else {
                map.put("entityName", columnName[0]);
            }


            String javaType = null;     //mysql对应java类型
            String mybatisType = map.get("mybatisType").toString().toUpperCase();
            //将字段对应转型为java类型
            switch (mybatisType) {
                case "BIT":
                    javaType = "Boolean";
                    break;
                case "TINYINT":
                    javaType = "Integer";
                    break;
                case "SMALLINT":
                    javaType = "Integer";
                    break;
                case "MEDIUMINT":
                    javaType = "Integer";
                    break;
                case "INT":
                    javaType = "Integer";
                    break;
                case "BIGINT":
                    javaType = "Long";
                    break;
                case "FLOAT":
                    javaType = "Float";
                    break;
                case "DOUBLE":
                    javaType = "Double";
                    break;
                case "DECIMAL":
                    javaType = "BigDecimal";
                    break;
                case "DATE":
                    javaType = "java.sql.Date";
                    break;
                case "DATETIME":
                    javaType = "java.util.Date";
                    break;
                case "TIMESTAMP":
                    javaType = "Timestamp";
                    break;
                case "TIME":
                    javaType = "Time";
                    break;
                case "CHAR":
                    javaType = "String";
                    break;
                case "VARCHAR":
                    javaType = "String";
                    break;
                case "MEDIUMTEXT":
                    javaType = "String";
                    break;
                case "TEXT":
                    javaType = "String";
                    break;
                case "LONGTEXT":
                    javaType = "String";
                    break;
                case "BINARY":
                    javaType = "byte[]";
                    break;
                case "TINYBLOB":
                    javaType = "byte[]";
                    break;
                case "MEDIUMBLOB":
                    javaType = "byte[]";
                    break;
                case "BLOB":
                    javaType = "byte[]";
                    break;
                case "LONGBLOB":
                    javaType = "byte[]";
                    break;
                case "ENUM":
                    javaType = "String";
                    break;
                case "SET":
                    javaType = "String";
                    break;
            }
            map.put("javaType", javaType);
        }

        return list;
    }


    /**
     * 生成文件
     *
     * @author by@Deng
     * @date 2017/9/29 下午9:26
     */
    @Override
    public Integer addFileByTableName(TableNamesEntity tableNamesEntity, Map<String, String> map) throws Exception {
        Date now = new Date();

        configuration.setDirectoryForTemplateLoading(new File(map.get("directory")));  //模板代码路径
        configuration.setObjectWrapper(new DefaultObjectWrapper());

        Map<String, Object> paramMap = new HashMap<>();

        String codePath = tableNamesEntity.getCodePath();   //前台传的路径
        File codeFile = new File(codePath);
        if (!codeFile.isDirectory()) throw new ByException("您填写的路径不正确");
        codePath = StringUtils.replace(codePath, "/", File.separator) + File.separator + "autoCode";

        //根路径
        StringBuffer javaPath = new StringBuffer(codePath);
        String[] rootArr = ConstUtils.rootPath.split("\\.");
        for (String str : rootArr) {
            javaPath.append(File.separator + str);
        }

        //java代码路径
        String[] packageArr = ConstUtils.packageName.split("\\.");
        for (String str : packageArr) {
            javaPath.append(File.separator + str);
        }
        javaPath.append(File.separator + tableNamesEntity.getModuleName() + File.separator);
        String javaCodePath = javaPath.toString();

        //mapper路径
        StringBuffer mapperCode = new StringBuffer(codePath);
        String[] mapperArr = ConstUtils.mapperName.split("\\.");
        for (String str : mapperArr) {
            mapperCode.append(File.separator + str);
        }
        String mapperPath = mapperCode.append(File.separator).toString();


        //entity层
        String entityPath = javaCodePath + "entity" + File.separator + tableNamesEntity.getClassName() + "Entity.java"; //entity文件路径

        Map<String,String> fileMap = getFileName(entityPath);
        String entityAllName = fileMap.get("fileAllName"); //entity全路径
        String entityName = fileMap.get("fileName");    //entity文件名

        //创建entity.ftl需要的对象实例
        EntityCode entityCode = new EntityCode();
        entityCode.setAuthor(tableNamesEntity.getAuthor()); //公共类的作者
        entityCode.setDatetime(DateFormatUtils.format(now,"yyyy-MM-dd hh:mm:ss"));  //公共类的时间
        entityCode.setTableName(tableNamesEntity.getTableName());   //公共类的表名称
        entityCode.setTableNamesEntity(tableNamesEntity);   //公共类表中的数据
        entityCode.setEntityName(entityName);   //公共类的entity的类名
        entityCode.setFileName(entityName); //entity文件名
        entityCode.setPackageAndImport(createTopCode(tableNamesEntity,fileMap));   //创建导包注释

        paramMap.put("path", entityPath);   //文件路径
        paramMap.put("structure", "entity");
        paramMap.put("entity",entityCode);
        createTemplate(paramMap);   //创建entity代码

        //dao层
        String daoPath=javaCodePath+"dao"+File.separator+tableNamesEntity.getClassName()+"Dao.java";      //dao层路径
        fileMap = getFileName(daoPath);
        fileMap.put("entityAllName",entityAllName); //entity全限定名

        String daoAllName = fileMap.get("fileAllName"); //dao全路径
        String daoName = fileMap.get("fileName");    //dao文件名

        //创建导包注释
        entityCode.setDaoName(daoName);
        entityCode.setPackageAndImport(createTopCode(tableNamesEntity,fileMap));

        paramMap.put("path", daoPath);   //文件路径
        paramMap.put("structure", "dao");
        paramMap.put("dao",entityCode);
        createTemplate(paramMap);   //创建dao代码


        //service层
        String servicePath=javaCodePath+"service"+File.separator+tableNamesEntity.getClassName()+"Service.java";      //service层路径
        fileMap = getFileName(servicePath); //service层文件名等信息
        fileMap.put("entityAllName",entityAllName); //entity全限定名

        String serviceAllName = fileMap.get("fileAllName"); //service全路径
        String serviceName = fileMap.get("fileName");    //service文件名

        //导包、注释
        entityCode.setFileName(serviceName);
        entityCode.setPackageAndImport(createTopCode(tableNamesEntity,fileMap));

        paramMap.put("path",servicePath);
        paramMap.put("structure", "service");
        paramMap.put("service",entityCode);
        createTemplate(paramMap);   //创建service代码


        //serviceImpl层
        String serviceImplPath=javaCodePath+"serviceImpl"+File.separator+tableNamesEntity.getClassName()+"ServiceImpl.java";      //serviceImpl层路径
        fileMap = getFileName(serviceImplPath); //serviceImpl层文件名等信息
        fileMap.put("entityAllName",entityAllName); //entity全限定名
        fileMap.put("serviceAllName",serviceAllName);   //service全路径
        fileMap.put("daoAllName",daoAllName);   //dao层全路径

        String serviceImplAllName = fileMap.get("fileAllName"); //serviceImpl全路径
        String serviceImplName = fileMap.get("fileName");    //serviceImpl文件名

        //导包、注释
        entityCode.setFileName(serviceName);
        entityCode.setPackageAndImport(createTopCode(tableNamesEntity,fileMap));

        paramMap.put("path",serviceImplPath);
        paramMap.put("structure", "serviceImpl");
        paramMap.put("service",entityCode);
        createTemplate(paramMap);   //创建serviceImpl代码


        //controller层
        String controllerPath=javaCodePath+"controller"+File.separator+tableNamesEntity.getClassName()+"Controller.java";   //controller层路径
        fileMap = getFileName(controllerPath); //controller层文件名等信息
        fileMap.put("serviceAllName",serviceAllName);   //controller全路径

        //导包、注释
        entityCode.setServiceName(serviceName+" "+StringUtils.uncapitalize(serviceName)+";");
        entityCode.setFileName(fileMap.get("fileName"));
        entityCode.setPackageAndImport(createTopCode(tableNamesEntity,fileMap));
        entityCode.setReqController(StringUtils.uncapitalize(fileMap.get("fileName").substring(0,fileMap.get("fileName").length()-"Controller".length())));

        paramMap.put("path",controllerPath);
        paramMap.put("structure", "controller");
        paramMap.put("controller",entityCode);
        createTemplate(paramMap);   //创建controller代码

        //mapper层
        String mappersFile = mapperPath + tableNamesEntity.getModuleName() + File.separator + tableNamesEntity.getClassName() + "Mapper.xml";    //mapper地址
        paramMap.put("path", mappersFile);   //文件路径
        paramMap.put("structure", "mapper");

        Map<String,String> mapBean = new HashMap<>();
        mapBean.put("daoAllName", daoAllName);     //mapper对应的dao全类名
        mapBean.put("entityAllName",entityAllName);
        paramMap.put("mapperInfo", new MapperInfo(tableNamesEntity, mapBean));
        createTemplate(paramMap);   //创建mapper代码

        return 1;
    }


    /**
     * 根据模板创建文件
     *
     * @author by@Deng
     * @date 2017/10/17 下午10:20
     */
    @Override
    public void createTemplate(Map<String, Object> map) throws Exception {
        Template temp = null;
        String structure = map.get("structure").toString(); //哪一层

        if (StringUtils.equals(structure, "entity")) {
            //entity模板位置
            temp = configuration.getTemplate(File.separator + "entity.ftl");
        }else if (StringUtils.equals(structure, "dao")) {
            //dao模板位置
            temp = configuration.getTemplate(File.separator + "dao.ftl");
        }else if (StringUtils.equals(structure, "service")) {
            //service接口模板位置
            temp = configuration.getTemplate(File.separator + "service.ftl");
        }else if (StringUtils.equals(structure, "serviceImpl")) {
            //serviceImpl实现类模板位置
            temp = configuration.getTemplate(File.separator + "serviceImpl.ftl");
        }else if (StringUtils.equals(structure, "controller")) {
            //controller层模板位置
            temp = configuration.getTemplate(File.separator + "controller.ftl");
        }else if (StringUtils.equals(structure, "mapper")) {
            //mapper模板位置
            temp = configuration.getTemplate(File.separator + "mapperXml.ftl");
        }

        //把数据写进模板中
        String path = map.get("path").toString();
        FileUtils.createFile(path);   //先创建文件
        Writer writer = new FileWriter(path);
        temp.process(map, writer);   //写入模板
        writer.close();
    }


    /**
     * 创建文件头部的导包、注释
     *
     * @author by@Deng
     * @date 2017/9/30 上午11:45
     */
    @Override
    public String createTopCode(TableNamesEntity tableNamesEntity, Map<String,String> map) {
        StringBuffer sb = new StringBuffer();
        String fileName = map.get("fileName");  //文件名
        String packageName = map.get("packageName");   //文件的包名

        sb.append("package " + packageName + ";" + ConstUtils.RT_1 + ConstUtils.RT_1);

        //判断是否是哪一层导对应包
        if (StringUtils.endsWith(fileName, "Entity")) {
            //导工具包
            List<TableCodeEntity> tableCodeEntities = tableNamesEntity.getTableCodeEntityList();
            Set<String> set = new HashSet<>();
            for (TableCodeEntity tableCodeEntity : tableCodeEntities) {
                String javaType = tableCodeEntity.getJavaType();
                if (StringUtils.equals(javaType, "java.util.Date") && !set.contains(javaType)) {
                    sb.append("import java.util.Date;" + ConstUtils.RT_1);
                    set.add(javaType);
                }
                if (StringUtils.equals(javaType, "Timestamp") && !set.contains(javaType)) {
                    sb.append("import java.sql.Timestamp;" + ConstUtils.RT_1);
                    set.add(javaType);
                }
                if (StringUtils.equals(javaType, "Time") && !set.contains(javaType)) {
                    sb.append("import java.sql.Time;" + ConstUtils.RT_1);
                    set.add(javaType);
                }
                if (StringUtils.equals(javaType, "BigDecimal") && !set.contains(javaType)) {
                    sb.append("import java.math.BigDecimal;" + ConstUtils.RT_1);
                    set.add(javaType);
                }
                if (StringUtils.equals(javaType, "byte[]") && !set.contains(javaType)) {
                    sb.append("import java.util.Arrays;" + ConstUtils.RT_1);
                    set.add(javaType);
                }
            }

        }else if (StringUtils.endsWith(fileName, "Dao")) {
            //导入dao层
            sb.append("import org.springframework.stereotype.Repository;" + ConstUtils.RT_1);
            sb.append("import " + ConstUtils.packageCommonDao + ";" + ConstUtils.RT_1);
            sb.append("import " + map.get("entityAllName")+ ";" + ConstUtils.RT_1);

        }else if (StringUtils.endsWith(fileName, "Service")) {
            //导入service层
            sb.append("import " + map.get("entityAllName") +";"+ ConstUtils.RT_1);
            sb.append("import "+ConstUtils.packageCommonService+";"+ ConstUtils.RT_1);
        }else if (StringUtils.endsWith(fileName, "ServiceImpl")) {
            //导入serviceImpl层
            sb.append("import org.springframework.stereotype.Service;" + ConstUtils.RT_1);
            sb.append("import org.springframework.transaction.annotation.Transactional;" + ConstUtils.RT_1);
            sb.append("import " + map.get("entityAllName")+";" + ConstUtils.RT_1);
            sb.append("import " +map.get("serviceAllName")+";"+ConstUtils.RT_1);
            sb.append("import "+map.get("daoAllName")+";"+ConstUtils.RT_1);
            sb.append("import "+ConstUtils.packageCommonServiceAbstract+";"+ ConstUtils.RT_1);
        }else if (StringUtils.endsWith(fileName, "Controller")) {
            //导入controller层
            sb.append("import "+ConstUtils.packageBase+".common.annotation.Result;" + ConstUtils.RT_1);
            sb.append("import org.springframework.web.bind.annotation.*;" + ConstUtils.RT_1);
            sb.append("import javax.annotation.Resource;"+ConstUtils.RT_1);
            sb.append("import "+map.get("serviceAllName")+";"+ConstUtils.RT_1);
        }

        return sb.length() > 0 ? sb.toString() : null;
    }


    /**
     * 根据路径获取文件名和包名
     *
     * @author by@Deng
     * @date 2017/10/17 下午7:56
     */
    @Override
    public Map<String,String> getFileName(String path) {
        Map<String,String> map = new HashMap<>();

        //文件不带后缀名
        String fileName = StringUtils.substring(path, StringUtils.lastIndexOf(path, File.separator) + 1);
        fileName = StringUtils.split(fileName, ".")[0];

        //fileAllName实体类全限定名
        String fileAllName = StringUtils.replace(StringUtils.substring(path, StringUtils.indexOf(path, domain),StringUtils.lastIndexOf(path,".")), File.separator, ".");

        //包名
        String packageName = StringUtils.substring(path, StringUtils.indexOf(path, domain), StringUtils.lastIndexOf(path, File.separator));
        packageName = StringUtils.replaceAll(packageName,File.separator,".");

        map.put("fileName",fileName);   //文件名不带后缀
        map.put("fileAllName",fileAllName); //全限定文件名
        map.put("packageName",packageName); //包名

        return map;
    }


}
