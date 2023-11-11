package com.example.g2_se1630_swd392.repository;

import com.example.g2_se1630_swd392.entity.Assignment;
import com.example.g2_se1630_swd392.entity.Class;
import com.example.g2_se1630_swd392.entity.Milestone;
import com.example.g2_se1630_swd392.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MilestoneRepository extends BaseRepository<Milestone, Integer>{
    Milestone findByTitle(String title);
    @Query("SELECT a from Milestone a where a.title = ?1 and a.title <> ?2")
    Milestone findByNameForUpdate(String newName, String oldName);
    Milestone findByTitleAndClasssAndActiveTrue(String title, Class classs);
    Milestone findByTitleAndProjectAndActiveTrue(String title, Project project);
    @Query("select i from Milestone i where (?1 is null or i.classs.id = ?1) " +
            "and (?2 is null or i.project.id = ?2)")
    Page<Milestone> findMileStones(Integer classId, Integer projectId, Pageable pageable);
}
