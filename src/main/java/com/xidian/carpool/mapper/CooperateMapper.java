package com.xidian.carpool.mapper;

import com.xidian.carpool.entity.Cooperate;
import com.xidian.carpool.entity.CooperateView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CooperateMapper {
    int addCooperate(Cooperate cooperate);

    Cooperate fillByID(Cooperate cooperate);

    int atDB(Cooperate cooperate);

    int updateCooperate(Cooperate cooperate);

    List<CooperateView> getNearCooperates();

    List<CooperateView> getCooperatesByDriverID(@Param("driverID") int driverID);

    CooperateView getCooperatesByID(@Param("id") int id);

    List<CooperateView> getCooperatesByIDList(@Param("idList") List<Integer> idList);
}
