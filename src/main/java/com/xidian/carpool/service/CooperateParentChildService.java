package com.xidian.carpool.service;

import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.Child;
import com.xidian.carpool.entity.Cooperate;
import com.xidian.carpool.entity.CooperateParentChild;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.mapper.CooperateParentChildMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CooperateParentChildService {
    @Autowired
    CooperateParentChildMapper mapper;
    @Autowired
    CooperateService cooperateService;
    @Autowired
    ChildService childService;

    public BaseResponse joinCooperate(CooperateParentChild cpc) {
        // 获得用户信息
        Child child = new Child(cpc.getChildid());
        BaseResponse baseResponse = childService.fillByID(child);
        if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
        child = (Child) baseResponse.getData();
        // 检查要加入的单是否关闭
        Cooperate cooperate = new Cooperate(cpc.getCooperateid());
        baseResponse = cooperateService.fillByID(cooperate);
        if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
        cooperate = (Cooperate) baseResponse.getData();
        if (cooperate.getState() >= 3) return new BaseResponse(ConstantCode.COOPERATE_NOT_OPEN);
        // 检查是否有上一单未结束
        baseResponse = childService.undoneCooperation(child);
        if (baseResponse.getCode() != ConstantCode.ALL_COOPERATION_DONE.getCode()) return baseResponse;
        // 检查通过，创建订单
        return this.add2DB(cpc);
    }

    public BaseResponse applyCooperate(CooperateParentChild cpc) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.addApplyCPC(cpc));
    }

    public BaseResponse add2DB(CooperateParentChild cooperateParentChild) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.addCPC(cooperateParentChild));
    }

    public boolean atDBid(CooperateParentChild cooperateParentChild) {
        return mapper.atDBid(cooperateParentChild) > 0;
    }

    public BaseResponse updateCPC(CooperateParentChild cpc) {
        if (!atDBid(cpc)) return new BaseResponse(ConstantCode.CPC_NOT_EXIST);
        return new BaseResponse(ConstantCode.SUCCESS, mapper.updateCPC(cpc));
    }

    public BaseResponse getCPCByCooperateID(int cooperateID) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.getCPCByCooperateID(cooperateID));
    }

    public BaseResponse getCPCsByChildID(int childID) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.getCPCsByChildID(childID));
    }

    public BaseResponse getApplyCPCByID(int id) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.getApplyCPCByID(id));
    }

    public BaseResponse handleApply(int applyCPCid, int fromState, int toState) {
        boolean success = mapper.handleApplyCPC(applyCPCid, fromState, toState) == 1;
        if (success && toState == 1) {
            CooperateParentChild cpc = (CooperateParentChild) this.getApplyCPCByID(applyCPCid).getData();
            if (this.joinCooperate(cpc).getCode() != ConstantCode.SUCCESS.getCode()) {
                mapper.handleApplyCPC(applyCPCid, toState, fromState);
                return new BaseResponse(ConstantCode.JOIN_FAILED, false);
            }
        }
        return new BaseResponse(ConstantCode.SUCCESS, success);
    }
}
