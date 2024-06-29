package com.xidian.carpool.mapper;

import com.xidian.carpool.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ParentMapper {

    List<Parent> queryParent();

    int addParent(Parent parent);

    int atDBopenid(Parent parent);

    int atDBid(Parent parent);

    Parent fillByOpenid(Parent parent);

    Parent fillById(Parent parent);

    List<Cooperate> undoneCooperation(Parent parent);

    List<Car> getCars(Parent parent);

    int haveSomeCarNumber(@Param("driverid") int driverid, @Param("carNumber") String carNumber);

    int haveSomeCarID(@Param("driverid") int driverid, @Param("carID") int carID);

    int bondCar(@Param("driverid") int driverid, @Param("carid") int carid);

    List<Child> getChildren(Parent parent);

    int haveSomeChildrenName(Parent parent, @Param("childName") String childName);

    int bondid(@Param("parentOpenid") String parentOpenid, @Param("childid") int childid);

    int bondOpenid(@Param("parentOpenid") String parentOpenid, @Param("childOpenid") String childOpenid);

    int userType(@Param("openid") String openid);

    int updateAvatarUrl(@Param("openid") String openid, @Param("avatarUrl") String avatarUrl);

    List<CooperateParentChild> getApplyCooperates(Parent parent);

    List<Parent> getParentList(List<Integer> idList);

    int haveSomeChildrenOpenid(Parent parent, @Param("childOpenid") String childOpenid);

    int verifying(@Param("openid") String openid);
}
