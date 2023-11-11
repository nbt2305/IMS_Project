package com.example.g2_se1630_swd392.service;

import com.example.g2_se1630_swd392.dto.assignment.request.ListAssignmentRequest;
import com.example.g2_se1630_swd392.dto.assignment.response.ListAssignmentResponse;
import com.example.g2_se1630_swd392.entity.Assignment;

/**
 * @author Trung Nguyễn Bá
 * @created 9/27/2023
 * @project IMS_G2_SWD392
 */
public interface AssignmentService extends BaseService<Assignment, Integer>{
    ListAssignmentResponse getAllAssignments(ListAssignmentRequest request);
}
