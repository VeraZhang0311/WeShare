package com.xidian.carpool.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.xidian.carpool.entity.base.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Child extends User {
    @JSONField(name = "stuclass")
    private String stuclass;
    @JSONField(name = "schooladdr")
    private String schooladdr;
    @JSONField(name = "homeaddr")
    private String homeaddr;
    @JSONField(name = "cpcs")
    private List<CooperateView> cpcs;

    public Child(String openid) {
        super(openid);
    }

    public Child(int id) {
        super(id);
    }

    public Child(boolean fake) {
        super(fake);
        this.setStuclass("1");
        this.setSchooladdr("1");
        this.setHomeaddr("1");
    }
}
