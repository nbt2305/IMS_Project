package com.example.g2_se1630_swd392.dto.issue_setting.request;

import com.example.g2_se1630_swd392.entity.Class;
import com.example.g2_se1630_swd392.entity.Project;
import com.example.g2_se1630_swd392.entity.Subject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIssueSettingRequest {
    private String name;
    private String description;
    private String type;
    private Integer gitlabIssueSettingId;
    private Integer projectId;
    private Integer classId;
    private Integer subjectId;
}
