package com.example.g2_se1630_swd392.service.impl;

import com.example.g2_se1630_swd392.common.exception.NameAlreadyExistsException;
import com.example.g2_se1630_swd392.common.exception.PermissionDeniedException;
import com.example.g2_se1630_swd392.common.exception.RecordNotFoundException;
import com.example.g2_se1630_swd392.common.utils.Constants;
import com.example.g2_se1630_swd392.dto.project.response.ProjectResponse;
import com.example.g2_se1630_swd392.dto.subject.request.CreateSubjectRequest;
import com.example.g2_se1630_swd392.dto.subject.request.ListSubjectRequest;
import com.example.g2_se1630_swd392.dto.subject.request.UpdateSubjectRequest;
import com.example.g2_se1630_swd392.dto.subject.response.CreateSubjectResponse;
import com.example.g2_se1630_swd392.dto.subject.response.ListSubjectResponse;
import com.example.g2_se1630_swd392.dto.subject.response.SubjectResponse;
import com.example.g2_se1630_swd392.entity.Project;
import com.example.g2_se1630_swd392.entity.Subject;
import com.example.g2_se1630_swd392.entity.SubjectAssignment;
import com.example.g2_se1630_swd392.entity.User;
import com.example.g2_se1630_swd392.mapper.SubjectMapper;
import com.example.g2_se1630_swd392.repository.*;
import com.example.g2_se1630_swd392.service.SubjectService;
import com.example.g2_se1630_swd392.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final UserService userService;
    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubjectAssignmentRepository subjectAssignmentRepository;

    @Override
    @Transactional
    public CreateSubjectResponse create(Object objectRequest) {
        log.info("Creating subject: ");
        User currentUser = userService.getCurrentUser();
        if (!Objects.equals(currentUser.getRoleId(), Constants.Role.ADMIN)) {
            throw new PermissionDeniedException();
        }
        CreateSubjectRequest request = (CreateSubjectRequest) objectRequest;
        Subject existingSubject = subjectRepository.findByName(request.getName());
        if (existingSubject != null) {
            throw new NameAlreadyExistsException("Subject");
        }
        Subject saveSubject = subjectMapper.convertCreateSubjectDtoToSubject(request);
        subjectRepository.save(saveSubject);
        var assignmentIds = getAssignmentIdsList(request.getAssignments());
        addNewSubjectAssignments(saveSubject.getId(), assignmentIds);
        var response = subjectMapper.convertSubjectToCreateSubjectResponse(saveSubject);
        response.setAssignments(assignmentRepository.getAllBySubjectId(saveSubject.getId()));
        return response;
    }

    public void addNewSubjectAssignments(Integer subjectId, List<Integer> assignmentIds) {
        for (Integer assignmentId : assignmentIds) {
            var subjectAssignment = new SubjectAssignment();
            subjectAssignment.setSubjectId(subjectId);
            subjectAssignment.setAssignmentId(assignmentId);
            subjectAssignmentRepository.save(subjectAssignment);
        }
    }

    public List<Integer> getAssignmentIdsList(List<Integer> assignmentIds) {
        var countAssignmentIds = assignmentRepository.countByIdIn(assignmentIds);
        if (assignmentIds.size() != countAssignmentIds) {
            throw new RecordNotFoundException("Assignment");
        }
        return assignmentIds;
    }

    @Override
    @Transactional
    @CachePut(value = "subjectResponse", key = "'subject' + #id")
    public SubjectResponse update(Integer id, Object objectRequest) {
        log.info("Updating subject with id: " + id);
        UpdateSubjectRequest request = (UpdateSubjectRequest) objectRequest;
        Subject foundSubjectById = findSubjectById(id);
        if (!checkSubjectAccess(foundSubjectById)) {
            throw new PermissionDeniedException();
        }
        Subject saveSubject = subjectMapper.convertUpdateSubjectRequestToSubject(request, id);
        Subject foundSubjectByName = subjectRepository.findByNameForUpdate(saveSubject.getName(), foundSubjectById.getName());
        if (foundSubjectByName != null) {
            throw new NameAlreadyExistsException("Subject");
        }
        subjectRepository.save(saveSubject);
        subjectAssignmentRepository.deleteAllBySubjectId(saveSubject.getId());
        var assignmentIds = getAssignmentIdsList(request.getAssignments());
        addNewSubjectAssignments(saveSubject.getId(), assignmentIds);
        var response = subjectMapper.convertSubjectToSubjectResponse(saveSubject);
        response.setAssignments(assignmentRepository.getAllBySubjectId(saveSubject.getId()));
        return response;
    }

    private Subject findSubjectById(Integer id) {
        return subjectRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Subject"));
    }

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    @Cacheable(value = "subjectResponse", key = "'subject' + #id")
    public SubjectResponse getDetail(Integer id) {
        Subject foundSubject = subjectRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Subject")
        );
        if (!checkSubjectAccess(foundSubject)) {
            throw new PermissionDeniedException();
        }

        var response = subjectMapper.convertSubjectToSubjectResponse(foundSubject);
        response.setAssignments(assignmentRepository.getAllBySubjectId(foundSubject.getId()));
        return response;
    }


    @Override
    @CacheEvict(value = "subjectResponse", key = "'subject' + #id")
    public void delete(Integer id) {
        log.info("Deleting subject with id: " + id);
        Subject existingSubject = findSubjectById(id);
        if (!checkSubjectAccess(existingSubject)) {
            throw new PermissionDeniedException();
        }
        subjectRepository.delete(existingSubject);
    }

    @Override
    public ListSubjectResponse getAllSubject(ListSubjectRequest request) {
        log.info("Searching subjects");
        request.validateInput();
        User currentUser = userService.getCurrentUser();
        if (!Objects.equals(currentUser.getRoleId(), Constants.Role.ADMIN) && !Objects.equals(currentUser.getRoleId(), Constants.Role.MANAGER)) {
            throw new PermissionDeniedException();
        }

        Pageable pageable;
        if (request.getOrderBy() == 1)
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()));
        else
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()).descending());

        Page<Subject> subjects = subjectRepository.searchSubject(
                request.getName(),
                request.getManagerId(),
                pageable
        );

        ListSubjectResponse response = new ListSubjectResponse();
        response.setTotalRecords(subjects.getTotalElements());
        response.setSubjectList(subjectMapper.convertList(subjects.getContent(), SubjectResponse.class));
        response.setPageIndex(request.getPageIndex());
        response.setPageSize(request.getPageSize());
        return response;
    }

    private boolean checkSubjectAccess(Subject subject) {
        User currentUser = userService.getCurrentUser();
        return (Objects.equals(currentUser.getRoleId(), Constants.Role.ADMIN) || Objects.equals(currentUser.getRoleId(), Constants.Role.MANAGER)) &&
                (!Objects.equals(currentUser.getRoleId(), Constants.Role.MANAGER) || Objects.equals(subject.getManager().getId(), currentUser.getId()));
    }
}
