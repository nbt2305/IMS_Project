package com.example.g2_se1630_swd392.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "class_student")
public class ClassStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(name = "class_id")
    private Integer classId;
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "status")
    private String status;
}
