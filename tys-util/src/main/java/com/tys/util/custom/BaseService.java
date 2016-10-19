package com.tys.util.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.tys.util.MStrUtil;
import com.tys.util.constants.Constants;

@SuppressWarnings("unchecked")
public class BaseService<T> {

	@SuppressWarnings("rawtypes")
	private final Class mclass = ((Class) ((java.lang.reflect.ParameterizedType) super.getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0]);

	@PersistenceContext
	protected EntityManager em;

	@Transactional
	public void save(T b) {
		em.persist(b);
	}

	@Transactional
	public void delete(T b) {
		if (!em.contains(b)) {
			b = em.merge(b);
		}
		em.remove(b);
	}

	@Transactional
	public void update(T b) {
		if (!em.contains(b)) {
			em.merge(b);
		}
		em.flush();
	}

	@Transactional
	public int executeUpdate(String sql) {
		return em.createQuery(sql).executeUpdate();
	}

	/**
	 * 注意效率损失，此方法会立即获取实体中所有关联实体
	 * 
	 * @param primaryKey
	 * @return
	 */
	public T findById(Object primaryKey) {
		return (T) findById(primaryKey, mclass);
	}

	public <E> E findById(Object primaryKey, Class<E> c) {
		return em.find(c, primaryKey);
	}

	public T findUnique(String sql) {
		return (T) findUnique(sql, mclass);
	}

