<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${mapperInfo.daoAllName}">

    <!-- 以下代码片段中,${mapperInfo.tableNamesEntity.tableName}表的别名为RA -->

    <!-- ${mapperInfo.tableNamesEntity.tableName}表的字段 -->
    <sql id ="${mapperInfo.tableNamesEntity.tableName}_columns">
        ${mapperInfo.column}
    </sql>

    
    <!-- ${mapperInfo.tableNamesEntity.tableName}表的resultMap映射 -->
    <resultMap id="entityResultMap" type="${mapperInfo.entityAllName}">
        ${mapperInfo.resMapStr}
    </resultMap>


    <!-- 添加一条记录 -->
    <insert id="insertEntity" parameterType="${mapperInfo.entityAllName}" keyProperty="id" useGeneratedKeys="true">
        INSERT INTO ${mapperInfo.tableNamesEntity.tableName}
        ${mapperInfo.insertColsStr}
    </insert>


    <!-- 批量添加 -->
    <insert id="insertEntityByBatch" parameterType="list" keyProperty="id" useGeneratedKeys="true">
        INSERT INTO ${mapperInfo.tableNamesEntity.tableName}
        ${mapperInfo.insertColsStrByBatch}
    </insert>


    <!-- 更新一条记录 -->
    <update id="updateEntity" parameterType="${mapperInfo.entityAllName}">
        UPDATE ${mapperInfo.tableNamesEntity.tableName}
        ${mapperInfo.updateEntity}
    </update>


    <!-- 根据主键删除一条记录 -->
    <delete id="deleteEntity">
        DELETE FROM ${mapperInfo.tableNamesEntity.tableName} WHERE id = ${r'#{0}'}
    </delete>


    <!-- 根据列条件查询实体类信息 -->
    <sql id="whereColumns">
${mapperInfo.whereColumns}
    </sql>


    <!-- 根据主键查找对应实体类 -->
    <select id="findEntityByMainId" resultType="${mapperInfo.entityAllName}">
        SELECT
            <include refid="${mapperInfo.tableNamesEntity.tableName}_columns"></include>
        FROM ${mapperInfo.tableNamesEntity.tableName} RA
        WHERE id = ${r'#{0}'}
    </select>


    <!-- 根据条件查出来是单条实体类 -->
    <select id="findEntityByOne" parameterType="${mapperInfo.entityAllName}" resultType="${mapperInfo.entityAllName}">
        SELECT
            <include refid="${mapperInfo.tableNamesEntity.tableName}_columns"></include>
        FROM
            ${mapperInfo.tableNamesEntity.tableName} RA
        <where>
            <include refid="whereColumns"></include>
        </where>
    </select>


    <!-- 根据列条件查找实体类集合 -->
    <select id="findEntityList" parameterType="${mapperInfo.entityAllName}" resultType="${mapperInfo.entityAllName}">
        SELECT
            <include refid="${mapperInfo.tableNamesEntity.tableName}_columns"></include>
        FROM
            ${mapperInfo.tableNamesEntity.tableName} RA
        <where>
            <include refid="whereColumns"></include>
        </where>
    </select>


    <!-- 查询记录数 -->
    <select id="findEntityCount" parameterType="${mapperInfo.entityAllName}" resultType="long">
        SELECT
            count(1)
        FROM
            ${mapperInfo.tableNamesEntity.tableName} RA
        <where>
            <include refid="whereColumns"></include>
        </where>
    </select>


    <!-- 单表分页查询 -->
    <select id="findPageEntityList" parameterType="map" resultType="${mapperInfo.entityAllName}">
        SELECT
            <include refid="${mapperInfo.tableNamesEntity.tableName}_columns"></include>
        FROM
            ${mapperInfo.tableNamesEntity.tableName} RA
        <where>
            <include refid="whereColumns"></include>
        </where>
        ORDER BY
        <choose>
            <when test="orderColumn!=null">
                RA.${r'${orderColumn}'}
            </when>
            <otherwise>
                RA.createTime
            </otherwise>
        </choose>
        <choose>
            <when test="sortColumn!=null">
                ${r'${sortColumn}'}
            </when>
            <otherwise>
                DESC
            </otherwise>
        </choose>
        LIMIT ${r'#{pageStart}'},${r'#{pageSize}'}
    </select>


    <!-- 单表分页查询总计数量 -->
    <select id="findPageEntityCount" parameterType="map" resultType="long">
        SELECT
            count(1)
        FROM
            ${mapperInfo.tableNamesEntity.tableName} RA
        <where>
            <include refid="whereColumns"></include>
        </where>
    </select>


</mapper>