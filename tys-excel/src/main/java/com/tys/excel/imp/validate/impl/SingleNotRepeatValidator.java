package com.tys.excel.imp.validate.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tys.excel.imp.validate.ExcelImportValidator;

/**
 * Excel导入校验器
 * 
 */
@Service("单列不重复校验器")
@Scope("prototype")
public class SingleNotRepeatValidator extends ExcelImportValidator<Object> {

    @Autowired
    private ApplicationContext appContext;

    @Override
    public void validate(Object dataLine, List<Object> readedLines, Map<String, Object> nextLineCellValues)
            throws Exception {
        for (int i = 0; i < beanPropertys.size(); i++) {
            String beanProperty = beanPropertys.get(i);
            boolean isRepeatInExcel = false;
            Object propertyValue = PropertyUtils.getProperty(dataLine, beanProperty);

            for (Object readLine : readedLines) {
                if (dataLine.equals(readLine)) {
                    continue;
                }

                if (propertyValue != null && PropertyUtils.getProperty(readLine, beanProperty) != null
                        && propertyValue.equals(PropertyUtils.getProperty(readLine, beanProperty))) {
                    isRepeatInExcel = true;
                    break;
                }
            }

            if (isRepeatInExcel) {
                addError(columnHeaders.get(i) + "重复或者在系统中已存在");
            } else {
                String bean, methodName;
                if (params.get(i).contains(".")) {
                    bean = params.get(i).split("[.]")[0];
                    methodName = params.get(i).split("[.]")[1];
                } else {
                    bean = defaultBean;
                    methodName = params.get(i);
                }

                try {
                    if ((Boolean) MethodUtils.invokeMethod(appContext.getBean(bean), methodName, dataLine)) {
                        addError(columnHeaders.get(i) + "重复或者在系统中已存在");
                    }
                } catch (Exception e) {
                	e.printStackTrace();
                    // 如果单列校验的底层方法未实现,这里会有异常,为不影响业务,暂不处理
                }
            }
        }
    }
}
