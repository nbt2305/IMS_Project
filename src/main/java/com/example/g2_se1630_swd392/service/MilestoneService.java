package com.example.g2_se1630_swd392.service;

import com.example.g2_se1630_swd392.dto.milestone.request.SearchMilestoneRequest;
import com.example.g2_se1630_swd392.dto.milestone.response.ListMilestoneResponse;
import com.example.g2_se1630_swd392.entity.Milestone;


public interface MilestoneService extends BaseService<Milestone, Integer>{
    ListMilestoneResponse getAllMilestones(SearchMilestoneRequest request);
}
