package com.example.g2_se1630_swd392.common.utils;

import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@NoArgsConstructor
public class ValidateUtils {
    public static int validateOrderBy(Integer orderBy, int defaultValue) {
        if (orderBy == null || orderBy < 0 || orderBy > 1)
            return defaultValue;
        return orderBy;
    }

    public static String validateSortBy(String sortBy, Field[] fields, String defaultValue) {
        if (sortBy == null)
            return defaultValue;
        for (Field field : fields) {
            if (field.getName().equals(sortBy))
                return field.getName();
        }
        return defaultValue;
    }
}
