package com.example.g2_se1630_swd392.repository;

import com.example.g2_se1630_swd392.entity.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PermissionRepository extends BaseRepository<Permission, Integer> {
    List<Permission> findByIdIn(List<Integer> permissionIds);

    @Query("SELECT p FROM RolePermission AS rp JOIN Permission AS p ON rp.permissionId = p.id WHERE rp.roleId = ?1")
    List<Permission> getAllByRoleId(Integer roleId);
}
