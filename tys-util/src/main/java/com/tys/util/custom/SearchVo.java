package com.tys.util.custom;

/**
 * 
 * <pre>
 * 搜索属性bean
 *
 * 创建日期: 2016年3月13日
 * 修改人 :  liul
 * 修改说明:
 * </pre>
 */
public class SearchVo {

	private Integer agentId;

	private String addrCodeMind;

	private Integer s_province;

	private Integer s_city;

	private Integer s_area;

	private String s_schoolName;

	private Integer s_schoolId;

	private String s_address;

	private String s_teacherName;

	private String s_phone;

	private String s_custAcct;

	private String s_isRegister;

	private String s_parentName;

	private String s_parentPhone;

	private String s_studentName;

	private String s_name;

	private Integer userType; // 处理mduser表中type

	private Integer userId;

	private Integer s_schoolAdminId;// 处理查询非mdSchool

	private int pagenum;

	private int pagesize;

	private String imeiNo;

	public Integer getS_province() {
		return s_province;
	}

	public void setS_province(Integer s_province) {
		this.s_province = s_province;
	}

	public Integer getS_city() {
		return s_city;
	}

	public void setS_city(Integer s_city) {
		this.s_city = s_city;
	}

	public Integer getS_area() {
		return s_area;
	}

	public void setS_area(Integer s_area) {
		this.s_area = s_area;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getS_schoolName() {
		return s_schoolName;
	}

	public void setS_schoolName(String s_schoolName) {
		this.s_schoolName = s_schoolName;
	}

	public String getS_address() {
		return s_address;
	}

	public void setS_address(String s_address) {
		this.s_address = s_address;
	}

	public String getS_teacherName() {
		return s_teacherName;
	}

	public void setS_teacherName(String s_teacherName) {
		this.s_teacherName = s_teacherName;
	}

	public String getS_phone() {
		return s_phone;
	}

	public void setS_phone(String s_phone) {
		this.s_phone = s_phone;
	}

	public String getS_custAcct() {
		return s_custAcct;
	}

	public void setS_custAcct(String s_custAcct) {
		this.s_custAcct = s_custAcct;
	}

	public String getS_isRegister() {
		return s_isRegister;
	}

	public void setS_isRegister(String s_isRegister) {
		this.s_isRegister = s_isRegister;
	}

	public String getS_parentName() {
		return s_parentName;
	}

	public void setS_parentName(String s_parentName) {
		this.s_parentName = s_parentName;
	}

	public String getS_studentName() {
		return s_studentName;
	}

	public void setS_studentName(String s_studentName) {
		this.s_studentName = s_studentName;
	}

	public int getPagenum() {
		return pagenum;
	}

	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getS_schoolAdminId() {
		return s_schoolAdminId;
	}

	public void setS_schoolAdminId(Integer s_schoolAdminId) {
		this.s_schoolAdminId = s_schoolAdminId;
	}

	public Integer getS_schoolId() {
		return s_schoolId;
	}

	public void setS_schoolId(Integer s_schoolId) {
		this.s_schoolId = s_schoolId;
	}

	public String getS_parentPhone() {
		return s_parentPhone;
	}

	public void setS_parentPhone(String s_parentPhone) {
		this.s_parentPhone = s_parentPhone;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getAddrCodeMind() {
		return addrCodeMind;
	}

	public void setAddrCodeMind(String addrCodeMind) {
		this.addrCodeMind = addrCodeMind;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

}
