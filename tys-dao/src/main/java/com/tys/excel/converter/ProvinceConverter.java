package com.tys.excel.converter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tys.entity.DProvince;
import com.tys.excel.convert.ExcelConverter;
import com.tys.excel.inject.InjectNameBatchConverter;
import com.tys.excel.inject.InjectNameConverter;
import com.tys.service.MdProvinceService;

@Service(ProvinceConverter.CONVERTER_NAME)
@Scope("prototype")
public class ProvinceConverter extends ExcelConverter<Long, String> implements InjectNameConverter<Long>,
    InjectNameBatchConverter<Long> {

    public static final String CONVERTER_NAME = "省";

    @Autowired
    private MdProvinceService mdProvinceService;

    @Override
    public Long getKey(String cellValue) throws Exception {
    	if(null == cellValue){
    		return null;
    	}
        try {
        	DProvince provinceVo = mdProvinceService.findByName(cellValue);
            return provinceVo != null ? Long.valueOf(provinceVo.getCode()) : null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getName(Long propertyValue) throws Exception {
        try {
        	DProvince provinceVo = mdProvinceService.findById(propertyValue);
            return provinceVo != null ? provinceVo.getName() : null;
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
