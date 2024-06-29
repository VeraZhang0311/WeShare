package com.xidian.carpool.mapper;

import com.xidian.carpool.entity.License;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Mapper
@Repository
public interface LicenseMapper {
    int addLicense(License license);

    int updateDriverLicense(License license);

    License getLicenseByDriverID(@Param("driverID") int driverID);
}
