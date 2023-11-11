package com.example.g2_se1630_swd392.dto.issue.request;

import com.example.g2_se1630_swd392.dto.base.SearchListByPage;
import com.example.g2_se1630_swd392.entity.Issue;
import com.example.g2_se1630_swd392.common.utils.ValidateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchIssueRequest extends SearchListByPage{

    @ApiModelProperty(notes = "orderBy 0: desc, 1: asc, default = 0", example = "0")
    private Integer orderBy;
    @ApiModelProperty(notes = "sort by field of issue: id, title,...", example = "id")
    private String sortBy;
    private Integer assigneeId;
    private Date dueDate;
    private Integer milestoneId;
    private String title;
    private Integer issueTypeId;
    private Integer issueStatusId;
    private Integer processId;
    private Integer projectId;
    private Integer gitlabIssueId;

    @Override
    public void validateInput(){
        super.validateInput();
        if(title != null){
            title = title.toLowerCase().trim();
        }
        orderBy = ValidateUtils.validateOrderBy(orderBy, 0);
        sortBy = ValidateUtils.validateSortBy(sortBy, Issue.class.getDeclaredFields(), "id");
    }
}
