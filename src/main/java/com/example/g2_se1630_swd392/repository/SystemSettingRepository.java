package com.example.g2_se1630_swd392.repository;

import com.example.g2_se1630_swd392.entity.SystemSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface SystemSettingRepository extends BaseRepository<SystemSetting, Integer> {
    List<SystemSetting> findAllByType(String type);
    SystemSetting findByNameAndTypeAndActiveTrue(String name, String type);
    Optional<SystemSetting> findByIdAndTypeAndActiveTrue(Integer id, String type);
    List<SystemSetting> findAllByIdInAndTypeAndActiveTrue(List<Integer> ids, String type);
    @Query("SELECT s from SystemSetting s where s.name = ?1 and s.name <> ?2 and s.type = ?3")
    SystemSetting findByNameAndTypeForUpdate(String newName, String oldName, String type);
    @Query("SELECT s from SystemSetting s where (?1 is null or s.type = ?1)" +
            "and (?2 is null or lower(s.name) like %?2%)" +
            "and (?3 is null or s.active = ?3)")
    Page<SystemSetting> searchSystemSettings(String type, String name, Boolean active, Pageable pageable);

    // Find Role name
    @Query("SELECT role.name FROM SystemSetting role WHERE role.id = ?1")
    String findRoleNameById(Integer roleId);
}
