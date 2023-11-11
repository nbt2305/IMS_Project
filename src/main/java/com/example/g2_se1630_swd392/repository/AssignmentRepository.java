package com.example.g2_se1630_swd392.repository;

import com.example.g2_se1630_swd392.entity.Assignment;
import com.example.g2_se1630_swd392.entity.Class;
import com.example.g2_se1630_swd392.entity.SystemSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends BaseRepository<Assignment, Integer>{
    //Lấy class theo subject_id
    @Query("SELECT su FROM SubjectAssignment AS ss JOIN Assignment AS su ON ss.assignmentId = su.id WHERE ss.subjectId = ?1")
    List<Assignment> getAllBySubjectId(Integer subjectId);
    int countByIdIn(List<Integer> ids);
    Assignment findByNameAndActiveTrue(String name);
    @Query("SELECT a from Assignment a where a.name = ?1 and a.name <> ?2")
    Assignment findByNameForUpdate(String newName, String oldName);
    @Query("SELECT s from Assignment s where (?1 is null or lower(s.name) like %?1%)" +
            "and s.active = ?2")
    Page<Assignment> searchAssignments(String name, Boolean active, Pageable pageable);

}
