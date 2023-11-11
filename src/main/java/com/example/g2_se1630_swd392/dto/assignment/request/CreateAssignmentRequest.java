package com.example.g2_se1630_swd392.dto.assignment.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CreateAssignmentRequest {
    private String name;
    private String description;
    private Date dueDate;
}
