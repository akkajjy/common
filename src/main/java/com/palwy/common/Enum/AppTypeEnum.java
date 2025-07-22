package com.palwy.common.Enum;

public enum AppTypeEnum {
    MALL(1, "商城"),
    LOAN_AID(2, "助贷");

    private final int value;
    private final String description;

    AppTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    // 根据整数值获取枚举实例（可选）
    public static AppTypeEnum fromValue(int value) {
        for (AppTypeEnum type : AppTypeEnum.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的应用类型值: " + value);
    }
}