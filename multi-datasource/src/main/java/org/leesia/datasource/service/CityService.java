package org.leesia.datasource.service;

import org.leesia.datasource.entity.City;

import java.util.List;
import java.util.Map;

/**
 * @Auther: leesia
 * @Date: 2018/8/8 08:58
 * @Description:
 */
public interface CityService {
    List<City> get(Map<String, Object> params);

    City getByName(String cityName);
}
