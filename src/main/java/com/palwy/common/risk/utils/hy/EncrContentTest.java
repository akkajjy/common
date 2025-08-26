package com.palwy.common.risk.utils.hy;

import com.palwy.common.risk.utils.hy.utils.AESUtil2;

/**
 * @description:
 * @author: juyang.liang
 * @since: 2023-07-13 10:47
 * @updatedUser:
 * @updatedDate:
 * @updatedRemark:
 * @version:
 */
public class EncrContentTest {
    public static void main(String[] args) {
        String key = "03f44cc3d1d311ed8fc2000c29b682ff";
        String content = "{\n" +
                "  \"thirdUserId\": \"1233455\",\n" +
                "  \"orderNo\": \"TC202204252151496660000004\",\n" +
                "  \"carOwnerInfo\": {\n" +
                "    \"licenseNo\": \"车牌号\",\n" +
                "    \"licenseType\": \"⼤型汽⻋号牌\",\n" +
                "    \"modelCode\": \"车辆型号\",\n" +
                "    \"registerDate\": \"2021-12-13\",\n" +
                "    \"useProperty\": \"不区分营业⾮营业\",\n" +
                "    \"displacement\": \"排量值\"\n" +
                "  },\n" +
                "  \"eduInfo\": {\n" +
                "    \"isProject211\": \"是\",\n" +
                "    \"isProject985\": \"否\",\n" +
                "    \"isDoubleFirstClass\": \"否\",\n" +
                "    \"educationalLevel\": \"学历等级\",\n" +
                "    \"major\": \"专业名称\",\n" +
                "    \"school\": \"学校名称\",\n" +
                "    \"specialty\": \"专业\",\n" +
                "    \"duration\": \"学制\",\n" +
                "    \"studyType\": \"学习形式\",\n" +
                "    \"situation\": \"结业\",\n" +
                "    \"enrollmentDate\": \"入学日期\",\n" +
                "    \"graduationDate\": \"毕业日期\"\n" +
                "  }\n" +
                "}";
        //加密
        String encryptContent = AESUtil2.encrypt(key, content);
        System.out.println("洞察加密内容：" + encryptContent);
        //解密内容
        String decryptContent = AESUtil2.decrypt(key, encryptContent);
            System.out.println("客户解密内容：" + decryptContent);
    }
}