	public <E> E findUnique(String sql, Class<E> c) {
		Query query = em.createQuery(sql, c);
		query.setMaxResults(1);
		try {
			return (E) query.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<T> findAll() {
		String sql = "SELECT b FROM " + mclass.getSimpleName() + " b";
		return findList(sql);
	}

	public long findCount(String sql) {
		Query query = em.createQuery(sql);
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<T> findList(String sql) {
		return findListEx(sql, 0, 10000);
	}

	public <E> List<E> findList(String sql, Class<E> c) {
		return findListEx(sql, 0, 10000, c);
	}

	@Transactional
	public List<T> findListEx(String sql, int startPosition, int maxResults) {
		return findListEx(sql, startPosition, maxResults, mclass);
	}

	public <E> List<E> findListEx(String sql, int startPosition, int maxResults, Class<E> c) {
		Query query = em.createQuery(sql, c);
		query.setMaxResults(maxResults);
		query.setFirstResult(startPosition);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param sql
	 * @param mPageNo
	 *            从1开始
	 * @param mPageSize
	 * @return
	 */
	public MPage<T> findPage(String sql, int mPageNo, int mPageSize) {
		return findPageEx(sql, mPageNo, mPageSize, "");
	}

	/**
	 * @param sql
	 * @param mPageNo
	 *            从1开始
	 * @param mPageSize
	 * @param c
	 * @return
	 */
	public <E> MPage<E> findPage(String sql, int mPageNo, int mPageSize, Class<E> c) {
		return findPageEx(sql, mPageNo, mPageSize, "", c);
	}

	/**
	 * @param sql
	 * @param mPageNo
	 *            从1开始
	 * @param mPageSize
	 * @param whereCase
	 * @return
	 */
	public MPage<T> findPageEx(String sql, int mPageNo, int mPageSize, String whereCase) {
		return findPageEx(sql, mPageNo, mPageSize, whereCase, mclass);
	}

	/**
	 * @param sql
	 * @param mPageNo
	 *            从1开始
	 * @param mPageSize
	 * @param whereCase
	 *            格式 "t where t.xx=xx";
	 * @param c
	 *            指定返回类型
	 * @return
	 */
	public <E> MPage<E> findPageEx(String sql, int mPageNo, int mPageSize, String whereCase, Class<E> c) {
		MPage<E> mPage = new MPage<E>();
		String countSql = "select count(*) from " + c.getSimpleName() + " " + whereCase;
		long count = findCount(countSql);
		mPage.setTotalCount((int) count);
		mPage.setPageNo(mPageNo);
		mPage.setPageSize(mPageSize);

		if (count > 0) {
			List<E> list = findListEx(sql, (mPageNo - 1) * mPageSize, mPageSize, c);
			mPage.setResult(list);
		}

		return mPage;
	}

	/**
	 * 若该对象是查询类的属性，可使用下面方法；否则请在查询类中重写，并将值置空
	 * 
	 * @param model
	 * @return
	 */
	protected String buildSqlFromSearchVo(SearchVo model) {
		StringBuilder builder = new StringBuilder();
		// 处理代理商查看权限
		if (!MStrUtil.isNullOrEmpty(model.getAgentId())) {
			builder.append(" and model.createBy = " + model.getAgentId());
			model.setAgentId(null);
		}

		// 处理查询时地域信息来源主体，默认查询自己的
		if (!MStrUtil.isNullOrEmpty(model.getAddrCodeMind())) {
			if (Constants.ADDRESS_CODE_MIND_SCHOOL.equals(model.getAddrCodeMind())) {
				if (!MStrUtil.isNullOrEmpty(model.getS_province())) {
					builder.append(" and model.mdSchool.provinceId= " + model.getS_province());
				}
				if (!MStrUtil.isNullOrEmpty(model.getS_city())) {
					builder.append(" and model.mdSchool.cityId= " + model.getS_city());
				}
				if (!MStrUtil.isNullOrEmpty(model.getS_area())) {
					builder.append(" and model.mdSchool.areaId= " + model.getS_area());
				}
			} else if (Constants.ADDRESS_CODE_MIND_CLASS.equals(model.getAddrCodeMind())) {
				if (!MStrUtil.isNullOrEmpty(model.getS_province())) {
					builder.append(" and model.mdClass.mdSchool.provinceId= " + model.getS_province());
				}
				if (!MStrUtil.isNullOrEmpty(model.getS_city())) {
					builder.append(" and model.mdClass.mdSchool.cityId= " + model.getS_city());
				}
				if (!MStrUtil.isNullOrEmpty(model.getS_area())) {
					builder.append(" and model.mdClass.mdSchool.areaId= " + model.getS_area());
				}
			}
		} else {
			if (!MStrUtil.isNullOrEmpty(model.getS_province())) {
				builder.append(" and model.provinceId= " + model.getS_province());
			}
			if (!MStrUtil.isNullOrEmpty(model.getS_city())) {
				builder.append(" and model.cityId= " + model.getS_city());
			}
			if (!MStrUtil.isNullOrEmpty(model.getS_area())) {
				builder.append(" and model.areaId= " + model.getS_area());
			}
		}

		if (!MStrUtil.isNullOrEmpty(model.getUserType())) {
			builder.append(" and model.type  = " + model.getUserType());
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_address())) {
			builder.append(" and model.address like '%" + model.getS_address() + "%'");
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_phone())) {
			builder.append(" and model.phone like '%" + model.getS_phone() + "%'");
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_custAcct())) {
			builder.append(" and model.custAcct like '%" + model.getS_custAcct() + "%'");
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_isRegister())) {
			builder.append(" and model.isRegister like '%" + model.getS_isRegister() + "%'");
		}

		/* 若该对象是查询类的属性，可使用下面方法；否则请在查询类中重写，并将值置空 */
		if (!MStrUtil.isNullOrEmpty(model.getS_schoolId())) {
			builder.append(" and model.mdSchool.id = " + model.getS_schoolId());
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_schoolAdminId())) {
			builder.append(" and model.mdSchool.adminId = " + model.getS_schoolAdminId());
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_schoolName())) {
			builder.append(" and model.mdSchool.name like '%" + model.getS_schoolName() + "%'");
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_teacherName())) {
			builder.append(" and model.mdUser.type=1 and model.mdUser.name like '%" + model.getS_teacherName() + "%'");
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_parentName())) {
			builder.append(" and model.mdUser.type=0 and model.mdUser.name like '%" + model.getS_parentName() + "%'");
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_parentPhone())) {
			builder.append(" and model.mdUser.type=0 and model.mdUser.phone like '%" + model.getS_parentPhone() + "%'");
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_studentName())) {
			builder.append(" and model.mdStudent.name like '%" + model.getS_studentName() + "%'");
		}
		return builder.toString();
	}
}
