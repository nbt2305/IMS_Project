package com.example.g2_se1630_swd392.dto._class.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateClassRequest {
    private String name;
    private String description;
    private Integer teacherId;
    private Integer subjectId;
    private Integer semesterId;
}
