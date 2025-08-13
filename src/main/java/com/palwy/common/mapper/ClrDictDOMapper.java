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

     int insert(ClrDictDO clrDictDO);

     int update(ClrDictDO clrDictDO);

     ClrDictDO select(Map<String, Object> map);

     List<ClrDictDO> selectList(Map<String, Object> map);
}
