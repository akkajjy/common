package com.palwy.common.mapper;

import com.palwy.common.entity.LoanSuperLinkClickRecord;
import com.palwy.common.entity.LoanSuperLinkClickRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoanSuperLinkClickRecordMapper {
    long countByExample(LoanSuperLinkClickRecordExample example);

    int deleteByExample(LoanSuperLinkClickRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LoanSuperLinkClickRecord record);

    int insertSelective(LoanSuperLinkClickRecord record);

    List<LoanSuperLinkClickRecord> selectByExample(LoanSuperLinkClickRecordExample example);

    LoanSuperLinkClickRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LoanSuperLinkClickRecord record, @Param("example") LoanSuperLinkClickRecordExample example);

    int updateByExample(@Param("record") LoanSuperLinkClickRecord record, @Param("example") LoanSuperLinkClickRecordExample example);

    int updateByPrimaryKeySelective(LoanSuperLinkClickRecord record);

    int updateByPrimaryKey(LoanSuperLinkClickRecord record);
}