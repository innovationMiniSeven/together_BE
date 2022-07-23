package com.example.together.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum CategoryEnum {
    PURCHASE("PURCHASE"),
    DELIVERY("DELIVERY"),
    EXHIBITION("EXHIBITION"),
    ETC("ETC");

    String category;

    CategoryEnum(String category) {
        this.category = category;
    }

    @JsonCreator
    public static CategoryEnum fromCategory(String category) {
        switch (category) {
            case "PURCHASE":
                return CategoryEnum.PURCHASE;
            case "DELIVERY":
                return CategoryEnum.DELIVERY;
            case "EXHIBITION":
                return CategoryEnum.EXHIBITION;
            case "ETC":
                return CategoryEnum.ETC;
        }
        return CategoryEnum.ETC;
    }
}
