package com.xidian.carpool.service;

import com.xidian.carpool.base.ConstantCode;
import com.xidian.carpool.entity.*;
import com.xidian.carpool.entity.base.BaseResponse;
import com.xidian.carpool.mapper.ParentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParentService {
    @Autowired
    ParentMapper mapper;
    @Autowired
    CooperateService cooperateService;
    @Autowired
    ChildService childService;
    @Autowired
    LicenseService licenseService;
    @Autowired
    CarService carService;
    @Autowired
    ParentService parentService;

    /**
     * 家长注册
     *
     * @param parent 家长信息对象
     * @return BaseResponse
     * @see #add2DB(Parent)
     */
    public BaseResponse register(Parent parent) {
        return this.add2DB(parent);
    }

    /**
     * 家长添加孩子<BR/>
     * 流程：家长已注册->家长未存储与child同名的孩子->将孩子信息加入数据库->绑定家长与孩子
     *
     * @param parentOpenid 家长openid
     * @param child        孩子信息对象
     * @return BaseResponse
     */
    public BaseResponse addChild(String parentOpenid, Child child) {
        Parent parent = new Parent(parentOpenid);
        // 检查家长是否注册
        if (!this.atDBopenid(parent)) return new BaseResponse(ConstantCode.PARENT_NOT_REGISTERED);
        // 检查此人是否已经有一个同名孩子
        if (this.haveSomeChildrenName(parent, child.getName()))
            return new BaseResponse(ConstantCode.CHILD_HAS_REGISTERED);
        // 检查是否已经存储了这个孩子，如果是，直接返回
        BaseResponse baseResponse = childService.add2DB(child);
        if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
        int childID = (int) baseResponse.getData();
        return this.bond(parentOpenid, childID);
    }

    /**
     * 家长添加车辆<BR/>
     * 流程：家长已注册->家长有驾照->家长未存储与car相同的车辆->将车辆信息加入数据库->绑定家长与车辆
     *
     * @param parentOpenid 家长openid
     * @param car          车辆信息对象
     * @return BaseResponse
     */
    public BaseResponse addCar(String parentOpenid, Car car) {
        Parent parent = new Parent(parentOpenid);
        BaseResponse baseResponse = this.fillByOpenid(parent);
        if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
        parent = (Parent) baseResponse.getData();
        // 检查是否有驾照
        baseResponse = this.canDrive(parent);
        if (baseResponse.getCode() != ConstantCode.PARENT_CAN_DRIVE.getCode()) return baseResponse;
        // 检查这个人是否已经有这辆车牌号的车
        if (this.haveCar(parent, car.getCarNumber())) return new BaseResponse(ConstantCode.HAS_BONDED_CAR);
        // 存储车辆信息
        baseResponse = carService.add2DB(car);
        if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
        // 将此车归于此人名下
        return this.bondCar(parent, (int) baseResponse.getData());
    }

    /**
     * 家长升级为爱心车主<BR/>
     * 流程：家长已注册->家长无驾照->将驾照信息加入数据库->绑定家长与驾照
     *
     * @param license 驾驶证信息对象
     * @return BaseResponse
     */
    public BaseResponse upgrade(License license) {
        Parent parent = new Parent(license.getDriverid());
        BaseResponse baseResponse = this.fillByID(parent);
        if (baseResponse.getCode() != ConstantCode.SUCCESS.getCode()) return baseResponse;
        parent = (Parent) baseResponse.getData();
        // 检查是否有驾照
        baseResponse = this.canDrive(parent);
        if (baseResponse.getCode() == ConstantCode.PARENT_CAN_DRIVE.getCode()) return baseResponse;
        // 未持有驾照，更新驾照
        return new BaseResponse(ConstantCode.SUCCESS, licenseService.add2DB(license));
    }

    /**
     * 获取家长所有的车辆<BR/>
     * 流程：家长已注册->获取家长所有车辆
     *
     * @param parentOpenid 家长openid
     * @return BaseResponse
     * @see #getCars(Parent)
     */
    public BaseResponse getCar(String parentOpenid) {
        Parent parent = new Parent(parentOpenid);
        if (!this.atDBopenid(parent)) return new BaseResponse(ConstantCode.PARENT_NOT_REGISTERED);
        // 获取所有的车
        return new BaseResponse(ConstantCode.SUCCESS, this.getCars(parent));
    }

    /**
     * 通过openid判断家长是否在数据库
     *
     * @param parent 家长信息对象
     * @return Boolean
     */
    public boolean atDBopenid(Parent parent) {
        return mapper.atDBopenid(parent) > 0;
    }

    /**
     * 通过id判断家长是否在数据库
     *
     * @param parent 家长信息对象
     * @return Boolean
     */
    public boolean atDBid(Parent parent) {
        return mapper.atDBid(parent) > 0;
    }

    /**
     * 将家长存入数据库<BR/>
     * 流程：家长未注册->将家长存入数据库
     *
     * @param parent 家长信息对象
     * @return BaseResponse
     */
    public BaseResponse add2DB(Parent parent) {
        //检查用户是否已经注册
        if (atDBopenid(parent)) return new BaseResponse(ConstantCode.PARENT_HAS_REGISTERED);
        return new BaseResponse(ConstantCode.SUCCESS, mapper.addParent(parent));
    }

    /**
     * 通过家长openid获取家长信息<BR/>
     * 流程：家长已注册->获取家长信息
     *
     * @param parent 家长信息对象，至少openid不为null
     * @return BaseResponse Data:Parent
     */
    public BaseResponse fillByOpenid(Parent parent) {
        if (!atDBopenid(parent)) return new BaseResponse(ConstantCode.PARENT_NOT_REGISTERED);
        return new BaseResponse(ConstantCode.SUCCESS, mapper.fillByOpenid(parent));
    }

    /**
     * 通过家长id获取家长信息<BR/>
     * 流程：家长已注册->获取家长信息
     *
     * @param parent 家长信息对象，至少id不为null
     * @return BaseResponse Data:Parent
     */
    public BaseResponse fillByID(Parent parent) {
        if (!atDBid(parent)) return new BaseResponse(ConstantCode.PARENT_NOT_REGISTERED);
        return new BaseResponse(ConstantCode.SUCCESS, mapper.fillById(parent));
    }

    /**
     * 判断家长是否为爱心车主<BR/>
     *
     * @param parent 家长信息对象，至少licenseid不为null
     * @return BaseResponse Data:Boolean
     */
    public BaseResponse canDrive(Parent parent) {
        if (parent.getLicenseid() != 0) return new BaseResponse(ConstantCode.PARENT_CAN_DRIVE, true);
        else return new BaseResponse(ConstantCode.PARENT_CANNOT_DRIVE, false);
    }

    /**
     * 获取家长所有的车辆
     *
     * @param parent 家长信息对象，至少id不为null
     * @return List<Car>
     * @see #getCar(String)
     */
    public List<Car> getCars(Parent parent) {
        return mapper.getCars(parent);
    }

    /**
     * 检查家长是否有未完成订单，如果有，返回其未完成订单
     *
     * @param parent 家长信息对象，至少id不为null
     * @return BaseResponse
     */
    public BaseResponse undoneCooperation(Parent parent) {
        List<Cooperate> cooperates = mapper.undoneCooperation(parent);
        if (cooperates.size() > 0) return new BaseResponse(ConstantCode.HAVE_UNDONE_COOPERATION, cooperates.get(0));
        else return new BaseResponse(ConstantCode.ALL_COOPERATION_DONE);
    }

    /**
     * 判断家长是否有某车牌号的车
     *
     * @param parent    家长信息对象，至少id不为null
     * @param carNumber 车牌号
     * @return Boolean
     */
    public boolean haveCar(Parent parent, String carNumber) {
        return mapper.haveSomeCarNumber(parent.getId(), carNumber) > 0;
    }

    /**
     * 判断家长是否有某id的车
     *
     * @param parent 家长信息对象，至少id不为null
     * @param carID  车辆ID
     * @return Boolean
     */
    public boolean haveCar(Parent parent, int carID) {
        return mapper.haveSomeCarID(parent.getId(), carID) > 0;
    }

    /**
     * 将数据库中id为carID与parent绑定起来
     *
     * @param parent 家长信息对象，至少id不为null
     * @param carID  车辆ID
     * @return BaseResponse
     */
    public BaseResponse bondCar(Parent parent, int carID) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.bondCar(parent.getId(), carID));
    }

    /**
     * 从数据库中拿到这个家长的驾驶证信息
     *
     * @param parent 家长信息对象，至少id不为null
     * @return License
     */
    public License getLicense(Parent parent) {
        return (License) licenseService.getLicenseByDriverID(parent.getId()).getData();
    }

    /**
     * 从数据库中拿到这个家长的所有订单
     *
     * @param parent 家长信息对象，至少id不为null
     * @return List<CooperateView>
     */
    public List<CooperateView> getCooperates(Parent parent) {
        return (List<CooperateView>) cooperateService.getCooperatesByDriverID(parent.getId()).getData();
    }

    /**
     * 从数据库中获取这个家长的所有孩子
     * 流程：获取所有孩子->孩子获取自己的订单记入孩子对象中
     *
     * @param parent 家长信息对象，至少id不为null
     * @return List<Child>
     */
    public List<Child> getChildren(Parent parent) {
        List<Child> children = mapper.getChildren(parent);
        Child tempChild;
        for (int i = 0; i < children.size(); ++i) {
            tempChild = children.get(i);
            tempChild.setCpcs(childService.getCPCs(children.get(i)));
            children.set(i, tempChild);
        }
        return children;
    }

    /**
     * 判断家长是否有名为childName的孩子
     *
     * @param parent    家长信息对象，至少openid不为null
     * @param childName 孩子姓名
     * @return Boolean
     */
    public boolean haveSomeChildrenName(Parent parent, String childName) {
        return mapper.haveSomeChildrenName(parent, childName) > 0;
    }

    /**
     * 判断家长是否有openid为childOpenid的孩子
     *
     * @param parent      家长信息对象，至少openid不为null
     * @param childOpenid 孩子openid
     * @return Boolean
     */
    public boolean haveSomeChildrenOpenid(Parent parent, String childOpenid) {
        return mapper.haveSomeChildrenOpenid(parent, childOpenid) > 0;
    }

    /**
     * 通过家长的openid与孩子的openid绑定家长与孩子<BR/>
     * 流程：家长已注册->孩子未注册->家长无此openid的孩子->绑定家长与孩子
     *
     * @param parentOpenid 家长openid
     * @param childOpenid  孩子openid
     * @return BaseResponse
     */
    public BaseResponse bond(String parentOpenid, String childOpenid) {
        Parent parent = new Parent(parentOpenid);
        if (!this.atDBopenid(parent))
            return new BaseResponse(ConstantCode.PARENT_NOT_REGISTERED);
        if (!childService.atDBOpenid(new Child(childOpenid)))
            return new BaseResponse(ConstantCode.CHILD_NOT_REGISTERED);
        if (parentService.haveSomeChildrenOpenid(parent, childOpenid))
            return new BaseResponse(ConstantCode.HAS_BONDED_CHILD);
        return new BaseResponse(ConstantCode.SUCCESS, mapper.bondOpenid(parentOpenid, childOpenid));
    }

    /**
     * 通过家长的openid与孩子的id绑定家长与孩子<BR/>
     * 流程：家长已注册->孩子未注册->绑定家长与孩子
     *
     * @param parentOpenid 家长openid
     * @param childid      孩子id
     * @return BaseResponse
     */
    public BaseResponse bond(String parentOpenid, int childid) {
        if (!this.atDBopenid(new Parent(parentOpenid)))
            return new BaseResponse(ConstantCode.PARENT_NOT_REGISTERED);
        if (!childService.atDBid(new Child(childid))) return new BaseResponse(ConstantCode.CHILD_NOT_REGISTERED);
        return new BaseResponse(ConstantCode.SUCCESS, mapper.bondid(parentOpenid, childid));
    }

    /**
     * 获取某openid的用户类型
     *
     * @param openid 用户openid
     * @return BaseResponse Data:Integer 孩子:-1 未注册:0 家长:1
     */
    public BaseResponse userType(String openid) {
        return new BaseResponse(ConstantCode.SUCCESS, mapper.userType(openid));
    }

    /**
     * 更新用户的头像地址
     *
     * @param openid    用户openid
     * @param avatarUrl 用户头像地址
     * @return BaseResponse
     */
    public BaseResponse updateAvatarUrl(String openid, String avatarUrl) {
        int userType = (int) this.userType(openid).getData();
        if (userType == 1) {
            return new BaseResponse(ConstantCode.SUCCESS, mapper.updateAvatarUrl(openid, avatarUrl));
        } else if (userType == -1) {
            return childService.updateAvatarUrl(openid, avatarUrl);
        }
        return null;
    }

    /**
     * 通过一个家长的id列表，获取这些家长的信息
     *
     * @param idList 家长id列表
     * @return BaseResponse Data:List<Parent>
     */
    public BaseResponse getParentList(List<Integer> idList) {
        if (idList.size() == 0) return new BaseResponse(ConstantCode.SUCCESS, new ArrayList<Parent>());
        List<Parent> parents = mapper.getParentList(idList);
        return new BaseResponse(ConstantCode.SUCCESS, parents);
    }

    /**
     * 获取家长的拼车申请详细信息
     *
     * @param parent 家长信息对象
     * @return BaseResponse
     */
    public BaseResponse getApplyCooperates(Parent parent) {
        List<CooperateParentChild> cpcList = mapper.getApplyCooperates(parent);
        List<Map<String, Object>> res = new ArrayList<>();
        Set<Integer> childID = new HashSet();
        Set<Integer> parentID = new HashSet();
        Set<Integer> cooperateID = new HashSet();
        for (CooperateParentChild cpc : cpcList) {
            childID.add(cpc.getChildid());
            cooperateID.add(cpc.getCooperateid());
            if (cpc.getParentid() != 0) {
                parentID.add(cpc.getParentid());
            }
        }
        List<Child> children = (List<Child>) childService.getChildList(new ArrayList<Integer>(childID)).getData();
        List<Parent> parents = (List<Parent>) parentService.getParentList(new ArrayList<Integer>(parentID)).getData();
        List<CooperateView> cooperateViews = (List<CooperateView>) cooperateService.getCooperatesByID(new ArrayList<Integer>(cooperateID)).getData();
        for (CooperateParentChild cpc : cpcList) {
            Map<String, Object> map = new HashMap<>();
            map.put("applyData", cpc);
            map.put("child", this.getChildIndex(children, cpc.getChildid()));
            map.put("cooperate", this.getCooperateViewIndex(cooperateViews, cpc.getCooperateid()));
            if (cpc.getParentid() != 0)
                map.put("parent", this.getParentIndex(parents, cpc.getParentid()));
            res.add(map);
        }
        return new BaseResponse(ConstantCode.SUCCESS, res);
    }

    /**
     * 寻找孩子列表中id为targetID的那个孩子
     *
     * @param list     孩子列表
     * @param targetID 目标ID
     * @return Child
     */
    private Child getChildIndex(List<Child> list, int targetID) {
        for (Child child : list) {
            if (child.getId() == targetID)
                return child;
        }
        return null;
    }

    /**
     * 寻找家长列表中id为targetID的那个家长
     *
     * @param list     家长列表
     * @param targetID 目标ID
     * @return Parent
     */
    private Parent getParentIndex(List<Parent> list, int targetID) {
        for (Parent parent : list) {
            if (parent.getId() == targetID)
                return parent;
        }
        return null;
    }

    /**
     * 寻找订单列表中id为targetID的那个订单
     *
     * @param list     订单列表
     * @param targetID 目标ID
     * @return CooperateView
     */
    private CooperateView getCooperateViewIndex(List<CooperateView> list, int targetID) {
        for (CooperateView cooperateView : list) {
            if (cooperateView.getId() == targetID)
                return cooperateView;
        }
        return null;
    }

    /**
     * 查询家长是否有未处理的升级申请
     *
     * @param openid 用户openid
     * @return BaseResponse Data:boolean
     */
    public BaseResponse verifying(String openid) {
        Parent parent = new Parent(openid);
        if (!this.atDBopenid(parent))
            return new BaseResponse(ConstantCode.PARENT_NOT_REGISTERED);
        return new BaseResponse(ConstantCode.SUCCESS, mapper.verifying(openid) > 0);
    }
}
