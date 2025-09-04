package com.palwy.common.risk.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.palwy.clrcore.enums.SubmitApplySceneEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WeightCategoryUtils {

//    public static void main(String[] args) {
//        //测试数据
//        List<WeightRandomItem> categoryList = new ArrayList<>();
//        WeightRandomItem weightCategory1 = new WeightRandomItem("一等奖", 20);
//        WeightRandomItem weightCategory2 = new WeightRandomItem("二等奖", 80);
//        WeightRandomItem weightCategory3 = new WeightRandomItem("三等奖", 50);

//        categoryList.add(weightCategory1);
//        categoryList.add(weightCategory2);
//        categoryList.add(weightCategory3);
//        String index = "13814156161";
//        int hash = Math.abs(index.hashCode()) % 100;
//        String i=getWeight10RandomRightsType(index,30, 60);
//        System.out.println("hash=" + i);
       // String result = getWeight(hash, categoryList);
//        System.out.println("  开奖结果： " + result);
//        for (int i = 0; i < 100; i++) {
//            String weightRandomRightsType = getWeight10RandomRightsType(index,10,90);
//            System.out.println(weightRandomRightsType);
//        }
//    }

    public  String getWeightRandomRightsType() {
        List<WeightRandomItem> categoryList = new ArrayList<>();
        WeightRandomItem weightCategory1 = new WeightRandomItem("1", 50);
        WeightRandomItem weightCategory2 = new WeightRandomItem("0", 50);

        categoryList.add(weightCategory1);
        categoryList.add(weightCategory2);
// 生成 [0, 100) 的随机整数，用于模拟 0~99 百分比区间
        int hash = new Random().nextInt(100);
//         int hash = applyFlowNo.hashCode() % 100;
//         System.out.println(hash);
        return getWeight(hash, categoryList);
    }
    public  String getWeightRandomRightsType(String mobile, String category, String weight) {
        List<WeightRandomItem> categoryList   = new ArrayList<>();
        log.info("配置打印:{},{}",category,weight);
        String[] split = category.split(",");
        for (int i = 0; i < split.length; i++) {
            String[] strings = weight.split(",");
            WeightRandomItem item = new WeightRandomItem(split[i],Integer.valueOf(strings[i]));
            categoryList.add(item);
        }

        int hash = Math.abs(mobile.hashCode()) % 100;
        log.info("权重:{}",hash);
        String result = getWeight(hash, categoryList);
        return result;
    }

    public static String getWeight10RandomRightsType(String mobile, int percentage1, int percentage2) {
        List<WeightRandomItem> categoryList = new ArrayList<>();
        WeightRandomItem weightCategory1 = new WeightRandomItem("1", percentage1);
        WeightRandomItem weightCategory2 = new WeightRandomItem("0", percentage2);

        categoryList.add(weightCategory1);
        categoryList.add(weightCategory2);
// 生成 [0, 100) 的随机整数，用于模拟 0~99 百分比区间
        //int hash = new Random().nextInt(100);
//        int hash = mobile.hashCode() % 100;
//        if(hash<0){
//            hash=-hash;
//        }
        int hash = Integer.parseInt(getLastTwoDigits(mobile));
        return getBetweenWeight(hash, categoryList);
    }

    /**
     * 权重获取方法
     *
     * @param categorys
     * @return
     */
    public  String getWeight(Integer index, List<WeightRandomItem> categorys) {
        Integer weightSum = 0;
        String result = null;
        for (WeightRandomItem wc : categorys) {
            weightSum += wc.getWeight();
        }

        if (weightSum <= 0 || weightSum > 100) {
            log.error("Error: weightSum=" + weightSum.toString());
            return result;
        }
//        Random random = new Random();
//        Integer n = random.nextInt(weightSum); // n in [0, weightSum)
        Integer m = 0;
        for (WeightRandomItem wc : categorys) {
            if (m <= index && index < m + wc.getWeight()) {
                result = wc.getCategory();
                break;
            }
            m += wc.getWeight();
        }
        return result;
    }


    /**
     * 区间权重获取方法
     *
     * @param categorys
     * @return
     */
    public static String getBetweenWeight(Integer index, List<WeightRandomItem> categorys) {
        Integer weightSum = 0;
        String result = null;
//        for (WeightRandomItem wc : categorys) {
//            weightSum += wc.getWeight();
//        }

//        if (weightSum <= 0 || weightSum > 100) {
//            log.error("Error: weightSum=" + weightSum.toString());
//            return result;
//        }
//        Random random = new Random();
//        Integer n = random.nextInt(weightSum); // n in [0, weightSum)
        Integer m = 0;
        for (WeightRandomItem wc : categorys) {
            if (m <= index && index < wc.getWeight()) {
                result = wc.getCategory();
                break;
            }
            m += wc.getWeight();
        }
        if(result==null){
            result="1";
        }

        return result;
    }

     static class WeightRandomItem {

        private String category;//类别
        private int weight;  //权重值

        public WeightRandomItem(String category, int weight) {
            this.category = category;
            this.weight = weight;
        }


        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

    public static String getLastTwoDigits(String phone) {
        if (phone == null || !phone.matches("1[3-9]\\d{9}")) {
            throw new IllegalArgumentException("无效手机号");
        }
        return phone.substring(phone.length() - 2);
    }

}
