package com.xidian.carpool.service;

import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.Car;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    CarMapper mapper;

    public BaseResponse fillByCarNumber(Car car) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.fillByCarNumber(car));
    }

    public BaseResponse add2DB(Car car) {
        //查看这个车牌号的车是不是已经有了
        if (this.atDB(car)) {
            BaseResponse baseResponse = this.fillByCarNumber(car);
            if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
            car = (Car) baseResponse.getData();
            return new BaseResponse(ConstantCode.CAR_EXIST, car.getId());
        }
        mapper.addCar(car);
        return new BaseResponse(ConstantCode.SUCCESS, car.getId());
    }

    public boolean atDB(Car car) {
        //查看这个车牌号的车是不是已经有了
        return mapper.atDB(car.getCarNumber()) > 0;
    }
}
