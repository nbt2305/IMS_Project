package com.example.g2_se1630_swd392.repository;

import com.example.g2_se1630_swd392.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IssueRepository extends BaseRepository<Issue, Integer> {

    @Query("select i from Issue i where (:assigneeId is null or i.assignee.id = :assigneeId) " +
            "and (:dueDate is null or i.dueDate = :dueDate) " +
            "and (:milestoneId is null or i.milestone.id = :milestoneId) " +
            "and (:title is null or lower(i.title) like %:title%) " +
            "and (:issueTypeId is null or i.issueType.id = :issueTypeId) " +
            "and (:issueStatusId is null or i.issueStatus.id = :issueStatusId) " +
            "and (:processId is null or i.process.id = :processId) " +
            "and (coalesce((:projectIds), null) is null or i.project.id in (:projectIds)) " +
            "and (:gitlabIssueId is null or i.gitlabIssueId = :gitlabIssueId)")
    Page<Issue> findIssues(Integer assigneeId,
                           Date dueDate,
                           Integer milestoneId,
                           String title,
                           Integer issueTypeId,
                           Integer issueStatusId,
                           Integer processId,
                           List<Integer> projectIds,
                           Integer gitlabIssueId,
                           Pageable pageable);
}
