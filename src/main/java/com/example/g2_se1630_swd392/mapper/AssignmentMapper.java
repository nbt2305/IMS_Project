package com.example.g2_se1630_swd392.mapper;

import com.example.g2_se1630_swd392.dto.assignment.request.CreateAssignmentRequest;
import com.example.g2_se1630_swd392.dto.assignment.request.UpdateAssignmentRequest;
import com.example.g2_se1630_swd392.dto.assignment.response.AssignmentResponse;
import com.example.g2_se1630_swd392.entity.Assignment;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@Component
public class AssignmentMapper {
    private static final ModelMapper modelMapper = new ModelMapper();
    public Assignment convertCreateAssignmentRequestToAssignment(CreateAssignmentRequest request) {
        return modelMapper.map(request, Assignment.class);
    }
    public Assignment convertUpdateAssignmentRequestToAssignment(UpdateAssignmentRequest request, Integer id) {
        Assignment response = modelMapper.map(request, Assignment.class);
        response.setId(id);
        return response;
    }
    public AssignmentResponse convertAssignmentToAssignmentResponse(Assignment request) {
        AssignmentResponse response = new AssignmentResponse();
        response.setId(request.getId());
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        response.setDueDate(request.getDueDate());
        return response;
    }




}
