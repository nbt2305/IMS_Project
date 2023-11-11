package com.example.g2_se1630_swd392.dto.milestone.response;

import com.example.g2_se1630_swd392.entity.Class;
import com.example.g2_se1630_swd392.entity.Project;
import com.example.g2_se1630_swd392.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("milestoneResponse")
public class MilestoneResponse {
    private Integer id;
    private String title;
    private String description;
    private Date startDate;
    private Date dueDate;
    private Class __class;
    private Project project;
}
