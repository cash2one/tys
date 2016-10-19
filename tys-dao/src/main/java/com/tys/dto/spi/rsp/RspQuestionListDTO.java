package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.slim.Question;
import com.tys.entity.slim.QuestionUpdate;

public class RspQuestionListDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private List<Question> list;
	
	private List<QuestionUpdate> updateList;
	
	private Integer leftCount;

	public List<Question> getList() {
		return list;
	}

	public void setList(List<Question> list) {
		this.list = list;
	}

	public List<QuestionUpdate> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(List<QuestionUpdate> updateList) {
		this.updateList = updateList;
	}

	public Integer getLeftCount() {
		return leftCount;
	}

	public void setLeftCount(Integer leftCount) {
		this.leftCount = leftCount;
	}


}
