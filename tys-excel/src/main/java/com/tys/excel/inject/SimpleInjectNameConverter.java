package com.tys.excel.inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service(SimpleInjectNameConverter.CONVERTER_NAME)
@Scope("prototype")
public class SimpleInjectNameConverter implements InjectNameBatchConverter<Integer> {

    public static final String CONVERTER_NAME = "simpleInjectNameConverter";

    @Override
    public Map<Integer, String> getNames(List<Integer> ids, String[] params) throws Exception {
        Map<Integer, String> map = new HashMap<Integer, String>();

        for (String param : params) {
            String[] s = StringUtils.split(param, ":");

            map.put(Integer.parseInt(s[0]), s[1]);
        }

        return map;
    }
}
