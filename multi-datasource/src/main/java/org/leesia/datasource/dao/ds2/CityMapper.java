package org.leesia.datasource.dao.ds2;

import org.apache.ibatis.annotations.Param;
import org.leesia.datasource.entity.City;
import org.leesia.datasource.entity.CityCriteria;

import java.util.List;

/**
 * @Auther: leesia
 * @Date: 2018/9/12 15:19
 * @Description:
 */
public interface CityMapper {

    long countByExample(CityCriteria example);

    int deleteByExample(CityCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(City record);

    int insertSelective(City record);

    List<City> selectByExample(CityCriteria example);

    City selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") City record, @Param("example") CityCriteria example);

    int updateByExample(@Param("record") City record, @Param("example") CityCriteria example);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}
