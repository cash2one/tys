package com.tys.excel.converter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tys.entity.DCity;
import com.tys.excel.convert.ExcelConverter;
import com.tys.excel.inject.InjectNameBatchConverter;
import com.tys.excel.inject.InjectNameConverter;
import com.tys.service.MdCityService;

@Service(CityConverter.CONVERTER_NAME)
@Scope("prototype")
public class CityConverter extends ExcelConverter<Long, String> implements InjectNameConverter<Long>,
    InjectNameBatchConverter<Long> {

    public static final String CONVERTER_NAME = "市";

    @Autowired
    private MdCityService mdCityService;

    @Override
    public Long getKey(String cellValue) throws Exception {
    	if(null == cellValue){
    		return null;
    	}
        try {
            DCity cityVo = mdCityService.findByName(cellValue);
            return cityVo != null ? Long.valueOf(cityVo.getCode()) : null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getName(Long propertyValue) throws Exception {
        try {
        	DCity cityVo = mdCityService.findById(propertyValue);
            return cityVo != null ? cityVo.getName() : null;
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
