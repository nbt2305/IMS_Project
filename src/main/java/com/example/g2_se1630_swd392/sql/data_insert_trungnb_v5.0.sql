insert into permissions (id, description, name)
values  (1, 'manage issue', 'MANAGE_ISSUES'),
        (2, 'manage projects', 'MANAGE_PROJECTS'),
        (3, 'manage classes', 'MANAGE_CLASSES'),
        (4, 'manage subjects', 'MANAGE_SUBJECTS'),
        (5, 'manage users', 'MANAGE_USERS'),
        (6, 'manage system settings', 'MANAGE_SYSTEM_SETTINGS');

insert into class_student (id, class_id, project_id, status, student_id)
values  (1, 1, null, null, 1),
        (2, 1, null, null, 2),
        (3, 1, null, null, 3),
        (4, 1, null, null, 4),
        (5, 1, null, null, 5),
        (6, 1, null, null, 6),
        (7, 1, null, null, 7),
        (8, 1, null, null, 8),
        (9, 1, null, null, 9),
        (10, 1, null, null, 10);