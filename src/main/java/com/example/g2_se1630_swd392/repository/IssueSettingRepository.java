package com.example.g2_se1630_swd392.repository;

import com.example.g2_se1630_swd392.entity.IssueSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IssueSettingRepository extends BaseRepository<IssueSetting, Integer>{
    @Query("select i from IssueSetting i where (lower(i.name) like %:name%) " +
            "and (:gitlabIssueTypeId is null or i.gitlabIssueSettingId = :gitlabIssueTypeId) " +
            "and (:active is null or i.active = :active) " +
            "and (:projectId is null or i.project.id = :projectId) " +
            "and (:type is null or i.type like %:type%) " +
            "and (:classId is null or i._class.id = :classId) " +
            "and (:subjectId is null or i.subject.id = :subjectId) ")
    Page<IssueSetting> searchIssueSetting(String name, Integer gitlabIssueTypeId,
               Boolean active, Integer projectId,
               String type, Integer classId, Integer subjectId,
               Pageable pageable);

    @Query("select i from IssueSetting i where i.id = :id and i.type = :type")
    Optional<IssueSetting> findIssueSettingByIdAndType(Integer id, String type);
}
