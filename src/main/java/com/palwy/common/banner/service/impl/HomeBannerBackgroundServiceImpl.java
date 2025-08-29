package com.palwy.common.banner.service.impl;

import com.palwy.common.banner.dto.HomeBannerRequest;
import com.palwy.common.banner.dto.HomeBannerStatusRequest;
import com.palwy.common.banner.entity.HomeBannerBackground;
import com.palwy.common.mapper.HomeBannerBackgroundMapper;
import com.palwy.common.banner.service.HomeBannerBackgroundService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.utils.StringUtils;
import com.palwy.common.utils.TOSUpFileUtil;
import com.palwy.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class HomeBannerBackgroundServiceImpl implements HomeBannerBackgroundService {

    @Autowired
    private HomeBannerBackgroundMapper homeBannerBackgroundMapper;

    @Autowired
    private TOSUpFileUtil tosUpFileUtil;

    @Override
    @Transactional
    public HomeBannerBackground addHomeBanner(HomeBannerRequest request) {
        // 创建新的背景图配置
        HomeBannerBackground homeBannerBackground = new HomeBannerBackground();
        homeBannerBackground.setTheme(request.getTheme());
        homeBannerBackground.setSmallImage(tosUpFileUtil.generatePublicFileUrl(request.getSmallImageFile()));
        homeBannerBackground.setLargeImage(tosUpFileUtil.generatePublicFileUrl(request.getLargeImageFile()));
        homeBannerBackground.setSmallImageFile(request.getSmallImageFile());
        homeBannerBackground.setLargeImageFile(request.getLargeImageFile());
        // 默认状态为下架
        homeBannerBackground.setStatus(0);
        homeBannerBackground.setGmtCreated(new Date());
        homeBannerBackground.setGmtModified(new Date());

        // 插入数据库
        homeBannerBackgroundMapper.insert(homeBannerBackground);

        return homeBannerBackground;
    }

    @Override
    public List<HomeBannerBackground> getAllHomeBanners() {
        return homeBannerBackgroundMapper.selectAll();
    }

    @Override
    public HomeBannerBackground getOnlineHomeBanner() {
        List<HomeBannerBackground> onlineBanners = homeBannerBackgroundMapper.selectAllOnline();
        return onlineBanners.isEmpty() ? null : onlineBanners.get(0);
    }

    @Override
    public HomeBannerBackground getHomeBannerById(Long id) {
        return homeBannerBackgroundMapper.selectById(id);
    }

    @Override
    @Transactional
    public HomeBannerBackground updateHomeBanner(Long id, HomeBannerRequest request) {
        // 获取现有配置
        HomeBannerBackground existingBanner = homeBannerBackgroundMapper.selectById(id);
        if (existingBanner == null) {
            throw new RuntimeException("背景图配置不存在");
        }

        // 更新字段
        existingBanner.setTheme(request.getTheme());
        existingBanner.setSmallImageFile(request.getSmallImageFile());
        if(StringUtils.isNotEmpty(request.getSmallImageFile())){
            existingBanner.setSmallImage(tosUpFileUtil.generatePublicFileUrl(request.getSmallImageFile()));
        }
        existingBanner.setLargeImageFile(request.getLargeImageFile());
        if(StringUtils.isNotEmpty(request.getLargeImageFile())){
            existingBanner.setLargeImage(tosUpFileUtil.generatePublicFileUrl(request.getLargeImageFile()));
        }
        existingBanner.setGmtModified(new Date());
        existingBanner.setModifier("system");

        // 更新数据库
        homeBannerBackgroundMapper.update(existingBanner);

        return existingBanner;
    }

    @Override
    @Transactional
    public ResultVO updateHomeBannerStatus(HomeBannerStatusRequest request) {
        // 获取现有配置
        HomeBannerBackground existingBanner = homeBannerBackgroundMapper.selectById(request.getId());
        if (existingBanner == null) {
            return ResultVOUtil.fail("暂无图片配置");
        }

        // 如果要上架，检查是否已有上架的背景图
        if (request.getStatus() == 1) {
            HomeBannerBackground onlineBanner = getOnlineHomeBanner();
            if (onlineBanner != null && !onlineBanner.getId().equals(request.getId())) {
                return ResultVOUtil.fail("请先下架原上架背景图（ID: " + onlineBanner.getId() + "，主题: " + onlineBanner.getTheme() + "）");
            }
        }

        // 更新状态
        homeBannerBackgroundMapper.updateStatus(request.getId(), request.getStatus());

        return ResultVOUtil.success();
    }

    @Override
    @Transactional
    public boolean deleteHomeBanner(Long id) {
        HomeBannerBackground existingBanner = homeBannerBackgroundMapper.selectById(id);
        if (existingBanner == null) {
            throw new RuntimeException("背景图配置不存在");
        }

        // 如果是上架状态，不允许删除
        if (existingBanner.getStatus() == 1) {
            throw new RuntimeException("请先下架背景图再删除");
        }

        // 逻辑删除
        homeBannerBackgroundMapper.deleteById(id);

        return true;
    }
}
