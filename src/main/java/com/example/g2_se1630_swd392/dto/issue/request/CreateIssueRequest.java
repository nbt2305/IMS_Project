package com.example.g2_se1630_swd392.dto.issue.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIssueRequest {
    private Integer assigneeId;
    private String description;
    private Date dueDate;
    private Integer milestoneId;
    private String title;
    private Integer issueTypeId;
    private Integer issueStatusId;
    private Integer processId;
    private Integer projectId;
    private Integer gitlabIssueId;
}
