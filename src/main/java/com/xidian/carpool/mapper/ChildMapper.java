package com.xidian.carpool.mapper;

import com.xidian.carpool.entity.Child;
import com.xidian.carpool.entity.CooperateParentChild;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChildMapper {

    int addChild(Child child);

    int atDBOpenid(Child child);

    int atDBid(Child child);

    Child fillByOpenid(Child child);

    Child fillById(Child child);

    List<CooperateParentChild> undoneCooperation(Child child);

    int userType(@Param("openid") String openid);

    int updateAvatarUrl(@Param("openid") String openid, @Param("avatarUrl") String avatarUrl);

    List<Child> getChildList(List<Integer> idList);
}
