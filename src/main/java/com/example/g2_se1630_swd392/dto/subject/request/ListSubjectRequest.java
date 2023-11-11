package com.example.g2_se1630_swd392.dto.subject.request;

import com.example.g2_se1630_swd392.dto.base.SearchListByPage;
import com.example.g2_se1630_swd392.entity.Subject;
import com.example.g2_se1630_swd392.common.utils.ValidateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListSubjectRequest extends SearchListByPage{

    @ApiModelProperty(notes = "orderBy 0: desc, 1: asc, default = 0", example = "0")
    private Integer orderBy;
    @ApiModelProperty(notes = "sort by field of issue: id, title,...", example = "id")
    private String sortBy;

    private String name;
    private Integer managerId;
    @Override
    public void validateInput(){
        super.validateInput();
        if(name != null){
            name = name.toLowerCase().trim();
        }else{
            name = "";
        }
        orderBy = ValidateUtils.validateOrderBy(orderBy, 0);
        sortBy = ValidateUtils.validateSortBy(sortBy, Subject.class.getDeclaredFields(), "id");
    }
}
