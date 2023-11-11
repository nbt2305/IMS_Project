package com.example.g2_se1630_swd392.entity;

import com.example.g2_se1630_swd392.entity.Subject;
import com.example.g2_se1630_swd392.entity.SystemSetting;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

/**
 * @author Nguyen Ba Trung
 * @created 2023.08.03 - 8:59 AM
 * @project G2_SWD392
 */
@Data
@MappedSuperclass
public class BaseEntity {
    @Column(name = "active")
    private Boolean active = true;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate = new Date();

    @LastModifiedDate
    @Column(name = "updated_date")
    private Date updatedDate = new Date();

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

}
