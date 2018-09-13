package org.leesia.datasource.dao.ds3;

import org.apache.ibatis.annotations.Param;
import org.leesia.datasource.entity.Nation;
import org.leesia.datasource.entity.NationCriteria;

import java.util.List;

public interface NationMapper {
    long countByExample(NationCriteria example);

    int deleteByExample(NationCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(Nation record);

    int insertSelective(Nation record);

    List<Nation> selectByExample(NationCriteria example);

    Nation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Nation record, @Param("example") NationCriteria example);

    int updateByExample(@Param("record") Nation record, @Param("example") NationCriteria example);

    int updateByPrimaryKeySelective(Nation record);

    int updateByPrimaryKey(Nation record);
}