package com.palwy.common.service.impl;


import com.palwy.common.entity.ListConfig;
import com.palwy.common.entity.ListConfigReq;
import com.palwy.common.mapper.ListConfigMapper;
import com.palwy.common.service.ListConfigService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
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
    public ResultVO<ListConfig> selectByCondition(ListConfigReq req) {

        ListConfig listConfig = new ListConfig();

        BeanUtils.copyProperties(req,listConfig);
        ListConfig listConfig1=listConfigMapper.selectByCondition(listConfig);
        log.info("listConfig1={}", listConfig1);
        if(listConfig1!=null){
            return ResultVOUtil.success("查询成功",listConfig1);

        }
        return ResultVOUtil.fail("查询失败");

    }
}