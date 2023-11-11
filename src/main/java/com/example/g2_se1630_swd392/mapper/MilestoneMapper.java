package com.example.g2_se1630_swd392.mapper;

import com.example.g2_se1630_swd392.dto.milestone.request.CreateMilestoneRequest;
import com.example.g2_se1630_swd392.dto.milestone.request.UpdateMilestoneRequest;
import com.example.g2_se1630_swd392.dto.milestone.response.MilestoneResponse;
import com.example.g2_se1630_swd392.entity.Milestone;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@Component
public class MilestoneMapper {
    private static final ModelMapper modelMapper = new ModelMapper();
    public Milestone convertCreateMilestoneRequestToMilestone(CreateMilestoneRequest request) {
        Milestone response = new Milestone();
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        response.setStartDate(request.getStartDate());
        response.setDueDate(request.getDueDate());
        return response;
    }
    public Milestone convertUpdateMilestoneRequestToMilestone(UpdateMilestoneRequest request, Milestone response) {
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        response.setStartDate(request.getStartDate());
        response.setDueDate(request.getDueDate());
        return response;
    }
    public MilestoneResponse convertMilestoneToMilestoneResponse(Milestone request) {
        MilestoneResponse response = new MilestoneResponse();
        response.setId(request.getId());
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        response.setStartDate(request.getStartDate());
        response.setDueDate(request.getDueDate());
        return response;
    }




}
