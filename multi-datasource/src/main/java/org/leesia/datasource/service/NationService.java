package org.leesia.datasource.service;

import org.leesia.datasource.entity.Nation;

import java.util.List;
import java.util.Map;

/**
 * @Auther: leesia
 * @Date: 2018/8/9 13:55
 * @Description:
 */
public interface NationService {

    List<Nation> get(Map<String, Object> params);

    Nation getByName(String nationName);
}
