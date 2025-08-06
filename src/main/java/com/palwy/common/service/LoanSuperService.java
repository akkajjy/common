package com.palwy.common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.palwy.common.Enum.FlagValueEnum;
import com.palwy.common.Enum.LoanSuperPrdStatusEnum;
import com.palwy.common.entity.LoanSuperConfig;
import com.palwy.common.entity.LoanSuperConfigExample;
import com.palwy.common.entity.LoanSuperLinkClickRecord;
import com.palwy.common.mapper.LoanSuperConfigMapper;
import com.palwy.common.mapper.LoanSuperLinkClickRecordMapper;
import com.palwy.common.req.LoanSuperClickVO;
import com.palwy.common.req.LoanSuperConfigVO;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.util.WorkDayUtil;
import com.palwy.common.utils.*;
import com.palwy.common.vo.ResultVO;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class LoanSuperService {

    public static final String PRD_PREFIX = "PRD";

    public static final String FORMAT = "yyyyMMddHHmmssSSS";

    @Autowired
    private LoanSuperConfigMapper loanSuperConfigMapper;

    @Autowired
    private LoanSuperLinkClickRecordMapper loanSuperLinkClickRecordMapper;

    @Autowired
    private TOSUpFileUtil tosUpFileUtil;
    public PageInfo<LoanSuperConfigVO> getPageList(LoanSuperConfigVO req){
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<LoanSuperConfig> list = loanSuperConfigMapper.getList(req);
        List<LoanSuperConfigVO> voList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            for(LoanSuperConfig loanSuperConfigResp : list){
                LoanSuperConfigVO configVO = new LoanSuperConfigVO();
                BeanUtils.copyProperties(loanSuperConfigResp,configVO);
                String accessUrl = tosUpFileUtil.generatePresignedUrl(loanSuperConfigResp.getPrdLogo(), 60);
                configVO.setPrdLogoAccessUrl(accessUrl);
                voList.add(configVO);
            }
        }
        PageInfo<LoanSuperConfigVO> pageInfo = new PageInfo<>(voList);
        pageInfo.setTotal(list.size());
        return pageInfo;
    }

    public ResultVO createConfig(LoanSuperConfigVO req) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
        String prdCode = PRD_PREFIX + LocalDateTime.now().format(formatter) ;
        req.setPrdCode(prdCode);
        LoanSuperConfig loanSuperConfig = new LoanSuperConfig();
        BeanUtils.copyProperties(req,loanSuperConfig);
        loanSuperConfig.setPrdStatus(LoanSuperPrdStatusEnum.ENABLE.name());
        loanSuperConfigMapper.insertSelective(loanSuperConfig);
        return ResultVOUtil.success("新增配置成功");
    }

    public ResultVO updateConfig(LoanSuperConfigVO req) {
        LoanSuperConfig loanSuperConfig = loanSuperConfigMapper.selectByPrimaryKey(req.getId());
        if(Objects.isNull(loanSuperConfig)){
            return ResultVOUtil.fail("无效的配置id");
        }
        if(StringUtils.isNotEmpty(req.getShowOrder())){
            if(CollectionUtils.isNotEmpty(this.getByOrder(req.getShowOrder(),loanSuperConfig.getId()))){
                return ResultVOUtil.fail("展示顺序不可重复");
            }
        }
        if(StringUtils.isNotEmpty(req.getPrdStatus())){
            loanSuperConfig.setPrdStatus(req.getPrdStatus());
        }
        loanSuperConfig.setPrdName(req.getPrdName());
        loanSuperConfig.setPrdLogo(req.getPrdLogo());
        loanSuperConfig.setPrdTag(req.getPrdTag());
        loanSuperConfig.setAmtUpper(req.getAmtUpper());
        loanSuperConfig.setShowTimeStart(req.getShowTimeStart());
        loanSuperConfig.setShowTimeEnd(req.getShowTimeEnd());
        loanSuperConfig.setShowHoliday(req.getShowHoliday());
        loanSuperConfig.setShowOrder(req.getShowOrder());
        loanSuperConfig.setLinkUrl(req.getLinkUrl());
        loanSuperConfigMapper.updateByPrimaryKey(loanSuperConfig);
        return ResultVOUtil.success("修改配置成功");
    }

    public List<LoanSuperConfigVO> getList() {
        LoanSuperConfigVO req = new LoanSuperConfigVO();
        req.setPrdStatus(LoanSuperPrdStatusEnum.ENABLE.name());
        List<LoanSuperConfig> list = loanSuperConfigMapper.getList(req);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        List<LoanSuperConfigVO> voList = new ArrayList<>();
        for(LoanSuperConfig loanSuperConfigResp : list){
            LoanSuperConfigVO configVO = new LoanSuperConfigVO();
            BeanUtils.copyProperties(loanSuperConfigResp,configVO);
            configVO.setShowLink(FlagValueEnum.N.name());
            //判断时间段
            LocalTime nowTime = LocalTime.now();
            if(nowTime.compareTo(LocalTime.parse(loanSuperConfigResp.getShowTimeStart())) >= 0 && nowTime.compareTo(LocalTime.parse(loanSuperConfigResp.getShowTimeEnd())) <= 0){
                //判断节假日是否需展示 如果=N 节假日展示
                if(FlagValueEnum.N.name().equals(loanSuperConfigResp.getShowHoliday())){
                    configVO.setShowLink(FlagValueEnum.Y.name());
                }else {
                    //如果节假日无需展示 判断今天是否是节假日,如果不是节假日则展示
                    if(!WorkDayUtil.getInstance().isHoliday(new Date())){
                        configVO.setShowLink(FlagValueEnum.Y.name());
                    }
                }
            }
            if(FlagValueEnum.Y.name().equals(configVO.getShowLink())){
                //获取预览url
                String accessUrl = tosUpFileUtil.generatePresignedUrl(loanSuperConfigResp.getPrdLogo(), 60);
                configVO.setPrdLogoAccessUrl(accessUrl);
            }
            voList.add(configVO);
        }
        voList.sort(Comparator.comparing(LoanSuperConfigVO::getShowOrder));
        return voList;
    }

    public ResultVO click(LoanSuperClickVO loanSuperClickVO) {
        if(StringUtils.isEmpty(loanSuperClickVO.getMobile()) && StringUtils.isEmpty(loanSuperClickVO.getCertNo())){
            return ResultVOUtil.fail("手机号和身份证号不可同时为空");
        }
        LoanSuperConfig loanSuperConfig = this.getByCode(loanSuperClickVO.getPrdCode());
        if(Objects.isNull(loanSuperConfig)){
            return ResultVOUtil.fail("无效的贷超产品编号");
        }
        LoanSuperLinkClickRecord loanSuperLinkClickRecord = new LoanSuperLinkClickRecord();
        loanSuperLinkClickRecord.setPrdCode(loanSuperConfig.getPrdCode());
        loanSuperLinkClickRecord.setLinkUrl(loanSuperConfig.getLinkUrl());
        loanSuperLinkClickRecord.setName(loanSuperClickVO.getName());
        loanSuperLinkClickRecord.setMobile(loanSuperClickVO.getMobile());
        loanSuperLinkClickRecord.setCertNo(loanSuperClickVO.getCertNo());
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loanSuperLinkClickRecord.setUserIp(ip);
        loanSuperLinkClickRecord.setUserIpAddr(IpAddressUtils.getRealAddressByIP(ip));
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        loanSuperLinkClickRecord.setUserBrowserName(userAgent.getBrowser().getName());
        loanSuperLinkClickRecord.setUserOperateSystem(userAgent.getOperatingSystem().getName());
        loanSuperLinkClickRecord.setClickTime(new Date());
        loanSuperLinkClickRecordMapper.insertSelective(loanSuperLinkClickRecord);
        return ResultVOUtil.success("点击成功");
    }

    public LoanSuperConfig getByCode(String prdCode){
        LoanSuperConfigExample example = new LoanSuperConfigExample();
        example.createCriteria().andIsDeletedEqualTo(FlagValueEnum.N.name())
                .andPrdStatusEqualTo(LoanSuperPrdStatusEnum.ENABLE.name())
                .andPrdCodeEqualTo(prdCode);
        List<LoanSuperConfig> loanSuperConfigs = loanSuperConfigMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(loanSuperConfigs) ? loanSuperConfigs.get(0) : null;
    }

    public List<LoanSuperConfig> getByOrder(String order,Long filterId){
        LoanSuperConfigExample example = new LoanSuperConfigExample();
        example.createCriteria().andIsDeletedEqualTo(FlagValueEnum.N.name())
                .andShowOrderEqualTo(order)
                .andIdNotEqualTo(filterId);
        return loanSuperConfigMapper.selectByExample(example);
    }
}
