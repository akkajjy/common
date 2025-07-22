package com.palwy.common.mapper;

import com.palwy.common.entity.ClrDictDO;

import java.util.List;
import java.util.Map;

/**
 * @author: lihaonan
 * @create: 2025/6/17 13:38
 * 描述：
 **/
public interface ClrDictDOMapper {

    public int insert(ClrDictDO clrDictDO);

    public int update(ClrDictDO clrDictDO);

    public ClrDictDO select(Map<String, Object> map);

    public List<ClrDictDO> selectList(Map<String, Object> map);
}
