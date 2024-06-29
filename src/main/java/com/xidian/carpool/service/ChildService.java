package com.xidian.carpool.service;

import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.Child;
import com.xidian.carpool.entity.CooperateParentChild;
import com.xidian.carpool.entity.CooperateView;
import com.xidian.carpool.entity.Parent;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.mapper.ChildMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChildService {
    @Autowired
    ChildMapper mapper;
    @Autowired
    CooperateService cooperateService;

    public BaseResponse register(Child child) {
        //检查用户是否已经注册
        if (this.atDBOpenid(child)) return new BaseResponse(ConstantCode.CHILD_HAS_REGISTERED);
        return this.add2DB(child);
    }


    public BaseResponse add2DB(Child child) {
        mapper.addChild(child);
        return new BaseResponse(ConstantCode.SUCCESS, child.getId());
    }

    public boolean atDBOpenid(Child child) {
        return mapper.atDBOpenid(child) > 0;
    }

    public boolean atDBid(Child child) {
        return mapper.atDBid(child) > 0;
    }

    public BaseResponse fillByOpenid(Child child) {
        if (!atDBOpenid(child)) return new BaseResponse(ConstantCode.CHILD_NOT_REGISTERED);
        return new BaseResponse(ConstantCode.SUCCESS, mapper.fillByOpenid(child));
    }

    public BaseResponse fillByID(Child child) {
        if (!atDBid(child)) return new BaseResponse(ConstantCode.CHILD_NOT_REGISTERED);
        return new BaseResponse(ConstantCode.SUCCESS, mapper.fillById(child));
    }

    public BaseResponse undoneCooperation(Child child) {
        List<CooperateParentChild> cooperates = mapper.undoneCooperation(child);
        if (cooperates.size() > 0) return new BaseResponse(ConstantCode.HAVE_UNDONE_COOPERATION, cooperates.get(0));
        else return new BaseResponse(ConstantCode.ALL_COOPERATION_DONE);
    }

    public List<CooperateView> getCPCs(Child child) {
        return (List<CooperateView>) cooperateService.getCooperatesByCPCChildID(child.getId()).getData();
    }

    public BaseResponse userType(String openid) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.userType(openid));
    }

    public BaseResponse updateAvatarUrl(String openid, String avatarUrl) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.updateAvatarUrl(openid, avatarUrl));
    }

    public BaseResponse getChildList(List<Integer> idList) {
        if (idList.size() == 0) return new BaseResponse(ConstantCode.SUCCESS, new ArrayList<Child>());
        List<Child> children = mapper.getChildList(idList);
        return new BaseResponse(ConstantCode.SUCCESS, children);
    }
}
