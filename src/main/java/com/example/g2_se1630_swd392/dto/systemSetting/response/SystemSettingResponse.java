package com.example.g2_se1630_swd392.dto.systemSetting.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@RedisHash("systemSettingResponse")
public class SystemSettingResponse implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private Boolean active;
    private Date createdDate;
    private Date updatedDate;
    private String createdBy;
    private String updatedBy;
    private String type;
    private Integer order;
}
