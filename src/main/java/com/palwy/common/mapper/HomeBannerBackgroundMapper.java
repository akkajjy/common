package com.palwy.common.mapper;
import com.palwy.common.banner.entity.HomeBannerBackground;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HomeBannerBackgroundMapper {

    /**
     * 插入首页背景图配置
     */
    int insert(HomeBannerBackground homeBannerBackground);

    /**
     * 根据ID查询首页背景图配置
     */
    HomeBannerBackground selectById(Long id);

    /**
     * 查询所有首页背景图配置（按创建时间倒序）
     */
    List<HomeBannerBackground> selectAll();

    /**
     * 查询所有上架的首页背景图配置
     */
    List<HomeBannerBackground> selectAllOnline();

    /**
     * 根据主题模糊查询首页背景图配置
     */
    List<HomeBannerBackground> selectByTheme(String theme);

    /**
     * 更新首页背景图配置
     */
    int update(HomeBannerBackground homeBannerBackground);

    /**
     * 更新状态（上架/下架）
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 逻辑删除首页背景图配置
     */
    int deleteById(Long id);
}
