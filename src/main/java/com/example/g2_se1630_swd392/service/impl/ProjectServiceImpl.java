package com.example.g2_se1630_swd392.service.impl;

import com.example.g2_se1630_swd392.common.exception.RecordNotFoundException;
import com.example.g2_se1630_swd392.dto.project.request.CreateProjectRequest;
import com.example.g2_se1630_swd392.dto.project.request.SearchProjectRequest;
import com.example.g2_se1630_swd392.dto.project.request.UpdateProjectRequest;
import com.example.g2_se1630_swd392.dto.project.response.ProjectResponse;
import com.example.g2_se1630_swd392.dto.project.response.SearchProjectResponse;
import com.example.g2_se1630_swd392.entity.Project;
import com.example.g2_se1630_swd392.mapper.ProjectMapper;
import com.example.g2_se1630_swd392.repository.ClassRepository;
import com.example.g2_se1630_swd392.repository.ProjectRepository;
import com.example.g2_se1630_swd392.repository.UserRepository;
import com.example.g2_se1630_swd392.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Log4j2
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private ProjectMapper projectMapper;
    @Override
    @Transactional
    public Object create(Object requestObject) {
        log.info("Creating project: ");
        var request = (CreateProjectRequest) requestObject;
        validateId(request.getClassId(), request.getLeaderId());

        Project project = projectMapper.convertCreateProjectRequestToProject(request, Project.class);
        project.setId(null);
        projectRepository.save(project);

        return projectMapper.convertProjectToProjectResponse(project, ProjectResponse.class);
    }

    private void validateId(Integer classId, Integer leaderId) {
        if (classId != null)
            classRepository.findById(classId).orElseThrow(
                    () -> new RecordNotFoundException("Class")
            );
        if(leaderId != null)
            userRepository.findById(leaderId).orElseThrow(
                    () -> new RecordNotFoundException("User")
            );
    }

    @Override
    @Transactional
    @CachePut(value = "projectResponse", key = "'project' + #integer")
    public Object update(Integer integer, Object requestObject) {
        log.info("Updating project with id: " + integer);
        var request = (UpdateProjectRequest) requestObject;
        findProjectById(request.getId());
        validateId(request.getClassId(), request.getLeaderId());

        Project project = projectMapper.convertUpdateProjectRequestToProject(request, Project.class);
        projectRepository.save(project);

        return projectMapper.convertProjectToProjectResponse(project, ProjectResponse.class);
    }

    Project findProjectById(Integer id){
        return projectRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Project")
        );
    }

    @Override
    @Transactional
    @Cacheable(value = "projectResponse", key = "'project' + #id")
    public Object getDetail(Integer id) {
        log.info("Getting project with id: " + id);
        return findProjectById(id);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public SearchProjectResponse searchProject(SearchProjectRequest request) {
        log.info("Searching projects");
        request.validateInput();

        Pageable pageable;
        if(request.getOrderBy() == 1)
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()));
        else
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()).descending());

        Page<Project> projects = projectRepository.searchProject(
                request.getName(),
                request.getStatus(),
                request.getGitlabProjectId(),
                request.getActive(),
                request.getClassId(),
                pageable
        );

        SearchProjectResponse response = new SearchProjectResponse();
        response.setTotalRecords(projects.getTotalElements());
        response.setProjectList(projectMapper.convertList(projects.getContent(), ProjectResponse.class));
        response.setPageSize(request.getPageSize());
        response.setPageIndex(request.getPageIndex());
        return response;
    }
}
