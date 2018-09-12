package org.leesia.datasource.service;

import org.leesia.datasource.entity.Province;

import java.util.List;
import java.util.Map;

/**
 * @Auther: leesia
 * @Date: 2018/8/8 09:02
 * @Description:
 */
public interface ProvinceService {
    List<Province> get(Map<String, Object> params);

    Province getByName(String provinceName);
}
