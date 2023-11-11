
insert into project(active, created_by, created_date, updated_by, updated_date, avatar, description, gitlab_project_id, name, status, class_id, leader_id)
values(true, null, '2023-10-03 11:43:03.000000', null, null, null, 'abc', null, 'project 1', null, null, 1),
      (true, null, '2023-10-03 11:43:03.000000', null, null, null, 'xyz', null, 'project 2', null, null, 2),
      (true, null, '2023-10-03 11:43:03.000000', null, null, null, 'hdd', null, 'project 3', null, null, 3),
      (true, null, '2023-10-03 11:43:03.000000', null, null, null, 'nnb', null, 'project 4', null, 1, null),
      (true, null, '2023-10-03 11:43:03.000000', null, null, null, 'ytr', null, 'project 5', null, 2, null);

insert into issue_setting (active, created_by, created_date, updated_by, updated_date, description, gitlab_issue_setting_id, name, type, class_id, project_id, subject_id)
VALUES (true, null, '2023-10-03 11:43:03.000000', null, null, 'status for issue', null, 'status 1', 'Issue Status', null, 1, null),
       (true, null, '2023-10-03 11:43:03.000000', null, null, 'issue setting for class', null, 'issue class 1', null, null, 1, null),
       (true, null, '2023-10-03 11:43:03.000000', null, null, 'type for issue', null, 'type 1', 'Issue Type', null, 1, null),
       (true, null, '2023-10-03 11:43:03.000000', null, null, 'issue setting for subject', null, 'issue subject 1', null, null, null, 1),
       (true, null, '2023-10-03 11:43:03.000000', null, null, 'process of issue', null, 'testing process', 'Process', null, 1, null);

insert into milestone(active, created_by, created_date, updated_by, updated_date, description, due_date, gitlab_milestone_id, started_date, title, class_id, project_id)
values (true, null, '2023-10-03 11:43:03.000000', null, null, 'desc milestone 1', null, null, null, 'milestone 1', 1, null),
       (true, null, '2023-10-03 11:43:03.000000', null, null, 'desc milestone 2', null, null, null, 'milestone 2', 2, null),
       (true, null, '2023-10-03 11:43:03.000000', null, null, 'desc milestone 3', null, null, null, 'milestone 3', null, 2),
       (true, null, '2023-10-03 11:43:03.000000', null, null, 'desc milestone 4', null, null, null, 'milestone 4', null, 1),
       (true, null, '2023-10-03 11:43:03.000000', null, null, 'desc milestone 5', null, null, null, 'milestone 5', null, 1);

insert into issue(active, created_by, created_date, updated_by, updated_date, description, due_date, gitlab_issue_id, title, assignee_id, issue_type_id, milestone_id, project_id, issue_status_id, process_id)
values (true, null, '2023-10-03 11:43:03.000000', null, null, 'desc issue 1', null, null, 'issue 1', 1, 3, null, 1, null, null),
       (true, null, '2023-10-03 11:43:03.000000', null, null, 'desc issue 2', null, null, 'issue 2', 1, 3, null, 1, null, 5),
       (true, null, '2023-10-03 11:43:03.000000', null, null, null, null, null, 'issue 3', 1, 3, null, 1, 1, null),
       (true, null, '2023-10-03 11:43:03.000000', null, null, 'abx', null, null, 'issue 4', 1, 3, null, 1, null, null),
       (true, null, '2023-10-03 11:43:03.000000', null, null, 'tfd', null, null, 'issue 5', 1, 3, null, 1, null, null);