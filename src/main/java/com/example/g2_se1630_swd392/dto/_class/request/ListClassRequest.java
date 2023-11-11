package com.example.g2_se1630_swd392.dto._class.request;

import com.example.g2_se1630_swd392.dto.base.SearchListByPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListClassRequest extends SearchListByPage{

    @ApiModelProperty(notes = "orderBy 0: desc, 1: asc, default = 0", example = "0")
    private Integer orderBy;
    @ApiModelProperty(notes = "sort by field of issue: id, title,...", example = "id")
    private String sortBy;
    private Integer teacherId;
    private String name;
    private Integer subjectId;
    private Integer semesterId;

}
