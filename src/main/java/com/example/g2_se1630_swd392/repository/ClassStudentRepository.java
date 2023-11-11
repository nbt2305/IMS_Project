package com.example.g2_se1630_swd392.repository;

import com.example.g2_se1630_swd392.entity.ClassStudent;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClassStudentRepository extends BaseRepository<ClassStudent, Integer>{
    void deleteAllByClassIdAndProjectIdAndStudentIdIn(Integer id, Integer projectId, List<Integer> studentIds);
    void deleteAllByClassIdAndStudentIdIn(Integer id, List<Integer> studentIds);
    List<ClassStudent> findByClassIdAndProjectIdNull(Integer classId);
}
