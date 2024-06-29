package com.xidian.carpool.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class CooperateView extends Cooperate {
    @JSONField(name = "shouldPickStuNum")
    private int shouldPickStuNum;
    @JSONField(name = "stuNumAtCar")
    private int stuNumAtCar;
    @JSONField(name = "cpc")
    private List<CooperateParentChild> cpc;
    @JSONField(name = "driverName")
    private String driverName;
    @JSONField(name = "driverPhone")
    private String driverPhone;
    @JSONField(name = "driverSex")
    private int driverSex;
    @JSONField(name = "driverOpenid")
    private String driverOpenid;
    @JSONField(name = "carColor")
    private String carColor;
    @JSONField(name = "carNumber")
    private String carNumber;
    @JSONField(name = "driverAvatarUrl")
    private String driverAvatarUrl;
}
