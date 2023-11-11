CREATE DATABASE  IF NOT EXISTS `swd392` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `swd392`;
create table assignment
(
    id           int auto_increment
        primary key,
    active       bit          null,
    created_by   varchar(255) null,
    created_date datetime(6)  null,
    updated_by   varchar(255) null,
    updated_date datetime(6)  null,
    description  varchar(255) null,
    due_date     datetime(6)  null,
    name         varchar(255) null
);

create table class_student
(
    id         int auto_increment
        primary key,
    class_id   int          null,
    project_id int          null,
    status     varchar(255) null,
    student_id int          null
);

create table permissions
(
    id          int auto_increment
        primary key,
    description varchar(255) null,
    name        varchar(255) null
);

create table role_permission
(
    id            int auto_increment
        primary key,
    permission_id int null,
    role_id       int null
);

create table subject_assignment
(
    id            int auto_increment
        primary key,
    assignment_id int null,
    subject_id    int null
);

create table system_setting
(
    id           int auto_increment
        primary key,
    active       bit          null,
    created_by   varchar(255) null,
    created_date datetime(6)  null,
    updated_by   varchar(255) null,
    updated_date datetime(6)  null,
    description  varchar(255) null,
    name         varchar(255) null,
    order_by     int          null,
    type         varchar(255) null
);

create table users
(
    id             int auto_increment
        primary key,
    active         bit          null,
    created_by     varchar(255) null,
    created_date   datetime(6)  null,
    updated_by     varchar(255) null,
    updated_date   datetime(6)  null,
    email          varchar(255) null,
    email_verified bit          null,
    name           varchar(255) null,
    password       varchar(255) null,
    phone_number   varchar(255) null,
    picture        varchar(255) null,
    role_id        int          null,
    username       varchar(255) null
);

create table subject
(
    id                    int auto_increment
        primary key,
    active                bit          null,
    created_by            varchar(255) null,
    created_date          datetime(6)  null,
    updated_by            varchar(255) null,
    updated_date          datetime(6)  null,
    description           varchar(255) null,
    gitlab_group_label_id int          null,
    name                  varchar(255) null,
    manager_id            int          null,
    constraint FKimv5jpt40lkj5lkj7qcl844kk
        foreign key (manager_id) references users (id)
);

create table class
(
    id              int auto_increment
        primary key,
    active          bit          null,
    created_by      varchar(255) null,
    created_date    datetime(6)  null,
    updated_by      varchar(255) null,
    updated_date    datetime(6)  null,
    description     varchar(255) null,
    gitlab_group_id int          null,
    name            varchar(255) null,
    semester_id     int          null,
    subject_id      int          null,
    teacher_id      int          null,
    constraint FK34v5b06opg9o8muvajovprk37
        foreign key (teacher_id) references users (id),
    constraint FKh0eyrgvqpfux7dvr8elhhdaf6
        foreign key (subject_id) references subject (id),
    constraint FKqiuof4ls1a5dyl3hovwaq0omu
        foreign key (semester_id) references system_setting (id)
);

create table project
(
    id                int auto_increment
        primary key,
    active            bit          null,
    created_by        varchar(255) null,
    created_date      datetime(6)  null,
    updated_by        varchar(255) null,
    updated_date      datetime(6)  null,
    avatar            varchar(255) null,
    description       varchar(255) null,
    gitlab_project_id int          null,
    name              varchar(255) null,
    status            varchar(255) null,
    class_id          int          null,
    leader_id         int          null,
    constraint FKk0qgt7g0ymcq91k1b4g5lntxl
        foreign key (leader_id) references users (id),
    constraint FKksdd9351hs2x48apxc9j3qs1i
        foreign key (class_id) references class (id)
);

create table issue_setting
(
    id                      int auto_increment
        primary key,
    active                  bit          null,
    created_by              varchar(255) null,
    created_date            datetime(6)  null,
    updated_by              varchar(255) null,
    updated_date            datetime(6)  null,
    description             varchar(255) null,
    gitlab_issue_setting_id int          null,
    name                    varchar(255) null,
    type                    varchar(255) null,
    class_id                int          null,
    project_id              int          null,
    subject_id              int          null,
    constraint FK1pqxfsvveq01t8j83txjinuwd
        foreign key (subject_id) references subject (id),
    constraint FK25habi6l89f1i0j66l6msyyjy
        foreign key (project_id) references project (id),
    constraint FKbq0x2by5bq5cqrfx5b5ltobkn
        foreign key (class_id) references class (id)
);

create table milestone
(
    id                  int auto_increment
        primary key,
    active              bit          null,
    created_by          varchar(255) null,
    created_date        datetime(6)  null,
    updated_by          varchar(255) null,
    updated_date        datetime(6)  null,
    description         varchar(255) null,
    due_date            datetime(6)  null,
    gitlab_milestone_id int          null,
    started_date        datetime(6)  null,
    title               varchar(255) null,
    class_id            int          null,
    project_id          int          null,
    constraint FKc3o4jxeki21gqbpy8ejyxtnus
        foreign key (project_id) references project (id),
    constraint FKodyoxmk0w776nvo4n66wyudev
        foreign key (class_id) references class (id)
);

create table issue
(
    id              int auto_increment
        primary key,
    active          bit          null,
    created_by      varchar(255) null,
    created_date    datetime(6)  null,
    updated_by      varchar(255) null,
    updated_date    datetime(6)  null,
    description     varchar(255) null,
    due_date        datetime(6)  null,
    gitlab_issue_id int          null,
    title           varchar(255) null,
    assignee_id     int          null,
    issue_status_id int          null,
    issue_type_id   int          null,
    milestone_id    int          null,
    process_id      int          null,
    project_id      int          null,
    constraint FK41vs0mhk1hrx1q2wrl51l0fh3
        foreign key (process_id) references issue_setting (id),
    constraint FK7t1o4tuel06m9bn4dppqmiod6
        foreign key (milestone_id) references milestone (id),
    constraint FKcombytcpeogaqi2012phvvvhy
        foreign key (project_id) references project (id),
    constraint FKi7k021nv0lpxgi6cdtjduaqjm
        foreign key (issue_status_id) references issue_setting (id),
    constraint FKif8mre3uuo6qs1s9qa2wbvphm
        foreign key (assignee_id) references users (id),
    constraint FKskgdt7pkg6e7d0xh6d2046611
        foreign key (issue_type_id) references issue_setting (id)
);






