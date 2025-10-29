package com.palwy.common.service;


import com.palwy.common.entity.ListConfig;
import com.palwy.common.entity.ListConfigReq;
import com.palwy.common.vo.ResultVO;


public interface ListConfigService {



    ResultVO<ListConfig> selectByCondition(ListConfigReq listConfig);


}