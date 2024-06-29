package com.xidian.carpool.service;

import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.Car;
import com.xidian.carpool.entity.License;
import com.xidian.carpool.entity.Parent;
import com.xidian.carpool.entity.Verify;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.mapper.VerifyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class VerifyService {
    @Autowired
    VerifyMapper mapper;
    @Autowired
    ParentService parentService;


    public BaseResponse queryVerify() {
        List<Verify> verifies = mapper.queryVerify();
        for (int i = 0; i < verifies.size(); ++i) {
            Verify verify = verifies.get(i);
            verify.setDriver((Parent) parentService.fillByOpenid(new Parent(verify.getOpenid())).getData());
            verifies.set(i, verify);
        }
        return new BaseResponse(ConstantCode.SUCCESS, verifies);
    }

    public BaseResponse addVerify(Verify verify) {
        int userType = (int) parentService.userType(verify.getOpenid()).getData();
        if (userType == 0)
            return new BaseResponse(ConstantCode.PARENT_NOT_REGISTERED);
        else if (userType == -1)
            return new BaseResponse(ConstantCode.CHILD_CANNOT_UPGRADE);
        //查询是否已经申请并待审核
        if (verify.getType() == 0) {
            List<Verify> unconfirmedVerify = mapper.getUnconfirmedVerifyByOpenid(verify.getOpenid());
            if (unconfirmedVerify.size() > 0)
                return new BaseResponse(ConstantCode.HAVE_UNCONFIRMED_VERIFY, unconfirmedVerify);
        } else if (verify.getType() == 1) {
            List<Verify> unconfirmedVerify = mapper.getUnconfirmedAddCarByCarNumber(verify.getOpenid(), verify.getCarNumber());
            if (unconfirmedVerify.size() > 0)
                return new BaseResponse(ConstantCode.HAVE_UNCONFIRMED_VERIFY, unconfirmedVerify);
            verify.setIndex(mapper.getCarVerifyIndex(verify.getOpenid()));
        }
        return new BaseResponse(ConstantCode.SUCCESS, mapper.addVerify(verify));
    }

    public BaseResponse confirmVerify(int id, boolean status) {
        // 审核通过
        if (status) {
            Verify verify = mapper.getVerifyByID(id);
            // 如果已经被审核过
            if (verify.getAccept() == 1)
                return new BaseResponse(ConstantCode.SUCCESS);
            else if (verify.getAccept() == 2)
                return new BaseResponse(ConstantCode.SYSTEM_ERROR);
            // 通过审核
            // 添加驾驶证
            Parent parent = new Parent(verify.getOpenid());
            BaseResponse baseResponse = parentService.fillByOpenid(parent);
            if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
            parent = (Parent) baseResponse.getData();
            if (verify.getType() == 0) {
                baseResponse = parentService.upgrade(new License(parent.getId()));
                if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
            }
            // 添加车辆
            Car car = new Car(verify.getCarColor(), verify.getCarNumber());
            baseResponse = parentService.addCar(verify.getOpenid(), car);
            if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
        }
        return new BaseResponse(ConstantCode.SUCCESS, mapper.confirmVerify(id, status ? 1 : 2));
    }

    public BaseResponse saveUploadUpgrade(String openid, int type, MultipartFile file) {
        if (this.saveFile(file, openid + "_" + type)) {
            return new BaseResponse(ConstantCode.SUCCESS);
        } else {
            return new BaseResponse(ConstantCode.UPLOAD_FILE_ERROR);
        }
    }

    public BaseResponse saveUploadCar(String openid, int type, MultipartFile file) {
        int index = mapper.getCarVerifyIndex(openid);
        if (this.saveFile(file, openid + "_Car_" + index + "_" + type)) {
            return new BaseResponse(ConstantCode.SUCCESS);
        } else {
            return new BaseResponse(ConstantCode.UPLOAD_FILE_ERROR);
        }
    }

    private boolean saveFile(MultipartFile file, String fileName) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String filePath = System.getProperty("user.dir") + "/static/images/upload/"
                        + fileName;
                // 转存文件
                File upload = new File(filePath);
                if (!upload.exists() && !upload.mkdirs()) return false;
                file.transferTo(upload);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
