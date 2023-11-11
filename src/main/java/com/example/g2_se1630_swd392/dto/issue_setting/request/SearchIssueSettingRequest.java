package com.example.g2_se1630_swd392.dto.issue_setting.request;

import com.example.g2_se1630_swd392.dto.base.SearchListByPage;
import com.example.g2_se1630_swd392.entity.IssueSetting;
import com.example.g2_se1630_swd392.common.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchIssueSettingRequest extends SearchListByPage {
    private Integer orderBy;
    private String sortBy;
    private String name;
    private String type;
    private Integer gitlabIssueSettingId;
    private Boolean active;
    private Integer projectId;
    private Integer subjectId;
    private Integer classId;

    @Override
    public void validateInput(){
        super.validateInput();

        name = name != null ? name.toLowerCase().trim() : "";
        type = type != null ? type.toLowerCase().trim() : null;
        orderBy = ValidateUtils.validateOrderBy(orderBy, 0);
        sortBy = ValidateUtils.validateSortBy(sortBy, IssueSetting.class.getDeclaredFields(), "id");
    }

}
