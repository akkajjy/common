package com.palwy.common.service;

import com.palwy.common.entity.ClrDictDO;

import java.util.List;

/**
 * @author: lihaonan
 * @create: 2025/6/17 13:54
 * 描述：
 **/
public interface ClrDictService {

    public int addClrDict(ClrDictDO clrDictDO);

    public int updateClrDict(ClrDictDO clrDictDO);

    /**
     * 根据字典类型查询字典信息
     * @param dictType
     * @return
     */
    public List<ClrDictDO> getClrDictListByDictType(String dictType);

    List<ClrDictDO> getClrChannel();
}
