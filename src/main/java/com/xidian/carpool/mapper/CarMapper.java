package com.xidian.carpool.mapper;

import com.xidian.carpool.entity.Car;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CarMapper {
    int atDB(@Param("carNumber") String carNumber);

    void addCar(Car car);

    Car fillByCarNumber(Car car);
}
