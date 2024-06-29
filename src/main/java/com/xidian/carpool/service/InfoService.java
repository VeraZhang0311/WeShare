package com.xidian.carpool.service;

import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.*;
import com.xidian.carpool.entity.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoService {
    @Autowired
    ParentService parentService;
    @Autowired
    CooperateService cooperateService;
    @Autowired
    ChildService childService;

    public BaseResponse getNearCooperate() {
        return cooperateService.getNearCooperate();
    }

    public BaseResponse getParentInfo(String openid) {
        Map<String, Object> map = new HashMap<>();
        Parent parent = (Parent) parentService.fillByOpenid(new Parent(openid)).getData();
        map.put("userInfo", parent);
        map.put("userType", "家长");
        map.put("nearCooperate", getNearCooperate().getData());
        map.put("cars", parentService.getCars(parent));
        map.put("children", parentService.getChildren(parent));
        map.put("license", parentService.getLicense(parent));
        map.put("cooperates", parentService.getCooperates(parent));
        map.put("applyCooperates", parentService.getApplyCooperates(parent));
        return new BaseResponse(ConstantCode.SUCCESS, map);
    }

    public BaseResponse getChildInfo(String openid) {
        Child child = new Child(openid);
        //if (!childService.atDBOpenid(child)) return new BaseResponse(ConstantCode.CHILD_NOT_REGISTERED);
        BaseResponse baseResponse = childService.fillByOpenid(child);
        if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
        child = (Child) baseResponse.getData();
        child.setCpcs(childService.getCPCs(child));
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", child);
        map.put("userType", "学生");
        map.put("nearCooperate", getNearCooperate().getData());
        return new BaseResponse(ConstantCode.SUCCESS, map);
    }

    public BaseResponse getUserInfo(String openid) {
        int userType = (int) parentService.userType(openid).getData();
        if (userType == -1) return getChildInfo(openid);
        else if (userType == 1) return getParentInfo(openid);
        else {
            Map<String, Object> map = new HashMap<>();
            map.put("nearCooperate", getNearCooperate().getData());
            return new BaseResponse(ConstantCode.USER_NOT_REGISTERED, map);
        }
    }

    public BaseResponse getBaseUserInfo(List<String> openid) {
        int userType;
        BaseResponse baseResponse;
        Map<String, Object> map = new HashMap<>();
        for (String o : openid) {
            userType = (int) parentService.userType(o).getData();
            if (userType == -1) {
                Child child = new Child(o);
                if (childService.atDBOpenid(child)) {
                    baseResponse = childService.fillByOpenid(child);
                    if (baseResponse.getCode() == ConstantCode.SUCCESS.getCode()) {
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("userInfo", baseResponse.getData());
                        userMap.put("userType", -1);
                        map.put(o, userMap);
                    }
                }
            } else if (userType == 1) {
                Parent parent = new Parent(o);
                if (parentService.atDBopenid(parent)) {
                    baseResponse = parentService.fillByOpenid(parent);
                    if (baseResponse.getCode() == ConstantCode.SUCCESS.getCode()) {
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("userInfo", baseResponse.getData());
                        userMap.put("userType", 1);
                        map.put(o, userMap);
                    }
                }
            }
        }
        return new BaseResponse(ConstantCode.SUCCESS, map);
    }
}
