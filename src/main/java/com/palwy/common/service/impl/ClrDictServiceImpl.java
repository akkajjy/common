package com.palwy.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.palwy.common.entity.ClrDictDO;
import com.palwy.common.mapper.ClrDictDOMapper;
import com.palwy.common.service.ClrDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lihaonan
 * @create: 2025/6/17 13:57
 * 描述：
 **/
@Slf4j
@Service
public class ClrDictServiceImpl implements ClrDictService {

    @Autowired
    private ClrDictDOMapper clrDictDOMapper;

    @Override
    public int addClrDict(ClrDictDO clrDictDO) {
        return 0;
    }

    @Override
    public int updateClrDict(ClrDictDO clrDictDO) {
        return 0;
    }

    @Override
    public List<ClrDictDO> getClrDictListByDictType(String dictType) {
        log.info("==========进入getClrDictListByDictType接口业务逻辑层==========");
        log.info("getClrDictListByDictType请求参数:dictType:{}",dictType);
        if(null == dictType || "".equals(dictType)){
            return null;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("dictType", dictType);
        params.put("status", "1");
        List<ClrDictDO> clrDictDOList = clrDictDOMapper.selectList(params);
        log.info("getClrDictListByDictType返回参数:{}", JSON.toJSONString(clrDictDOList));
        return clrDictDOList;
    }

    @Override
    public List<ClrDictDO> getClrChannel() {
        log.info("==========进入getClrChannel接口业务逻辑层==========");


        Map<String, Object> params = new HashMap<>();
        params.put("dictType", "ChannelCodeEnum");
        params.put("status", "1");
        List<ClrDictDO> clrDictDOList = clrDictDOMapper.selectList(params);
        log.info("getClrDictListByDictType返回参数:{}", JSON.toJSONString(clrDictDOList));
        return clrDictDOList;
    }
}
