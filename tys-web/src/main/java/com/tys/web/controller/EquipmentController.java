/**
 * 
 */
package com.tys.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.tys.dto.DataTableReqDTO;
import com.tys.dto.DataTableRspDTO;
import com.tys.entity.MdEquipment;
import com.tys.entity.MdSchool;
import com.tys.entity.RelSysuserLevel;
import com.tys.entity.SysUser;
import com.tys.service.MdEquipmentService;
import com.tys.service.MdSchoolService;
import com.tys.service.RelSysuserLevelService;
import com.tys.service.SysUserService;
import com.tys.util.MDateUtil;
import com.tys.util.MStrUtil;
import com.tys.util.MUtil;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;
import com.tys.web.utils.ResultInfo;
import com.tys.web.utils.SysUserHelper;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/sys")
public class EquipmentController {
	
	@Autowired
	private MdEquipmentService mdEquipmentService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private MdSchoolService mdSchoolService;
	
	@Autowired
	private RelSysuserLevelService relSysuserLevelService;
	
	/**
	 * 返回学校管理首页
	 * 
	 * @param request
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/equipmentStock", method = { RequestMethod.GET })
	public ModelAndView equipMentStock(HttpServletRequest request, SearchVo search) {
		ModelAndView mv = new ModelAndView("equipment/equipment_stock_list");
		return mv;
	}
	
	/**
	 * 返回学校管理首页
	 * 
	 * @param request
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/equipmentSales", method = { RequestMethod.GET })
	public ModelAndView equipMentSalesList(HttpServletRequest request, SearchVo search) {
		ModelAndView mv = new ModelAndView("equipment/equipment_sales_list");
		return mv;
	}
	
	
	/**
	 * 设备入库
	 * 
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/equipment/add", method = { RequestMethod.POST })
	public @ResponseBody ResultInfo add(MdEquipment model,HttpSession session) {
		ResultInfo resultInfo = new ResultInfo(true);
		try {
			if(MStrUtil.isNullOrEmpty(model.getImei())){
				resultInfo.addData("notice", "imei号不能为空!");
				return resultInfo;
			}
			
			if(mdEquipmentService.isExistsByImei(model.getImei())){
				resultInfo.addData("notice", "imei号已经存在,不能重复添加!");
				return resultInfo;
			}
			
			SysUser mduser = SysUserHelper.getSysUser(session);
			if(null != mduser){
				model.setCreateBy(mduser.getId());
			}
			this.mdEquipmentService.add(model);
			resultInfo.addData("result", "success");
			resultInfo.addData("notice", "新增设备成功!");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "新增设备成失败!");
			e.printStackTrace();
		}
		return resultInfo;
	}
	
	
	/**
	 * 分配经销商
	 * 
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/equipment/assignDealer", method = { RequestMethod.POST })
	public @ResponseBody ResultInfo assignDealer(MdEquipment model,HttpSession session) {
		ResultInfo resultInfo = new ResultInfo(true);
		try {
			if(MStrUtil.isNullOrEmpty(model.getId())){
				resultInfo.addData("notice", "设备不存在!");
				return resultInfo;
			}
			
			
			SysUser mduser = SysUserHelper.getSysUser(session);
			if(null != mduser){
				model.setUpdatedBy(mduser.getId());
			}
			
			SysUser user = this.sysUserService.findByPhone(model.getDealerName());
			if(null == user){
				resultInfo.addData("notice", "经销商不存在!");
				return resultInfo;
			}
			model.setDealerId(user.getId());
			this.mdEquipmentService.assign(model);
			resultInfo.addData("result", "success");
			resultInfo.addData("notice", "分配经销商成功!");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "分配经销商失败!");
			e.printStackTrace();
		}
		return resultInfo;
	}
	
	
	/**
	 * 分配学校
	 * 
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/equipment/assignSchool", method = { RequestMethod.POST })
	public @ResponseBody ResultInfo assignSchool(MdEquipment model,HttpSession session) {
		ResultInfo resultInfo = new ResultInfo(true);
		try {
			if(MStrUtil.isNullOrEmpty(model.getId())){
				resultInfo.addData("notice", "设备不存在!");
				return resultInfo;
			}
			
			MdSchool condition = new MdSchool();
			condition.setName(model.getSchoolName());
			MdSchool school = mdSchoolService.getByCondition(condition);
			if(null == school){
				resultInfo.addData("notice", "学校不存在!");
				return resultInfo;
			}
			model.setSchoolId(school.getId());
			
			SysUser mduser = SysUserHelper.getSysUser(session);
			if(null != mduser){
				model.setUpdatedBy(mduser.getId());
			}
			this.mdEquipmentService.assign(model);
			resultInfo.addData("result", "success");
			resultInfo.addData("notice", "分配学校成功!");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "分配学校失败!");
			e.printStackTrace();
		}
		return resultInfo;
	}
	
	
	/**
	 * 获取设备库存分页列表显示
	 * 
	 * @param response
	 * @param reqDto
	 * @param search
	 */
	@RequestMapping(value = "/equipmentStock/list")
	public void equipmentList(HttpServletResponse response,HttpSession session , @ModelAttribute DataTableReqDTO reqDto, @ModelAttribute SearchVo search) {
		SysUser user = SysUserHelper.getSysUser(session);
		if(null != user){
			search.setUserType(user.getUserType());
			search.setUserId(user.getId());
		}
		MPage<MdEquipment> page = mdEquipmentService.findEquipmetnStockPageByModel(search, reqDto.getPage(), reqDto.getLength());
		DataTableRspDTO<MdEquipment> rsp = new DataTableRspDTO<MdEquipment>();
		rsp.setDraw(reqDto.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}
	
	/**
	 * 获取已售设备分页列表显示
	 * 
	 * @param response
	 * @param reqDto
	 * @param search
	 */
	@RequestMapping(value = "/equipmentSales/list")
	public void equipmentSalesList(HttpServletResponse response, HttpSession session ,@ModelAttribute DataTableReqDTO reqDto, SearchVo search) {
		SysUser user = SysUserHelper.getSysUser(session);
		if(null != user){
			search.setUserType(user.getUserType());
			search.setUserId(user.getId());
		}
		MPage<MdEquipment> page = mdEquipmentService.findEquipmetnSalesPageByModel(search, reqDto.getPage(), reqDto.getLength());
		DataTableRspDTO<MdEquipment> rsp = new DataTableRspDTO<MdEquipment>();
		rsp.setDraw(reqDto.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}
	
	
	/**
	 * 设备删除
	 * 
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/equipment/del", method = { RequestMethod.POST })
	public @ResponseBody String del(MdEquipment model,HttpSession session) {
		try {
			MdEquipment entity = null;
			if(null != model.getId()){
				entity = this.mdEquipmentService.findById(model.getId());
			}
			//设备不存在
			if(null == entity){
				return "1";
			}
			
			//只有入库状态才能删除
			if(!Integer.valueOf(0).equals(entity.getState())){
				return "2";
			}
			
			this.mdEquipmentService.delete(entity);
			//成功
			return "200";
		} catch (BeansException e) {
			e.printStackTrace();
			//系统异常
			return "999";
		}
	}
	
	/**
	 * 设备返还
	 * 
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/equipment/rtn", method = { RequestMethod.POST })
	public @ResponseBody String rtn(MdEquipment model,HttpSession session) {
		try {
			
			SysUser user = SysUserHelper.getSysUser(session);
			//会话失效
			if(null== user){
				return "0";
			}
			
			
			MdEquipment entity = null;
			if(null != model.getId()){
				entity = this.mdEquipmentService.findById(model.getId());
			}
			
			//设备不存在
			if(null == entity){
				return "1";
			}
			
			//已售设备或者入库状态的设备不能返还
			if(!Integer.valueOf(1).equals(entity.getState()) &&  !Integer.valueOf(2).equals(entity.getState())){
				return "2";
			}
			
			if(Integer.valueOf(2).equals(entity.getState())){
				MdSchool school = mdSchoolService.findById(entity.getSchoolId());
				//学校不存在,无法返还
				if(null == school){
					return "3";
				}
				
				//无权限操作该数据
				if(!user.getId().equals(school.getAdminId())){
					return "4";
				}
				
				entity.setSchoolId(null);
				entity.setState(1);
				entity.setUpdatedBy(user.getId());
				entity.setUpdateTime(MDateUtil.getNowTime());
				this.mdEquipmentService.update(entity);
			}else if(Integer.valueOf(1).equals(entity.getState())){
				if(!user.getId().equals(entity.getDealerId())){
					//无权限操作该数据
					return "4";
				}
				
				RelSysuserLevel relSysuserLevel = this.relSysuserLevelService.getParentByUserId(entity.getId());
				if(null == relSysuserLevel){
					//无上级经销商
					return "5";
				}
				
				SysUser parentUser = sysUserService.findById(relSysuserLevel.getParentId());
				
				if(null == parentUser){
					//上级经销商不存在
					return "6";
				}
				
				entity.setSchoolId(null);
				entity.setDealerId(parentUser.getId());
				if(parentUser.getUserType().equals(1)){
					entity.setState(0);
				}
				entity.setUpdatedBy(user.getId());
				entity.setUpdateTime(MDateUtil.getNowTime());
				this.mdEquipmentService.update(entity);
			}
			return "200";
		} catch (BeansException e) {
			e.printStackTrace();
			return "999";
		}
		
	}
	

}
