package com.example.g2_se1630_swd392.service;

import com.example.g2_se1630_swd392.dto.issue.request.BulkEditIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.request.SearchIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.response.SearchIssueResponse;
import com.example.g2_se1630_swd392.entity.Issue;

public interface IssueService extends BaseService<Issue, Integer>{
    SearchIssueResponse searchIssues(SearchIssueRequest request);
    void bulkEditIssue(BulkEditIssueRequest request);
}
