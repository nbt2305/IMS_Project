package com.example.g2_se1630_swd392.dto.milestone.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CreateMilestoneRequest {
    private String title;
    private String description;
    private Date startDate;
    private Date dueDate;
    private Integer projectId;
    private Integer classId;
}
