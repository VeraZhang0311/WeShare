package com.xidian.carpool.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Verify {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "openid")
    private String openid;
    @JSONField(name = "carColor")
    private String carColor;
    @JSONField(name = "carNumber")
    private String carNumber;
    @JSONField(name = "accept")
    private int accept;
    @JSONField(name = "driver")
    private Parent driver;
    @JSONField(name = "type")
    private int type;
    @JSONField(name = "index")
    private int index;
}