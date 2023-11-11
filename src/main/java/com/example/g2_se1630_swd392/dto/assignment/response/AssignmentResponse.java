package com.example.g2_se1630_swd392.dto.assignment.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("assignmentResponse")
public class AssignmentResponse {
    private Integer id;
    private String name;
    private String description;
    private Date dueDate;
}
