package com.xidian.carpool.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class Car {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "color")
    private String color;
    @JSONField(name = "carNumber")
    private String carNumber;

    public Car(String color, String carNumber) {
        this.setColor(color);
        this.setCarNumber(carNumber);
    }

    public Car(int id) {
        this.setId(id);
    }

    public Car(String carNumber) {
        this.setCarNumber(carNumber);
    }

}
