package com.palwy.common.service;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ksvip.next.components.util.serializer.JsonUtils;
import com.palwy.bytehouse.domain.handler.ByteHouseClient;
import com.palwy.bytehouse.domain.handler.SpecifyEnvByteHouseClient;
import com.palwy.common.req.BigScreenDataVO;
import com.palwy.common.req.DistributionVO;
import com.palwy.common.req.LineChartVO;
import com.palwy.common.req.QueryDataVO;
import com.palwy.common.utils.DateTimeUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class BigScreenDataService {

    public static final String QUERY_SQL_ENV = "prod";

    public static final String QUERY_SQL = "SELECT * from bssmst_area.real_time_big_screen_data_rpt;";
    @Autowired
    private SpecifyEnvByteHouseClient specifyEnvByteHouseClient;

    public BigScreenDataVO getData(){
        //1、查询数据
        List<QueryDataVO> list = this.queryData();
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        //2、组装数据
        BigScreenDataVO bigScreenDataVO = new BigScreenDataVO();
        List<DistributionVO> ageGroup = Lists.newArrayList();
        List<DistributionVO> enduGroup = Lists.newArrayList();
        List<DistributionVO> areaGroup = Lists.newArrayList();
        List<DistributionVO> shopDataGrowthTrend = Lists.newArrayList();
        List<DistributionVO> legalRightDataGrowthTrend = Lists.newArrayList();
        List<DistributionVO> shopSalesTop10 = Lists.newArrayList();

        for(QueryDataVO queryDataVO : list){
            DistributionVO distributionVO = new DistributionVO();
            distributionVO.setLabel(queryDataVO.getIndex());
            distributionVO.setValue(queryDataVO.getIndexValue());
            if("当日总金额".equals(queryDataVO.getData_label())){
                bigScreenDataVO.setCurrentTotalAmt(this.yuanToWan(queryDataVO.getIndexValue(),2));
            }else if("当日订单量".equals(queryDataVO.getData_label())){
                bigScreenDataVO.setCurrentTotalCount(queryDataVO.getIndexValue());
            }else if("件均".equals(queryDataVO.getData_label())){
                bigScreenDataVO.setItemsAre(queryDataVO.getIndexValue());
            }else if("累计金额排名".equals(queryDataVO.getData_label()) && "商城".equals(queryDataVO.getIndex())){
                bigScreenDataVO.setShopAmt(this.yuanToWan(queryDataVO.getIndexValue(),2));
            }else if("累计金额排名".equals(queryDataVO.getData_label()) && "权益".equals(queryDataVO.getIndex())){
                bigScreenDataVO.setLegalRightAmt(this.yuanToWan(queryDataVO.getIndexValue(),2));
            }else if("年龄分布".equals(queryDataVO.getData_label())){
                ageGroup.add(distributionVO);
            }else if("学历分布".equals(queryDataVO.getData_label())){
                enduGroup.add(distributionVO);
            }else if("地域分布".equals(queryDataVO.getData_label())){
                areaGroup.add(distributionVO);
            }else if("数据增长趋势-商城".equals(queryDataVO.getData_label())){
                distributionVO.setXIndex(this.formatDateTime(queryDataVO.getIndex()));
                shopDataGrowthTrend.add(distributionVO);
            }else if("数据增长趋势-权益".equals(queryDataVO.getData_label())){
                distributionVO.setXIndex(this.formatDateTime(queryDataVO.getIndex()));
                legalRightDataGrowthTrend.add(distributionVO);
            }else if("商品销售排行TOP10".equals(queryDataVO.getData_label())){
                shopSalesTop10.add(distributionVO);
            }
        }
        bigScreenDataVO.setAgeGroup(this.calculateWithApacheCommons(ageGroup));
        bigScreenDataVO.setEnduGroup(this.calculateWithApacheCommons(enduGroup));
        this.processGroup(areaGroup);
        bigScreenDataVO.setAreaGroup(areaGroup);
        this.processDateGroup(shopDataGrowthTrend);
        LineChartVO shopLineChartVO = new LineChartVO();
        List<String> indexList = shopDataGrowthTrend.stream()
                .map(DistributionVO::getXIndex)
                .collect(Collectors.toList());
        shopLineChartVO.setIndexList(indexList);
        List<BigDecimal> valueList = shopDataGrowthTrend.stream()
                .map(data -> yuanToWan(data.getValue(),0))
                .collect(Collectors.toList());
        shopLineChartVO.setValueList(valueList);
        bigScreenDataVO.setShopDataGrowthTrend(shopLineChartVO);
        this.processDateGroup(legalRightDataGrowthTrend);
        LineChartVO legalRightLineChartVO = new LineChartVO();
        List<String> legalRightIndexList = legalRightDataGrowthTrend.stream()
                .map(DistributionVO::getXIndex)
                .collect(Collectors.toList());
        legalRightLineChartVO.setIndexList(legalRightIndexList);
        List<BigDecimal> legalRightValueList = legalRightDataGrowthTrend.stream()
                .map(data -> yuanToWan(data.getValue(),0))
                .collect(Collectors.toList());
        legalRightLineChartVO.setValueList(legalRightValueList);
        bigScreenDataVO.setLegalRightDataGrowthTrend(legalRightLineChartVO);
        this.processGroup(shopSalesTop10);
        bigScreenDataVO.setShopSalesTop10(shopSalesTop10);
        return bigScreenDataVO;
    }

    public List<DistributionVO> calculateWithApacheCommons(List<DistributionVO> values) {
        // 1. 计算总值（过滤null值）
        BigDecimal total = values.stream()
                .map(DistributionVO::getValue)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2. 处理总和为0的情况
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal equalPercent = new BigDecimal(100.0)
                    .divide(new BigDecimal(values.size()), 1, RoundingMode.HALF_UP);
            return values.stream()
                    .map(vo -> {
                        DistributionVO newVo = new DistributionVO();
                        newVo.setLabel(vo.getLabel());
                        newVo.setValue(vo.getValue());
                        newVo.setPercentage(equalPercent);
                        return newVo;
                    })
                    .collect(Collectors.toList());
        }

        List<DistributionVO> result = new ArrayList<>();
        BigDecimal sumOfPercents = BigDecimal.ZERO;

        // 3. 计算前n-1项的百分比
        for (int i = 0; i < values.size() - 1; i++) {
            DistributionVO vo = values.get(i);
            BigDecimal value = vo.getValue() != null ? vo.getValue() : BigDecimal.ZERO;

            // 计算百分比（保留3位中间精度）
            BigDecimal percent = value.multiply(new BigDecimal("100"))
                    .divide(total, 3, RoundingMode.HALF_UP)
                    // 四舍五入到1位小数
                    .setScale(1, RoundingMode.HALF_UP);

            DistributionVO newVo = new DistributionVO();
            newVo.setLabel(vo.getLabel());
            newVo.setValue(vo.getValue());
            newVo.setPercentage(percent);

            result.add(newVo);
            sumOfPercents = sumOfPercents.add(percent);
        }

        // 4. 计算最后一项的百分比（确保总和为100%）
        BigDecimal lastPercent = new BigDecimal("100.0").subtract(sumOfPercents)
                .setScale(1, RoundingMode.HALF_UP);

        DistributionVO lastVo = values.get(values.size() - 1);
        DistributionVO newLastVo = new DistributionVO();
        newLastVo.setLabel(lastVo.getLabel());
        newLastVo.setValue(lastVo.getValue());
        newLastVo.setPercentage(lastPercent);
        result.add(newLastVo);

        return result;
    }


    public BigDecimal yuanToWan(BigDecimal yuanAmount,int scale) {
        if (yuanAmount == null) {
            return BigDecimal.ZERO;
        }
        return yuanAmount.divide(new BigDecimal("10000"), scale, RoundingMode.HALF_UP);
    }

    public void processGroup(List<DistributionVO> list) {
        // 1. 按照value转为BigDecimal倒序排序
        List<DistributionVO> sortedList = list.stream()
                .sorted(Comparator.comparing(
                        vo -> vo.getValue(),
                        Comparator.reverseOrder()
                ))
                .collect(Collectors.toList());

        // 2. 为每个元素设置order (NO01, NO02...)
        IntStream.range(0, sortedList.size())
                .forEach(i -> {
                    String orderNumber = String.format("NO.%02d", i + 1);
                    sortedList.get(i).setOrder(orderNumber);
                });
        list.clear();
        list.addAll(sortedList);
    }

    public void processDateGroup(List<DistributionVO> list) {
        // 1. 按照value转为BigDecimal倒序排序
        List<DistributionVO> sortedList = list.stream()
                .sorted(Comparator.comparing(
                        vo -> vo.getXIndex()
                ))
                .collect(Collectors.toList());

        // 2. 为每个元素设置order (NO01, NO02...)
        IntStream.range(0, sortedList.size())
                .forEach(i -> {
                    String orderNumber = String.format("NO.%02d", i + 1);
                    sortedList.get(i).setOrder(orderNumber);
                });
        list.clear();
        list.addAll(sortedList);
    }


    private String formatDateTime(String dataTime){
        try {
            LocalDate date = LocalDate.parse(dataTime);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd");
            String formattedDate = date.format(outputFormatter);
            return formattedDate;
        }catch (Exception e){
            log.error("日期格式化异常:{}",dataTime,e);
        }
        return null;
    }

    private List<QueryDataVO> queryData(){
        try {
            log.info("调用数仓查询大屏数据入参：{}", QUERY_SQL);
            List list = specifyEnvByteHouseClient.select(QUERY_SQL_ENV,QUERY_SQL);
            log.info("调用数仓查询实时数据结果：{};{}", QUERY_SQL, JsonUtils.toJSONString(list));
            if(CollectionUtils.isNotEmpty(list)){
                return JSON.parseArray(JSON.toJSONString(list), QueryDataVO.class);
            }
        } catch (Exception e) {
            log.info("调用数仓查询实时数据异常:{}",QUERY_SQL,e);
        }
        return null;
    }
}
