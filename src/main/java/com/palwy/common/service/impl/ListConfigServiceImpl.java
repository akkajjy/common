package com.palwy.common.service.impl;


import com.palwy.common.entity.ListConfig;
import com.palwy.common.entity.ListConfigReq;
import com.palwy.common.mapper.ListConfigMapper;
import com.palwy.common.service.ListConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ListConfigServiceImpl implements ListConfigService {

    @Autowired
    private ListConfigMapper listConfigMapper;

    @Override
    public ListConfig selectByCondition(ListConfigReq req) {

        ListConfig listConfig = new ListConfig();

        BeanUtils.copyProperties(req,listConfig);
        return listConfigMapper.selectByCondition(listConfig);
    }
}