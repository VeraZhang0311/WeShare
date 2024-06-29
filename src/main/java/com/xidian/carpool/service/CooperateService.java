package com.xidian.carpool.service;

import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.Cooperate;
import com.xidian.carpool.entity.CooperateParentChild;
import com.xidian.carpool.entity.CooperateView;
import com.xidian.carpool.entity.Parent;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.mapper.CooperateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class CooperateService {
    @Autowired
    ParentService parentService;
    @Autowired
    CooperateMapper mapper;
    @Autowired
    CooperateParentChildService cooperateParentChildService;

    public BaseResponse newCooperate(Cooperate cooperate) {
        // 创建完善用户
        Parent parent = new Parent(cooperate.getDriverid());
        BaseResponse baseResponse = parentService.fillByID(parent);
        if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
        parent = (Parent) baseResponse.getData();
        // 检查用户是否能开车
        baseResponse = parentService.canDrive(parent);
        if (baseResponse.getCode() != ConstantCode.PARENT_CAN_DRIVE.getCode()) return baseResponse;
        // 检查是否有上一单未结束
        baseResponse = parentService.undoneCooperation(parent);
        if (baseResponse.getCode() != ConstantCode.ALL_COOPERATION_DONE.getCode()) return baseResponse;
        // 检查用的车是不是自己的
        if (!parentService.haveCar(parent, cooperate.getCarid()))
            return new BaseResponse(ConstantCode.HAS_NOT_BONDED_CAR);
        // 检查通过，创建订单
        return this.add2DB(cooperate);
    }

    public BaseResponse updateCooperate(String openid, Cooperate cooperate) {
        // 创建完善用户
        Parent parent = new Parent(openid);
        BaseResponse baseResponse = parentService.fillByOpenid(parent);
        if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
        parent = (Parent) baseResponse.getData();
        // 检查openid是不是订单司机
        if (parent.getId() != cooperate.getDriverid()) return new BaseResponse(ConstantCode.WRONG_DRIVER);
        return this.updateCooperate(cooperate);
    }

    public BaseResponse add2DB(Cooperate cooperate) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.addCooperate(cooperate));
    }

    public boolean atDBid(Cooperate cooperate) {
        return mapper.atDB(cooperate) > 0;
    }

    public BaseResponse fillByID(Cooperate cooperate) {
        if (!this.atDBid(cooperate)) return new BaseResponse(ConstantCode.COOPERATE_NOT_EXIST);
        return new BaseResponse(ConstantCode.SUCCESS, mapper.fillByID(cooperate));
    }

    public BaseResponse updateCooperate(Cooperate cooperate) {
        if (!this.atDBid(cooperate)) return new BaseResponse(ConstantCode.COOPERATE_NOT_EXIST);
        return new BaseResponse(ConstantCode.SUCCESS, mapper.updateCooperate(cooperate));
    }

    public BaseResponse getNearCooperate() {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.getNearCooperates());
    }

    public BaseResponse getCooperatesByDriverID(int driverID) {
        List<CooperateView> cooperateViews = mapper.getCooperatesByDriverID(driverID);
        CooperateView tempCooperate;
        for (int i = 0, size = cooperateViews.size(); i < size; i++) {
            tempCooperate = cooperateViews.get(i);
            tempCooperate.setCpc((List<CooperateParentChild>) cooperateParentChildService.getCPCByCooperateID(tempCooperate.getId()).getData());
            cooperateViews.set(i, tempCooperate);
        }
        return new BaseResponse(ConstantCode.SUCCESS, cooperateViews);
    }

    public BaseResponse getCooperatesByCPCChildID(int childID) {
        List<CooperateParentChild> cpcs = (List<CooperateParentChild>) cooperateParentChildService.getCPCsByChildID(childID).getData();
        List<CooperateView> cooperateViews = new LinkedList<>();
        CooperateView cooperateView;
        for (CooperateParentChild cpc : cpcs) {
            cooperateView = (CooperateView) this.getCooperatesByID(cpc.getCooperateid()).getData();
            List<CooperateParentChild> tempCPCs = Arrays.asList(new CooperateParentChild());
            tempCPCs.set(0, cpc);
            cooperateView.setCpc(tempCPCs);
            cooperateViews.add(cooperateView);
        }
        return new BaseResponse(ConstantCode.SUCCESS, cooperateViews);
    }

    public BaseResponse getCooperatesByID(int ID) {
        CooperateView cooperate = mapper.getCooperatesByID(ID);
        cooperate.setCpc((List<CooperateParentChild>) cooperateParentChildService.getCPCByCooperateID(ID).getData());
        return new BaseResponse(ConstantCode.SUCCESS, cooperate);
    }

    public BaseResponse getCooperatesByID(List<Integer> idList) {
        if (idList.size() == 0) return new BaseResponse(ConstantCode.SUCCESS, new ArrayList<CooperateView>());
        List<CooperateView> cooperateViewList = mapper.getCooperatesByIDList(idList);
        return new BaseResponse(ConstantCode.SUCCESS, cooperateViewList);
    }
}
