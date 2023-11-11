package com.example.g2_se1630_swd392.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "class")
@EqualsAndHashCode(callSuper = true)
public class Class extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "gitlab_group_id")
    private Integer gitlabGroupId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private User teacher;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "semester_id", referencedColumnName = "id")
    private SystemSetting semester;

}
