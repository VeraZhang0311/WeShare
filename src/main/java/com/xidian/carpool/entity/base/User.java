package com.xidian.carpool.entity.base;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @JSONField(name = "id")
    @NotNull
    private int id;
    @JSONField(name = "openid")
    private String openid;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "sex")
    private int sex;
    @JSONField(name = "phone")
    private String phone;
    @JSONField(name = "avatarUrl")
    private String avatarUrl;

    public User(String openid) {
        this.openid = openid;
    }

    public User(boolean fake) {
        this.setOpenid("1");
        this.setSex(1);
        this.setName("1");
        this.setPhone("1");
        this.setAvatarUrl("1");
    }

    public User(int id) {
        this.setId(id);
    }
}
