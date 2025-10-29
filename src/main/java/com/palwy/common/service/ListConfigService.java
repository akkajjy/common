package com.palwy.common.service;


import com.palwy.common.entity.ListConfig;
import com.palwy.common.entity.ListConfigReq;


public interface ListConfigService {



    ListConfig selectByCondition(ListConfigReq listConfig);


}