package com.xidian.carpool.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.xidian.carpool.mapper.CooperateMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class Cooperate {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "datetime")
    private Date datetime;
    @JSONField(name = "driverid")
    private int driverid;
    @JSONField(name = "state")
    private int state;
    @JSONField(name = "maxstu")
    private int maxstu;
    @JSONField(name = "locate")
    private String locate;
    @JSONField(name = "locateTime")
    private Date locateTime;
    @JSONField(name = "carid")
    private int carid;
    @JSONField(name = "departureTime")
    private Date departureTime;
    @JSONField(name = "planArriveTime")
    private Date planArriveTime;
    @JSONField(name = "arriveTime")
    private Date arriveTime;
    @JSONField(name = "leftTime")
    private Date leftTime;
    @JSONField(name = "overTime")
    private Date overTime;
    @JSONField(name = "departure")
    private String departure;
    @JSONField(name = "destination")
    private String destination;

    public Cooperate(int id) {
        this.setId(id);
    }
}
