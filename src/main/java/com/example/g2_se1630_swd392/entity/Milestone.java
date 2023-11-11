package com.example.g2_se1630_swd392.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "milestone")
@EqualsAndHashCode(callSuper = true)
public class Milestone extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "gitlab_milestone_id")
    private Integer gitlabMilestoneId;
    @Column(name = "started_date")
    private Date startDate;
    @Column(name = "due_date")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties(value = {"milestone"})
    private Project project;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties(value = {"milestone"})
    private Class classs;

}
