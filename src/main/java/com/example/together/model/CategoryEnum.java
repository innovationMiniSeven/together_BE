package com.example.together.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum CategoryEnum {
    PURCHASE,
    DELIVERY,
    EXHIBITION,
    ETC;

    @JsonCreator
    public static CategoryEnum from(String category) {
        category = category.toUpperCase();
        switch (category) {
            case "PURCHASE":
                return CategoryEnum.PURCHASE;
            case "DELIVERY":
                return CategoryEnum.DELIVERY;
            case "EXHIBITION":
                return CategoryEnum.EXHIBITION;
            default:
                return CategoryEnum.ETC;
        }
    }
}
