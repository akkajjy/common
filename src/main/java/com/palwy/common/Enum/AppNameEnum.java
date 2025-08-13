package com.palwy.common.Enum;

public enum AppNameEnum {
    TDD("TDD", "桃多多");  // 使用字符串作为标识符

    private final String value;        // 统一为String类型
    private final String description;

    // 修正1：构造函数参数类型改为String（匹配枚举常量）
    // 修正2：构造函数名称与枚举类名一致
    AppNameEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    // 修正3：返回值类型与字段类型一致
    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    // 修正4：参数类型改为String，使用equals比较
    public static AppNameEnum fromValue(String value) {
        for (AppNameEnum type : AppNameEnum.values()) {
            if (type.value.equals(value)) {  // 字符串比较使用equals()
                return type;
            }
        }
        throw new IllegalArgumentException("无效的应用名称值: " + value);
    }
}