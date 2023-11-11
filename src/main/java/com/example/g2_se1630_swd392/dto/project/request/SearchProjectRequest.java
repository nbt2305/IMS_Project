package com.example.g2_se1630_swd392.dto.project.request;

import com.example.g2_se1630_swd392.dto.base.SearchListByPage;
import com.example.g2_se1630_swd392.entity.Project;
import com.example.g2_se1630_swd392.common.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchProjectRequest extends SearchListByPage {
    private Integer orderBy;
    private String sortBy;
    private String name;
    private String status;
    private Integer gitlabProjectId;
    private Integer classId;
    private Boolean active;
    private Integer leaderId;

    @Override
    public void validateInput(){
        super.validateInput();
        if(name != null){
            name = name.toLowerCase().trim();
        }else{
            name = "";
        }

        orderBy = ValidateUtils.validateOrderBy(orderBy, 0);
        sortBy = ValidateUtils.validateSortBy(sortBy, Project.class.getDeclaredFields(), "id");

    }
}
