package com.example.g2_se1630_swd392.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignment")
@EqualsAndHashCode(callSuper = true)
public class Assignment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "due_date")
    private Date dueDate;

//    @ManyToOne
//    @JoinColumn(name = "subject_id")
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @JsonIgnoreProperties(value = {"assignment"})
//    private Subject subject;
}
