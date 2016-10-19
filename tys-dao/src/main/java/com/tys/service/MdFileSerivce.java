package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.MdFile;
import com.tys.util.custom.BaseService;
import com.tys.util.custom.MPage;

/**
 * 
 * @author wjc 2016-4-2 09:52:51
 *
 */
@Service
@Repository
public class MdFileSerivce extends BaseService<MdFile> {
	public MPage<MdFile> findFileList(int mPageNo, int mPageSize, String name, int type) {
		String whereCase = " t where t.isDeleted = 0";
		if (!"".equals(name) && name != null) {
			whereCase += " and t.name like '%" + name + "%'";
		}
		if (type != -1) {
			whereCase += " and t.type = " + type;
		}
		String sql = " from MdFile" + whereCase;
		return findPageEx(sql, mPageNo, mPageSize, whereCase);
	}

	public MdFile getFileByName(String name) {
		String sql = "select t from MdFile t where t.isDeleted=0 and t.url like '%"+name+"%'";
		return findUnique(sql);
	}

	@Transactional
	public void removeFile(int id) {
		MdFile file = this.findById(id);
		file.setIsDeleted(1);
		this.update(file);
	}
}
