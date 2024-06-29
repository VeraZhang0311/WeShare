package com.xidian.carpool.mapper;

import com.xidian.carpool.entity.Verify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VerifyMapper {
    List<Verify> queryVerify();

    int addVerify(Verify verify);

    int confirmVerify(@Param("id") int id, @Param("status") int status);

    Verify getVerifyByID(@Param("id") int id);

    List<Verify> getUnconfirmedVerifyByOpenid(@Param("openid") String openid);

    List<Verify> getUnconfirmedAddCarByCarNumber(@Param("openid") String openid,@Param("carNumber") String carNumber);

    int getCarVerifyIndex(@Param("openid") String openid);
}
