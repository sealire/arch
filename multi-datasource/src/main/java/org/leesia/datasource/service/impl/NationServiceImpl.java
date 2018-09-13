package org.leesia.datasource.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.leesia.datasource.dao.ds3.NationMapper;
import org.leesia.datasource.entity.Nation;
import org.leesia.datasource.entity.NationCriteria;
import org.leesia.datasource.service.NationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: leesia
 * @Date: 2018/8/9 14:29
 * @Description:
 */
@Service("nationService")
public class NationServiceImpl implements NationService {

    private static final Logger logger = LoggerFactory.getLogger(NationServiceImpl.class);

    @Autowired
    private NationMapper mapper;

    @Override
    public List<Nation> get(Map<String, Object> params) {
        logger.info("查询民族：{}", params);
        NationCriteria criteria = createCriteria(params);
        return mapper.selectByExample(criteria);
    }

    @Override
    public Nation getByName(String nationName) {
        logger.info("查询民族：{}", nationName);
        Map<String, Object> params = new HashMap<>();
        params.put("nationName", nationName);
        NationCriteria criteria = createCriteria(params);
        List<Nation> nations = mapper.selectByExample(criteria);

        if (nations != null && !nations.isEmpty()) {
            return nations.get(0);
        }
        return null;
    }

    private NationCriteria createCriteria(Map<String, Object> params) {
        NationCriteria criteria = new NationCriteria();
        NationCriteria.Criteria c = criteria.createCriteria();

        String id = (String) params.get("id");
        if (StringUtils.isNotBlank(id)) {
            c.andIdEqualTo(id);
        }

        String nationName = (String) params.get("nationName");
        if (StringUtils.isNotBlank(nationName)) {
            c.andNationNameLike("%" + nationName + "%");
        }

        return criteria;
    }
}
