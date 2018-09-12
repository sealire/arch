package org.leesia.datasource.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.leesia.datasource.dao.ds1.ProvinceMapper;
import org.leesia.datasource.service.ProvinceService;
import org.leesia.datasource.entity.Province;
import org.leesia.datasource.entity.ProvinceCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: leesia
 * @Date: 2018/8/8 09:02
 * @Description:
 */
@Service("provinceService")
public class ProvinceServiceImpl implements ProvinceService {

    private static final Logger logger = LoggerFactory.getLogger(ProvinceServiceImpl.class);

    @Autowired
    private ProvinceMapper mapper;

    @Override
    public List<Province> get(Map<String, Object> params) {
        logger.info("查询省份：{}", params);
        ProvinceCriteria criteria = createCriteria(params);
        return mapper.selectByExample(criteria);
    }

    @Override
    public Province getByName(String provinceName) {
        logger.info("查询省份：{}", provinceName);
        Map<String, Object> params = new HashMap<>();
        params.put("provinceName", provinceName);
        ProvinceCriteria criteria = createCriteria(params);
        List<Province> provinces = mapper.selectByExample(criteria);

        if (provinces != null && !provinces.isEmpty()) {
            return provinces.get(0);
        }
        return null;
    }

    private ProvinceCriteria createCriteria(Map<String, Object> params) {
        ProvinceCriteria criteria = new ProvinceCriteria();
        ProvinceCriteria.Criteria c = criteria.createCriteria();

        String id = (String) params.get("id");
        if (StringUtils.isNotBlank(id)) {
            c.andIdEqualTo(id);
        }

        String provinceName = (String) params.get("provinceName");
        if (StringUtils.isNotBlank(provinceName)) {
            c.andProvinceNameLike("%" + provinceName + "%");
        }

        return criteria;
    }
}
