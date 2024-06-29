package com.xidian.carpool.mapper;

import com.xidian.carpool.entity.CooperateParentChild;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CooperateParentChildMapper {
    int addCPC(CooperateParentChild cooperateParentChild);

    int atDBid(CooperateParentChild cooperateParentChild);

    int updateCPC(CooperateParentChild cooperateParentChild);

    CooperateParentChild fillByID(CooperateParentChild cpc);

    List<CooperateParentChild> getCPCByCooperateID(@Param("cooperateID") int cooperateID);

    List<CooperateParentChild> getCPCsByChildID(@Param("childID") int childID);

    int addApplyCPC(CooperateParentChild cpc);

    int handleApplyCPC(@Param("id") int id, @Param("fromState") int fromState, @Param("toState") int toState);

    CooperateParentChild getApplyCPCByID(@Param("id") int id);
}
