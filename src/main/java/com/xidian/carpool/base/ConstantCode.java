package com.xidian.carpool.base;

public interface ConstantCode {
    /* return success */
    RetCode SUCCESS = RetCode.mark(0, "success");
    /* websocket info*/
    RetCode SEND_SUCCESS = RetCode.mark(205001, "消息发送成功");
    RetCode LOGIN_SUCCESS = RetCode.mark(205002, "websocket登陆成功");
    /* user class error */
    RetCode PARENT_NOT_REGISTERED = new RetCode(305001, "家长还未注册");
    RetCode CHILD_NOT_REGISTERED = new RetCode(305002, "孩子还未注册");
    RetCode CHILD_HAS_REGISTERED = new RetCode(305003, "孩子已经注册");
    RetCode PARENT_HAS_REGISTERED = new RetCode(305004, "家长已经注册");
    RetCode USER_NOT_REGISTERED = new RetCode(305005, "用户未注册");
    RetCode UPLOAD_FILE_ERROR = new RetCode(305006, "图片上传失败");
    RetCode HAVE_UNCONFIRMED_VERIFY = new RetCode(305007, "已有未审核申请");
    RetCode CHILD_CANNOT_UPGRADE = new RetCode(305008, "学生不能升级为爱心车主");
    RetCode HAS_BONDED_CAR = new RetCode(305009, "司机已经绑定了此车");
    RetCode HAS_NOT_BONDED_CAR = new RetCode(305010, "司机还未绑定此车");
    RetCode WRONG_DRIVER = new RetCode(305011, "不是您的订单");
    RetCode PARENT_CANNOT_DRIVE = new RetCode(305012, "家长还未注册驾照");
    RetCode PARENT_CAN_DRIVE = new RetCode(305013, "家长已拥有驾照");
    RetCode HAVE_UNDONE_COOPERATION = new RetCode(305014, "还有订单未完成");
    RetCode ALL_COOPERATION_DONE = new RetCode(305015, "所有订单都已完成");
    RetCode COOPERATE_EXIST = new RetCode(305016, "订单已存在");
    RetCode COOPERATE_NOT_EXIST = new RetCode(305017, "订单不存在");
    RetCode CPC_EXIST = new RetCode(305018, "已加入此订单");
    RetCode CPC_NOT_EXIST = new RetCode(305019, "订单不存在");
    RetCode COOPERATE_NOT_OPEN = new RetCode(305020, "订单已关闭");
    RetCode JOIN_FAILED = new RetCode(305021, "加入订单失败");
    RetCode UID_NULL = new RetCode(305022, "登陆uid为空");
    RetCode CAR_EXIST = new RetCode(305023, "车辆已经存在");
    RetCode CAR_NOT_EXIST = new RetCode(305024, "车辆不存在");
    RetCode STORE_CAR_FAILED = new RetCode(305025, "车辆存储失败");
    RetCode BOND_CAR_FAILED = new RetCode(305026, "车辆绑定失败");
    RetCode HAS_BONDED_CHILD = new RetCode(305027, "家长已经绑定了此学生");

    /* system error */
    RetCode SYSTEM_ERROR = RetCode.mark(105001, "服务端错误");
    RetCode PARAM_VAILD_FAIL = RetCode.mark(105002, "参数无效");
    RetCode MYSQL_ERROR = RetCode.mark(105003, "数据库错误");
    RetCode API_REFUSED = RetCode.mark(105004, "API请求被拒绝");

}
