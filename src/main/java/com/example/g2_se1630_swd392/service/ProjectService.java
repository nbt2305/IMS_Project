package com.example.g2_se1630_swd392.service;

import com.example.g2_se1630_swd392.dto.project.request.SearchProjectRequest;
import com.example.g2_se1630_swd392.dto.project.response.SearchProjectResponse;
import com.example.g2_se1630_swd392.entity.Project;

public interface ProjectService extends BaseService<Project, Integer>{
    SearchProjectResponse searchProject(SearchProjectRequest request);
}
