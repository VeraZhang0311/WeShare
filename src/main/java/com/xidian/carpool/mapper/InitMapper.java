package com.xidian.carpool.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InitMapper {
    int existTrigger(@Param("triggerName") String triggerName, @Param("dbName") String dbName);

    int existTable(@Param("tableName") String tableName, @Param("dbName") String dbName);

    int initCarTable(@Param("tableName") String tableName);

    int initChildTable(@Param("tableName") String tableName);

    int initCooperateTable(@Param("tableName") String tableName);

    int initCPCTable(@Param("tableName") String tableName);

    int initLicenseTable(@Param("tableName") String tableName);

    int initParentTable(@Param("tableName") String tableName);

    int initPCTable(@Param("tableName") String tableName);

    int initDriverCarTable(@Param("tableName") String tableName);

    int initVerifyTable(@Param("tableName") String tableName);

    int initApplyJoinTable(@Param("tableName") String tableName);

    int initCooperateView(@Param("viewName") String viewName);

    int updateTimeTrigger(@Param("triggerName") String triggerName);

    int addChildNameTrigger(@Param("triggerName") String triggerName);


    int initUnreceivedMessagesTable(@Param("tableName") String tableName);
}
