package com.example.g2_se1630_swd392.dto._class.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateClassStudentRequest {
    private Integer classId;
    private List<Integer> students;
    private Integer projectId;
}
