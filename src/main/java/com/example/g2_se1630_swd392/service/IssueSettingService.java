package com.example.g2_se1630_swd392.service;

import com.example.g2_se1630_swd392.dto.issue_setting.request.SearchIssueSettingRequest;
import com.example.g2_se1630_swd392.dto.issue_setting.response.SearchIssueSettingResponse;
import com.example.g2_se1630_swd392.entity.IssueSetting;

public interface IssueSettingService extends BaseService<IssueSetting, Integer>{
    SearchIssueSettingResponse searchIssueType(SearchIssueSettingRequest request);

    Object changeActive(Integer id);
}
