package org.leesia.datasource.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.leesia.datasource.dao.ds2.CityMapper;
import org.leesia.datasource.service.CityService;
import org.leesia.datasource.entity.City;
import org.leesia.datasource.entity.CityCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: leesia
 * @Date: 2018/8/8 08:58
 * @Description:
 */
@Service("cityService")
public class CityServiceImpl implements CityService {

    private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityMapper mapper;

    @Override
    public List<City> get(Map<String, Object> params) {
        logger.info("查询城市：{}", params);
        CityCriteria criteria = createCriteria(params);
        return mapper.selectByExample(criteria);
    }

    @Override
    public City getByName(String cityName) {
        logger.info("查询城市：{}", cityName);
        Map<String, Object> params = new HashMap<>();
        params.put("cityName", cityName);
        CityCriteria criteria = createCriteria(params);
        List<City> cities = mapper.selectByExample(criteria);

        if (cities != null && !cities.isEmpty()) {
            return cities.get(0);
        }
        return null;
    }

    private CityCriteria createCriteria(Map<String, Object> params) {
        CityCriteria criteria = new CityCriteria();
        CityCriteria.Criteria c = criteria.createCriteria();

        String id = (String) params.get("id");
        if (StringUtils.isNotBlank(id)) {
            c.andIdEqualTo(id);
        }

        String cityName = (String) params.get("cityName");
        if (StringUtils.isNotBlank(cityName)) {
            c.andCityNameLike("%" + cityName + "%");
        }

        String province = (String) params.get("province");
        if (StringUtils.isNotBlank(province)) {
            c.andProvinceLike("%" + province + "%");
        }

        String provinceName = (String) params.get("provinceName");
        if (StringUtils.isNotBlank(provinceName)) {
            c.andProvinceNameLike("%" + provinceName + "%");
        }

        return criteria;
    }
}
