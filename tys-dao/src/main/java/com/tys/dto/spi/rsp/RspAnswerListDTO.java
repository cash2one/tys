package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.InfoQuestionAnswer;

public class RspAnswerListDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;


	private List<InfoQuestionAnswer> list;


	public List<InfoQuestionAnswer> getList() {
		return list;
	}

	public void setList(List<InfoQuestionAnswer> list) {
		this.list = list;
	}
	

}
