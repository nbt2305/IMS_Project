package com.example.g2_se1630_swd392.dto._class.response;

import com.example.g2_se1630_swd392.entity.Project;
import com.example.g2_se1630_swd392.entity.Subject;
import com.example.g2_se1630_swd392.entity.SystemSetting;
import com.example.g2_se1630_swd392.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("classResponse")
public class ClassResponse {
    private Integer id;
    private String name;
    private String description;
    private List<Project> projects;
    private User teacher;
    private Subject subject;
    private SystemSetting semester;
}
