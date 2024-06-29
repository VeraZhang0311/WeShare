package com.xidian.carpool.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class License {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "driverid")
    private int driverid;

    public License(int id, int driverid) {
        this.id = id;
        this.driverid = driverid;
    }

    public License(int driverid) {
        this.driverid = driverid;
    }
}

