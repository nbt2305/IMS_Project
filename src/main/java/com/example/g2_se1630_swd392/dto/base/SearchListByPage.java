package com.example.g2_se1630_swd392.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchListByPage{
    private Integer pageIndex;
    private Integer pageSize;

    public void validateInput(){
        if(pageIndex == null || pageIndex < 0)
            pageIndex = 0;
        if(pageSize == null || pageSize <= 0)
            pageSize = 5;
    }
}
