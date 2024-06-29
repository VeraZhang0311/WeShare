package com.xidian.carpool.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Data
@NoArgsConstructor
@Configuration
public class Message {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "fromUID")
    private String fromUID;
    @JSONField(name = "toUID")
    private String toUID;
    @JSONField(name = "time")
    private Date time;
    @JSONField(name = "data")
    private String data;
    @JSONField(name = "cooperateID")
    private int cooperateID;

    public Message(String fromUID, String toUID, String data, int cooperateID) {
        this.fromUID = fromUID;
        this.toUID = toUID;
        this.data = data;
        this.cooperateID = cooperateID;
    }
}
