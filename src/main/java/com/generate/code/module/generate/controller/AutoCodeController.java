package com.generate.code.module.generate.controller;


import com.generate.code.exception.ByException;
import com.generate.code.module.generate.service.AutoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.generate.code.module.generate.entity.TableNamesEntity;
import com.generate.code.module.generate.serviceImpl.AutoCodeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by by@Deng on 2017/7/5.
 */
@Controller
public class AutoCodeController {

    @Resource(name="autoCodeServiceImpl")
//    @Resource
    private AutoCodeService autoCodeService;

    /**
     * 获取数据库名称
     */
    @RequestMapping(value="/autoCode/getDatabaseName",method = RequestMethod.GET)
    @ResponseBody
    public List<String> getDatabaseName(){
        return autoCodeService.getDatabaseName();
    }


    /**
     * 获取数据库对应的表名称
     * @return
     */
    @RequestMapping(value = "/autoCode/getTablesName",method = RequestMethod.GET)
    @ResponseBody
    public List<String> getTablesName(@RequestParam String dataBaseName){
        if(StringUtils.isNotEmpty(dataBaseName)){
            return autoCodeService.getTablesName(dataBaseName);
        }
       return null;
    }

    /**
     * 获取表对应字段
     * @author by@Deng
     * @date 2017/8/1 下午11:16
     */
    @RequestMapping(value = "/autoCode/getTableColumnsByTableName",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getTableColumnsByTableName(@RequestParam Map<String,String> paramMap){

        String tableName= paramMap.get("tableName");
        String dataBaseName = paramMap.get("dataBaseName");

        if(StringUtils.isNotEmpty(dataBaseName) && StringUtils.isNotEmpty(tableName)){
            return autoCodeService.getTableColumnsByTableName(paramMap);
        }
        return null;
    }


    /**
     * 生成文件
     * @author by@Deng
     * @date 2017/8/1 下午11:20
     */
    @RequestMapping(value = "/autoCode/addFileByTableName",method = RequestMethod.POST)
    @ResponseBody
    public Integer addFileByTableName(@RequestBody TableNamesEntity tableNamesEntity, HttpServletRequest request) {
        try {
            String path = request.getServletContext().getRealPath("ftl");
            File directory = new File(path);

            Map<String,String> map = new HashMap<>();
            map.put("directory",directory.getAbsolutePath());   //模板路径

            return autoCodeService.addFileByTableName(tableNamesEntity,map);
        } catch (Exception e) {
            throw new ByException("生成文件失败");
        }
    }

}
