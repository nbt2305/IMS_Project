package com.example.g2_se1630_swd392.repository;

import com.example.g2_se1630_swd392.entity.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClassRepository extends BaseRepository<Class, Integer>{
    //Lấy class theo subject_id

    int countByIdIn(List<Integer> ids);

    Class findByNameAndActiveTrue(String name);

    Class findByIdAndActiveTrue(Integer id);
    @Query("SELECT c from Class c where c.name = ?1 and c.subject.id = ?2 and c.semester.id = ?3")
    Class findByNameAndSubjectIdAndSemesterId(String name, Integer subjectId, Integer semesterId);
    @Query("SELECT c from Class c where c.name = ?1 and c.name <> ?2")
    Class findByNameForUpdate(String newName, String oldName);

    @Query("SELECT s from Class s where (?1 is null or lower(s.name) like %?1%)" +
            "and (?2 is null or s.teacher.id = ?2)" +
            "and (?3 is null or s.subject.id = ?3)" +
            "and (?4 is null or s.semester.id = ?4)")
    Page<Class> findAllClasses(String name , Integer teacherId, Integer subjectId, Integer semesterId, Pageable pageable);
}
