package com.example.g2_se1630_swd392.dto.project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectRequest {
    private String name;
    private String avatar;
    private String status;
    private String description;
    private Integer gitlabProjectId;
    private Integer classId;
    private Integer leaderId;
}
