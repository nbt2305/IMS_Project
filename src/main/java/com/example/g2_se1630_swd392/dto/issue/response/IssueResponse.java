package com.example.g2_se1630_swd392.dto.issue.response;

import com.example.g2_se1630_swd392.dto.base.BaseDto;
import com.example.g2_se1630_swd392.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueResponse extends BaseDto {
    private  Integer id;
    private User assignee;
    private String description;
    private Date dueDate;
    private Milestone milestone;
    private String title;
    private IssueSetting issueType;
    private IssueSetting issueStatus;
    private IssueSetting process;
    private Project project;
    private Integer gitlabIssueId;
}
