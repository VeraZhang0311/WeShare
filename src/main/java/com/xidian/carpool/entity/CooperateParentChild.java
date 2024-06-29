package com.xidian.carpool.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class CooperateParentChild {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "cooperateid")
    private int cooperateid;
    @JSONField(name = "parentid")
    private int parentid;
    @JSONField(name = "childid")
    private int childid;
    @JSONField(name = "childAddr")
    private String childAddr;
    @JSONField(name = "state")
    private int state;
    @JSONField(name = "dismistime")
    private Date dismistime;
    @JSONField(name = "cooperatetime")
    private Date cooperatetime;
    @JSONField(name = "destinationAddr")
    private String destinationAddr;
    @JSONField(name = "oncartime")
    private Date oncartime;
    @JSONField(name = "overtime")
    private Date overtime;
    @JSONField(name = "cooperate")
    private CooperateView cooperateView;
    @JSONField(name = "childName")
    private String childName;
    // 申请加入使用的driverid
    @JSONField(name = "driverid")
    private int driverid;
    public CooperateParentChild(int id) {
        this.setId(id);
    }
}
