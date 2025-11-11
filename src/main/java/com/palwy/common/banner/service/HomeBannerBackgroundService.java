package com.palwy.common.banner.service;

import com.palwy.common.banner.dto.HomeBannerRequest;
import com.palwy.common.banner.dto.HomeBannerStatusRequest;
import com.palwy.common.banner.entity.HomeBannerBackground;
import com.palwy.common.vo.ResultVO;

import java.util.List;

public interface HomeBannerBackgroundService {

    /**
     * 添加首页背景图配置
     */
    HomeBannerBackground addHomeBanner(HomeBannerRequest request);

    /**
     * 获取所有首页背景图配置列表
     */
    List<HomeBannerBackground> getAllHomeBanners();

    /**
     * 获取上架的首页背景图配置
     */
    HomeBannerBackground getOnlineHomeBanner();

    /**
     * 根据ID获取首页背景图配置
     */
    HomeBannerBackground getHomeBannerById(Long id);

    /**
     * 更新首页背景图配置
     */
    HomeBannerBackground updateHomeBanner(Long id, HomeBannerRequest request);

    /**
     * 更新首页背景图状态（上架/下架）
     */
    ResultVO updateHomeBannerStatus(HomeBannerStatusRequest request);

    /**
     * 删除首页背景图配置
     */
    boolean deleteHomeBanner(Long id);
}
