package com.palwy.common.mapper;

import com.palwy.common.entity.LoanSuperConfig;
import com.palwy.common.entity.LoanSuperConfigExample;
import com.palwy.common.req.LoanSuperConfigVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoanSuperConfigMapper {
    long countByExample(LoanSuperConfigExample example);

    int deleteByExample(LoanSuperConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LoanSuperConfig record);

    int insertSelective(LoanSuperConfig record);

    List<LoanSuperConfig> selectByExample(LoanSuperConfigExample example);

    LoanSuperConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LoanSuperConfig record, @Param("example") LoanSuperConfigExample example);

    int updateByExample(@Param("record") LoanSuperConfig record, @Param("example") LoanSuperConfigExample example);

    int updateByPrimaryKeySelective(LoanSuperConfig record);

    int updateByPrimaryKey(LoanSuperConfig record);

    List<LoanSuperConfigVO> getList(LoanSuperConfigVO req);

}