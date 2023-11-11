package com.example.g2_se1630_swd392.service.impl;

import com.example.g2_se1630_swd392.common.exception.RecordNotFoundException;
import com.example.g2_se1630_swd392.dto.issue_setting.request.CreateIssueSettingRequest;
import com.example.g2_se1630_swd392.dto.issue_setting.request.SearchIssueSettingRequest;
import com.example.g2_se1630_swd392.dto.issue_setting.request.UpdateIssueSettingRequest;
import com.example.g2_se1630_swd392.dto.issue_setting.response.IssueSettingResponse;
import com.example.g2_se1630_swd392.dto.issue_setting.response.SearchIssueSettingResponse;
import com.example.g2_se1630_swd392.entity.IssueSetting;
import com.example.g2_se1630_swd392.mapper.IssueSettingMapper;
import com.example.g2_se1630_swd392.repository.ClassRepository;
import com.example.g2_se1630_swd392.repository.IssueSettingRepository;
import com.example.g2_se1630_swd392.repository.ProjectRepository;
import com.example.g2_se1630_swd392.repository.SubjectRepository;
import com.example.g2_se1630_swd392.service.IssueSettingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class IssueSettingServiceImpl implements IssueSettingService {
    private final IssueSettingRepository issueTypeRepository;
    private final ProjectRepository projectRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final IssueSettingMapper issueSettingMapper;
    @Override
    public Object create(Object requestObject) {
        var request = (CreateIssueSettingRequest) requestObject;
        validateId(request.getProjectId(), request.getClassId(), request.getSubjectId());
        IssueSetting issueType = issueSettingMapper.convertCreateIssueTypeRequestToIssueType(request, IssueSetting.class);
        issueType.setId(null);
        issueTypeRepository.save(issueType);

        return issueSettingMapper.convertIssueTypeToIssueTypeResponse(issueType, IssueSettingResponse.class);
    }

    private void validateId(Integer projectId, Integer classId, Integer subjectId) {
        if(projectId != null)
            projectRepository.findById(projectId).orElseThrow(
                    () -> new RecordNotFoundException("Project")
            );
        if (classId != null)
            classRepository.findById(classId).orElseThrow(
                    () -> new RecordNotFoundException("Class")
            );
        if(subjectId != null)
            subjectRepository.findById(subjectId).orElseThrow(
                    () -> new RecordNotFoundException("Subject")
            );
    }

    @Override
    public Object update(Integer integer, Object requestObject) {
        var request = (UpdateIssueSettingRequest) requestObject;
        findIssueSettingById(request.getId());
        validateId(request.getProjectId(), request.getClassId(), request.getSubjectId());
        IssueSetting issueType = issueSettingMapper.convertUpdateIssueTypeRequestToIssueType(request, IssueSetting.class);
        issueTypeRepository.save(issueType);

        return issueSettingMapper.convertIssueTypeToIssueTypeResponse(issueType, IssueSettingResponse.class);
    }

    public IssueSetting findIssueSettingById(Integer id) {
        return issueTypeRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Issue Setting")
        );
    }

    @Override
    public Object getDetail(Integer id) {
        return findIssueSettingById(id);
    }

    @Override
    public void delete(Integer id) {
        IssueSetting issueType = findIssueSettingById(id);
        issueTypeRepository.delete(issueType);
    }

    @Override
    public SearchIssueSettingResponse searchIssueType(SearchIssueSettingRequest request) {
        request.validateInput();

        Pageable pageable;
        if(request.getOrderBy() == 1)
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()));
        else
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()).descending());

        Page<IssueSetting> issueSettings = issueTypeRepository.searchIssueSetting(
                request.getName(),
                request.getGitlabIssueSettingId(),
                request.getActive(),
                request.getProjectId(),
                request.getType(),
                request.getClassId(),
                request.getSubjectId(),
                pageable
        );

        SearchIssueSettingResponse response = new SearchIssueSettingResponse();
        response.setTotalRecords(issueSettings.getTotalElements());
        response.setIssueSettingList(issueSettingMapper.convertList(issueSettings.getContent()));
        response.setPageSize(request.getPageSize());
        response.setPageIndex(request.getPageIndex());

        return response;
    }

    @Override
    public Object changeActive(Integer id) {
        IssueSetting issueType = findIssueSettingById(id);
        issueType.setActive(!issueType.getActive());
        issueTypeRepository.save(issueType);
        return issueType;
    }
}
