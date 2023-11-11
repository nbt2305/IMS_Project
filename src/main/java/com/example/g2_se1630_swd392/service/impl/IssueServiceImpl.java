package com.example.g2_se1630_swd392.service.impl;

import com.example.g2_se1630_swd392.common.exception.PermissionDeniedException;
import com.example.g2_se1630_swd392.common.exception.RecordNotFoundException;
import com.example.g2_se1630_swd392.common.utils.Constants;
import com.example.g2_se1630_swd392.dto.issue.request.BulkEditIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.request.CreateIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.request.SearchIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.request.UpdateIssueRequest;
import com.example.g2_se1630_swd392.dto.issue.response.IssueResponse;
import com.example.g2_se1630_swd392.dto.issue.response.SearchIssueResponse;
import com.example.g2_se1630_swd392.dto.project.response.ProjectResponse;
import com.example.g2_se1630_swd392.entity.*;
import com.example.g2_se1630_swd392.mapper.IssueMapper;
import com.example.g2_se1630_swd392.repository.*;
import com.example.g2_se1630_swd392.service.IssueService;
import com.example.g2_se1630_swd392.service.UserService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class IssueServiceImpl implements IssueService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://gitlab.com/api/v4";
    private final String access_token = "Bearer glpat-snxL68RcfZxMEyfpxz7a";

    private final IssueRepository issueRepository;
    private final IssueSettingRepository issueTypeRepository;
    private final ProjectRepository projectRepository;
    private final MilestoneRepository milestoneRepository;
    private final UserRepository userRepository;

    private final IssueMapper issueMapper;
    private final UserService userService;

    public SearchIssueResponse searchIssues(SearchIssueRequest request){
        User currentUser = userService.getCurrentUser();
        List<Integer> projectIds = null;
        if(currentUser.getRoleId() <= 1){
            List<Project> projects = projectRepository.searchProjectByStudentId(currentUser.getId());
            projectIds = projects.stream()
                    .map(Project::getId)
                    .toList();
            if(request.getProjectId() != null){
                if(projectIds.contains(request.getProjectId())){
                    projectIds = new ArrayList<>();
                    projectIds.add(request.getProjectId());
                }
                else{
                    projectIds = new ArrayList<>();
                }
            }
        }

        request.validateInput();

        Pageable pageable;
        if(request.getOrderBy() == 1)
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()));
        else
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()).descending());

        Page<Issue> issues = issueRepository.findIssues(
            request.getAssigneeId(),
            request.getDueDate(),
            request.getMilestoneId(),
            request.getTitle(),
            request.getIssueTypeId(),
            request.getIssueStatusId(),
            request.getProcessId(),
            projectIds,
            request.getGitlabIssueId(),
            pageable
        );

        List<IssueResponse> issueList = issueMapper.convertList(issues.getContent(), IssueResponse.class);

        SearchIssueResponse response = new SearchIssueResponse();
        response.setTotalRecords(issues.getTotalElements());
        response.setIssueList(issueList);
        response.setPageIndex(request.getPageIndex());
        response.setPageSize(request.getPageSize());

        return response;
    }

    @Override
    public void bulkEditIssue(BulkEditIssueRequest request) {
        if(request.getIssueIds() == null || request.getIssueIds().size() == 0)
            return;
        validateId(request.getAssigneeId(), request.getIssueTypeId(),
         null, request.getMilestoneId(), request.getIssueStatusId(), request.getProcessId());
        List<Issue> issues = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        for (Integer id : request.getIssueIds()) {
            Issue issue = issueRepository.findById(id).orElse(null);
            if(issue != null){
                if(request.getAssigneeId() != null){
                    issue.setAssignee(new User());
                    issue.getAssignee().setId(request.getAssigneeId());
                }
                if(request.getMilestoneId() != null){
                    issue.setMilestone(new Milestone());
                    issue.getMilestone().setId(request.getMilestoneId());
                }
                if(request.getIssueTypeId() != null){
                    issue.setIssueType(new IssueSetting());
                    issue.getIssueType().setId(request.getIssueTypeId());
                }
                if(request.getIssueStatusId() != null){
                    issue.setIssueStatus(new IssueSetting());
                    issue.getIssueStatus().setId(request.getIssueStatusId());
                }
                if(request.getProcessId() != null){
                    issue.setProcess(new IssueSetting());
                    issue.getProcess().setId(request.getProcessId());
                }
                issue.setUpdatedDate(new Date());
                issues.add(issue);
            }
        }
        issueRepository.saveAll(issues);
    }

    @Override
    public Object create(Object requestObject) {
        var request = (CreateIssueRequest) requestObject;
        validateId(request.getAssigneeId(), request.getIssueTypeId(),
                request.getProjectId(), request.getMilestoneId(),
                request.getIssueStatusId(), request.getProcessId());
        Issue issue = issueMapper.convertCreateIssueRequestToIssue(request, Issue.class);

        issue = issueRepository.save(issue);

        return issueMapper.convertIssueToIssueResponse(issue, IssueResponse.class);
    }

    private void validateId(Integer assigneeId, Integer issueTypeId, Integer projectId,
                             Integer milestoneId, Integer statusId, Integer processId) {
        if(assigneeId != null){
            User user = userRepository.findByIdAndActiveTrue(assigneeId).orElseThrow(
                    () -> new RecordNotFoundException("User")
            );
        }
        if(issueTypeId != null){
            IssueSetting issueType = issueTypeRepository
                    .findIssueSettingByIdAndType(issueTypeId, "Issue Type")
                    .orElseThrow( () -> new RecordNotFoundException("Issue Type"));
        }
        if(projectId != null){
            Project project = projectRepository.findById(projectId).orElseThrow(
                    () -> new RecordNotFoundException("Project")
            );
        }
        if(milestoneId != null){
            Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(
                    () -> new RecordNotFoundException("Milestone")
            );
        }
        if (statusId != null){
            IssueSetting issueStatus = issueTypeRepository
                .findIssueSettingByIdAndType(statusId, "Issue Status")
                .orElseThrow(() -> new RecordNotFoundException("Issue Status"));
        }
        if(processId != null){
            IssueSetting process = issueTypeRepository
                .findIssueSettingByIdAndType(processId, "Process")
                .orElseThrow(() -> new RecordNotFoundException("Process"));
        }
    }

    @Override
    public Object update(Integer integer, Object requestObject) {
        var request = (UpdateIssueRequest) requestObject;
        validateId(request.getAssigneeId(), request.getIssueTypeId(), request.getProjectId(),
                 request.getMilestoneId(), request.getIssueStatusId(), request.getProcessId());
        Issue existedIssue = findIssueById(request.getId());
        Issue updateIssue = issueMapper.convertUpdateIssueRequestToIssue(request, Issue.class);

        issueRepository.save(updateIssue);

        return issueMapper.convertIssueToIssueResponse(updateIssue, IssueResponse.class);
    }

    public Issue findIssueById(Integer id) {
        return issueRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Issue")
        );
    }

    @Override
    public Object getDetail(Integer id) {
        return findIssueById(id);
    }

    @Override
    public void delete(Integer id) {
        Issue issue = findIssueById(id);
        issueRepository.delete(issue);
    }

//    @Scheduled(fixedRate = 5*1000)
//    @Async
//    public void getInfoProjects(){
//        System.out.println("==========================================");
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromUriString(apiUrl+"/issues")
//                .queryParam("membership", true);
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", access_token);
//        headers.set("membership", "true");
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<List<Issue>> response = restTemplate.exchange(
//                builder.toUriString(),
//                HttpMethod.GET,
//                entity,
//                new ParameterizedTypeReference<List<Issue>>() {}
//        );
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            for (Issue externalData : response.getBody()) {
//                System.out.println(externalData);
//            }
//        } else {
//            // Xử lý lỗi nếu có.
//            System.out.println("Loi roi");
//        }
//    }
}
