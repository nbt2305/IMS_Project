package com.example.g2_se1630_swd392.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "picture")
    private String picture;
    @Column(name = "name")
    private String name;
    @Column(name = "email_verified")
    private Boolean emailVerified;
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "phone_number")
    private String phoneNumber;
}
