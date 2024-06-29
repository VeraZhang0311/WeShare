package com.xidian.carpool.service;

import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.License;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.mapper.LicenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {
    @Autowired
    LicenseMapper mapper;

    public BaseResponse add2DB(License license) {
        mapper.addLicense(license);
        mapper.updateDriverLicense(license);
        return new BaseResponse(ConstantCode.SUCCESS);
    }

    public BaseResponse getLicenseByDriverID(int driverid) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.getLicenseByDriverID(driverid));
    }
}
