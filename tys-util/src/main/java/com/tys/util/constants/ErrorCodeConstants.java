package com.tys.util.constants;

public class ErrorCodeConstants {

	/** 未知错误 ***/
	public static final int ERROR = 0;
	/** 参数无效 ***/
	public static final int ERROR_PARA = 1;
	/** 操作成功 ***/
	public static final int SUCCESS = 1000;
	/** 账号已存在 ***/
	public static final int ERROR_APP_USER_EXISTS = 3001;
	/** 账号或密码错误 ***/
	public static final int ERROR_APP_LOGIN_CHECK_FAILD = 3002;
	/** 未登录，无权操作 ***/
	public static final int ERROR_APP_NO_LOGIN = 3003;
	/** 验证码无效 ***/
	public static final int ERROR_VERIFICATIONCODE_INVALID = 3004;
	/** 登录设备已变更 ***/
	public static final int ERROR_APP_LOGIN_NOT_SAME_PHONE = 3005;
	/** 用户不存在 ***/
	public static final int ERROR_MEMBER_NOT_EXISTS = 3006;
	/** 原密码不正确 ***/
	public static final int ERROR_OLD_PW = 3007;
	/** imei对应的设备不存在 ***/
	public static final int ERROR_EQUIPMENT_NOT_EXISTS = 3008;
	/** 每天只能签到一次 ***/
	public static final int ERROR_CAN_NOT_CHECK_IN = 3009;
	/** 设备未定位 ***/
	public static final int ERROR_NO_LOCATION = 3010;
	/** 当前家长未添加孩子 ***/
	public static final int ERROR_USER_NO_STUENTE = 3011;
	/** 当前用群聊班级不存在 ***/
	public static final int ERROR_CHAT_CLASS_NOT_EXISTS = 3012;
	/** 班级不存在 ***/
	public static final int ERROR_CLASS_NOT_EXISTS = 3013;
	/** 当前用户不是班主任，无权操作 ***/
	public static final int ERROR_NOT_CLASS_ADMIN = 3014;
	/** 当前用户不属于当前群班级 ***/
	public static final int ERROR_USER_NOT_BELONG_CHAT_CLASS = 3015;
	/** 当前设备已被绑定 ***/
	public static final int ERROR_DEVICE_ALREADY_BIND = 3016;
	/** 未找到考勤数据 ***/
	public static final int ERROR_NO_ATTENDANCE = 3017;
	/** 当前老师没有在指定班级任课 ***/
	public static final int ERROR_TEACHER_NO_CLASS = 3018;
	/** 当前老师没有在指定班级任班主任 ***/
	public static final int ERROR_TEACHER_NO_CLASS_ADMIN = 3019;
	/** 设备无响应/不在线 ***/
	public static final int ERROR_DEVICE_RSP_ERROR = 3020;
	/** 设备无响应/不在线 ***/
	public static final int ERROR_NO_NEW_VERSION = 3021;
	
	

}
