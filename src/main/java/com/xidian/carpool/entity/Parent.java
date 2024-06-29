package com.xidian.carpool.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.xidian.carpool.entity.base.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parent extends User {
    @JSONField(name = "idnumber")
    @NotNull
    private String idnumber;
    @JSONField(name = "licenseid")
    private int licenseid;

    public Parent(boolean fake) {
        super(fake);
        this.setIdnumber("1");
        this.setLicenseid(-1);
    }

    public Parent(String parentOpenid) {
        super(parentOpenid);
    }

    public Parent(int id) {
        super(id);
    }
}
