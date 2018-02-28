package com.generate.code.module.generate.serviceImpl;

import com.generate.code.module.generate.entity.TableCodeEntity;
import com.generate.code.module.generate.entity.TableNamesEntity;
import com.generate.code.utils.ConstUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 生成mapper方法
 *
 * @author by@Deng
 * @create 2017-10-17 22:31
 */
public class MapperInfo {

    private String daoAllName;  //dao层的全限定名
    private String entityAllName;   //entity全限定名
    private TableNamesEntity tableNamesEntity;

    public MapperInfo(TableNamesEntity tableNamesEntity, Map<String,String> map) {
        this.daoAllName = map.get("daoAllName");
        this.entityAllName = map.get("entityAllName");
        this.tableNamesEntity = tableNamesEntity;
    }


    /**
     * 获取表里的字段
     * @author by@Deng
     * @date 2017/10/20 下午4:52
     */
    public String getColumn(){
        StringBuffer sb = new StringBuffer();

        for(TableCodeEntity tableCodeEntity :tableNamesEntity.getTableCodeEntityList()){
            sb.append("RA."+tableCodeEntity.getColumnName()+",");
        }
        return StringUtils.substring(sb.toString(),0,sb.toString().length()-1);
    }

    /**
     * 获取resultMap字段
     * @author by@Deng
     * @date 2017/10/17 下午10:52
     */
    public String getResMapStr() {
        StringBuffer sb = new StringBuffer();
        //循环列字段
        int columns = tableNamesEntity.getTableCodeEntityList().size()-1;
        for(int i=0;i<=columns;i++){
            TableCodeEntity tableCodeEntity =tableNamesEntity.getTableCodeEntityList().get(i);
            String blank =ConstUtils.RT_1;
            if(i==columns){
                blank ="";
            }

            if(StringUtils.equalsIgnoreCase(tableCodeEntity.getColumnName(),"id")){
                sb.append("<id  property=\"" + tableCodeEntity.getEntityName() + "\"  column=\"" + tableCodeEntity.getColumnName() + "\"/>"+ConstUtils.RT_1);
            }else{
                sb.append(ConstUtils.BLANK_8+"<result  property=\"" + tableCodeEntity.getEntityName() + "\"  column=\"" + tableCodeEntity.getColumnName() + "\"/>"+blank);
            }
        }
        return sb.toString();
    }


    /**
     * 添加一条记录
     * @author by@Deng
     * @date 2017/10/17 下午11:08
     */
    public String getInsertColsStr() {
        StringBuffer sb = new StringBuffer();   //最终代码
        StringBuffer colBuf = new StringBuffer();
        StringBuffer valBuf = new StringBuffer();

        sb.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">"+ConstUtils.RT_1);
        for(TableCodeEntity tableCodeEntity : tableNamesEntity.getTableCodeEntityList()){
            //createTime和updateTime由数据库自动更新
            if(!(tableCodeEntity.getColumnName().equals("createTime") || tableCodeEntity.getColumnName().equals("updateTime"))){
                colBuf.append(ConstUtils.BLANK_4+ConstUtils.BLANK_8+tableCodeEntity.getColumnName()+","+ConstUtils.RT_1);
                valBuf.append(ConstUtils.BLANK_4+ConstUtils.BLANK_8+"#{"+tableCodeEntity.getEntityName()+"},"+ConstUtils.RT_1);
            }
        }
        sb.append(StringUtils.substring(colBuf.toString(),0,colBuf.toString().lastIndexOf(ConstUtils.RT_1)));
        sb.append(ConstUtils.RT_1+ConstUtils.BLANK_8+"</trim>"+ConstUtils.RT_1);
        sb.append(ConstUtils.BLANK_8+"<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">"+ConstUtils.RT_1);
        sb.append(StringUtils.substring(valBuf.toString(),0,valBuf.toString().lastIndexOf(ConstUtils.RT_1)));
        sb.append(ConstUtils.RT_1+ConstUtils.BLANK_8+"</trim>");
        return sb.toString();
    }


