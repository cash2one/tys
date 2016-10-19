package com.tys.excel.converter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tys.entity.DArea;
import com.tys.excel.convert.ExcelConverter;
import com.tys.excel.inject.InjectNameBatchConverter;
import com.tys.excel.inject.InjectNameConverter;
import com.tys.service.MdAreaService;

@Service(CountyConverter.CONVERTER_NAME)
@Scope("prototype")
public class CountyConverter extends ExcelConverter<Long, String> implements InjectNameConverter<Long>,
        InjectNameBatchConverter<Long> {

    public static final String CONVERTER_NAME = "区/县";

    @Autowired
    private MdAreaService mdAreaService;

    @Override
    public Long getKey(String cellValue) throws Exception {
    	if(null == cellValue){
    		return null;
    	}
        try {
        	DArea countyVo = mdAreaService.findByName(cellValue);
            return countyVo != null ? Long.valueOf(countyVo.getCode()) : null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getName(Long propertyValue) throws Exception {
        try {
        	DArea countyVo = mdAreaService.findById(propertyValue);
            return countyVo != null ? countyVo.getName() : null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getName(Long id, String[] params) throws Exception {
        return getName(id);
    }

    @Override
    public Map<Long, String> getNames(List<Long> ids, String[] params) throws Exception {
        return null;
    }

}
