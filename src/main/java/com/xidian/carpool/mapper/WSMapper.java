package com.xidian.carpool.mapper;

import com.xidian.carpool.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WSMapper {
    int addMessage(Message message);

    List<Message> getOfflineMsg(String id);

    int delOfflineMsh(String id);
}
