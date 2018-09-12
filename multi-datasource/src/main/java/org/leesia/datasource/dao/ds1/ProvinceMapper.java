package org.leesia.datasource.dao.ds1;

import org.apache.ibatis.annotations.Param;
import org.leesia.datasource.entity.Province;
import org.leesia.datasource.entity.ProvinceCriteria;

import java.util.List;

/**
 * @Auther: leesia
 * @Date: 2018/9/12 15:19
 * @Description:
 */
public interface ProvinceMapper {

    long countByExample(ProvinceCriteria example);

    int deleteByExample(ProvinceCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(Province record);

    int insertSelective(Province record);

    List<Province> selectByExample(ProvinceCriteria example);

    Province selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Province record, @Param("example") ProvinceCriteria example);

    int updateByExample(@Param("record") Province record, @Param("example") ProvinceCriteria example);

    int updateByPrimaryKeySelective(Province record);

    int updateByPrimaryKey(Province record);
}
