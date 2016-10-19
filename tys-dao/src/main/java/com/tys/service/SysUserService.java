package com.tys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.SysUser;
import com.tys.entity.slim.Agent;
import com.tys.entity.slim.Employee;
import com.tys.util.MCollectionUtils;
import com.tys.util.custom.BaseService;
import com.tys.util.custom.MPage;

@Service
@Repository
public class SysUserService extends BaseService<SysUser> {

	@Resource
	RelSysuserAreaService userAreaService;

	public String findPwByAcct(String loginName) {
		String sql = "select t.password from SysUser t where t.loginName='" + loginName + "'";
		return findUnique(sql, String.class);
	}

	public MPage<Employee> findPageByType(int type, int page, int rows) {
		String wherecase = "t where t.isDeleted = 0 and t.userType=" + type;
		String sql = "select t from com.tys.entity.slim.Employee " + wherecase + " order by t.id";
		return findPageEx(sql, page, rows, wherecase, Employee.class);
	}

	public List<SysUser> findDealerListByAreaCode(Integer areaCode) {
		String sql = "from SysUser t where t.areaCode = " + areaCode + " and t.isDeleted = 0 and t.userType = 2 order by t.id";
		return this.findList(sql);
	}
	
	public SysUser findByPhone(String phone){
		String sql = "from SysUser t where t.phone = '" + phone + "' and t.isDeleted = 0 and t.userType = 2";
		List<SysUser> list =  this.findList(sql);
		if(MCollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
		
	}

	public SysUser findByAcct(String loginName) {
		String sql = " from SysUser t where t.isDeleted = 0 and t.loginName='" + loginName + "'";
		return findUnique(sql, SysUser.class);
	}

	public List<SysUser> findByName(String name) {
		String sql = " from SysUser t where t.isDeleted = 0 and t.name like'%" + name + "%'";
		return findList(sql, SysUser.class);
	}

	public MPage<Agent> findAgentByType(SysUser user, int page, int rows) {
		String wherecase = " t where t.userType = 2 and t.isDeleted = 0";
		// 这里要加入判断 超级管理员和总公司能看到全国所有的代理商 而一般的省市区代理商只能看到自己对应的代理商信息
		if (user.getUserType() <= 1) {
			// 证明是超级管理员或者总公司
			wherecase += " and 1 = 1";
		} else {
			wherecase += " and t.id in (select m.sysUserId from RelSysuserLevel m where m.parentId =" + user.getId()
					+ ")";
		}
		String sql = " from Agent " + wherecase+" order by t.createTime";
		return findPageEx(sql, page, rows, wherecase, Agent.class);
	}

	public MPage<Agent> searchAgent(int page, int rows, String name, int provinceCode, int cityCode, int areaCode) {
		String wherecase = "t where t.userType=2";
		wherecase += " and t.isDeleted = 0";
		if (!"".equals(name)) {
			wherecase += " and t.name like'%" + name + "%'";
		}
		if (provinceCode != 0) {
			wherecase += " and t.id in (select m.agentId from RelSysuserArea m where m.isDeleted=0 and m.provinceCode="+provinceCode+")";
		}
		if (cityCode != 0) {
			wherecase=wherecase.substring(0, wherecase.length()-1);
			wherecase += " and m.cityCode=" + cityCode+")";
		}
		if (areaCode != 0) {
			wherecase=wherecase.substring(0, wherecase.length()-1);
			wherecase += " and m.areaCode=" + areaCode+")";
		}
		String sql = "select t from com.tys.entity.slim.Agent " + wherecase + " order by t.createTime";
		return findPageEx(sql, page, rows, wherecase, Agent.class);
	}

	public MPage<Employee> searchEmployee(int page, int rows, String name, String ids) {
		String wherecase = "t where t.userType=4";
		wherecase += " and t.isDeleted = 0";
		if (!"".equals(name)) {
			wherecase += " and t.name like'%" + name + "%'";
		}
		if (!"".equals(ids)) {
			wherecase += " and t.createBy in (" + ids + ")";
		}
		String sql = "select t from com.tys.entity.slim.Employee " + wherecase + " order by t.id";
		return findPageEx(sql, page, rows, wherecase, Employee.class);
	}

	@Transactional
	public void deleteUser(int userId) {
		SysUser user = this.findById(userId);
		user.setIsDeleted(1);
		this.update(user);
	}

	public Map<Integer, String> findMapByIds(Set<Integer> ids) {
		Map<Integer, String> resultMap = new HashMap<Integer, String>();
		if (MCollectionUtils.isEmpty(ids)) {
			return resultMap;
		}
		StringBuilder sql = new StringBuilder("from SysUser a where a.isDeleted = 0 and a.id in( ");
		Integer pos = 0;
		for (Integer id : ids) {
			if (pos == ids.size() - 1) {
				sql.append(id);
			} else {
				sql.append(id + ",");
			}
			pos++;
		}
		sql.append(")");
		List<SysUser> result = this.findList(sql.toString());

		if (MCollectionUtils.isNotEmpty(result)) {
			for (SysUser entity : result) {
				resultMap.put(entity.getId(), entity.getName());
			}
		}
		return resultMap;
	}

	public SysUser findByCondition(SysUser condition) {
		StringBuilder hql = new StringBuilder("from SysUser a where a.isDeleted = 0");
		if (condition.getProvinceCode() != null) {
			hql.append(" and a.provinceCode = " + condition.getProvinceCode());
		}
		if (condition.getCityCode() != null) {
			hql.append(" and a.cityCode = " + condition.getCityCode());
		}

		if (condition.getAreaCode() != null) {
			hql.append(" and a.areaCode = " + condition.getAreaCode());
		}
		hql.append(" and a.name = '" + condition.getName() + "'");

		List<SysUser> list = this.findList(hql.toString());
		if (MCollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

}
