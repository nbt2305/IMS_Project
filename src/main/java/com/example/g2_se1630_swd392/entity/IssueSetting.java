package com.example.g2_se1630_swd392.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "issue_setting")
public class IssueSetting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;
    @Column(name = "gitlab_issue_setting_id")
    private Integer gitlabIssueSettingId;
    @ManyToOne
    @JoinColumn(name = "project_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties(value = {"issue_setting"})
    private Project project;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties(value = {"issue_setting"})
    private Class _class;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties(value = {"issue_setting"})
    private Subject subject;

    @OneToMany(mappedBy = "issueType", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties(value = {"issue_type", "issue"})
    @JsonIgnore
    private List<Issue> issues;
}