    /**
     * 批量添加
     * @author by@Deng
     * @date 2017/10/17 下午11:08
     */
    public String getInsertColsStrByBatch() {
        StringBuffer sb = new StringBuffer();   //最终代码
        StringBuffer colBuf = new StringBuffer();
        StringBuffer valBuf = new StringBuffer();

        sb.append("<trim prefix=\"(\" suffix=\") VALUES\" suffixOverrides=\",\">"+ConstUtils.RT_1);
        valBuf.append("<foreach collection=\"list\" index=\"index\" item=\"item\" separator=\",\">"+ConstUtils.RT_1);
        valBuf.append(ConstUtils.BLANK_8+"("+ConstUtils.RT_1);
        for(TableCodeEntity tableCodeEntity : tableNamesEntity.getTableCodeEntityList()){
            //createTime和updateTime由数据库自动更新
            if(!(tableCodeEntity.getColumnName().equals("createTime") || tableCodeEntity.getColumnName().equals("updateTime"))){
                colBuf.append(ConstUtils.BLANK_4+ConstUtils.BLANK_8+tableCodeEntity.getColumnName()+","+ConstUtils.RT_1);
                valBuf.append(ConstUtils.BLANK_4+ConstUtils.BLANK_8+"#{item."+tableCodeEntity.getEntityName()+"},"+ConstUtils.RT_1);
            }
        }

        sb.append(StringUtils.substring(colBuf.toString(),0,colBuf.toString().lastIndexOf(ConstUtils.RT_1)));
        sb.append(ConstUtils.RT_1+ConstUtils.BLANK_8+"</trim>"+ConstUtils.RT_1+ConstUtils.BLANK_8);

        sb.append(StringUtils.substring(valBuf.toString(),0,valBuf.toString().lastIndexOf(ConstUtils.RT_1)-1));
        sb.append(ConstUtils.RT_1+ConstUtils.BLANK_8+")"+ConstUtils.RT_1+ConstUtils.BLANK_8+"</foreach>");
        return sb.toString();
    }


    /**
     * 更新一条记录
     * @author by@Deng
     * @date 2017/10/20 下午10:17
     */
    public String getUpdateEntity(){
        StringBuffer sb = new StringBuffer();
        StringBuffer colBuf = new StringBuffer();
        StringBuffer updateBuf = new StringBuffer();

        sb.append("<trim prefix=\"set\" suffixOverrides=\",\">"+ConstUtils.RT_1);
        for(TableCodeEntity tableCodeEntity:tableNamesEntity.getTableCodeEntityList()){
            //createTime和updateTime由数据库自动更新
            if(!(tableCodeEntity.getColumnName().equals("createTime") || tableCodeEntity.getColumnName().equals("updateTime"))){
                //非主键
                if(StringUtils.equals(tableCodeEntity.getPrimaryKey(),"0")){
                    colBuf.append(ConstUtils.BLANK_8+ConstUtils.BLANK_4+"<if test=\""+tableCodeEntity.getEntityName()+" != null\">"+ConstUtils.RT_1);
                    colBuf.append(ConstUtils.BLANK_8+ConstUtils.BLANK_8+tableCodeEntity.getColumnName()+" = #{" + tableCodeEntity.getEntityName()+"},"+ConstUtils.RT_1);
                    colBuf.append(ConstUtils.BLANK_8+ConstUtils.BLANK_4+"</if>"+ConstUtils.RT_1);
                }else{
                    updateBuf.append(tableCodeEntity.getColumnName()+" = #{" +tableCodeEntity.getEntityName()+"}");
                }
            }
        }

        sb.append(StringUtils.substring(colBuf.toString(),0,colBuf.toString().lastIndexOf(ConstUtils.RT_1)));
        sb.append(ConstUtils.RT_1+ConstUtils.BLANK_8+"</trim>"+ConstUtils.RT_1);
        sb.append(ConstUtils.BLANK_8+"WHERE "+updateBuf);
        return sb.toString();
    }


    /**
     * 根据列条件查询实体类信息
     * @author by@Deng
     * @date 2017/10/21 上午12:41
     */
    public String getWhereColumns(){
        StringBuffer sb = new StringBuffer();
        StringBuffer colBuf = new StringBuffer();

        for(int i=0;i<tableNamesEntity.getTableCodeEntityList().size();i++){
            TableCodeEntity tableCodeEntity = tableNamesEntity.getTableCodeEntityList().get(i);
            String blank = ConstUtils.RT_1;
            if(i==tableNamesEntity.getTableCodeEntityList().size()-1){
                blank="";
            }

            colBuf.append(ConstUtils.BLANK_8+"<if test=\""+tableCodeEntity.getEntityName()+" != null\">"+ConstUtils.RT_1);
            colBuf.append(ConstUtils.BLANK_8+ConstUtils.BLANK_4+"AND RA."+tableCodeEntity.getColumnName()+" = #{" + tableCodeEntity.getEntityName()+"}"+ConstUtils.RT_1);
            colBuf.append(ConstUtils.BLANK_8+"</if>"+blank);

        }
        sb.append(colBuf.toString());
        return sb.toString();
    }








    public String getDaoAllName() {
        return daoAllName;
    }

    public void setDaoAllName(String daoAllName) {
        this.daoAllName = daoAllName;
    }

    public String getEntityAllName() {
        return entityAllName;
    }

    public void setEntityAllName(String entityAllName) {
        this.entityAllName = entityAllName;
    }

    public TableNamesEntity getTableNamesEntity() {
        return tableNamesEntity;
    }

    public void setTableNamesEntity(TableNamesEntity tableNamesEntity) {
        this.tableNamesEntity = tableNamesEntity;
    }

    @Override
    public String toString() {
        return "MapperInfo{" +
                "daoAllName='" + daoAllName + '\'' +
                ", entityAllName='" + entityAllName + '\'' +
                ", tableNamesEntity=" + tableNamesEntity +
                '}';
    }
}
