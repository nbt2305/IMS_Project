package com.example.g2_se1630_swd392.dto.project.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectRequest extends CreateProjectRequest{
    private Integer id;
}
