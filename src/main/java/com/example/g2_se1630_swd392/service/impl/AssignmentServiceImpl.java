package com.example.g2_se1630_swd392.service.impl;

import com.example.g2_se1630_swd392.common.exception.NameAlreadyExistsException;
import com.example.g2_se1630_swd392.common.exception.RecordNotFoundException;
import com.example.g2_se1630_swd392.dto.assignment.request.CreateAssignmentRequest;
import com.example.g2_se1630_swd392.dto.assignment.request.ListAssignmentRequest;
import com.example.g2_se1630_swd392.dto.assignment.request.UpdateAssignmentRequest;
import com.example.g2_se1630_swd392.dto.assignment.response.AssignmentResponse;
import com.example.g2_se1630_swd392.dto.assignment.response.ListAssignmentResponse;
import com.example.g2_se1630_swd392.entity.Assignment;
import com.example.g2_se1630_swd392.mapper.AssignmentMapper;
import com.example.g2_se1630_swd392.repository.AssignmentRepository;
import com.example.g2_se1630_swd392.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Log4j2
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    @Override
    @Transactional
    public AssignmentResponse create(Object objectRequest) {
        log.info("Creating assignment: ");
        var request = (CreateAssignmentRequest) objectRequest;
        var foundAssignment = assignmentRepository.findByNameAndActiveTrue(request.getName());
        if (foundAssignment != null) {
            throw new NameAlreadyExistsException("Assignment");
        }
        var saveAssignment = assignmentRepository.save(assignmentMapper.convertCreateAssignmentRequestToAssignment(request));
        return assignmentMapper.convertAssignmentToAssignmentResponse(saveAssignment);
    }

    @Override
    @CachePut(value = "assignmentResponse", key = "'assignment' + #id")
    public AssignmentResponse update(Integer id, Object objectRequest) {
        log.info("Updating assignment with id: " + id);
        var request = (UpdateAssignmentRequest) objectRequest;
        var foundAssignment = assignmentRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Assignment")
        );
        var foundAssignmentByName = assignmentRepository.findByNameForUpdate(request.getName(), foundAssignment.getName());
        if (foundAssignmentByName != null) {
            throw new NameAlreadyExistsException("Assignment");
        }
        var saveAssignment = assignmentRepository.save(assignmentMapper.convertUpdateAssignmentRequestToAssignment(request, id));
        return assignmentMapper.convertAssignmentToAssignmentResponse(saveAssignment);
    }

    @Override
    @Cacheable(value = "assignmentResponse", key = "'assignment' + #id")
    public AssignmentResponse getDetail(Integer id) {
        log.info("Fetching assignment with id: " + id);
        var foundAssignment = assignmentRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Assignment")
        );
        return assignmentMapper.convertAssignmentToAssignmentResponse(foundAssignment);
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public ListAssignmentResponse getAllAssignments(ListAssignmentRequest request) {
        log.info("Searching assignments");
        request.validateInput();

        Pageable pageable;
        if (request.getOrderBy() == 1)
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()));
        else
            pageable = PageRequest.of(request.getPageIndex(), request.getPageSize(), Sort.by(request.getSortBy()).descending());

        Page<Assignment> assignments = assignmentRepository.searchAssignments(request.getName(), request.getActive(), pageable);

        ListAssignmentResponse response = new ListAssignmentResponse();
        response.setTotalRecords(assignments.getTotalElements());
        response.setAssignmentList(assignments.stream().map(assignmentMapper::convertAssignmentToAssignmentResponse).toList());
        response.setPageIndex(request.getPageIndex());
        response.setPageSize(request.getPageSize());

        return response;
    }
}
