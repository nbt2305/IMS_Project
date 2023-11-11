package com.example.g2_se1630_swd392.dto.subject.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateSubjectRequest {
    private String name;
    private String description;
    private List<Integer> assignments;
}
