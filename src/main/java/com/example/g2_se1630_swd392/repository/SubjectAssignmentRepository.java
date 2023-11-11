package com.example.g2_se1630_swd392.repository;

import com.example.g2_se1630_swd392.entity.SubjectAssignment;

public interface SubjectAssignmentRepository extends BaseRepository<SubjectAssignment, Integer>{
    void deleteAllBySubjectId(Integer id);
}
