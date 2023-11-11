package com.example.g2_se1630_swd392.repository;

import com.example.g2_se1630_swd392.entity.Permission;
import com.example.g2_se1630_swd392.entity.RolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RolePermissionRepository extends BaseRepository<RolePermission, Integer> {
    List<RolePermission> findByRoleId(Integer roleId);
}
