package ru.ilka.leetcode.mapstructdemo;

import lombok.Getter;

public enum TypeEnum {

    ETHERMINE_ETH_EU1("eu1-ethermine"),
    ETHERMINE_ETC_EU1("eu1-ethermine-etc"),
    ONTOPOOL_QKC("qkc-ontopool"),
    TWO_MINERS_ETP("etp-2miners"),
    TWO_MINERS_EXP("exp-2miners"),
    TWO_MINERS_RVN("rvn-2miners"),
    TWO_MINERS_XMR("xmr-2miners"),
    DBIXMINE_DBIX("dbix-dbixmine"),
    AION_POOL("aion-pool");

    @Getter
    private final String displayedName;

    TypeEnum(String displayedName) {
        this.displayedName = displayedName;
    }

    public static TypeEnum getByDisplayedName(String displayedName) {
        for (TypeEnum val : values()) {
            if (val.displayedName.equals(displayedName)) {
                return val;
            }
        }
        throw new RuntimeException("TypeEnum not found!");
    }
}
