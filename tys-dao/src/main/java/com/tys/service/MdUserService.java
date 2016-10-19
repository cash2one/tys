package com.tys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.dto.spi.req.ReqSettingDTO;
import com.tys.dto.spi.rsp.RspMdUserDTO;
import com.tys.entity.MdCourse;
import com.tys.entity.MdUser;
import com.tys.util.MStrUtil;
import com.tys.util.constants.Constants;
import com.tys.util.custom.BaseService;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;

@Service
@Repository
public class MdUserService extends BaseService<MdUser> {


	@Override
	protected String buildSqlFromSearchVo(SearchVo model) {
		StringBuilder builder = new StringBuilder(" model where model.isDeleted = 0 ");
		if (!MStrUtil.isNullOrEmpty(model.getS_name())) {
			builder.append(" and model.name like '%" + model.getS_name() + "%'");
		}
		builder.append(super.buildSqlFromSearchVo(model));
		return builder.toString();
	}

	public MPage<MdUser> findMdTeacherPageByModel(SearchVo search, int page, int rows) {
		try {
			search.setUserType(Constants.USER_TYPE_TEACHER);

			StringBuilder sqlStr = new StringBuilder("from MdUser ");
			String wherecase = buildSqlFromSearchVo(search).toString();
			sqlStr.append(wherecase);
			sqlStr.append(" order by id");
			return findPageEx(sqlStr.toString(), page, rows, wherecase, MdUser.class);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public List<MdUser> selectMdUserListFromModel(SearchVo search) {
		try {
			StringBuilder builder = new StringBuilder("from MdUser ");
			builder.append(buildSqlFromSearchVo(search));
			builder.append(" order by createTime desc");

			List<MdUser> teachers = findList(builder.toString());
			return teachers;
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public List<MdUser> getSysUserByType(MdUser user) {
		String sql = " from MdUser model where model.isDeleted=0";

		return findList(sql, MdUser.class);
	}

	public List<MdUser> getSysUserByType(int userType) {
		String sql = " from MdUser t where t.isDeleted = 0 and t.type=" + userType;
		return findList(sql, MdUser.class);
	}

	/**
	 * 根据用户类型（type）获取用户信息列表（带分页）
	 * 
	 * @param type
	 *            用户类型
	 * @param page
	 *            起始页
	 * @param rows
	 *            行数
	 * @return page
	 */
	public MPage<MdUser> getUserByType(int type, int page, int rows) {
		String wherecase = " t where t.isDeleted = 0 and t.type=" + type;
		String sql = "select t from MdUser" + wherecase + " order by t.id";
		return findPageEx(sql, page, rows, wherecase, MdUser.class);
	}

	/**
	 * 账号登录允许手机登录与自定义账号登录
	 * 
	 * @param loginName
	 * @return
	 */
	public String findPwByAcct(String loginName) {
		String sql = "select t.pw from MdUser t where t.isDeleted = 0 and (t.custAcct='" + loginName + "' or t.phone='" + loginName + "')";
		return findUnique(sql, String.class);
	}

	/**
	 * 账号登录允许手机登录与自定义账号登录
	 * 
	 * @param loginName
	 * @return
	 */
	public MdUser findByAcct(String loginName) {
		String sql = " from MdUser t where t.isDeleted = 0 and (t.custAcct='" + loginName + "' or t.phone='" + loginName + "')";
		return findUnique(sql, MdUser.class);
	}

	/**
	 * 根据用户名统计
	 * 
	 * @param account
	 * @return
	 */
	public Long countByAccount(String account) {
		String sql = "select count(t.id) from MdUser t where t.custAcct='" + account + "' and isDeleted = 0";
		return this.findCount(sql);
	}

	/**
	 * 根据用户名判断用户是否存在
	 * 
	 * @param account
	 * @return
	 */
	public boolean isExistsByAccount(String account) {
		return countByAccount(account) > 0 ? true : false;
	}

	/**
	 * APP登录后修改手机imei和版本
	 * 
	 * @param mdUser
	 */
	@Transactional
	public void updateAfterLogin(MdUser mdUser) {

		String sql = "update MdUser set version = '" + mdUser.getVersion() + "' , imei = '" + mdUser.getImei() + "' where id = " + mdUser.getId();
		this.executeUpdate(sql);
	}

	public RspMdUserDTO findByUserId(Integer userId) {
		MdUser mdUser = this.findById(userId);
		if (null == mdUser) {
			return null;
		}

		RspMdUserDTO rspMdUserDTO = new RspMdUserDTO();
		List<MdCourse> courseList = new ArrayList<MdCourse>();
		rspMdUserDTO.setCourseList(courseList);
		rspMdUserDTO.setName(mdUser.getName());
		rspMdUserDTO.setSex(String.valueOf(mdUser.getSex()));
		rspMdUserDTO.setCustAcct(mdUser.getCustAcct());
		rspMdUserDTO.setCityCode(mdUser.getCityCode());
		rspMdUserDTO.setBirthday(mdUser.getBirthday());
		return rspMdUserDTO;
	}

	@Transactional
	public int update(int accId, ReqSettingDTO settingDTO) {
		StringBuilder sql = new StringBuilder();
		sql.append("update MdUser set isDeleted=0");
		if (settingDTO.getName() != null) {
			sql.append(",").append("name='" + settingDTO.getName()).append("'");
		}
		if (settingDTO.getBirthday() != null) {
			sql.append(",").append("birthday='").append(settingDTO.getBirthday()).append("'");
		}
		if (settingDTO.getCityCode() != null) {
			sql.append(",").append("cityCode=" + settingDTO.getCityCode());
		}
		if (settingDTO.getCustAcct() != null) {
			sql.append(",").append("custAcct='" + settingDTO.getCustAcct()).append("'");
		}
		if (settingDTO.getImgId() != null) {
			sql.append(",").append("imgId=" + settingDTO.getImgId());
		}
		if (settingDTO.getSex() != null) {
			sql.append(",").append("sex=" + settingDTO.getSex());
		}

		sql.append(" where id=" + accId);
		return this.executeUpdate(sql.toString());
	}

	public Integer findIdByPhone(String phone) {
		String sql = "select t.id from MdUser t where t.isDeleted = 0 and t.phone='" + phone + "'";
		return findUnique(sql, Integer.class);
	}

	public List<MdUser> getParentsByClassId(int userType, int classId) {
		String sql = "from MdUser model where model.isDeleted = 0 and model.type=" + userType
				+ " and model.id in (select rus.userId from RelUserStudent rus,MdStudent stu where rus.studentId = stu.id and stu.classId = "
				+ classId + ")";
		return findList(sql);
	}

	public List<MdUser> getTeachersByClassId(int userType, int classId) {
		String sql = "from MdUser model where model.isDeleted = 0 and model.type=" + userType
				+ " and model.id in (select rts.techerId from RelTecherClass rts where rts.classId = " + classId + ")";
		return findList(sql);
	}

	/**
	 * 把学校直属的老师账号改为家长，并把学校id置空
	 * @param schoolId
	 * @return
	 */
	@Transactional
	public int updateTeacherBySchoolId(Integer schoolId) {
		String sql = "update MdUser set type=0,schoolId=NULL where schoolId="+schoolId;
		return executeUpdate(sql);
	}

}
