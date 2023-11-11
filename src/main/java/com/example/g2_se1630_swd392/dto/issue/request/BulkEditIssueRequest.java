package com.example.g2_se1630_swd392.dto.issue.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkEditIssueRequest {
    private List<Integer> issueIds;
    private Integer assigneeId;
    private Integer milestoneId;
    private Integer issueTypeId;
    private Integer issueStatusId;
    private Integer processId;
}
