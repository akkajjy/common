package com.palwy.common.Enum;

public enum OsTypeEnum {
    MALL(1, "Android"),
    LOAN_AID(2, "IOS");

    private final int value;
    private final String description;

    OsTypeEnum(int value, String description) {
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
    public static OsTypeEnum fromValue(int value) {
        for (OsTypeEnum type : OsTypeEnum.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的应用类型值: " + value);
    }
}
